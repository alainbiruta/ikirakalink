package com.microcareer.match.presentation.home

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microcareer.match.presentation.home.HomeViewModel
import com.microcareer.match.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    jobsState             : JobsState,
    isRefreshing          : Boolean,
    onRefresh             : () -> Unit,
    onNavigateToJobDetails: (String) -> Unit,
    onNavigateToSkillInput: () -> Unit,
    onNavigateToLearn     : () -> Unit,
    onNavigateToProfile   : () -> Unit,
    onSaveJob             : (String) -> Unit,
    onApplyJob            : (String) -> Unit,
    onLogout              : () -> Unit,
    // Snackbar state passed in so nav layer can collect HomeUiEvents
    // and show snackbars via the same host the Scaffold owns.
    snackbarHostState     : SnackbarHostState = remember { SnackbarHostState() },
    modifier              : Modifier = Modifier
) {
    var selectedTab by rememberSaveable { mutableStateOf(HomeTab.JOBS) }
    var showMenu    by remember { mutableStateOf(false) }

    Scaffold(
        modifier     = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.microcareer)) },
                actions = {
                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.AccountCircle, contentDescription = stringResource(R.string.profile))
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, contentDescription = stringResource(R.string.more_options))
                    }
                    DropdownMenu(
                        expanded        = showMenu,
                        onDismissRequest = { showMenu = false }
                    ) {
                        DropdownMenuItem(
                            text         = { Text(stringResource(R.string.log_out)) },
                            leadingIcon  = { Icon(Icons.Default.ExitToApp, contentDescription = null) },
                            onClick      = { showMenu = false; onLogout() }
                        )
                    }
                }
            )
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == HomeTab.JOBS,
                    onClick  = { selectedTab = HomeTab.JOBS },
                    icon     = { Icon(Icons.Default.Work, contentDescription = null) },
                    label    = { Text(stringResource(R.string.jobs)) }
                )
                NavigationBarItem(
                    selected = false,  // ✅ Never selected (navigates away)
                    onClick  = onNavigateToSkillInput,
                    icon     = { Icon(Icons.Default.Star, contentDescription = null) },
                    label    = { Text(stringResource(R.string.skills)) }
                )
                NavigationBarItem(
                    selected = false,  // ✅ FIXED: Never selected (navigates away)
                    onClick  = onNavigateToLearn,  // ✅ FIXED: Navigate to LearningScreen
                    icon     = { Icon(Icons.Default.School, contentDescription = null) },
                    label    = { Text(stringResource(R.string.learn)) }
                )
                NavigationBarItem(
                    selected = false,  // ✅ Never selected (navigates away)
                    onClick  = onNavigateToProfile,
                    icon     = { Icon(Icons.Default.Person, contentDescription = null) },
                    label    = { Text(stringResource(R.string.profile)) }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            HomeTab.JOBS -> JobsListTab(
                modifier               = Modifier.padding(padding),
                jobsState              = jobsState,
                isRefreshing           = isRefreshing,
                onRefresh              = onRefresh,
                onJobClick             = onNavigateToJobDetails,
                onSaveJob              = onSaveJob,
                onApplyJob             = onApplyJob,
                onNavigateToSkillInput = onNavigateToSkillInput
            )
            HomeTab.LEARN -> Box(
                modifier         = Modifier.padding(padding).fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(stringResource(R.string.learning_content_coming_soon))
            }
        }
    }
}

/* ============================================================
   ======================== ENUM ===============================
   ============================================================ */

private enum class HomeTab { JOBS, LEARN }

