"""
Work Confirmation & Payment Endpoints
======================================

API endpoints for work completion tracking and payment management.

Place this in: app/api/v1/work_payments.py
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_
from typing import List, Optional
import logging
from datetime import datetime, timedelta

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application
from app.models.payment import WorkConfirmation, Payment
from app.schemas.payment import (
    WorkConfirmationCreate,
    WorkConfirmationConfirm,
    WorkConfirmationDispute,
    WorkConfirmationResolveDispute,
    PaymentCreate,
    PaymentUpdate,
    EarningsSummary,
    WorkPaymentApiResponse
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# WORK CONFIRMATION ENDPOINTS
# ══════════════════════════════════════════════════════════

@router.post("/work-confirmations", response_model=WorkPaymentApiResponse, status_code=status.HTTP_201_CREATED)
async def create_work_confirmation(
    data: WorkConfirmationCreate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Create a work confirmation for a completed job.
    
    Either employer or employee can initiate.
    """
    logger.info(f"Creating work confirmation for job {data.job_id}")
    
    # Get job
    job = db.query(Job).filter(Job.id == data.job_id).first()
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Get application
    application = db.query(Application).filter(Application.id == data.application_id).first()
    if not application:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Application not found"
        )
    
    # Validate: Application must be ACCEPTED
    if application.status != "ACCEPTED":
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Can only confirm work for accepted applications"
        )
    
    # Validate: User must be either employer or employee
    is_employer = job.employer_id == current_user.id
    is_employee = application.employee_id == current_user.id
    
    if not (is_employer or is_employee):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized for this job"
        )
    
    # Check if confirmation already exists
    existing = db.query(WorkConfirmation).filter(
        WorkConfirmation.application_id == data.application_id
    ).first()
    
    if existing:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Work confirmation already exists for this application"
        )
    
    # Create work confirmation
    confirmation = WorkConfirmation(
        job_id=data.job_id,
        application_id=data.application_id,
        employee_id=application.employee_id,
        employer_id=job.employer_id,
        hours_worked=data.hours_worked,
        actual_payment=data.actual_payment,
        completion_notes=data.completion_notes,
        evidence_photos=data.evidence_photos
    )
    
    # Auto-confirm if creator is employer or employee
    if is_employer:
        confirmation.employer_confirmed = True
        confirmation.employer_confirmed_at = datetime.utcnow().isoformat()
        confirmation.status = "EMPLOYER_CONFIRMED"
    elif is_employee:
        confirmation.employee_confirmed = True
        confirmation.employee_confirmed_at = datetime.utcnow().isoformat()
        confirmation.status = "EMPLOYEE_CONFIRMED"
    
    db.add(confirmation)
    db.commit()
    db.refresh(confirmation)
    
    logger.info(f"✅ Work confirmation created: {confirmation.id}")
    
    return WorkPaymentApiResponse(
        success=True,
        message="Work confirmation created successfully",
        data=confirmation.to_dict()
    )


@router.get("/work-confirmations/my", response_model=WorkPaymentApiResponse)
async def get_my_work_confirmations(
    status_filter: Optional[str] = Query(None, description="Filter by status"),
    skip: int = Query(0, ge=0),
    limit: int = Query(10, ge=1, le=50),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get all work confirmations for current user (as employer or employee).
    """
    logger.info(f"Fetching work confirmations for user: {current_user.email}")
    
    # Build query
    query = db.query(WorkConfirmation).filter(
        or_(
            WorkConfirmation.employee_id == current_user.id,
            WorkConfirmation.employer_id == current_user.id
        )
    )
    
    if status_filter:
        query = query.filter(WorkConfirmation.status == status_filter)
    
    total = query.count()
    confirmations = query.order_by(WorkConfirmation.created_at.desc()).offset(skip).limit(limit).all()
    
    return WorkPaymentApiResponse(
        success=True,
        message=f"Found {total} work confirmations",
        data={
            "confirmations": [c.to_dict() for c in confirmations],
            "total": total,
            "skip": skip,
            "limit": limit
        }
    )


@router.get("/work-confirmations/{confirmation_id}", response_model=WorkPaymentApiResponse)
async def get_work_confirmation(
    confirmation_id: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Get a specific work confirmation"""
    confirmation = db.query(WorkConfirmation).filter(WorkConfirmation.id == confirmation_id).first()
    
    if not confirmation:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Work confirmation not found"
        )
    
    # Check permission
    if confirmation.employee_id != current_user.id and confirmation.employer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to view this confirmation"
        )
    
    return WorkPaymentApiResponse(
        success=True,
        message="Work confirmation retrieved",
        data=confirmation.to_dict()
    )


