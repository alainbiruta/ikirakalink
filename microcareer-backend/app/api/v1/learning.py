"""
Learning Module Endpoints
=========================

Prefix: /api/v1/learning   (registered in router.py)

Routes  — order matters: static paths ABOVE /{module_id}
  GET  /                      browse all modules
  GET  /recommendations/me    skill-gap personalised list
  GET  /progress/me           user progress summary
  GET  /{module_id}           single module detail
  POST /{module_id}/start     mark in-progress
  POST /{module_id}/progress  update progress %
  POST /{module_id}/complete  mark complete + unlock skills on user.skills
"""

from fastapi import APIRouter, Depends, HTTPException, Query
from sqlalchemy.orm import Session
from sqlalchemy import and_, or_
from typing import Optional
import uuid, logging
from datetime import datetime

from app.database import get_db
from app.dependencies import get_current_user, get_current_employee
from app.models.user import User
from app.models.learning_module import LearningModule, LearningProgress
from app.models.job import Job
from app.schemas.job import ApiResponse

logger = logging.getLogger(__name__)
router = APIRouter()


# ══════════════════════════════════════════════════════════
# PRIVATE HELPERS
# ══════════════════════════════════════════════════════════

def _get_progress(db: Session, user_id: str, module_id: str) -> Optional[LearningProgress]:
    return db.query(LearningProgress).filter(
        and_(
            LearningProgress.user_id   == user_id,
            LearningProgress.module_id == module_id
        )
    ).first()


def _module_dict(module: LearningModule, progress: Optional[LearningProgress]) -> dict:
    """Serialises module + per-user progress into the shape Android expects."""
    if progress:
        user_status   = "completed"   if progress.is_completed          else "in_progress"
        pct           = progress.progress_percent
        completed_at  = int(progress.completed_at.timestamp()       * 1000) if progress.completed_at       else None
        started_at    = int(progress.started_at.timestamp()         * 1000) if progress.started_at         else None
        last_accessed = int(progress.last_accessed_at.timestamp()   * 1000) if progress.last_accessed_at   else None
    else:
        user_status   = "not_started"
        pct           = 0
        completed_at  = started_at = last_accessed = None

    return {
        "id":                  module.id,
        "title":               module.title,
        "description":         module.description,
        "skillName":           module.skill_name,
        "duration":            module.duration,
        "contentUrl":          module.content_url,
        "contentType":         module.content_type,
        "difficulty":          module.difficulty,
        "prerequisites":       module.prerequisites or [],
        "recommendationScore": module.recommendation_score,
        "createdAt":           int(module.created_at.timestamp() * 1000),
        "isSynced":            module.is_synced,
        # progress block
        "userStatus":          user_status,
        "progressPercent":     pct,
        "startedAt":           started_at,
        "completedAt":         completed_at,
        "lastAccessedAt":      last_accessed,
    }


# ══════════════════════════════════════════════════════════
# GET /   browse all modules
# ══════════════════════════════════════════════════════════

@router.get("/", response_model=ApiResponse)
async def get_learning_modules(
    skill_name   : Optional[str] = None,
    difficulty   : Optional[str] = Query(None, pattern="^(BEGINNER|INTERMEDIATE|ADVANCED)$"),
    content_type : Optional[str] = None,
    page         : int           = Query(1,  ge=1),
    page_size    : int           = Query(20, ge=1, le=100),
    current_user : User          = Depends(get_current_user),
    db           : Session       = Depends(get_db)
):
    q = db.query(LearningModule)
    if skill_name:
        q = q.filter(LearningModule.skill_name.ilike(f"%{skill_name}%"))
    if difficulty:
        q = q.filter(LearningModule.difficulty == difficulty.upper())
    if content_type:
        q = q.filter(LearningModule.content_type == content_type.upper())

    q     = q.order_by(LearningModule.recommendation_score.desc().nullslast(),
                       LearningModule.created_at.desc())
    total = q.count()
    mods  = q.offset((page - 1) * page_size).limit(page_size).all()

    data = [_module_dict(m, _get_progress(db, current_user.id, m.id)) for m in mods]
    return ApiResponse(success=True, message=f"Found {total} modules",
                       data={"modules": data, "total": total, "page": page,
                             "pageSize": page_size, "hasMore": total > page * page_size})


# ══════════════════════════════════════════════════════════
# GET /recommendations/me   — ABOVE /{module_id}
# ══════════════════════════════════════════════════════════

