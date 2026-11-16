"""test_fix

Revision ID: 04ed026575fe
Revises: 5879da4f05f0
Create Date: 2026-04-09 09:21:14.459918

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '04ed026575fe'
down_revision: Union[str, None] = '5879da4f05f0'
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade() -> None:
    pass


def downgrade() -> None:
    pass
