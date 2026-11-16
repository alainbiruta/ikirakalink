package com.microcareer.match.presentation.skill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.domain.repository.ProfileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SkillViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SkillUiState())
    val uiState: StateFlow<SkillUiState> = _uiState.asStateFlow()

    init {
        observeProfile()
    }

    // ── PROFILE OBSERVATION ──────────────────────────────────────────────────
    // Loads saved skills from Room on every launch so the CV builder and
    // SkillInputScreen always start with the persisted list.

    private fun observeProfile() {
        viewModelScope.launch {
            repository.getUserProfile().collectLatest { profile ->
                _uiState.update { current ->
                    current.copy(
                        skills    = profile.skills,
                        isLoading = false
                    )
                }
            }
        }
    }

    // ── PERSONAL INFO ────────────────────────────────────────────────────────

    fun updatePersonalInfo(info: PersonalInfoUiModel) {
        _uiState.update { it.copy(personalInfo = info) }
    }

    // ── SKILLS ───────────────────────────────────────────────────────────────
    // FIX (Bug 2): Every mutation now calls persistSkills() which writes to
    // Room immediately and pushes to the backend. Previously addSkill() and
    // removeSkill() only mutated _uiState — nothing was ever saved.

    fun onInputChange(value: String) {
        _uiState.update { it.copy(input = value, inputError = null) }
    }

    fun addSkill() {
        val trimmed = _uiState.value.input.trim()
        if (trimmed.isBlank()) return

        if (_uiState.value.skills.any { it.equals(trimmed, ignoreCase = true) }) {
            _uiState.update { it.copy(inputError = "Skill already added") }
            return
        }

        val updatedSkills = _uiState.value.skills + trimmed
        _uiState.update { it.copy(skills = updatedSkills, input = "", inputError = null) }
        // Persist every time a skill is added
        persistSkills(updatedSkills)
    }

    fun removeSkill(skill: String) {
        val updatedSkills = _uiState.value.skills - skill
        _uiState.update { it.copy(skills = updatedSkills) }
        // Persist every time a skill is removed
        persistSkills(updatedSkills)
    }

    // ── WORK EXPERIENCE ──────────────────────────────────────────────────────

    fun addWorkExperience(experience: WorkExperienceUiModel) {
        _uiState.update { it.copy(workExperiences = it.workExperiences + experience) }
    }

    fun removeWorkExperience(id: String) {
        _uiState.update {
            it.copy(workExperiences = it.workExperiences.filterNot { exp -> exp.id == id })
        }
    }

    // ── EDUCATION ────────────────────────────────────────────────────────────

    fun addEducation(education: EducationUiModel) {
        _uiState.update { it.copy(education = it.education + education) }
    }

    fun removeEducation(id: String) {
        _uiState.update {
            it.copy(education = it.education.filterNot { edu -> edu.id == id })
        }
    }

    // ── SAVE (explicit, called by SkillInputScreen save button) ──────────────

    fun saveSkills() {
        persistSkills(_uiState.value.skills)
    }

    // ── INTERNAL PERSIST ────────────────────────────────────────────────────
    // Single place that writes to the repository.
    // Called by addSkill(), removeSkill(), and saveSkills().

    private fun persistSkills(skills: List<String>) {
        viewModelScope.launch {
            _uiState.update { it.copy(isSaving = true, error = null) }
            try {
                repository.saveSkills(skills)
                _uiState.update { it.copy(isSaving = false, saveSuccess = true) }
            } catch (e: Exception) {
                _uiState.update {
                    it.copy(
                        isSaving = false,
                        error    = e.message ?: "Failed to save. Please try again."
                    )
                }
            }
        }
    }

    // ── ONE-SHOT FLAG CONSUMERS ───────────────────────────────────────────────

    fun onSaveSuccessConsumed() {
        _uiState.update { it.copy(saveSuccess = false) }
    }

    fun onCvTextConsumed() {
        _uiState.update { it.copy(generatedCvText = null) }
    }

    // ── CV GENERATION ────────────────────────────────────────────────────────

    fun generateCv() {
        val text = CvGenerator.generateCvText(_uiState.value)
        _uiState.update { it.copy(generatedCvText = text) }
    }
    // ── PDF EXPORT ───────────────────────────────────────────────────────────

    fun exportCvAsPdf(context: android.content.Context) {
        viewModelScope.launch(kotlinx.coroutines.Dispatchers.IO) {
            try {
                val cvText = CvGenerator.generateCvText(_uiState.value)
                val name = _uiState.value.personalInfo.fullName.ifBlank { "My" }
                val fileName = "${name.replace(" ", "_")}_CV.pdf"
                CvPdfExporter.exportToDownloads(context, cvText, fileName)
                _uiState.update { it.copy(pdfExportMessage = "Saved to Downloads: $fileName") }
            } catch (e: Exception) {
                _uiState.update { it.copy(pdfExportMessage = "Export failed: ${e.message}") }
            }
        }
    }

    fun onPdfExportMessageConsumed() {
        _uiState.update { it.copy(pdfExportMessage = null) }
    }
}