"""
Employer Job Endpoints
======================

API routes for employers to post and manage jobs.

Features:
- Create job postings
- View own job listings
- Edit job listings
- Delete job listings
- View applicants with match scores
- Accept/reject applications
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from sqlalchemy import and_
from typing import Optional, List
import uuid
from datetime import datetime
import logging

from app.database import get_db

from app.dependencies import get_current_employer
from app.models.user import User
from app.models.job import Job, Application
from app.schemas.job import (
    JobCreate,
    JobUpdate,
    JobResponse,
    JobListResponse,
    JobApiResponse,
    JobListApiResponse,
    ApplicationWithEmployee,
    ApiResponse,
    JobStatus
)

logger = logging.getLogger(__name__)

router = APIRouter()
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

def _resolve_required_skills(skills: list, category: str) -> list:
    if skills:
        return skills
    return CATEGORY_DEFAULT_SKILLS.get(category, [])
# ══════════════════════════════════════════════════════════
# Create Job
# ══════════════════════════════════════════════════════════

@router.post("", response_model=JobApiResponse, status_code=status.HTTP_201_CREATED)
async def create_job(
    job_data: JobCreate,
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Create a new job posting.
    
    **Employer only**
    
    Required fields:
    - title
    - description
    - location (with coordinates)
    - required_skills
    - payment
    - duration
    - category
    """
    logger.info(f"Employer {current_user.email} creating job: {job_data.title}")
    
    # Validate employer has company info
    if not current_user.company_name:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Please set your company name before posting jobs"
        )
    
    # Create job
    job = Job(
        id=str(uuid.uuid4()),
        title=job_data.title,
        description=job_data.description,
        employer_id=current_user.id,
        employer_name=current_user.company_name,
        location=job_data.location,
        latitude=job_data.latitude  or 0.0,
        longitude=job_data.longitude  or 0.0,
        required_skills=_resolve_required_skills(job_data.required_skills or [], job_data.category),
        payment=job_data.payment,
        currency=job_data.currency,
        duration=job_data.duration,
        category=job_data.category,
        is_urgent=job_data.is_urgent,
        required_hours=job_data.required_hours,
        required_availability=job_data.required_availability,
        deadline=job_data.deadline,
        max_applicants=job_data.max_applicants,
        status="OPEN",
        total_applicants=0
    )
    
    db.add(job)
    db.commit()
    db.refresh(job)
    
    logger.info(f"✅ Job created: {job.id}")
    
    return JobApiResponse(
        success=True,
        message="Job posted successfully",
        data=JobResponse.from_orm(job)
    )

# ══════════════════════════════════════════════════════════
# Get My Jobs
# ══════════════════════════════════════════════════════════

@router.get("/mine", response_model=JobListApiResponse)
async def get_my_jobs(
    status_filter: Optional[JobStatus] = None,
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Get list of jobs posted by current employer.
    
    **Employer only**
    """
    logger.info(f"Fetching jobs for employer: {current_user.email}")
    
    # Build query
    query = db.query(Job).filter(Job.employer_id == current_user.id)
    
    if status_filter:
        query = query.filter(Job.status == status_filter.value)
    
    # Order by created date (newest first)
    query = query.order_by(Job.created_at.desc())
    
    # Get total count
    total = query.count()
    
    # Apply pagination
    offset = (page - 1) * page_size
    jobs = query.offset(offset).limit(page_size).all()
    
    # Convert to responses
    job_responses = [JobResponse.from_orm(job) for job in jobs]
    
    has_more = total > (page * page_size)
    
    list_response = JobListResponse(
        jobs=job_responses,
        total=total,
        page=page,
        page_size=page_size,
        has_more=has_more
    )
    
    logger.info(f"Returning {len(jobs)} jobs (page {page})")
    
    return JobListApiResponse(
        success=True,
        message=f"Found {total} jobs",
        data=list_response
    )

# ══════════════════════════════════════════════════════════
# Update Job
# ══════════════════════════════════════════════════════════

@router.put("/{job_id}", response_model=JobApiResponse)
async def update_job(
    job_id: str,
    job_update: JobUpdate,
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Update a job posting.
    
    **Employer only** - Can only update own jobs
    """
    logger.info(f"Employer {current_user.email} updating job {job_id}")
    
    # Get job
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Check ownership
    if not job.is_owned_by(current_user.id):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only update your own jobs"
        )
    
    # Update fields
    update_data = job_update.dict(exclude_unset=True)
    for field, value in update_data.items():
        setattr(job, field, value)
    
    db.commit()
    db.refresh(job)
    
    logger.info(f"✅ Job updated: {job_id}")
    
    return JobApiResponse(
        success=True,
        message="Job updated successfully",
        data=JobResponse.from_orm(job)
    )

# ══════════════════════════════════════════════════════════
# Delete Job
# ══════════════════════════════════════════════════════════

