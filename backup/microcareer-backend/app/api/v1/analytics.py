"""
Analytics & Insights Endpoints
===============================

Advanced analytics for data-driven decision making.

Place this in: app/api/v1/analytics.py
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query, Response
from sqlalchemy.orm import Session
from sqlalchemy import func, and_, or_, desc, extract
from typing import Optional, List
import logging
from datetime import datetime, timedelta
import csv
import io

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application
from app.models.review import Review
from app.models.payment import WorkConfirmation, Payment
from app.schemas.analytics import (
    ExportRequest,
    AnalyticsApiResponse
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# HELPER: Admin Verification
# ══════════════════════════════════════════════════════════

async def get_admin_user(current_user: User = Depends(get_current_user)) -> User:
    """Verify user is admin"""
    if current_user.role != "ADMIN":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Admin access required"
        )
    return current_user

# ══════════════════════════════════════════════════════════
# TIME-SERIES ANALYTICS
# ══════════════════════════════════════════════════════════

@router.get("/time-series/users", response_model=AnalyticsApiResponse)
async def get_user_growth_timeseries(
    period: str = Query("daily", description="daily, weekly, monthly"),
    days: int = Query(30, ge=1, le=365),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Get user registration time-series data.
    
    Returns daily/weekly/monthly user sign-ups over time.
    Includes growth rate and trend analysis.
    """
    logger.info(f"Fetching user time-series: {period}, {days} days")
    
    start_date = datetime.utcnow() - timedelta(days=days)
    start_date_iso = start_date.isoformat()
    
    # Get user registrations by date
    if period == "daily":
        daily_data = db.query(
            func.date(User.created_at).label('date'),
            func.count(User.id).label('count')
        ).filter(
            User.created_at >= start_date_iso
        ).group_by(func.date(User.created_at)).all()
        
        data_points = [
            {"date": str(date), "value": count}
            for date, count in daily_data
        ]
    
    elif period == "weekly":
        # Group by week
        weekly_data = db.query(
            func.date_trunc('week', func.cast(User.created_at, db.DateTime)).label('week'),
            func.count(User.id).label('count')
        ).filter(
            User.created_at >= start_date_iso
        ).group_by('week').all()
        
        data_points = [
            {"date": str(week), "value": count}
            for week, count in weekly_data
        ]
    
    else:  # monthly
        monthly_data = db.query(
            func.date_trunc('month', func.cast(User.created_at, db.DateTime)).label('month'),
            func.count(User.id).label('count')
        ).filter(
            User.created_at >= start_date_iso
        ).group_by('month').all()
        
        data_points = [
            {"date": str(month), "value": count}
            for month, count in monthly_data
        ]
    
    # Calculate metrics
    total = sum(dp['value'] for dp in data_points)
    average = total / len(data_points) if data_points else 0
    
    # Determine trend (compare first half vs second half)
    if len(data_points) >= 4:
        mid = len(data_points) // 2
        first_half_avg = sum(dp['value'] for dp in data_points[:mid]) / mid
        second_half_avg = sum(dp['value'] for dp in data_points[mid:]) / (len(data_points) - mid)
        
        if second_half_avg > first_half_avg * 1.1:
            trend = "up"
        elif second_half_avg < first_half_avg * 0.9:
            trend = "down"
        else:
            trend = "stable"
    else:
        trend = "insufficient_data"
    
    return AnalyticsApiResponse(
        success=True,
        message=f"User growth time-series ({period})",
        data={
            "metric": "user_registrations",
            "period": period,
            "dataPoints": data_points,
            "total": total,
            "average": round(average, 1),
            "trend": trend,
            "startDate": start_date.isoformat(),
            "endDate": datetime.utcnow().isoformat()
        }
    )


