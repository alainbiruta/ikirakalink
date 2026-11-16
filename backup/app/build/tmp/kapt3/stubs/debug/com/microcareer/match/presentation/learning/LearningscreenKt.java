package com.microcareer.match.presentation.learning;

import androidx.compose.foundation.layout.*;
import androidx.compose.material.icons.Icons;
import androidx.compose.material.icons.filled.*;
import androidx.compose.material3.*;
import androidx.compose.runtime.*;
import androidx.compose.ui.Alignment;
import androidx.compose.ui.Modifier;
import androidx.compose.ui.text.font.FontWeight;
import androidx.compose.ui.text.style.TextOverflow;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.R;

@kotlin.Metadata(mv = {1, 9, 0}, k = 2, xi = 48, d1 = {"\u0000B\n\u0000\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0005\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010 \n\u0002\b\u0006\u001a\u001e\u0010\u0000\u001a\u00020\u00012\u0006\u0010\u0002\u001a\u00020\u00032\f\u0010\u0004\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a\u001e\u0010\u0006\u001a\u00020\u00012\u0006\u0010\u0007\u001a\u00020\u00032\f\u0010\b\u001a\b\u0012\u0004\u0012\u00020\u00010\u0005H\u0003\u001a.\u0010\t\u001a\u00020\u00012\u0006\u0010\n\u001a\u00020\u000b2\u0012\u0010\f\u001a\u000e\u0012\u0004\u0012\u00020\u000b\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0003\u001a<\u0010\u0010\u001a\u00020\u00012\u0006\u0010\u0011\u001a\u00020\u00122\f\u0010\u0013\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\u0012\u0010\u0014\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0007\u001a\b\u0010\u0015\u001a\u00020\u0001H\u0003\u001a6\u0010\u0016\u001a\u00020\u00012\u0006\u0010\u0017\u001a\u00020\u00182\f\u0010\u0019\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\f\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0003\u001aH\u0010\u001b\u001a\u00020\u00012\f\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00180\u001d2\u0012\u0010\u001e\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\u0012\u0010\u001a\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0003\u001a<\u0010\u001f\u001a\u00020\u00012\u0006\u0010 \u001a\u00020\u00032\u0012\u0010!\u001a\u000e\u0012\u0004\u0012\u00020\u0003\u0012\u0004\u0012\u00020\u00010\r2\f\u0010\"\u001a\b\u0012\u0004\u0012\u00020\u00010\u00052\b\b\u0002\u0010\u000e\u001a\u00020\u000fH\u0003\u00a8\u0006#"}, d2 = {"EmptyContent", "", "searchQuery", "", "onClearSearch", "Lkotlin/Function0;", "ErrorContent", "message", "onRetry", "FilterChips", "selectedFilter", "Lcom/microcareer/match/presentation/learning/LearningFilter;", "onFilterChange", "Lkotlin/Function1;", "modifier", "Landroidx/compose/ui/Modifier;", "LearningScreen", "viewModel", "Lcom/microcareer/match/presentation/learning/LearningViewModel;", "onBack", "onNavigateToModuleDetail", "LoadingContent", "ModuleCard", "module", "Lcom/microcareer/match/data/local/entity/LearningModuleEntity;", "onClick", "onStartLearning", "ModulesList", "modules", "", "onModuleClick", "SearchBar", "query", "onQueryChange", "onClearQuery", "app_debug"})
public final class LearningscreenKt {
    
    /**
     * Learning modules screen.
     *
     * Shows available learning content with:
     * - Search functionality
     * - Category filters (All, Recommended, Completed)
     * - Module cards with progress
     * - Start learning action
     */
    @kotlin.OptIn(markerClass = {androidx.compose.material3.ExperimentalMaterial3Api.class})
    @androidx.compose.runtime.Composable()
    public static final void LearningScreen(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.learning.LearningViewModel viewModel, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function0<kotlin.Unit> onBack, @org.jetbrains.annotations.NotNull()
    kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onNavigateToModuleDetail, @org.jetbrains.annotations.NotNull()
    androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void SearchBar(java.lang.String query, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onQueryChange, kotlin.jvm.functions.Function0<kotlin.Unit> onClearQuery, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void FilterChips(com.microcareer.match.presentation.learning.LearningFilter selectedFilter, kotlin.jvm.functions.Function1<? super com.microcareer.match.presentation.learning.LearningFilter, kotlin.Unit> onFilterChange, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ModulesList(java.util.List<com.microcareer.match.data.local.entity.LearningModuleEntity> modules, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onModuleClick, kotlin.jvm.functions.Function1<? super java.lang.String, kotlin.Unit> onStartLearning, androidx.compose.ui.Modifier modifier) {
    }
    
    @kotlin.OptIn(markerClass = {androidx.compose.foundation.layout.ExperimentalLayoutApi.class})
    @androidx.compose.runtime.Composable()
    private static final void ModuleCard(com.microcareer.match.data.local.entity.LearningModuleEntity module, kotlin.jvm.functions.Function0<kotlin.Unit> onClick, kotlin.jvm.functions.Function0<kotlin.Unit> onStartLearning, androidx.compose.ui.Modifier modifier) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void LoadingContent() {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void EmptyContent(java.lang.String searchQuery, kotlin.jvm.functions.Function0<kotlin.Unit> onClearSearch) {
    }
    
    @androidx.compose.runtime.Composable()
    private static final void ErrorContent(java.lang.String message, kotlin.jvm.functions.Function0<kotlin.Unit> onRetry) {
    }
}