@router.post("/work-confirmations/{confirmation_id}/confirm", response_model=WorkPaymentApiResponse)
async def confirm_work(
    confirmation_id: str,
    data: WorkConfirmationConfirm,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Confirm work completion (employer or employee).
    
    When both parties confirm, status becomes COMPLETED.
    """
    logger.info(f"Confirming work: {confirmation_id}")
    
    confirmation = db.query(WorkConfirmation).filter(WorkConfirmation.id == confirmation_id).first()
    
    if not confirmation:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Work confirmation not found"
        )
    
    # Determine if user is employer or employee
    is_employer = confirmation.employer_id == current_user.id
    is_employee = confirmation.employee_id == current_user.id
    
    if not (is_employer or is_employee):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized for this confirmation"
        )
    
    # Update confirmation
    if is_employer:
        if confirmation.employer_confirmed:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="Employer has already confirmed"
            )
        confirmation.employer_confirmed = True
        confirmation.employer_confirmed_at = datetime.utcnow().isoformat()
        
    elif is_employee:
        if confirmation.employee_confirmed:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="Employee has already confirmed"
            )
        confirmation.employee_confirmed = True
        confirmation.employee_confirmed_at = datetime.utcnow().isoformat()
    
    # Update optional fields
    if data.hours_worked:
        confirmation.hours_worked = data.hours_worked
    if data.actual_payment:
        confirmation.actual_payment = data.actual_payment
    if data.completion_notes:
        confirmation.completion_notes = data.completion_notes
    
    # Check if both have confirmed
    if confirmation.employer_confirmed and confirmation.employee_confirmed:
        confirmation.status = "COMPLETED"
        confirmation.completed_at = datetime.utcnow().isoformat()
        logger.info(f"✅ Work fully confirmed: {confirmation_id}")
    elif confirmation.employer_confirmed:
        confirmation.status = "EMPLOYER_CONFIRMED"
    elif confirmation.employee_confirmed:
        confirmation.status = "EMPLOYEE_CONFIRMED"
    
    db.commit()
    db.refresh(confirmation)
    
    return WorkPaymentApiResponse(
        success=True,
        message="Work confirmed successfully",
        data=confirmation.to_dict()
    )


@router.post("/work-confirmations/{confirmation_id}/dispute", response_model=WorkPaymentApiResponse)
async def raise_dispute(
    confirmation_id: str,
    data: WorkConfirmationDispute,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Raise a dispute on work confirmation.
    Either party can raise a dispute.
    """
    logger.info(f"Raising dispute on confirmation: {confirmation_id}")
    
    confirmation = db.query(WorkConfirmation).filter(WorkConfirmation.id == confirmation_id).first()
    
    if not confirmation:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Work confirmation not found"
        )
    
    # Check permission
    if confirmation.employee_id != current_user.id and confirmation.employer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized for this confirmation"
        )
    
    # Check if already disputed
    if confirmation.has_dispute and not confirmation.dispute_resolved:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Dispute already active on this confirmation"
        )
    
    # Raise dispute
    confirmation.has_dispute = True
    confirmation.dispute_reason = data.dispute_reason
    confirmation.dispute_raised_by = current_user.id
    confirmation.dispute_raised_at = datetime.utcnow().isoformat()
    confirmation.status = "DISPUTED"
    
    db.commit()
    db.refresh(confirmation)
    
    logger.info(f"✅ Dispute raised on confirmation: {confirmation_id}")
    
    return WorkPaymentApiResponse(
        success=True,
        message="Dispute raised successfully",
        data=confirmation.to_dict()
    )


