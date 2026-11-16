package com.microcareer.match.presentation.employee

import androidx.compose.foundation.layout.*
import androidx.compose.ui.res.stringResource
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.Bookmark
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.microcareer.match.R
import com.microcareer.match.data.local.entity.JobEntity
import com.microcareer.match.presentation.home.HomeViewModel


// ─────────────────────────────────────────────────────────
// JOB DETAIL SCREEN
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun JobDetailScreen(
    jobId     : String,
    viewModel : HomeViewModel,
    onBack    : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    val job: JobEntity? = remember(uiState.jobs, uiState.recommendations, jobId) {
        uiState.jobs.find { it.id == jobId }
            ?: uiState.recommendations.find { it.id == jobId }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text       = job?.title ?: stringResource(R.string.job_not_found),
                        maxLines   = 1,
                        style      = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.SemiBold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector        = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                },
                actions = {
                    if (job != null) {
                        IconButton(onClick = { viewModel.toggleSaveJob(job.id, job.isSaved) }) {
                            Icon(
                                imageVector        = if (job.isSaved) Icons.Default.Bookmark else Icons.Outlined.Bookmark,
                                contentDescription = if (job.isSaved) stringResource(R.string.remove_from_saved) else stringResource(R.string.saved),
                                tint               = if (job.isSaved) MaterialTheme.colorScheme.primary
                                else LocalContentColor.current
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->

        if (job == null) {
            Box(
                modifier         = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Icon(
                        imageVector        = Icons.Default.SearchOff,
                        contentDescription = null,
                        modifier           = Modifier.size(56.dp),
                        tint               = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(stringResource(R.string.job_not_found), style = MaterialTheme.typography.titleMedium)
                    OutlinedButton(onClick = onBack) { Text(stringResource(R.string.go_back)) }
                }
            }
            return@Scaffold
        }

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── Header ────────────────────────────────────────
            Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                Text(
                    text       = job.title,
                    style      = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold
                )
                if (!job.employerName.isNullOrBlank()) {
                    Row(
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Icon(
                            Icons.Default.Business, null,
                            modifier = Modifier.size(16.dp),
                            tint     = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text       = job.employerName,
                            style      = MaterialTheme.typography.bodyMedium,
                            color      = MaterialTheme.colorScheme.primary,
                            fontWeight = FontWeight.Medium
                        )
                    }
                }
            }

            // ── Match score badge ─────────────────────────────
            if (job.matchScore > 0f) {
                val matchPct = (job.matchScore * 100).toInt()
                val matchColor = when {
                    matchPct >= 80 -> MaterialTheme.colorScheme.tertiary
                    matchPct >= 50 -> MaterialTheme.colorScheme.secondary
                    else           -> MaterialTheme.colorScheme.outline
                }
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = matchColor.copy(alpha = 0.12f)
                ) {
                    Row(
                        modifier              = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Icon(Icons.Default.Analytics, null,
                            modifier = Modifier.size(16.dp), tint = matchColor)
                        Text(
                            text       = "$matchPct% Matching",
                            style      = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color      = matchColor
                        )
                    }
                }
            }

            // ── Quick info chips ──────────────────────────────
            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement   = Arrangement.spacedBy(8.dp)
            ) {
                if (job.location.isNotBlank()) {
                    InfoChip(icon = Icons.Default.LocationOn, label = job.location)
                }
                if (job.category.isNotBlank()) {
                    InfoChip(icon = Icons.Default.Category, label = job.category)
                }
                InfoChip(
                    icon  = Icons.Default.Payments,
                    label = "${job.payment.toInt()} ${job.currency}"
                )
                InfoChip(
                    icon  = Icons.Default.Schedule,
                    label = "${job.duration} ${if (job.duration == 1L) "day" else "days"}"
                )
            }

            HorizontalDivider()

            // ── Description ───────────────────────────────────
            SectionHeader(stringResource(R.string.about_this_job))
            Text(
                text  = job.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // ── Required Skills ───────────────────────────────
            if (job.requiredSkills.isNotEmpty()) {
                HorizontalDivider()
                SectionHeader("Required Skills")
                Text(
                    text  = "You need these skills to qualify for this job.",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(Modifier.height(4.dp))
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp)
                ) {
                    job.requiredSkills.forEach { skill ->
                        AssistChip(
                            onClick = {},
                            label   = {
                                Text(skill, style = MaterialTheme.typography.bodySmall)
                            },
                            leadingIcon = {
                                Icon(
                                    Icons.Default.CheckCircle, null,
                                    modifier = Modifier.size(16.dp),
                                    tint     = MaterialTheme.colorScheme.primary
                                )
                            }
                        )
                    }
                }
            }

            HorizontalDivider()

            // ── Apply button ──────────────────────────────────
            if (job.isApplied) {
                FilledTonalButton(
                    onClick  = {},
                    enabled  = false,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CheckCircle, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.you_already_applied), fontWeight = FontWeight.SemiBold)
                }
            } else {
                Button(
                    onClick  = { viewModel.applyForJob(job.id) },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, null, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.apply_now), fontWeight = FontWeight.SemiBold)
                }
            }

            Spacer(Modifier.height(16.dp))
        }
    }
}

// ─────────────────────────────────────────────────────────
// HELPERS
// ─────────────────────────────────────────────────────────

@Composable
private fun SectionHeader(text: String) {
    Text(
        text       = text,
        style      = MaterialTheme.typography.titleSmall,
        fontWeight = FontWeight.Bold
    )
}

@Composable
private fun InfoChip(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    AssistChip(
        onClick     = {},
        label       = { Text(label, style = MaterialTheme.typography.labelMedium) },
        leadingIcon = { Icon(icon, null, modifier = Modifier.size(14.dp)) }
    )
}