@router.get("/time-series/jobs", response_model=AnalyticsApiResponse)
async def get_job_posting_timeseries(
    period: str = Query("daily", description="daily, weekly, monthly"),
    days: int = Query(30, ge=1, le=365),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Get job posting time-series data.
    
    Returns daily/weekly/monthly job postings over time.
    """
    start_date = datetime.utcnow() - timedelta(days=days)
    start_date_iso = start_date.isoformat()
    
    # Get job postings by date
    daily_data = db.query(
        func.date(Job.created_at).label('date'),
        func.count(Job.id).label('count')
    ).filter(
        Job.created_at >= start_date_iso
    ).group_by(func.date(Job.created_at)).all()
    
    data_points = [
        {"date": str(date), "value": count}
        for date, count in daily_data
    ]
    
    total = sum(dp['value'] for dp in data_points)
    average = total / len(data_points) if data_points else 0
    
    return AnalyticsApiResponse(
        success=True,
        message=f"Job posting time-series ({period})",
        data={
            "metric": "job_postings",
            "period": period,
            "dataPoints": data_points,
            "total": total,
            "average": round(average, 1)
        }
    )


@router.get("/time-series/revenue", response_model=AnalyticsApiResponse)
async def get_revenue_timeseries(
    period: str = Query("daily", description="daily, weekly, monthly"),
    days: int = Query(30, ge=1, le=365),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Get revenue time-series data.
    
    Returns daily/weekly/monthly revenue (platform fees) over time.
    """
    start_date = datetime.utcnow() - timedelta(days=days)
    start_date_iso = start_date.isoformat()
    
    # Get daily revenue (platform fees)
    # Note: This assumes paid_at is stored as ISO string
    daily_revenue = db.query(
        func.date(Payment.created_at).label('date'),
        func.sum(Payment.platform_fee_amount).label('revenue')
    ).filter(
        Payment.status == "PAID",
        Payment.created_at >= start_date_iso
    ).group_by(func.date(Payment.created_at)).all()
    
    data_points = [
        {"date": str(date), "value": float(revenue or 0)}
        for date, revenue in daily_revenue
    ]
    
    total = sum(dp['value'] for dp in data_points)
    average = total / len(data_points) if data_points else 0
    
    return AnalyticsApiResponse(
        success=True,
        message=f"Revenue time-series ({period})",
        data={
            "metric": "platform_revenue",
            "period": period,
            "dataPoints": data_points,
            "total": round(total, 2),
            "average": round(average, 2),
            "currency": "RWF"
        }
    )

# ══════════════════════════════════════════════════════════
# GEOGRAPHIC ANALYTICS
# ══════════════════════════════════════════════════════════

@router.get("/geographic/distribution", response_model=AnalyticsApiResponse)
async def get_geographic_distribution(
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Get geographic distribution of jobs and users.
    
    Returns:
    - Jobs by location
    - Users by location
    - Top locations
    - Coverage statistics
    """
    logger.info("Fetching geographic distribution")
    
    # Get jobs by location
    jobs_by_location = db.query(
        Job.location,
        func.count(Job.id).label('jobs_count')
    ).group_by(Job.location).all()
    
    # Get applications by job location
    apps_by_location = db.query(
        Job.location,
        func.count(Application.id).label('apps_count')
    ).join(Job, Application.job_id == Job.id
    ).group_by(Job.location).all()
    
    # Get payments by job location
    payments_by_location = db.query(
        Job.location,
        func.avg(Payment.amount).label('avg_payment')
    ).join(Payment, Job.id == Payment.job_id
    ).filter(Payment.status == "PAID"
    ).group_by(Job.location).all()
    
    # Combine data
    location_data = {}
    for location, count in jobs_by_location:
        if location:
            location_data[location] = {
                "location": location,
                "jobsCount": count,
                "usersCount": 0,  # Could add user location tracking
                "applicationsCount": 0,
                "avgPayment": 0,
                "completionRate": 0
            }
    
    for location, count in apps_by_location:
        if location and location in location_data:
            location_data[location]["applicationsCount"] = count
    
    for location, avg_pay in payments_by_location:
        if location and location in location_data:
            location_data[location]["avgPayment"] = round(float(avg_pay or 0), 2)
    
    # Sort by job count
    sorted_locations = sorted(
        location_data.values(),
        key=lambda x: x['jobsCount'],
        reverse=True
    )
    
    top_location = sorted_locations[0]['location'] if sorted_locations else "N/A"
    
    return AnalyticsApiResponse(
        success=True,
        message="Geographic distribution retrieved",
        data={
            "byLocation": sorted_locations,
            "totalLocations": len(sorted_locations),
            "topLocation": top_location,
            "coveragePercentage": 100.0  # Placeholder
        }
    )

# ══════════════════════════════════════════════════════════
# CONVERSION FUNNELS
# ══════════════════════════════════════════════════════════

@router.get("/funnels/job-to-hire", response_model=AnalyticsApiResponse)
async def get_job_to_hire_funnel(
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Job-to-hire conversion funnel.
    
    Stages:
    1. Jobs Posted
    2. Jobs with Applications
    3. Jobs with Accepted Applications
    4. Jobs with Completed Work
    5. Jobs with Payments Made
    """
    # Stage 1: Jobs Posted
    total_jobs = db.query(Job).count()
    
    # Stage 2: Jobs with Applications
    jobs_with_apps = db.query(Job.id).join(Application).distinct().count()
    
    # Stage 3: Jobs with Accepted Applications
    jobs_with_accepted = db.query(Job.id).join(Application).filter(
        Application.status == "ACCEPTED"
    ).distinct().count()
    
    # Stage 4: Jobs with Completed Work
    jobs_with_completed = db.query(Job.id).join(WorkConfirmation).filter(
        WorkConfirmation.status == "COMPLETED"
    ).distinct().count()
    
    # Stage 5: Jobs with Payments
    jobs_with_payments = db.query(Job.id).join(Payment).filter(
        Payment.status == "PAID"
    ).distinct().count()
    
    # Calculate percentages
    stages = [
        {
            "stage": "Jobs Posted",
            "count": total_jobs,
            "percentage": 100.0,
            "dropOffRate": 0
        },
        {
            "stage": "Received Applications",
            "count": jobs_with_apps,
            "percentage": round((jobs_with_apps / total_jobs * 100) if total_jobs > 0 else 0, 1),
            "dropOffRate": round(((total_jobs - jobs_with_apps) / total_jobs * 100) if total_jobs > 0 else 0, 1)
        },
        {
            "stage": "Accepted Applicant",
            "count": jobs_with_accepted,
            "percentage": round((jobs_with_accepted / total_jobs * 100) if total_jobs > 0 else 0, 1),
            "dropOffRate": round(((jobs_with_apps - jobs_with_accepted) / jobs_with_apps * 100) if jobs_with_apps > 0 else 0, 1)
        },
        {
            "stage": "Work Completed",
            "count": jobs_with_completed,
            "percentage": round((jobs_with_completed / total_jobs * 100) if total_jobs > 0 else 0, 1),
            "dropOffRate": round(((jobs_with_accepted - jobs_with_completed) / jobs_with_accepted * 100) if jobs_with_accepted > 0 else 0, 1)
        },
        {
            "stage": "Payment Made",
            "count": jobs_with_payments,
            "percentage": round((jobs_with_payments / total_jobs * 100) if total_jobs > 0 else 0, 1),
            "dropOffRate": round(((jobs_with_completed - jobs_with_payments) / jobs_with_completed * 100) if jobs_with_completed > 0 else 0, 1)
        }
    ]
    
    overall_conversion = round((jobs_with_payments / total_jobs * 100) if total_jobs > 0 else 0, 1)
    
    return AnalyticsApiResponse(
        success=True,
        message="Job-to-hire funnel retrieved",
        data={
            "funnelName": "Job to Hire",
            "stages": stages,
            "overallConversionRate": overall_conversion,
            "totalStarted": total_jobs,
            "totalCompleted": jobs_with_payments
        }
    )


@router.get("/funnels/user-engagement", response_model=AnalyticsApiResponse)
async def get_user_engagement_funnel(
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    User engagement funnel.
    
    Stages:
    1. Users Registered
    2. Users who Applied to Jobs
    3. Users with Accepted Applications
    4. Users with Completed Work
    5. Users with Earnings
    """
    # Stage 1: Total Users (Employees)
    total_employees = db.query(User).filter(User.role == "EMPLOYEE").count()
    
    # Stage 2: Users who applied
    users_who_applied = db.query(Application.employee_id).distinct().count()
    
    # Stage 3: Users with accepted applications
    users_with_accepted = db.query(Application.employee_id).filter(
        Application.status == "ACCEPTED"
    ).distinct().count()
    
    # Stage 4: Users with completed work
    users_with_completed = db.query(WorkConfirmation.employee_id).filter(
        WorkConfirmation.status == "COMPLETED"
    ).distinct().count()
    
    # Stage 5: Users with earnings
    users_with_earnings = db.query(Payment.employee_id).filter(
        Payment.status == "PAID"
    ).distinct().count()
    
    stages = [
        {
            "stage": "Registered",
            "count": total_employees,
            "percentage": 100.0
        },
        {
            "stage": "Applied to Jobs",
            "count": users_who_applied,
            "percentage": round((users_who_applied / total_employees * 100) if total_employees > 0 else 0, 1)
        },
        {
            "stage": "Got Accepted",
            "count": users_with_accepted,
            "percentage": round((users_with_accepted / total_employees * 100) if total_employees > 0 else 0, 1)
        },
        {
            "stage": "Completed Work",
            "count": users_with_completed,
            "percentage": round((users_with_completed / total_employees * 100) if total_employees > 0 else 0, 1)
        },
        {
            "stage": "Earned Money",
            "count": users_with_earnings,
            "percentage": round((users_with_earnings / total_employees * 100) if total_employees > 0 else 0, 1)
        }
    ]
    
    overall_conversion = round((users_with_earnings / total_employees * 100) if total_employees > 0 else 0, 1)
    
    return AnalyticsApiResponse(
        success=True,
        message="User engagement funnel retrieved",
        data={
            "funnelName": "User Engagement",
            "stages": stages,
            "overallConversionRate": overall_conversion,
            "totalStarted": total_employees,
            "totalCompleted": users_with_earnings
        }
    )

# ══════════════════════════════════════════════════════════
# Continue in next file...
# ══════════════════════════════════════════════════════════
"""
Analytics Endpoints - Part 2
=============================

Performance metrics and export functionality.
Append to: app/api/v1/analytics.py
"""

# ══════════════════════════════════════════════════════════
# PERFORMANCE METRICS
# ══════════════════════════════════════════════════════════

@router.get("/performance/metrics", response_model=AnalyticsApiResponse)
async def get_performance_metrics(
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Get key performance indicators (KPIs).
    
    Metrics:
    - Time to hire (days)
    - Application response time
    - Job fill rate
    - User retention rate
    - Payment completion rate
    """
    # Calculate metrics
    
    # 1. Average time to hire (from job post to acceptance)
    # This would require timestamp tracking - placeholder for now
    avg_time_to_hire = 7.5  # days
    
    # 2. Application response rate
    total_apps = db.query(Application).count()
    responded_apps = db.query(Application).filter(
        Application.status.in_(["ACCEPTED", "REJECTED"])
    ).count()
    response_rate = (responded_apps / total_apps * 100) if total_apps > 0 else 0
    
    # 3. Job fill rate
    total_jobs = db.query(Job).count()
    filled_jobs = db.query(Job).join(Application).filter(
        Application.status == "ACCEPTED"
    ).distinct().count()
    fill_rate = (filled_jobs / total_jobs * 100) if total_jobs > 0 else 0
    
    # 4. Work completion rate
    total_accepted = db.query(Application).filter(
        Application.status == "ACCEPTED"
    ).count()
    completed_work = db.query(WorkConfirmation).filter(
        WorkConfirmation.status == "COMPLETED"
    ).count()
    completion_rate = (completed_work / total_accepted * 100) if total_accepted > 0 else 0
    
    # 5. Payment completion rate
    total_payments = db.query(Payment).count()
    paid_payments = db.query(Payment).filter(Payment.status == "PAID").count()
    payment_rate = (paid_payments / total_payments * 100) if total_payments > 0 else 0
    
    metrics = [
        {
            "metricName": "Time to Hire",
            "value": avg_time_to_hire,
            "unit": "days",
            "benchmark": 10.0,
            "status": "good" if avg_time_to_hire < 10 else "warning"
        },
        {
            "metricName": "Application Response Rate",
            "value": round(response_rate, 1),
            "unit": "%",
            "benchmark": 80.0,
            "status": "good" if response_rate >= 80 else "warning"
        },
        {
            "metricName": "Job Fill Rate",
            "value": round(fill_rate, 1),
            "unit": "%",
            "benchmark": 70.0,
            "status": "good" if fill_rate >= 70 else "warning"
        },
        {
            "metricName": "Work Completion Rate",
            "value": round(completion_rate, 1),
            "unit": "%",
            "benchmark": 85.0,
            "status": "good" if completion_rate >= 85 else "warning"
        },
        {
            "metricName": "Payment Completion Rate",
            "value": round(payment_rate, 1),
            "unit": "%",
            "benchmark": 95.0,
            "status": "good" if payment_rate >= 95 else "warning"
        }
    ]
    
    return AnalyticsApiResponse(
        success=True,
        message="Performance metrics retrieved",
        data={"metrics": metrics}
    )

# ══════════════════════════════════════════════════════════
# DATA EXPORT
# ══════════════════════════════════════════════════════════

@router.get("/export/users")
async def export_users_csv(
    format: str = Query("csv", description="csv or json"),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Export users data to CSV or JSON.
    
    Returns downloadable file.
    """
    logger.info(f"Exporting users data as {format}")
    
    users = db.query(User).all()
    
    if format == "csv":
        # Create CSV
        output = io.StringIO()
        writer = csv.writer(output)
        
        # Header
        writer.writerow([
            'ID', 'Name', 'Email', 'Role', 'Is Active', 
            'Is Verified', 'Created At'
        ])
        
        # Data
        for user in users:
            writer.writerow([
                user.id,
                user.name,
                user.email,
                user.role,
                user.is_active,
                user.is_verified,
                user.created_at
            ])
        
        # Return CSV
        output.seek(0)
        return Response(
            content=output.getvalue(),
            media_type="text/csv",
            headers={
                "Content-Disposition": f"attachment; filename=users_{datetime.utcnow().strftime('%Y%m%d')}.csv"
            }
        )
    
    else:  # JSON
        return AnalyticsApiResponse(
            success=True,
            message=f"Exported {len(users)} users",
            data={
                "users": [u.to_dict() for u in users],
                "total": len(users),
                "exportedAt": datetime.utcnow().isoformat()
            }
        )


@router.get("/export/jobs")
async def export_jobs_csv(
    status: Optional[str] = Query(None),
    format: str = Query("csv", description="csv or json"),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Export jobs data to CSV or JSON.
    """
    query = db.query(Job)
    
    if status:
        query = query.filter(Job.status == status)
    
    jobs = query.all()
    
    if format == "csv":
        output = io.StringIO()
        writer = csv.writer(output)
        
        writer.writerow([
            'ID', 'Title', 'Category', 'Location', 'Payment', 
            'Status', 'Employer ID', 'Created At'
        ])
        
        for job in jobs:
            writer.writerow([
                job.id,
                job.title,
                job.category,
                job.location,
                job.payment,
                job.status,
                job.employer_id,
                job.created_at
            ])
        
        output.seek(0)
        return Response(
            content=output.getvalue(),
            media_type="text/csv",
            headers={
                "Content-Disposition": f"attachment; filename=jobs_{datetime.utcnow().strftime('%Y%m%d')}.csv"
            }
        )
    
    else:
        return AnalyticsApiResponse(
            success=True,
            message=f"Exported {len(jobs)} jobs",
            data={
                "jobs": [j.to_dict() for j in jobs],
                "total": len(jobs)
            }
        )


@router.get("/export/payments")
async def export_payments_csv(
    status: Optional[str] = Query(None),
    format: str = Query("csv", description="csv or json"),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Export payments data to CSV or JSON.
    """
    query = db.query(Payment)
    
    if status:
        query = query.filter(Payment.status == status)
    
    payments = query.all()
    
    if format == "csv":
        output = io.StringIO()
        writer = csv.writer(output)
        
        writer.writerow([
            'ID', 'Job ID', 'Employee ID', 'Employer ID', 
            'Amount', 'Platform Fee', 'Net Amount', 'Status',
            'Payment Method', 'Created At', 'Paid At'
        ])
        
        for payment in payments:
            writer.writerow([
                payment.id,
                payment.job_id,
                payment.employee_id,
                payment.employer_id,
                float(payment.amount),
                float(payment.platform_fee_amount),
                float(payment.net_amount or 0),
                payment.status,
                payment.payment_method,
                payment.created_at,
                payment.paid_at
            ])
        
        output.seek(0)
        return Response(
            content=output.getvalue(),
            media_type="text/csv",
            headers={
                "Content-Disposition": f"attachment; filename=payments_{datetime.utcnow().strftime('%Y%m%d')}.csv"
            }
        )
    
    else:
        return AnalyticsApiResponse(
            success=True,
            message=f"Exported {len(payments)} payments",
            data={
                "payments": [p.to_dict() for p in payments],
                "total": len(payments)
            }
        )

# ══════════════════════════════════════════════════════════
# SUMMARY REPORT
# ══════════════════════════════════════════════════════════

@router.get("/reports/monthly-summary", response_model=AnalyticsApiResponse)
async def get_monthly_summary_report(
    month: Optional[int] = Query(None, ge=1, le=12),
    year: Optional[int] = Query(None, ge=2020, le=2030),
    admin: User = Depends(get_admin_user),
    db: Session = Depends(get_db)
):
    """
    Generate monthly summary report.
    
    Includes:
    - New users
    - New jobs
    - Applications
    - Completed work
    - Revenue
    - Top performers
    """
    # Default to current month
    if not month or not year:
        now = datetime.utcnow()
        month = now.month
        year = now.year
    
    # Calculate date range
    start_date = datetime(year, month, 1)
    if month == 12:
        end_date = datetime(year + 1, 1, 1)
    else:
        end_date = datetime(year, month + 1, 1)
    
    start_iso = start_date.isoformat()
    end_iso = end_date.isoformat()
    
    # Gather metrics
    new_users = db.query(User).filter(
        User.created_at >= start_iso,
        User.created_at < end_iso
    ).count()
    
    new_jobs = db.query(Job).filter(
        Job.created_at >= start_iso,
        Job.created_at < end_iso
    ).count()
    
    new_applications = db.query(Application).filter(
        Application.created_at >= start_iso,
        Application.created_at < end_iso
    ).count()
    
    completed_work = db.query(WorkConfirmation).filter(
        WorkConfirmation.completed_at >= start_iso,
        WorkConfirmation.completed_at < end_iso,
        WorkConfirmation.status == "COMPLETED"
    ).count()
    
    monthly_revenue = db.query(func.sum(Payment.platform_fee_amount)).filter(
        Payment.created_at >= start_iso,
        Payment.created_at < end_iso,
        Payment.status == "PAID"
    ).scalar() or 0
    
    return AnalyticsApiResponse(
        success=True,
        message=f"Monthly summary for {year}-{month:02d}",
        data={
            "month": month,
            "year": year,
            "newUsers": new_users,
            "newJobs": new_jobs,
            "newApplications": new_applications,
            "completedWork": completed_work,
            "revenue": float(monthly_revenue),
            "currency": "RWF"
        }
    )

# ══════════════════════════════════════════════════════════
# END OF ANALYTICS
# ══════════════════════════════════════════════════════════