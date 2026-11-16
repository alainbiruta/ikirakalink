"""
Work Confirmation and Payment Schemas
======================================

Pydantic models for API requests and responses.
Place this in: app/schemas/payment.py
"""

from pydantic import BaseModel, Field, validator
from typing import Optional, List
from datetime import datetime
from decimal import Decimal

# ══════════════════════════════════════════════════════════
# Work Confirmation Schemas
# ══════════════════════════════════════════════════════════

class WorkConfirmationCreate(BaseModel):
    """Schema for creating a work confirmation"""
    job_id: str
    application_id: str
    hours_worked: Optional[Decimal] = Field(None, gt=0, description="Hours worked")
    actual_payment: Optional[Decimal] = Field(None, gt=0, description="Actual payment amount")
    completion_notes: Optional[str] = Field(None, max_length=1000)
    evidence_photos: Optional[List[str]] = Field(None, description="URLs of evidence photos")

class WorkConfirmationConfirm(BaseModel):
    """Schema for confirming work completion"""
    hours_worked: Optional[Decimal] = Field(None, gt=0)
    actual_payment: Optional[Decimal] = Field(None, gt=0)
    completion_notes: Optional[str] = Field(None, max_length=1000)

class WorkConfirmationDispute(BaseModel):
    """Schema for raising a dispute"""
    dispute_reason: str = Field(..., min_length=10, max_length=500)

class WorkConfirmationResolveDispute(BaseModel):
    """Schema for resolving a dispute"""
    resolution: str = Field(..., min_length=10, max_length=500)

class WorkConfirmationDetail(BaseModel):
    """Detailed work confirmation response"""
    id: str
    job_id: str
    application_id: str
    employee_id: str
    employer_id: str
    employer_confirmed: bool
    employer_confirmed_at: Optional[str]
    employee_confirmed: bool
    employee_confirmed_at: Optional[str]
    hours_worked: Optional[float]
    actual_payment: Optional[float]
    currency: str
    completion_notes: Optional[str]
    evidence_photos: List[str]
    has_dispute: bool
    dispute_reason: Optional[str]
    dispute_raised_by: Optional[str]
    dispute_raised_at: Optional[str]
    dispute_resolved: bool
    dispute_resolution: Optional[str]
    dispute_resolved_at: Optional[str]
    status: str
    completed_at: Optional[str]
    created_at: str
    updated_at: str

# ══════════════════════════════════════════════════════════
# Payment Schemas
# ══════════════════════════════════════════════════════════

class PaymentCreate(BaseModel):
    """Schema for creating a payment record"""
    job_id: str
    work_confirmation_id: Optional[str] = None
    employee_id: str
    amount: Decimal = Field(..., gt=0)
    currency: str = Field("RWF", max_length=3)
    payment_method: Optional[str] = Field(None, max_length=50)
    due_date: Optional[str] = None  # ISO format
    platform_fee_percent: Decimal = Field(0, ge=0, le=100)
    payment_notes: Optional[str] = Field(None, max_length=500)

class PaymentUpdate(BaseModel):
    """Schema for updating payment status"""
    status: Optional[str] = Field(None, description="PENDING, PROCESSING, PAID, FAILED, REFUNDED, DISPUTED")
    payment_method: Optional[str] = None
    transaction_id: Optional[str] = None
    payment_reference: Optional[str] = None
    paid_at: Optional[str] = None  # ISO format
    failure_reason: Optional[str] = None
    payment_notes: Optional[str] = None

class PaymentDetail(BaseModel):
    """Detailed payment response"""
    id: str
    job_id: str
    work_confirmation_id: Optional[str]
    employee_id: str
    employer_id: str
    amount: float
    currency: str
    payment_method: Optional[str]
    status: str
    transaction_id: Optional[str]
    payment_reference: Optional[str]
    due_date: Optional[str]
    paid_at: Optional[str]
    platform_fee_percent: float
    platform_fee_amount: float
    net_amount: Optional[float]
    payment_notes: Optional[str]
    failure_reason: Optional[str]
    created_at: str
    updated_at: str

class EarningsSummary(BaseModel):
    """User's earnings summary"""
    user_id: str
    total_earnings: float  # Total paid
    pending_payments: float  # Pending + Processing
    completed_jobs: int
    average_payment: float
    currency: str
    recent_payments: List[PaymentDetail]

# ══════════════════════════════════════════════════════════
# API Response Wrapper
# ══════════════════════════════════════════════════════════

class WorkPaymentApiResponse(BaseModel):
    """Standard API response"""
    success: bool
    message: str
    data: Optional[dict] = None