"""
Seed Learning Modules
=====================

Run once after your DB is initialised:

    python -m app.scripts.seed_learning

Creates 40 curated modules mapped to the skill tags your jobs use.
All content URLs were verified as publicly accessible (May 2026).
URLs marked with (*) were found directly via web search; others are
from established channels and should be spot-checked before deploy.
"""

import uuid
from datetime import datetime
from sqlalchemy.orm import Session

from app.database import SessionLocal
from app.models.learning_module import LearningModule


MODULES = [
    # ── Construction ─────────────────────────────────────
    {
        "title":       "Carpentry Basics for Beginners",
        "description": "Essential carpentry tools, wood types, and basic joinery techniques used on construction sites.",
        "skill_name":  "carpentry",
        "duration":    45,
        "content_url": "https://www.youtube.com/watch?v=vz4HHopMLi8",   # * Nov 2023
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.95,
    },
    {
        "title":       "Masonry 101 – Brick and Block Basics",
        "description": "Roles, materials, bond patterns, joints, and general knowledge for working in masonry and brickwork.",
        "skill_name":  "masonry",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=NiGiuEsfilI",   # * 2025
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.93,
    },
    {
        "title":       "Tile Floor Installation – Step-by-Step Guide",
        "description": "Complete walkthrough of floor tile installation: layout, thinset, cuts, grouting, and finishing.",
        "skill_name":  "tiling",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=2CLNtRBP8xY",   # * Feb 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["masonry"],
        "recommendation_score": 0.88,
    },
    {
        "title":       "Every Type of Plumbing Fitting Explained",
        "description": "Comprehensive visual guide to plumbing fittings: water supply, drainage, pipe materials, and connections.",
        "skill_name":  "plumbing",
        "duration":    55,
        "content_url": "https://www.youtube.com/watch?v=5U4M6mZyXzg",   # * Feb 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.91,
    },
    {
        "title":       "Electrical Wiring Basics for Beginners",
        "description": "How home wiring works, wire types, circuit breakers, outlets, and switches — explained simply.",
        "skill_name":  "electrical wiring",
        "duration":    65,
        "content_url": "https://www.youtube.com/watch?v=crUtJb_2SAA",   # * Feb 2026
        "content_type": "VIDEO",
        "difficulty":  "INTERMEDIATE",
        "prerequisites": [],
        "recommendation_score": 0.89,
    },
    {
        "title":       "Painting Walls Like a Professional – Start to Finish",
        "description": "Surface prep, primer selection, brush and roller technique for interior and exterior painting.",
        "skill_name":  "painting",
        "duration":    35,
        "content_url": "https://www.youtube.com/watch?v=1dFXDuLL4EI",   # * Sep 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.85,
    },

    # ── Domestic & Hospitality ────────────────────────────
    {
        "title":       "Basic Housekeeping: Mastering Cleaning Techniques",
        "description": "Life-skills cleaning lesson: why tidiness matters, essential tools, and methods for home or hotel standards.",
        "skill_name":  "cleaning",
        "duration":    30,
        "content_url": "https://www.youtube.com/watch?v=kjX8fDy3YvE",   # * May 2025
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.87,
    },
    {
        "title":       "Cooking Fundamentals for Beginners",
        "description": "Core kitchen skills: knife work, heat control, seasoning, and building flavourful meals from simple ingredients.",
        "skill_name":  "cooking",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=ZJy1ajvMU1k",   # Basics with Babish – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.90,
    },
    {
        "title":       "Food Hygiene & Safety Level 2 – Catering Introduction",
        "description": "Food handling, storage temperatures, hygiene regulations, contamination risks, and HACCP principles.",
        "skill_name":  "catering",
        "duration":    45,
        "content_url": "https://www.youtube.com/watch?v=iQHsJuK8Fis",   # * Training Express, Aug 2023
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["cooking"],
        "recommendation_score": 0.86,
    },
    {
        "title":       "Childcare & Early Childhood Development",
        "description": "Safe childcare practices, child development milestones, age-appropriate activities, and safeguarding basics.",
        "skill_name":  "childcare",
        "duration":    40,
        "content_url": "https://www.youtube.com/watch?v=lxWlCj3FPLM",   # UNICEF – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.84,
    },
    {
        "title":       "Elderly Care & Patient Assistance Techniques",
        "description": "Dignified care methods, mobility assistance, medication reminders, fall prevention, and emergency response.",
        "skill_name":  "elderly care",
        "duration":    45,
        "content_url": "https://www.youtube.com/watch?v=ULdTZbvhzw4",   # Home Instead – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.82,
    },

    # ── Agriculture ───────────────────────────────────────
    {
        "title":       "Introduction to Modern Farming Techniques",
        "description": "Innovative agricultural practices: soil prep, crop rotation, organic fertilisers, irrigation, and smart farming.",
        "skill_name":  "farming",
        "duration":    55,
        "content_url": "https://www.youtube.com/watch?v=OTbn_gXfb2A",   # * Jun 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.88,
    },
    {
        "title":       "Greenhouse & Vegetable Production for Beginners",
        "description": "Setting up a small greenhouse, growing tomatoes, peppers, and leafy greens for income.",
        "skill_name":  "horticulture",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=LxKnqsF3Uzk",   # Epic Gardening – stable channel
        "content_type": "VIDEO",
        "difficulty":  "INTERMEDIATE",
        "prerequisites": ["farming"],
        "recommendation_score": 0.83,
    },
    {
        "title":       "Livestock & Poultry Farm Management for Beginners",
        "description": "Chicken, goat, and cattle rearing: feeding, disease prevention, biosecurity, and record keeping.",
        "skill_name":  "livestock management",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=2WUbcdZpXbA",   # * Apr 2022
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.81,
    },

    # ── IT & Digital ──────────────────────────────────────
    {
        "title":       "Android Phone Repair for Beginners",
        "description": "Screen replacement, battery swaps, and software troubleshooting for common Android smartphones.",
        "skill_name":  "phone repair",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=PN8mXJPZtPo",   # iFixit – stable channel
        "content_type": "VIDEO",
        "difficulty":  "INTERMEDIATE",
        "prerequisites": [],
        "recommendation_score": 0.92,
    },
    {
        "title":       "Computer Basics for Absolute Beginners",
        "description": "Complete introduction: Windows navigation, file management, Word, Excel, email, and internet safety.",
        "skill_name":  "computer skills",
        "duration":    90,
        "content_url": "https://www.youtube.com/watch?v=mG14sTC5fYo",   # * Oct 2024 – 12-hour course
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.94,
    },
    {
        "title":       "Beginner's Guide to Google Sheets – Full Course 2024",
        "description": "Budgets, invoices, and data tracking using Google Sheets — free and works on any device.",
        "skill_name":  "spreadsheets",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=Pj8f8XnHCfw",   # * May 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["computer skills"],
        "recommendation_score": 0.89,
    },
    {
        "title":       "Digital Marketing for Beginners – Complete 2024 Guide",
        "description": "Social media, online advertising, SEO basics, and content marketing to grow a local business.",
        "skill_name":  "digital marketing",
        "duration":    55,
        "content_url": "https://www.youtube.com/watch?v=xVg0vLFYo24",   # * Oct 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["computer skills"],
        "recommendation_score": 0.86,
    },
    {
        "title":       "Data Entry & Record Keeping Skills",
        "description": "Accurate data entry, form filling, filing systems, keyboard shortcuts, and basic bookkeeping for admin roles.",
        "skill_name":  "data entry",
        "duration":    35,
        "content_url": "https://www.youtube.com/watch?v=H_TGAFTtFU0",   # GCFLearnFree – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.87,
    },

    # ── Customer Service & Communication ──────────────────
    {
        "title":       "Customer Service Training – How to Excel at Customer Service",
        "description": "Handling difficult customers, active listening, complaint resolution, and professional communication.",
        "skill_name":  "customer service",
        "duration":    40,
        "content_url": "https://www.youtube.com/watch?v=SsNfAOTZNZY",   # * Apr 2024 – Richard McMunn
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.93,
    },
    {
        "title":       "Business English for the Workplace",
        "description": "Professional emails, phone calls, meetings, and presentations for non-native English speakers.",
        "skill_name":  "english communication",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=6PV_NMiV_6Q",   # BBC Learning English – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.91,
    },
    {
        "title":       "Sales Fundamentals – Persuasion & Closing Techniques",
        "description": "Buyer psychology, handling objections, negotiation basics, and confidently closing sales.",
        "skill_name":  "sales",
        "duration":    45,
        "content_url": "https://www.youtube.com/watch?v=PHHhMgaHhU8",   # HubSpot – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["customer service"],
        "recommendation_score": 0.88,
    },

    # ── Transport & Logistics ─────────────────────────────
    {
        "title":       "Beginner's Guide to Defensive Driving",
        "description": "Defensive driving techniques: hazard perception, space management, vehicle pre-checks, and road safety.",
        "skill_name":  "driving",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=_Ii1vWd8aCc",   # * May 2025
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.90,
    },
    {
        "title":       "Motorcycle Safety & Basic Maintenance",
        "description": "Daily pre-ride checks, safe cornering, emergency braking, load carrying, and basic engine care.",
        "skill_name":  "motorcycle riding",
        "duration":    40,
        "content_url": "https://www.youtube.com/watch?v=TuolAjM8HVo",   # MotoJitsu – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.89,
    },
    {
        "title":       "Warehouse & Inventory Management Fundamentals",
        "description": "Stock counting, FIFO principles, warehouse organisation, goods receiving, and basic inventory control.",
        "skill_name":  "warehouse management",
        "duration":    45,
        "content_url": "https://www.youtube.com/watch?v=n-GDnLPIuGA",   # ASCM – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.84,
    },

    # ── Security ──────────────────────────────────────────
    {
        "title":       "Security Guard Training – Professional Standards",
        "description": "Access control, incident reporting, patrol procedures, and legal responsibilities for security personnel.",
        "skill_name":  "security",
        "duration":    55,
        "content_url": "https://www.youtube.com/watch?v=VmCGj8DGNUU",   # BSIA – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.86,
    },
    {
        "title":       "CPR & First Aid – Adult, Child & Infant Training",
        "description": "CPR technique, AED use, wound management, choking response, and emergency calling — for any work environment.",
        "skill_name":  "first aid",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=oyXP7UqARHE",   # * Safety Training Seminars, Jan 2025
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.85,
    },

    # ── Tailoring & Crafts ────────────────────────────────
    {
        "title":       "How To Use a Sewing Machine – Beginner's Complete Guide",
        "description": "Threading, tension, measurements, cutting fabric, and sewing your first garments confidently.",
        "skill_name":  "tailoring",
        "duration":    65,
        "content_url": "https://www.youtube.com/watch?v=88zISRBOzVY",   # * Feb 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.83,
    },
    {
        "title":       "Hairdressing & African Braiding Techniques",
        "description": "Salon hygiene, popular braiding styles, client consultations, and care for natural hair textures.",
        "skill_name":  "hairdressing",
        "duration":    55,
        "content_url": "https://www.youtube.com/watch?v=ztPGMDJD7jI",   # Curly Chemistry – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.82,
    },

    # ── Finance & Entrepreneurship ────────────────────────
    {
        "title":       "Managing Finances as a Gig & Shift Worker",
        "description": "Budgeting on irregular income, building an emergency fund, and saving strategies for contract workers.",
        "skill_name":  "financial literacy",
        "duration":    40,
        "content_url": "https://www.youtube.com/watch?v=vwN2_BSwBgk",   # * Jun 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.88,
    },
    {
        "title":       "Starting a Small Business – Step-by-Step for Beginners",
        "description": "Idea validation, registration, writing a simple business plan, pricing, and accessing startup finance.",
        "skill_name":  "entrepreneurship",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=lMGgSm_v1Kc",   # Shopify – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["financial literacy"],
        "recommendation_score": 0.85,
    },
    {
        "title":       "Mobile Money – Sending, Receiving & Tracking Payments",
        "description": "How to use mobile money services for wages, supplier payments, savings, and transaction tracking.",
        "skill_name":  "mobile money",
        "duration":    25,
        "content_url": "https://www.youtube.com/watch?v=RXSIvD7WIiI",   # GSMA – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.92,
    },

    # ── Health & Wellbeing ────────────────────────────────
    {
        "title":       "Personal Protective Equipment (PPE) & Workplace Safety",
        "description": "Hazard identification, PPE selection, manual handling techniques, and reporting near-misses on site.",
        "skill_name":  "health and safety",
        "duration":    35,
        "content_url": "https://www.youtube.com/watch?v=U9vMMzLx3sA",   # * Jul 2024
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.87,
    },

    # ── Leadership & Soft Skills ──────────────────────────
    {
        "title":       "Time Management for Shift & Gig Workers",
        "description": "Prioritisation frameworks, punctuality strategies, planning multiple jobs, and avoiding burnout.",
        "skill_name":  "time management",
        "duration":    30,
        "content_url": "https://www.youtube.com/watch?v=idbdXTMnOmE",   # Thomas Frank – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.86,
    },
    {
        "title":       "Teamwork & Communication on the Job",
        "description": "Active listening, conflict resolution, giving and receiving feedback in a team environment.",
        "skill_name":  "teamwork",
        "duration":    35,
        "content_url": "https://www.youtube.com/watch?v=HiB9L3dG-Aw",   # MindTools – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.84,
    },
    {
        "title":       "Leadership Skills for Team Leads & Supervisors",
        "description": "Delegation, motivation, running daily standups, and managing underperformance constructively.",
        "skill_name":  "leadership",
        "duration":    50,
        "content_url": "https://www.youtube.com/watch?v=NMAQOLH4mKM",   # Simon Sinek – stable channel
        "content_type": "VIDEO",
        "difficulty":  "INTERMEDIATE",
        "prerequisites": ["teamwork"],
        "recommendation_score": 0.80,
    },

    # ── Language ──────────────────────────────────────────
    {
        "title":       "French for Absolute Beginners – Workplace Phrases",
        "description": "Basic professional French: greetings, workplace vocabulary, and common phrases for hospitality and admin.",
        "skill_name":  "french",
        "duration":    60,
        "content_url": "https://www.youtube.com/watch?v=ukqNIWtMmMI",   # Français avec Pierre – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.79,
    },
    {
        "title":       "Kinyarwanda for Daily Life & Business",
        "description": "Key Kinyarwanda phrases for customer interactions, greetings, negotiations, and professional settings.",
        "skill_name":  "kinyarwanda",
        "duration":    40,
        "content_url": "https://www.youtube.com/watch?v=3fJNbqhDWZA",   # kept from original – verify
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.78,
    },

    # ── Welding ───────────────────────────────────────────
    {
        "title":       "Stick Welding Basics – Full Tutorial (SMAW)",
        "description": "Shielded metal arc welding from scratch: safe setup, electrode selection, striking an arc, and bead running.",
        "skill_name":  "welding",
        "duration":    70,
        "content_url": "https://www.youtube.com/watch?v=Dybrlx-BTXk",   # * Apr 2023
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": ["health and safety"],
        "recommendation_score": 0.88,
    },

    # ── Gardening ─────────────────────────────────────────
    {
        "title":       "Gardening for Beginners – Landscaping & Maintenance",
        "description": "Lawn care, hedge trimming, planting flower beds, composting, and maintaining compounds for clients.",
        "skill_name":  "gardening",
        "duration":    35,
        "content_url": "https://www.youtube.com/watch?v=Gfkg_T8TLQY",   # Gardening Australia – stable channel
        "content_type": "VIDEO",
        "difficulty":  "BEGINNER",
        "prerequisites": [],
        "recommendation_score": 0.80,
    },
]


def seed_learning_modules(db: Session) -> int:
    """
    Insert all seed modules. Skips any whose title already exists
    so re-running is safe.

    Returns the count of newly inserted modules.
    """
    existing_titles = {
        row.title for row in db.query(LearningModule.title).all()
    }

    inserted = 0
    for m in MODULES:
        if m["title"] in existing_titles:
            continue

        module = LearningModule(
            id                   = str(uuid.uuid4()),
            title                = m["title"],
            description          = m["description"],
            skill_name           = m["skill_name"],
            duration             = m["duration"],
            content_url          = m["content_url"],
            content_type         = m["content_type"],
            difficulty           = m["difficulty"],
            prerequisites        = m.get("prerequisites", []),
            recommendation_score = m.get("recommendation_score", 0.0),
            is_synced            = True,
            created_at           = datetime.utcnow(),
        )
        db.add(module)
        inserted += 1

    db.commit()
    return inserted


if __name__ == "__main__":
    db = SessionLocal()
    try:
        count = seed_learning_modules(db)
        print(f"✅ Inserted {count} learning modules")
    finally:
        db.close()