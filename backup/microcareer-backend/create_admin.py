"""
Create Admin User Script
========================
Run from your project root (microcareer-backend/):
    python create_admin.py
"""

import sys
import os
import uuid
from datetime import datetime
from dotenv import load_dotenv

load_dotenv()

from sqlalchemy import text
from app.database import SessionLocal
from passlib.context import CryptContext

pwd_context = CryptContext(schemes=["bcrypt"], deprecated="auto")

ADMIN_FULL_NAME = "Super Admin"
ADMIN_EMAIL     = "admin@microcareer.rw"
ADMIN_PASSWORD  = "Admin@MicroCareer"   # <- change this before running

def create_admin():
    db = SessionLocal()

    try:
        # Check if admin already exists
        result = db.execute(
            text("SELECT id, role FROM users WHERE email = :email"),
            {"email": ADMIN_EMAIL}
        ).fetchone()

        if result:
            print(f"[!] User '{ADMIN_EMAIL}' already exists. Role: {result.role}")
            if result.role != "ADMIN":
                db.execute(
                    text("UPDATE users SET role = 'ADMIN' WHERE email = :email"),
                    {"email": ADMIN_EMAIL}
                )
                db.commit()
                print("    Role promoted to ADMIN.")
            else:
                print("    Already an ADMIN. Nothing to do.")
            return

        # Insert using raw SQL to handle PostgreSQL VARCHAR[] skills column correctly
        admin_id = str(uuid.uuid4())
        hashed_pw = pwd_context.hash(ADMIN_PASSWORD)
        now = datetime.utcnow()

        db.execute(text("""
            INSERT INTO users (
                id, email, full_name, hashed_password, role,
                skills, rating, completed_jobs, is_synced,
                created_at, updated_at
            )
            VALUES (
                :id, :email, :full_name, :hashed_password, :role,
                '{}', 0.0, 0, true,
                :created_at, :updated_at
            )
        """), {
            "id":              admin_id,
            "email":           ADMIN_EMAIL,
            "full_name":       ADMIN_FULL_NAME,
            "hashed_password": hashed_pw,
            "role":            "ADMIN",
            "created_at":      now,
            "updated_at":      now,
        })

        db.commit()

        print("Admin user created successfully!")
        print(f"   Full name : {ADMIN_FULL_NAME}")
        print(f"   Email     : {ADMIN_EMAIL}")
        print(f"   Role      : ADMIN")
        print(f"   ID        : {admin_id}")
        print()
        print("Login with:")
        print(f"   Email     : {ADMIN_EMAIL}")
        print(f"   Password  : {ADMIN_PASSWORD}")
        print()
        print("Change the password after first login!")

    except Exception as e:
        db.rollback()
        print(f"Error: {e}")
        sys.exit(1)

    finally:
        db.close()


if __name__ == "__main__":
    create_admin()