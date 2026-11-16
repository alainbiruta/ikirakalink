package com.microcareer.match.presentation.learning

import android.content.Intent
import androidx.compose.material.icons.automirrored.filled.Article
import androidx.core.net.toUri
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microcareer.match.R
import com.microcareer.match.data.local.entity.LearningModuleEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LearningModuleDetailScreen(
    moduleId  : String,
    viewModel : LearningViewModel,
    onBack    : () -> Unit,
    modifier  : Modifier = Modifier
) {
    val context       = LocalContext.current
    val allState      by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarState = remember { SnackbarHostState() }

    val module = remember(allState, moduleId) {
        (allState as? LearningUiState.Success)?.modules?.find { it.id == moduleId }
    }

    // ── Local progress (slider state) ─────────────────────
    var localProgress by remember(module?.progress) {
        mutableIntStateOf(module?.progress?.coerceIn(0, 100) ?: 0)
    }

    // ── Completion dialog state ───────────────────────────
    var showDialog    by remember { mutableStateOf(false) }
    var unlockedSkills by remember { mutableStateOf<List<String>>(emptyList()) }

    // ── Collect one-shot events from ViewModel ────────────
    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is LearningEvent.SkillUnlocked -> {
                    if (event.moduleId == moduleId) {
                        unlockedSkills = event.newSkills
                        showDialog     = true
                    }
                }
            }
        }
    }

    // ── Milestone snackbars ───────────────────────────────
    var prevProgress by remember { mutableIntStateOf(localProgress) }
    LaunchedEffect(localProgress) {
        val milestone = when {
            localProgress >= 25 && prevProgress < 25 -> "🎯 25% — keep going!"
            localProgress >= 50 && prevProgress < 50 -> "🔥 Halfway there!"
            localProgress >= 75 && prevProgress < 75 -> "⭐ Almost done!"
            else -> null
        }
        milestone?.let { snackbarState.showSnackbar(it, duration = SnackbarDuration.Short) }
        prevProgress = localProgress
    }

    // ── Handlers ──────────────────────────────────────────
    val onProgressChange: (Int) -> Unit = { v ->
        localProgress = v.coerceIn(0, 100)
        viewModel.updateProgress(moduleId, localProgress)
    }

    val onMarkComplete: () -> Unit = {
        localProgress = 100
        viewModel.markAsComplete(moduleId)   // fires SkillUnlocked event
    }

    val onOpenContent: (String) -> Unit = { url ->
        runCatching {
            context.startActivity(Intent(Intent.ACTION_VIEW, url.toUri()))
        }
    }

    // ── Scaffold ──────────────────────────────────────────
    Scaffold(
        modifier     = modifier,
        snackbarHost = { SnackbarHost(snackbarState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.module_details), maxLines = 1) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, stringResource(R.string.back))
                    }
                },
                actions = {
                    module?.contentUrl?.let { url ->
                        IconButton(onClick = { onOpenContent(url) }) {
                            Icon(Icons.Default.OpenInBrowser, "Open content")
                        }
                    }
                }
            )
        }
    ) { padding ->

        if (module == null) {
            Box(Modifier.fillMaxSize().padding(padding), Alignment.Center) {
                CircularProgressIndicator()
            }
        } else {
            DetailContent(
                module          = module,
                localProgress   = localProgress,
                onProgressChange = onProgressChange,
                onStartLearning = {
                    if (localProgress == 0) onProgressChange(1)
                    module.contentUrl?.let { onOpenContent(it) }
                },
                onMarkComplete  = onMarkComplete,
                modifier        = Modifier.fillMaxSize().padding(padding)
            )
        }
    }

    // ── Skill-unlock celebration dialog ───────────────────
    if (showDialog) {
        SkillUnlockedDialog(
            moduleName     = module?.title ?: "",
            unlockedSkills = unlockedSkills,
            onContinue     = { showDialog = false },
            onBackToJobs   = { showDialog = false; onBack() }
        )
    }
}

