"""
Dependencies
============

FastAPI dependency injection functions.
Includes role-based access control.

Uses JWT verification throughout — same system as /auth/token and /auth/register.
"""
from dotenv import load_dotenv
load_dotenv()

import logging
import os

from fastapi import Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer
from jose import JWTError, jwt
from sqlalchemy.orm import Session

from app.database import get_db
from app.models.user import User

logger = logging.getLogger(__name__)

# ──────────────────────────────────────────────────────────────────────────────
# JWT Configuration  (must match auth.py)
# ──────────────────────────────────────────────────────────────────────────────

SECRET_KEY = os.getenv("SECRET_KEY")
ALGORITHM = "HS256"

# tokenUrl tells Swagger UI where the login form posts to
oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/api/v1/auth/token")


# ──────────────────────────────────────────────────────────────────────────────
# Core JWT dependency — used by every protected endpoint
# ──────────────────────────────────────────────────────────────────────────────

async def get_current_user(
    token: str = Depends(oauth2_scheme),
    db: Session = Depends(get_db),
) -> User:
    """
    Decode JWT, look up the user, normalise their role, and return them.
    Raises HTTP 401 if the token is missing, invalid, or expired.
    Raises HTTP 401 if the email in the token doesn't match any user.
    """
    log = logging.getLogger("AUTH_DEBUG")

    log.info(f"[1] Token arrived: {'YES → ' + token[:30] if token else 'NO TOKEN AT ALL'}")
    log.info(f"[2] SECRET_KEY: {'SET → ' + SECRET_KEY[:8] + '...' if SECRET_KEY else 'NONE ← BUG'}")

    credentials_exception = HTTPException(
        status_code=status.HTTP_401_UNAUTHORIZED,
        detail="Could not validate credentials",
        headers={"WWW-Authenticate": "Bearer"},
    )

    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        email: str = payload.get("sub")
        log.info(f"[3] Decoded email: {email}")
        if email is None:
            log.error("[4] FAIL: no 'sub' claim in token")
            raise credentials_exception
    except JWTError as e:
        log.error(f"[4] FAIL JWTError: {e}")
        raise credentials_exception

    user = db.query(User).filter(User.email == email).first()
    log.info(f"[5] User in DB: {'YES' if user else 'NO ← email not found'}")
    if user is None:
        raise credentials_exception

    # Normalise role so "employer", " EMPLOYER", "Employer" all work identically.
    # This is done here once rather than in every role-check function.
    user.role = (user.role or "").strip().upper()

    return user


# ──────────────────────────────────────────────────────────────────────────────
# Role-Based Access Control
# ──────────────────────────────────────────────────────────────────────────────

def require_role(required_role: str):
    """
    Dependency factory for role-based access control.

    Usage:
        @router.post("/jobs")
        async def create_job(
            current_user: User = Depends(require_role("EMPLOYER"))
        ):
    """
    async def role_checker(
        current_user: User = Depends(get_current_user),
    ) -> User:
        if current_user.role != required_role.upper():
            logger.warning(
                f"Access denied: {current_user.email} "
                f"(role={current_user.role!r}) tried {required_role!r}-only endpoint"
            )
            raise HTTPException(
                status_code=status.HTTP_403_FORBIDDEN,
                detail=f"This endpoint requires {required_role} role",
            )
        return current_user

    return role_checker


# ──────────────────────────────────────────────────────────────────────────────
# Convenience role dependencies
# ──────────────────────────────────────────────────────────────────────────────

def get_current_employee(
    current_user: User = Depends(get_current_user),
) -> User:
    """Ensure the current user is an EMPLOYEE."""
    if not current_user.is_employee():
        logger.warning(
            f"Access denied: {current_user.email} "
            f"(role={current_user.role!r}) tried employee-only endpoint"
        )
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="This endpoint is only for employees",
        )
    return current_user


def get_current_employer(
    current_user: User = Depends(get_current_user),
) -> User:
    """Ensure the current user is an EMPLOYER."""
    if not current_user.is_employer():
        logger.warning(
            f"Access denied: {current_user.email} "
            f"(role={current_user.role!r}) tried employer-only endpoint"
        )
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="This endpoint is only for employers",
        )
    return current_user


def get_current_admin(
    current_user: User = Depends(get_current_user),
) -> User:
    """Ensure the current user is an ADMIN."""
    if not current_user.is_admin():
        logger.warning(
            f"Access denied: {current_user.email} "
            f"(role={current_user.role!r}) tried admin-only endpoint"
        )
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="This endpoint requires admin privileges",
        )
    return current_user


# ──────────────────────────────────────────────────────────────────────────────
# Optional Authentication
# ──────────────────────────────────────────────────────────────────────────────

async def get_current_user_optional(
    token: str = Depends(oauth2_scheme),
    db: Session = Depends(get_db),
) -> User | None:
    """
    Returns the current user if the token is valid, None otherwise.
    Use for endpoints that work both with and without authentication.
    """
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        email: str = payload.get("sub")
        if not email:
            return None
        user = db.query(User).filter(User.email == email).first()
        if user:
            user.role = (user.role or "").strip().upper()
        return user
    except Exception:
        return None


# ──────────────────────────────────────────────────────────────────────────────
# Admin-specific dependencies
# ──────────────────────────────────────────────────────────────────────────────

async def get_admin_user(
    current_user: User = Depends(get_current_user),
) -> User:
    if current_user.role != "ADMIN":
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Admin access required",
        )
    return current_user


async def get_admin_or_moderator(
    current_user: User = Depends(get_current_user),
) -> User:
    if current_user.role not in ("ADMIN", "MODERATOR"):
        raise HTTPException(
            status_code=status.HTTP_403_FORBIDDEN,
            detail="Admin or moderator access required",
        )
    return current_user