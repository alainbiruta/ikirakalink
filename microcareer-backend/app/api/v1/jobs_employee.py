"""
Employee Job Endpoints
======================

API routes for employees to browse and apply for jobs.

Features:
- Browse jobs with AI match scores
- Filter by category, payment, urgency, distance
- Apply for jobs
- Save / unsave jobs
- View saved jobs list

Pagination note
---------------
Both skip/limit (Flutter default) and page/page_size are accepted on
every list endpoint.  When skip or limit are supplied they take priority;
otherwise page/page_size are used.  This keeps the client and any future
admin tooling compatible without a breaking change.
"""

import uuid
import logging
from typing import Optional

from fastapi import APIRouter, Depends, HTTPException, Query, status
from sqlalchemy.orm import Session
from sqlalchemy import and_
from fastapi import APIRouter, Depends, HTTPException, Query, status, Request
from slowapi import Limiter
from slowapi.util import get_remote_address 
from app.database import get_db
from app.dependencies import get_current_employee
from app.models.job import Application, Job, SavedJob
from app.models.user import User
from app.schemas.job import (
    ApiResponse,
    ApplicationApiResponse,
    ApplicationCreate,
    JobApiResponse,
    JobListApiResponse,
    JobListResponse,
    JobResponse,
)
from app.services.advanced_matching import get_matching_service

logger = logging.getLogger(__name__)

router = APIRouter()

limiter = Limiter(key_func=get_remote_address)
# ─────────────────────────────────────────────────────────────────────────────
# Internal helpers
# ─────────────────────────────────────────────────────────────────────────────
# ADD THIS BLOCK
CATEGORY_DEFAULT_SKILLS = {
    "Cleaning":       ["cleaning", "housekeeping", "sanitation", "laundry"],
    "Construction":   ["construction", "masonry", "carpentry", "painting"],
    "Cooking":        ["cooking", "chef", "catering", "food preparation"],
    "Hospitality":    ["hospitality", "customer service", "cooking", "waitstaff"],
    "Delivery":       ["delivery", "driving", "logistics", "courier"],
    "Agriculture":    ["farming", "agriculture", "irrigation", "harvesting"],
    "Teaching":       ["teaching", "tutoring", "education", "training"],
    "Healthcare":     ["healthcare", "nursing", "patient care", "medical"],
    "Security":       ["security", "surveillance", "patrol", "guard"],
    "IT & Tech":      ["programming", "software", "networking", "technical"],
    "Retail":         ["retail", "sales", "cashier", "customer service"],
    "Manufacturing":  ["manufacturing", "assembly", "production", "quality control"],
    "Transportation": ["driving", "transport", "logistics", "heavy vehicle"],
    "Other":          [],
}

def _effective_skills(job: Job) -> list:
    if job.required_skills:
        return job.required_skills
    return CATEGORY_DEFAULT_SKILLS.get(job.category, [])
def _resolve_pagination(
    skip: int,
    limit: int,
    page: int,
    page_size: int,
) -> tuple[int, int]:
    """
    Return (offset, page_size) from whichever pagination style was used.

    Rules
    -----
    - If the caller sent skip > 0 OR an explicit limit != the default (20),
      treat it as skip/limit style.
    - Otherwise fall back to page/page_size style.
    This means a plain request with no params at all uses page=1, page_size=20
    which is the correct default for both styles.
    """
    if skip > 0 or limit != 20:
        return skip, limit
    offset = (page - 1) * page_size
    return offset, page_size


def _employee_dict(user: User) -> dict:
    """Build the employee profile dict expected by the matching service."""
    return {
        "skills": user.skills or [],
        "latitude": user.latitude,
        "longitude": user.longitude,
        "available_hours": user.available_hours,
        "rating": user.rating,
    }


def _enrich_job(
    job: Job,
    employee: User,
    db: Session,
    max_distance_km: float = 50.0,
) -> tuple[JobResponse, float]:
    matching_service = get_matching_service()

    user_profile = {
        "skills":                employee.skills or [],
        "location":              getattr(employee, "location", None),
        "expected_payment":      getattr(employee, "expected_payment", None),
        "experience_years":      getattr(employee, "experience_years", 0),
        "preferred_categories":  getattr(employee, "preferred_categories", []),
    }

    job_data = {
        "required_skills":  _effective_skills(job),
        "preferred_skills": getattr(job, "preferred_skills", []),
        "location":         job.location,
        "payment":          float(job.payment) if job.payment else 0,
        "min_experience":   getattr(job, "min_experience", 0),
        "max_experience":   getattr(job, "max_experience", None),
        "category":         job.category,
    }

    score_data   = matching_service.calculate_comprehensive_score(user_profile, job_data)
    match_score  = score_data["overall_score"]

    is_saved = (
        db.query(SavedJob)
        .filter(and_(SavedJob.job_id == job.id, SavedJob.employee_id == employee.id))
        .first() is not None
    )
    has_applied = (
        db.query(Application)
        .filter(and_(Application.job_id == job.id, Application.employee_id == employee.id))
        .first() is not None
    )

    response            = JobResponse.from_orm(job)
    response.match_score = match_score
    response.is_saved    = is_saved
    response.has_applied = has_applied
    return response, match_score


