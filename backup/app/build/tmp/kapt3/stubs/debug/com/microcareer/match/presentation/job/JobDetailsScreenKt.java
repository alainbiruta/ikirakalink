package com.microcareer.match.presentation.job;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.graphics.vector.ImageVector;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextAlign;
import androidx.compose.ui.text.style.TextOverflow;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000<\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0006\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0004\u001a\u0010\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a&\u0010\u0004\u001a\u00020\u00012\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\bH\u0003\u001a\u0018\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0006H\u0003\u001a\u0018\u0010\r\u001a\u00020\u00012\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\\\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u000f2\f\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0007\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\f\u0010\u0014\u001a\b\u0012\u0004\u0012\u00020\u00010\b2\b\b\u0002\u0010\u0015\u001a\u00020\u00162\b\b\u0002\u0010\u0017\u001a\u00020\u0018H\u0007\u001a\u0010\u0010\u0019\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u0003H\u0003\u001a\u0010\u0010\u001a\u001a\u00020\u00012\u0006\u0010\u001b\u001a\u00020\u0006H\u0003\u00a8\u0006\u001c"}, d2 = {"EmptyContent", "", "padding", "Landroidx/compose/foundation/layout/PaddingValues;", "ErrorContent", "error", "", "onRetry", "Lkotlin/Function0;", "InfoChip", "icon", "Landroidx/compose/ui/graphics/vector/ImageVector;", "label", "JobContent", "state", "Lcom/microcareer/match/presentation/job/JobDetailsUiState;", "JobDetailsScreen", "uiState", "onBack", "onApply", "onToggleSave", "snackbarHostState", "Landroidx/compose/material3/SnackbarHostState;", "modifier", "Landroidx/compose/ui/Modifier;", "LoadingContent", "SectionHeader", "title", "app_debug"})
public final class JobDetailsScreenKt {
    
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class, androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    public static final void JobDetailsScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.job.JobDetailsUiState uiState, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onRetry, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onApply, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onToggleSave, @org.jetbrains.annotations.NotNull()
    androidx.compose.material3.SnackbarHostState snackbarHostState, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingContent(androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorContent(java.lang.String error, androidx.compose.foundation.layout.PaddingValues padding, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyContent(androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void JobContent(com.microcareer.match.presentation.job.JobDetailsUiState state, androidx.compose.foundation.layout.PaddingValues padding) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SectionHeader(java.lang.String title) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void InfoChip(androidx.compose.ui.graphics.vector.ImageVector icon, java.lang.String label) {
    }
}