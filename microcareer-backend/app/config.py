"""
Configuration Management
========================

Loads environment variables and provides typed configuration.
"""

from pydantic_settings import BaseSettings
from typing import List
import os

class Settings(BaseSettings):
    """
    Application settings loaded from environment variables.
    
    Create a .env file in the project root with these variables.
    """
    
    # Application
    ENVIRONMENT: str = "development"
    DEBUG: bool = True
    SECRET_KEY: str = "illest"
    
    # Database
    DATABASE_URL: str = "postgresql://postgres:illest@localhost:5432/microcareer"
    
    # Firebase
    FIREBASE_CREDENTIALS_PATH: str = "firebase-credentials.json"
    
    # CORS
    ALLOWED_ORIGINS: List[str] = [
        "http://localhost:3000",
        "http://10.0.2.2:8000",  # Android emulator
        "http://127.0.0.1:8000",
    ]
    
    # JWT (for custom tokens if needed)
    JWT_ALGORITHM: str = "HS256"
    ACCESS_TOKEN_EXPIRE_MINUTES: int = 60 * 24  # 24 hours
    
    # Pagination
    DEFAULT_PAGE_SIZE: int = 20
    MAX_PAGE_SIZE: int = 100
    
    # Matching Algorithm
    MIN_MATCH_SCORE: float = 0.3  # Minimum 30% match to show job
    MAX_DISTANCE_KM: float = 50.0  # Maximum distance for location matching
    
    class Config:
        env_file = ".env"
        case_sensitive = True

# Create global settings instance
settings = Settings()

# ══════════════════════════════════════════════════════════
# Usage:
# from app.config import settings
# database_url = settings.DATABASE_URL
# ══════════════════════════════════════════════════════════