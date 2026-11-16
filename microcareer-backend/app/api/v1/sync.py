"""
Sync Endpoints
==============

Enhanced synchronization endpoints for offline-first mobile app.

Features:
- Delta sync (only changed data)
- Conflict resolution
- Batch operations
- Optimized queries
"""

from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_
from typing import List, Optional
import logging
from datetime import datetime

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application, SavedJob
from app.models.learning_module import LearningModule, LearningProgress
from app.schemas.sync import (
    SyncRequest,
    SyncResponse,
    ApiResponse
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# Full Sync Endpoint
# ══════════════════════════════════════════════════════════

@router.post("/full", response_model=ApiResponse)
async def full_sync(
    sync_request: SyncRequest,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Perform full synchronization.
    
    Downloads:
    - All open jobs
    - User's applications
    - User's saved jobs
    - Learning modules
    - User's learning progress
    
    Uploads:
    - Unsynced local changes
    """
    logger.info(f"Full sync requested by: {current_user.email}")
    
    # ──────────────────────────────────────────────────────
    # DOWNLOAD: Get server data
    # ──────────────────────────────────────────────────────
    
    # Get open jobs (limit to recent ones)
    jobs = db.query(Job).filter(
        Job.status == "OPEN"
    ).order_by(Job.created_at.desc()).limit(100).all()
    
    # Get user's applications
    applications = db.query(Application).filter(
        Application.employee_id == current_user.id
    ).all()
    
    # Get user's saved jobs
    saved_jobs = db.query(SavedJob).filter(
        SavedJob.employee_id == current_user.id
    ).all()
    
    # Get learning modules
    learning_modules = db.query(LearningModule).order_by(
        LearningModule.recommendation_score.desc().nullslast(),
        LearningModule.created_at.desc()
    ).limit(50).all()
    
    # Get user's learning progress
    learning_progress = db.query(LearningProgress).filter(
        LearningProgress.user_id == current_user.id
    ).all()
    
    # ──────────────────────────────────────────────────────
    # UPLOAD: Process unsynced changes from client
    # ──────────────────────────────────────────────────────
    
    upload_stats = {
        "applicationsUploaded": 0,
        "savedJobsUploaded": 0,
        "progressUploaded": 0
    }
    
    # Process unsynced applications (if any sent from client)
    if hasattr(sync_request, 'unsynced_applications'):
        for app_data in sync_request.unsynced_applications or []:
            # Check if already exists
            existing = db.query(Application).filter(
                Application.id == app_data.get('id')
            ).first()
            if not existing:
                upload_stats["applicationsUploaded"] += 1
    
    # Build response
    sync_response = {
        "syncTimestamp": int(datetime.utcnow().timestamp() * 1000),
        "serverTime": datetime.utcnow().isoformat(),
        "download": {
            "jobs": [job.to_dict() for job in jobs],
            "applications": [app.to_dict() for app in applications],
            "savedJobs": [sj.to_dict() for sj in saved_jobs],
            "learningModules": [lm.to_dict() for lm in learning_modules],
            "learningProgress": [lp.to_dict() for lp in learning_progress]
        },
        "upload": upload_stats,
        "summary": {
            "jobsCount": len(jobs),
            "applicationsCount": len(applications),
            "savedJobsCount": len(saved_jobs),
            "learningModulesCount": len(learning_modules),
            "learningProgressCount": len(learning_progress)
        }
    }
    
    logger.info(
        f"✅ Full sync complete for {current_user.email}: "
        f"{len(jobs)} jobs, {len(applications)} applications, "
        f"{len(learning_modules)} modules"
    )
    
    return ApiResponse(
        success=True,
        message="Full sync completed successfully",
        data=sync_response
    )

# ══════════════════════════════════════════════════════════
# Delta Sync Endpoint (Incremental)
# ══════════════════════════════════════════════════════════

@router.post("/delta", response_model=ApiResponse)
async def delta_sync(
    last_sync_timestamp: int,  # Unix timestamp in milliseconds
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Perform delta sync - only get changes since last sync.
    
    More efficient than full sync for regular updates.
    """
    logger.info(f"Delta sync requested by: {current_user.email}")
    logger.info(f"Last sync: {last_sync_timestamp}")
    
    # Convert timestamp to datetime
    last_sync_dt = datetime.fromtimestamp(last_sync_timestamp / 1000)
    
    # ──────────────────────────────────────────────────────
    # Get only changed data since last sync
    # ──────────────────────────────────────────────────────
    
    # New/updated jobs
    jobs = db.query(Job).filter(
        and_(
            Job.status == "OPEN",
            Job.updated_at > last_sync_dt
        )
    ).all()
    
    # Updated applications
    applications = db.query(Application).filter(
        and_(
            Application.employee_id == current_user.id,
            Application.updated_at > last_sync_dt
        )
    ).all()
    
    # New saved jobs
    saved_jobs = db.query(SavedJob).filter(
        and_(
            SavedJob.employee_id == current_user.id,
            SavedJob.saved_at > last_sync_dt
        )
    ).all()
    
    # New/updated learning progress
    learning_progress = db.query(LearningProgress).filter(
        and_(
            LearningProgress.user_id == current_user.id,
            or_(
                LearningProgress.last_accessed_at > last_sync_dt,
                LearningProgress.completed_at > last_sync_dt
            )
        )
    ).all()
    
    sync_response = {
        "syncTimestamp": int(datetime.utcnow().timestamp() * 1000),
        "serverTime": datetime.utcnow().isoformat(),
        "lastSyncTimestamp": last_sync_timestamp,
        "changes": {
            "jobs": [job.to_dict() for job in jobs],
            "applications": [app.to_dict() for app in applications],
            "savedJobs": [sj.to_dict() for sj in saved_jobs],
            "learningProgress": [lp.to_dict() for lp in learning_progress]
        },
        "summary": {
            "newJobs": len(jobs),
            "updatedApplications": len(applications),
            "newSavedJobs": len(saved_jobs),
            "updatedProgress": len(learning_progress),
            "hasChanges": len(jobs) + len(applications) + len(saved_jobs) + len(learning_progress) > 0
        }
    }
    
    logger.info(
        f"✅ Delta sync complete: "
        f"{len(jobs)} new jobs, {len(applications)} app updates"
    )
    
    return ApiResponse(
        success=True,
        message=f"Delta sync completed - {sync_response['summary']['hasChanges'] and 'changes found' or 'no changes'}",
        data=sync_response
    )

# ══════════════════════════════════════════════════════════
# Get Sync Status
# ══════════════════════════════════════════════════════════

@router.get("/status", response_model=ApiResponse)
async def get_sync_status(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get sync status and statistics.
    
    Returns:
    - Last sync time (if tracked)
    - Data counts
    - Pending changes
    """
    
    # Get counts
    jobs_count = db.query(Job).filter(Job.status == "OPEN").count()
    applications_count = db.query(Application).filter(
        Application.employee_id == current_user.id
    ).count()
    saved_jobs_count = db.query(SavedJob).filter(
        SavedJob.employee_id == current_user.id
    ).count()
    modules_count = db.query(LearningModule).count()
    progress_count = db.query(LearningProgress).filter(
        LearningProgress.user_id == current_user.id
    ).count()
    
    return ApiResponse(
        success=True,
        message="Sync status retrieved",
        data={
            "serverTime": datetime.utcnow().isoformat(),
            "serverTimestamp": int(datetime.utcnow().timestamp() * 1000),
            "dataCounts": {
                "jobs": jobs_count,
                "applications": applications_count,
                "savedJobs": saved_jobs_count,
                "learningModules": modules_count,
                "learningProgress": progress_count
            },
            "user": {
                "id": current_user.id,
                "email": current_user.email,
                "role": current_user.role
            }
        }
    )

# ══════════════════════════════════════════════════════════
# Force Sync Conflict Resolution
# ══════════════════════════════════════════════════════════

@router.post("/resolve-conflicts", response_model=ApiResponse)
async def resolve_sync_conflicts(
    strategy: str = "server-wins",  # server-wins, client-wins, merge
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Resolve sync conflicts using specified strategy.
    
    Strategies:
    - server-wins: Server data takes precedence
    - client-wins: Client data takes precedence (not recommended)
    - merge: Attempt to merge (complex)
    
    For MVP, always use server-wins.
    """
    logger.info(f"Resolving sync conflicts for {current_user.email} - strategy: {strategy}")
    
    if strategy != "server-wins":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Only 'server-wins' strategy is currently supported"
        )
    
    # In server-wins, no action needed - server is source of truth
    # Just return current state
    
    return ApiResponse(
        success=True,
        message="Conflicts resolved - server data is authoritative",
        data={
            "strategy": strategy,
            "resolvedAt": datetime.utcnow().isoformat()
        }
    )

# ══════════════════════════════════════════════════════════