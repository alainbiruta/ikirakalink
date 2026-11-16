package com.microcareer.match.presentation.skill

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.ui.res.stringResource
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInVertically
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.ArrowForward
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.RadioButtonUnchecked
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.microcareer.match.R

/* ============================================================
   =================== SKILL INPUT SCREEN =====================

   Quick-add skills entry point, reached from bottom nav.
   Acts as the BRIDGE into CvBuilderScreen — the CvProgressCard
   shows the user what's complete across all 4 CV sections and
   deep-links them directly to the section that needs work.

   KEY ARCHITECTURAL NOTE:
   `viewModel` is passed in (not created here via hiltViewModel).
   AppNavigation scopes SkillViewModel to the MainGraph entry
   so this screen and CvBuilderScreen share the SAME instance.
   Skills added here are immediately visible in CvBuilderScreen
   without any reload.

   SIGNATURE CHANGE vs previous version:
   + onNavigateToCvBuilder(startTab: Int) — navigates to
     CvBuilderScreen and opens the specified tab directly.
   ============================================================ */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SkillInputScreen(
    viewModel: SkillViewModel,
    onBack: () -> Unit,
    onNavigateToCvBuilder: (startTab: Int) -> Unit   // NEW — bridge to CV builder
) {
    val uiState: SkillUiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    // Compute progress on every state change — pure, no side effects
    val cvProgress = remember(uiState) { CvProgressCalculator.compute(uiState) }

    // One-shot: consume flag first, then navigate
    LaunchedEffect(uiState.saveSuccess) {
        if (uiState.saveSuccess) {
            viewModel.onSaveSuccessConsumed()
            onBack()
        }
    }

    // Surface repo errors as Snackbar
    LaunchedEffect(uiState.error) {
        val msg = uiState.error ?: return@LaunchedEffect
        snackbarHostState.showSnackbar(message = msg, duration = SnackbarDuration.Long)
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.add_skills)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back)
                        )
                    }
                }
            )
        }
    ) { paddingValues ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            // ── Skill input row ────────────────────────────────
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = uiState.input,
                    onValueChange = viewModel::onInputChange,
                    label = { Text(stringResource(R.string.enter_a_skill)) },
                    placeholder = { Text(stringResource(R.string.e_g_kotlin_leadership)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = uiState.inputError != null,
                    supportingText = uiState.inputError?.let { err ->
                        { Text(err, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { viewModel.addSkill() })
                )
                Button(
                    onClick = viewModel::addSkill,
                    enabled = uiState.input.isNotBlank(),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_skill))
                }
            }

            // ── Skill chips ────────────────────────────────────
            if (uiState.skills.isNotEmpty()) {
                Text(
                    text = "${uiState.skills.size} skill${if (uiState.skills.size == 1) "" else "s"} added",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    uiState.skills.forEach { skill ->
                        InputChip(
                            selected = false,
                            onClick = {},
                            label = { Text(skill) },
                            trailingIcon = {
                                IconButton(
                                    onClick = { viewModel.removeSkill(skill) },
                                    modifier = Modifier.size(18.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription = stringResource(R.string.remove),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }

            // ── CV Progress Card (bridge) ──────────────────────
            // Appears as soon as the screen loads. Each section
            // row is individually tappable and deep-links to the
            // correct tab in CvBuilderScreen.
            AnimatedVisibility(
                visible = !uiState.isLoading,
                enter = fadeIn() + slideInVertically(initialOffsetY = { it / 2 })
            ) {
                CvProgressCard(
                    progress = cvProgress,
                    onSectionClick = { tabIndex ->
                        onNavigateToCvBuilder(tabIndex)
                    },
                    onBuildCvClick = {
                        // Navigate to the first incomplete section for best UX
                        val firstIncomplete = cvProgress.sections
                            .firstOrNull { !it.isComplete }
                            ?.tabIndex ?: 0
                        onNavigateToCvBuilder(firstIncomplete)
                    }
                )
            }

            // ── Save button ────────────────────────────────────
            Button(
                onClick = viewModel::saveSkills,
                enabled = uiState.skills.isNotEmpty() && !uiState.isSaving,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (uiState.isSaving) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(18.dp),
                        strokeWidth = 2.dp,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                } else {
                    Text(stringResource(R.string.save_all_skills))
                }
            }

            // Bottom padding so content isn't hidden by nav bar
            Spacer(Modifier.height(16.dp))
        }
    }
}

/* ============================================================
   ================== CV PROGRESS CARD ========================

   Shows the user exactly how complete their CV is across all
   4 sections. Each row is tappable and opens CvBuilderScreen
   at the relevant tab. This is the professional bridge between
   the quick-add skill flow and the full CV builder.
   ============================================================ */

@Composable
private fun CvProgressCard(
    progress: CvProgress,
    onSectionClick: (tabIndex: Int) -> Unit,
    onBuildCvClick: () -> Unit
) {
    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            // ── Header ────────────────────────────────────────
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "CV Completeness",
                        style = MaterialTheme.typography.titleSmall,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "${progress.completedSections} of ${progress.totalSections} sections complete",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                // Percentage badge
                Surface(
                    shape = MaterialTheme.shapes.small,
                    color = if (progress.fraction == 1f)
                        MaterialTheme.colorScheme.primaryContainer
                    else
                        MaterialTheme.colorScheme.secondaryContainer
                ) {
                    Text(
                        text = "${(progress.fraction * 100).toInt()}%",
                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 4.dp),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = if (progress.fraction == 1f)
                            MaterialTheme.colorScheme.onPrimaryContainer
                        else
                            MaterialTheme.colorScheme.onSecondaryContainer
                    )
                }
            }

            // ── Progress bar ──────────────────────────────────
            LinearProgressIndicator(
                progress = { progress.fraction },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(6.dp),
                trackColor = MaterialTheme.colorScheme.surfaceVariant
            )

            HorizontalDivider()

            // ── Section rows ──────────────────────────────────
            progress.sections.forEach { section ->
                CvSectionRow(
                    section = section,
                    onClick = { onSectionClick(section.tabIndex) }
                )
            }

            HorizontalDivider()

            // ── CTA ───────────────────────────────────────────
            Button(
                onClick = onBuildCvClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (progress.fraction == 1f) "View & Share CV"
                           else "Complete Your CV"
                )
                Spacer(Modifier.width(8.dp))
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(18.dp)
                )
            }
        }
    }
}

@Composable
private fun CvSectionRow(
    section: CvSectionProgress,
    onClick: () -> Unit
) {
    // Each row navigates to that specific tab in CvBuilderScreen
    TextButton(
        onClick = onClick,
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Icon(
                imageVector = if (section.isComplete)
                    Icons.Default.CheckCircle
                else
                    Icons.Default.RadioButtonUnchecked,
                contentDescription = null,
                modifier = Modifier.size(20.dp),
                tint = if (section.isComplete)
                    MaterialTheme.colorScheme.primary
                else
                    MaterialTheme.colorScheme.onSurfaceVariant
            )
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = section.label,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = if (section.isComplete) FontWeight.Normal else FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = section.detail,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
            Icon(
                imageVector = Icons.AutoMirrored.Filled.ArrowForward,
                contentDescription = stringResource(R.string.fill_in_name_and_section),
                modifier = Modifier.size(16.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}
