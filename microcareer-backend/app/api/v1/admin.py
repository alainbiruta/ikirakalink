"""
Admin Panel - User Management
==============================

PATCHED to match actual DB schema:

  users table columns:
    id, email, full_name, phone_number, profile_picture_url,
    location, latitude, longitude, rating, completed_jobs, skills,
    created_at, updated_at, is_synced, available_hours,
    company_name, company_description, role, hashed_password, fcm_token

Key differences from original:
  - User.name        → User.full_name
  - User.is_active   → does NOT exist; we use role == "SUSPENDED" as the
                        suspended sentinel (see SUSPEND / ACTIVATE below)
  - User.is_verified → does NOT exist; removed from edit endpoint
  - UserEdit         → only touches fields that actually exist in the table
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from sqlalchemy import func, and_, or_, desc
from typing import Optional
import logging
from sqlalchemy import func, and_, or_, desc, text
from datetime import datetime, timedelta

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application
from app.models.review import Review
from app.models.payment import WorkConfirmation, Payment
from app.schemas.admin import (
    JobModerate,
    UserSuspend,
    UserEdit,
    ReviewModerate,
    AdminApiResponse,
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ─────────────────────────────────────────────────────────────────────────────
# SENTINEL VALUE used instead of is_active (column doesn't exist).
# A suspended user gets role = "SUSPENDED"; restoring sets it back to the
# original role stored in the suspend payload (or EMPLOYEE by default).
# ─────────────────────────────────────────────────────────────────────────────
SUSPENDED_ROLE = "SUSPENDED"


# ══════════════════════════════════════════════════════════════════════════════
# HELPER: Admin Verification
# ══════════════════════════════════════════════════════════════════════════════

async def get_admin_user(current_user: User = Depends(get_current_user)) -> User:
    """Verify the current user is an ADMIN."""
    if current_user.role != "ADMIN":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Admin access required",
        )
    return current_user


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 1: LIST ALL USERS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/users", response_model=AdminApiResponse)
async def list_all_users(
    search: Optional[str] = Query(None, description="Search by full_name, email, or phone"),
    role: Optional[str] = Query(None, description="Filter by role (EMPLOYEE, EMPLOYER, ADMIN, SUSPENDED …)"),
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """
    List all users.

    NOTE: `is_active` does not exist in the DB.  Use `role=SUSPENDED` to
    filter suspended accounts.
    """
    logger.info(f"Admin {admin.email} listing users")

    query = db.query(User)

    if search:
        term = f"%{search}%"
        query = query.filter(
            or_(
                User.full_name.ilike(term),       # ← was User.name
                User.email.ilike(term),
                User.phone_number.ilike(term),
            )
        )

    if role:
        query = query.filter(User.role == role)

    total = query.count()
    users = query.order_by(User.created_at.desc()).offset(skip).limit(limit).all()

    return AdminApiResponse(
        success=True,
        message=f"Found {total} users",
        data={
            "users": [_user_dict(u) for u in users],
            "total": total,
            "skip": skip,
            "limit": limit,
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 2: GET USER DETAILS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/users/{user_id}", response_model=AdminApiResponse)
async def get_user_details(
    user_id: str,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """Detailed user info including statistics."""
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User not found")

    jobs_posted = (
        db.query(Job).filter(Job.employer_id == user_id).count()
        if user.role in ("EMPLOYER", "ADMIN")
        else 0
    )
    jobs_applied = (
        db.query(Application).filter(Application.employee_id == user_id).count()
        if user.role in ("EMPLOYEE", "ADMIN")
        else 0
    )

    completed_jobs = db.query(WorkConfirmation).filter(
        WorkConfirmation.employee_id == user_id,
        WorkConfirmation.status == "COMPLETED",
    ).count()

    reviews_received = db.query(Review).filter(
        Review.reviewee_id == user_id,
        Review.is_approved == True,
    ).all()

    average_rating = (
        sum(r.overall_rating for r in reviews_received) / len(reviews_received)
        if reviews_received
        else 0
    )

    total_earned = (
        db.query(func.sum(Payment.net_amount))
        .filter(Payment.employee_id == user_id, Payment.status == "PAID")
        .scalar()
        or 0
    )
    total_paid = (
        db.query(func.sum(Payment.amount))
        .filter(Payment.employer_id == user_id, Payment.status == "PAID")
        .scalar()
        or 0
    )

    return AdminApiResponse(
        success=True,
        message="User details retrieved",
        data={
            "user": _user_dict(user),
            "stats": {
                "jobsPosted": jobs_posted,
                "jobsApplied": jobs_applied,
                "jobsCompleted": completed_jobs,
                "averageRating": round(average_rating, 1),
                "totalReviews": len(reviews_received),
                "totalEarned": float(total_earned),
                "totalPaid": float(total_paid),
            },
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 3: EDIT USER
# ══════════════════════════════════════════════════════════════════════════════

@router.put("/users/{user_id}", response_model=AdminApiResponse)
async def edit_user(
    user_id: str,
    data: UserEdit,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """
    Edit user profile.

    Updatable fields (all optional):
      full_name, email, role, phone_number, location,
      available_hours, company_name, company_description

    Fields removed from original because they don't exist in the DB:
      is_active, is_verified
    """
    logger.info(f"Admin {admin.email} editing user {user_id}")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User not found")

    # full_name  (was `name` in the original)
    if getattr(data, "full_name", None):
        user.full_name = data.full_name
    elif getattr(data, "name", None):          # tolerate old field name
        user.full_name = data.name

    if getattr(data, "email", None):
        existing = db.query(User).filter(
            User.email == data.email, User.id != user_id
        ).first()
        if existing:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST, detail="Email already in use"
            )
        user.email = data.email

    if getattr(data, "role", None):
        user.role = data.role

    if getattr(data, "phone_number", None):
        user.phone_number = data.phone_number

    if getattr(data, "location", None):
        user.location = data.location

    if getattr(data, "available_hours", None) is not None:
        user.available_hours = data.available_hours

    if getattr(data, "company_name", None):
        user.company_name = data.company_name

    if getattr(data, "company_description", None):
        user.company_description = data.company_description

    db.commit()
    db.refresh(user)

    logger.info(f"✅ User {user_id} updated by admin")
    return AdminApiResponse(
        success=True, message="User updated successfully", data=_user_dict(user)
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 4: SUSPEND USER
# ══════════════════════════════════════════════════════════════════════════════

@router.post("/users/{user_id}/suspend", response_model=AdminApiResponse)
async def suspend_user(
    user_id: str,
    data: UserSuspend,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """
    Suspend a user.

    Because there is no `is_active` column we set role = 'SUSPENDED'.
    The original role is stored in `data.original_role` (if provided) so
    activate_user can restore it.  Cannot suspend ADMIN accounts.
    """
    logger.info(f"Admin {admin.email} suspending user {user_id}")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User not found")

    if user.role == "ADMIN":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Cannot suspend admin users"
        )

    previous_role = user.role
    user.role = SUSPENDED_ROLE

    db.commit()

    logger.info(f"✅ User {user_id} suspended (was {previous_role}): {data.reason}")
    return AdminApiResponse(
        success=True,
        message=(
            f"User suspended for {data.duration_days} days"
            if data.duration_days
            else "User suspended permanently"
        ),
        data={
            "userId": user_id,
            "previousRole": previous_role,
            "reason": data.reason,
            "durationDays": data.duration_days,
            "suspendedAt": str(user.updated_at),
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 5: ACTIVATE USER
# ══════════════════════════════════════════════════════════════════════════════

@router.post("/users/{user_id}/activate", response_model=AdminApiResponse)
async def activate_user(
    user_id: str,
    restore_role: Optional[str] = Query("EMPLOYEE", description="Role to restore (EMPLOYEE or EMPLOYER)"),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """
    Re-activate a suspended user.

    Restores their role to `restore_role` (defaults to EMPLOYEE).
    """
    logger.info(f"Admin {admin.email} activating user {user_id}")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User not found")

    user.role = restore_role or "EMPLOYEE"
    db.commit()
    db.refresh(user)

    logger.info(f"✅ User {user_id} activated with role {user.role}")
    return AdminApiResponse(
        success=True, message="User activated successfully", data=_user_dict(user)
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 6: DELETE USER
# ══════════════════════════════════════════════════════════════════════════════

@router.delete("/users/{user_id}", response_model=AdminApiResponse)
async def delete_user(
    user_id: str,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    """
    Hard-delete a user.

    No `is_active` column exists, so this performs an actual DB delete.
    Cannot delete ADMIN accounts.
    """
    logger.info(f"Admin {admin.email} deleting user {user_id}")

    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="User not found")

    if user.role == "ADMIN":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Cannot delete admin users"
        )

    user_info = {
        "userId": user_id,
        "email": user.email,
        "full_name": user.full_name,
        "deletedBy": admin.email,
        "deletedAt": str(datetime.utcnow()),
    }

    db.delete(user)
    db.commit()

    logger.info(f"✅ User {user_id} hard-deleted by admin")
    return AdminApiResponse(
        success=True, message="User deleted successfully", data=user_info
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 7: LIST ALL JOBS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/jobs", response_model=AdminApiResponse)
async def list_all_jobs(
    search: Optional[str] = Query(None),
    status: Optional[str] = Query(None),
    employer_id: Optional[str] = Query(None),
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} listing jobs")

    query = db.query(Job)

    if search:
        term = f"%{search}%"
        query = query.filter(
            or_(Job.title.ilike(term), Job.description.ilike(term))
        )
    if status:
        query = query.filter(Job.status == status)
    if employer_id:
        query = query.filter(Job.employer_id == employer_id)

    total = query.count()
    jobs = query.order_by(Job.created_at.desc()).offset(skip).limit(limit).all()

    jobs_data = []
    for job in jobs:
        jd = job.to_dict()
        employer = db.query(User).filter(User.id == job.employer_id).first()
        if employer:
            # Use full_name (not .name)
            jd["employerName"] = employer.full_name
            jd["employerEmail"] = employer.email
        jobs_data.append(jd)

    return AdminApiResponse(
        success=True,
        message=f"Found {total} jobs",
        data={"jobs": jobs_data, "total": total, "skip": skip, "limit": limit},
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 8: MODERATE JOB
# ══════════════════════════════════════════════════════════════════════════════

@router.put("/jobs/{job_id}", response_model=AdminApiResponse)
async def moderate_job(
    job_id: str,
    data: JobModerate,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} moderating job {job_id}")

    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    old_status = job.status
    job.status = data.status

    if data.moderation_note:
        logger.info(f"Moderation note for job {job_id}: {data.moderation_note}")

    db.commit()
    db.refresh(job)

    logger.info(f"✅ Job {job_id}: {old_status} → {data.status}")
    return AdminApiResponse(
        success=True,
        message=f"Job status updated from {old_status} to {data.status}",
        data={
            **job.to_dict(),
            "moderationNote": data.moderation_note,
            "moderatedBy": admin.email,
            "moderatedAt": str(job.updated_at),
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 9: DELETE JOB
# ══════════════════════════════════════════════════════════════════════════════

@router.delete("/jobs/{job_id}", response_model=AdminApiResponse)
async def delete_job(
    job_id: str,
    reason: str = Query(..., min_length=10),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} deleting job {job_id} — {reason}")

    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    info = {
        "id": job.id,
        "title": job.title,
        "employer_id": job.employer_id,
        "status": job.status,
        "deletedBy": admin.email,
        "deletionReason": reason,
    }

    db.execute(text("DELETE FROM saved_jobs WHERE job_id = :jid"), {"jid": job_id})
    db.query(Application).filter(Application.job_id == job_id).delete()
    db.delete(job)
    db.commit()

    logger.info(f"✅ Job {job_id} deleted")
    return AdminApiResponse(success=True, message="Job deleted successfully", data=info)


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 10: JOB STATISTICS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/jobs/{job_id}/stats", response_model=AdminApiResponse)
async def get_job_stats(
    job_id: str,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    applications = db.query(Application).filter(Application.job_id == job_id).all()
    app_stats = {
        "total": len(applications),
        "pending":   sum(1 for a in applications if a.status == "PENDING"),
        "accepted":  sum(1 for a in applications if a.status == "ACCEPTED"),
        "rejected":  sum(1 for a in applications if a.status == "REJECTED"),
        "withdrawn": sum(1 for a in applications if a.status == "WITHDRAWN"),
    }

    confirmations = db.query(WorkConfirmation).filter(WorkConfirmation.job_id == job_id).all()
    work_stats = {
        "total":     len(confirmations),
        "completed": sum(1 for c in confirmations if c.status == "COMPLETED"),
        "pending":   sum(1 for c in confirmations if c.status in ("PENDING","EMPLOYER_CONFIRMED","EMPLOYEE_CONFIRMED")),
        "disputed":  sum(1 for c in confirmations if c.status == "DISPUTED"),
    }

    payments = db.query(Payment).filter(Payment.job_id == job_id).all()
    payment_stats = {
        "total":       len(payments),
        "totalAmount": sum(float(p.amount) for p in payments),
        "paid":        sum(1 for p in payments if p.status == "PAID"),
        "pending":     sum(1 for p in payments if p.status in ("PENDING","PROCESSING")),
    }

    return AdminApiResponse(
        success=True,
        message="Job statistics retrieved",
        data={
            "job": job.to_dict(),
            "applications": app_stats,
            "workConfirmations": work_stats,
            "payments": payment_stats,
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 11: LIST FLAGGED REVIEWS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/reviews/flagged", response_model=AdminApiResponse)
async def get_flagged_reviews(
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} viewing flagged reviews")

    query = db.query(Review).filter(Review.is_flagged == True)
    total = query.count()
    reviews = query.order_by(Review.created_at.desc()).offset(skip).limit(limit).all()

    return AdminApiResponse(
        success=True,
        message=f"Found {total} flagged reviews",
        data={
            "reviews": [_enrich_review(rv, db) for rv in reviews],
            "total": total,
            "skip": skip,
            "limit": limit,
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 12: MODERATE REVIEW
# ══════════════════════════════════════════════════════════════════════════════

@router.put("/reviews/{review_id}/moderate", response_model=AdminApiResponse)
async def moderate_review(
    review_id: str,
    data: ReviewModerate,
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} moderating review {review_id}")

    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Review not found")

    review.is_approved = data.is_approved
    review.is_flagged  = False

    if data.moderation_note:
        logger.info(f"Moderation note for review {review_id}: {data.moderation_note}")

    db.commit()
    db.refresh(review)

    label = "approved" if data.is_approved else "rejected"
    logger.info(f"✅ Review {review_id} {label}")
    return AdminApiResponse(
        success=True,
        message=f"Review {label} successfully",
        data={
            **review.to_dict(),
            "moderationNote": data.moderation_note,
            "moderatedBy": admin.email,
            "moderatedAt": str(review.updated_at),
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 13: LIST ALL REVIEWS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/reviews", response_model=AdminApiResponse)
async def list_all_reviews(
    search: Optional[str] = Query(None),
    is_approved: Optional[bool] = Query(None),
    is_flagged: Optional[bool] = Query(None),
    rating: Optional[int] = Query(None, ge=1, le=5),
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    query = db.query(Review)
    if search:
        query = query.filter(Review.comment.ilike(f"%{search}%"))
    if is_approved is not None:
        query = query.filter(Review.is_approved == is_approved)
    if is_flagged is not None:
        query = query.filter(Review.is_flagged == is_flagged)
    if rating:
        query = query.filter(Review.overall_rating == rating)

    total   = query.count()
    reviews = query.order_by(Review.created_at.desc()).offset(skip).limit(limit).all()

    return AdminApiResponse(
        success=True,
        message=f"Found {total} reviews",
        data={
            "reviews": [_enrich_review(rv, db) for rv in reviews],
            "total": total,
            "skip": skip,
            "limit": limit,
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 14: DELETE REVIEW
# ══════════════════════════════════════════════════════════════════════════════

@router.delete("/reviews/{review_id}", response_model=AdminApiResponse)
async def delete_review(
    review_id: str,
    reason: str = Query(..., min_length=10),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} deleting review {review_id} — {reason}")

    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Review not found")

    info = {
        "id": review.id,
        "reviewerId": review.reviewer_id,
        "revieweeId": review.reviewee_id,
        "rating": review.overall_rating,
        "deletedBy": admin.email,
        "deletionReason": reason,
    }
    db.delete(review)
    db.commit()

    logger.info(f"✅ Review {review_id} deleted")
    return AdminApiResponse(success=True, message="Review deleted successfully", data=info)


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 15: PLATFORM STATISTICS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/stats/platform", response_model=AdminApiResponse)
async def get_platform_stats(
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} fetching platform statistics")

    # ── Users ──────────────────────────────────────────────────────────────
    total_users    = db.query(User).count()
    # "active" = anyone whose role is not SUSPENDED
    active_users   = db.query(User).filter(User.role != SUSPENDED_ROLE).count()
    suspended_users = total_users - active_users

    employees = db.query(User).filter(User.role == "EMPLOYEE").count()
    employers = db.query(User).filter(User.role == "EMPLOYER").count()
    admins    = db.query(User).filter(User.role == "ADMIN").count()

    seven_days_ago  = (datetime.utcnow() - timedelta(days=7)).isoformat()
    thirty_days_ago = (datetime.utcnow() - timedelta(days=30)).isoformat()

    new_users_7d  = db.query(User).filter(User.created_at >= seven_days_ago).count()
    new_users_30d = db.query(User).filter(User.created_at >= thirty_days_ago).count()

    # ── Jobs ───────────────────────────────────────────────────────────────
    total_jobs     = db.query(Job).count()
    open_jobs      = db.query(Job).filter(Job.status == "OPEN").count()
    closed_jobs    = db.query(Job).filter(Job.status == "CLOSED").count()
    cancelled_jobs = db.query(Job).filter(Job.status == "CANCELLED").count()

    new_jobs_7d  = db.query(Job).filter(Job.created_at >= seven_days_ago).count()
    new_jobs_30d = db.query(Job).filter(Job.created_at >= thirty_days_ago).count()

    job_categories = (
        db.query(Job.category, func.count(Job.id).label("count"))
        .group_by(Job.category)
        .order_by(desc("count"))
        .limit(5)
        .all()
    )
    top_categories = {cat: cnt for cat, cnt in job_categories} if job_categories else {}

    # ── Applications ───────────────────────────────────────────────────────
    total_applications   = db.query(Application).count()
    pending_applications = db.query(Application).filter(Application.status == "PENDING").count()
    accepted_applications = db.query(Application).filter(
    Application.status.in_(["ACCEPTED", "COMPLETED"])
    ).count()
    rejected_applications = db.query(Application).filter(Application.status == "REJECTED").count()

    application_rate = (total_applications / open_jobs * 100) if open_jobs else 0
    acceptance_rate  = (accepted_applications / total_applications * 100) if total_applications else 0

    # ── Work Confirmations ─────────────────────────────────────────────────
    total_confirmations = db.query(Application).filter(
        Application.status.in_(["ACCEPTED", "COMPLETED"])
    ).count()
    completed_work = db.query(Application).filter(Application.status == "COMPLETED").count()
    pending_work   = db.query(Application).filter(Application.status == "ACCEPTED").count()
    disputed_work  = 0  # no dispute system implemented yet

    completion_rate = (completed_work / total_confirmations * 100) if total_confirmations else 0
    dispute_rate    = 0.0

    # ── Reviews ────────────────────────────────────────────────────────────
    total_reviews   = db.query(Review).filter(Review.is_approved == True).count()
    flagged_reviews = db.query(Review).filter(Review.is_flagged == True).count()

    avg_rating = (
        db.query(func.avg(Review.overall_rating))
        .filter(Review.is_approved == True, Review.is_public == True)
        .scalar()
        or 0
    )

    rating_dist = {}
    for r in [5, 4, 3, 2, 1]:
        rating_dist[str(r)] = db.query(Review).filter(
            Review.overall_rating == r, Review.is_approved == True
        ).count()

    # ── Payments ───────────────────────────────────────────────────────────
    total_payments   = db.query(Payment).count()
    paid_payments    = db.query(Payment).filter(Payment.status == "PAID").count()
    pending_payments = db.query(Payment).filter(
        Payment.status.in_(("PENDING", "PROCESSING"))
    ).count()

    total_earnings      = db.query(func.sum(Payment.amount)).filter(Payment.status == "PAID").scalar() or 0
    total_platform_fees = db.query(func.sum(Payment.platform_fee_amount)).filter(Payment.status == "PAID").scalar() or 0
    total_net           = db.query(func.sum(Payment.net_amount)).filter(Payment.status == "PAID").scalar() or 0

    avg_payment = float(total_earnings) / paid_payments if paid_payments else 0

    first_of_month = datetime.utcnow().replace(day=1, hour=0, minute=0, second=0).isoformat()
    payments_this_month = (
        db.query(func.sum(Payment.amount))
        .filter(Payment.status == "PAID", Payment.paid_at >= first_of_month)
        .scalar()
        or 0
    )

    stats = {
        "users": {
            "total": total_users,
            "active": active_users,
            "suspended": suspended_users,   # renamed from "inactive" — no is_active column
            "employees": employees,
            "employers": employers,
            "admins": admins,
            "new7Days": new_users_7d,
            "new30Days": new_users_30d,
        },
        "jobs": {
            "total": total_jobs,
            "open": open_jobs,
            "closed": closed_jobs,
            "cancelled": cancelled_jobs,
            "new7Days": new_jobs_7d,
            "new30Days": new_jobs_30d,
            "topCategories": top_categories,
        },
        "applications": {
            "total": total_applications,
            "pending": pending_applications,
            "accepted": accepted_applications,
            "rejected": rejected_applications,
            "applicationRate": round(application_rate, 1),
            "acceptanceRate": round(acceptance_rate, 1),
        },
        "work": {
            "totalConfirmations": total_confirmations,
            "completed": completed_work,
            "pending": pending_work,
            "disputed": disputed_work,
            "completionRate": round(completion_rate, 1),
            "disputeRate": round(dispute_rate, 1),
        },
        "reviews": {
            "total": total_reviews,
            "flagged": flagged_reviews,
            "averageRating": round(float(avg_rating), 1),
            "ratingDistribution": rating_dist,
        },
        "finance": {
            "totalEarnings": float(total_earnings),
            "platformFeesCollected": float(total_platform_fees),
            "netToEmployees": float(total_net),
            "totalPayments": total_payments,
            "paidPayments": paid_payments,
            "pendingPayments": pending_payments,
            "averagePayment": round(avg_payment, 2),
            "paymentsThisMonth": float(payments_this_month),
            "currency": "RWF",
        },
        "engagement": {
            "jobsPerEmployer": round(float(total_jobs) / employers, 1) if employers else 0,
            "applicationsPerJob": round(float(total_applications) / total_jobs, 1) if total_jobs else 0,
            "completedJobsPerEmployee": round(float(completed_work) / employees, 1) if employees else 0,
        },
        "generatedAt": datetime.utcnow().isoformat(),
    }

    logger.info("✅ Platform statistics generated")
    return AdminApiResponse(
        success=True, message="Platform statistics retrieved successfully", data=stats
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 16: ACTIVITY TIMELINE
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/stats/activity", response_model=AdminApiResponse)
async def get_activity_timeline(
    days: int = Query(30, ge=1, le=90),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    logger.info(f"Admin {admin.email} fetching {days}-day activity timeline")

    start = (datetime.utcnow() - timedelta(days=days)).isoformat()

    daily_users = db.query(
        func.date(User.created_at).label("date"),
        func.count(User.id).label("count"),
    ).filter(User.created_at >= start).group_by(func.date(User.created_at)).all()

    daily_jobs = db.query(
        func.date(Job.created_at).label("date"),
        func.count(Job.id).label("count"),
    ).filter(Job.created_at >= start).group_by(func.date(Job.created_at)).all()

    daily_apps = db.query(
    func.date(Application.applied_at).label("date"),
    func.count(Application.id).label("count"),
    ).filter(Application.applied_at >= start).group_by(func.date(Application.applied_at)).all()

    return AdminApiResponse(
        success=True,
        message=f"Activity timeline for last {days} days",
        data={
            "users":        {str(d): c for d, c in daily_users},
            "jobs":         {str(d): c for d, c in daily_jobs},
            "applications": {str(d): c for d, c in daily_apps},
            "period":       f"Last {days} days",
            "startDate":    start,
            "endDate":      datetime.utcnow().isoformat(),
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# ENDPOINT 17: TOP PERFORMERS
# ══════════════════════════════════════════════════════════════════════════════

@router.get("/stats/top-performers", response_model=AdminApiResponse)
async def get_top_performers(
    limit: int = Query(10, ge=1, le=50),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db),
):
    # Top rated employees — use rating column that already exists on User
    top_rated = (
        db.query(User)
        .filter(User.role == "EMPLOYEE", User.rating.isnot(None))
        .order_by(desc(User.rating))
        .limit(limit)
        .all()
    )

    # Most active employers (by jobs posted)
    top_employers = (
        db.query(
            User.id, User.full_name, User.email,
            func.count(Job.id).label("jobs_posted"),
        )
        .join(Job, User.id == Job.employer_id)
        .filter(User.role == "EMPLOYER")
        .group_by(User.id, User.full_name, User.email)
        .order_by(desc("jobs_posted"))
        .limit(limit)
        .all()
    )

    # Top earners (employees with most paid payments)
    top_earners = (
        db.query(
            User.id, User.full_name, User.email,
            func.sum(Payment.net_amount).label("total_earned"),
        )
        .join(Payment, User.id == Payment.employee_id)
        .filter(User.role == "EMPLOYEE", Payment.status == "PAID")
        .group_by(User.id, User.full_name, User.email)
        .order_by(desc("total_earned"))
        .limit(limit)
        .all()
    )

    return AdminApiResponse(
        success=True,
        message="Top performers retrieved",
        data={
            "topRated": [
                {
                    "userId": u.id,
                    "name": u.full_name,
                    "email": u.email,
                    "averageRating": round(float(u.rating), 1) if u.rating else 0,
                    "completedJobs": u.completed_jobs or 0,
                }
                for u in top_rated
            ],
            "topEmployers": [
                {"userId": uid, "name": name, "email": email, "jobsPosted": cnt}
                for uid, name, email, cnt in top_employers
            ],
            "topEarners": [
                {"userId": uid, "name": name, "email": email, "totalEarned": float(earned or 0)}
                for uid, name, email, earned in top_earners
            ],
        },
    )


# ══════════════════════════════════════════════════════════════════════════════
# PRIVATE HELPERS
# ══════════════════════════════════════════════════════════════════════════════

def _user_dict(u: User) -> dict:
    """
    Safe serialisation of a User row.
    Returns only the columns that actually exist in the DB.
    Does NOT expose hashed_password or fcm_token.
    """
    return {
        "id":                  u.id,
        "email":               u.email,
        "full_name":           u.full_name,
        "phone_number":        u.phone_number,
        "profile_picture_url": u.profile_picture_url,
        "location":            u.location,
        "latitude":            float(u.latitude)  if u.latitude  else None,
        "longitude":           float(u.longitude) if u.longitude else None,
        "rating":              float(u.rating)    if u.rating    else 0.0,
        "completed_jobs":      u.completed_jobs   or 0,
        "skills":              u.skills,
        "available_hours":     u.available_hours,
        "company_name":        u.company_name,
        "company_description": u.company_description,
        "role":                u.role,
        "is_synced":           u.is_synced,
        "created_at":          str(u.created_at),
        "updated_at":          str(u.updated_at),
        # Derived / virtual fields for the frontend
        "is_active":           u.role != SUSPENDED_ROLE,
        "is_suspended":        u.role == SUSPENDED_ROLE,
    }


def _enrich_review(rv: Review, db: Session) -> dict:
    """Attach reviewer/reviewee names and job title to a review dict."""
    d = rv.to_dict()

    reviewer = db.query(User).filter(User.id == rv.reviewer_id).first()
    if reviewer:
        d["reviewerName"]  = reviewer.full_name   # ← full_name not .name
        d["reviewerEmail"] = reviewer.email

    reviewee = db.query(User).filter(User.id == rv.reviewee_id).first()
    if reviewee:
        d["revieweeName"]  = reviewee.full_name
        d["revieweeEmail"] = reviewee.email

    job = db.query(Job).filter(Job.id == rv.job_id).first()
    if job:
        d["jobTitle"] = job.title

    return d