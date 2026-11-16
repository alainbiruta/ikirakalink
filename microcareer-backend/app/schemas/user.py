"""
User Schemas
============

Pydantic models for user API requests and responses.
Enhanced with role-based fields.
"""

from pydantic import BaseModel, EmailStr, Field
from typing import List, Optional, Dict
from datetime import datetime
from enum import Enum
from pydantic import BaseModel, EmailStr, Field
from typing import Optional, List

# ══════════════════════════════════════════════════════════
# Enums
# ══════════════════════════════════════════════════════════

class UserRole(str, Enum):
    """User role enumeration"""
    EMPLOYEE = "EMPLOYEE"
    EMPLOYER = "EMPLOYER"
    ADMIN = "ADMIN"

# ══════════════════════════════════════════════════════════
# User Schemas
# ══════════════════════════════════════════════════════════

class UserBase(BaseModel):
    """Base user schema with common fields"""
    email: EmailStr
    full_name: str = Field(default="", min_length=0, max_length=100)
    phone_number: Optional[str] = None
    location: Optional[str] = None
    latitude: Optional[float] = None
    longitude: Optional[float] = None

class UserCreate(UserBase):
    """Schema for creating a new user"""
    password: str = Field(..., min_length=6)
    role: UserRole = UserRole.EMPLOYEE  # ✅ NEW: Role during registration

class UserUpdate(BaseModel):
    """Schema for updating user profile"""
    full_name: Optional[str] = Field(None, min_length=0, max_length=100)
    phone_number: Optional[str] = None
    location: Optional[str] = None
    latitude: Optional[float] = Field(None, ge=-90, le=90)
    longitude: Optional[float] = Field(None, ge=-180, le=180)
    profile_picture_url: Optional[str] = None
    skills: Optional[List[str]] = None
    # ✅ NEW: Employee fields
    available_hours: Optional[Dict[str, List[str]]] = None
    # ✅ NEW: Employer fields
    company_name: Optional[str] = None
    company_description: Optional[str] = None

class UserResponse(BaseModel):
    """Schema for user response"""
    id: str
    email: EmailStr
    full_name: str = ""
    phone_number: Optional[str] = None
    role: str  # ✅ NEW: User role
    profile_picture_url: Optional[str] = None
    location: Optional[str] = None
    latitude: Optional[float] = None
    longitude: Optional[float] = None
    rating: float = 0.0
    completed_jobs: int = 0
    skills: List[str] = []
    # ✅ NEW: Employee fields
    available_hours: Optional[Dict[str, List[str]]] = None
    # ✅ NEW: Employer fields
    company_name: Optional[str] = None
    company_description: Optional[str] = None
    created_at: datetime
    updated_at: datetime
    
    class Config:
        from_attributes = True

class UserPublic(BaseModel):
    """Public user info (for other users to see)"""
    id: str
    full_name: str = "Anonymous"
    location: Optional[str] = None
    rating: float = 0.0
    completed_jobs: int = 0
    role: str  # ✅ NEW: Show role
    # ✅ NEW: Employer info
    company_name: Optional[str] = None
    
    class Config:
        from_attributes = True

# ══════════════════════════════════════════════════════════
# Skill Schemas
# ══════════════════════════════════════════════════════════

class SkillsUpdate(BaseModel):
    """Schema for updating user skills"""
    skills: List[str] = Field(..., min_items=0, max_items=50)

class AvailabilityUpdate(BaseModel):
    """Schema for updating employee availability"""
    available_hours: Dict[str, List[str]]
    # Example: {"monday": ["9-17"], "tuesday": ["14-22"]}

# ══════════════════════════════════════════════════════════
# API Response Wrappers
# ══════════════════════════════════════════════════════════

class ApiResponse(BaseModel):
    """Standard API response wrapper"""
    success: bool
    message: str
    data: Optional[dict] = None

class UserApiResponse(BaseModel):
    """User-specific API response"""
    success: bool
    message: str
    data: Optional[UserResponse] = None

# ══════════════════════════════════════════════════════════