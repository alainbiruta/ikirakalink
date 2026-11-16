"""
Application Management Endpoints
=================================

API routes for employees to manage their job applications.

Features:
- View all applications
- Get application statistics
- Withdraw applications
- View application history
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query , Request
from slowapi import Limiter
from slowapi.util import get_remote_address
from sqlalchemy.orm import Session
from sqlalchemy import and_, func
from typing import Optional
import logging
from app.fcm_service import send_notification
from datetime import datetime

from app.database import get_db
from app.dependencies import get_current_employee
from app.models.user import User
from app.models.job import Job, Application
from app.schemas.job import ApplicationResponse, ApiResponse
from app.dependencies import get_current_employee, get_current_employer 

logger = logging.getLogger(__name__)

router = APIRouter()
limiter = Limiter(key_func=get_remote_address)

# ══════════════════════════════════════════════════════════
# Get My Applications
# ══════════════════════════════════════════════════════════

@router.get("/", response_model=ApiResponse)
async def get_my_applications(
    status_filter: Optional[str] = Query(None, pattern="^(PENDING|ACCEPTED|REJECTED|WITHDRAWN)$"),
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db)
):
    """
    Get all job applications for current employee.
    
    **Employee only**
    
    Returns applications with job details, sorted by date (newest first).
    """
    logger.info(f"Fetching applications for employee: {current_user.email}")
    
    # Build query
    query = db.query(Application).filter(Application.employee_id == current_user.id)
    
    if status_filter:
        query = query.filter(Application.status == status_filter)
    
    # Order by applied date (newest first)
    query = query.order_by(Application.applied_at.desc())
    
    # Get total count
    total = query.count()
    
    # Apply pagination
    offset = (page - 1) * page_size
    applications = query.offset(offset).limit(page_size).all()
    
    # Build response with job details
    applications_data = []
    for app in applications:
        # Get job details
        job = db.query(Job).filter(Job.id == app.job_id).first()
        
        if job:
            app_data = {
                "id": app.id,
                "jobId": app.job_id,
                "status": app.status,
                "message": app.message,
                "coverLetter": app.cover_letter,
                "matchScore": app.match_score,
                "appliedAt": app.applied_at.isoformat(),
                "updatedAt": app.updated_at.isoformat(),
                "job": {
                    "id": job.id,
                    "title": job.title,
                    "employerName": job.employer_name,
                    "location": job.location,
                    "payment": job.payment,
                    "currency": job.currency,
                    "category": job.category,
                    "status": job.status,
                    "isUrgent": job.is_urgent
                }
            }
            applications_data.append(app_data)
    
    has_more = total > (page * page_size)
    
    logger.info(f"Returning {len(applications_data)} applications (page {page})")
    
    return ApiResponse(
        success=True,
        message=f"Found {total} applications",
        data={
            "applications": applications_data,
            "total": total,
            "page": page,
            "pageSize": page_size,
            "hasMore": has_more
        }
    )

# ══════════════════════════════════════════════════════════
# Get Application Statistics
# ══════════════════════════════════════════════════════════

@router.get("/stats", response_model=ApiResponse)
async def get_application_stats(
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db)
):
    """
    Get application statistics for current employee.
    
    **Employee only**
    
    Returns:
    - Total applications
    - Applications by status
    - Average match score
    - Recent activity
    """
    logger.info(f"Fetching application stats for: {current_user.email}")
    
    # Get all applications
    applications = db.query(Application).filter(
        Application.employee_id == current_user.id
    ).all()
    
    total = len(applications)
    
    # Count by status
    pending = sum(1 for app in applications if app.status == "PENDING")
    accepted = sum(1 for app in applications if app.status == "ACCEPTED")
    rejected = sum(1 for app in applications if app.status == "REJECTED")
    withdrawn = sum(1 for app in applications if app.status == "WITHDRAWN")
    
    # Calculate average match score
    match_scores = [app.match_score for app in applications if app.match_score is not None]
    avg_match_score = sum(match_scores) / len(match_scores) if match_scores else 0.0
    
    # Get recent applications (last 7 days)
    from datetime import timedelta
    week_ago = datetime.utcnow() - timedelta(days=7)
    recent_applications = [
        app for app in applications 
        if app.applied_at and app.applied_at >= week_ago
    ]
    
    # Success rate (accepted / total non-pending)
    non_pending = accepted + rejected
    success_rate = (accepted / non_pending * 100) if non_pending > 0 else 0.0
    
    return ApiResponse(
        success=True,
        message="Application statistics retrieved",
        data={
            "total": total,
            "byStatus": {
                "pending": pending,
                "accepted": accepted,
                "rejected": rejected,
                "withdrawn": withdrawn
            },
            "averageMatchScore": round(avg_match_score, 2),
            "successRate": round(success_rate, 1),
            "recentApplications": len(recent_applications),
            "activeApplications": pending + accepted
        }
    )

@router.get("/received", response_model=ApiResponse)
@limiter.limit("30/minute") 
async def get_received_applications(
    request: Request, 
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_employer),  # employer only
    db: Session = Depends(get_db)
):
    """Get all applications received for employer's jobs. Employer only."""
    
    # Get all jobs owned by this employer
    employer_jobs = db.query(Job).filter(Job.employer_id == current_user.id).all()
    job_ids = [job.id for job in employer_jobs]

    if not job_ids:
        return ApiResponse(
            success=True,
            message="No applications received",
            data={"applications": [], "total": 0}
        )

    query = db.query(Application).filter(
        Application.job_id.in_(job_ids)
    ).order_by(Application.applied_at.desc())

    total = query.count()
    offset = (page - 1) * page_size
    applications = query.offset(offset).limit(page_size).all()

    apps_data = []
    for app in applications:
        employee = db.query(User).filter(User.id == app.employee_id).first()
        job = db.query(Job).filter(Job.id == app.job_id).first()
        if employee and job:
            apps_data.append({
                "id":          app.id,
                "status":      app.status,
                "matchScore":  app.match_score,
                "appliedAt":   app.applied_at.isoformat(),
                "message":     app.message,
                "coverLetter": app.cover_letter,
                "job": {
                    "id":    job.id,
                    "title": job.title
                },
                "employee": {
                    "id":            employee.id,
                    "fullName":      employee.full_name,
                    "phoneNumber":   employee.phone_number,
                    "profilePictureUrl": employee.profile_picture_url,
                    "location":      employee.location,
                    "rating":        employee.rating,
                    "completedJobs": employee.completed_jobs,
                    "skills":        employee.skills or []
                }
            })

    return ApiResponse(
        success=True,
        message=f"Found {total} applications",
        data={
            "applications": apps_data,
            "total":        total,
            "page":         page,
            "pageSize":     page_size,
            "hasMore":      total > (page * page_size)
        }
    )
