"""
Review Endpoints
================

API endpoints for ratings and reputation system.

Features:
- Submit reviews (POST)
- View reviews (GET)
- Update reviews (PUT)
- Delete reviews (DELETE)
- Respond to reviews
- Flag reviews
- Calculate reputation

Place this in: app/api/v1/reviews.py
"""

from fastapi import APIRouter, Depends, HTTPException, status, Query
from sqlalchemy.orm import Session
from sqlalchemy import func, and_, or_
from typing import List, Optional
import logging
from datetime import datetime

from app.database import get_db
from app.dependencies import get_current_user
from app.models.user import User
from app.models.job import Job, Application
from app.models.review import Review
from app.schemas.review import (
    ReviewCreate,
    ReviewUpdate,
    ReviewResponse,
    ReviewFlag,
    ReviewDetail,
    ReviewSummary,
    UserReputation,
    ReviewApiResponse
)

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# CREATE REVIEW
# ══════════════════════════════════════════════════════════

@router.post("/", response_model=ReviewApiResponse, status_code=status.HTTP_201_CREATED)
async def create_review(
    review_data: ReviewCreate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Submit a review for a completed job.
    
    Rules:
    - Can only review users you've worked with
    - One review per job per user
    - Job must be completed (application ACCEPTED)
    - Cannot review yourself
    """
    logger.info(f"Creating review: {current_user.email} reviewing user {review_data.reviewee_id}")
    
    # Validate: Cannot review yourself
    if review_data.reviewee_id == current_user.id:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Cannot review yourself"
        )
    
    # Validate: Job exists
    job = db.query(Job).filter(Job.id == review_data.job_id).first()
    if not job:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Job not found"
        )
    
    # Validate: Users have worked together on this job
    # Check if there's an ACCEPTED application
# Validate: Users have worked together on this job
    is_employer = job.employer_id == current_user.id
    employee_id = review_data.reviewee_id if is_employer else current_user.id

    application = db.query(Application).filter(
        Application.job_id == review_data.job_id,
        Application.employee_id == employee_id,
        Application.status.in_(["ACCEPTED", "COMPLETED"])
    ).first()

    if not application:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Can only review users you've worked with (application must be ACCEPTED or COMPLETED)"
        )

    # Validate: Haven't already reviewed this user for this job
    existing_review = db.query(Review).filter(
        and_(
            Review.job_id == review_data.job_id,
            Review.reviewer_id == current_user.id
        )
    ).first()

    if existing_review:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="You have already reviewed this job"
        )

    # Create review
    review = Review(
        job_id=review_data.job_id,
        reviewer_id=current_user.id,
        reviewee_id=review_data.reviewee_id,
        overall_rating=review_data.overall_rating,
        quality_rating=review_data.category_ratings.quality if review_data.category_ratings else None,
        punctuality_rating=review_data.category_ratings.punctuality if review_data.category_ratings else None,
        communication_rating=review_data.category_ratings.communication if review_data.category_ratings else None,
        professionalism_rating=review_data.category_ratings.professionalism if review_data.category_ratings else None,
        comment=review_data.comment,
        is_public=review_data.is_public
    )

    db.add(review)

    reviewee = db.query(User).filter(User.id == review_data.reviewee_id).first()
    if reviewee:
      existing = [r[0] for r in db.query(Review.overall_rating).filter(Review.reviewee_id == review_data.reviewee_id).all()]
    all_ratings = existing + [review_data.overall_rating]
    reviewee.rating = round(sum(all_ratings) / len(all_ratings), 2)

    db.commit()
    db.refresh(review)

    logger.info(f"✅ Review created: {review.id}")

    return ReviewApiResponse(
        success=True,
        message="Review submitted successfully",
        data=review.to_dict()
    )
# ══════════════════════════════════════════════════════════
# GET USER'S REVIEWS (Received)
# ══════════════════════════════════════════════════════════

@router.get("/user/{user_id}", response_model=ReviewApiResponse)
async def get_user_reviews(
    user_id: str,
    only_public: bool = Query(True, description="Only show public reviews"),
    skip: int = Query(0, ge=0),
    limit: int = Query(10, ge=1, le=50),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get all reviews for a specific user.
    
    Returns reviews where the user is the reviewee (received reviews).
    """
    logger.info(f"Fetching reviews for user: {user_id}")
    
    # Build query
    query = db.query(Review).filter(Review.reviewee_id == user_id)
    
    # Filter by public if requested (unless viewing own reviews)
    if only_public or user_id != current_user.id:
        query = query.filter(Review.is_public == True, Review.is_approved == True)
    
    # Get total count
    total = query.count()
    
    # Get reviews with pagination
    reviews = query.order_by(Review.created_at.desc()).offset(skip).limit(limit).all()
    
    return ReviewApiResponse(
        success=True,
        message=f"Found {total} reviews",
        data={
            "reviews": [review.to_dict() for review in reviews],
            "total": total,
            "skip": skip,
            "limit": limit
        }
    )

# ══════════════════════════════════════════════════════════
# GET MY REVIEWS (Given by me)
# ══════════════════════════════════════════════════════════

@router.get("/my-reviews", response_model=ReviewApiResponse)
async def get_my_reviews(
    skip: int = Query(0, ge=0),
    limit: int = Query(10, ge=1, le=50),
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get all reviews I've submitted (as reviewer).
    """
    logger.info(f"Fetching reviews by: {current_user.email}")
    
    # Get reviews where current user is the reviewer
    query = db.query(Review).filter(Review.reviewer_id == current_user.id)
    total = query.count()
    
    reviews = query.order_by(Review.created_at.desc()).offset(skip).limit(limit).all()
    
    return ReviewApiResponse(
        success=True,
        message=f"Found {total} reviews you've submitted",
        data={
            "reviews": [review.to_dict() for review in reviews],
            "total": total
        }
    )

# ══════════════════════════════════════════════════════════
# GET USER REPUTATION
# ══════════════════════════════════════════════════════════

@router.get("/reputation/{user_id}", response_model=ReviewApiResponse)
async def get_user_reputation(
    user_id: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Get comprehensive reputation data for a user.
    
    Includes:
    - Overall average rating
    - Total review count
    - Rating distribution (5-star, 4-star, etc.)
    - Category averages
    - Recent reviews
    """
    logger.info(f"Calculating reputation for user: {user_id}")
    
    # Get user
    user = db.query(User).filter(User.id == user_id).first()
    if not user:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="User not found"
        )
    
    # Get all public, approved reviews for this user
    reviews = db.query(Review).filter(
        Review.reviewee_id == user_id,
        Review.is_public == True,
        Review.is_approved == True
    ).all()
    
    total_reviews = len(reviews)
    
    if total_reviews == 0:
        return ReviewApiResponse(
            success=True,
            message="No reviews yet",
            data={
                "userId": user_id,
                "userName": user.full_name,
                "overallRating": 0.0,
                "totalReviews": 0,
                "ratingDistribution": {"5": 0, "4": 0, "3": 0, "2": 0, "1": 0},
                "categoryAverages": {},
                "recentReviews": []
            }
        )
    
    # Calculate overall average
    overall_avg = sum(r.overall_rating for r in reviews) / total_reviews
    
    # Calculate rating distribution
    distribution = {"5": 0, "4": 0, "3": 0, "2": 0, "1": 0}
    for review in reviews:
        distribution[str(review.overall_rating)] += 1
    
    # Calculate category averages
    quality_reviews = [r.quality_rating for r in reviews if r.quality_rating is not None]
    punctuality_reviews = [r.punctuality_rating for r in reviews if r.punctuality_rating is not None]
    communication_reviews = [r.communication_rating for r in reviews if r.communication_rating is not None]
    professionalism_reviews = [r.professionalism_rating for r in reviews if r.professionalism_rating is not None]
    
    category_avg = {
        "quality": round(sum(quality_reviews) / len(quality_reviews), 1) if quality_reviews else None,
        "punctuality": round(sum(punctuality_reviews) / len(punctuality_reviews), 1) if punctuality_reviews else None,
        "communication": round(sum(communication_reviews) / len(communication_reviews), 1) if communication_reviews else None,
        "professionalism": round(sum(professionalism_reviews) / len(professionalism_reviews), 1) if professionalism_reviews else None
    }
    
    # Get recent reviews (last 5)
    recent_reviews = sorted(reviews, key=lambda r: r.created_at, reverse=True)[:5]
    
    return ReviewApiResponse(
        success=True,
        message="Reputation calculated successfully",
        data={
            "userId": user_id,
            "userName": user.full_name,
            "overallRating": round(overall_avg, 1),
            "totalReviews": total_reviews,
            "ratingDistribution": distribution,
            "categoryAverages": category_avg,
            "recentReviews": [r.to_dict() for r in recent_reviews]
        }
    )

# ══════════════════════════════════════════════════════════
# UPDATE REVIEW
# ══════════════════════════════════════════════════════════

@router.put("/{review_id}", response_model=ReviewApiResponse)
async def update_review(
    review_id: str,
    update_data: ReviewUpdate,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Update your own review.
    Only the reviewer can update their review.
    """
    logger.info(f"Updating review: {review_id}")
    
    # Get review
    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Review not found"
        )
    
    # Check permission: only reviewer can update
    if review.reviewer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only update your own reviews"
        )
    
    # Update fields
    if update_data.overall_rating is not None:
        review.overall_rating = update_data.overall_rating
    
    if update_data.category_ratings:
        if update_data.category_ratings.quality is not None:
            review.quality_rating = update_data.category_ratings.quality
        if update_data.category_ratings.punctuality is not None:
            review.punctuality_rating = update_data.category_ratings.punctuality
        if update_data.category_ratings.communication is not None:
            review.communication_rating = update_data.category_ratings.communication
        if update_data.category_ratings.professionalism is not None:
            review.professionalism_rating = update_data.category_ratings.professionalism
    
    if update_data.comment is not None:
        review.comment = update_data.comment
    
    if update_data.is_public is not None:
        review.is_public = update_data.is_public
    
    db.commit()
    db.refresh(review)
    
    logger.info(f"✅ Review updated: {review_id}")
    
    return ReviewApiResponse(
        success=True,
        message="Review updated successfully",
        data=review.to_dict()
    )

# ══════════════════════════════════════════════════════════
# DELETE REVIEW
# ══════════════════════════════════════════════════════════

@router.delete("/{review_id}", response_model=ReviewApiResponse)
async def delete_review(
    review_id: str,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Delete your own review.
    Only the reviewer can delete their review.
    """
    logger.info(f"Deleting review: {review_id}")
    
    # Get review
    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Review not found"
        )
    
    # Check permission
    if review.reviewer_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="You can only delete your own reviews"
        )
    
    db.delete(review)
    db.commit()
    
    logger.info(f"✅ Review deleted: {review_id}")
    
    return ReviewApiResponse(
        success=True,
        message="Review deleted successfully",
        data={"reviewId": review_id}
    )

