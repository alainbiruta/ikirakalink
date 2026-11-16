"""
User Endpoints
==============

API routes for user profile management.
Enhanced with role-specific features.
"""

from fastapi import APIRouter, Depends, HTTPException, status
from sqlalchemy.orm import Session
import logging

from app.database import get_db
from app.dependencies import (
    get_current_user,
    get_current_employee,
    get_current_employer
)
from app.models.user import User
from app.schemas.user import (
    UserResponse,
    UserUpdate,
    SkillsUpdate,
    AvailabilityUpdate,
    UserApiResponse,
    ApiResponse
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# Common User Profile Endpoints
# ══════════════════════════════════════════════════════════

@router.get("/me", response_model=UserApiResponse)
async def get_current_user_profile(
    current_user: User = Depends(get_current_user)
):
    """
    Get current user's profile.
    Works for all roles.
    """
    logger.info(f"Fetching profile for user: {current_user.email} (role: {current_user.role})")
    
    # Ensure full_name is not None
    if current_user.full_name is None:
        current_user.full_name = ""
    
    return UserApiResponse(
        success=True,
        message="Profile retrieved successfully",
        data=UserResponse.from_orm(current_user)
    )

@router.put("/me", response_model=UserApiResponse)
async def update_current_user_profile(
    user_update: UserUpdate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Update current user's profile.
    Works for all roles.
    """
    logger.info(f"Updating profile for user: {current_user.email}")
    
    # Update only provided fields
    update_data = user_update.dict(exclude_unset=True)
    
    for field, value in update_data.items():
        setattr(current_user, field, value)
    
    # Ensure full_name is not None
    if current_user.full_name is None:
        current_user.full_name = ""
    
    db.commit()
    db.refresh(current_user)
    
    logger.info(f"✅ Profile updated for user: {current_user.email}")
    
    return UserApiResponse(
        success=True,
        message="Profile updated successfully",
        data=UserResponse.from_orm(current_user)
    )

# ══════════════════════════════════════════════════════════
# Employee-Only Endpoints
# ══════════════════════════════════════════════════════════

@router.put("/me/skills", response_model=ApiResponse)
async def update_user_skills(
    skills_update: SkillsUpdate,
    current_user: User = Depends(get_current_employee),
    db: Session = Depends(get_db)              # ← inject correctly
):
    logger.info(f"Updating skills for employee: {current_user.email}")
    current_user.skills = skills_update.skills
    db.add(current_user)
    db.commit()
    db.refresh(current_user)
    logger.info(f"✅ Skills saved: {skills_update.skills}")
    return ApiResponse(
        success=True,
        message=f"Skills updated. Total: {len(skills_update.skills)}",
        data={"skills": current_user.skills}
    )
    
   

@router.get("/me/skills", response_model=ApiResponse)
async def get_user_skills(
    current_user: User = Depends(get_current_employee)  # ✅ Employee only
):
    """
    Get employee's skills list.
    
    **Employee only**
    """
    return ApiResponse(
        success=True,
        message=f"Skills retrieved. Total: {len(current_user.skills or [])}",
        data={"skills": current_user.skills or []}
    )

@router.put("/me/availability", response_model=ApiResponse)
async def update_availability(
    availability: AvailabilityUpdate,
    current_user: User = Depends(get_current_employee),  # ✅ Employee only
    db: Session = Depends(get_db)
):
    """
    Update employee's availability schedule.
    
    **Employee only**
    
    Example:
    ```json
    {
      "available_hours": {
        "monday": ["9-17"],
        "tuesday": ["14-22"],
        "wednesday": ["9-17"],
        "thursday": ["9-17"],
        "friday": ["9-14"]
      }
    }
    ```
    """
    logger.info(f"Updating availability for employee: {current_user.email}")
    
    current_user.available_hours = availability.available_hours
    db.commit()
    
    return ApiResponse(
        success=True,
        message="Availability updated successfully",
        data={"availableHours": availability.available_hours}
    )

# ══════════════════════════════════════════════════════════
# Employer-Only Endpoints
# ══════════════════════════════════════════════════════════

@router.put("/me/company", response_model=ApiResponse)
async def update_company_info(
    company_name: str,
    company_description: str = "",
    current_user: User = Depends(get_current_employer),  # ✅ Employer only
    db: Session = Depends(get_db)
):
    """
    Update employer's company information.
    
    **Employer only**
    """
    logger.info(f"Updating company info for employer: {current_user.email}")
    
    current_user.company_name = company_name
    current_user.company_description = company_description
    db.commit()
    
    return ApiResponse(
        success=True,
        message="Company information updated successfully",
        data={
            "companyName": company_name,
            "companyDescription": company_description
        }
    )

# ══════════════════════════════════════════════════════════
# Common Endpoints
# ══════════════════════════════════════════════════════════

@router.put("/me/profile-picture", response_model=ApiResponse)
async def update_profile_picture(
    profile_picture_url: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Update user's profile picture URL"""
    logger.info(f"Updating profile picture for user: {current_user.email}")
    
    current_user.profile_picture_url = profile_picture_url
    db.commit()
    
    return ApiResponse(
        success=True,
        message="Profile picture updated successfully",
        data={"profilePictureUrl": profile_picture_url}
    )

@router.get("/me/stats", response_model=ApiResponse)
async def get_user_stats(
    current_user: User = Depends(get_current_user)
):
    """Get user statistics"""
    return ApiResponse(
        success=True,
        message="Statistics retrieved successfully",
        data={
            "role": current_user.role,
            "rating": current_user.rating,
            "completedJobs": current_user.completed_jobs,
            "totalSkills": len(current_user.skills or []) if current_user.is_employee() else 0,
            "accountAge": (
                current_user.updated_at - current_user.created_at
            ).days if current_user.updated_at and current_user.created_at else 0
        }
    )

# ══════════════════════════════════════════════════════════