@router.get("/recommendations/me", response_model=ApiResponse)
async def get_recommendations(
    limit        : int     = Query(10, ge=1, le=50),
    current_user : User    = Depends(get_current_employee),
    db           : Session = Depends(get_db)
):
    """
    Returns modules whose skill_name overlaps with skills that open jobs
    require but the employee doesn't yet have.
    Falls back to highest-scored incomplete modules when gaps are empty.
    """
    user_skills = set(current_user.skills or [])

    # 1. Compute skill gaps from open jobs near the user
    jobs_q = db.query(Job).filter(Job.status == "OPEN")
    if current_user.location:
        regional = jobs_q.filter(Job.location.ilike(f"%{current_user.location}%")).all()
        demanded = {s for j in regional for s in (j.required_skills or [])}
    else:
        demanded = set()

    if not demanded:                                    # fallback: all open jobs
        demanded = {s for j in jobs_q.limit(200).all() for s in (j.required_skills or [])}

    skill_gaps = demanded - user_skills

    # 2. Completed module IDs for this user
    done_ids = {
        row.module_id for row in
        db.query(LearningProgress.module_id).filter(
            LearningProgress.user_id      == current_user.id,
            LearningProgress.is_completed == True
        ).all()
    }

    # 3. Modules matching gaps
    recommended = []
    if skill_gaps:
        gap_filters = [LearningModule.skill_name.ilike(f"%{g}%") for g in skill_gaps]
        base_q = db.query(LearningModule).filter(or_(*gap_filters))
        if done_ids:
            base_q = base_q.filter(~LearningModule.id.in_(done_ids))
        recommended = base_q.order_by(
            LearningModule.recommendation_score.desc().nullslast()
        ).limit(limit).all()

    # 4. Pad with popular modules
    if len(recommended) < limit:
        seen = done_ids | {m.id for m in recommended}
        extra_q = db.query(LearningModule)
        if seen:
            extra_q = extra_q.filter(~LearningModule.id.in_(seen))
        extra = extra_q.order_by(
            LearningModule.recommendation_score.desc().nullslast()
        ).limit(limit - len(recommended)).all()
        recommended.extend(extra)

    result = []
    for m in recommended:
        d = _module_dict(m, _get_progress(db, current_user.id, m.id))
        matched = [g for g in skill_gaps if g.lower() in m.skill_name.lower()]
        d["recommendationReason"] = (
            f"Missing skill: {', '.join(matched)}" if matched else "Popular on MicroCareer"
        )
        result.append(d)

    return ApiResponse(success=True, message=f"{len(result)} recommendations",
                       data={"skillGaps":   list(skill_gaps),
                             "userSkills":  list(user_skills),
                             "recommended": result,
                             "total":       len(result)})


# ══════════════════════════════════════════════════════════
# GET /progress/me   — ABOVE /{module_id}
# ══════════════════════════════════════════════════════════

@router.get("/progress/me", response_model=ApiResponse)
async def get_my_progress(
    current_user : User    = Depends(get_current_user),
    db           : Session = Depends(get_db)
):
    records = db.query(LearningProgress).filter(
        LearningProgress.user_id == current_user.id
    ).all()

    total_started   = len(records)
    total_completed = sum(1 for r in records if r.is_completed)

    by_skill: dict = {}
    for rec in records:
        mod = db.query(LearningModule).filter(LearningModule.id == rec.module_id).first()
        if not mod:
            continue
        e = by_skill.setdefault(mod.skill_name, {"started": 0, "completed": 0})
        e["started"] += 1
        if rec.is_completed:
            e["completed"] += 1

    return ApiResponse(success=True, message="Progress retrieved",
                       data={"totalStarted":    total_started,
                             "totalCompleted":   total_completed,
                             "completionRate":   round((total_completed / total_started * 100)
                                                       if total_started else 0, 1),
                             "inProgress":       total_started - total_completed,
                             "progressBySkill":  by_skill})


# ══════════════════════════════════════════════════════════
# GET /{module_id}
# ══════════════════════════════════════════════════════════

@router.get("/{module_id}", response_model=ApiResponse)
async def get_module_detail(
    module_id    : str,
    current_user : User    = Depends(get_current_user),
    db           : Session = Depends(get_db)
):
    module = db.query(LearningModule).filter(LearningModule.id == module_id).first()
    if not module:
        raise HTTPException(404, "Learning module not found")

    prog = _get_progress(db, current_user.id, module_id)
    if prog:
        prog.last_accessed_at = datetime.utcnow()
        db.commit()

    return ApiResponse(success=True, message="Module retrieved",
                       data=_module_dict(module, prog))


# ══════════════════════════════════════════════════════════
# POST /{module_id}/start
# ══════════════════════════════════════════════════════════