# ══════════════════════════════════════════════════════════
# Update Application Status (Employer: Accept / Reject)
# ══════════════════════════════════════════════════════════

@router.patch("/{application_id}/status", response_model=ApiResponse)
async def update_application_status(
    application_id: str,
    job_id:         str = Query(...),
    new_status:     str = Query(..., alias="status"),
    current_user:   User = Depends(get_current_employer),
    db:             Session = Depends(get_db)
):
    
    """Employer accepts or rejects an application. Employer only."""
    logger.info(f"Employer {current_user.email} → application {application_id} → {new_status}")

    new_status = new_status.upper()
    if new_status not in {"ACCEPTED", "REJECTED"}:
        raise HTTPException(
            status_code=400,
            detail=f"Invalid status '{new_status}'. Must be ACCEPTED or REJECTED."
        )

    # Verify job belongs to this employer
    job = db.query(Job).filter(
        Job.id == job_id,
        Job.employer_id == current_user.id
    ).first()

    if not job:
        raise HTTPException(status_code=403, detail="Not authorized for this job")

    application = db.query(Application).filter(
        Application.id == application_id,
        Application.job_id == job_id
    ).first()

    if not application:
        raise HTTPException(status_code=404, detail="Application not found")

    # AFTER
    old_status = application.status
    application.status = new_status
    db.commit()

    logger.info(f"✅ Application {application_id}: {old_status} → {new_status}")

    # ── Send push notification to employee ────────────────
    employee = db.query(User).filter(User.id == application.employee_id).first()
    if employee and employee.fcm_token:
        if new_status == "ACCEPTED":
            title = "🎉 Application Accepted!"
            body  = f"Congratulations! You got the job: {job.title}"
        else:
            title = "Application Update"
            body  = f"Your application for {job.title} was not selected."

        send_notification(
            fcm_token = employee.fcm_token,
            title     = title,
            body      = body,
            data      = {
                "type"           : "application_accepted" if new_status == "ACCEPTED" else "application_rejected",
                "application_id" : application_id,
                "job_id"         : job_id,
            }
        )
    # ── End notification ──────────────────────────────────

    return ApiResponse(
        success=True,
        message=f"Application {new_status.lower()} successfully",
        data={
            "applicationId"  : application_id,
            "previousStatus" : old_status,
            "newStatus"      : new_status
        }
    )

# ══════════════════════════════════════════════════════════
# Withdraw Application
# ══════════════════════════════════════════════════════════