# ══════════════════════════════════════════════════════════
# RESPOND TO REVIEW
# ══════════════════════════════════════════════════════════

@router.post("/{review_id}/respond", response_model=ReviewApiResponse)
async def respond_to_review(
    review_id: str,
    response_data: ReviewResponse,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Respond to a review (only reviewee can respond).
    """
    logger.info(f"Responding to review: {review_id}")
    
    # Get review
    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Review not found"
        )
    
    # Check permission: only reviewee can respond
    if review.reviewee_id != current_user.id:
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Only the reviewed user can respond"
        )
    
    # Add response
    review.response = response_data.response
    review.response_at = datetime.utcnow().isoformat()
    
    db.commit()
    db.refresh(review)
    
    logger.info(f"✅ Response added to review: {review_id}")
    
    return ReviewApiResponse(
        success=True,
        message="Response submitted successfully",
        data=review.to_dict()
    )

# ══════════════════════════════════════════════════════════
# FLAG REVIEW
# ══════════════════════════════════════════════════════════

@router.post("/{review_id}/flag", response_model=ReviewApiResponse)
async def flag_review(
    review_id: str,
    flag_data: ReviewFlag,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """
    Flag a review for moderation.
    Any authenticated user can flag inappropriate reviews.
    """
    logger.info(f"Flagging review: {review_id}")
    
    # Get review
    review = db.query(Review).filter(Review.id == review_id).first()
    if not review:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="Review not found"
        )
    
    # Flag the review
    review.is_flagged = True
    review.flagged_reason = flag_data.reason
    
    db.commit()
    
    logger.info(f"✅ Review flagged: {review_id} - {flag_data.reason}")
    
    return ReviewApiResponse(
        success=True,
        message="Review flagged for moderation",
        data={"reviewId": review_id, "reason": flag_data.reason}
    )

# ══════════════════════════════════════════════════════════