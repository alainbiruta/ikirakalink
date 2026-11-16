package com.microcareer.match.presentation.job

import androidx.activity.compose.BackHandler
import androidx.compose.ui.res.stringResource
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.microcareer.match.R



/* ============================================================
   ===================== UI STATE =============================
   All fields mapped to actual JobEntity fields:
     company      ← JobEntity.employerName
     salary       ← formatted from JobEntity.payment + currency + duration
     jobType      ← JobEntity.category
     postedDate   ← formatted from JobEntity.createdAt
     requirements ← JobEntity.requiredSkills
   ============================================================ */

@Immutable
data class JobDetailsUiState(
    val isLoading    : Boolean       = false,
    val jobTitle     : String        = "",
    val company      : String        = "",   // employerName
    val location     : String        = "",
    val salary       : String        = "",   // formatted: "$50 / 2 hours"
    val jobType      : String        = "",   // category
    val isRemote     : Boolean       = false,
    val postedDate   : String        = "",   // "Posted 3d ago"
    val description  : String        = "",
    val requirements : List<String>  = emptyList(), // requiredSkills
    val isSaved      : Boolean       = false,
    val isApplying   : Boolean       = false,
    val error        : String?       = null,

) {
    val hasContent: Boolean get() = jobTitle.isNotBlank()
}

/* ============================================================
   =================== SCREEN ENTRY POINT ====================
   ============================================================ */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobDetailsScreen(
    uiState          : JobDetailsUiState,
    onBack           : () -> Unit,
    onRetry          : () -> Unit,
    onApply          : () -> Unit,
    onToggleSave     : () -> Unit,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    modifier         : Modifier          = Modifier
) {
    BackHandler(enabled = true, onBack = onBack)

    Scaffold(
        modifier     = modifier,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    AnimatedVisibility(
                        visible = uiState.hasContent,
                        enter   = fadeIn(),
                        exit    = fadeOut()
                    ) {
                        Text(
                            text     = uiState.jobTitle,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                },
                navigationIcon = {
                    val navBack = stringResource(R.string.navigate_back)
                    IconButton(
                        onClick  = onBack,
                        modifier = Modifier.semantics {
                            contentDescription = navBack
                        }
                    ) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    AnimatedVisibility(visible = uiState.hasContent) {
                        IconButton(onClick = onToggleSave) {
                            Icon(
                                imageVector        = if (uiState.isSaved) Icons.Default.Bookmark
                                                     else Icons.Default.BookmarkBorder,
                                contentDescription = if (uiState.isSaved) "Unsave job"
                                                     else "Save job",
                                tint               = if (uiState.isSaved)
                                                         MaterialTheme.colorScheme.primary
                                                     else LocalContentColor.current
                            )
                        }
                    }
                }
            )
        },
        floatingActionButton = {
            if (uiState.hasContent && !uiState.isLoading && uiState.error == null) {
                ExtendedFloatingActionButton(
                    onClick = { if (!uiState.isApplying) onApply() },
                    icon    = {
                        if (uiState.isApplying) {
                            CircularProgressIndicator(
                                modifier    = Modifier.size(18.dp),
                                strokeWidth = 2.dp,
                                color       = LocalContentColor.current
                            )
                        } else {
                            Icon(
                                imageVector        = Icons.AutoMirrored.Filled.Send,
                                contentDescription = null
                            )
                        }
                    },
                    text = { Text(if (uiState.isApplying) "Applying…" else "Apply Now") }
                )
            }
        }
    ) { innerPadding ->
        when {
            uiState.isLoading   -> LoadingContent(innerPadding)
            uiState.error != null -> ErrorContent(
                error   = uiState.error,
                padding = innerPadding,
                onRetry = onRetry
            )
            !uiState.hasContent -> EmptyContent(innerPadding)
            else                -> JobContent(state = uiState, padding = innerPadding)
        }
    }
}

/* ============================================================
   ===================== LOADING STATE ========================
   ============================================================ */

@Composable
private fun LoadingContent(padding: PaddingValues) {
    Box(
        modifier         = Modifier.fillMaxSize().padding(padding),
        contentAlignment = Alignment.Center
    ) { CircularProgressIndicator() }
}

