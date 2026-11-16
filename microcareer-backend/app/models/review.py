"""
Review Model
============

SQLAlchemy model for reviews table.
Place this in: app/models/review.py
"""

from sqlalchemy import Column, String, Integer, Boolean, Text, ForeignKey, CheckConstraint, UniqueConstraint
from sqlalchemy.orm import relationship
from datetime import datetime
from app.database import Base
from app.models.mixins import TimestampMixin
import uuid

class Review(Base, TimestampMixin):
    """
    Review model for ratings and reputation system.
    
    Features:
    - Overall rating (1-5 stars, required)
    - Category ratings (quality, punctuality, communication, professionalism)
    - Text comments
    - Public/private visibility
    - Response from reviewee
    - Moderation flags
    """
    
    __tablename__ = "reviews"
    
    # Primary Key
    id = Column(String, primary_key=True, default=lambda: str(uuid.uuid4()))
    
    # Foreign Keys
    job_id = Column(String, ForeignKey("jobs.id", ondelete="CASCADE"), nullable=False)
    reviewer_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    reviewee_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    
    # Overall Rating (required, 1-5)
    overall_rating = Column(Integer, nullable=False)
    
    # Category Ratings (optional, 1-5 each)
    quality_rating = Column(Integer, nullable=True)
    punctuality_rating = Column(Integer, nullable=True)
    communication_rating = Column(Integer, nullable=True)
    professionalism_rating = Column(Integer, nullable=True)
    
    # Review Content
    comment = Column(Text, nullable=True)
    
    # Visibility
    is_public = Column(Boolean, default=True, nullable=False)
    
    # Response from reviewee
    response = Column(Text, nullable=True)
    response_at = Column(String, nullable=True)  # ISO format timestamp
    
    # Moderation
    is_flagged = Column(Boolean, default=False, nullable=False)
    flagged_reason = Column(Text, nullable=True)
    is_approved = Column(Boolean, default=True, nullable=False)
    
    # Relationships
    job = relationship("Job", back_populates="reviews")
    reviewer = relationship("User", foreign_keys=[reviewer_id], backref="reviews_given")
    reviewee = relationship("User", foreign_keys=[reviewee_id], backref="reviews_received")
    
    # Constraints
    __table_args__ = (
        CheckConstraint('overall_rating >= 1 AND overall_rating <= 5', name='check_overall_rating'),
        CheckConstraint('quality_rating IS NULL OR (quality_rating >= 1 AND quality_rating <= 5)', name='check_quality_rating'),
        CheckConstraint('punctuality_rating IS NULL OR (punctuality_rating >= 1 AND punctuality_rating <= 5)', name='check_punctuality_rating'),
        CheckConstraint('communication_rating IS NULL OR (communication_rating >= 1 AND communication_rating <= 5)', name='check_communication_rating'),
        CheckConstraint('professionalism_rating IS NULL OR (professionalism_rating >= 1 AND professionalism_rating <= 5)', name='check_professionalism_rating'),
        CheckConstraint('reviewer_id != reviewee_id', name='check_different_users'),
        UniqueConstraint('job_id', 'reviewer_id', name='one_review_per_job'),
    )
    
    def to_dict(self):
        """Convert review to dictionary"""
        return {
            "id": self.id,
            "jobId": self.job_id,
            "reviewerId": self.reviewer_id,
            "revieweeId": self.reviewee_id,
            "overallRating": self.overall_rating,
            "categoryRatings": {
                "quality": self.quality_rating,
                "punctuality": self.punctuality_rating,
                "communication": self.communication_rating,
                "professionalism": self.professionalism_rating
            },
            "comment": self.comment,
            "isPublic": self.is_public,
            "response": self.response,
            "responseAt": self.response_at,
            "isFlagged": self.is_flagged,
            "flaggedReason": self.flagged_reason,
            "isApproved": self.is_approved,
            "createdAt": self.created_at,
            "updatedAt": self.updated_at
        }
    
    def __repr__(self):
        return f"<Review {self.id}: {self.overall_rating}★ for user {self.reviewee_id}>"