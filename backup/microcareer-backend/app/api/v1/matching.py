"""
Enhanced Matching Endpoints
============================

ML-powered job matching endpoints using advanced algorithms.

Place this in: app/api/v1/matching.py (or update existing)
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from typing import List, Optional
import logging

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application
from app.services.advanced_matching import get_matching_service
from fastapi import APIRouter, Depends, HTTPException, status, Query, Request  # ← add Request
from slowapi import Limiter                                                      # ← add
from slowapi.util import get_remote_address

logger = logging.getLogger(__name__)

router = APIRouter()
limiter = Limiter(key_func=get_remote_address) 
# ══════════════════════════════════════════════════════════
# ENHANCED JOB RECOMMENDATIONS
# ══════════════════════════════════════════════════════════

@router.get("/recommendations/advanced")
@limiter.limit("10/minute")
async def get_advanced_job_recommendations(
    request: Request,
    limit: int = Query(10, ge=1, le=50),
    min_score: float = Query(0.2, ge=0.0, le=1.0),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get ML-powered job recommendations for current user.
    
    Uses:
    - TF-IDF skill matching
    - Cosine similarity
    - Multi-factor scoring
    - Personalized ranking
    
    Query params:
    - limit: Number of recommendations (1-50)
    - min_score: Minimum match score (0.0-1.0)
    
    Returns jobs with match scores and breakdowns.
    """
    logger.info(f"Getting advanced recommendations for user: {current_user.email}")
    
    # Get matching service
    matching_service = get_matching_service()
    
    # Get open jobs (exclude jobs user already applied to)
    applied_job_ids = [
        app.job_id for app in 
        db.query(Application).filter(Application.employee_id == current_user.id).all()
    ]
    
    jobs_query = db.query(Job).filter(
        Job.status == "OPEN"
    )
    
    if applied_job_ids:
        jobs_query = jobs_query.filter(~Job.id.in_(applied_job_ids))
    
    jobs = jobs_query.all()
    
    if not jobs:
        return {
            "success": True,
            "message": "No jobs available",
            "data": {
                "recommendations": [],
                "total": 0
            }
        }
    
    # Prepare user profile
    user_profile = {
        'skills': current_user.skills or [],
        'location': getattr(current_user, 'location', None),
        'expected_payment': getattr(current_user, 'expected_payment', None),
        'experience_years': getattr(current_user, 'experience_years', 0),
        'preferred_categories': getattr(current_user, 'preferred_categories', [])
    }
    
    # Prepare jobs data
    jobs_data = []
    for job in jobs:
        jobs_data.append({
            'id': job.id,
            'title': job.title,
            'description': job.description,
            'category': job.category,
            'location': job.location,
            'payment': float(job.payment) if job.payment else 0,
            'required_skills': job.required_skills or [],
            'preferred_skills': getattr(job, 'preferred_skills', []),
            'min_experience': getattr(job, 'min_experience', 0),
            'max_experience': getattr(job, 'max_experience', None),
            'employer_id': job.employer_id,
            'created_at': job.created_at
        })
    
    # Rank jobs using ML
    ranked_jobs = matching_service.rank_jobs_for_user(
        user_profile,
        jobs_data,
        top_k=limit * 2  # Get more, then filter by min_score
    )
    
    # Filter by minimum score
    filtered_jobs = [
        job for job in ranked_jobs 
        if job['match_score'] >= min_score
    ][:limit]
    
    logger.info(f"✅ Found {len(filtered_jobs)} matches for {current_user.email}")
    
    return {
        "success": True,
        "message": f"Found {len(filtered_jobs)} recommended jobs",
        "data": {
            "recommendations": filtered_jobs,
            "total": len(filtered_jobs),
            "algorithm": "ml_based",
            "factors": ["skills", "location", "payment", "experience", "category"]
        }
    }


# ══════════════════════════════════════════════════════════
# MATCH SCORE EXPLANATION
# ══════════════════════════════════════════════════════════

