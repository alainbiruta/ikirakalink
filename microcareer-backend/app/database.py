"""
Database Configuration
======================

SQLAlchemy setup for PostgreSQL database.
"""

from sqlalchemy import create_engine
from sqlalchemy.ext.declarative import declarative_base
from sqlalchemy.orm import sessionmaker, Session
from typing import Generator
import logging

from app.config import settings

logger = logging.getLogger(__name__)

# ══════════════════════════════════════════════════════════
# Database Engine
# ══════════════════════════════════════════════════════════

engine = create_engine(
    settings.DATABASE_URL,
    pool_pre_ping=True,  # Verify connections before using
    echo=settings.DEBUG,  # Log SQL queries in debug mode
)

# Session factory
SessionLocal = sessionmaker(
    autocommit=False,
    autoflush=False,
    bind=engine
)

# Base class for models
Base = declarative_base()

# ══════════════════════════════════════════════════════════
# Dependency Injection
# ══════════════════════════════════════════════════════════

def get_db() -> Generator[Session, None, None]:
    """
    Dependency that provides a database session.
    
    Usage in routes:
    @router.get("/users")
    def get_users(db: Session = Depends(get_db)):
        users = db.query(User).all()
        return users
    """
    db = SessionLocal()
    try:
        yield db
    finally:
        db.close()

# ══════════════════════════════════════════════════════════
# Database Utilities
# ══════════════════════════════════════════════════════════

def init_db():
    """
    Initialize database - create all tables.
    
    Note: In production, use Alembic migrations instead.
    This is mainly for development/testing.
    """
    logger.info("Creating database tables...")
    Base.metadata.create_all(bind=engine)
    logger.info("✅ Database tables created")

def drop_db():
    """
    Drop all tables - USE WITH CAUTION!
    Only for development/testing.
    """
    if settings.ENVIRONMENT == "production":
        raise Exception("Cannot drop database in production!")
    
    logger.warning("Dropping all database tables...")
    Base.metadata.drop_all(bind=engine)
    logger.info("✅ Database tables dropped")

# ══════════════════════════════════════════════════════════