@router.post("/work-confirmations/{confirmation_id}/resolve-dispute", response_model=WorkPaymentApiResponse)
async def resolve_dispute(
    confirmation_id: str,
    data: WorkConfirmationResolveDispute,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Resolve a dispute (admin only for now).
    Future: Could allow mutual resolution.
    """
    logger.info(f"Resolving dispute on confirmation: {confirmation_id}")
    
    # TODO: Add admin role check
    # if current_user.role != "ADMIN":
    #     raise HTTPException(status_code=403, detail="Admin only")
    
    confirmation = db.query(WorkConfirmation).filter(WorkConfirmation.id == confirmation_id).first()
    
    if not confirmation:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Work confirmation not found"
        )
    
    if not confirmation.has_dispute:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="No active dispute on this confirmation"
        )
    
    # Resolve dispute
    confirmation.dispute_resolved = True
    confirmation.dispute_resolution = data.resolution
    confirmation.dispute_resolved_at = datetime.utcnow().isoformat()
    confirmation.status = "RESOLVED"
    
    db.commit()
    db.refresh(confirmation)
    
    logger.info(f"✅ Dispute resolved on confirmation: {confirmation_id}")
    
    return WorkPaymentApiResponse(
        success=True,
        message="Dispute resolved successfully",
        data=confirmation.to_dict()
    )

# ══════════════════════════════════════════════════════════
# Continue in Part 2...
# ══════════════════════════════════════════════════════════
"""
Payment Endpoints (Part 2)
===========================

Continue from Part 1...
Add these endpoints to the same file: app/api/v1/work_payments.py
"""

# ══════════════════════════════════════════════════════════
# PAYMENT ENDPOINTS
# ══════════════════════════════════════════════════════════

@router.post("/payments", response_model=WorkPaymentApiResponse, status_code=status.HTTP_201_CREATED)
async def create_payment(
    data: PaymentCreate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Create a payment record.
    
    Can be created by employer or system.
    """
    logger.info(f"Creating payment for job {data.job_id}")
    
    # Get job
    job = db.query(Job).filter(Job.id == data.job_id).first()
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Validate: User must be employer
    if job.employer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Only employer can create payment"
        )
    
    # Calculate platform fee and net amount
    platform_fee_amount = data.amount * (data.platform_fee_percent / 100)
    net_amount = data.amount - platform_fee_amount
    
    # Create payment
    payment = Payment(
        job_id=data.job_id,
        work_confirmation_id=data.work_confirmation_id,
        employee_id=data.employee_id,
        employer_id=current_user.id,
        amount=data.amount,
        currency=data.currency,
        payment_method=data.payment_method,
        due_date=data.due_date,
        platform_fee_percent=data.platform_fee_percent,
        platform_fee_amount=platform_fee_amount,
        net_amount=net_amount,
        payment_notes=data.payment_notes
    )
    
    db.add(payment)
    db.commit()
    db.refresh(payment)
    
    logger.info(f"✅ Payment created: {payment.id}")
    
    return WorkPaymentApiResponse(
        success=True,
        message="Payment created successfully",
        data=payment.to_dict()
    )


@router.get("/payments/my", response_model=WorkPaymentApiResponse)
async def get_my_payments(
    as_role: str = Query("employee", description="employee or employer"),
    status_filter: Optional[str] = Query(None),
    skip: int = Query(0, ge=0),
    limit: int = Query(10, ge=1, le=50),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get my payments (as employee or employer).
    """
    logger.info(f"Fetching payments for user: {current_user.email} as {as_role}")
    
    # Build query
    if as_role == "employee":
        query = db.query(Payment).filter(Payment.employee_id == current_user.id)
    elif as_role == "employer":
        query = db.query(Payment).filter(Payment.employer_id == current_user.id)
    else:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="as_role must be 'employee' or 'employer'"
        )
    
    if status_filter:
        query = query.filter(Payment.status == status_filter)
    
    total = query.count()
    payments = query.order_by(Payment.created_at.desc()).offset(skip).limit(limit).all()
    
    return WorkPaymentApiResponse(
        success=True,
        message=f"Found {total} payments",
        data={
            "payments": [p.to_dict() for p in payments],
            "total": total,
            "skip": skip,
            "limit": limit
        }
    )


@router.get("/payments/{payment_id}", response_model=WorkPaymentApiResponse)
async def get_payment(
    payment_id: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Get a specific payment"""
    payment = db.query(Payment).filter(Payment.id == payment_id).first()
    
    if not payment:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Payment not found"
        )
    
    # Check permission
    if payment.employee_id != current_user.id and payment.employer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Not authorized to view this payment"
        )
    
    return WorkPaymentApiResponse(
        success=True,
        message="Payment retrieved",
        data=payment.to_dict()
    )


