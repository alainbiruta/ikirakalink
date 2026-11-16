"""
Matching Service
================

AI-powered job matching algorithm.

Features:
- Skill similarity (TF-IDF + Cosine Similarity)
- Location proximity (Haversine distance)
- Time availability matching
- Reputation scoring
- Urgency boost
- Hybrid recommender
"""

from typing import List, Dict, Optional
from math import radians, cos, sin, asin, sqrt
import logging

logger = logging.getLogger(__name__)

# ══════════════════════════════════════════════════════════
# Helper Functions
# ══════════════════════════════════════════════════════════

def haversine_distance(lat1: float, lon1: float, lat2: float, lon2: float) -> float:
    """
    Calculate the great circle distance between two points 
    on the earth (specified in decimal degrees).
    
    Returns distance in kilometers.
    """
    # Convert decimal degrees to radians
    lon1, lat1, lon2, lat2 = map(radians, [lon1, lat1, lon2, lat2])
    
    # Haversine formula
    dlon = lon2 - lon1
    dlat = lat2 - lat1
    a = sin(dlat/2)**2 + cos(lat1) * cos(lat2) * sin(dlon/2)**2
    c = 2 * asin(sqrt(a))
    
    # Radius of earth in kilometers
    r = 6371
    
    return c * r

def calculate_skill_similarity(job_skills: List[str], user_skills: List[str]) -> float:
    """
    Calculate skill similarity using Jaccard similarity.
    
    For now, uses simple set overlap.
    TODO: Implement TF-IDF + Cosine Similarity in Week 3
    """
    if not job_skills or not user_skills:
        return 0.0
    
    # Convert to lowercase for comparison
    job_set = set(skill.lower() for skill in job_skills)
    user_set = set(skill.lower() for skill in user_skills)
    
    # Jaccard similarity: intersection / union
    intersection = len(job_set & user_set)
    union = len(job_set | user_set)
    
    if union == 0:
        return 0.0
    
    similarity = intersection / union
    
    logger.debug(f"Skill similarity: {similarity:.2f} (matched {intersection}/{len(job_set)} required skills)")
    
    return similarity

def calculate_location_score(
    job_lat: float,
    job_lon: float,
    user_lat: Optional[float],
    user_lon: Optional[float],
    max_distance_km: float = 50.0
) -> float:
    """
    Calculate location proximity score.
    
    Score decreases linearly with distance.
    Max distance defaults to 50km.
    """
    if user_lat is None or user_lon is None:
        # No location data - return neutral score
        return 0.5
    
    distance = haversine_distance(job_lat, job_lon, user_lat, user_lon)
    
    if distance > max_distance_km:
        score = 0.0
    else:
        # Linear decrease: 1.0 at 0km, 0.0 at max_distance_km
        score = max(0.0, 1.0 - (distance / max_distance_km))
    
    logger.debug(f"Location score: {score:.2f} (distance: {distance:.1f}km)")
    
    return score

def calculate_availability_score(
    job_required: Optional[Dict],
    user_available: Optional[Dict]
) -> float:
    """
    Calculate time availability matching score.
    
    TODO: Implement detailed time slot matching in Week 3
    For now, returns 1.0 if both have data, 0.5 otherwise
    """
    if not job_required or not user_available:
        return 0.5  # Neutral score if no availability data
    
    # Simple check for now - detailed implementation in Week 3
    # Will check if user's available days overlap with job's required days
    
    return 1.0  # Placeholder

# ══════════════════════════════════════════════════════════
# Main Matching Function
# ══════════════════════════════════════════════════════════

def calculate_match_score(
    job: Dict,
    employee: Dict,
    max_distance_km: float = 50.0
) -> float:
    """
    Calculate overall match score using hybrid recommender approach.
    
    Weights:
    - Skill similarity: 40%
    - Location proximity: 20%
    - Time availability: 15%
    - Reputation: 15%
    - Job urgency: 10%
    
    Returns score between 0.0 and 1.0
    """
    
    # 1. Skill Similarity (40%)
    skill_score = calculate_skill_similarity(
        job.get("required_skills", []),
        employee.get("skills", [])
    )
    
    # 2. Location Proximity (20%)
    location_score = calculate_location_score(
        job.get("latitude", 0),
        job.get("longitude", 0),
        employee.get("latitude"),
        employee.get("longitude"),
        max_distance_km
    )
    
    # 3. Time Availability (15%)
    availability_score = calculate_availability_score(
        job.get("required_availability"),
        employee.get("available_hours")
    )
    
    # 4. Reputation Score (15%)
    user_rating = employee.get("rating", 0.0)
    reputation_score = user_rating / 5.0  # Normalize to 0-1
    
    # 5. Job Urgency Boost (10%)
    urgency_boost = 1.0 if job.get("is_urgent", False) else 0.5
    
    # Weighted combination
    final_score = (
        skill_score * 0.40 +
        location_score * 0.20 +
        availability_score * 0.15 +
        reputation_score * 0.15 +
        urgency_boost * 0.10
    )
    
    # Ensure score is between 0 and 1
    final_score = max(0.0, min(1.0, final_score))
    
    logger.info(
        f"Match score: {final_score:.2f} "
        f"(skill: {skill_score:.2f}, location: {location_score:.2f}, "
        f"availability: {availability_score:.2f}, reputation: {reputation_score:.2f}, "
        f"urgency: {urgency_boost:.2f})"
    )
    
    return final_score

# ══════════════════════════════════════════════════════════
# Batch Matching
# ══════════════════════════════════════════════════════════

def calculate_match_scores_batch(
    jobs: List[Dict],
    employee: Dict,
    max_distance_km: float = 50.0
) -> List[tuple]:
    """
    Calculate match scores for multiple jobs.
    
    Returns list of (job, score) tuples, sorted by score descending.
    """
    scored_jobs = []
    
    for job in jobs:
        score = calculate_match_score(job, employee, max_distance_km)
        scored_jobs.append((job, score))
    
    # Sort by score descending
    scored_jobs.sort(key=lambda x: x[1], reverse=True)
    
    return scored_jobs

# ══════════════════════════════════════════════════════════
# Advanced Matching (Week 3-4)
# ══════════════════════════════════════════════════════════

# TODO: Implement TF-IDF vectorization
"""
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity

def calculate_skill_similarity_advanced(job_skills, user_skills):
    # Convert skills to text
    job_text = ' '.join(job_skills)
    user_text = ' '.join(user_skills)
    
    # Vectorize
    vectorizer = TfidfVectorizer()
    vectors = vectorizer.fit_transform([job_text, user_text])
    
    # Calculate cosine similarity
    similarity = cosine_similarity(vectors[0:1], vectors[1:2])[0][0]
    
    return similarity
"""

# TODO: Implement Learning-to-Rank model
"""
from lightgbm import LGBMRanker

def train_ranking_model(historical_applications):
    # Features: skill_match, location_score, etc.
    # Labels: application_success (0/1)
    # Train LightGBM ranker
    # Return trained model
    pass

def predict_match_with_model(model, job, employee):
    # Extract features
    features = extract_features(job, employee)
    # Predict score
    score = model.predict(features)
    return score
"""

# ══════════════════════════════════════════════════════════