# ─────────────────────────────────────────────────────────────────────────────
# Browse Jobs  –  GET /jobs
# ─────────────────────────────────────────────────────────────────────────────

@router.get("", response_model=JobListApiResponse)
@limiter.limit("60/minute")
async def get_jobs(
    # Filters
    request: Request,
    category: Optional[str] = None,
    min_payment: Optional[float] = None,
    max_payment: Optional[float] = None,
    is_urgent: Optional[bool] = None,
    max_distance_km: float = Query(50.0, ge=1, le=100),
    # skip/limit pagination (Flutter default)
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    # page/page_size pagination (alternative)
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    # Auth + DB
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    """
    Return open jobs with personalised AI match scores.

    **Employee only.**  Supports both ``skip``/``limit`` and
    ``page``/``page_size`` query params — whichever the client sends.
    Results are sorted by match score (highest first).
    """
    logger.info(f"Fetching jobs for employee: {current_user.email}")

    # Build base query
    query = db.query(Job).filter(Job.status == "OPEN")

    if category:
        query = query.filter(Job.category == category)
    if min_payment is not None:
        query = query.filter(Job.payment >= min_payment)
    if max_payment is not None:
        query = query.filter(Job.payment <= max_payment)
    if is_urgent is not None:
        query = query.filter(Job.is_urgent == is_urgent)

    total = query.count()

    offset, effective_limit = _resolve_pagination(skip, limit, page, page_size)
    jobs = query.offset(offset).limit(effective_limit).all()

    # Enrich and sort
    enriched = [_enrich_job(job, current_user, db, max_distance_km) for job in jobs]
    enriched.sort(key=lambda x: x[1], reverse=True)
    sorted_jobs = [r for r, _ in enriched]

    # Derive page number for the response envelope (works for both styles)
    effective_page = (offset // effective_limit) + 1 if effective_limit else 1
    has_more = total > (offset + effective_limit)

    logger.info(
        f"Returning {len(sorted_jobs)} jobs "
        f"(offset={offset}, limit={effective_limit}, total={total})"
    )

    return JobListApiResponse(
        success=True,
        message=f"Found {total} jobs",
        data=JobListResponse(
            jobs=sorted_jobs,
            total=total,
            page=effective_page,
            page_size=effective_limit,
            has_more=has_more,
        ),
    )


# ─────────────────────────────────────────────────────────────────────────────
# Saved Jobs List  –  GET /jobs/saved/list
# Must be declared BEFORE /{job_id} to avoid FastAPI treating "saved" as an id
# ─────────────────────────────────────────────────────────────────────────────

@router.get("/saved/list", response_model=JobListApiResponse)
async def get_saved_jobs(
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    """
    Return the employee's saved / bookmarked jobs.

    **Employee only.**
    """
    saved_records = (
        db.query(SavedJob).filter(SavedJob.employee_id == current_user.id).all()
    )
    job_ids = [s.job_id for s in saved_records]

    if not job_ids:
        return JobListApiResponse(
            success=True,
            message="No saved jobs",
            data=JobListResponse(jobs=[], total=0, page=1, page_size=20, has_more=False),
        )

    query = db.query(Job).filter(Job.id.in_(job_ids))
    total = query.count()

    offset, effective_limit = _resolve_pagination(skip, limit, page, page_size)
    jobs = query.offset(offset).limit(effective_limit).all()

    job_responses = []
    for job in jobs:
        r = JobResponse.from_orm(job)
        r.is_saved = True
        # Check has_applied too while we're here
        r.has_applied = (
            db.query(Application)
            .filter(
                and_(
                    Application.job_id == job.id,
                    Application.employee_id == current_user.id,
                )
            )
            .first()
            is not None
        )
        job_responses.append(r)

    effective_page = (offset // effective_limit) + 1 if effective_limit else 1
    has_more = total > (offset + effective_limit)

    return JobListApiResponse(
        success=True,
        message=f"Found {total} saved jobs",
        data=JobListResponse(
            jobs=job_responses,
            total=total,
            page=effective_page,
            page_size=effective_limit,
            has_more=has_more,
        ),
    )


# ─────────────────────────────────────────────────────────────────────────────
# Job Detail  –  GET /jobs/{job_id}
# ─────────────────────────────────────────────────────────────────────────────

@router.get("/{job_id}", response_model=JobApiResponse)
async def get_job_details(
    job_id: str,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    """
    Return full details for a single job, including match score.

    **Employee only.**
    """
    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    job_response, _ = _enrich_job(job, current_user, db)

    return JobApiResponse(
        success=True,
        message="Job retrieved successfully",
        data=job_response,
    )


# ─────────────────────────────────────────────────────────────────────────────
# Apply for Job  –  POST /jobs/{job_id}/apply
# ─────────────────────────────────────────────────────────────────────────────

@router.post("/{job_id}/apply", response_model=ApplicationApiResponse)
@limiter.limit("10/minute")
async def apply_for_job(
    request: Request,
    job_id: str,
    application_data: ApplicationCreate,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    logger.info(f"Employee {current_user.email} applying for job {job_id}")

    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    if not job.can_accept_applications():
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="This job is no longer accepting applications",
        )

    already_applied = (
        db.query(Application)
        .filter(
            and_(
                Application.job_id == job_id,
                Application.employee_id == current_user.id,
                Application.status != "WITHDRAWN",
            )
        )
        .first()
    )
    if already_applied:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="You have already applied for this job",
        )

    matching_service = get_matching_service()
    user_profile = {
    "skills": current_user.skills or [],
    "location": getattr(current_user, "location", None),
    "expected_payment": getattr(current_user, "expected_payment", None),
    "experience_years": getattr(current_user, "experience_years", 0),
    "preferred_categories": getattr(current_user, "preferred_categories", []),
}
    job_data = {
    "required_skills": _effective_skills(job),
    "location": job.location,
    "payment": float(job.payment) if job.payment else 0,
    "category": job.category,
}
    score_data  = matching_service.calculate_comprehensive_score(user_profile, job_data)
    match_score = score_data["overall_score"]

    application = Application(
        id=str(uuid.uuid4()),
        job_id=job_id,
        employee_id=current_user.id,
        status="PENDING",
        message=application_data.message,
        cover_letter=application_data.cover_letter,
        match_score=match_score,
    )
    db.add(application)
    job.total_applicants += 1
    db.commit()
    db.refresh(application)

    already_saved = (
        db.query(SavedJob)
        .filter(and_(SavedJob.job_id == job_id, SavedJob.employee_id == current_user.id))
        .first()
    )
    if not already_saved:
        db.add(SavedJob(id=str(uuid.uuid4()), job_id=job_id, employee_id=current_user.id))
        db.commit()

    logger.info(f"✅ Application created: {application.id}")

    return ApplicationApiResponse(
        success=True,
        message="Application submitted successfully",
        data=application,
    )

# ─────────────────────────────────────────────────────────────────────────────
# Save Job  –  POST /jobs/{job_id}/save
# ─────────────────────────────────────────────────────────────────────────────

@router.post("/{job_id}/save", response_model=ApiResponse)
async def save_job(
    job_id: str,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    """
    Bookmark a job for later.

    **Employee only.**  Returns 400 if already saved.
    """
    job = db.query(Job).filter(Job.id == job_id).first()
    if not job:
        raise HTTPException(status_code=status.HTTP_404_NOT_FOUND, detail="Job not found")

    already_saved = (
        db.query(SavedJob)
        .filter(and_(SavedJob.job_id == job_id, SavedJob.employee_id == current_user.id))
        .first()
    )
    if already_saved:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST, detail="Job already saved"
        )

    db.add(SavedJob(id=str(uuid.uuid4()), job_id=job_id, employee_id=current_user.id))
    db.commit()

    logger.info(f"Job {job_id} saved by {current_user.email}")

    return ApiResponse(success=True, message="Job saved successfully", data={"jobId": job_id})


# ─────────────────────────────────────────────────────────────────────────────
# Unsave Job  –  DELETE /jobs/{job_id}/save
# ─────────────────────────────────────────────────────────────────────────────

@router.delete("/{job_id}/save", response_model=ApiResponse)
async def unsave_job(
    job_id: str,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db),
):
    """
    Remove a job from bookmarks.

    **Employee only.**  Returns 404 if the job was not saved.
    """
    saved_job = (
        db.query(SavedJob)
        .filter(and_(SavedJob.job_id == job_id, SavedJob.employee_id == current_user.id))
        .first()
    )
    if not saved_job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND, detail="Job not in saved list"
        )

    db.delete(saved_job)
    db.commit()

    logger.info(f"Job {job_id} unsaved by {current_user.email}")

    return ApiResponse(
        success=True, message="Job removed from saved list", data={"jobId": job_id}
    )