// ─────────────────────────────────────────────────────────
// DETAIL CONTENT
// ─────────────────────────────────────────────────────────

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun DetailContent(
    module          : LearningModuleEntity,
    localProgress   : Int,
    onProgressChange: (Int) -> Unit,
    onStartLearning : () -> Unit,
    onMarkComplete  : () -> Unit,
    modifier        : Modifier = Modifier
) {
    Column(
        modifier            = modifier.verticalScroll(rememberScrollState()).padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        // ── Header ────────────────────────────────────────
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), Arrangement.spacedBy(12.dp)) {

                if (module.isCompleted) {
                    CompletedBadge()
                }

                Text(module.title,
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold)

                Text(module.skillName,
                    style = MaterialTheme.typography.titleMedium,
                    color = MaterialTheme.colorScheme.primary)

                HorizontalDivider()

                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement   = Arrangement.spacedBy(8.dp)
                ) {
                    MetaItem(Icons.Default.Schedule,     "${module.duration} min")
                    MetaItem(Icons.AutoMirrored.Filled.TrendingUp,   module.difficulty)
                    MetaItem(contentTypeIcon(module.contentType), module.contentType)
                }
            }
        }

        // ── Description ───────────────────────────────────
        Card(modifier = Modifier.fillMaxWidth()) {
            Column(Modifier.padding(20.dp), Arrangement.spacedBy(8.dp)) {
                Text(stringResource(R.string.about_this_module),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)
                Text(module.description,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }

        // ── Prerequisites ─────────────────────────────────
        if (module.prerequisites.isNotEmpty()) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), Arrangement.spacedBy(10.dp)) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                        verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Info, null, tint = MaterialTheme.colorScheme.primary)
                        Text(stringResource(R.string.prerequisites),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold)
                    }
                    module.prerequisites.forEach { pre ->
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp),
                            verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.CheckCircle, null,
                                Modifier.size(16.dp),
                                tint = MaterialTheme.colorScheme.tertiary)
                            Text(pre, style = MaterialTheme.typography.bodyMedium)
                        }
                    }
                }
            }
        }

        // ── Progress tracker ──────────────────────────────
        AnimatedVisibility(
            visible = localProgress > 0 || module.isCompleted,
            enter   = fadeIn() + expandVertically(),
            exit    = fadeOut() + shrinkVertically()
        ) {
            Card(modifier = Modifier.fillMaxWidth()) {
                Column(Modifier.padding(20.dp), Arrangement.spacedBy(16.dp)) {
                    Text(stringResource(R.string.your_progress),
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold)

                    Row(Modifier.fillMaxWidth(),
                        Arrangement.SpaceBetween, Alignment.CenterVertically) {
                        Text("$localProgress%",
                            style = MaterialTheme.typography.bodyLarge,
                            fontWeight = FontWeight.SemiBold,
                            color = MaterialTheme.colorScheme.primary)
                        if (module.isCompleted) {
                            Icon(Icons.Default.CheckCircle, null,
                                tint = MaterialTheme.colorScheme.primary)
                        }
                    }

                    LinearProgressIndicator(
                        progress = { localProgress / 100f },
                        modifier = Modifier.fillMaxWidth().height(8.dp)
                    )

                    if (!module.isCompleted && localProgress > 0) {
                        Row(Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
                            listOf(25 to "¼", 50 to "½", 75 to "¾", 100 to "✓").forEach { (m, lbl) ->
                                MilestoneChip(m, localProgress, lbl)
                            }
                        }
                    }

                    if (!module.isCompleted) {
                        Column(verticalArrangement = Arrangement.spacedBy(4.dp)) {
                            Text(stringResource(R.string.update_your_progress),
                                style = MaterialTheme.typography.bodySmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Slider(
                                value         = localProgress.toFloat(),
                                onValueChange = { onProgressChange(it.toInt()) },
                                valueRange    = 0f..100f,
                                steps         = 19,
                                modifier      = Modifier.fillMaxWidth()
                            )
                        }
                    }
                }
            }
        }

        // ── Buttons ───────────────────────────────────────
        Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
            Button(
                onClick        = onStartLearning,
                modifier       = Modifier.fillMaxWidth(),
                contentPadding = PaddingValues(16.dp)
            ) {
                Icon(when {
                    module.isCompleted -> Icons.Default.Replay
                    localProgress > 0  -> Icons.Default.PlayArrow
                    else               -> Icons.Default.School
                }, null, Modifier.size(20.dp))
                Spacer(Modifier.width(10.dp))
                Text(when {
                    module.isCompleted -> "Review Module"
                    localProgress > 0  -> "Continue Learning"
                    else               -> "Start Learning"
                }, style = MaterialTheme.typography.titleSmall)
            }

            if (!module.isCompleted && localProgress > 0) {
                OutlinedButton(
                    onClick  = onMarkComplete,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(Icons.Default.CheckCircle, null, Modifier.size(18.dp))
                    Spacer(Modifier.width(8.dp))
                    Text(stringResource(R.string.mark_as_complete))
                }
            }
        }

        Spacer(Modifier.height(16.dp))
    }
}

