"""
Authentication Endpoints
=================================
Fixes applied:
  1. user.password → user.hashed_password (was crashing on every login)
  2. Register now uses Pydantic body (passwords no longer leak into logs/URLs)
  3. SECRET_KEY now reads from .env via os.getenv()
  4. id field uses uuid4 consistently (Firebase rows must be cleaned — see guide)
  5. FCM token registration endpoint added
  6. Login now accepts email OR phone number
"""
from dotenv import load_dotenv
import os

load_dotenv()
from fastapi import APIRouter, Depends, HTTPException, status
from fastapi.security import OAuth2PasswordBearer, OAuth2PasswordRequestForm
from sqlalchemy.orm import Session
from pydantic import BaseModel, EmailStr
from datetime import datetime, timedelta
from jose import JWTError, jwt
from passlib.context import CryptContext
from app.dependencies import get_current_user
import logging
import uuid

from app.database import get_db
from app.models.user import User

logger = logging.getLogger(__name__)

router = APIRouter()

# ══════════════════════════════════════════════════════════
# SECURITY CONFIGURATION
# ══════════════════════════════════════════════════════════

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

SECRET_KEY = os.getenv("SECRET_KEY")
if not SECRET_KEY:
    raise RuntimeError("SECRET_KEY is not set in .env — server will not start")

ALGORITHM = "HS256"
ACCESS_TOKEN_EXPIRE_MINUTES = 60 * 24 * 7  # 7 days

oauth2_scheme = OAuth2PasswordBearer(tokenUrl="/api/v1/auth/token")

# ══════════════════════════════════════════════════════════
# SCHEMAS
# ══════════════════════════════════════════════════════════

class RegisterRequest(BaseModel):
    email: EmailStr
    password: str
    full_name: str
    role: str               # "EMPLOYEE" or "EMPLOYER"
    phone_number: str = None
    location: str = None
    company_name: str = None


class FcmTokenRequest(BaseModel):
    fcm_token: str


# ══════════════════════════════════════════════════════════
# PASSWORD UTILITIES
# ══════════════════════════════════════════════════════════

def verify_password(plain_password: str, hashed_password: str) -> bool:
    return pwd_context.verify(plain_password, hashed_password)

def get_password_hash(password: str) -> str:
    return pwd_context.hash(password)

# ══════════════════════════════════════════════════════════
# JWT TOKEN
# ══════════════════════════════════════════════════════════

def create_access_token(data: dict, expires_delta: timedelta = None) -> str:
    to_encode = data.copy()
    expire = datetime.utcnow() + (expires_delta or timedelta(minutes=ACCESS_TOKEN_EXPIRE_MINUTES))
    to_encode.update({"exp": expire})
    return jwt.encode(to_encode, SECRET_KEY, algorithm=ALGORITHM)

def verify_token(token: str, credentials_exception):
    try:
        payload = jwt.decode(token, SECRET_KEY, algorithms=[ALGORITHM])
        email: str = payload.get("sub")
        if email is None:
            raise credentials_exception
        return email
    except JWTError:
        raise credentials_exception

# ══════════════════════════════════════════════════════════
# AUTHENTICATION
# ══════════════════════════════════════════════════════════

def _is_phone(identifier: str) -> bool:
    """
    Returns True if the identifier looks like a phone number.
    Handles:  +250788123456  /  0788123456  /  250788123456
    """
    cleaned = identifier.replace(" ", "").replace("-", "")
    digits  = cleaned.lstrip("+")
    return digits.isdigit() and len(digits) >= 9


def _normalize_phone(phone: str) -> str:
    """
    Normalizes a phone number to international format for DB lookup.
      0788123456   → +250788123456
      250788123456 → +250788123456
      +250788123456→ +250788123456  (unchanged)
    """
    cleaned = phone.replace(" ", "").replace("-", "")
    if cleaned.startswith("+"):
        return cleaned
    if cleaned.startswith("0"):
        return "+250" + cleaned[1:]
    if cleaned.startswith("250"):
        return "+" + cleaned
    return "+" + cleaned


