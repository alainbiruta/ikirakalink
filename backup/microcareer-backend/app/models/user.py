"""
User Model
==========

SQLAlchemy model for users table.
Enhanced with role-based access control.
"""

from sqlalchemy import Column, String, Float, Integer, Boolean, ARRAY
from sqlalchemy.dialects.postgresql import TIMESTAMP, JSONB
from datetime import datetime

from app.database import Base

class User(Base):
    
    """
    User model - stores user profile information.
    
    Supports three roles:
    - EMPLOYEE: Job seekers
    - EMPLOYER: Job posters
    - ADMIN: Platform administrators
    """
    __tablename__ = "users"
    def _normalized_role(self):
        return (self.role or "").strip().upper()

    def is_employee(self):
        return self._normalized_role() == "EMPLOYEE"

    def is_employer(self):
        return self._normalized_role() == "EMPLOYER"

    def is_admin(self):
        return self._normalized_role() == "ADMIN"
    # Primary key
    id = Column(String, primary_key=True, index=True)
    
    # Basic info
    email = Column(String, unique=True, nullable=False, index=True)
    full_name = Column(String, nullable=False)
    phone_number = Column(String, nullable=True)
    hashed_password = Column(String, nullable=False)

    # Role
    role = Column(String, default="EMPLOYEE", nullable=False, index=True)
    # Values: 'EMPLOYEE', 'EMPLOYER', 'ADMIN'
    
    # Profile
    profile_picture_url = Column(String, nullable=True)
    location = Column(String, nullable=True)
    latitude = Column(Float, nullable=True)
    longitude = Column(Float, nullable=True)
    
    # Performance metrics
    rating = Column(Float, default=0.0)
    completed_jobs = Column(Integer, default=0)
    
    # Skills (for employees)
    skills = Column(ARRAY(String), default=[])
    
    # Availability (for employees)
    available_hours = Column(JSONB, nullable=True)
    
    # Company info (for employers)
    company_name = Column(String, nullable=True)
    company_description = Column(String, nullable=True)

    # ✅ FCM push notification token — updated by Android after login
    fcm_token = Column(String, nullable=True)

    # Timestamps
    created_at = Column(
        TIMESTAMP(timezone=True),
        default=datetime.utcnow,
        nullable=False
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
        return f"<User {self.email} ({self.role})>"
    
    def to_dict(self):
        return {
            "id": self.id,
            "email": self.email,
            "fullName": self.full_name,
            "phoneNumber": self.phone_number,
            "role": self.role,
            "profilePictureUrl": self.profile_picture_url,
            "location": self.location,
            "latitude": self.latitude,
            "longitude": self.longitude,
            "rating": self.rating,
            "completedJobs": self.completed_jobs,
            "skills": self.skills or [],
            "availableHours": self.available_hours,
            "companyName": self.company_name,
            "companyDescription": self.company_description,
            "createdAt": int(self.created_at.timestamp() * 1000),
            "updatedAt": int(self.updated_at.timestamp() * 1000),
            "isSynced": self.is_synced,
        }
    