@router.get("/match-score/{job_id}")
async def explain_match_score(
    job_id: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get detailed explanation of match score for a specific job.
    
    Returns:
    - Overall score
    - Breakdown by factor
    - Recommendations for improvement
    """
    job = db.query(Job).filter(Job.id == job_id).first()
    
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    matching_service = get_matching_service()
    
    # Prepare user profile
    user_profile = {
        'skills': current_user.skills or [],
        'location': getattr(current_user, 'location', None),
        'expected_payment': getattr(current_user, 'expected_payment', None),
        'experience_years': getattr(current_user, 'experience_years', 0),
        'preferred_categories': getattr(current_user, 'preferred_categories', [])
    }
    
    # Prepare job data
    job_data = {
        'required_skills': job.required_skills or [],
        'preferred_skills': getattr(job, 'preferred_skills', []),
        'location': job.location,
        'payment': float(job.payment) if job.payment else 0,
        'min_experience': getattr(job, 'min_experience', 0),
        'max_experience': getattr(job, 'max_experience', None),
        'category': job.category
    }
    
    # Calculate score
    score_data = matching_service.calculate_comprehensive_score(user_profile, job_data)
    
    # Generate improvement suggestions
    suggestions = []
    breakdown = score_data['breakdown']
    
    if breakdown['skills'] < 0.7:
        missing_skills = set(job.required_skills or []) - set(current_user.skills or [])
        if missing_skills:
            suggestions.append({
                "factor": "skills",
                "message": f"Learn these skills to improve match: {', '.join(list(missing_skills)[:3])}"
            })
    
    if breakdown['location'] < 0.5:
        suggestions.append({
            "factor": "location",
            "message": f"Job is in {job.location}. Consider if you can work there."
        })
    
    if breakdown['payment'] < 0.7:
        suggestions.append({
            "factor": "payment",
            "message": "Payment may be below your expectations. Consider if acceptable."
        })
    
    return {
        "success": True,
        "message": "Match score calculated",
        "data": {
            "jobId": job_id,
            "jobTitle": job.title,
            "overallScore": score_data['overall_score'],
            "recommendation": score_data['recommendation'],
            "breakdown": score_data['breakdown'],
            "suggestions": suggestions,
            "weights": score_data['weights_used']
        }
    }


# ══════════════════════════════════════════════════════════
# SIMILAR JOBS
# ══════════════════════════════════════════════════════════

@router.get("/similar/{job_id}")
async def get_similar_jobs(
    job_id: str,
    limit: int = Query(5, ge=1, le=20),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Find jobs similar to a given job.
    
    Uses skill similarity and category matching.
    """
    source_job = db.query(Job).filter(Job.id == job_id).first()
    
    if not source_job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Get other jobs in same category
    similar_jobs = db.query(Job).filter(
        Job.id != job_id,
        Job.status == "OPEN",
        Job.category == source_job.category
    ).limit(limit).all()
    
    matching_service = get_matching_service()
    
    # Calculate similarity scores
    results = []
    for job in similar_jobs:
        # Calculate skill similarity
        skill_sim = matching_service.calculate_skill_similarity(
            source_job.required_skills or [],
            job.required_skills or [],
            job.get('preferred_skills', []) if hasattr(job, 'preferred_skills') else []
        )
        
        results.append({
            **job.to_dict(),
            'similarity_score': round(skill_sim, 3)
        })
    
    # Sort by similarity
    results.sort(key=lambda x: x['similarity_score'], reverse=True)
    
    return {
        "success": True,
        "message": f"Found {len(results)} similar jobs",
        "data": {
            "sourceJob": {
                "id": source_job.id,
                "title": source_job.title
            },
            "similarJobs": results
        }
    }


# ══════════════════════════════════════════════════════════
# MATCHING STATISTICS
# ══════════════════════════════════════════════════════════

@router.get("/stats/matching-quality")
async def get_matching_quality_stats(
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get statistics on matching quality.
    
    Compares match scores of accepted vs rejected applications.
    """
    # Get user's applications
    applications = db.query(Application).filter(
        Application.employee_id == current_user.id
    ).all()
    
    if not applications:
        return {
            "success": True,
            "message": "No application history",
            "data": {
                "totalApplications": 0
            }
        }
    
    matching_service = get_matching_service()
    user_profile = {
        'skills': current_user.skills or [],
        'location': getattr(current_user, 'location', None),
        'expected_payment': getattr(current_user, 'expected_payment', None),
        'experience_years': getattr(current_user, 'experience_years', 0),
        'preferred_categories': getattr(current_user, 'preferred_categories', [])
    }
    
    accepted_scores = []
    rejected_scores = []
    pending_scores = []
    
    for app in applications:
        job = db.query(Job).filter(Job.id == app.job_id).first()
        if not job:
            continue
        
        job_data = {
            'required_skills': job.required_skills or [],
            'location': job.location,
            'payment': float(job.payment) if job.payment else 0,
            'category': job.category
        }
        
        score_data = matching_service.calculate_comprehensive_score(user_profile, job_data)
        score = score_data['overall_score']
        
        if app.status == "ACCEPTED":
            accepted_scores.append(score)
        elif app.status == "REJECTED":
            rejected_scores.append(score)
        else:
            pending_scores.append(score)
    
    # Calculate averages
    avg_accepted = sum(accepted_scores) / len(accepted_scores) if accepted_scores else 0
    avg_rejected = sum(rejected_scores) / len(rejected_scores) if rejected_scores else 0
    avg_pending = sum(pending_scores) / len(pending_scores) if pending_scores else 0
    
    return {
        "success": True,
        "message": "Matching quality statistics",
        "data": {
            "totalApplications": len(applications),
            "accepted": {
                "count": len(accepted_scores),
                "averageMatchScore": round(avg_accepted, 3)
            },
            "rejected": {
                "count": len(rejected_scores),
                "averageMatchScore": round(avg_rejected, 3)
            },
            "pending": {
                "count": len(pending_scores),
                "averageMatchScore": round(avg_pending, 3)
            },
            "insight": "Higher match scores correlate with acceptance" if avg_accepted > avg_rejected else "Match scores need calibration"
        }
    }

# ══════════════════════════════════════════════════════════