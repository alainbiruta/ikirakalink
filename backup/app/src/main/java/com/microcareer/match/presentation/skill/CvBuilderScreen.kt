    package com.microcareer.match.presentation.skill

    import android.content.Intent
    import androidx.compose.ui.res.stringResource
    import androidx.compose.animation.AnimatedContent
    import androidx.compose.animation.fadeIn
    import androidx.compose.animation.fadeOut
    import androidx.compose.animation.togetherWith
    import androidx.compose.foundation.layout.*
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.rememberScrollState
    import androidx.compose.foundation.text.KeyboardActions
    import androidx.compose.foundation.text.KeyboardOptions
    import androidx.compose.foundation.text.selection.SelectionContainer
    import androidx.compose.foundation.verticalScroll
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.automirrored.filled.ArrowBack
    import androidx.compose.material.icons.filled.*
    import androidx.compose.material3.*
    import androidx.compose.runtime.*
    import androidx.compose.runtime.saveable.rememberSaveable
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.focus.FocusDirection
    import androidx.compose.ui.platform.LocalContext
    import androidx.compose.ui.platform.LocalFocusManager
    import androidx.compose.ui.text.font.FontFamily
    import androidx.compose.ui.text.font.FontWeight
    import androidx.compose.ui.text.input.ImeAction
    import androidx.compose.ui.text.input.KeyboardCapitalization
    import androidx.compose.ui.text.input.KeyboardType
    import androidx.compose.ui.text.style.TextOverflow
    import androidx.compose.ui.window.Dialog
    import androidx.compose.ui.window.DialogProperties
    import androidx.compose.ui.unit.dp
    import androidx.lifecycle.compose.collectAsStateWithLifecycle
    import com.microcareer.match.R

    /* ============================================================
       =================== CV BUILDER SCREEN =====================

       Full 4-tab CV editor: Profile | Skills | Experience | Education
       FAB opens a CV preview → share via system share sheet.

       KEY ARCHITECTURAL NOTE:
       `viewModel` is passed in (not created here via hiltViewModel).
       AppNavigation scopes SkillViewModel to the MainGraph entry so
       CvBuilderScreen and SkillInputScreen share the SAME instance.
       Skills added in SkillInputScreen appear here immediately.

       SIGNATURE:
       startTab: Int  — which tab to open on entry (default 0).
                        SkillInputScreen passes the index of the
                        first incomplete section for best UX,
                        or the index of the section the user tapped.
       ============================================================ */

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
    @Composable
    fun CvBuilderScreen(
        viewModel: SkillViewModel,
        onBack: () -> Unit,
        startTab: Int = 0      // deep-link target from SkillInputScreen
    ) {
        val uiState: SkillUiState by viewModel.uiState.collectAsStateWithLifecycle()
        val context = LocalContext.current
        val snackbarHostState = remember { SnackbarHostState() }

        // startTab is used once on first composition only — rememberSaveable
        // then owns the selected tab so rotation/process death doesn't reset it
        var selectedTab by rememberSaveable { mutableIntStateOf(startTab) }

        var showAddExperienceDialog by remember { mutableStateOf(false) }
        var showAddEducationDialog by remember { mutableStateOf(false) }
        var showCvPreview by remember { mutableStateOf(false) }

        // Compute progress on every state change
        val cvProgress = remember(uiState) { CvProgressCalculator.compute(uiState) }

        // One-shot: share CV text via system share sheet then consume the flag
        LaunchedEffect(uiState.generatedCvText) {
            val cvText = uiState.generatedCvText ?: return@LaunchedEffect
            val intent = Intent(Intent.ACTION_SEND).apply {
                type = "text/plain"
                putExtra(Intent.EXTRA_TEXT, cvText)
                putExtra(
                    Intent.EXTRA_SUBJECT,
                    "${uiState.personalInfo.fullName.ifBlank { "My" }} CV"
                )
            }
            context.startActivity(Intent.createChooser(intent, "Share CV via"))
            viewModel.onCvTextConsumed()
        }

        // One-shot: surface repo errors as Snackbar
        LaunchedEffect(uiState.error) {
            val msg = uiState.error ?: return@LaunchedEffect
            snackbarHostState.showSnackbar(message = msg, duration = SnackbarDuration.Long)
        }
        LaunchedEffect(uiState.pdfExportMessage) {
            val msg = uiState.pdfExportMessage ?: return@LaunchedEffect
            snackbarHostState.showSnackbar(message = msg, duration = SnackbarDuration.Long)
            viewModel.onPdfExportMessageConsumed()
        }

        val tabs = listOf("Profile", "Skills", "Experience", "Education")

        Scaffold(
            snackbarHost = { SnackbarHost(snackbarHostState) },
            topBar = {
                Column {
                    TopAppBar(
                        title = {
                            Column {
                                Text(stringResource(R.string.cv_builder))
                                // Subtle progress hint in the subtitle
                                Text(
                                    text = "${cvProgress.completedSections}/${cvProgress.totalSections} sections complete",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        },
                        navigationIcon = {
                            IconButton(onClick = onBack) {
                                Icon(
                                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                                    contentDescription = stringResource(R.string.back)
                                )
                            }
                        },
                        actions = {
                            IconButton(onClick = { showCvPreview = true }) {
                                Icon(
                                    imageVector = Icons.Default.Visibility,
                                    contentDescription = stringResource(R.string.preview_cv)
                                )
                            }
                        }
                    )
                    // Thin linear progress bar across the full width under the TopAppBar
                    LinearProgressIndicator(
                        progress = { cvProgress.fraction },
                        modifier = Modifier.fillMaxWidth(),
                        trackColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                    ScrollableTabRow(
                        selectedTabIndex = selectedTab,
                        edgePadding = 0.dp
                    ) {
                        tabs.forEachIndexed { index, label ->
                            val sectionProgress = cvProgress.sections.getOrNull(index)
                            Tab(
                                selected = selectedTab == index,
                                onClick = { selectedTab = index },
                                text = {
                                    Row(
                                        verticalAlignment = Alignment.CenterVertically,
                                        horizontalArrangement = Arrangement.spacedBy(4.dp)
                                    ) {
                                        Text(label)
                                        // Green dot on complete sections
                                        if (sectionProgress?.isComplete == true) {
                                            Icon(
                                                imageVector = Icons.Default.CheckCircle,
                                                contentDescription = null,
                                                modifier = Modifier.size(12.dp),
                                                tint = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            },
            floatingActionButton = {
                ExtendedFloatingActionButton(
                    onClick = { showCvPreview = true },
                    icon = { Icon(Icons.Default.Description, contentDescription = null) },
                    text = { Text(stringResource(R.string.generate_cv)) }
                )
            }
        ) { padding ->

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    return@Box
                }

                // AnimatedContent so switching tabs has a smooth crossfade
                AnimatedContent(
                    targetState = selectedTab,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "tabContent"
                ) { tab ->
                    when (tab) {
                        0 -> PersonalInfoTab(
                            personalInfo = uiState.personalInfo,
                            onUpdate = viewModel::updatePersonalInfo
                        )
                        1 -> SkillsTab(
                            skills = uiState.skills,
                            input = uiState.input,
                            inputError = uiState.inputError,
                            onInputChange = viewModel::onInputChange,
                            onAddSkill = viewModel::addSkill,
                            onRemoveSkill = viewModel::removeSkill
                        )
                        2 -> ExperienceTab(
                            experiences = uiState.workExperiences,
                            onAdd = { showAddExperienceDialog = true },
                            onRemove = viewModel::removeWorkExperience
                        )
                        3 -> EducationTab(
                            education = uiState.education,
                            onAdd = { showAddEducationDialog = true },
                            onRemove = viewModel::removeEducation
                        )
                    }
                }
            }
        }

        // ── Dialogs ──────────────────────────────────────────────

        if (showAddExperienceDialog) {
            AddExperienceDialog(
                onDismiss = { showAddExperienceDialog = false },
                onConfirm = { exp ->
                    viewModel.addWorkExperience(exp)
                    showAddExperienceDialog = false
                }
            )
        }

        if (showAddEducationDialog) {
            AddEducationDialog(
                onDismiss = { showAddEducationDialog = false },
                onConfirm = { edu ->
                    viewModel.addEducation(edu)
                    showAddEducationDialog = false
                }
            )
        }

        if (showCvPreview) {
            CvPreviewDialog(
                cvText = CvGenerator.generateCvText(uiState),
                onDismiss = { showCvPreview = false },
                onDownloadPdf = {
                    showCvPreview = false
                    viewModel.exportCvAsPdf(context)   // context is already in scope via LocalContext
                }
            )
        }
    }

    /* ============================================================
       ==================== TAB: PERSONAL INFO ====================
       ============================================================ */

    @Composable
    private fun PersonalInfoTab(
        personalInfo: PersonalInfoUiModel,
        onUpdate: (PersonalInfoUiModel) -> Unit
    ) {
        val focusManager = LocalFocusManager.current

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            item {
                Text(
                    stringResource(R.string.personal_details),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.fullName,
                    onValueChange = { onUpdate(personalInfo.copy(fullName = it)) },
                    label = { Text(stringResource(R.string.full_name_required)) },
                    placeholder = { Text(stringResource(R.string.e_g_alex_johnson)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Person, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.email,
                    onValueChange = { onUpdate(personalInfo.copy(email = it)) },
                    label = { Text(stringResource(R.string.email_required)) },
                    placeholder = { Text(stringResource(R.string.e_g_alex_email_com)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Email, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Email,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.phone,
                    onValueChange = { onUpdate(personalInfo.copy(phone = it)) },
                    label = { Text(stringResource(R.string.phone)) },
                    placeholder = { Text(stringResource(R.string.e_g_phone_number)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Phone, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Phone,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.location,
                    onValueChange = { onUpdate(personalInfo.copy(location = it)) },
                    label = { Text(stringResource(R.string.location)) },
                    placeholder = { Text(stringResource(R.string.e_g_kigali_rwanda)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.LocationOn, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.linkedIn,
                    onValueChange = { onUpdate(personalInfo.copy(linkedIn = it)) },
                    label = { Text(stringResource(R.string.linkedin_url)) },
                    placeholder = { Text(stringResource(R.string.e_g_linkedin)) },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = { Icon(Icons.Default.Link, null) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Uri,
                        imeAction = ImeAction.Next
                    ),
                    keyboardActions = KeyboardActions(
                        onNext = { focusManager.moveFocus(FocusDirection.Down) }
                    )
                )
            }
            item {
                Text(
                    stringResource(R.string.professional_summary),
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item {
                OutlinedTextField(
                    value = personalInfo.summary,
                    onValueChange = { onUpdate(personalInfo.copy(summary = it)) },
                    label = { Text(stringResource(R.string.summary)) },
                    placeholder = { Text(stringResource(R.string.brief_overview)) },
                    modifier = Modifier.fillMaxWidth(),
                    minLines = 4,
                    maxLines = 8,
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Sentences
                    )
                )
            }
            item { Spacer(Modifier.height(80.dp)) } // FAB clearance
        }
    }

    /* ============================================================
       ===================== TAB: SKILLS ==========================
       ============================================================ */

    @OptIn(ExperimentalLayoutApi::class)
    @Composable
    private fun SkillsTab(
        skills: List<String>,
        input: String,
        inputError: String?,
        onInputChange: (String) -> Unit,
        onAddSkill: () -> Unit,
        onRemoveSkill: (String) -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.Top,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = onInputChange,
                    label = { Text(stringResource(R.string.add_a_skill)) },
                    placeholder = { Text(stringResource(R.string.e_g_kotlin_project_management)) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    isError = inputError != null,
                    supportingText = inputError?.let { err ->
                        { Text(err, color = MaterialTheme.colorScheme.error) }
                    },
                    keyboardOptions = KeyboardOptions(
                        capitalization = KeyboardCapitalization.Words,
                        imeAction = ImeAction.Done
                    ),
                    keyboardActions = KeyboardActions(onDone = { onAddSkill() })
                )
                Button(
                    onClick = onAddSkill,
                    enabled = input.isNotBlank(),
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = null)
                }
            }

            if (skills.isEmpty()) {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = stringResource(R.string.no_skills_added_yet_type_a_skill_above_and_tap),
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            } else {
                Text(
                    text = "${skills.size} skill${if (skills.size == 1) "" else "s"} added",
                    style = MaterialTheme.typography.labelMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    skills.forEach { skill ->
                        InputChip(
                            selected = false,
                            onClick = {},
                            label = { Text(skill) },
                            trailingIcon = {
                                IconButton(
                                    onClick = { onRemoveSkill(skill) },
                                    modifier = Modifier.size(18.dp)
                                ) {
                                    Icon(
                                        Icons.Default.Close,
                                        contentDescription =stringResource(R.string.remove),
                                        modifier = Modifier.size(14.dp)
                                    )
                                }
                            }
                        )
                    }
                }
            }
        }
    }

    /* ============================================================
       =================== TAB: EXPERIENCE ========================
       ============================================================ */

    @Composable
    private fun ExperienceTab(
        experiences: List<WorkExperienceUiModel>,
        onAdd: () -> Unit,
        onRemove: (String) -> Unit
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (experiences.isEmpty()) {
                EmptyTabContent(
                    icon = Icons.Default.Work,
                    title = stringResource(R.string.no_experience_added_yet),
                    subtitle = stringResource(R.string.add_work_history),
                    buttonText = stringResource(R.string.add_experience),
                    onButtonClick = onAdd
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(experiences, key = { it.id }) { exp ->
                        ExperienceCard(experience = exp, onRemove = { onRemove(exp.id) })
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
                SmallFloatingActionButton(
                    onClick = onAdd,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 96.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_experience))
                }
            }
        }
    }

    @Composable
    private fun ExperienceCard(
        experience: WorkExperienceUiModel,
        onRemove: () -> Unit
    ) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.Top,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            experience.jobTitle,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                        Text(
                            experience.company,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            if (experience.isCurrent) "${experience.startDate} – Present"
                            else "${experience.startDate} – ${experience.endDate}",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                    IconButton(onClick = onRemove) {
                        Icon(
                            Icons.Default.Delete,
                            contentDescription = stringResource(R.string.remove),
                            tint = MaterialTheme.colorScheme.error
                        )
                    }
                }
                if (experience.description.isNotBlank()) {
                    Text(
                        experience.description,
                        style = MaterialTheme.typography.bodySmall,
                        maxLines = 3,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }

    /* ============================================================
       =================== TAB: EDUCATION =========================
       ============================================================ */

    @Composable
    private fun EducationTab(
        education: List<EducationUiModel>,
        onAdd: () -> Unit,
        onRemove: (String) -> Unit
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            if (education.isEmpty()) {
                EmptyTabContent(
                    icon = Icons.Default.School,
                    title = stringResource(R.string.no_education_added_yet),
                    subtitle = stringResource(R.string.add_academic_background),
                    buttonText = stringResource(R.string.add_education),
                    onButtonClick = onAdd
                )
            } else {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(education, key = { it.id }) { edu ->
                        EducationCard(education = edu, onRemove = { onRemove(edu.id) })
                    }
                    item { Spacer(Modifier.height(80.dp)) }
                }
                SmallFloatingActionButton(
                    onClick = onAdd,
                    modifier = Modifier
                        .align(Alignment.BottomEnd)
                        .padding(end = 16.dp, bottom = 96.dp)
                ) {
                    Icon(Icons.Default.Add, contentDescription = stringResource(R.string.add_education))
                }
            }
        }
    }

    @Composable
    private fun EducationCard(
        education: EducationUiModel,
        onRemove: () -> Unit
    ) {
        ElevatedCard(modifier = Modifier.fillMaxWidth()) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    val degreeField = listOfNotNull(
                        education.degree.takeIf { it.isNotBlank() },
                        education.fieldOfStudy.takeIf { it.isNotBlank() }
                    ).joinToString(" in ")
                    if (degreeField.isNotBlank()) {
                        Text(
                            degreeField,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis
                        )
                    }
                    if (education.institution.isNotBlank()) {
                        Text(
                            education.institution,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    val years = listOfNotNull(
                        education.startDate.takeIf { it.isNotBlank() },
                        education.endDate.takeIf { it.isNotBlank() }
                    ).joinToString(" – ")
                    if (years.isNotBlank()) {
                        Text(
                            years,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
                IconButton(onClick = onRemove) {
                    Icon(
                        Icons.Default.Delete,
                        contentDescription = stringResource(R.string.remove),
                        tint = MaterialTheme.colorScheme.error
                    )
                }
            }
        }
    }

    /* ============================================================
       =============== SHARED: EMPTY TAB CONTENT ==================
       ============================================================ */

    @Composable
    private fun EmptyTabContent(
        icon: androidx.compose.ui.graphics.vector.ImageVector,
        title: String,
        subtitle: String,
        buttonText: String,
        onButtonClick: () -> Unit
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                imageVector = icon,
                contentDescription = null,
                modifier = Modifier.size(56.dp),
                tint = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(12.dp))
            Text(title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.SemiBold)
            Spacer(Modifier.height(8.dp))
            Text(
                subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(Modifier.height(16.dp))
            Button(onClick = onButtonClick) {
                Icon(Icons.Default.Add, null, Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(buttonText)
            }
        }
    }

    /* ============================================================
       ============ DIALOG: ADD WORK EXPERIENCE ===================
       ============================================================ */

    @Composable
    private fun AddExperienceDialog(
        onDismiss: () -> Unit,
        onConfirm: (WorkExperienceUiModel) -> Unit
    ) {
        var jobTitle by remember { mutableStateOf("") }
        var company by remember { mutableStateOf("") }
        var startDate by remember { mutableStateOf("") }
        var endDate by remember { mutableStateOf("") }
        var isCurrent by remember { mutableStateOf(false) }
        var description by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.add_work_experience)) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = jobTitle,
                        onValueChange = { jobTitle = it },
                        label = { Text(stringResource(R.string.job_title)) },
                        placeholder = { Text(stringResource(R.string.e_g_software_engineer)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    OutlinedTextField(
                        value = company,
                        onValueChange = { company = it },
                        label = { Text(stringResource(R.string.company)) },
                        placeholder = { Text(stringResource(R.string.e_g_microcareer)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    OutlinedTextField(
                        value = startDate,
                        onValueChange = { startDate = it },
                        label = { Text(stringResource(R.string.start_date)) },
                        placeholder = { Text(stringResource(R.string.e_g_jan_2022)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Checkbox(
                            checked = isCurrent,
                            onCheckedChange = { checked ->
                                isCurrent = checked
                                if (checked) endDate = ""
                            }
                        )
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.currently_working_here), style = MaterialTheme.typography.bodyMedium)
                    }
                    if (!isCurrent) {
                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text(stringResource(R.string.end_date)) },
                            placeholder = { Text(stringResource(R.string.e_g_dec_2023)) },
                            modifier = Modifier.fillMaxWidth(),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                    }
                    OutlinedTextField(
                        value = description,
                        onValueChange = { description = it },
                        label = { Text(stringResource(R.string.description)) },
                        placeholder = { Text(stringResource(R.string.key_responsibilities)) },
                        modifier = Modifier.fillMaxWidth(),
                        minLines = 3,
                        maxLines = 6,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Sentences
                        )
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(
                            WorkExperienceUiModel(
                                jobTitle = jobTitle.trim(),
                                company = company.trim(),
                                startDate = startDate.trim(),
                                endDate = endDate.trim(),
                                isCurrent = isCurrent,
                                description = description.trim()
                            )
                        )
                    },
                    enabled = jobTitle.isNotBlank() && company.isNotBlank() && startDate.isNotBlank()
                ) { Text(stringResource(R.string.add)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    /* ============================================================
       ============ DIALOG: ADD EDUCATION =========================
       ============================================================ */

    @Composable
    private fun AddEducationDialog(
        onDismiss: () -> Unit,
        onConfirm: (EducationUiModel) -> Unit
    ) {
        var institution by remember { mutableStateOf("") }
        var degree by remember { mutableStateOf("") }
        var fieldOfStudy by remember { mutableStateOf("") }
        var startDate by remember { mutableStateOf("") }
        var endDate by remember { mutableStateOf("") }
        val focusManager = LocalFocusManager.current

        AlertDialog(
            onDismissRequest = onDismiss,
            title = { Text(stringResource(R.string.add_education)) },
            text = {
                Column(
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    OutlinedTextField(
                        value = institution,
                        onValueChange = { institution = it },
                        label = { Text(stringResource(R.string.institution)) },
                        placeholder = { Text(stringResource(R.string.e_g_university_of_rwanda)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    OutlinedTextField(
                        value = degree,
                        onValueChange = { degree = it },
                        label = { Text(stringResource(R.string.degree)) },
                        placeholder = { Text(stringResource(R.string.e_g_bachelors)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    OutlinedTextField(
                        value = fieldOfStudy,
                        onValueChange = { fieldOfStudy = it },
                        label = { Text(stringResource(R.string.field_of_study)) },
                        placeholder = { Text(stringResource(R.string.e_g_computer_science)) },
                        modifier = Modifier.fillMaxWidth(),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            capitalization = KeyboardCapitalization.Words,
                            imeAction = ImeAction.Next
                        ),
                        keyboardActions = KeyboardActions(
                            onNext = { focusManager.moveFocus(FocusDirection.Down) }
                        )
                    )
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = startDate,
                            onValueChange = { startDate = it },
                            label = { Text(stringResource(R.string.start_year)) },
                            placeholder = { Text(stringResource(R.string.year_2018)) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Next
                            ),
                            keyboardActions = KeyboardActions(
                                onNext = { focusManager.moveFocus(FocusDirection.Down) }
                            )
                        )
                        OutlinedTextField(
                            value = endDate,
                            onValueChange = { endDate = it },
                            label = { Text(stringResource(R.string.end_year)) },
                            placeholder = { Text(stringResource(R.string.year_2022)) },
                            modifier = Modifier.weight(1f),
                            singleLine = true,
                            keyboardOptions = KeyboardOptions(
                                keyboardType = KeyboardType.Number,
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = { focusManager.clearFocus() }
                            )
                        )
                    }
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        onConfirm(
                            EducationUiModel(
                                institution = institution.trim(),
                                degree = degree.trim(),
                                fieldOfStudy = fieldOfStudy.trim(),
                                startDate = startDate.trim(),
                                endDate = endDate.trim()
                            )
                        )
                    },
                    enabled = institution.isNotBlank() && degree.isNotBlank()
                ) { Text(stringResource(R.string.add)) }
            },
            dismissButton = {
                TextButton(onClick = onDismiss) { Text(stringResource(R.string.cancel)) }
            }
        )
    }

    /* ============================================================
       ================== DIALOG: CV PREVIEW ======================
       ============================================================ */

    @Composable
    private fun CvPreviewDialog(
        cvText: String,
        onDismiss: () -> Unit,
        onDownloadPdf: () -> Unit
    ) {
        Dialog(
            onDismissRequest = onDismiss,
            properties = DialogProperties(usePlatformDefaultWidth = false)
        ) {
            Surface(
                modifier = Modifier
                    .fillMaxWidth(0.95f)
                    .fillMaxHeight(0.85f),
                shape = MaterialTheme.shapes.large,
                tonalElevation = 6.dp
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 8.dp, vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            stringResource(R.string.cv_preview),
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier.padding(start = 8.dp)
                        )
                        Row {
                            IconButton(onClick = onDownloadPdf  ) {
                                Icon(Icons.Default.Download, contentDescription = stringResource(R.string.share_cv))
                            }
                            IconButton(onClick = onDismiss) {
                                Icon(Icons.Default.Close, contentDescription = stringResource(R.string.close))
                            }
                        }
                    }
                    HorizontalDivider()
                    if (cvText.isBlank()) {
                        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            Column(
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.spacedBy(8.dp)
                            ) {
                                Icon(
                                    Icons.Default.Description,
                                    contentDescription = null,
                                    modifier = Modifier.size(48.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    stringResource(R.string.your_cv_is_empty),
                                    style = MaterialTheme.typography.titleMedium
                                )
                                Text(
                                    stringResource(R.string.fill_in_name_and_section),
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }
                    } else {
                        SelectionContainer(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(16.dp)
                                .verticalScroll(rememberScrollState())
                        ) {
                            Text(
                                text = cvText,
                                style = MaterialTheme.typography.bodySmall.copy(
                                    fontFamily = FontFamily.Monospace
                                )
                            )
                        }
                    }
                }
            }
        }
    }