def authenticate_user(db: Session, identifier: str, password: str):
    """
    Looks up a user by email OR phone number, then verifies the password.
    `identifier` is whatever the user typed in the login field.
    """
    user = None

    if _is_phone(identifier):
        # ── Phone number login ────────────────────────────────────────────
        normalized = _normalize_phone(identifier)
        logger.info(f"Login attempt via phone: {normalized}")

        # Build every possible format the number might be stored as in DB
        cleaned   = identifier.replace(" ", "").replace("-", "")
        local_fmt = "0" + cleaned.lstrip("+").lstrip("250") if not cleaned.startswith("0") else cleaned

        # Try: +250788...  →  0788...  →  cleaned raw  →  original input
        user = (
            db.query(User).filter(User.phone_number == normalized).first()
            or db.query(User).filter(User.phone_number == local_fmt).first()
            or db.query(User).filter(User.phone_number == cleaned).first()
            or db.query(User).filter(User.phone_number == identifier.strip()).first()
        )
        if not user:
            logger.warning(f"Login failed - phone not found: {normalized}")
            return False
    else:
        # ── Email login ───────────────────────────────────────────────────
        email = identifier.strip().lower()
        logger.info(f"Login attempt via email: {email}")
        user = db.query(User).filter(User.email == email).first()
        if not user:
            logger.warning(f"Login failed - email not found: {email}")
            return False

    if not user.hashed_password:
        logger.warning(f"Login failed - user has no password set: {user.email}")
        return False

    if not verify_password(password, user.hashed_password):
        logger.warning(f"Login failed - invalid password for: {user.email}")
        return False

    logger.info(f"✅ User authenticated: {user.email}")
    return user




# ══════════════════════════════════════════════════════════
# HELPER
# ══════════════════════════════════════════════════════════

def user_to_dict(user: User) -> dict:
    return {
        "id": user.id,
        "email": user.email,
        "full_name": user.full_name,
        "role": user.role,
        "phone_number": user.phone_number,
        "profile_picture_url": user.profile_picture_url,
        "location": user.location,
        "skills": user.skills or [],
        "available_hours": user.available_hours,
        "company_name": user.company_name,
        "created_at": user.created_at,
        "updated_at": user.updated_at,
    }

# ══════════════════════════════════════════════════════════
# ENDPOINTS
# ══════════════════════════════════════════════════════════

@router.post("/token")
async def login(
    form_data: OAuth2PasswordRequestForm = Depends(),
    db: Session = Depends(get_db)
):
    """Login — accepts email or phone number + password, returns JWT + user info."""
    logger.info(f"Login attempt: {form_data.username}")

    user = authenticate_user(db, form_data.username, form_data.password)
    if not user:
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Incorrect email/phone or password",
            headers={"WWW-Authenticate": "Bearer"},
        )

    access_token = create_access_token(data={"sub": user.email})
    logger.info(f"✅ Login successful: {user.email}")

    return {
        "access_token": access_token,
        "token_type": "bearer",
        "user": user_to_dict(user),
    }


@router.post("/register", status_code=status.HTTP_201_CREATED)
async def register(
    body: RegisterRequest,
    db: Session = Depends(get_db)
):
    """Register new user"""
    logger.info(f"Registration attempt: {body.email}")

    existing = db.query(User).filter(User.email == body.email).first()
    if existing:
        raise HTTPException(
            status_code=status.HTTP_400_BAD_REQUEST,
            detail="Email already registered"
        )

    # Also check phone uniqueness if provided
    if body.phone_number:
        normalized_phone = _normalize_phone(body.phone_number)
        phone_exists = db.query(User).filter(User.phone_number == normalized_phone).first()
        if phone_exists:
            raise HTTPException(
                status_code=status.HTTP_400_BAD_REQUEST,
                detail="Phone number already registered"
            )

    new_user = User(
        id=str(uuid.uuid4()),
        email=body.email,
        hashed_password=get_password_hash(body.password),
        full_name=body.full_name,
        role=body.role.upper().strip(),
        phone_number=_normalize_phone(body.phone_number) if body.phone_number else None,
        location=body.location,
        company_name=body.company_name if body.role.upper() == "EMPLOYER" else None,
        created_at=datetime.utcnow(),
        updated_at=datetime.utcnow(),
    )

    db.add(new_user)
    db.commit()
    db.refresh(new_user)

    logger.info(f"✅ User registered: {new_user.email}")
    access_token = create_access_token(data={"sub": new_user.email})

    return {
        "access_token": access_token,
        "token_type": "bearer",
        "user": user_to_dict(new_user),
    }


@router.post("/fcm-token", status_code=status.HTTP_200_OK)
async def update_fcm_token(
    body: FcmTokenRequest,
    current_user: User = Depends(get_current_user),
    db: Session = Depends(get_db)
):
    """Saves the device FCM token for the authenticated user."""
    logger.info(f"FCM token update for user: {current_user.email}")

    current_user.fcm_token = body.fcm_token
    current_user.updated_at = datetime.utcnow()
    db.commit()

    logger.info(f"✅ FCM token saved for: {current_user.email}")
    return {"status": "ok"}


@router.get("/me")
async def get_me(current_user: User = Depends(get_current_user)):
    """Get current authenticated user"""
    return user_to_dict(current_user)