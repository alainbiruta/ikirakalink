"""
Learning Module Schemas
=======================

Pydantic models for learning module API requests and responses.
"""

from pydantic import BaseModel, Field
from typing import List, Optional
from datetime import datetime

# ══════════════════════════════════════════════════════════
# Learning Module Schemas
# ══════════════════════════════════════════════════════════

class LearningModuleResponse(BaseModel):
    """Schema for learning module response"""
    id: str
    title: str
    description: str
    skill_name: str
    duration: int  # minutes
    content_url: Optional[str]
    content_type: str  # video, article, interactive, etc.
    difficulty: str  # beginner, intermediate, advanced
    prerequisites: List[str]
    recommendation_score: Optional[float]
    created_at: datetime
    
    class Config:
        from_attributes = True

class LearningProgressUpdate(BaseModel):
    """Schema for updating learning progress"""
    progress_percent: int = Field(..., ge=0, le=100)

class LearningModuleListResponse(BaseModel):
    """Schema for learning module list response"""
    modules: List[LearningModuleResponse]
    total: int
    page: int
    page_size: int
    has_more: bool

# ══════════════════════════════════════════════════════════
# API Response Wrappers
# ══════════════════════════════════════════════════════════

class ApiResponse(BaseModel):
    """Standard API response wrapper"""
    success: bool
    message: str
    data: Optional[dict] = None

# ══════════════════════════════════════════════════════════