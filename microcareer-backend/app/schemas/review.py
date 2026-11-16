"""
Review Schemas
==============

Pydantic models for review API requests and responses.
Place this in: app/schemas/review.py
"""

from pydantic import BaseModel, Field, validator
from typing import Optional
from datetime import datetime

# ══════════════════════════════════════════════════════════
# Request Schemas
# ══════════════════════════════════════════════════════════

class CategoryRatings(BaseModel):
    """Category-specific ratings (all optional, 1-5)"""
    quality: Optional[int] = Field(None, ge=1, le=5, description="Work quality rating")
    punctuality: Optional[int] = Field(None, ge=1, le=5, description="Timeliness rating")
    communication: Optional[int] = Field(None, ge=1, le=5, description="Communication rating")
    professionalism: Optional[int] = Field(None, ge=1, le=5, description="Professionalism rating")

class ReviewCreate(BaseModel):
    """Schema for creating a new review"""
    job_id: str = Field(..., description="ID of the job this review is for")
    reviewee_id: str = Field(..., description="ID of the user being reviewed")
    overall_rating: int = Field(..., ge=1, le=5, description="Overall rating (1-5 stars)")
    category_ratings: Optional[CategoryRatings] = Field(None, description="Optional category ratings")
    comment: Optional[str] = Field(None, max_length=1000, description="Review comment")
    is_public: bool = Field(True, description="Whether review is publicly visible")
    
    @validator('comment', pre=True)
    def comment_not_empty(cls, v):
     if v == '':
        return None
     return v

class ReviewUpdate(BaseModel):
    """Schema for updating an existing review"""
    overall_rating: Optional[int] = Field(None, ge=1, le=5)
    category_ratings: Optional[CategoryRatings] = None
    comment: Optional[str] = Field(None, max_length=1000)
    is_public: Optional[bool] = None

class ReviewResponse(BaseModel):
    """Schema for responding to a review (by reviewee)"""
    response: str = Field(..., max_length=500, description="Response text")
    
    @validator('response')
    def response_not_empty(cls, v):
        if v.strip() == '':
            raise ValueError('Response cannot be empty')
        return v

class ReviewFlag(BaseModel):
    """Schema for flagging a review"""
    reason: str = Field(..., max_length=200, description="Reason for flagging")

# ══════════════════════════════════════════════════════════
# Response Schemas
# ══════════════════════════════════════════════════════════

class CategoryRatingsResponse(BaseModel):
    """Category ratings response"""
    quality: Optional[int] = None
    punctuality: Optional[int] = None
    communication: Optional[int] = None
    professionalism: Optional[int] = None

class ReviewerInfo(BaseModel):
    """Reviewer information"""
    id: str
    name: str
    rating: float
    review_count: int

class ReviewDetail(BaseModel):
    """Detailed review response"""
    id: str
    job_id: str
    reviewer_id: str
    reviewee_id: str
    reviewer_info: Optional[ReviewerInfo] = None
    overall_rating: int
    category_ratings: CategoryRatingsResponse
    comment: Optional[str] = None
    is_public: bool
    response: Optional[str] = None
    response_at: Optional[str] = None
    is_flagged: bool
    is_approved: bool
    created_at: str
    updated_at: str

class ReviewSummary(BaseModel):
    """Summary of user's reviews"""
    user_id: str
    average_rating: float
    total_reviews: int
    rating_distribution: dict  # {"5": 10, "4": 5, "3": 2, "2": 1, "1": 0}
    category_averages: CategoryRatingsResponse

class UserReputation(BaseModel):
    """User reputation response"""
    user_id: str
    user_name: str
    overall_rating: float
    total_reviews: int
    completed_jobs: int
    rating_distribution: dict
    category_averages: CategoryRatingsResponse
    recent_reviews: list[ReviewDetail]

# ══════════════════════════════════════════════════════════
# API Response Wrapper
# ══════════════════════════════════════════════════════════

class ReviewApiResponse(BaseModel):
    """Standard API response for review operations"""
    success: bool
    message: str
    data: Optional[dict] = None