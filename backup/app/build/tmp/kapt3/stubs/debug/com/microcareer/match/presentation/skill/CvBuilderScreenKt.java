package com.microcareer.match.presentation.skill;

import android.content.Intent;
import androidx.compose.foundation.layout.*;
import androidx.compose.foundation.text.KeyboardOptions;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.focus.FocusDirection;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.input.ImeAction;
import androidx.compose.ui.text.input.KeyboardCapitalization;
import androidx.compose.ui.text.input.KeyboardType;
import androidx.compose.ui.text.style.TextOverflow;
import androidx.compose.ui.window.DialogProperties;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000P\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\n\u0002\u0010 \n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\t\u001a*\u0010\u0000\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a*\u0010\u0007\u001a\u00020\u00012\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0004\u001a\u000e\u0012\u0004\u0012\u00020\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a(\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\f\u0010\f\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\b\b\u0002\u0010\r\u001a\u00020\u000eH\u0007\u001a,\u0010\u000f\u001a\u00020\u00012\u0006\u0010\u0010\u001a\u00020\u00112\f\u0010\u0002\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\u001e\u0010\u0013\u001a\u00020\u00012\u0006\u0010\u0014\u001a\u00020\u00062\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a8\u0010\u0016\u001a\u00020\u00012\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00060\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a6\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u00112\u0006\u0010\u001d\u001a\u00020\u00112\u0006\u0010\u001e\u001a\u00020\u00112\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a\u001e\u0010 \u001a\u00020\u00012\u0006\u0010!\u001a\u00020\b2\f\u0010\u0015\u001a\b\u0012\u0004\u0012\u00020\u00010\u0003H\u0003\u001a8\u0010\"\u001a\u00020\u00012\f\u0010#\u001a\b\u0012\u0004\u0012\u00020\b0\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010\u0015\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a$\u0010$\u001a\u00020\u00012\u0006\u0010%\u001a\u00020&2\u0012\u0010\'\u001a\u000e\u0012\u0004\u0012\u00020&\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a^\u0010(\u001a\u00020\u00012\f\u0010)\u001a\b\u0012\u0004\u0012\u00020\u00110\u00172\u0006\u0010*\u001a\u00020\u00112\b\u0010+\u001a\u0004\u0018\u00010\u00112\u0012\u0010,\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010-\u001a\b\u0012\u0004\u0012\u00020\u00010\u00032\u0012\u0010.\u001a\u000e\u0012\u0004\u0012\u00020\u0011\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u00a8\u0006/"}, d2 = {"AddEducationDialog", "", "onDismiss", "Lkotlin/Function0;", "onConfirm", "Lkotlin/Function1;", "Lcom/microcareer/match/presentation/skill/EducationUiModel;", "AddExperienceDialog", "Lcom/microcareer/match/presentation/skill/WorkExperienceUiModel;", "CvBuilderScreen", "viewModel", "Lcom/microcareer/match/presentation/skill/SkillViewModel;", "onBack", "startTab", "", "CvPreviewDialog", "cvText", "", "onDownloadPdf", "EducationCard", "education", "onRemove", "EducationTab", "", "onAdd", "EmptyTabContent", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "subtitle", "buttonText", "onButtonClick", "ExperienceCard", "experience", "ExperienceTab", "experiences", "PersonalInfoTab", "personalInfo", "Lcom/microcareer/match/presentation/skill/PersonalInfoUiModel;", "onUpdate", "SkillsTab", "skills", "input", "inputError", "onInputChange", "onAddSkill", "onRemoveSkill", "app_debug"})
public final class CvBuilderScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    public static final void CvBuilderScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.skill.SkillViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, int startTab) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void PersonalInfoTab(com.microcareer.match.presentation.skill.PersonalInfoUiModel personalInfo, kotlin.jvm.functions.Function1<? super com.microcareer.match.presentation.skill.PersonalInfoUiModel, kotlin.Unit> onUpdate) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void SkillsTab(java.util.List<java.lang.String> skills, java.lang.String input, java.lang.String inputError, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onInputChange, kotlin.jvm.functions.Function0<kotlin.Unit> onAddSkill, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemoveSkill) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExperienceTab(java.util.List<com.microcareer.match.presentation.skill.WorkExperienceUiModel> experiences, kotlin.jvm.functions.Function0<kotlin.Unit> onAdd, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ExperienceCard(com.microcareer.match.presentation.skill.WorkExperienceUiModel experience, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EducationTab(java.util.List<com.microcareer.match.presentation.skill.EducationUiModel> education, kotlin.jvm.functions.Function0<kotlin.Unit> onAdd, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EducationCard(com.microcareer.match.presentation.skill.EducationUiModel education, kotlin.jvm.functions.Function0<kotlin.Unit> onRemove) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyTabContent(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String subtitle, java.lang.String buttonText, kotlin.jvm.functions.Function0<kotlin.Unit> onButtonClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AddExperienceDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.microcareer.match.presentation.skill.WorkExperienceUiModel, kotlin.Unit> onConfirm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AddEducationDialog(kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function1<? super com.microcareer.match.presentation.skill.EducationUiModel, kotlin.Unit> onConfirm) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CvPreviewDialog(java.lang.String cvText, kotlin.jvm.functions.Function0<kotlin.Unit> onDismiss, kotlin.jvm.functions.Function0<kotlin.Unit> onDownloadPdf) {
    }
}