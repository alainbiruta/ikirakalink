package com.microcareer.match.presentation.skill;

import java.util.UUID;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\b#\n\u0002\u0010\b\n\u0002\b\u0002\b\u0086\b\u0018\u00002\u00020\u0001B\u0097\u0001\u0012\b\b\u0002\u0010\u0002\u001a\u00020\u0003\u0012\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u0012\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u0012\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u0012\b\b\u0002\u0010\u000b\u001a\u00020\u0006\u0012\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u0006\u0012\b\b\u0002\u0010\u000e\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0010\u001a\u00020\u000f\u0012\b\b\u0002\u0010\u0011\u001a\u00020\u000f\u0012\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u0012\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\u0002\u0010\u0014J\t\u0010#\u001a\u00020\u0003H\u00c6\u0003J\t\u0010$\u001a\u00020\u000fH\u00c6\u0003J\u000b\u0010%\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010&\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000f\u0010\'\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005H\u00c6\u0003J\u000f\u0010(\u001a\b\u0012\u0004\u0012\u00020\b0\u0005H\u00c6\u0003J\u000f\u0010)\u001a\b\u0012\u0004\u0012\u00020\n0\u0005H\u00c6\u0003J\t\u0010*\u001a\u00020\u0006H\u00c6\u0003J\u000b\u0010+\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\u000b\u0010,\u001a\u0004\u0018\u00010\u0006H\u00c6\u0003J\t\u0010-\u001a\u00020\u000fH\u00c6\u0003J\t\u0010.\u001a\u00020\u000fH\u00c6\u0003J\u009b\u0001\u0010/\u001a\u00020\u00002\b\b\u0002\u0010\u0002\u001a\u00020\u00032\u000e\b\u0002\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u00052\u000e\b\u0002\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u00052\u000e\b\u0002\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u00052\b\b\u0002\u0010\u000b\u001a\u00020\u00062\n\b\u0002\u0010\f\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\r\u001a\u0004\u0018\u00010\u00062\b\b\u0002\u0010\u000e\u001a\u00020\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u000f2\b\b\u0002\u0010\u0011\u001a\u00020\u000f2\n\b\u0002\u0010\u0012\u001a\u0004\u0018\u00010\u00062\n\b\u0002\u0010\u0013\u001a\u0004\u0018\u00010\u0006H\u00c6\u0001J\u0013\u00100\u001a\u00020\u000f2\b\u00101\u001a\u0004\u0018\u00010\u0001H\u00d6\u0003J\t\u00102\u001a\u000203H\u00d6\u0001J\t\u00104\u001a\u00020\u0006H\u00d6\u0001R\u0017\u0010\t\u001a\b\u0012\u0004\u0012\u00020\n0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0015\u0010\u0016R\u0013\u0010\r\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0017\u0010\u0018R\u0013\u0010\u0012\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0019\u0010\u0018R\u0011\u0010\u000b\u001a\u00020\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001a\u0010\u0018R\u0013\u0010\f\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0018R\u0011\u0010\u000e\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u001cR\u0011\u0010\u0010\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0010\u0010\u001cR\u0013\u0010\u0013\u001a\u0004\u0018\u00010\u0006\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0018R\u0011\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001e\u0010\u001fR\u0011\u0010\u0011\u001a\u00020\u000f\u00a2\u0006\b\n\u0000\u001a\u0004\b \u0010\u001cR\u0017\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00060\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b!\u0010\u0016R\u0017\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\b0\u0005\u00a2\u0006\b\n\u0000\u001a\u0004\b\"\u0010\u0016\u00a8\u00065"}, d2 = {"Lcom/microcareer/match/presentation/skill/SkillUiState;", "", "personalInfo", "Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;", "skills", "", "", "workExperiences", "Lcom/microcareer/match/presentation/skill/WorkExperienceUiModel;", "education", "Lcom/microcareer/match/presentation/skill/EducationUiModel;", "input", "inputError", "error", "isLoading", "", "isSaving", "saveSuccess", "generatedCvText", "pdfExportMessage", "(Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;Ljava/util/List;Ljava/util/List;Ljava/util/List;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;ZZZLjava/lang/String;Ljava/lang/String;)V", "getEducation", "()Ljava/util/List;", "getError", "()Ljava/lang/String;", "getGeneratedCvText", "getInput", "getInputError", "()Z", "getPdfExportMessage", "getPersonalInfo", "()Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;", "getSaveSuccess", "getSkills", "getWorkExperiences", "component1", "component10", "component11", "component12", "component2", "component3", "component4", "component5", "component6", "component7", "component8", "component9", "copy", "equals", "other", "hashCode", "", "toString", "app_debug"})
public final class SkillUiState {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.presentation.skill.PersonalInfoUiModel personalInfo = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<java.lang.String> skills = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> workExperiences = null;
    @org.jetbrains.annotations.NotNull()
    private final java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> education = null;
    @org.jetbrains.annotations.NotNull()
    private final java.lang.String input = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String inputError = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String error = null;
    private final boolean isLoading = false;
    private final boolean isSaving = false;
    private final boolean saveSuccess = false;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String generatedCvText = null;
    @org.jetbrains.annotations.Nullable()
    private final java.lang.String pdfExportMessage = null;
    
    public SkillUiState(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.PersonalInfoUiModel personalInfo, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> workExperiences, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> education, @org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.Nullable()
    java.lang.String inputError, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isLoading, boolean isSaving, boolean saveSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String generatedCvText, @org.jetbrains.annotations.Nullable()
    java.lang.String pdfExportMessage) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.skill.PersonalInfoUiModel getPersonalInfo() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> getSkills() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> getWorkExperiences() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> getEducation() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getInput() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getInputError() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getError() {
        return null;
    }
    
    public final boolean isLoading() {
        return false;
    }
    
    public final boolean isSaving() {
        return false;
    }
    
    public final boolean getSaveSuccess() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getGeneratedCvText() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getPdfExportMessage() {
        return null;
    }
    
    public SkillUiState() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.skill.PersonalInfoUiModel component1() {
        return null;
    }
    
    public final boolean component10() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component11() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component12() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<java.lang.String> component2() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> component3() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> component4() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String component5() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component6() {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String component7() {
        return null;
    }
    
    public final boolean component8() {
        return false;
    }
    
    public final boolean component9() {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.microcareer.match.presentation.skill.SkillUiState copy(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.PersonalInfoUiModel personalInfo, @org.jetbrains.annotations.NotNull()
    java.util.List<java.lang.String> skills, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> workExperiences, @org.jetbrains.annotations.NotNull()
    java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> education, @org.jetbrains.annotations.NotNull()
    java.lang.String input, @org.jetbrains.annotations.Nullable()
    java.lang.String inputError, @org.jetbrains.annotations.Nullable()
    java.lang.String error, boolean isLoading, boolean isSaving, boolean saveSuccess, @org.jetbrains.annotations.Nullable()
    java.lang.String generatedCvText, @org.jetbrains.annotations.Nullable()
    java.lang.String pdfExportMessage) {
        return null;
    }
    
    @java.lang.Override()
    public boolean equals(@org.jetbrains.annotations.Nullable()
    java.lang.Object other) {
        return false;
    }
    
    @java.lang.Override()
    public int hashCode() {
        return 0;
    }
    
    @java.lang.Override()
    @org.jetbrains.annotations.NotNull()
    public java.lang.String toString() {
        return null;
    }
}