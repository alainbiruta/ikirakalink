"""
Firebase Authentication Utility
================================

Firebase Admin SDK integration for user authentication.
Verifies Firebase ID tokens from Android app.
"""

import firebase_admin
from firebase_admin import credentials, auth
from fastapi import HTTPException, status
import logging
import os

from app.config import settings

logger = logging.getLogger(__name__)

# ══════════════════════════════════════════════════════════
# Initialize Firebase Admin SDK
# ══════════════════════════════════════════════════════════

try:
    # Check if credentials file exists
    if not os.path.exists(settings.FIREBASE_CREDENTIALS_PATH):
        logger.warning(f"Firebase credentials not found at: {settings.FIREBASE_CREDENTIALS_PATH}")
        logger.warning("Authentication will not work without Firebase credentials!")
        firebase_app = None
    else:
        # Initialize Firebase Admin SDK
        cred = credentials.Certificate(settings.FIREBASE_CREDENTIALS_PATH)
        firebase_app = firebase_admin.initialize_app(cred)
        logger.info("✅ Firebase Admin SDK initialized successfully")
except Exception as e:
    logger.error(f"Failed to initialize Firebase Admin SDK: {str(e)}")
    firebase_app = None

# ══════════════════════════════════════════════════════════
# Token Verification
# ══════════════════════════════════════════════════════════

async def verify_firebase_token(token: str) -> dict:
    """
    Verify Firebase ID token from Android app.
    
    Args:
        token: Firebase ID token (from Authorization header)
        
    Returns:
        dict: Decoded token with user information
        
    Raises:
        HTTPException: If token is invalid or expired
        
    Example token payload:
    {
        "uid": "firebase_user_id",
        "email": "user@example.com",
        "name": "User Name",
        "picture": "https://...",
        "email_verified": true
    }
    """
    if not firebase_app:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail="Firebase Admin SDK not initialized"
        )
    
    try:
        # Remove 'Bearer ' prefix if present
        if token.startswith('Bearer '):
            token = token[7:]
        
        # Verify the token
        decoded_token = auth.verify_id_token(token)
        
        logger.info(f"Token verified for user: {decoded_token.get('email')}")
        
        return decoded_token
        
    except auth.InvalidIdTokenError:
        logger.warning("Invalid Firebase token")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Invalid authentication token"
        )
    except auth.ExpiredIdTokenError:
        logger.warning("Expired Firebase token")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail="Authentication token has expired"
        )
    except Exception as e:
        logger.error(f"Token verification error: {str(e)}")
        raise HTTPException(
            status_code=status.HTTP_401_UNAUTHORIZED,
            detail=f"Authentication failed: {str(e)}"
        )

# ══════════════════════════════════════════════════════════
# User Info Retrieval
# ══════════════════════════════════════════════════════════

async def get_firebase_user(uid: str) -> dict:
    """
    Get user information from Firebase by UID.
    
    Args:
        uid: Firebase user ID
        
    Returns:
        dict: User information from Firebase
    """
    if not firebase_app:
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail="Firebase Admin SDK not initialized"
        )
    
    try:
        user = auth.get_user(uid)
        return {
            "uid": user.uid,
            "email": user.email,
            "displayName": user.display_name,
            "photoURL": user.photo_url,
            "phoneNumber": user.phone_number,
            "emailVerified": user.email_verified,
        }
    except auth.UserNotFoundError:
        raise HTTPException(
            status_code=status.HTTP_404_NOT_FOUND,
            detail="User not found in Firebase"
        )
    except Exception as e:
        logger.error(f"Error fetching Firebase user: {str(e)}")
        raise HTTPException(
            status_code=status.HTTP_500_INTERNAL_SERVER_ERROR,
            detail="Failed to fetch user information"
        )

# ══════════════════════════════════════════════════════════