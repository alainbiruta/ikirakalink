"""
Admin Panel Schemas
===================

Pydantic schemas for admin operations.
Place this in: app/schemas/admin.py
"""

from pydantic import BaseModel, Field
from typing import Optional, List
from datetime import datetime

# ══════════════════════════════════════════════════════════
# User Management Schemas
# ══════════════════════════════════════════════════════════

class UserSuspend(BaseModel):
    """Schema for suspending a user"""
    reason: str = Field(..., min_length=10, max_length=500)
    duration_days: Optional[int] = Field(None, description="Suspension duration in days, None for permanent")

class UserBan(BaseModel):
    """Schema for banning a user"""
    reason: str = Field(..., min_length=10, max_length=500)
    is_permanent: bool = Field(True, description="Permanent ban or temporary")

class UserEdit(BaseModel):
    """Schema for editing user by admin"""
    name: Optional[str] = None
    email: Optional[str] = None
    role: Optional[str] = Field(None, description="EMPLOYEE, EMPLOYER, ADMIN, MODERATOR")
    is_active: Optional[bool] = None
    is_verified: Optional[bool] = None

# ══════════════════════════════════════════════════════════
# Job Moderation Schemas
# ══════════════════════════════════════════════════════════

class JobModerate(BaseModel):
    """Schema for moderating a job"""
    status: str = Field(..., description="OPEN, CLOSED, CANCELLED, SUSPENDED")
    moderation_note: Optional[str] = Field(None, max_length=500)

class JobFeature(BaseModel):
    """Schema for featuring a job"""
    is_featured: bool
    featured_until: Optional[str] = None  # ISO format date

# ══════════════════════════════════════════════════════════
# Content Moderation Schemas
# ══════════════════════════════════════════════════════════

class ReviewModerate(BaseModel):
    """Schema for moderating a review"""
    is_approved: bool
    moderation_note: Optional[str] = Field(None, max_length=500)

# ══════════════════════════════════════════════════════════
# API Response
# ══════════════════════════════════════════════════════════

class AdminApiResponse(BaseModel):
    """Standard admin API response"""
    success: bool
    message: str
    data: Optional[dict] = None