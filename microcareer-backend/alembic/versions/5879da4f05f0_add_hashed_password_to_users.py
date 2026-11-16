"""add hashed_password to users

Revision ID: 5879da4f05f0
Revises: 
Create Date: 2026-04-09 09:05:32.579644

"""
from typing import Sequence, Union

from alembic import op
import sqlalchemy as sa


# revision identifiers, used by Alembic.
revision: str = '5879da4f05f0'
down_revision: Union[str, None] = None
branch_labels: Union[str, Sequence[str], None] = None
depends_on: Union[str, Sequence[str], None] = None


def upgrade():
    op.add_column(
        'users',
        sa.Column('hashed_password', sa.String(), nullable=True)
    )

def downgrade():
    op.drop_column('users', 'hashed_password')
