package com.microcareer.match.presentation.employee;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.microcareer.match.data.local.entity.JobEntity;
import com.microcareer.match.presentation.home.HomeViewModel;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000D\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a1\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0011\u0010\u0006\u001a\r\u0012\u0004\u0012\u00020\u00010\u0005\u00a2\u0006\u0002\b\u0007H\u0003\u001a2\u0010\b\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u001a(\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0013\u001a\u00020\u000f2\u0006\u0010\u0014\u001a\u00020\u000f2\u0006\u0010\u000b\u001a\u00020\fH\u0003\u001a2\u0010\u0015\u001a\u00020\u00012\u0006\u0010\u0016\u001a\u00020\u00172\f\u0010\u0018\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000eH\u0007\u001aF\u0010\u0019\u001a\u00020\u00012\f\u0010\t\u001a\b\u0012\u0004\u0012\u00020\u00030\n2\u0006\u0010\u000b\u001a\u00020\f2\u0012\u0010\r\u001a\u000e\u0012\u0004\u0012\u00020\u000f\u0012\u0004\u0012\u00020\u00010\u000e2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\u000eH\u0003\u00a8\u0006\u001b"}, d2 = {"ActivityJobCard", "", "job", "Lcom/microcareer/match/data/local/entity/JobEntity;", "onClick", "Lkotlin/Function0;", "trailingContent", "Landroidx/compose/runtime/Composable;", "AppliedJobsList", "jobs", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "onJobClick", "Lkotlin/Function1;", "", "EmptyState", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "title", "message", "SavedAndApplicationsScreen", "viewModel", "Lcom/microcareer/match/presentation/home/HomeViewModel;", "onBack", "SavedJobsList", "onUnsave", "app_debug"})
public final class SavedandapplicationsscreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void SavedAndApplicationsScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.home.HomeViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SavedJobsList(java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick, kotlin.jvm.functions.Function1<? super com.microcareer.match.data.local.entity.JobEntity, kotlin.Unit> onUnsave) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void AppliedJobsList(java.util.List<com.microcareer.match.data.local.entity.JobEntity> jobs, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onJobClick) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void ActivityJobCard(com.microcareer.match.data.local.entity.JobEntity job, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> trailingContent) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyState(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String title, java.lang.String message, androidx.compose.foundation.layout.PaddingValues padding) {
    }
}