package com.microcareer.match.presentation.skill

import java.util.UUID

/* ============================================================
   ======================== CV UI MODELS ======================
   All data shapes for the CV builder and skill screens.
   ============================================================ */

data class PersonalInfoUiModel(
    val fullName: String = "",
    val email: String = "",
    val phone: String = "",
    val location: String = "",
    val linkedIn: String = "",
    val summary: String = ""
)

data class WorkExperienceUiModel(
    // Stable, unique ID — required for LazyColumn keys and CRUD ops
    val id: String = UUID.randomUUID().toString(),
    val jobTitle: String = "",
    val company: String = "",
    val startDate: String = "",
    val endDate: String = "",
    val isCurrent: Boolean = false,
    val description: String = ""
)

data class EducationUiModel(
    val id: String = UUID.randomUUID().toString(),
    val institution: String = "",
    val degree: String = "",
    val fieldOfStudy: String = "",
    val startDate: String = "",
    val endDate: String = ""
)

/* ============================================================
   ========================= UI STATE =========================
   Single state object for both CvBuilderScreen and
   SkillInputScreen. Both screens use SkillViewModel.

   `inputError`    — validation error for the skill text field
   `error`         — general/repo error shown as Snackbar
   `saveSuccess`   — one-shot flag; consumed by the UI then reset
   `generatedCvText` — non-null triggers the share sheet in UI;
                       consumed by the UI via onCvTextConsumed()
   ============================================================ */

data class SkillUiState(
    val personalInfo: PersonalInfoUiModel = PersonalInfoUiModel(),
    val skills: List<String> = emptyList(),
    val workExperiences: List<WorkExperienceUiModel> = emptyList(),
    val education: List<EducationUiModel> = emptyList(),
    // Skill quick-add helpers
    val input: String = "",
    val inputError: String? = null,
    // General feedback
    val error: String? = null,
    val isLoading: Boolean = true,
    val isSaving: Boolean = false,
    // One-shot navigation/share triggers — always reset after consumption
    val saveSuccess: Boolean = false,
    val generatedCvText: String? = null,
    val pdfExportMessage: String? = null
)