@router.put("/payments/{payment_id}", response_model=WorkPaymentApiResponse)
async def update_payment_status(
    payment_id: str,
    data: PaymentUpdate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Update payment status.
    
    Employer can update payment details and status.
    """
    logger.info(f"Updating payment: {payment_id}")
    
    payment = db.query(Payment).filter(Payment.id == payment_id).first()
    
    if not payment:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Payment not found"
        )
    
    # Check permission: Only employer can update
    if payment.employer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Only employer can update payment"
        )
    
    # Update fields
    if data.status:
        payment.status = data.status
        
        # If marking as PAID, set paid_at timestamp
        if data.status == "PAID" and not payment.paid_at:
            payment.paid_at = datetime.utcnow().isoformat()
    
    if data.payment_method:
        payment.payment_method = data.payment_method
    
    if data.transaction_id:
        payment.transaction_id = data.transaction_id
    
    if data.payment_reference:
        payment.payment_reference = data.payment_reference
    
    if data.paid_at:
        payment.paid_at = data.paid_at
    
    if data.failure_reason:
        payment.failure_reason = data.failure_reason
    
    if data.payment_notes:
        payment.payment_notes = data.payment_notes
    
    db.commit()
    db.refresh(payment)
    
    logger.info(f"✅ Payment updated: {payment_id}")
    
    return WorkPaymentApiResponse(
        success=True,
        message="Payment updated successfully",
        data=payment.to_dict()
    )


@router.get("/earnings/summary", response_model=WorkPaymentApiResponse)
async def get_earnings_summary(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get earnings summary for current user (as employee).
    
    Includes:
    - Total earnings (paid)
    - Pending payments
    - Completed jobs count
    - Average payment
    - Recent payments
    """
    logger.info(f"Calculating earnings for user: {current_user.email}")
    
    # Get all payments for user as employee
    payments = db.query(Payment).filter(Payment.employee_id == current_user.id).all()
    
    # Calculate totals
    paid_payments = [p for p in payments if p.status == "PAID"]
    pending_payments = [p for p in payments if p.status in ["PENDING", "PROCESSING"]]
    
    total_earnings = sum(float(p.net_amount or 0) for p in paid_payments)
    pending_amount = sum(float(p.net_amount or 0) for p in pending_payments)
    
    # Get completed jobs count
    completed_jobs = db.query(WorkConfirmation).filter(
        WorkConfirmation.employee_id == current_user.id,
        WorkConfirmation.status == "COMPLETED"
    ).count()
    
    # Calculate average payment
    average_payment = total_earnings / len(paid_payments) if paid_payments else 0
    
    # Get recent payments (last 10)
    recent_payments = sorted(payments, key=lambda p: p.created_at, reverse=True)[:10]
    
    return WorkPaymentApiResponse(
        success=True,
        message="Earnings summary calculated",
        data={
            "userId": current_user.id,
            "totalEarnings": round(total_earnings, 2),
            "pendingPayments": round(pending_amount, 2),
            "completedJobs": completed_jobs,
            "averagePayment": round(average_payment, 2),
            "currency": "RWF",
            "recentPayments": [p.to_dict() for p in recent_payments]
        }
    )


@router.get("/earnings/history", response_model=WorkPaymentApiResponse)
async def get_earnings_history(
    start_date: Optional[str] = Query(None, description="ISO format date"),
    end_date: Optional[str] = Query(None, description="ISO format date"),
    skip: int = Query(0, ge=0),
    limit: int = Query(20, ge=1, le=100),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get earnings history with optional date filtering.
    """
    logger.info(f"Fetching earnings history for user: {current_user.email}")
    
    query = db.query(Payment).filter(
        Payment.employee_id == current_user.id,
        Payment.status == "PAID"
    )
    
    # TODO: Add date filtering when implemented
    # if start_date:
    #     query = query.filter(Payment.paid_at >= start_date)
    # if end_date:
    #     query = query.filter(Payment.paid_at <= end_date)
    
    total = query.count()
    payments = query.order_by(Payment.paid_at.desc()).offset(skip).limit(limit).all()
    
    total_amount = sum(float(p.net_amount or 0) for p in payments)
    
    return WorkPaymentApiResponse(
        success=True,
        message=f"Found {total} payments",
        data={
            "payments": [p.to_dict() for p in payments],
            "total": total,
            "totalAmount": round(total_amount, 2),
            "currency": "RWF",
            "skip": skip,
            "limit": limit
        }
    )


# ══════════════════════════════════════════════════════════
# STATISTICS ENDPOINTS
# ══════════════════════════════════════════════════════════

@router.get("/stats/work-completion", response_model=WorkPaymentApiResponse)
async def get_work_completion_stats(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get work completion statistics.
    """
    # Get confirmations as employee
    employee_confirmations = db.query(WorkConfirmation).filter(
        WorkConfirmation.employee_id == current_user.id
    ).all()
    
    # Get confirmations as employer
    employer_confirmations = db.query(WorkConfirmation).filter(
        WorkConfirmation.employer_id == current_user.id
    ).all()
    
    # Calculate stats
    employee_stats = {
        "total": len(employee_confirmations),
        "completed": len([c for c in employee_confirmations if c.status == "COMPLETED"]),
        "pending": len([c for c in employee_confirmations if c.status in ["PENDING", "EMPLOYER_CONFIRMED", "EMPLOYEE_CONFIRMED"]]),
        "disputed": len([c for c in employee_confirmations if c.status == "DISPUTED"])
    }
    
    employer_stats = {
        "total": len(employer_confirmations),
        "completed": len([c for c in employer_confirmations if c.status == "COMPLETED"]),
        "pending": len([c for c in employer_confirmations if c.status in ["PENDING", "EMPLOYER_CONFIRMED", "EMPLOYEE_CONFIRMED"]]),
        "disputed": len([c for c in employer_confirmations if c.status == "DISPUTED"])
    }
    
    return WorkPaymentApiResponse(
        success=True,
        message="Work completion stats calculated",
        data={
            "asEmployee": employee_stats,
            "asEmployer": employer_stats
        }
    )

# ══════════════════════════════════════════════════════════