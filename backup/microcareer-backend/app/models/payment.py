"""
Work Confirmation and Payment Models
=====================================

SQLAlchemy models for work confirmations and payments.
Place this in: app/models/payment.py
"""

from sqlalchemy import Column, String, Boolean, Numeric, Text, ForeignKey, CheckConstraint, UniqueConstraint, ARRAY
from sqlalchemy.orm import relationship
from datetime import datetime
from app.database import Base
from app.models.mixins import TimestampMixin
import uuid

class WorkConfirmation(Base, TimestampMixin):
    """
    Work Confirmation model for tracking job completion.
    
    Features:
    - Dual confirmation (employer + employee must both confirm)
    - Work hours and payment tracking
    - Evidence photos
    - Dispute management
    - Auto status updates
    """
    
    __tablename__ = "work_confirmations"
    
    # Primary Key
    id = Column(String, primary_key=True, default=lambda: str(uuid.uuid4()))
    
    # Foreign Keys
    job_id = Column(String, ForeignKey("jobs.id", ondelete="CASCADE"), nullable=False)
    application_id = Column(String, ForeignKey("applications.id", ondelete="CASCADE"), nullable=False)
    employee_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    employer_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    
    # Confirmation Status
    employer_confirmed = Column(Boolean, default=False, nullable=False)
    employer_confirmed_at = Column(String, nullable=True)  # ISO format
    employee_confirmed = Column(Boolean, default=False, nullable=False)
    employee_confirmed_at = Column(String, nullable=True)  # ISO format
    
    # Work Details
    hours_worked = Column(Numeric(10, 2), nullable=True)
    actual_payment = Column(Numeric(10, 2), nullable=True)
    currency = Column(String(3), default="RWF", nullable=False)
    
    # Evidence
    completion_notes = Column(Text, nullable=True)
    evidence_photos = Column(ARRAY(String), nullable=True)  # Array of URLs
    
    # Dispute
    has_dispute = Column(Boolean, default=False, nullable=False)
    dispute_reason = Column(Text, nullable=True)
    dispute_raised_by = Column(String, ForeignKey("users.id"), nullable=True)
    dispute_raised_at = Column(String, nullable=True)  # ISO format
    dispute_resolved = Column(Boolean, default=False, nullable=False)
    dispute_resolution = Column(Text, nullable=True)
    dispute_resolved_at = Column(String, nullable=True)  # ISO format
    
    # Status
    status = Column(String(50), default="PENDING", nullable=False)
    completed_at = Column(String, nullable=True)  # ISO format
    
    # Relationships
    job = relationship("Job", backref="work_confirmations")
    application = relationship("Application", backref="work_confirmation", uselist=False)
    employee = relationship("User", foreign_keys=[employee_id], backref="work_confirmations_as_employee")
    employer = relationship("User", foreign_keys=[employer_id], backref="work_confirmations_as_employer")
    payments = relationship("Payment", back_populates="work_confirmation")
    
    # Constraints
    __table_args__ = (
        CheckConstraint('hours_worked IS NULL OR hours_worked > 0', name='check_positive_hours'),
        CheckConstraint('actual_payment IS NULL OR actual_payment > 0', name='check_positive_payment'),
        UniqueConstraint('application_id', name='one_confirmation_per_application'),
    )
    
    def to_dict(self):
        """Convert to dictionary"""
        return {
            "id": self.id,
            "jobId": self.job_id,
            "applicationId": self.application_id,
            "employeeId": self.employee_id,
            "employerId": self.employer_id,
            "employerConfirmed": self.employer_confirmed,
            "employerConfirmedAt": self.employer_confirmed_at,
            "employeeConfirmed": self.employee_confirmed,
            "employeeConfirmedAt": self.employee_confirmed_at,
            "hoursWorked": float(self.hours_worked) if self.hours_worked else None,
            "actualPayment": float(self.actual_payment) if self.actual_payment else None,
            "currency": self.currency,
            "completionNotes": self.completion_notes,
            "evidencePhotos": self.evidence_photos or [],
            "hasDispute": self.has_dispute,
            "disputeReason": self.dispute_reason,
            "disputeRaisedBy": self.dispute_raised_by,
            "disputeRaisedAt": self.dispute_raised_at,
            "disputeResolved": self.dispute_resolved,
            "disputeResolution": self.dispute_resolution,
            "disputeResolvedAt": self.dispute_resolved_at,
            "status": self.status,
            "completedAt": self.completed_at,
            "createdAt": self.created_at,
            "updatedAt": self.updated_at
        }
    
    def __repr__(self):
        return f"<WorkConfirmation {self.id}: {self.status}>"