@router.delete("/{job_id}", response_model=ApiResponse)
async def delete_job(
    job_id: str,
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Delete a job posting.
    
    **Employer only** - Can only delete own jobs
    
    Note: This changes status to CANCELLED rather than deleting
    """
    logger.info(f"Employer {current_user.email} deleting job {job_id}")
    
    # Get job
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Check ownership
    if not job.is_owned_by(current_user.id):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only delete your own jobs"
        )
    
    # Soft delete - change status to CANCELLED
    job.status = "CANCELLED"
    db.commit()
    
    logger.info(f"✅ Job cancelled: {job_id}")
    
    return ApiResponse(
        success=True,
        message="Job cancelled successfully",
        data={"jobId": job_id}
    )

# ══════════════════════════════════════════════════════════
# Get Job Applicants
# ══════════════════════════════════════════════════════════

@router.get("/{job_id}/applicants", response_model=ApiResponse)
async def get_job_applicants(
    job_id: str,
    status_filter: Optional[str] = None,
    sort_by: str = Query("match_score", pattern="^(match_score|applied_at)$"),
    page: int = Query(1, ge=1),
    page_size: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Get list of applicants for a job.
    
    **Employer only** - Can only view applicants for own jobs
    
    Returns applicants with:
    - Employee profile summary
    - Match score
    - Application status
    - Application message
    """
    logger.info(f"Employer {current_user.email} viewing applicants for job {job_id}")
    
    # Get job
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Check ownership
    if not job.is_owned_by(current_user.id):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only view applicants for your own jobs"
        )
    
    # Build query
    query = db.query(Application).filter(Application.job_id == job_id)
    
    if status_filter:
        query = query.filter(Application.status == status_filter)
    
    # Sort
    if sort_by == "match_score":
        query = query.order_by(Application.match_score.desc())
    else:  # applied_at
        query = query.order_by(Application.applied_at.desc())
    
    # Get total count
    total = query.count()
    
    # Apply pagination
    offset = (page - 1) * page_size
    applications = query.offset(offset).limit(page_size).all()
    
    # Build response with employee details
    applicants = []
    for app in applications:
        # Get employee
        employee = db.query(User).filter(User.id == app.employee_id).first()
        
        if employee:
            employee_summary = {
                "id": employee.id,
                "fullName": employee.full_name or "Anonymous",
                "profilePictureUrl": employee.profile_picture_url,
                "location": employee.location,
                "rating": employee.rating,
                "completedJobs": employee.completed_jobs,
                "skills": employee.skills or []
            }
            
            applicant_data = {
                "id": app.id,
                "status": app.status,
                "message": app.message,
                "coverLetter": app.cover_letter,
                "matchScore": app.match_score,
                "appliedAt": app.applied_at.isoformat(),
                "employee": employee_summary
            }
            
            applicants.append(applicant_data)
    
    has_more = total > (page * page_size)
    
    logger.info(f"Returning {len(applicants)} applicants (page {page})")
    
    return ApiResponse(
        success=True,
        message=f"Found {total} applicants",
        data={
            "applicants": applicants,
            "total": total,
            "page": page,
            "pageSize": page_size,
            "hasMore": has_more
        }
    )

# ══════════════════════════════════════════════════════════
# Update Application Status
# ══════════════════════════════════════════════════════════

@router.put("/{job_id}/applicants/{application_id}", response_model=ApiResponse)
async def update_application_status(
    job_id: str,
    application_id: str,
    new_status: str = Query(..., pattern="^(ACCEPTED|REJECTED)$"),
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Accept or reject an application.
    
    **Employer only** - Can only update applications for own jobs
    """
    logger.info(f"Employer {current_user.email} updating application {application_id} to {new_status}")
    
    # Get job
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Check ownership
    if not job.is_owned_by(current_user.id):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only manage applications for your own jobs"
        )
    
    # Get application
    application = db.query(Application).filter(
        and_(
            Application.id == application_id,
            Application.job_id == job_id
        )
    ).first()
    
    if not application:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Application not found"
        )
    
    # Update status
    old_status = application.status
    application.status = new_status
    
    # If accepting, might want to close job
    if new_status == "ACCEPTED" and job.status == "OPEN":
        # Optionally auto-close job when accepting someone
        # job.status = "FILLED"
        pass
    
    db.commit()
    
    logger.info(f"✅ Application {application_id} updated: {old_status} → {new_status}")
    
    return ApiResponse(
        success=True,
        message=f"Application {new_status.lower()}",
        data={
            "applicationId": application_id,
            "newStatus": new_status
        }
    )

# ══════════════════════════════════════════════════════════
# Get Job Statistics
# ══════════════════════════════════════════════════════════

@router.get("/{job_id}/stats", response_model=ApiResponse)
async def get_job_stats(
    job_id: str,
    current_user: User = Depends(get_current_employer),
    db: Session = Depends(get_db)
):
    """
    Get statistics for a job.
    
    **Employer only** - Can only view stats for own jobs
    """
    # Get job
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Check ownership
    if not job.is_owned_by(current_user.id):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only view stats for your own jobs"
        )
    
    # Get application statistics
    total_apps = db.query(Application).filter(Application.job_id == job_id).count()
    pending_apps = db.query(Application).filter(
        and_(Application.job_id == job_id, Application.status == "PENDING")
    ).count()
    accepted_apps = db.query(Application).filter(
        and_(Application.job_id == job_id, Application.status == "ACCEPTED")
    ).count()
    rejected_apps = db.query(Application).filter(
        and_(Application.job_id == job_id, Application.status == "REJECTED")
    ).count()
    
    # Calculate average match score
    applications = db.query(Application).filter(Application.job_id == job_id).all()
    avg_match_score = sum(app.match_score or 0 for app in applications) / len(applications) if applications else 0
    
    return ApiResponse(
        success=True,
        message="Job statistics retrieved",
        data={
            "jobId": job_id,
            "totalApplicants": total_apps,
            "pending": pending_apps,
            "accepted": accepted_apps,
            "rejected": rejected_apps,
            "averageMatchScore": round(avg_match_score, 2),
            "status": job.status,
            "isUrgent": job.is_urgent,
            "createdAt": job.created_at.isoformat()
        }
    )

# ══════════════════════════════════════════════════════════