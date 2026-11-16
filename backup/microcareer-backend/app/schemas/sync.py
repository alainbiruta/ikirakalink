"""
Sync Schemas
============

Pydantic models for sync API requests and responses.
"""

from pydantic import BaseModel, Field
from typing import List, Optional, Dict, Any
from datetime import datetime

# ══════════════════════════════════════════════════════════
# Sync Schemas
# ══════════════════════════════════════════════════════════

class SyncRequest(BaseModel):
    """Schema for sync request"""
    last_sync_timestamp: Optional[int] = None  # Unix timestamp in milliseconds
    unsynced_applications: Optional[List[Dict]] = []
    unsynced_saved_jobs: Optional[List[str]] = []
    unsynced_progress: Optional[List[Dict]] = []

class SyncResponse(BaseModel):
    """Schema for sync response"""
    sync_timestamp: int
    server_time: str
    download: Dict[str, Any]
    upload: Dict[str, int]
    summary: Dict[str, int]

class ApiResponse(BaseModel):
    """Standard API response wrapper"""
    success: bool
    message: str
    data: Optional[dict] = None

# ══════════════════════════════════════════════════════════