class Payment(Base, TimestampMixin):
    """
    Payment model for tracking payments.
    
    Features:
    - Multiple payment methods
    - Status tracking (PENDING, PAID, FAILED, etc.)
    - Platform fee calculation
    - Transaction references
    - Due dates
    """
    
    __tablename__ = "payments"
    
    # Primary Key
    id = Column(String, primary_key=True, default=lambda: str(uuid.uuid4()))
    
    # Foreign Keys
    job_id = Column(String, ForeignKey("jobs.id", ondelete="CASCADE"), nullable=False)
    work_confirmation_id = Column(String, ForeignKey("work_confirmations.id", ondelete="SET NULL"), nullable=True)
    employee_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    employer_id = Column(String, ForeignKey("users.id", ondelete="CASCADE"), nullable=False)
    
    # Payment Details
    amount = Column(Numeric(10, 2), nullable=False)
    currency = Column(String(3), default="RWF", nullable=False)
    
    # Payment Method
    payment_method = Column(String(50), nullable=True)
    
    # Payment Status
    status = Column(String(50), default="PENDING", nullable=False)
    
    # Transaction Details
    transaction_id = Column(String(100), nullable=True)
    payment_reference = Column(String(100), nullable=True)
    
    # Payment Dates
    due_date = Column(String, nullable=True)  # ISO format
    paid_at = Column(String, nullable=True)  # ISO format
    
    # Platform Fee
    platform_fee_percent = Column(Numeric(5, 2), default=0, nullable=False)
    platform_fee_amount = Column(Numeric(10, 2), default=0, nullable=False)
    net_amount = Column(Numeric(10, 2), nullable=True)
    
    # Notes
    payment_notes = Column(Text, nullable=True)
    failure_reason = Column(Text, nullable=True)
    
    # Relationships
    job = relationship("Job", backref="payments")
    work_confirmation = relationship("WorkConfirmation", back_populates="payments")
    employee = relationship("User", foreign_keys=[employee_id], backref="payments_received")
    employer = relationship("User", foreign_keys=[employer_id], backref="payments_made")
    
    # Constraints
    __table_args__ = (
        CheckConstraint('amount > 0', name='check_positive_amount'),
        CheckConstraint('platform_fee_percent >= 0 AND platform_fee_percent <= 100', name='check_valid_fee_percent'),
        CheckConstraint('net_amount IS NULL OR net_amount >= 0', name='check_positive_net_amount'),
    )
    
    def to_dict(self):
        """Convert to dictionary"""
        return {
            "id": self.id,
            "jobId": self.job_id,
            "workConfirmationId": self.work_confirmation_id,
            "employeeId": self.employee_id,
            "employerId": self.employer_id,
            "amount": float(self.amount),
            "currency": self.currency,
            "paymentMethod": self.payment_method,
            "status": self.status,
            "transactionId": self.transaction_id,
            "paymentReference": self.payment_reference,
            "dueDate": self.due_date,
            "paidAt": self.paid_at,
            "platformFeePercent": float(self.platform_fee_percent),
            "platformFeeAmount": float(self.platform_fee_amount),
            "netAmount": float(self.net_amount) if self.net_amount else None,
            "paymentNotes": self.payment_notes,
            "failureReason": self.failure_reason,
            "createdAt": self.created_at,
            "updatedAt": self.updated_at
        }
    
    def __repr__(self):
        return f"<Payment {self.id}: {self.amount} {self.currency} - {self.status}>"