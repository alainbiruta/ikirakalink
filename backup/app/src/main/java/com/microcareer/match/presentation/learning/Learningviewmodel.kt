package com.microcareer.match.presentation.learning

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.data.repository.LearningRepository
import com.microcareer.match.data.repository.LearningRepositoryImpl
import com.microcareer.match.domain.model.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearningViewModel @Inject constructor(
    private val learningRepository: LearningRepository
) : ViewModel() {

    // ── UI State ──────────────────────────────────────────
    private val _uiState = MutableStateFlow<LearningUiState>(LearningUiState.Loading)
    val uiState: StateFlow<LearningUiState> = _uiState.asStateFlow()

    // ── Events (one-shot, consumed by UI) ─────────────────
    private val _events = MutableSharedFlow<LearningEvent>()
    val events: SharedFlow<LearningEvent> = _events.asSharedFlow()

    // ── Search / filter ───────────────────────────────────
    private val _searchQuery   = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedFilter = MutableStateFlow(LearningFilter.ALL)
    val selectedFilter: StateFlow<LearningFilter> = _selectedFilter.asStateFlow()

    // ── Debounced progress writes ─────────────────────────
    private val _progressUpdates = MutableSharedFlow<Pair<String, Int>>()

    init {
        observeModules()
        observeProgressUpdates()
        // Fetch fresh data from server on first open
        refresh()
    }

    // ══════════════════════════════════════════════════════
    // OBSERVE MODULES
    // ══════════════════════════════════════════════════════

    private fun observeModules() {
        viewModelScope.launch {
            combine(_searchQuery, _selectedFilter) { q, f -> q to f }
                .flatMapLatest { (query, filter) ->
                    when {
                        query.isNotBlank()              -> learningRepository.searchModules(query)
                        filter == LearningFilter.RECOMMENDED -> learningRepository.getRecommendedModules()
                        filter == LearningFilter.COMPLETED   -> learningRepository.getCompletedModules()
                        else                            -> learningRepository.getAllModules()
                    }
                }
                .distinctUntilChanged()
                .collect { resource ->
                    _uiState.value = when (resource) {
                        is Resource.Loading -> LearningUiState.Loading
                        is Resource.Success -> {
                            val modules = resource.data ?: emptyList()
                            if (modules.isEmpty()) LearningUiState.Empty
                            else LearningUiState.Success(modules)
                        }
                        is Resource.Error   -> LearningUiState.Error(
                            resource.message
                        )
                    }
                }
        }
    }

    // ══════════════════════════════════════════════════════
    // DEBOUNCED PROGRESS WRITES
    // ══════════════════════════════════════════════════════

    @OptIn(FlowPreview::class)
    private fun observeProgressUpdates() {
        viewModelScope.launch {
            _progressUpdates
                .debounce(500)
                .collect { (moduleId, progress) ->
                    learningRepository.updateProgress(moduleId, progress)
                }
        }
    }

    // ══════════════════════════════════════════════════════
    // PUBLIC ACTIONS
    // ══════════════════════════════════════════════════════

    fun onSearchQueryChange(query: String) { _searchQuery.value = query }
    fun clearSearch()                      { _searchQuery.value = "" }
    fun onFilterChange(filter: LearningFilter) { _selectedFilter.value = filter }

    /** Debounced — safe to call on every slider tick */
    fun updateProgress(moduleId: String, progress: Int) {
        viewModelScope.launch { _progressUpdates.emit(moduleId to progress) }
    }

    /**
     * Marks module complete immediately (no debounce) AND calls
     * the backend to unlock skills on user.skills server-side.
     * Emits a [LearningEvent.SkillUnlocked] event so the UI can
     * show the celebration dialog with the exact skill names.
     */
    fun markAsComplete(moduleId: String) {
        viewModelScope.launch {
            val impl = learningRepository as? LearningRepositoryImpl
            if (impl != null) {
                val result = impl.completeModule(moduleId)
                if (result is Resource.Success) {
                    val skills = result.data
                    _events.emit(
                        LearningEvent.SkillUnlocked(
                            moduleId   = moduleId,
                            newSkills  = skills
                        )
                    )
                }
            } else {
                // Fallback: local-only update (shouldn't happen in production)
                learningRepository.updateProgress(moduleId, 100)
                _events.emit(LearningEvent.SkillUnlocked(moduleId, emptyList()))
            }
        }
    }

    fun refresh() {
        viewModelScope.launch { learningRepository.refreshModules() }
    }
}

// ══════════════════════════════════════════════════════════
// UI STATE
// ══════════════════════════════════════════════════════════

sealed interface LearningUiState {
    data object Loading : LearningUiState
    data object Empty   : LearningUiState
    data class  Success(val modules: List<LearningModuleEntity>) : LearningUiState
    data class  Error(val message: String) : LearningUiState
}

// ══════════════════════════════════════════════════════════
// ONE-SHOT EVENTS
// ══════════════════════════════════════════════════════════

sealed interface LearningEvent {
    /**
     * Fired after [LearningViewModel.markAsComplete].
     * [newSkills] is empty when offline; the UI should still
     * show the celebration dialog but skip the "X new jobs" text.
     */
    data class SkillUnlocked(
        val moduleId  : String,
        val newSkills : List<String>
    ) : LearningEvent
}

// ══════════════════════════════════════════════════════════
// FILTERS
// ══════════════════════════════════════════════════════════

enum class LearningFilter(val label: String) {
    ALL("All"),
    RECOMMENDED("For You"),
    COMPLETED("Completed")
}