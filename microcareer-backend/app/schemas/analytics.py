"""
Analytics Schemas
=================

Pydantic schemas for advanced analytics endpoints.
Place this in: app/schemas/analytics.py
"""

from pydantic import BaseModel, Field
from typing import Optional, List, Dict
from datetime import datetime

# ══════════════════════════════════════════════════════════
# Time-Series Schemas
# ══════════════════════════════════════════════════════════

class TimeSeriesDataPoint(BaseModel):
    """Single data point in time series"""
    date: str
    value: int
    label: Optional[str] = None

class TimeSeriesData(BaseModel):
    """Time series dataset"""
    metric: str
    period: str  # daily, weekly, monthly
    data_points: List[TimeSeriesDataPoint]
    total: int
    average: float
    trend: str  # up, down, stable

class GrowthMetrics(BaseModel):
    """Growth analysis over time"""
    metric_name: str
    current_period: int
    previous_period: int
    growth_rate: float  # Percentage
    growth_direction: str  # up, down, stable

# ══════════════════════════════════════════════════════════
# Geographic Analytics Schemas
# ══════════════════════════════════════════════════════════

class LocationStats(BaseModel):
    """Statistics for a specific location"""
    location: str
    jobs_count: int
    users_count: int
    applications_count: int
    avg_payment: float
    completion_rate: float

class GeographicDistribution(BaseModel):
    """Geographic distribution of platform activity"""
    by_location: List[LocationStats]
    total_locations: int
    top_location: str
    coverage_percentage: float

# ══════════════════════════════════════════════════════════
# Performance Metrics Schemas
# ══════════════════════════════════════════════════════════

class FunnelStage(BaseModel):
    """Single stage in conversion funnel"""
    stage: str
    count: int
    percentage: float
    drop_off_rate: Optional[float] = None

class ConversionFunnel(BaseModel):
    """Complete conversion funnel analysis"""
    funnel_name: str
    stages: List[FunnelStage]
    overall_conversion_rate: float
    total_started: int
    total_completed: int

class PerformanceMetric(BaseModel):
    """Single performance metric"""
    metric_name: str
    value: float
    unit: str
    benchmark: Optional[float] = None
    status: str  # good, warning, critical

# ══════════════════════════════════════════════════════════
# Cohort Analysis Schemas
# ══════════════════════════════════════════════════════════

class CohortData(BaseModel):
    """Cohort analysis data"""
    cohort_name: str  # e.g., "Jan 2024"
    cohort_size: int
    retention_rates: Dict[str, float]  # {period: rate}
    activity_score: float

# ══════════════════════════════════════════════════════════
# Export Schemas
# ══════════════════════════════════════════════════════════

class ExportRequest(BaseModel):
    """Request for data export"""
    report_type: str = Field(..., description="users, jobs, payments, reviews")
    format: str = Field("csv", description="csv or json")
    date_from: Optional[str] = None
    date_to: Optional[str] = None
    filters: Optional[Dict] = None

# ══════════════════════════════════════════════════════════
# API Response
# ══════════════════════════════════════════════════════════

class AnalyticsApiResponse(BaseModel):
    """Standard analytics API response"""
    success: bool
    message: str
    data: Optional[dict] = None