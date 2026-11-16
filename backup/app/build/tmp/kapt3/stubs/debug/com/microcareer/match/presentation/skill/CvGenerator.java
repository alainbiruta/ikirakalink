package com.microcareer.match.presentation.skill;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000N\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010 \n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\"\u0010\u0007\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u000e0\rH\u0002J\u001c\u0010\u000f\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000b2\u0006\u0010\u0010\u001a\u00020\u0011H\u0002J\"\u0010\u0012\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00140\rH\u0002J\"\u0010\u0015\u001a\u00020\b2\n\u0010\t\u001a\u00060\nj\u0002`\u000b2\f\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\u00170\rH\u0002J\u000e\u0010\u0018\u001a\u00020\u00142\u0006\u0010\u0019\u001a\u00020\u001aR\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u001b"}, d2 = {"Lcom/microcareer/match/presentation/skill/CvGenerator;", "", "()V", "NARROW_DIVIDER_LENGTH", "", "SKILLS_PER_ROW", "WIDE_DIVIDER_LENGTH", "appendEducation", "", "sb", "Ljava/lang/StringBuilder;", "Lkotlin/text/StringBuilder;", "education", "", "Lcom/microcareer/match/presentation/skill/EducationUiModel;", "appendPersonalInfo", "info", "Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;", "appendSkills", "skills", "", "appendWorkExperience", "experiences", "Lcom/microcareer/match/presentation/skill/WorkExperienceUiModel;", "generateCvText", "state", "Lcom/microcareer/match/presentation/skill/SkillUiState;", "app_debug"})
public final class CvGenerator {
    private static final int WIDE_DIVIDER_LENGTH = 50;
    private static final int NARROW_DIVIDER_LENGTH = 30;
    private static final int SKILLS_PER_ROW = 4;
    @org.jetbrains.annotations.NotNull()
    public static final com.microcareer.match.presentation.skill.CvGenerator INSTANCE = null;
    
    private CvGenerator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String generateCvText(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.SkillUiState state) {
        return null;
    }
    
    private final void appendPersonalInfo(java.lang.StringBuilder sb, com.microcareer.match.presentation.skill.PersonalInfoUiModel info) {
    }
    
    private final void appendSkills(java.lang.StringBuilder sb, java.util.List<java.lang.String> skills) {
    }
    
    private final void appendWorkExperience(java.lang.StringBuilder sb, java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> experiences) {
    }
    
    private final void appendEducation(java.lang.StringBuilder sb, java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> education) {
    }
}