/* ============================================================
   ====================== JOBS LIST TAB =======================
   ============================================================ */

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun JobsListTab(
    jobsState             : JobsState,
    isRefreshing          : Boolean,
    onRefresh             : () -> Unit,
    onJobClick            : (String) -> Unit,
    onSaveJob             : (String) -> Unit,
    onApplyJob            : (String) -> Unit,
    onNavigateToSkillInput: () -> Unit,
    modifier              : Modifier = Modifier
) {
    val pullState = rememberPullToRefreshState()

    if (pullState.isRefreshing) {
        LaunchedEffect(Unit) { onRefresh() }
    }

    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) pullState.endRefresh()
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .nestedScroll(pullState.nestedScrollConnection)
    ) {
        when (jobsState) {

            is JobsState.Loading -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            is JobsState.Empty -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier            = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Work,
                            contentDescription = null,
                            modifier           = Modifier.size(56.dp),
                            tint               = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            text       = "No matches yet",
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            text  = "Add your skills so we can find the best micro-career opportunities for you.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Button(onClick = onNavigateToSkillInput) {
                            Icon(Icons.Default.Star, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.add_your_skills))
                        }
                    }
                }
            }

            is JobsState.Error -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier            = Modifier.padding(horizontal = 32.dp)
                    ) {
                        Icon(
                            imageVector        = Icons.Default.Warning,
                            contentDescription = null,
                            modifier           = Modifier.size(56.dp),
                            tint               = MaterialTheme.colorScheme.error
                        )
                        Text(
                            text  = jobsState.message,
                            color = MaterialTheme.colorScheme.error,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Button(onClick = onRefresh) {
                            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(Modifier.width(8.dp))
                            Text(stringResource(R.string.try_again))
                        }
                    }
                }
            }

            is JobsState.Success -> {
                LazyColumn(
                    modifier        = Modifier.fillMaxSize(),
                    contentPadding  = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(
                        items = jobsState.jobs,
                        key   = { it.id }
                    ) { job ->
                        JobCard(
                            job     = job,
                            onClick = { onJobClick(job.id) },   // ← passes real DB id
                            onSave  = { onSaveJob(job.id) },
                            onApply = { onApplyJob(job.id) }
                        )
                    }
                }
            }
        }

        PullToRefreshContainer(
            state    = pullState,
            modifier = Modifier.align(Alignment.TopCenter)
        )
    }
}

/* ============================================================
   ======================== JOB CARD ===========================
   ============================================================ */

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobCard(
    job     : JobUiModel,
    onClick : () -> Unit,
    onSave  : () -> Unit,
    onApply : () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedCard(
        onClick  = onClick,
        modifier = modifier.fillMaxWidth()
    ) {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            // ── Title + bookmark ──────────────────────────────
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top,
                modifier              = Modifier.fillMaxWidth()
            ) {
                Text(
                    text       = job.title,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis,
                    modifier   = Modifier.weight(1f)
                )
                IconButton(
                    onClick  = onSave,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        imageVector        = if (job.isSaved) Icons.Default.Bookmark
                        else Icons.Outlined.Bookmark,
                        contentDescription = if (job.isSaved) "Remove saved job" else "Save job",
                        tint               = if (job.isSaved) MaterialTheme.colorScheme.primary
                        else LocalContentColor.current
                    )
                }
            }

            // ── Company ───────────────────────────────────────
            Text(
                text  = job.company,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.primary
            )

            // ── Info chips: location, category, salary ────────
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement   = Arrangement.spacedBy(4.dp)
            ) {
                if (job.location.isNotBlank()) {
                    AssistChip(
                        onClick     = {},
                        label       = { Text(job.location, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(14.dp)) }
                    )
                }
                if (job.category.isNotBlank()) {
                    AssistChip(
                        onClick     = {},
                        label       = { Text(job.category, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Icon(Icons.Default.Category, contentDescription = null, modifier = Modifier.size(14.dp)) }
                    )
                }
                if (job.salary.isNotBlank()) {
                    AssistChip(
                        onClick     = {},
                        label       = { Text(job.salary, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Icon(Icons.Default.Payments, contentDescription = null, modifier = Modifier.size(14.dp)) }
                    )
                }
            }

            // ── Description ───────────────────────────────────
            Text(
                text     = job.description,
                style    = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis
            )

            // ── Match score + Apply button ────────────────────
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically,
                modifier              = Modifier.fillMaxWidth()
            ) {
                if (job.matchScore > 0f) {
                    Text(
                        text  = "Match: ${(job.matchScore * 100).toInt()}%",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.tertiary
                    )
                }
                Button(
                    onClick  = onApply,
                    enabled  = !job.isApplying && !job.isApplied,
                    modifier = Modifier.defaultMinSize(minWidth = 90.dp)
                ) {
                    if (job.isApplying) {
                        CircularProgressIndicator(
                            strokeWidth = 2.dp,
                            modifier    = Modifier.size(16.dp),
                            color       = MaterialTheme.colorScheme.onPrimary
                        )
                    } else {
                        Text(if (job.isApplied) "Applied" else "Apply")
                    }
                }
            }
        }
    }

}