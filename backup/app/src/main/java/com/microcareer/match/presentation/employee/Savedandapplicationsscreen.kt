package com.microcareer.match.presentation.employee

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.presentation.home.HomeViewModel
import com.microcareer.match.R
// ─────────────────────────────────────────────────────────
// SAVED & APPLICATIONS SCREEN
// ─────────────────────────────────────────────────────────
// Two tabs:
//   • Saved    — jobs the employee bookmarked, tap to view
//   • Applied  — jobs already applied to, with status badge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SavedAndApplicationsScreen(
    viewModel  : HomeViewModel,
    onBack     : () -> Unit,
    onJobClick : (String) -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
// REPLACE WITH THESE
    val savedJobs   = uiState.savedJobs
    val appliedJobs = uiState.appliedJobs
    var selectedTab by remember { mutableIntStateOf(0) }

    Scaffold(
        topBar = {
            Column {
                TopAppBar(
                    title = { Text(stringResource(R.string.my_activity), fontWeight = FontWeight.Bold) },
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(
                                imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = stringResource(R.string.back)
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer
                    )
                )
                TabRow(selectedTabIndex = selectedTab) {
                    Tab(
                        selected = selectedTab == 0,
                        onClick  = { selectedTab = 0 },
                        text     = {
                            Row(
                                verticalAlignment     = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Bookmark, null, modifier = Modifier.size(16.dp))
                                Text(stringResource(R.string.saved))
                                if (savedJobs.isNotEmpty()) {
                                    Badge { Text(savedJobs.size.toString()) }
                                }
                            }
                        }
                    )
                    Tab(
                        selected = selectedTab == 1,
                        onClick  = { selectedTab = 1 },
                        text     = {
                            Row(
                                verticalAlignment     = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(6.dp)
                            ) {
                                Icon(Icons.Default.Assignment, null, modifier = Modifier.size(16.dp))
                                Text(stringResource(R.string.applied))
                                if (appliedJobs.isNotEmpty()) {
                                    Badge { Text(appliedJobs.size.toString()) }
                                }
                            }
                        }
                    )
                }
            }
        }
    ) { padding ->

        when (selectedTab) {
            0 -> SavedJobsList(
                jobs       = savedJobs,
                padding    = padding,
                onJobClick = onJobClick,
                onUnsave   = { job -> viewModel.toggleSaveJob(job.id, job.isSaved) }
            )
            1 -> AppliedJobsList(
                jobs       = appliedJobs,
                padding    = padding,
                onJobClick = onJobClick
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
// SAVED JOBS TAB
// ─────────────────────────────────────────────────────────

@Composable
private fun SavedJobsList(
    jobs       : List<JobEntity>,
    padding    : PaddingValues,
    onJobClick : (String) -> Unit,
    onUnsave   : (JobEntity) -> Unit
) {
    if (jobs.isEmpty()) {
        EmptyState(
            icon    = Icons.Default.BookmarkBorder,
            title   = stringResource(R.string.no_saved_jobs_yet),
            message = "Tap the bookmark icon on any job card to save it here for later.",
            padding = padding
        )
        return
    }

    LazyColumn(
        modifier            = Modifier.fillMaxSize().padding(padding),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(jobs, key = { it.id }) { job ->
            ActivityJobCard(
                job        = job,
                onClick    = { onJobClick(job.id) },
                trailingContent = {
                    // Quick unsave directly from this list
                    IconButton(onClick = { onUnsave(job) }) {
                        Icon(
                            imageVector        = Icons.Default.Bookmark,
                            contentDescription = stringResource(R.string.remove_from_saved),
                            tint               = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
// APPLIED JOBS TAB
// ─────────────────────────────────────────────────────────

@Composable
private fun AppliedJobsList(
    jobs       : List<JobEntity>,
    padding    : PaddingValues,
    onJobClick : (String) -> Unit
) {
    if (jobs.isEmpty()) {
        EmptyState(
            icon    = Icons.Default.Assignment,
            title   = stringResource(R.string.no_applications_yet),
            message = "Jobs you apply to will appear here. You can track their status from this list.",
            padding = padding
        )
        return
    }

    LazyColumn(
        modifier            = Modifier.fillMaxSize().padding(padding),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items(jobs, key = { it.id }) { job ->
            ActivityJobCard(
                job     = job,
                onClick = { onJobClick(job.id) },
                trailingContent = {
                    // Application status badge
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = MaterialTheme.colorScheme.tertiaryContainer
                    ) {
                        Text(
                            text      = "Applied ✓",
                            modifier  = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                            style     = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.SemiBold,
                            color     = MaterialTheme.colorScheme.onTertiaryContainer
                        )
                    }
                }
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
// SHARED JOB CARD (compact — no apply button, tap to view)
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun ActivityJobCard(
    job              : JobEntity,
    onClick          : () -> Unit,
    trailingContent  : @Composable () -> Unit
) {
    ElevatedCard(
        onClick  = onClick,
        modifier = Modifier.fillMaxWidth()
    ) {
        Row(
            modifier              = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment     = Alignment.Top,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                modifier            = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Text(
                    text       = job.title,
                    style      = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                if (!job.employerName.isNullOrBlank()) {
                    Text(
                        text  = job.employerName,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary,
                        maxLines = 1
                    )
                }
                // Location + payment in one line
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment     = Alignment.CenterVertically
                ) {
                    if (job.location.isNotBlank()) {
                        Row(
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(2.dp)
                        ) {
                            Icon(
                                Icons.Default.LocationOn, null,
                                modifier = Modifier.size(12.dp),
                                tint     = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                text  = job.location,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                    Text(
                        text  = "•",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text  = "${job.payment.toInt()} ${job.currency}",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // Required skills (compact — max 3)
                if (job.requiredSkills.isNotEmpty()) {
                    FlowRow(
                        horizontalArrangement = Arrangement.spacedBy(4.dp),
                        verticalArrangement   = Arrangement.spacedBy(4.dp),
                        modifier              = Modifier.padding(top = 4.dp)
                    ) {
                        job.requiredSkills.take(3).forEach { skill ->
                            SuggestionChip(
                                onClick = {},
                                label   = {
                                    Text(skill, style = MaterialTheme.typography.labelSmall)
                                }
                            )
                        }
                        if (job.requiredSkills.size > 3) {
                            SuggestionChip(
                                onClick = {},
                                label   = {
                                    Text(
                                        "+${job.requiredSkills.size - 3} more",
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            )
                        }
                    }
                }
            }

            Spacer(Modifier.width(8.dp))
            trailingContent()
        }
    }
}

// ─────────────────────────────────────────────────────────
// EMPTY STATE
// ─────────────────────────────────────────────────────────

@Composable
private fun EmptyState(
    icon    : androidx.compose.ui.graphics.vector.ImageVector,
    title   : String,
    message : String,
    padding : PaddingValues
) {
    Box(
        modifier            = Modifier.fillMaxSize().padding(padding).padding(32.dp),
        contentAlignment    = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector        = icon,
                contentDescription = null,
                modifier           = Modifier.size(64.dp),
                tint               = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                text       = title,
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                text  = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}