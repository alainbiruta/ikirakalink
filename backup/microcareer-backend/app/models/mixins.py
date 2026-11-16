from sqlalchemy import Column, DateTime
from sqlalchemy.sql import func


class TimestampMixin:
    """
    Reusable mixin that adds timestamp fields:
    - created_at: set once when row is created
    - updated_at: auto-updates when row is modified (ORM-level)
    """

    created_at = Column(
        DateTime(timezone=True),
        server_default=func.now(),
        nullable=False
    )

    updated_at = Column(
        DateTime(timezone=True),
        server_default=func.now(),
        onupdate=func.now(),
        nullable=False
    )