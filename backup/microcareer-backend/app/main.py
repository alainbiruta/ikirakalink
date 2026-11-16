"""
MicroCareer Backend - Main Application
=======================================

FastAPI application for MicroCareer job matching platform.

Features:
- RESTful API for mobile app
- Firebase Authentication
- PostgreSQL database
- Job matching algorithm
- Learning module recommendations
- Offline sync support
"""
from dotenv import load_dotenv
load_dotenv()
import os
from fastapi import FastAPI, Request                          # ← add Request
from fastapi.middleware.cors import CORSMiddleware
from fastapi.responses import JSONResponse
from slowapi import Limiter, _rate_limit_exceeded_handler    # ← add
from slowapi.util import get_remote_address                  # ← add
from slowapi.errors import RateLimitExceeded                 # ← add
import logging

from app.config import settings
from app.database import engine, Base
from app.api.router import api_router
from app.api.v1 import auth
from fastapi.exceptions import RequestValidationError
from fastapi.responses import JSONResponse


logging.basicConfig(
    level=logging.INFO if settings.DEBUG else logging.WARNING,
    format="%(asctime)s - %(name)s - %(levelname)s - %(message)s"
)
logger = logging.getLogger(__name__)

# ══════════════════════════════════════════════════════════
# Rate Limiter
# ══════════════════════════════════════════════════════════

limiter = Limiter(key_func=get_remote_address, default_limits=["200/minute"])

# ══════════════════════════════════════════════════════════
# App
# ══════════════════════════════════════════════════════════

app = FastAPI(
    title="MicroCareer API",
    description="Backend API for MicroCareer job matching platform",
    version="1.0.0",
    docs_url="/docs" if settings.DEBUG else None,
    redoc_url="/redoc" if settings.DEBUG else None,
    redirect_slashes=False
)

app.state.limiter = limiter                                                    # ← add
app.add_exception_handler(RateLimitExceeded, _rate_limit_exceeded_handler)    # ← add

# ══════════════════════════════════════════════════════════
# CORS
# ══════════════════════════════════════════════════════════

app.add_middleware(
    CORSMiddleware,
    allow_origins=[
        "http://localhost:3000",
        "http://127.0.0.1:3000",
        "http://localhost:5500",
        "http://127.0.0.1:5500",
        "null"
    ],
    allow_credentials=True,
    allow_methods=["*"],
    allow_headers=["*"],
)

# ══════════════════════════════════════════════════════════
# Startup & Shutdown
# ══════════════════════════════════════════════════════════
@app.exception_handler(RequestValidationError)
async def validation_exception_handler(request, exc):
    import logging
    logging.getLogger("VALIDATION").error(f"422 body: {exc.errors()}")
    return JSONResponse(status_code=422, content={"detail": exc.errors()})
@app.on_event("startup")
async def startup_event():
    logger.info("🚀 Starting MicroCareer Backend...")
    logger.info(f"Environment: {settings.ENVIRONMENT}")
    logger.info(f"Debug mode: {settings.DEBUG}")
    if settings.DEBUG:
        Base.metadata.create_all(bind=engine)
        logger.info("✅ Database tables created/verified")
    logger.info("✅ MicroCareer Backend started successfully!")

@app.on_event("shutdown")
async def shutdown_event():
    logger.info("👋 Shutting down MicroCareer Backend...")

# ══════════════════════════════════════════════════════════
# Health Check
# ══════════════════════════════════════════════════════════

@app.get("/")
async def root():
    return {
        "name": "MicroCareer API",
        "version": "1.0.0",
        "status": "operational",
        "docs": "/docs" if settings.DEBUG else "disabled",
    }

@app.get("/health")
async def health_check():
    return {
        "status": "healthy",
        "environment": settings.ENVIRONMENT,
        "database": "connected",
    }

# ══════════════════════════════════════════════════════════
# Routes
# ══════════════════════════════════════════════════════════

app.include_router(api_router, prefix="/api/v1")

# ══════════════════════════════════════════════════════════
# Error Handlers
# ══════════════════════════════════════════════════════════

@app.exception_handler(Exception)
async def global_exception_handler(request, exc):
    logger.error(f"Unhandled exception: {str(exc)}", exc_info=True)
    return JSONResponse(
        status_code=500,
        content={
            "success": False,
            "message": "Internal server error",
            "error": str(exc) if settings.DEBUG else "An error occurred"
        }
    )