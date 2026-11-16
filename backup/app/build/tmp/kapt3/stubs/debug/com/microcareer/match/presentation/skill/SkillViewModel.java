package com.microcareer.match.presentation.skill;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.domain.repository.ProfileRepository;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.flow.StateFlow;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000X\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\b\n\u0002\u0018\u0002\n\u0000\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u000e\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fJ\u0006\u0010\u0010\u001a\u00020\rJ\u000e\u0010\u0011\u001a\u00020\r2\u0006\u0010\u0012\u001a\u00020\u0013J\u000e\u0010\u0014\u001a\u00020\r2\u0006\u0010\u0015\u001a\u00020\u0016J\u0006\u0010\u0017\u001a\u00020\rJ\b\u0010\u0018\u001a\u00020\rH\u0002J\u0006\u0010\u0019\u001a\u00020\rJ\u000e\u0010\u001a\u001a\u00020\r2\u0006\u0010\u001b\u001a\u00020\u001cJ\u0006\u0010\u001d\u001a\u00020\rJ\u0006\u0010\u001e\u001a\u00020\rJ\u0016\u0010\u001f\u001a\u00020\r2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u001c0!H\u0002J\u000e\u0010\"\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u001cJ\u000e\u0010$\u001a\u00020\r2\u0006\u0010%\u001a\u00020\u001cJ\u000e\u0010&\u001a\u00020\r2\u0006\u0010#\u001a\u00020\u001cJ\u0006\u0010\'\u001a\u00020\rJ\u000e\u0010(\u001a\u00020\r2\u0006\u0010)\u001a\u00020*R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00070\t\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000b\u00a8\u0006+"}, d2 = {"Lcom/microcareer/match/presentation/skill/SkillViewModel;", "Landroidx/lifecycle/ViewModel;", "repository", "Lcom/microcareer/match/domain/repository/ProfileRepository;", "(Lcom/microcareer/match/domain/repository/ProfileRepository;)V", "_uiState", "Lkotlinx/coroutines/flow/MutableStateFlow;", "Lcom/microcareer/match/presentation/skill/SkillUiState;", "uiState", "Lkotlinx/coroutines/flow/StateFlow;", "getUiState", "()Lkotlinx/coroutines/flow/StateFlow;", "addEducation", "", "education", "Lcom/microcareer/match/presentation/skill/EducationUiModel;", "addSkill", "addWorkExperience", "experience", "Lcom/microcareer/match/presentation/skill/WorkExperienceUiModel;", "exportCvAsPdf", "context", "Landroid/content/Context;", "generateCv", "observeProfile", "onCvTextConsumed", "onInputChange", "value", "", "onPdfExportMessageConsumed", "onSaveSuccessConsumed", "persistSkills", "skills", "", "removeEducation", "id", "removeSkill", "skill", "removeWorkExperience", "saveSkills", "updatePersonalInfo", "info", "Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class SkillViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.domain.repository.ProfileRepository repository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.skill.SkillUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.skill.SkillUiState> uiState = null;
    
    @javax.inject.Inject()
    public SkillViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.domain.repository.ProfileRepository repository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.skill.SkillUiState> getUiState() {
        return null;
    }
    
    private final void observeProfile() {
    }
    
    public final void updatePersonalInfo(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.PersonalInfoUiModel info) {
    }
    
    public final void onInputChange(@org.jetbrains.annotations.NotNull()
    java.lang.String value) {
    }
    
    public final void addSkill() {
    }
    
    public final void removeSkill(@org.jetbrains.annotations.NotNull()
    java.lang.String skill) {
    }
    
    public final void addWorkExperience(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.WorkExperienceUiModel experience) {
    }
    
    public final void removeWorkExperience(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void addEducation(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.EducationUiModel education) {
    }
    
    public final void removeEducation(@org.jetbrains.annotations.NotNull()
    java.lang.String id) {
    }
    
    public final void saveSkills() {
    }
    
    private final void persistSkills(java.util.List<java.lang.String> skills) {
    }
    
    public final void onSaveSuccessConsumed() {
    }
    
    public final void onCvTextConsumed() {
    }
    
    public final void generateCv() {
    }
    
    public final void exportCvAsPdf(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final void onPdfExportMessageConsumed() {
    }
}