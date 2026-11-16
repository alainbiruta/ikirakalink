"""
Job Schemas
============

Pydantic models for job API requests and responses.
"""

from pydantic import BaseModel, Field
from typing import List, Optional, Dict
from datetime import datetime
from enum import Enum

# ══════════════════════════════════════════════════════════
# Enums
# ══════════════════════════════════════════════════════════

class JobStatus(str, Enum):
    """Job status enumeration"""
    OPEN = "OPEN"
    FILLED = "FILLED"
    CLOSED = "CLOSED"
    CANCELLED = "CANCELLED"

class ApplicationStatus(str, Enum):
    """Application status enumeration"""
    PENDING = "PENDING"
    ACCEPTED = "ACCEPTED"
    REJECTED = "REJECTED"
    WITHDRAWN = "WITHDRAWN"

# ══════════════════════════════════════════════════════════
# Job Schemas
# ══════════════════════════════════════════════════════════

class JobBase(BaseModel):
    """Base job schema"""
    title: str = Field(..., min_length=5, max_length=2000)
    description: str = Field(..., min_length=5, max_length=50000)
    location: str = Field(..., min_length=3, max_length=2000)
    latitude: Optional[float] = Field(None, ge=-90, le=90)
    longitude: Optional[float] = Field(None, ge=-180, le=180)
    required_skills: List[str] = Field(default=[], max_items=2000)
    payment: float = Field(..., gt=0)
    currency: str = Field(default="RWF", max_length=10)
    duration: str = Field(..., max_length=100)
    category: str = Field(..., max_length=100)

class JobCreate(JobBase):
    """Schema for creating a new job (employer only)"""
    is_urgent: bool = False
    required_hours: Optional[int] = Field(None, ge=0, le=168)  # Max 1 week
    required_availability: Optional[Dict[str, List[str]]] = None
    # Example: {"days": ["monday", "tuesday"], "time": "9-17"}
    deadline: Optional[datetime] = None
    max_applicants: Optional[int] = Field(None, ge=0, le=1000)

class JobUpdate(BaseModel):
    """Schema for updating a job (employer only)"""
    title: Optional[str] = Field(None, min_length=50, max_length=2000)
    description: Optional[str] = Field(None, min_length=2000, max_length=5000)
    required_skills: Optional[List[str]] = Field(None, max_items=2000)
    payment: Optional[float] = Field(None, gt=0)
    duration: Optional[str] = Field(None, max_length=100)
    is_urgent: Optional[bool] = None
    status: Optional[JobStatus] = None
    deadline: Optional[datetime] = None
    max_applicants: Optional[int] = Field(None, ge=0, le=1000)

class JobResponse(BaseModel):
    """Schema for job response"""
    id: str
    title: str
    description: str
    employer_id: str
    employer_name: str
    location: str
    latitude: float
    longitude: float
    required_skills: List[str]
    payment: float
    currency: str
    duration: str
    is_urgent: bool
    required_hours: Optional[int]
    required_availability: Optional[Dict]
    category: str
    status: str
    deadline: Optional[datetime]
    total_applicants: int
    max_applicants: Optional[int]
    created_at: datetime
    updated_at: datetime
    # Optional match score (added dynamically)
    match_score: Optional[float] = None
    # Optional flags (for employee view)
    is_saved: Optional[bool] = None
    has_applied: Optional[bool] = None
    
    class Config:
        from_attributes = True

class JobListResponse(BaseModel):
    """Schema for job list response"""
    jobs: List[JobResponse]
    total: int
    page: int
    page_size: int
    has_more: bool

# ══════════════════════════════════════════════════════════
# Application Schemas
# ══════════════════════════════════════════════════════════

class ApplicationCreate(BaseModel):
    """Schema for creating an application"""
    message: Optional[str] = Field(None, max_length=1000)
    cover_letter: Optional[str] = Field(None, max_length=5000)

class ApplicationResponse(BaseModel):
    """Schema for application response"""
    id: str
    job_id: str
    employee_id: str
    status: str
    message: Optional[str]
    cover_letter: Optional[str]
    match_score: Optional[float]
    applied_at: datetime
    updated_at: datetime
    # Include job details when requested
    job: Optional[JobResponse] = None
    
    class Config:
        from_attributes = True

class ApplicationWithEmployee(BaseModel):
    """Application with employee details (for employer view)"""
    id: str
    status: str
    message: Optional[str]
    match_score: Optional[float]
    applied_at: datetime
    employee: Dict  # Employee profile summary
    
    class Config:
        from_attributes = True

# ══════════════════════════════════════════════════════════
# API Response Wrappers
# ══════════════════════════════════════════════════════════

class ApiResponse(BaseModel):
    """Standard API response wrapper"""
    success: bool
    message: str
    data: Optional[dict] = None

class JobApiResponse(BaseModel):
    """Job-specific API response"""
    success: bool
    message: str
    data: Optional[JobResponse] = None

class JobListApiResponse(BaseModel):
    """Job list API response"""
    success: bool
    message: str
    data: Optional[JobListResponse] = None

class ApplicationApiResponse(BaseModel):
    """Application API response"""
    success: bool
    message: str
    data: Optional[ApplicationResponse] = None

# ══════════════════════════════════════════════════════════
# Search & Filter Schemas
# ══════════════════════════════════════════════════════════

class JobSearchFilters(BaseModel):
    """Schema for job search filters"""
    query: Optional[str] = None  # Search in title/description
    category: Optional[str] = None
    min_payment: Optional[float] = None
    max_payment: Optional[float] = None
    location: Optional[str] = None
    max_distance_km: Optional[float] = Field(None, ge=1, le=100)
    is_urgent: Optional[bool] = None
    skills: Optional[List[str]] = None
    status: Optional[JobStatus] = JobStatus.OPEN
    
    # Pagination
    page: int = Field(1, ge=1)
    page_size: int = Field(20, ge=1, le=100)
    
    # Sorting
    sort_by: str = Field("match_score", pattern="^(match_score|created_at|payment|deadline)$")
    sort_order: str = Field("desc", pattern="^(asc|desc)$")

# ══════════════════════════════════════════════════════════