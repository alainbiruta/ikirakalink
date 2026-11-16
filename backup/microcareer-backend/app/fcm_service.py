"""
fcm_service.py
==============
Day 19 — Firebase Cloud Messaging helper.

Wraps the firebase-admin SDK so any route file can send a push notification
with a single function call.

Setup requirements
------------------
1. pip install firebase-admin

2. Download your service account key from:
   Firebase Console → Project Settings → Service Accounts → Generate new private key

3. Save it as  backend/firebase-service-account.json
   (already in .gitignore — NEVER commit it)

4. Add to your .env:
   FIREBASE_CREDENTIALS_PATH=firebase-service-account.json

Usage
-----
    from app.fcm_service import send_notification

    send_notification(
        fcm_token   = user.fcm_token,
        title       = "Application accepted!",
        body        = "You got the job for Senior Developer.",
        data        = {"type": "application_accepted", "job_id": str(job.id)}
    )
"""

import logging
import os
from typing import Optional

logger = logging.getLogger(__name__)

# ── Firebase Admin SDK initialisation ─────────────────────────────────────────
# Lazy init so the app starts even if credentials aren't present yet (dev mode).

_firebase_app = None

def _get_firebase_app():
    global _firebase_app
    if _firebase_app is not None:
        return _firebase_app

    try:
        import firebase_admin
        from firebase_admin import credentials

        creds_path = os.getenv(
            "FIREBASE_CREDENTIALS_PATH",
            "firebase-service-account.json"
        )

        if not os.path.exists(creds_path):
            logger.warning(
                f"Firebase credentials not found at '{creds_path}'. "
                "Push notifications will be skipped. "
                "Set FIREBASE_CREDENTIALS_PATH in .env to enable them."
            )
            return None

        cred = credentials.Certificate(creds_path)
        _firebase_app = firebase_admin.initialize_app(cred)
        logger.info("Firebase Admin SDK initialised ✅")
        return _firebase_app

    except ImportError:
        logger.warning("firebase-admin not installed. Run: pip install firebase-admin")
        return None
    except Exception as e:
        logger.error(f"Firebase init failed: {e}")
        return None


# ── Public API ─────────────────────────────────────────────────────────────────

def send_notification(
    fcm_token: Optional[str],
    title: str,
    body: str,
    data: Optional[dict] = None,
) -> bool:
    """
    Send a push notification to a single device.

    Parameters
    ----------
    fcm_token : str | None
        The device's FCM registration token (stored on the User model).
        Pass None to skip silently (user hasn't granted permission yet).
    title : str
        Notification title shown in the system tray.
    body : str
        Notification body text.
    data : dict | None
        Optional key/value data payload sent alongside the notification.
        Android reads this in onMessageReceived() → message.data.
        ALL values must be strings (Firebase requirement).

    Returns
    -------
    bool
        True if the message was accepted by FCM, False otherwise.
    """
    if not fcm_token:
        logger.debug("No FCM token — skipping push notification")
        return False

    app = _get_firebase_app()
    if app is None:
        return False

    try:
        from firebase_admin import messaging

        # Ensure all data values are strings (Firebase rejects non-string values)
        str_data = {k: str(v) for k, v in data.items()} if data else {}

        message = messaging.Message(
            notification=messaging.Notification(
                title=title,
                body=body,
            ),
            data=str_data,
            token=fcm_token,
            android=messaging.AndroidConfig(
                priority="high",
            ),
        )

        response = messaging.send(message)
        logger.info(f"Push sent ✅ message_id={response}")
        return True

    except Exception as e:
        # Log but never let a failed push crash the API endpoint.
        logger.error(f"FCM send failed: {e}")
        return False