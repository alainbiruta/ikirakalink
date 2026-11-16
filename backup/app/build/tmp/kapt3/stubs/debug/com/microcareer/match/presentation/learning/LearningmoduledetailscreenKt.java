package com.microcareer.match.presentation.learning;

import android.content.Intent;
import androidx.compose.animation.*;
import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import com.microcareer.match.R;
import com.microcareer.match.data.local.entity.LearningModuleEntity;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000J\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010 \n\u0002\b\u0005\u001a\b\u0010\u0000\u001a\u00020\u0001H\u0003\u001aR\u0010\u0002\u001a\u00020\u00012\u0006\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0012\u0010\u0007\u001a\u000e\u0012\u0004\u0012\u00020\u0006\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010\u000b\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u0003\u001a0\u0010\u000e\u001a\u00020\u00012\u0006\u0010\u000f\u001a\u00020\u00102\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\b\b\u0002\u0010\f\u001a\u00020\rH\u0007\u001a\u0018\u0010\u0014\u001a\u00020\u00012\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0010H\u0003\u001a \u0010\u0018\u001a\u00020\u00012\u0006\u0010\u0019\u001a\u00020\u00062\u0006\u0010\u001a\u001a\u00020\u00062\u0006\u0010\u0017\u001a\u00020\u0010H\u0003\u001a:\u0010\u001b\u001a\u00020\u00012\u0006\u0010\u001c\u001a\u00020\u00102\f\u0010\u001d\u001a\b\u0012\u0004\u0012\u00020\u00100\u001e2\f\u0010\u001f\u001a\b\u0012\u0004\u0012\u00020\u00010\n2\f\u0010 \u001a\b\u0012\u0004\u0012\u00020\u00010\nH\u0003\u001a\u0010\u0010!\u001a\u00020\u00162\u0006\u0010\"\u001a\u00020\u0010H\u0002\u00a8\u0006#"}, d2 = {"CompletedBadge", "", "DetailContent", "module", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "localProgress", "", "onProgressChange", "Lkotlin/Function1;", "onStartLearning", "Lkotlin/Function0;", "onMarkComplete", "modifier", "Landroidx/compose/ui/Modifier;", "LearningModuleDetailScreen", "moduleId", "", "viewModel", "Lcom/microcareer/match/presentation/learning/LearningViewModel;", "onBack", "MetaItem", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "MilestoneChip", "milestone", "current", "SkillUnlockedDialog", "moduleName", "unlockedSkills", "", "onContinue", "onBackToJobs", "contentTypeIcon", "type", "app_debug"})
public final class LearningmoduledetailscreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LearningModuleDetailScreen(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, @org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.learning.LearningViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void DetailContent(com.microcareer.match.data.local.entity.LearningModuleEntity module, int localProgress, kotlin.jvm.functions.Function1<? super java.lang.Integer, kotlin.Unit> onProgressChange, kotlin.jvm.functions.Function0<kotlin.Unit> onStartLearning, kotlin.jvm.functions.Function0<kotlin.Unit> onMarkComplete, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SkillUnlockedDialog(java.lang.String moduleName, java.util.List<java.lang.String> unlockedSkills, kotlin.jvm.functions.Function0<kotlin.Unit> onContinue, kotlin.jvm.functions.Function0<kotlin.Unit> onBackToJobs) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void CompletedBadge() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MetaItem(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void MilestoneChip(int milestone, int current, java.lang.String label) {
    }
    
    private static final androidx.compose.ui.graphics.vector.ImageVector contentTypeIcon(java.lang.String type) {
        return null;
    }
}