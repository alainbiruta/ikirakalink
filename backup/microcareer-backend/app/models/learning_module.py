"""
Learning Module Model
=====================

SQLAlchemy models for learning_modules and learning_progress tables.
"""

from sqlalchemy import Column, String, Integer, Float, Boolean, ARRAY, ForeignKey
from sqlalchemy.dialects.postgresql import TIMESTAMP
from datetime import datetime

from app.database import Base

# ══════════════════════════════════════════════════════════
# Learning Module Model
# ══════════════════════════════════════════════════════════

class LearningModule(Base):
    """
    Learning Module model - stores educational content.
    
    Corresponds to Android LearningModuleEntity.
    """
    __tablename__ = "learning_modules"
    
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Content
    title = Column(String, nullable=False, index=True)
    description = Column(String, nullable=False)
    skill_name = Column(String, nullable=False, index=True)
    
    # Metadata
    duration = Column(Integer, nullable=False)  # minutes
    content_url = Column(String, nullable=True)
    content_type = Column(String, nullable=False)  # VIDEO, ARTICLE, INTERACTIVE
    difficulty = Column(String, nullable=False)  # BEGINNER, INTERMEDIATE, ADVANCED
    
    # Prerequisites
    prerequisites = Column(ARRAY(String), default=[])
    
    # Recommendation score (for sorting/filtering)
    recommendation_score = Column(Float, default=0.0, index=True)
    
    # Timestamps
    created_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False,
        index=True
    )
    
    # Sync status
    is_synced = Column(Boolean, default=True)
    
    def __repr__(self):
        return f"<LearningModule {self.title}>"
    
    def to_dict(self):
        """Convert model to dictionary"""
        return {
            "id": self.id,
            "title": self.title,
            "description": self.description,
            "skillName": self.skill_name,
            "duration": self.duration,
            "contentUrl": self.content_url,
            "contentType": self.content_type,
            "difficulty": self.difficulty,
            "prerequisites": self.prerequisites or [],
            "recommendationScore": self.recommendation_score,
            "createdAt": int(self.created_at.timestamp() * 1000),
            "isSynced": self.is_synced,
        }

# ══════════════════════════════════════════════════════════
# Learning Progress Model
# ══════════════════════════════════════════════════════════

class LearningProgress(Base):
    """
    Learning Progress model - tracks user progress on modules.
    
    Separate table for progress tracking (normalized design).
    One progress record per user per module.
    """
    __tablename__ = "learning_progress"
    
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Foreign keys
    module_id = Column(String, ForeignKey('learning_modules.id'), nullable=False, index=True)
    user_id = Column(String, ForeignKey('users.id'), nullable=False, index=True)
    
    # Progress tracking
    progress_percent = Column(Integer, default=0, nullable=False)  # 0-100
    is_completed = Column(Boolean, default=False, nullable=False)
    
    # Timestamps
    started_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False
    )
    completed_at = Column(TIMESTAMP(timezone=True), nullable=True)
    last_accessed_at = Column(TIMESTAMP(timezone=True), nullable=True)
    
    # Sync status
    is_synced = Column(Boolean, default=True)
    
    def __repr__(self):
        return f"<LearningProgress {self.module_id} - {self.progress_percent}%>"
    
    def to_dict(self):
        """Convert model to dictionary"""
        return {
            "id": self.id,
            "moduleId": self.module_id,
            "userId": self.user_id,
            "progressPercent": self.progress_percent,
            "isCompleted": self.is_completed,
            "startedAt": int(self.started_at.timestamp() * 1000) if self.started_at else None,
            "completedAt": int(self.completed_at.timestamp() * 1000) if self.completed_at else None,
            "lastAccessedAt": int(self.last_accessed_at.timestamp() * 1000) if self.last_accessed_at else None,
            "isSynced": self.is_synced,
        }