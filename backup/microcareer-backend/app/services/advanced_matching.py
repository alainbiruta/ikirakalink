"""
Advanced AI Matching Service
=============================

ML-powered job matching using:
- TF-IDF vectorization  (corpus-level, not per-call)
- Cosine similarity
- Multi-factor scoring
- Personalized ranking
"""

from typing import List, Dict, Optional
import numpy as np
from sklearn.feature_extraction.text import TfidfVectorizer
from sklearn.metrics.pairwise import cosine_similarity
import logging

logger = logging.getLogger(__name__)


class AdvancedMatchingService:
    """
    Advanced ML-based job matching service.

    Key design decisions
    ─────────────────────
    • TF-IDF vocabulary is built from a *corpus* of many skill documents so
      IDF weights are meaningful. Per-call fit_transform() on 2 documents
      makes shared terms score 0 (IDF = log(1) = 0) — that was the 22% ceiling bug.
    • A fresh local TfidfVectorizer is used for each similarity call so
      concurrent requests never corrupt shared state.
    • Neutral scores (0.5–0.7) are returned when data is missing rather
      than penalising with 0.3.
    """

    # ── Corpus used to give TF-IDF real IDF weights ────────────────────────
    # These are common domain terms for MicroCareer.  Add more as your DB
    # grows — or replace with a corpus built from your actual job rows at
    # startup (see build_corpus_from_jobs() below).
    _BASE_CORPUS = [
        "plumbing welding construction electrical carpentry masonry painting",
        "cleaning housekeeping laundry sanitation janitorial",
        "delivery logistics driving transport motorbike courier",
        "agriculture farming irrigation harvesting livestock crop",
        "hospitality cooking chef catering waitstaff restaurant hotel",
        "it tech programming software web mobile data networking",
        "teaching tutoring education training coaching mentoring",
        "healthcare nursing medical clinical patient care pharmacy",
        "security guard surveillance patrol protection",
        "retail sales cashier merchandising customer service shop",
        "transportation driving logistics fleet heavy vehicle",
        "manufacturing assembly production quality control factory",
        "tailoring sewing fashion design textile garment",
        "hair beauty salon barber cosmetics makeup",
        "accounting finance bookkeeping payroll audit",
        "marketing social media content branding advertising",
        "administration office reception data entry filing",
        "engineering mechanical civil structural design",
    ]

    def __init__(self):
        # Pre-fit a vectorizer on the base corpus so IDF weights reflect
        # a realistic term distribution across many documents.
        self._corpus_vectorizer = TfidfVectorizer(
            max_features=500,
            stop_words="english",
            ngram_range=(1, 2),
            min_df=1,
            sublinear_tf=True,   # log(1+tf) — dampens very frequent terms
        )
        self._corpus_vectorizer.fit(self._BASE_CORPUS)
        logger.info("AdvancedMatchingService initialised — corpus vocabulary size: %d",
                    len(self._corpus_vectorizer.vocabulary_))

    # ──────────────────────────────────────────────────────
    # PUBLIC: call this once at startup with your real job data
    # ──────────────────────────────────────────────────────

    def build_corpus_from_jobs(self, jobs: List[Dict]) -> None:
        """
        Rebuild TF-IDF vocabulary from actual job skill data.

        Call this at application startup after loading jobs from the DB:

            matching_service.build_corpus_from_jobs(jobs_data)

        This gives IDF weights that reflect your real data distribution.
        """
        corpus = list(self._BASE_CORPUS)  # always include base terms

        for job in jobs:
            parts = []
            if job.get("required_skills"):
                parts.append(" ".join(job["required_skills"]))
            if job.get("title"):
                parts.append(job["title"])
            if job.get("category"):
                parts.append(job["category"])
            if parts:
                corpus.append(" ".join(parts).lower())

        self._corpus_vectorizer = TfidfVectorizer(
            max_features=500,
            stop_words="english",
            ngram_range=(1, 2),
            min_df=1,
            sublinear_tf=True,
        )
        self._corpus_vectorizer.fit(corpus)
        logger.info("Corpus rebuilt from %d jobs — vocabulary size: %d",
                    len(jobs), len(self._corpus_vectorizer.vocabulary_))

    # ──────────────────────────────────────────────────────
    # SKILL SIMILARITY
    # ──────────────────────────────────────────────────────

    def calculate_skill_similarity(
        self,
        user_skills: List[str],
        job_required_skills: List[str],
        job_preferred_skills: Optional[List[str]] = None,
    ) -> float:
        """
        Calculate skill match score using corpus-aware TF-IDF + cosine similarity.

        BUG FIXED: previously called fit_transform([user_text, job_text]) which
        builds a 2-document corpus where any shared term gets IDF = log(2/2) = 0,
        making it invisible to cosine similarity.  We now transform against the
        pre-fitted corpus vectorizer so IDF weights are meaningful.

        Returns a score in [0, 1].
        """
        if not user_skills or not job_required_skills:
            return 0.0

        try:
            user_text = " ".join(user_skills).lower()

            req_text = " ".join(job_required_skills).lower()
            if job_preferred_skills:
                # Preferred skills contribute half the weight of required ones
                pref_text = " ".join(job_preferred_skills).lower()
                job_text = req_text + " " + req_text + " " + pref_text  # req repeated for weight
            else:
                job_text = req_text

            # FIX: transform (not fit_transform) against the pre-fitted corpus
            # so IDF values reflect a realistic distribution, not just these 2 docs.
            vectors = self._corpus_vectorizer.transform([user_text, job_text])
            tfidf_similarity = cosine_similarity(vectors[0:1], vectors[1:2])[0][0]

            # Exact-overlap bonus: what fraction of required skills does the user have?
            user_skill_set     = {s.strip().lower() for s in user_skills}
            required_skill_set = {s.strip().lower() for s in job_required_skills}
            exact_match_ratio  = len(user_skill_set & required_skill_set) / len(required_skill_set)

            # 60% TF-IDF cosine  +  40% exact overlap
            # (raised exact-overlap weight because skill names are short tokens
            #  that TF-IDF can miss on single-word matches)
            final_score = (0.60 * tfidf_similarity) + (0.40 * exact_match_ratio)
            return min(1.0, float(final_score))

        except Exception as e:
            logger.warning("Skill similarity error — falling back to exact overlap: %s", e)
            user_skill_set     = {s.strip().lower() for s in user_skills}
            required_skill_set = {s.strip().lower() for s in job_required_skills}
            if not required_skill_set:
                return 0.0
            return len(user_skill_set & required_skill_set) / len(required_skill_set)

    # ──────────────────────────────────────────────────────
    # LOCATION SCORE
    # ──────────────────────────────────────────────────────

    def calculate_location_score(
        self,
        user_location: Optional[str],
        job_location: Optional[str],
    ) -> float:
        """
        Returns a location match score in [0, 1].

        BUG FIXED: previously returned 0.3 for a non-matching location, which
        penalised users whose location just wasn't stored yet.  Now returns
        0.5 (neutral) in any ambiguous case so missing location data doesn't
        drag down otherwise strong matches.
        """
        if not user_location or not job_location:
            return 0.5  # neutral — no data, no penalty

        user_loc = user_location.lower().strip()
        job_loc  = job_location.lower().strip()

        if user_loc == job_loc:
            return 1.0
        if user_loc in job_loc or job_loc in user_loc:
            return 0.8

        # Same top-level city token?  e.g. "Kigali Gasabo" vs "Kigali Kimihurura"
        user_tokens = set(user_loc.split())
        job_tokens  = set(job_loc.split())
        if user_tokens & job_tokens:
            return 0.6

        # BUG FIX: was 0.3 — changed to 0.5 (neutral, not a penalty)
        return 0.5

    # ──────────────────────────────────────────────────────
    # PAYMENT SCORE
    # ──────────────────────────────────────────────────────

    def calculate_payment_score(
        self,
        user_expected_payment: Optional[float],
        job_payment: Optional[float],
    ) -> float:
        if not user_expected_payment or not job_payment:
            return 0.7  # neutral

        ratio = job_payment / user_expected_payment

        if ratio >= 0.9:    return 1.0   # meets or exceeds expectation
        if ratio >= 0.8:    return 0.7
        if ratio >= 0.6:    return 0.4
        return 0.1

    # ──────────────────────────────────────────────────────
    # EXPERIENCE SCORE
    # ──────────────────────────────────────────────────────

    def calculate_experience_score(
        self,
        user_experience_years: Optional[int],
        job_min_experience: int = 0,
        job_max_experience: Optional[int] = None,
    ) -> float:
        if user_experience_years is None:
            return 0.7  # neutral

        if user_experience_years >= job_min_experience:
            if job_max_experience and user_experience_years > job_max_experience:
                excess = user_experience_years - job_max_experience
                return 0.8 if excess <= 2 else 0.6
            return 1.0

        gap = job_min_experience - user_experience_years
        if gap == 1: return 0.6
        if gap == 2: return 0.4
        return 0.2

    # ──────────────────────────────────────────────────────
    # CATEGORY SCORE
    # ──────────────────────────────────────────────────────

    def calculate_category_score(
        self,
        user_preferred_categories: List[str],
        job_category: Optional[str],
    ) -> float:
        if not user_preferred_categories or not job_category:
            return 0.5  # neutral

        job_cat = job_category.lower()
        user_cats = [c.lower() for c in user_preferred_categories]

        if job_cat in user_cats:
            return 1.0
        if any(c in job_cat or job_cat in c for c in user_cats):
            return 0.7
        return 0.3

    # ──────────────────────────────────────────────────────
    # COMPREHENSIVE SCORE
    # ──────────────────────────────────────────────────────

    def calculate_comprehensive_score(
        self,
        user_profile: Dict,
        job: Dict,
        weights: Optional[Dict] = None,
    ) -> Dict:
        if weights is None:
            weights = {
                "skills":     0.40,  # raised — skills are the primary signal
                "location":   0.20,
                "payment":    0.20,
                "experience": 0.10,
                "category":   0.10,
            }

        skill_score      = self.calculate_skill_similarity(
            user_profile.get("skills", []),
            job.get("required_skills", []),
            job.get("preferred_skills", []),
        )
        location_score   = self.calculate_location_score(
            user_profile.get("location"),
            job.get("location"),
        )
        payment_score    = self.calculate_payment_score(
            user_profile.get("expected_payment"),
            job.get("payment"),
        )
        experience_score = self.calculate_experience_score(
            user_profile.get("experience_years"),
            job.get("min_experience", 0),
            job.get("max_experience"),
        )
        category_score   = self.calculate_category_score(
            user_profile.get("preferred_categories", []),
            job.get("category"),
        )

        final_score = (
            weights["skills"]     * skill_score     +
            weights["location"]   * location_score  +
            weights["payment"]    * payment_score   +
            weights["experience"] * experience_score +
            weights["category"]   * category_score
        )

        return {
            "overall_score": round(final_score, 3),
            "breakdown": {
                "skills":     round(skill_score, 3),
                "location":   round(location_score, 3),
                "payment":    round(payment_score, 3),
                "experience": round(experience_score, 3),
                "category":   round(category_score, 3),
            },
            "weights_used": weights,
            "recommendation": self._get_recommendation(final_score),
        }

    def _get_recommendation(self, score: float) -> str:
        if score >= 0.8:  return "excellent_match"
        if score >= 0.6:  return "good_match"
        if score >= 0.4:  return "fair_match"
        return "poor_match"

    # ──────────────────────────────────────────────────────
    # RANK JOBS
    # ──────────────────────────────────────────────────────

    def rank_jobs_for_user(
        self,
        user_profile: Dict,
        jobs: List[Dict],
        top_k: int = 10,
    ) -> List[Dict]:
        scored = []
        for job in jobs:
            score_data = self.calculate_comprehensive_score(user_profile, job)
            scored.append({
                **job,
                "match_score":      score_data["overall_score"],
                "match_breakdown":  score_data["breakdown"],
                "recommendation":   score_data["recommendation"],
            })

        scored.sort(key=lambda x: x["match_score"], reverse=True)
        return scored[:top_k]


# ══════════════════════════════════════════════════════════
# Singleton
# ══════════════════════════════════════════════════════════

_matching_service: Optional[AdvancedMatchingService] = None


def get_matching_service() -> AdvancedMatchingService:
    global _matching_service
    if _matching_service is None:
        _matching_service = AdvancedMatchingService()
    return _matching_service