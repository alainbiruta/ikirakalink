package com.microcareer.match.presentation.employer

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.presentation.home.FcmNotificationSheetSection
import com.microcareer.match.R
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import coil.compose.AsyncImage

// ─────────────────────────────────────────────────────────
// SCREEN
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerHomeScreen(
    viewModel           : EmployerHomeViewModel = hiltViewModel(),
    onNavigateToPostJob : () -> Unit,
    onNavigateToProfile : () -> Unit,
    onEditJob           : (String) -> Unit,
    onLogout            : () -> Unit
) {
    val uiState           by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    var showMenu          by remember { mutableStateOf(false) }
    var selectedTab       by remember { mutableStateOf(EmployerTab.MY_JOBS) }

    // ── Notification bottom sheet state ───────────────────
    var showNotificationsSheet by remember { mutableStateOf(false) }

    // Derived counts for the badge and sheet
    val pendingApplicants = uiState.applicants.count { it.status == "PENDING" }
    val totalNotifications = pendingApplicants  // extend here when FCM lands (Day 19)

    // Collect one-off events
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is EmployerUiEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text(
                            stringResource(R.string.microcareer),
                            style      = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold
                        )
                        uiState.companyName?.let {
                            Text(
                                it,
                                style = MaterialTheme.typography.bodySmall,
                                color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.75f)
                            )
                        }
                    }
                },
                actions = {
                    // ── Notification bell ──────────────────────────
                    // Badge shows pending applicant count.
                    // Opens employer-specific notifications sheet.
                    Box {
                        IconButton(onClick = { showNotificationsSheet = true }) {
                            Icon(Icons.Default.Notifications, "Notifications")
                        }
                        if (totalNotifications > 0) {
                            Badge(
                                modifier = Modifier
                                    .align(Alignment.TopEnd)
                                    .padding(top = 6.dp, end = 6.dp)
                            ) {
                                Text(
                                    text  = if (totalNotifications > 9) "9+"
                                    else totalNotifications.toString(),
                                    style = MaterialTheme.typography.labelSmall
                                )
                            }
                        }
                    }

                    IconButton(onClick = onNavigateToProfile) {
                        Icon(Icons.Default.AccountCircle, "Profile")
                    }
                    IconButton(onClick = { showMenu = true }) {
                        Icon(Icons.Default.MoreVert, "More")
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
        },
        floatingActionButton = {
            if (selectedTab == EmployerTab.MY_JOBS) {
                ExtendedFloatingActionButton(
                    onClick = onNavigateToPostJob,
                    icon    = { Icon(Icons.Default.Add, null) },
                    text    = { Text(stringResource(R.string.post_job)) }
                )
            }
        },
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = selectedTab == EmployerTab.MY_JOBS,
                    onClick  = { selectedTab = EmployerTab.MY_JOBS },
                    icon     = { Icon(Icons.Default.Work, null) },
                    label    = { Text(stringResource(R.string.my_jobs)) }
                )
                NavigationBarItem(
                    selected = selectedTab == EmployerTab.APPLICANTS,
                    onClick  = { selectedTab = EmployerTab.APPLICANTS },
                    icon = {
                        // Badge on Applicants tab when there are pending reviews
                        BadgedBox(
                            badge = {
                                if (pendingApplicants > 0) {
                                    Badge {
                                        Text(
                                            if (pendingApplicants > 9) "9+"
                                            else pendingApplicants.toString()
                                        )
                                    }
                                }
                            }
                        ) {
                            Icon(Icons.Default.People, null)
                        }
                    },
                    label    = { Text(stringResource(R.string.applicants)) }
                )
                NavigationBarItem(
                    selected = selectedTab == EmployerTab.DASHBOARD,
                    onClick  = { selectedTab = EmployerTab.DASHBOARD },
                    icon     = { Icon(Icons.Default.Dashboard, null) },
                    label    = { Text(stringResource(R.string.dashboard)) }
                )
            }
        }
    ) { padding ->
        when (selectedTab) {
            EmployerTab.MY_JOBS    -> MyJobsTab(
                uiState        = uiState,
                padding        = padding,
                onRefresh      = viewModel::refresh,
                onEdit         = onEditJob,
                onDelete       = viewModel::deleteJob,
                onToggleStatus = viewModel::toggleJobStatus
            )
            EmployerTab.APPLICANTS -> ApplicantsTab(
                uiState  = uiState,
                padding  = padding,
                onAccept = viewModel::acceptApplicant,
                onReject = viewModel::rejectApplicant
            )
            EmployerTab.DASHBOARD  -> DashboardTab(
                uiState = uiState,
                padding = padding
            )


        }
    }


    // ── Employer Notifications bottom sheet ────────────────
    // Shows pending applicants, open jobs summary, and
    // a placeholder for FCM push notifications (Day 19).
    val fcmNotifications by viewModel.fcmNotifications.collectAsState()

    if (showNotificationsSheet) {
        ModalBottomSheet(
            onDismissRequest = { showNotificationsSheet = false }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 24.dp)
                    .padding(bottom = 32.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                Text(
                    stringResource(R.string.notifications),
                    style      = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold
                )

                FcmNotificationSheetSection(
                    notifications = fcmNotifications,
                    onClearAll = { viewModel.markNotificationsRead() }
                )
                // ── Pending applicants card ────────────────
                if (pendingApplicants > 0) {

                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.primaryContainer
                        )
                    ) {
                        Row(
                            modifier              = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Default.People, null,
                                tint = MaterialTheme.colorScheme.primary
                            )
                            Column {
                                Text(
                                    "$pendingApplicants applicant(s) awaiting review",
                                    style      = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    stringResource(R.string.go_to_applicants_tab_to_accept_or_decline),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = androidx.compose.ui.graphics.Color.White.copy(alpha = 0.75f)
                                )

                            }
                        }
                    }

                }

                // ── Open jobs summary card ─────────────────
                val openJobs = uiState.myJobs.count { it.status == "OPEN" }
                if (openJobs > 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.secondaryContainer
                        )
                    ) {
                        Row(
                            modifier              = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Default.WorkOutline, null,
                                tint = MaterialTheme.colorScheme.secondary
                            )
                            Column {
                                Text(
                                    "$openJobs active job(s) currently open",
                                    style      = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    stringResource(R.string.receiving_applications_from_workers),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // ── Accepted applicants summary ────────────
                val acceptedApplicants = uiState.applicants.count { it.status == "ACCEPTED" }
                if (acceptedApplicants > 0) {
                    Card(
                        colors = CardDefaults.cardColors(
                            containerColor = MaterialTheme.colorScheme.tertiaryContainer
                        )
                    ) {
                        Row(
                            modifier              = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                            verticalAlignment     = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            Icon(
                                Icons.Default.CheckCircle, null,
                                tint = MaterialTheme.colorScheme.tertiary
                            )
                            Column {
                                Text(
                                    "$acceptedApplicants worker(s) accepted",
                                    style      = MaterialTheme.typography.bodyMedium,
                                    fontWeight = FontWeight.SemiBold
                                )
                                Text(
                                    stringResource(R.string.ready_to_start_work),
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    }
                }

                // ── Empty state ────────────────────────────
                if (totalNotifications == 0 && openJobs == 0 && acceptedApplicants == 0) {
                    Box(
                        modifier         = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 32.dp),
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
                            Text(
                                stringResource(R.string.post_a_job_to_start),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
// MY JOBS TAB
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun MyJobsTab(
    uiState        : EmployerUiState,
    padding        : PaddingValues,
    onRefresh      : () -> Unit,
    onEdit         : (String) -> Unit,
    onDelete       : (String) -> Unit,
    onToggleStatus : (String) -> Unit
) {
    val pullState = rememberPullToRefreshState()
    if (pullState.isRefreshing) LaunchedEffect(Unit) { onRefresh() }
    LaunchedEffect(uiState.isLoading) { if (!uiState.isLoading) pullState.endRefresh() }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .nestedScroll(pullState.nestedScrollConnection)
    ) {
        if (uiState.isLoading && uiState.myJobs.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        } else if (uiState.myJobs.isEmpty()) {
            EmptyJobsPlaceholder()
        } else {
            LazyColumn(
                contentPadding      = PaddingValues(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(uiState.myJobs, key = { it.id }) { job ->
                    EmployerJobCard(
                        job            = job,
                        onEdit         = { onEdit(job.id) },
                        onDelete       = { onDelete(job.id) },
                        onToggleStatus = { onToggleStatus(job.id) }
                    )
                }
                item { Spacer(Modifier.height(80.dp)) } // FAB clearance
            }
        }

        PullToRefreshContainer(state = pullState, modifier = Modifier.align(Alignment.TopCenter))
    }
}

@Composable
private fun EmptyJobsPlaceholder() {
    Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp),
            modifier            = Modifier.padding(32.dp)
        ) {
            Icon(
                Icons.Default.WorkOutline, null,
                modifier = Modifier.size(64.dp),
                tint     = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Text(
                stringResource(R.string.no_jobs_posted_yet),
                style      = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold
            )
            Text(
                stringResource(R.string.tap_to_post_first_job),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
// EMPLOYER JOB CARD
// ─────────────────────────────────────────────────────────

@Composable
private fun EmployerJobCard(
    job            : JobEntity,
    onEdit         : () -> Unit,
    onDelete       : () -> Unit,
    onToggleStatus : () -> Unit
) {
    var showDeleteDialog by remember { mutableStateOf(false) }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            title            = { Text(stringResource(R.string.delete_job)) },
            text             = { Text("Delete \"${job.title}\"? This cannot be undone.") },
            confirmButton    = {
                TextButton(onClick = { showDeleteDialog = false; onDelete() }) {
                    Text(stringResource(R.string.delete), color = MaterialTheme.colorScheme.error)
                }
            },
            dismissButton    = {
                TextButton(onClick = { showDeleteDialog = false }) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Title + status badge
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment     = Alignment.Top
            ) {
                Text(
                    text       = job.title,
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    modifier   = Modifier.weight(1f),
                    maxLines   = 1,
                    overflow   = TextOverflow.Ellipsis
                )
                StatusBadge(status = job.status)
            }

            // Location + payment
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AssistChip(
                    onClick     = {},
                    label       = {
                        Text(job.location, style = MaterialTheme.typography.labelSmall)
                    },
                    leadingIcon = { Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp)) }
                )
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
            }

            // Description snippet
            Text(
                text     = job.description,
                style    = MaterialTheme.typography.bodySmall,
                maxLines = 2,
                overflow = TextOverflow.Ellipsis,
                color    = MaterialTheme.colorScheme.onSurfaceVariant
            )

            HorizontalDivider()

            // Actions
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedButton(
                    onClick  = onToggleStatus,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(
                        if (job.status == "OPEN") Icons.Default.PauseCircle
                        else Icons.Default.PlayCircle,
                        null, Modifier.size(16.dp)
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        if (job.status == "OPEN") "Close" else "Reopen",
                        style = MaterialTheme.typography.labelMedium
                    )
                }
                OutlinedButton(onClick = onEdit, modifier = Modifier.weight(1f)) {
                    Icon(Icons.Default.Edit, null, Modifier.size(16.dp))
                    Spacer(Modifier.width(4.dp))
                    Text(stringResource(R.string.edit), style = MaterialTheme.typography.labelMedium)
                }
                IconButton(onClick = { showDeleteDialog = true }) {
                    Icon(
                        Icons.Default.DeleteOutline, "Delete",
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }
}

@Composable
private fun StatusBadge(status: String) {
    val (color, label) = when (status) {
        "OPEN"        -> MaterialTheme.colorScheme.tertiary to "Open"
        "CLOSED"      -> MaterialTheme.colorScheme.outline to "Closed"
        "IN_PROGRESS" -> MaterialTheme.colorScheme.secondary to "Active"
        "COMPLETED"   -> MaterialTheme.colorScheme.primary to "Done"
        else          -> MaterialTheme.colorScheme.outline to status
    }
    Surface(
        color = color.copy(alpha = 0.15f),
        shape = MaterialTheme.shapes.small
    ) {
        Text(
            text     = label,
            color    = color,
            style    = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
        )
    }
}

// ─────────────────────────────────────────────────────────
// APPLICANTS TAB
// ─────────────────────────────────────────────────────────

@Composable
private fun ApplicantsTab(
    uiState  : EmployerUiState,
    padding  : PaddingValues,
    onAccept : (applicantId: String, jobId: String) -> Unit,
    onReject : (applicantId: String, jobId: String) -> Unit
) {
    if (uiState.applicants.isEmpty()) {
        Box(
            Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.PeopleOutline, null,
                    modifier = Modifier.size(56.dp),
                    tint     = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Text(stringResource(R.string.no_applicants_yet), style = MaterialTheme.typography.titleMedium)
                Text(
                    stringResource(R.string.applications_will_appear_here),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        return
    }

    LazyColumn(
        modifier            = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(uiState.applicants, key = { it.applicantId + it.jobId }) { applicant ->
            ApplicantCard(
                applicant = applicant,
                onAccept  = { onAccept(applicant.applicantId, applicant.jobId) },
                onReject  = { onReject(applicant.applicantId, applicant.jobId) }
            )
        }
    }
}

@Composable
private fun ApplicantCard(
    applicant : ApplicantUiModel,
    onAccept  : () -> Unit,
    onReject  : () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // ── Avatar + name + match score ───────────────
            Row(
                modifier              = Modifier.fillMaxWidth(),
                verticalAlignment     = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Profile picture or initials fallback
                if (applicant.profilePictureUrl != null) {
                    AsyncImage(
                        model              = applicant.profilePictureUrl,
                        contentDescription = "Photo of ${applicant.applicantName}",
                        modifier           = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale       = ContentScale.Crop
                    )
                } else {
                    Surface(
                        modifier = Modifier.size(48.dp),
                        shape    = CircleShape,
                        color    = MaterialTheme.colorScheme.primaryContainer
                    ) {
                        Box(contentAlignment = Alignment.Center) {
                            Text(
                                text       = applicant.applicantName
                                    .split(" ")
                                    .take(2)
                                    .joinToString("") { it.first().uppercase() },
                                style      = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.Bold,
                                color      = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }
                }

                // Name + job title
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        applicant.applicantName,
                        style      = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.SemiBold
                    )
                    Text(
                        "For: ${applicant.jobTitle}",
                        style    = MaterialTheme.typography.bodySmall,
                        color    = MaterialTheme.colorScheme.onSurfaceVariant,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                // Match score badge
                if (applicant.matchScore > 0f) {
                    Surface(
                        color = MaterialTheme.colorScheme.tertiaryContainer,
                        shape = MaterialTheme.shapes.small
                    ) {
                        Text(
                            text     = "${(applicant.matchScore * 100).toInt()}% match",
                            style    = MaterialTheme.typography.labelSmall,
                            color    = MaterialTheme.colorScheme.onTertiaryContainer,
                            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                }
            }

            // ── Phone number ──────────────────────────────
            if (!applicant.phoneNumber.isNullOrBlank()) {
                Row(
                    verticalAlignment     = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Icon(
                        Icons.Default.Phone, null,
                        modifier = Modifier.size(14.dp),
                        tint     = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        applicant.phoneNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            // ── Skills ────────────────────────────────────
            if (applicant.skills.isNotEmpty()) {
                Text(
                    "Skills: ${applicant.skills.joinToString(", ")}",
                    style    = MaterialTheme.typography.bodySmall,
                    color    = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }

            // ── Accept / Reject buttons ───────────────────
            if (applicant.status == "PENDING") {
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(
                        onClick  = onReject,
                        modifier = Modifier.weight(1f),
                        colors   = ButtonDefaults.outlinedButtonColors(
                            contentColor = MaterialTheme.colorScheme.error
                        )
                    ) {
                        Icon(Icons.Default.Close, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.decline))
                    }
                    Button(onClick = onAccept, modifier = Modifier.weight(1f)) {
                        Icon(Icons.Default.Check, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.accept))
                    }
                }
            } else {
                val (color, label) = when (applicant.status) {
                    "ACCEPTED" -> MaterialTheme.colorScheme.tertiary to "✓ Accepted"
                    "REJECTED" -> MaterialTheme.colorScheme.error    to "✗ Declined"
                    else       -> MaterialTheme.colorScheme.outline  to applicant.status
                }
                Text(label, color = color, style = MaterialTheme.typography.labelMedium)
            }
        }
    }
}

// ─────────────────────────────────────────────────────────
// DASHBOARD TAB
// ─────────────────────────────────────────────────────────

@Composable
private fun DashboardTab(
    uiState : EmployerUiState,
    padding : PaddingValues
) {
    LazyColumn(
        modifier            = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding      = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Text(
                stringResource(R.string.overview),
                style      = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
        }

        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardStat(
                    modifier = Modifier.weight(1f),
                    label    = stringResource(R.string.active_jobs),
                    value    = uiState.myJobs.count { it.status == "OPEN" }.toString(),
                    icon     = Icons.Default.WorkOutline,
                    tint     = MaterialTheme.colorScheme.primary
                )
                DashboardStat(
                    modifier = Modifier.weight(1f),
                    label    = stringResource(R.string.applicants),
                    value    = uiState.applicants.size.toString(),
                    icon     = Icons.Default.People,
                    tint     = MaterialTheme.colorScheme.secondary
                )
            }
        }

        item {
            Row(
                modifier              = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                DashboardStat(
                    modifier = Modifier.weight(1f),
                    label    = stringResource(R.string.pending_review),
                    value    = uiState.applicants.count { it.status == "PENDING" }.toString(),
                    icon     = Icons.Default.HourglassEmpty,
                    tint     = MaterialTheme.colorScheme.tertiary
                )
                DashboardStat(
                    modifier = Modifier.weight(1f),
                    label    = stringResource(R.string.total_jobs),
                    value    = uiState.myJobs.size.toString(),
                    icon     = Icons.Default.BarChart,
                    tint     = MaterialTheme.colorScheme.outline
                )
            }
        }

        if (uiState.myJobs.isNotEmpty()) {
            item {
                Text(
                    stringResource(R.string.recent_jobs),
                    style      = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold
                )
            }
            items(uiState.myJobs.take(3), key = { "dash_${it.id}" }) { job ->
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier              = Modifier.padding(16.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                job.title,
                                style      = MaterialTheme.typography.titleSmall,
                                fontWeight = FontWeight.SemiBold
                            )
                            Text(
                                job.location,
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                        StatusBadge(status = job.status)
                    }
                }
            }
        }
    }
}

@Composable
private fun DashboardStat(
    modifier : Modifier,
    label    : String,
    value    : String,
    icon     : androidx.compose.ui.graphics.vector.ImageVector,
    tint     : androidx.compose.ui.graphics.Color
) {
    ElevatedCard(modifier = modifier) {
        Column(
            modifier            = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(icon, null, tint = tint, modifier = Modifier.size(28.dp))
            Text(value, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
            Text(
                label,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

// ─────────────────────────────────────────────────────────
// ENUMS & MODELS
// ─────────────────────────────────────────────────────────

enum class EmployerTab { MY_JOBS, APPLICANTS, DASHBOARD }

data class ApplicantUiModel(
    val applicantId   : String,
    val applicantName : String,
    val jobId         : String,
    val jobTitle      : String,
    val matchScore    : Float        = 0f,
    val skills        : List<String> = emptyList(),
    val status        : String       = "PENDING",
    val phoneNumber       : String?      = null,
    val profilePictureUrl : String?      = null
)