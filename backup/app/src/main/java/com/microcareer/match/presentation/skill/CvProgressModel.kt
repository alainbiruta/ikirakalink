package com.microcareer.match.presentation.skill

/* ============================================================
   =================== CV PROGRESS MODEL ======================

   Pure utility — no Android or Compose dependencies.
   Computes CV section completeness from SkillUiState.

   Used by:
     • SkillInputScreen — shows a progress card as a bridge
       to CvBuilderScreen
     • CvBuilderScreen  — could show a header completion bar

   TAB INDEX CONSTANTS match CvBuilderScreen's tab order:
     0 = Profile, 1 = Skills, 2 = Experience, 3 = Education
   ============================================================ */

data class CvSectionProgress(
    val label: String,
    val tabIndex: Int,
    val isComplete: Boolean,
    val detail: String
)

data class CvProgress(
    val sections: List<CvSectionProgress>,
    val completedSections: Int,
    val totalSections: Int,
    val fraction: Float
)

object CvProgressCalculator {

    private const val MIN_SKILLS = 1
    private const val MIN_EXPERIENCES = 1
    private const val MIN_EDUCATION = 1

    fun compute(state: SkillUiState): CvProgress {
        val profileComplete = state.personalInfo.fullName.isNotBlank() &&
                state.personalInfo.email.isNotBlank()

        val skillsComplete = state.skills.size >= MIN_SKILLS
        val experienceComplete = state.workExperiences.size >= MIN_EXPERIENCES
        val educationComplete = state.education.size >= MIN_EDUCATION

        val sections = listOf(
            CvSectionProgress(
                label = "Profile",
                tabIndex = 0,
                isComplete = profileComplete,
                detail = if (profileComplete) "Name & email added"
                else "Add your name and email"
            ),
            CvSectionProgress(
                label = "Skills",
                tabIndex = 1,
                isComplete = skillsComplete,
                detail = if (skillsComplete) "${state.skills.size} skill${if (state.skills.size == 1) "" else "s"} added"
                else "No skills added yet"
            ),
            CvSectionProgress(
                label = "Experience",
                tabIndex = 2,
                isComplete = experienceComplete,
                detail = if (experienceComplete) "${state.workExperiences.size} position${if (state.workExperiences.size == 1) "" else "s"} added"
                else "No experience added yet"
            ),
            CvSectionProgress(
                label = "Education",
                tabIndex = 3,
                isComplete = educationComplete,
                detail = if (educationComplete) "${state.education.size} qualification${if (state.education.size == 1) "" else "s"} added"
                else "No education added yet"
            )
        )

        val completed = sections.count { it.isComplete }

        return CvProgress(
            sections = sections,
            completedSections = completed,
            totalSections = sections.size,
            fraction = completed.toFloat() / sections.size.toFloat()
        )
    }
}