@router.patch("/{application_id}/withdraw", response_model=ApiResponse)
@limiter.limit("20/minute")  
async def withdraw_application(
    request: Request,           
    application_id: str,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db)
):
    """
    Withdraw a job application.
    
    **Employee only** - Can only withdraw own applications
    
    Can only withdraw PENDING applications.
    """
    logger.info(f"Employee {current_user.email} withdrawing application {application_id}")
    
    # Get application
    application = db.query(Application).filter(
        and_(
            Application.id == application_id,
            Application.employee_id == current_user.id
        )
    ).first()
    
    if not application:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Application not found"
        )
    
    # Check if can be withdrawn
    if application.status != "PENDING":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail=f"Cannot withdraw application with status: {application.status}"
        )
    
    # Update status
    old_status = application.status
    application.status = "WITHDRAWN"
    
    # Decrease job applicant count
    job = db.query(Job).filter(Job.id == application.job_id).first()
    if job and job.total_applicants > 0:
        job.total_applicants -= 1
    
    db.commit()
    
    logger.info(f"✅ Application withdrawn: {application_id} ({old_status} → WITHDRAWN)")
    
    return ApiResponse(
        success=True,
        message="Application withdrawn successfully",
        data={
            "applicationId": application_id,
            "previousStatus": old_status,
            "newStatus": "WITHDRAWN"
        }
    )

# ══════════════════════════════════════════════════════════
# ══════════════════════════════════════════════════════════
# Mark Application as Completed (Employer: work done)
# ══════════════════════════════════════════════════════════

@router.patch("/{application_id}/complete", response_model=ApiResponse)
async def mark_application_complete(
    application_id: str,
    current_user:   User = Depends(get_current_employer),
    db:             Session = Depends(get_db)
):
    """
    Employer marks work as completed — unlocks the rating flow.
    Only valid when application status is ACCEPTED.
    Employer only.
    """
    logger.info(f"Employer {current_user.email} marking {application_id} complete")

    # Load application and verify employer owns the job
    application = db.query(Application).join(
        Job, Job.id == Application.job_id
    ).filter(
        Application.id == application_id,
        Job.employer_id == current_user.id
    ).first()

    if not application:
        raise HTTPException(status_code=404, detail="Application not found or not authorized")

    if application.status != "ACCEPTED":
        raise HTTPException(
            status_code=400,
            detail=f"Can only complete an ACCEPTED application. Current status: {application.status}"
        )

    old_status          = application.status
    application.status  = "COMPLETED"
    application.updated_at = datetime.utcnow()

    # Increment worker's completed_jobs count
    employee = db.query(User).filter(User.id == application.employee_id).first()
    if employee:
        employee.completed_jobs = (employee.completed_jobs or 0) + 1

    db.commit()
    logger.info(f"✅ Application {application_id}: {old_status} → COMPLETED")

    # Notify worker
    if employee and employee.fcm_token:
        job = db.query(Job).filter(Job.id == application.job_id).first()
        send_notification(
            fcm_token=employee.fcm_token,
            title="Work Completed ✓",
            body=f"Your work on \"{job.title if job else 'the job'}\" has been marked complete. You can now be rated.",
            data={
                "type":           "work_completed",
                "application_id": application_id,
            }
        )

    return ApiResponse(
        success=True,
        message="Application marked as completed",
        data={
            "applicationId":  application_id,
            "previousStatus": old_status,
            "newStatus":      "COMPLETED"
        }
    )
# ══════════════════════════════════════════════════════════
# Get Single Application Details
# ══════════════════════════════════════════════════════════

@router.get("/{application_id}", response_model=ApiResponse)
async def get_application_details(
    application_id: str,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db)
):
    """
    Get detailed information about a specific application.
    
    **Employee only** - Can only view own applications
    """
    application = db.query(Application).filter(
        and_(
            Application.id == application_id,
            Application.employee_id == current_user.id
        )
    ).first()
    
    if not application:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Application not found"
        )
    
    # Get job details
    job = db.query(Job).filter(Job.id == application.job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Associated job not found"
        )
    
    return ApiResponse(
        success=True,
        message="Application details retrieved",
        data={
            "id": application.id,
            "status": application.status,
            "message": application.message,
            "coverLetter": application.cover_letter,
            "matchScore": application.match_score,
            "appliedAt": application.applied_at.isoformat(),
            "updatedAt": application.updated_at.isoformat(),
            "job": {
                "id": job.id,
                "title": job.title,
                "description": job.description,
                "employerName": job.employer_name,
                "location": job.location,
                "latitude": job.latitude,
                "longitude": job.longitude,
                "requiredSkills": job.required_skills or [],
                "payment": job.payment,
                "currency": job.currency,
                "duration": job.duration,
                "category": job.category,
                "status": job.status,
                "isUrgent": job.is_urgent,
                "totalApplicants": job.total_applicants
            }
        }
    )