/* ============================================================
   ====================== ERROR STATE =========================
   ============================================================ */

@Composable
private fun ErrorContent(
    error   : String,
    padding : PaddingValues,
    onRetry : () -> Unit
) {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector        = Icons.Default.ErrorOutline,
            contentDescription = null,
            modifier           = Modifier.size(56.dp),
            tint               = MaterialTheme.colorScheme.error
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text      = error,
            color     = MaterialTheme.colorScheme.error,
            style     = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
        Spacer(Modifier.height(24.dp))
        Button(onClick = onRetry) {
            Icon(Icons.Default.Refresh, contentDescription = null, modifier = Modifier.size(18.dp))
            Spacer(Modifier.width(8.dp))
            Text(stringResource(R.string.try_again))
        }
    }
}

/* ============================================================
   ====================== EMPTY STATE =========================
   ============================================================ */

@Composable
private fun EmptyContent(padding: PaddingValues) {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector        = Icons.Default.SearchOff,
            contentDescription = null,
            modifier           = Modifier.size(56.dp),
            tint               = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.height(16.dp))
        Text(
            text       = "Job not found",
            style      = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.SemiBold
        )
        Spacer(Modifier.height(8.dp))
        Text(
            text      = "This listing may have been removed or is no longer available.",
            style     = MaterialTheme.typography.bodyMedium,
            color     = MaterialTheme.colorScheme.onSurfaceVariant,
            textAlign = TextAlign.Center
        )
    }
}

/* ============================================================
   ==================== JOB CONTENT ===========================
   ============================================================ */

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun JobContent(
    state   : JobDetailsUiState,
    padding : PaddingValues
) {
    Column(
        modifier            = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(padding)
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // ── Header ────────────────────────────────────────────
        Text(
            text       = state.jobTitle,
            style      = MaterialTheme.typography.headlineSmall,
            fontWeight = FontWeight.Bold
        )
        Text(
            text  = state.company,      // employerName
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.primary
        )

        // ── Quick-info chips ──────────────────────────────────
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement   = Arrangement.spacedBy(8.dp)
        ) {
            if (state.location.isNotBlank())
                InfoChip(Icons.Default.LocationOn, state.location)
            if (state.jobType.isNotBlank())
                InfoChip(Icons.Default.Category, state.jobType)   // category icon
            if (state.isRemote)
                InfoChip(Icons.Default.Wifi, "Remote")
            if (state.salary.isNotBlank())
                InfoChip(Icons.Default.Payments, state.salary)    // payment/currency/duration
        }

        if (state.postedDate.isNotBlank()) {
            Text(
                text  = state.postedDate,
                style = MaterialTheme.typography.labelMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }

        HorizontalDivider()

        // ── Description ───────────────────────────────────────
        if (state.description.isNotBlank()) {
            SectionHeader("About the Role")
            Text(state.description, style = MaterialTheme.typography.bodyLarge)
        }

        // ── Required Skills (requiredSkills from JobEntity) ───
        if (state.requirements.isNotEmpty()) {
            HorizontalDivider()
            SectionHeader("Required Skills")
            state.requirements.forEach { skill ->
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment     = Alignment.Top
                ) {
                    Icon(
                        imageVector        = Icons.Default.CheckCircleOutline,
                        contentDescription = null,
                        modifier           = Modifier.size(18.dp).padding(top = 3.dp),
                        tint               = MaterialTheme.colorScheme.primary
                    )
                    Text(skill, style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        // FAB clearance
        Spacer(Modifier.height(88.dp))
    }
}

/* ============================================================
   =================== SHARED COMPONENTS ======================
   ============================================================ */

@Composable
private fun SectionHeader(title: String) {
    Text(
        text       = title,
        style      = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color      = MaterialTheme.colorScheme.onSurface
    )
}

@Composable
private fun InfoChip(icon: ImageVector, label: String) {
    AssistChip(
        onClick     = {},
        label       = { Text(label, style = MaterialTheme.typography.labelMedium) },
        leadingIcon = {
            Icon(icon, contentDescription = null, modifier = Modifier.size(16.dp))
        }
    )
}
