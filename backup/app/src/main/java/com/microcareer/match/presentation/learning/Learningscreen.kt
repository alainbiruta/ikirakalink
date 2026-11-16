package com.microcareer.match.presentation.learning

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microcareer.match.data.local.entity.LearningModuleEntity
import com.microcareer.match.R

/**
 * Learning modules screen.
 *
 * Shows available learning content with:
 * - Search functionality
 * - Category filters (All, Recommended, Completed)
 * - Module cards with progress
 * - Start learning action
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningScreen(
    viewModel: LearningViewModel,
    onBack: () -> Unit,
    onNavigateToModuleDetail: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val searchQuery by viewModel.searchQuery.collectAsStateWithLifecycle()
    val selectedFilter by viewModel.selectedFilter.collectAsStateWithLifecycle()

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.learning)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                actions = {
                    IconButton(onClick = { viewModel.refresh() }) {
                        Icon(Icons.Default.Refresh, contentDescription = stringResource(R.string.refresh))
                    }
                }
            )
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {

            // ── Search Bar ────────────────────────────────────
            SearchBar(
                query = searchQuery,
                onQueryChange = viewModel::onSearchQueryChange,
                onClearQuery = viewModel::clearSearch,
                modifier = Modifier.padding(16.dp)
            )

            // ── Filter Chips ──────────────────────────────────
            FilterChips(
                selectedFilter = selectedFilter,
                onFilterChange = viewModel::onFilterChange,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
            )

            // ── Content ───────────────────────────────────────
            when (val state = uiState) {
                is LearningUiState.Loading -> LoadingContent()
                is LearningUiState.Empty -> EmptyContent(
                    searchQuery = searchQuery,
                    onClearSearch = viewModel::clearSearch
                )
                is LearningUiState.Error -> ErrorContent(
                    message = state.message,
                    onRetry = { viewModel.refresh() }
                )
                is LearningUiState.Success -> ModulesList(
                    modules = state.modules,
                    onModuleClick = { moduleId ->
                        onNavigateToModuleDetail(moduleId)
                        android.util.Log.d("LearningScreen", "Module clicked: $moduleId")
                    },
                    onStartLearning = { moduleId ->
                        onNavigateToModuleDetail(moduleId)
                        android.util.Log.d("LearningScreen", "Start learning: $moduleId")
                    }
                )
            }
        }
    }
}

/* ══════════════════════════════════════════════════════════
   SEARCH BAR
   ══════════════════════════════════════════════════════════ */

@Composable
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    onClearQuery: () -> Unit,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(stringResource(R.string.search_modules)) },
        leadingIcon = {
            Icon(Icons.Default.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = onClearQuery) {
                    Icon(Icons.Default.Close, contentDescription = stringResource(R.string.clear))
                }
            }
        },
        singleLine = true
    )
}

/* ══════════════════════════════════════════════════════════
   FILTER CHIPS
   ══════════════════════════════════════════════════════════ */

@Composable
private fun FilterChips(
    selectedFilter: LearningFilter,
    onFilterChange: (LearningFilter) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(LearningFilter.entries.toTypedArray()) { filter ->
            FilterChip(
                selected = selectedFilter == filter,
                onClick = { onFilterChange(filter) },
                label = { Text(filter.label) },
                leadingIcon = if (selectedFilter == filter) {
                    { Icon(Icons.Default.Check, contentDescription = null, modifier = Modifier.size(18.dp)) }
                } else null
            )
        }
    }
}

/* ══════════════════════════════════════════════════════════
   MODULES LIST
   ══════════════════════════════════════════════════════════ */

@Composable
private fun ModulesList(
    modules: List<LearningModuleEntity>,
    onModuleClick: (String) -> Unit,
    onStartLearning: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(
            items = modules,
            key = { it.id }
        ) { module ->
            ModuleCard(
                module = module,
                onClick = { onModuleClick(module.id) },
                onStartLearning = { onStartLearning(module.id) }
            )
        }
    }
}

/* ══════════════════════════════════════════════════════════
   MODULE CARD
   ══════════════════════════════════════════════════════════ */

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ModuleCard(
    module: LearningModuleEntity,
    onClick: () -> Unit,
    onStartLearning: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {

            // ── Title & Skill ─────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = module.title,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                    Text(
                        text = module.skillName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                // Completion badge
                if (module.isCompleted) {
                    Surface(
                        color = MaterialTheme.colorScheme.primaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                Icons.Default.CheckCircle,
                                contentDescription = null,
                                modifier = Modifier.size(14.dp),
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Text(
                                stringResource(R.string.done),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // ── Description ───────────────────────────────────
            Text(
                text = module.description,
                style = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Meta Info ─────────────────────────────────────
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                // Duration
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Schedule,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        "${module.duration} min",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Difficulty
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.TrendingUp,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        module.difficulty,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }

                // Content Type
                Row(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        when (module.contentType) {
                            "VIDEO" -> Icons.Default.PlayCircle
                            "INTERACTIVE" -> Icons.Default.TouchApp
                            else -> Icons.Default.Article
                        },
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        module.contentType,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ── Progress Bar ──────────────────────────────────
            if (module.progress > 0 && !module.isCompleted) {
                Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                    LinearProgressIndicator(
                        progress = { module.progress / 100f },
                        modifier = Modifier.fillMaxWidth(),
                    )
                    Text(
                        "${module.progress}% complete",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // ── Action Button ─────────────────────────────────
            Button(
                onClick = onStartLearning,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    when {
                        module.isCompleted -> Icons.Default.Replay
                        module.progress > 0 -> Icons.Default.PlayArrow
                        else -> Icons.Default.School
                    },
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
                Spacer(Modifier.width(8.dp))
                Text(
                    when {
                        module.isCompleted -> "Review"
                        module.progress > 0 -> "Continue"
                        else -> "Start Learning"
                    }
                )
            }
        }
    }
}

/* ══════════════════════════════════════════════════════════
   LOADING STATE
   ══════════════════════════════════════════════════════════ */

@Composable
private fun LoadingContent() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

/* ══════════════════════════════════════════════════════════
   EMPTY STATE
   ══════════════════════════════════════════════════════════ */

@Composable
private fun EmptyContent(
    searchQuery: String,
    onClearSearch: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.School,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = if (searchQuery.isNotEmpty()) {
                "No modules found"
            } else {
                "No learning modules available"
            },
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = if (searchQuery.isNotEmpty()) {
                "Try a different search term"
            } else {
                "Check back later for new content"
            },
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        if (searchQuery.isNotEmpty()) {
            Spacer(Modifier.height(16.dp))
            Button(onClick = onClearSearch) {
                Text(stringResource(R.string.clear_search))
            }
        }
    }
}

/* ══════════════════════════════════════════════════════════
   ERROR STATE
   ══════════════════════════════════════════════════════════ */

@Composable
private fun ErrorContent(
    message: String,
    onRetry: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            Icons.Default.ErrorOutline,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text = "Oops!",
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text = message,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.try_again))
        }
    }
}