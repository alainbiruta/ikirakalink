package com.microcareer.match.presentation.learning;

import androidx.lifecycle.ViewModel;
import com.microcareer.match.data.local.entity.LearningModuleEntity;
import com.microcareer.match.data.repository.LearningRepository;
import com.microcareer.match.data.repository.LearningRepositoryImpl;
import com.microcareer.match.domain.model.Resource;
import dagger.hilt.android.lifecycle.HiltViewModel;
import kotlinx.coroutines.FlowPreview;
import kotlinx.coroutines.flow.*;
import javax.inject.Inject;

@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000T\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0002\u0010\b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0010\u0002\n\u0002\b\f\b\u0007\u0018\u00002\u00020\u0001B\u000f\b\u0007\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010\u001e\u001a\u00020\u001fJ\u000e\u0010 \u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\nJ\b\u0010\"\u001a\u00020\u001fH\u0002J\b\u0010#\u001a\u00020\u001fH\u0002J\u000e\u0010$\u001a\u00020\u001f2\u0006\u0010%\u001a\u00020\u000fJ\u000e\u0010&\u001a\u00020\u001f2\u0006\u0010\'\u001a\u00020\nJ\u0006\u0010(\u001a\u00020\u001fJ\u0016\u0010)\u001a\u00020\u001f2\u0006\u0010!\u001a\u00020\n2\u0006\u0010*\u001a\u00020\u000bR\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R \u0010\b\u001a\u0014\u0012\u0010\u0012\u000e\u0012\u0004\u0012\u00020\n\u0012\u0004\u0012\u00020\u000b0\t0\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\f\u001a\b\u0012\u0004\u0012\u00020\n0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0010\u001a\b\u0012\u0004\u0012\u00020\u00110\rX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0012\u001a\b\u0012\u0004\u0012\u00020\u00070\u0013\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0014\u0010\u0015R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0017\u0010\u0016\u001a\b\u0012\u0004\u0012\u00020\n0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019R\u0017\u0010\u001a\u001a\b\u0012\u0004\u0012\u00020\u000f0\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001b\u0010\u0019R\u0017\u0010\u001c\u001a\b\u0012\u0004\u0012\u00020\u00110\u0017\u00a2\u0006\b\n\u0000\u001a\u0004\b\u001d\u0010\u0019\u00a8\u0006+"}, d2 = {"Lcom/microcareer/match/presentation/learning/LearningViewModel;", "Landroidx/lifecycle/ViewModel;", "learningRepository", "Lcom/microcareer/match/data/repository/LearningRepository;", "(Lcom/microcareer/match/data/repository/LearningRepository;)V", "_events", "Lkotlinx/coroutines/flow/MutableSharedFlow;", "Lcom/microcareer/match/presentation/learning/LearningEvent;", "_progressUpdates", "Lkotlin/Pair;", "", "", "_searchQuery", "Lkotlinx/coroutines/flow/MutableStateFlow;", "_selectedFilter", "Lcom/microcareer/match/presentation/learning/LearningFilter;", "_uiState", "Lcom/microcareer/match/presentation/learning/LearningUiState;", "events", "Lkotlinx/coroutines/flow/SharedFlow;", "getEvents", "()Lkotlinx/coroutines/flow/SharedFlow;", "searchQuery", "Lkotlinx/coroutines/flow/StateFlow;", "getSearchQuery", "()Lkotlinx/coroutines/flow/StateFlow;", "selectedFilter", "getSelectedFilter", "uiState", "getUiState", "clearSearch", "", "markAsComplete", "moduleId", "observeModules", "observeProgressUpdates", "onFilterChange", "filter", "onSearchQueryChange", "query", "refresh", "updateProgress", "progress", "app_debug"})
@dagger.hilt.android.lifecycle.HiltViewModel()
public final class LearningViewModel extends androidx.lifecycle.ViewModel {
    @org.jetbrains.annotations.NotNull()
    private final com.microcareer.match.data.repository.LearningRepository learningRepository = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.learning.LearningUiState> _uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.learning.LearningUiState> uiState = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<com.microcareer.match.presentation.learning.LearningEvent> _events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.learning.LearningEvent> events = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<java.lang.String> _searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<java.lang.String> searchQuery = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableStateFlow<com.microcareer.match.presentation.learning.LearningFilter> _selectedFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.learning.LearningFilter> selectedFilter = null;
    @org.jetbrains.annotations.NotNull()
    private final kotlinx.coroutines.flow.MutableSharedFlow<kotlin.Pair<java.lang.String, java.lang.Integer>> _progressUpdates = null;
    
    @javax.inject.Inject()
    public LearningViewModel(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.data.repository.LearningRepository learningRepository) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.learning.LearningUiState> getUiState() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.SharedFlow<com.microcareer.match.presentation.learning.LearningEvent> getEvents() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<java.lang.String> getSearchQuery() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final kotlinx.coroutines.flow.StateFlow<com.microcareer.match.presentation.learning.LearningFilter> getSelectedFilter() {
        return null;
    }
    
    private final void observeModules() {
    }
    
    @kotlin.OptIn(markerClass = {kotlinx.coroutines.FlowPreview.class})
    private final void observeProgressUpdates() {
    }
    
    public final void onSearchQueryChange(@org.jetbrains.annotations.NotNull()
    java.lang.String query) {
    }
    
    public final void clearSearch() {
    }
    
    public final void onFilterChange(@org.jetbrains.annotations.NotNull()
    com.microcareer.match.presentation.learning.LearningFilter filter) {
    }
    
    /**
     * Debounced — safe to call on every slider tick
     */
    public final void updateProgress(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId, int progress) {
    }
    
    /**
     * Marks module complete immediately (no debounce) AND calls
     * the backend to unlock skills on user.skills server-side.
     * Emits a [LearningEvent.SkillUnlocked] event so the UI can
     * show the celebration dialog with the exact skill names.
     */
    public final void markAsComplete(@org.jetbrains.annotations.NotNull()
    java.lang.String moduleId) {
    }
    
    public final void refresh() {
    }
}