// ─────────────────────────────────────────────────────────
// SKILL-UNLOCK DIALOG  (replaces old CompletionDialog)
// ─────────────────────────────────────────────────────────

@Composable
private fun SkillUnlockedDialog(
    moduleName     : String,
    unlockedSkills : List<String>,
    onContinue     : () -> Unit,
    onBackToJobs   : () -> Unit
) {
    AlertDialog(
        onDismissRequest = onContinue,
        icon = {
            Icon(Icons.Default.EmojiEvents, null,
                Modifier.size(48.dp), tint = MaterialTheme.colorScheme.primary)
        },
        title = {
            Text(stringResource(R.string.congratulations),
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold)
        },
        text = {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(stringResource(R.string.you_ve_completed))
                Text(moduleName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.primary)

                if (unlockedSkills.isNotEmpty()) {
                    Spacer(Modifier.height(8.dp))
                    Surface(
                        color = MaterialTheme.colorScheme.secondaryContainer,
                        shape = MaterialTheme.shapes.medium
                    ) {
                        Column(Modifier.padding(16.dp), Arrangement.spacedBy(6.dp)) {
                            Row(horizontalArrangement = Arrangement.spacedBy(6.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Star, null,
                                    Modifier.size(16.dp),
                                    tint = MaterialTheme.colorScheme.secondary)
                                Text("Skills added to your profile:",
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                            unlockedSkills.forEach { skill ->
                                Text("• $skill",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSecondaryContainer)
                            }
                        }
                    }
                    Spacer(Modifier.height(4.dp))
                    Text(stringResource(R.string.skill_helps_match_jobs),
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        },
        confirmButton = {
            Button(onClick = onContinue) {
                Text(stringResource(R.string.continue_learning))
            }
        },
        dismissButton = {
            TextButton(onClick = onBackToJobs) {
                Text(stringResource(R.string.back_to_jobs))
            }
        }
    )
}

// ─────────────────────────────────────────────────────────
// SMALL HELPERS
// ─────────────────────────────────────────────────────────

@Composable
private fun CompletedBadge() {
    Surface(color = MaterialTheme.colorScheme.primaryContainer,
        shape = MaterialTheme.shapes.small) {
        Row(Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
            Arrangement.spacedBy(6.dp), Alignment.CenterVertically) {
            Icon(Icons.Default.CheckCircle, null, Modifier.size(18.dp),
                tint = MaterialTheme.colorScheme.primary)
            Text(stringResource(R.string.completed),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary)
        }
    }
}

@Composable
private fun MetaItem(icon: androidx.compose.ui.graphics.vector.ImageVector, label: String) {
    Row(horizontalArrangement = Arrangement.spacedBy(6.dp), verticalAlignment = Alignment.CenterVertically) {
        Icon(icon, null, Modifier.size(18.dp), tint = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(label, style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun MilestoneChip(milestone: Int, current: Int, label: String) {
    val reached = current >= milestone
    Surface(
        shape = MaterialTheme.shapes.small,
        color = if (reached) MaterialTheme.colorScheme.primaryContainer
        else         MaterialTheme.colorScheme.surfaceVariant,
        modifier = Modifier.size(36.dp)
    ) {
        Box(contentAlignment=Alignment.Center) {
            Text(if (reached) "✓" else label,
                style = MaterialTheme.typography.labelSmall,
                color = if (reached) MaterialTheme.colorScheme.primary
                else         MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

private fun contentTypeIcon(type: String) = when (type.uppercase()) {
    "VIDEO"       -> Icons.Default.PlayCircle
    "INTERACTIVE" -> Icons.Default.TouchApp
    else          -> Icons.AutoMirrored.Filled.Article
}