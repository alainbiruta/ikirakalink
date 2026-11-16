"""
API Router
==========

Main router that includes all API endpoints.
Updated with applications and learning modules.
"""

from fastapi import APIRouter

from app.api.v1 import users
from app.api.v1 import jobs_employee, jobs_employer
from app.api.v1 import applications
from app.api.v1 import learning
from app.api.v1 import sync
from app.api.v1 import reviews
from app.api.v1 import work_payments
from app.api.v1 import admin
from app.api.v1 import analytics
from app.api.v1 import matching
from app.api.v1 import auth 

api_router = APIRouter()
# ══════════════════════════════════════════════════════════
# Admin
# ══════════════════════════════════════════════════════════

api_router.include_router(
    admin.router,
    prefix="/admin",
    tags=["admin"]
)

# ══════════════════════════════════════════════════════════
# ══════════════════════════════════════════════════════════
# AUTHENTICATION (NO PREFIX - OAuth2 requires /token endpoint)
# ══════════════════════════════════════════════════════════
 
api_router.include_router(
    auth.router,
    prefix="/auth",
    tags=["authentication"]
)
 
# ══════════════════════════════════════════════════════════
# EXISTING ROUTERS (Keep as is)
# ══════════════════════════════════════════════════════════
# ══════════════════════════════════════════════════════════
# Analytics
# ══════════════════════════════════════════════════════════

api_router.include_router(
    analytics.router,
    prefix="/analytics",
    tags=["analytics"]
)
# ══════════════════════════════════════════════════════════
# ══════════════════════════════════════════════════════════
# User Endpoints
# ══════════════════════════════════════════════════════════

api_router.include_router(
    users.router,
    prefix="/users",
    tags=["users"]
)

# CORRECT ORDER — employer first
# ══════════════════════════════════════════════════════════
# Job Endpoints - Employer
# ══════════════════════════════════════════════════════════

api_router.include_router(
    jobs_employer.router,
    prefix="/employer/jobs",
    tags=["jobs-employer"]  
)

# ══════════════════════════════════════════════════════════
# Job Endpoints - Employee
# ══════════════════════════════════════════════════════════

api_router.include_router(
    jobs_employee.router,
    prefix="/jobs",
    tags=["jobs-employee"]
)

# ══════════════════════════════════════════════════════════
# Application Endpoints
# ══════════════════════════════════════════════════════════

api_router.include_router(
    applications.router,
    prefix="/applications",
    tags=["applications"]
)

# ══════════════════════════════════════════════════════════
# Learning Module Endpoints
# ══════════════════════════════════════════════════════════

api_router.include_router(
    learning.router,
    prefix="/learning",
    tags=["learning"]
)

# ══════════════════════════════════════════════════════════
# Sync Endpoints
# ══════════════════════════════════════════════════════════

api_router.include_router(
    sync.router,
    prefix="/sync",
    tags=["sync"]
)
# ══════════════════════════════════════════════════════════
# Reviews Endpoints
# ══════════════════════════════════════════════════════════

api_router.include_router(
    reviews.router,
    prefix="/reviews",
    tags=["reviews"]
)
# ══════════════════════════════════════════════════════════
# Reviews Endpoints
# ══════════════════════════════════════════════════════════
api_router.include_router(
    work_payments.router,
    prefix="/work",
    tags=["work-payments"]
)
# ══════════════════════════════════════════════════════════
# Matching
# ══════════════════════════════════════════════════════════
api_router.include_router(
    matching.router,
    prefix="/matching",
    tags=["matching"]
)
# ══════════════════════════════════════════════════════════
# ══════════════════════════════════════════════════════════
# Health Check
# ══════════════════════════════════════════════════════════

@api_router.get("/health")
async def health_check():
    """Health check endpoint"""
    return {
        "status": "healthy",
        "message": "MicroCareer API is running",
        "version": "1.0.0"
    }

# ══════════════════════════════════════════════════════════