@router.post("/{module_id}/start", response_model=ApiResponse)
async def start_module(
    module_id    : str,
    current_user : User    = Depends(get_current_user),
    db           : Session = Depends(get_db)
):
    module = db.query(LearningModule).filter(LearningModule.id == module_id).first()
    if not module:
        raise HTTPException(404, "Learning module not found")

    prog = _get_progress(db, current_user.id, module_id)
    if not prog:
        prog = LearningProgress(
            id               = str(uuid.uuid4()),
            user_id          = current_user.id,
            module_id        = module_id,
            progress_percent = 1,
            started_at       = datetime.utcnow(),
            last_accessed_at = datetime.utcnow(),
            is_completed     = False
        )
        db.add(prog)
        db.commit()
        db.refresh(prog)
        logger.info(f"Started module {module_id} for {current_user.email}")

    return ApiResponse(success=True, message="Module started",
                       data=_module_dict(module, prog))


# ══════════════════════════════════════════════════════════
# POST /{module_id}/progress
# ══════════════════════════════════════════════════════════

@router.post("/{module_id}/progress", response_model=ApiResponse)
async def update_progress(
    module_id        : str,
    progress_percent : int     = Query(..., ge=0, le=100),
    current_user     : User    = Depends(get_current_user),
    db               : Session = Depends(get_db)
):
    module = db.query(LearningModule).filter(LearningModule.id == module_id).first()
    if not module:
        raise HTTPException(404, "Learning module not found")

    prog = _get_progress(db, current_user.id, module_id)
    if not prog:
        prog = LearningProgress(
            id               = str(uuid.uuid4()),
            user_id          = current_user.id,
            module_id        = module_id,
            progress_percent = progress_percent,
            started_at       = datetime.utcnow(),
            last_accessed_at = datetime.utcnow(),
            is_completed     = progress_percent >= 100
        )
        db.add(prog)
    else:
        prog.progress_percent = progress_percent
        prog.last_accessed_at = datetime.utcnow()
        if progress_percent >= 100 and not prog.is_completed:
            prog.is_completed  = True
            prog.completed_at  = datetime.utcnow()

    db.commit()
    return ApiResponse(success=True, message=f"Progress: {progress_percent}%",
                       data={"moduleId": module_id, "progressPercent": progress_percent,
                             "isCompleted": prog.is_completed})


# ══════════════════════════════════════════════════════════
# POST /{module_id}/complete   ← THE SKILL-UNLOCK ENDPOINT
# ══════════════════════════════════════════════════════════

@router.post("/{module_id}/complete", response_model=ApiResponse)
async def complete_module(
    module_id    : str,
    current_user : User    = Depends(get_current_employee),
    db           : Session = Depends(get_db)
):
    """
    Marks the module complete AND adds module.skill_name to user.skills.

    Response includes:
      newSkills      — list of skill names just added to the profile
      newMatchCount  — how many OPEN jobs the user now matches
                       (so Android can show "You now match X more jobs")
    """
    module = db.query(LearningModule).filter(LearningModule.id == module_id).first()
    if not module:
        raise HTTPException(404, "Learning module not found")

    # 1. Upsert progress record
    prog = _get_progress(db, current_user.id, module_id)
    if not prog:
        prog = LearningProgress(
            id               = str(uuid.uuid4()),
            user_id          = current_user.id,
            module_id        = module_id,
            progress_percent = 100,
            started_at       = datetime.utcnow(),
            completed_at     = datetime.utcnow(),
            last_accessed_at = datetime.utcnow(),
            is_completed     = True
        )
        db.add(prog)
    else:
        prog.progress_percent = 100
        prog.is_completed     = True
        prog.completed_at     = datetime.utcnow()
        prog.last_accessed_at = datetime.utcnow()

    # 2. Add module skill_name to user.skills (deduplicated)
    skill_to_add = module.skill_name.strip().lower()
    existing     = [s.strip().lower() for s in (current_user.skills or [])]
    newly_added  = []

    if skill_to_add and skill_to_add not in existing:
        current_user.skills = list(current_user.skills or []) + [module.skill_name]
        newly_added         = [module.skill_name]
        logger.info(f"✅ Skill '{module.skill_name}' added to {current_user.email}")

    db.commit()

    # 3. Count how many open jobs user now matches (simple skill overlap)
    open_jobs     = db.query(Job).filter(Job.status == "OPEN").limit(500).all()
    updated_skills = set(s.strip().lower() for s in (current_user.skills or []))
    match_count   = sum(
        1 for job in open_jobs
        if any(s.strip().lower() in updated_skills for s in (job.required_skills or []))
    )

    logger.info(f"Module {module_id} completed by {current_user.email}. "
                f"New skills: {newly_added}. Match count: {match_count}")

    return ApiResponse(
        success = True,
        message = "Module completed! Skills updated.",
        data    = {
            "moduleId":      module_id,
            "moduleName":    module.title,
            "newSkills":     newly_added,        # shown in Android dialog
            "newMatchCount": match_count,
            "completedAt":   int(prog.completed_at.timestamp() * 1000),
        }
    )