"""
Job Model
=========

SQLAlchemy model for jobs table.
Enhanced with employer features and matching fields.
"""
from datetime import datetime, timezone
from sqlalchemy import Column, String, Float, Integer, Boolean, ARRAY, ForeignKey
from sqlalchemy.dialects.postgresql import TIMESTAMP, JSONB
from datetime import datetime
from sqlalchemy.orm import relationship
from app.database import Base
from .mixins import TimestampMixin

class Job(Base, TimestampMixin):
    """
    Job model - stores job listings.
    
    Enhanced features:
    - Urgency flag
    - Required availability
    - Application tracking
    - Match score storage
    """
    __tablename__ = "jobs"
    
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Job details
    title = Column(String, nullable=False, index=True)
    description = Column(String, nullable=False)
    
    # Employer info
    employer_id = Column(String, nullable=False, index=True)
    employer_name = Column(String, nullable=False)
    
    # Location
    location = Column(String, nullable=False, index=True)
    latitude = Column(Float, nullable=False)
    longitude = Column(Float, nullable=False)
    
    # Requirements & compensation
    required_skills = Column(ARRAY(String), default=[])
    payment = Column(Float, nullable=False)
    currency = Column(String, default="RWF")
    duration = Column(String, nullable=False)  # e.g., "2 hours", "1 day"
    reviews = relationship("Review", back_populates="job", cascade="all, delete-orphan")
    # ✅ NEW: Job urgency and requirements
    is_urgent = Column(Boolean, default=False, index=True)
    required_hours = Column(Integer, nullable=True)  # Hours per week/day
    required_availability = Column(JSONB, nullable=True)
    # Example: {"days": ["monday", "tuesday"], "time": "9-17"}
    
    # Classification
    category = Column(String, nullable=False, index=True)
    status = Column(
        String,
        default="OPEN",
        index=True
    )  # OPEN, FILLED, CLOSED, CANCELLED
    
    # Deadline
    deadline = Column(TIMESTAMP(timezone=True), nullable=True)
    
    # ✅ NEW: Application tracking
    total_applicants = Column(Integer, default=0)
    max_applicants = Column(Integer, nullable=True)  # Limit applications
    
    # Timestamps
    created_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False,
        index=True
    )
    updated_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        onupdate=datetime.utcnow,
        nullable=False
    )
    
    # Sync status
    is_synced = Column(Boolean, default=True)
    
    def __repr__(self):
        return f"<Job {self.title} by {self.employer_name}>"
    
    def to_dict(self, include_match_score=False, match_score=None):
        """Convert model to dictionary"""
        result = {
            "id": self.id,
            "title": self.title,
            "description": self.description,
            "employerId": self.employer_id,
            "employerName": self.employer_name,
            "location": self.location,
            "latitude": self.latitude,
            "longitude": self.longitude,
            "requiredSkills": self.required_skills or [],
            "payment": self.payment,
            "currency": self.currency,
            "duration": self.duration,
            "isUrgent": self.is_urgent,
            "requiredHours": self.required_hours,
            "requiredAvailability": self.required_availability,
            "category": self.category,
            "status": self.status,
            "deadline": int(self.deadline.timestamp() * 1000) if self.deadline else None,
            "totalApplicants": self.total_applicants,
            "maxApplicants": self.max_applicants,
            "createdAt": int(self.created_at.timestamp() * 1000),
            "updatedAt": int(self.updated_at.timestamp() * 1000),
            "isSynced": self.is_synced,
        }
        
        if include_match_score and match_score is not None:
            result["matchScore"] = match_score
        
        return result
    
    # ✅ Helper methods
    def can_accept_applications(self):
        """Check if job can still accept applications"""
        if self.status != "OPEN":
            return False
        if self.max_applicants and self.total_applicants >= self.max_applicants:
            return False
        if self.deadline and datetime.now(timezone.utc) > self.deadline:
            return False
        return True
    
    def is_owned_by(self, user_id: str):
        """Check if job belongs to user"""
        return self.employer_id == user_id


# ══════════════════════════════════════════════════════════
# Application Model
# ══════════════════════════════════════════════════════════

class Application(Base):
    """
    Application model - stores job applications.
    Links employees to jobs they've applied for.
    """
    __tablename__ = "applications"
    
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Foreign keys
    job_id = Column(String, ForeignKey('jobs.id'), nullable=False, index=True)
    employee_id = Column(String, ForeignKey('users.id'), nullable=False, index=True)
    
    # Application details
    status = Column(
        String,
        default="PENDING",
        nullable=False,
        index=True
    )  # PENDING, ACCEPTED, REJECTED, WITHDRAWN
    
    message = Column(String, nullable=True)  # Cover letter / message
    cover_letter = Column(String, nullable=True)
    
    # Match score at time of application
    match_score = Column(Float, nullable=True)
    
    # Timestamps
    applied_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False,
        index=True
    )
    updated_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        onupdate=datetime.utcnow,
        nullable=False
    )
    
    # Sync status
    is_synced = Column(Boolean, default=True)
    
    def __repr__(self):
        return f"<Application {self.id} - {self.status}>"
    
    def to_dict(self):
        """Convert model to dictionary"""
        return {
            "id": self.id,
            "jobId": self.job_id,
            "employeeId": self.employee_id,
            "status": self.status,
            "message": self.message,
            "coverLetter": self.cover_letter,
            "matchScore": self.match_score,
            "appliedAt": int(self.applied_at.timestamp() * 1000),
            "updatedAt": int(self.updated_at.timestamp() * 1000),
            "isSynced": self.is_synced,
        }


# ══════════════════════════════════════════════════════════
# Saved Job Model
# ══════════════════════════════════════════════════════════

class SavedJob(Base):
    """
    Saved job model - stores bookmarked jobs.
    Allows employees to save jobs for later.
    """
    __tablename__ = "saved_jobs"
    
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Foreign keys
    job_id = Column(String, ForeignKey('jobs.id'), nullable=False, index=True)
    employee_id = Column(String, ForeignKey('users.id'), nullable=False, index=True)
    
    # Timestamp
    saved_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False
    )
    
    # Sync status
    is_synced = Column(Boolean, default=True)
    
    def __repr__(self):
        return f"<SavedJob {self.job_id} by {self.employee_id}>"
    
    def to_dict(self):
        """Convert model to dictionary"""
        return {
            "id": self.id,
            "jobId": self.job_id,
            "employeeId": self.employee_id,
            "savedAt": int(self.saved_at.timestamp() * 1000),
            "isSynced": self.is_synced,
        }