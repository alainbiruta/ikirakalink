package com.microcareer.match.presentation.employee

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
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
import androidx.hilt.navigation.compose.hiltViewModel
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.presentation.home.HomeViewModel
import com.microcareer.match.presentation.home.HomeUiEvent
import com.microcareer.match.R
import androidx.compose.foundation.shape.RoundedCornerShape
private val CATEGORIES = listOf(
    "All", "Construction", "Cleaning", "Delivery", "Agriculture",
    "Hospitality", "IT & Tech", "Teaching", "Healthcare", "Security", "Other"
)

// FIX 3: Lowered from 0.40f → 0.20f so jobs with real but moderate scores
// appear in "Best Matches" while the TF-IDF fix ramps up. Raise back to 0.40f
// once you've validated real scores in production.
private const val MIN_MATCH_SCORE = 0.20f

// ─────────────────────────────────────────────────────────
// SCREEN
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployeeHomeScreen(
    viewModel             : HomeViewModel = hiltViewModel(),
    onNavigateToJobDetail : (String) -> Unit,
    onNavigateToProfile   : () -> Unit,
    onNavigateToSkills    : () -> Unit,
    onNavigateToLearn     : () -> Unit,
    onNavigateToSaved     : () -> Unit,
    onLogout              : () -> Unit
) {
    val uiState               by viewModel.uiState.collectAsState()
    val fcmNotifications      by viewModel.fcmNotifications.collectAsState()
    val unreadCount            = fcmNotifications.count { !it.isRead }
    val snackbarHostState      = remember { SnackbarHostState() }
    var searchActive           by rememberSaveable { mutableStateOf(false) }
    var searchQuery            by rememberSaveable { mutableStateOf("") }
    var selectedCategory       by rememberSaveable { mutableStateOf("All") }
    var showMenu               by remember { mutableStateOf(false) }
    var showNotificationsSheet by remember { mutableStateOf(false) }

    // FIX 4: Always use the recommendation score when available — don't guard
    // with freshScore > job.matchScore, which prevents score corrections downward
    // (e.g. after user removes a skill). The recommendation list is the source of
    // truth; fall back to the cached job score only when no recommendation exists.
    val recommendationScores = uiState.recommendations.associate { it.id to it.matchScore }
    val jobsWithScores = remember(uiState.jobs, recommendationScores) {
        uiState.jobs.map { job ->
            val freshScore = recommendationScores[job.id]
            // Use fresh score unconditionally when present — not only when higher
            if (freshScore != null) job.copy(matchScore = freshScore) else job
        }
    }

    // FIX 6: Wrap filteredJobs in remember so it doesn't re-run on every scroll
    // frame. Only recomputes when the underlying list or query changes.
    val displayJobs = remember(jobsWithScores, searchQuery) {
        filteredJobs(jobsWithScores, searchQuery)
    }

    val qualifiedRecommended = remember(uiState.recommendations) {
        uiState.recommendations
            .filter { it.matchScore >= MIN_MATCH_SCORE }
            .sortedByDescending { it.matchScore }
    }

    LaunchedEffect(Unit) {
        viewModel.uiEvents.collect { event ->
            when (event) {
                is HomeUiEvent.ShowSnackbar        -> snackbarHostState.showSnackbar(event.message)
                is HomeUiEvent.NavigateToJobDetail -> onNavigateToJobDetail(event.jobId)
                is HomeUiEvent.NavigateToLogin     -> onLogout()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            Column {
                TopAppBar(
                    title = {
                        Text(
                            stringResource(R.string.find_work),
                            style      = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                    },
                    actions = {
                        IconButton(onClick = { searchActive = !searchActive }) {
                            Icon(
                                if (searchActive) Icons.Default.SearchOff else Icons.Default.Search,
                                contentDescription = null
                            )
                        }
                        IconButton(onClick = onNavigateToSaved) {
                            Icon(Icons.Default.Bookmarks, contentDescription = null)
                        }
                        Box {
                            IconButton(onClick = { showNotificationsSheet = true }) {
                                Icon(Icons.Default.Notifications, contentDescription = null)
                            }
                            if (unreadCount > 0) {
                                Badge(
                                    modifier = Modifier
                                        .align(Alignment.TopEnd)
                                        .padding(top = 6.dp, end = 6.dp)
                                ) {
                                    Text(
                                        text  = if (unreadCount > 9) "9+" else unreadCount.toString(),
                                        style = MaterialTheme.typography.labelSmall
                                    )
                                }
                            }
                        }
                        IconButton(onClick = { showMenu = true }) {
                            Icon(Icons.Default.MoreVert, contentDescription = null)
                        }
                        DropdownMenu(
                            expanded         = showMenu,
                            onDismissRequest = { showMenu = false }
                        ) {
                            DropdownMenuItem(
                                text        = { Text(stringResource(R.string.log_out)) },
                                leadingIcon = { Icon(Icons.Default.ExitToApp, null) },
                                onClick     = { showMenu = false; onLogout() }
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor         = MaterialTheme.colorScheme.primary,
                        titleContentColor      = androidx.compose.ui.graphics.Color.White,
                        actionIconContentColor = androidx.compose.ui.graphics.Color.White
                    )
                )

                AnimatedVisibility(visible = uiState.isOffline) {
                    Surface(color = MaterialTheme.colorScheme.errorContainer) {
                        Row(
                            modifier              = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 6.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.WifiOff, null,
                                tint     = MaterialTheme.colorScheme.error,
                                modifier = Modifier.size(16.dp)
                            )
                            Text(
                                stringResource(R.string.offline_showing_cached_jobs),
                                style = MaterialTheme.typography.labelMedium,
                                color = MaterialTheme.colorScheme.onErrorContainer
                            )
                            if (uiState.pendingSyncCount > 0) {
                                Text(
                                    "${uiState.pendingSyncCount} ${stringResource(R.string.actions_pending_sync)}",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onErrorContainer
                                )
                            }
                        }
                    }
                }

                AnimatedVisibility(visible = searchActive) {
                    OutlinedTextField(
                        value         = searchQuery,
                        onValueChange = {
                            searchQuery = it
                            viewModel.search(it)
                        },
                        placeholder  = { Text(stringResource(R.string.search_jobs_skills_location)) },
                        leadingIcon  = { Icon(Icons.Default.Search, null) },
                        trailingIcon = if (searchQuery.isNotBlank()) {
                            {
                                IconButton(onClick = {
                                    searchQuery = ""
                                    viewModel.search("")
                                }) { Icon(Icons.Default.Clear, null) }
                            }
                        } else null,
                        singleLine = true,
                        modifier   = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    )
                }

                LazyRow(
                    contentPadding        = PaddingValues(horizontal = 12.dp, vertical = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    items(CATEGORIES) { cat ->
                        FilterChip(
                            selected = selectedCategory == cat,
                            onClick  = {
                                selectedCategory = cat
                                viewModel.setCategory(if (cat == "All") null else cat)
                            },
                            label = { Text(cat, style = MaterialTheme.typography.labelMedium) }
                        )
                    }
                }
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = true,
                    onClick  = {},
                    icon     = { Icon(Icons.Default.Work, null) },
                    label    = { Text(stringResource(R.string.jobs)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick  = onNavigateToSkills,
                    icon     = { Icon(Icons.Default.Psychology, null) },
                    label    = { Text(stringResource(R.string.skills)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick  = onNavigateToLearn,
                    icon     = { Icon(Icons.Default.MenuBook, null) },
                    label    = { Text(stringResource(R.string.learn)) }
                )
                NavigationBarItem(
                    selected = false,
                    onClick  = onNavigateToProfile,
                    icon     = { Icon(Icons.Default.Person, null) },
                    label    = { Text(stringResource(R.string.profile)) }
                )
            }
        }
    ) { padding ->
        EmployeeJobFeed(
            jobs         = displayJobs,
            recommended  = qualifiedRecommended,
            isLoading    = uiState.isLoading,
            isRefreshing = uiState.isRefreshing,
            isOffline    = uiState.isOffline,
            padding      = padding,
            onRefresh    = viewModel::refresh,
            onJobClick   = onNavigateToJobDetail,
            onSave       = { id, saved -> viewModel.toggleSaveJob(id, saved) },
            onApply      = viewModel::applyForJob
        )
    }

    if (showNotificationsSheet) {
        ModalBottomSheet(onDismissRequest = {
            showNotificationsSheet = false
            viewModel.markNotificationsRead()
        }) {
            Column(
                modifier            = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    stringResource(R.string.notifications),
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                if (fcmNotifications.isEmpty() && uiState.pendingSyncCount == 0) {
                    Box(
                        modifier         = Modifier.fillMaxWidth().padding(vertical = 32.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Icon(
                                Icons.Default.NotificationsNone, null,
                                modifier = Modifier.size(48.dp),
                                tint     = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Text(
                                stringResource(R.string.no_notifications_yet),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                } else {
                    // FIX 7: LazyColumn with keys so only changed items recompose.
                    // Also handles large notification lists without janking the sheet.
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(8.dp),
                        modifier            = Modifier.heightIn(max = 400.dp)
                    ) {
                        items(
                            items = fcmNotifications.take(10),
                            key   = { it.id }          // stable key — no full recompose on read
                        ) { notif ->
                            NotificationCard(notif)
                        }

                        // FIX 7 cont: Sync pending card as a list item so it scrolls
                        // with notifications instead of being clipped outside the lazy layout.
                        if (uiState.pendingSyncCount > 0) {
                            item(key = "sync_pending") {
                                SyncPendingCard(uiState.pendingSyncCount)
                            }
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
// NOTIFICATION CARD  (extracted to keep recomposition scope small)
// ─────────────────────────────────────────────────────────

@Composable
private fun NotificationCard(notif: com.microcareer.match.data.local.FcmNotification) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = if (!notif.isRead)
                MaterialTheme.colorScheme.primaryContainer
            else
                MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                if (notif.type == "application_accepted")
                    Icons.Default.CheckCircle
                else
                    Icons.Default.Notifications,
                contentDescription = null,
                tint = if (notif.type == "application_accepted")
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.error
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    notif.title,
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    notif.body,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun SyncPendingCard(pendingCount: Int) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.secondaryContainer
        )
    ) {
        Row(
            modifier              = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment     = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(Icons.Default.Sync, null, tint = MaterialTheme.colorScheme.secondary)
            Column {
                Text(
                    "$pendingCount ${stringResource(R.string.actions_pending_sync)}",
                    style      = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    stringResource(R.string.will_sync_automatically_when_online),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
// HELPERS
// ─────────────────────────────────────────────────────────

private fun filteredJobs(jobs: List<JobEntity>, query: String): List<JobEntity> {
    if (query.isBlank()) return jobs
    val q = query.lowercase()
    return jobs.filter {
        it.title.lowercase().contains(q)       ||
                it.description.lowercase().contains(q) ||
                it.location.lowercase().contains(q)    ||
                it.category.lowercase().contains(q)    ||
                it.requiredSkills.any { s -> s.lowercase().contains(q) }
    }
}

// ─────────────────────────────────────────────────────────
// JOB FEED
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun EmployeeJobFeed(
    jobs        : List<JobEntity>,
    recommended : List<JobEntity>,
    isLoading   : Boolean,
    isRefreshing: Boolean,
    isOffline   : Boolean,
    padding     : PaddingValues,
    onRefresh   : () -> Unit,
    onJobClick  : (String) -> Unit,
    onSave      : (String, Boolean) -> Unit,
    onApply     : (String) -> Unit
) {
    val pullState = rememberPullToRefreshState()
    val listState = rememberLazyListState()

    // FIX 1 & 5: The two-LaunchedEffect pattern had a race condition — if the ViewModel
    // set isRefreshing=false before Compose re-entered the effect, endRefresh() was
    // never called and the spinner spun forever. Also, rapid pulls could fire onRefresh()
    // twice simultaneously.
    //
    // Fix: single effect that owns the full pull-to-refresh lifecycle:
    //   1. Detect the pull gesture (pullState.isRefreshing becomes true)
    //   2. Call onRefresh() once and wait for the ViewModel to finish (isRefreshing=false)
    //   3. Only then call endRefresh()
    // The `isRefreshing` parameter is the ViewModel's flag, not the pull state's flag.
    // Effect 1: user pulled → tell the ViewModel to refresh
    LaunchedEffect(pullState.isRefreshing) {
        if (pullState.isRefreshing) onRefresh()
    }

// Effect 2: ViewModel finished → dismiss the spinner
// Keyed on isRefreshing so it re-runs the moment the flag flips false.
    LaunchedEffect(isRefreshing) {
        if (!isRefreshing) pullState.endRefresh()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .nestedScroll(pullState.nestedScrollConnection)
    ) {
        when {
            isLoading && jobs.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            jobs.isEmpty() && isOffline -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier            = Modifier.padding(32.dp)
                    ) {
                        Icon(
                            Icons.Default.WifiOff, null,
                            modifier = Modifier.size(64.dp),
                            tint     = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            stringResource(R.string.offline_showing_cached_jobs),
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            stringResource(R.string.no_jobs_found),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            jobs.isEmpty() -> {
                Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier            = Modifier.padding(32.dp)
                    ) {
                        Icon(
                            Icons.Default.SearchOff, null,
                            modifier = Modifier.size(64.dp),
                            tint     = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                        Text(
                            stringResource(R.string.no_jobs_found),
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.SemiBold
                        )
                        Text(
                            stringResource(R.string.try_a_different_category_or_add_more_ski),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }
            else -> {
                LazyColumn(
                    state               = listState,
                    contentPadding      = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    if (recommended.isNotEmpty()) {
                        item {
                            Row(
                                modifier              = Modifier.fillMaxWidth(),
                                verticalAlignment     = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Text(
                                    stringResource(R.string.best_matches),
                                    style      = MaterialTheme.typography.titleSmall,
                                    fontWeight = FontWeight.Bold,
                                    color      = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    stringResource(R.string.based_on_your_skills),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                        items(recommended.take(3), key = { "rec_${it.id}" }) { job ->
                            EmployeeJobCard(
                                job           = job,
                                isHighlighted = true,
                                onClick       = { onJobClick(job.id) },
                                onSave        = { onSave(job.id, job.isSaved) },
                                onApply       = { onApply(job.id) }
                            )
                        }
                        item { HorizontalDivider() }
                        item {
                            Text(
                                stringResource(R.string.all_jobs),
                                style      = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                    items(jobs, key = { it.id }) { job ->
                        EmployeeJobCard(
                            job     = job,
                            onClick = { onJobClick(job.id) },
                            onSave  = { onSave(job.id, job.isSaved) },
                            onApply = { onApply(job.id) }
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

// ─────────────────────────────────────────────────────────
// JOB CARD
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun EmployeeJobCard(
    job           : JobEntity,
    isHighlighted : Boolean = false,
    onClick       : () -> Unit,
    onSave        : () -> Unit,
    onApply       : () -> Unit
) {
    ElevatedCard(
        onClick  = onClick,
        modifier = Modifier
            .fillMaxWidth()
            .then(
                if (isHighlighted)
                    Modifier.border(
                        width = 0.5.dp,
                        color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f),
                        shape = CardDefaults.elevatedShape
                    )
                else Modifier
            ),
        colors = if (isHighlighted)
            CardDefaults.elevatedCardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer)
        else
            CardDefaults.elevatedCardColors()
    )
    {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        job.title,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        maxLines   = 1,
                        overflow   = TextOverflow.Ellipsis
                    )
                    if (job.employerName != null) {
                        Text(
                            job.employerName,
                            style    = MaterialTheme.typography.bodySmall,
                            color    = MaterialTheme.colorScheme.primary,
                            maxLines = 1
                        )
                    }
                }
                IconButton(onClick = onSave, modifier = Modifier.size(36.dp)) {
                    Icon(
                        if (job.isSaved) Icons.Default.Bookmark else Icons.Outlined.Bookmark,
                        contentDescription = null,
                        tint = if (job.isSaved) MaterialTheme.colorScheme.primary
                        else LocalContentColor.current
                    )
                }
            }

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement   = Arrangement.spacedBy(4.dp)
            ) {
                if (job.location.isNotBlank()) {
                    AssistChip(
                        onClick     = {},
                        label       = { Text(job.location, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp)) }
                    )
                }
                if (job.category.isNotBlank()) {
                    AssistChip(
                        onClick     = {},
                        label       = { Text(job.category, style = MaterialTheme.typography.labelSmall) },
                        leadingIcon = { Icon(Icons.Default.Category, null, Modifier.size(14.dp)) }
                    )
                }
                AssistChip(
                    onClick     = {},
                    label       = {
                        Text(
                            "${job.payment.toInt()} ${job.currency}",
                            style = MaterialTheme.typography.labelSmall
                        )
                    },
                    leadingIcon = { Icon(Icons.Default.Payments, null, Modifier.size(14.dp)) }
                )
                if (job.duration > 0) {
                    AssistChip(
                        onClick     = {},
                        label       = {
                            Text(
                                if (job.duration == 1L) "1 day" else "${job.duration} days",
                                style = MaterialTheme.typography.labelSmall
                            )
                        },
                        leadingIcon = { Icon(Icons.Default.Schedule, null, Modifier.size(14.dp)) }
                    )
                }
            }

            Text(
                job.description,
                style    = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )

            if (job.requiredSkills.isNotEmpty()) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement   = Arrangement.spacedBy(4.dp)
                ) {
                    Text(
                        stringResource(R.string.skills_label),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    job.requiredSkills.take(4).forEach { skill ->
                        SuggestionChip(
                            onClick = {},
                            label   = { Text(skill, style = MaterialTheme.typography.labelSmall) }
                        )
                    }
                    if (job.requiredSkills.size > 4) {
                        SuggestionChip(
                            onClick = {},
                            label   = {
                                Text(
                                    "+${job.requiredSkills.size - 4} more",
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        )
                    }
                }
            }

            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.CenterVertically
            ) {
                val matchPct = (job.matchScore * 100).toInt()
                if (matchPct >= (MIN_MATCH_SCORE * 100).toInt()) {
                    val (matchColor, matchBg) = when {
                        matchPct >= 80 -> Pair(
                            MaterialTheme.colorScheme.onTertiaryContainer,
                            MaterialTheme.colorScheme.tertiaryContainer
                        )
                        matchPct >= 60 -> Pair(
                            MaterialTheme.colorScheme.onSecondaryContainer,
                            MaterialTheme.colorScheme.secondaryContainer
                        )
                        matchPct >= 40 -> Pair(
                            MaterialTheme.colorScheme.onPrimaryContainer,
                            MaterialTheme.colorScheme.primaryContainer
                        )
                        else           -> Pair(
                            MaterialTheme.colorScheme.onSurfaceVariant,
                            MaterialTheme.colorScheme.surfaceVariant
                        )
                    }
                    Surface(
                        shape = RoundedCornerShape(20.dp),
                        color = matchBg
                    ) {
                        Row(
                            modifier              = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                Icons.Default.Analytics, null,
                                modifier = Modifier.size(12.dp),
                                tint     = matchColor
                            )
                            Text(
                                "$matchPct% match",
                                style      = MaterialTheme.typography.labelSmall,
                                color      = matchColor,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }
                } else {
                    Spacer(Modifier.weight(1f))
                }
                Button(
                    onClick  = onApply,
                    enabled  = !job.isApplied,
                    modifier = Modifier.defaultMinSize(minWidth = 88.dp)
                ) {
                    Text(
                        if (job.isApplied) stringResource(R.string.applied)
                        else stringResource(R.string.apply_now),
                        style = MaterialTheme.typography.labelMedium
                    )
                }
            }
        }
    }
}