package com.microcareer.match.presentation.employee

import androidx.activity.compose.BackHandler
import androidx.compose.ui.res.stringResource
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.microcareer.match.presentation.profile.ProfileViewModel
import kotlinx.coroutines.flow.collectLatest
import com.microcareer.match.presentation.profile.ProfileEvent
import com.microcareer.match.utils.LocaleHelper
import com.microcareer.match.R

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun EmployeeProfileScreen(
    viewModel : ProfileViewModel = hiltViewModel(),
    onBack    : () -> Unit,
    onLogout  : () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }
    val context = androidx.compose.ui.platform.LocalContext.current
    BackHandler(onBack = onBack)

// Re-reads the saved locale every time this screen is (re)composed,
// including when the user navigates back to it
    DisposableEffect(Unit) {
        viewModel.loadSavedLanguage(context)
        onDispose { }
    }

    LaunchedEffect(Unit) {
        viewModel.events.collectLatest { event ->
            when (event) {
                is ProfileEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.uploadProfilePhoto(it) } }

    var activeSheet by remember { mutableStateOf<EmployeeSheet?>(null) }

    var skillInput   by remember { mutableStateOf("") }
    var localSkills  by remember { mutableStateOf(uiState.skills) }
    var showAddSkill by remember { mutableStateOf(false) }
    LaunchedEffect(uiState.skills) { localSkills = uiState.skills }

    var showBioEdit by remember { mutableStateOf(false) }
    var bioText     by remember { mutableStateOf(uiState.bio ?: "") }

    if (activeSheet != null) {
        ModalBottomSheet(onDismissRequest = { activeSheet = null }) {
            when (activeSheet) {

                // ── EDIT PERSONAL INFO ────────────────────────────────────
                EmployeeSheet.EditInfo -> {
                    var name     by remember { mutableStateOf(uiState.fullName ?: "") }
                    var phone    by remember { mutableStateOf(uiState.phoneNumber ?: "") }
                    var location by remember { mutableStateOf(uiState.location ?: "") }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(stringResource(R.string.edit_personal_info), style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                        OutlinedTextField(value = name, onValueChange = { name = it },
                            label = { Text(stringResource(R.string.full_name)) }, modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Person, null) }, singleLine = true)
                        OutlinedTextField(value = phone, onValueChange = { phone = it },
                            label = { Text(stringResource(R.string.phone_number)) }, modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.Phone, null) }, singleLine = true,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone))
                        OutlinedTextField(value = location, onValueChange = { location = it },
                            label = { Text(stringResource(R.string.location)) }, modifier = Modifier.fillMaxWidth(),
                            leadingIcon = { Icon(Icons.Default.LocationOn, null) }, singleLine = true)
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(onClick = { activeSheet = null }, modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.cancel))
                            }
                            Button(
                                onClick = {
                                    viewModel.updatePersonalInfo(name.trim(), phone.trim(), location.trim())
                                    activeSheet = null
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text(stringResource(R.string.save)) }
                        }
                    }
                }

                // ── NOTIFICATIONS ─────────────────────────────────────────
                EmployeeSheet.Notifications -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(stringResource(R.string.notifications), style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        NotificationToggleRow(
                            label   = stringResource(R.string.push_notifications),
                            sub     = "Job matches, application updates",
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = { viewModel.onNotificationsChanged(it) }
                        )
                        HorizontalDivider()
                        NotificationToggleRow(
                            label   = stringResource(R.string.new_job_alerts),
                            sub     = "When new jobs match your skills",
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = { viewModel.onNotificationsChanged(it) }
                        )
                        HorizontalDivider()
                        NotificationToggleRow(
                            label   = stringResource(R.string.application_status),
                            sub     = "Accepted, rejected, shortlisted",
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = { viewModel.onNotificationsChanged(it) }
                        )
                        Spacer(Modifier.height(8.dp))
                        Button(onClick = { activeSheet = null }, modifier = Modifier.fillMaxWidth()) {
                            Text(stringResource(R.string.done))
                        }
                    }
                }

                // ── LANGUAGE ──────────────────────────────────────────────
                EmployeeSheet.Language -> {
                    val context = androidx.compose.ui.platform.LocalContext.current
                    val languages = LocaleHelper.supportedLanguages
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(
                            stringResource(R.string.language_title),
                            style      = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold
                        )
                        Spacer(Modifier.height(8.dp))
                        languages.forEach { (displayName, tag) ->
                            ListItem(
                                modifier        = Modifier
                                    .fillMaxWidth()
                                    .clickable {
                                        viewModel.onLanguageSelected(context, tag)
                                        activeSheet = null
                                        (context as? android.app.Activity)?.recreate()
                                    },
                                headlineContent = { Text(displayName) },
                                trailingContent = {
                                    if (tag == LocaleHelper.getSavedLocale(context)) {
                                        Icon(
                                            Icons.Default.Check, null,
                                            tint = MaterialTheme.colorScheme.primary
                                        )
                                    }
                                }
                            )
                            HorizontalDivider()
                        }
                    }
                }

                // ── PRIVACY & SECURITY ────────────────────────────────────
                EmployeeSheet.Security -> {
                    var currentPw by remember { mutableStateOf("") }
                    var newPw     by remember { mutableStateOf("") }
                    var confirmPw by remember { mutableStateOf("") }
                    var showPw    by remember { mutableStateOf(false) }
                    var pwError   by remember { mutableStateOf<String?>(null) }

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(stringResource(R.string.privacy_security), style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)

                        OutlinedTextField(
                            value         = currentPw,
                            onValueChange = { currentPw = it },
                            label         = { Text(stringResource(R.string.current_password)) },
                            modifier      = Modifier.fillMaxWidth(),
                            singleLine    = true,
                            leadingIcon   = { Icon(Icons.Default.Lock, null) },
                            visualTransformation = if (showPw) VisualTransformation.None
                            else PasswordVisualTransformation(),
                            trailingIcon  = {
                                IconButton(onClick = { showPw = !showPw }) {
                                    Icon(if (showPw) Icons.Default.VisibilityOff else Icons.Default.Visibility, null)
                                }
                            }
                        )
                        OutlinedTextField(
                            value         = newPw,
                            onValueChange = { newPw = it; pwError = null },
                            label         = { Text(stringResource(R.string.new_password)) },
                            modifier      = Modifier.fillMaxWidth(),
                            singleLine    = true,
                            leadingIcon   = { Icon(Icons.Default.Lock, null) },
                            visualTransformation = if (showPw) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        OutlinedTextField(
                            value         = confirmPw,
                            onValueChange = { confirmPw = it; pwError = null },
                            label         = { Text(stringResource(R.string.confirm_new_password)) },
                            modifier      = Modifier.fillMaxWidth(),
                            singleLine    = true,
                            isError       = pwError != null,
                            supportingText = pwError?.let { { Text(it, color = MaterialTheme.colorScheme.error) } },
                            leadingIcon   = { Icon(Icons.Default.Lock, null) },
                            visualTransformation = if (showPw) VisualTransformation.None
                            else PasswordVisualTransformation()
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(onClick = { activeSheet = null }, modifier = Modifier.weight(1f)) {
                                Text(stringResource(R.string.cancel))
                            }
                            Button(
                                onClick = {
                                    when {
                                        currentPw.isBlank() -> pwError = "Enter current password"
                                        newPw.length < 8    -> pwError = "Min 8 characters"
                                        newPw != confirmPw  -> pwError = "Passwords do not match"
                                        else -> {
                                            viewModel.updatePassword(currentPw, newPw)
                                            activeSheet = null
                                        }
                                    }
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text(stringResource(R.string.change)) }
                        }
                    }
                }

                // ── HELP & SUPPORT ────────────────────────────────────────
                EmployeeSheet.Help -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text(stringResource(R.string.help_support), style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                        Spacer(Modifier.height(8.dp))
                        HelpItem("How to apply for a job",    Icons.Default.Work)
                        HorizontalDivider()
                        HelpItem("How to update your skills", Icons.Default.Build)
                        HorizontalDivider()
                        HelpItem("Payment & wallet help",     Icons.Default.AccountBalanceWallet)
                        HorizontalDivider()
                        HelpItem("Contact support",           Icons.Default.Email)
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            color  = MaterialTheme.colorScheme.secondaryContainer,
                            shape  = MaterialTheme.shapes.medium
                        ) {
                            Row(modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp),
                                horizontalArrangement = Arrangement.spacedBy(12.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Icon(Icons.Default.Email, null,
                                    tint = MaterialTheme.colorScheme.onSecondaryContainer)
                                Column {
                                    Text(stringResource(R.string.email_support), fontWeight = FontWeight.SemiBold,
                                        style = MaterialTheme.typography.bodyMedium)
                                    Text(stringResource(R.string.support_email),
                                        style = MaterialTheme.typography.bodySmall,
                                        color = MaterialTheme.colorScheme.onSecondaryContainer)
                                }
                            }
                        }
                    }
                }

                null -> Unit
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.my_profile)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = stringResource(R.string.back))
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier            = Modifier
                .fillMaxSize()
                .padding(padding),
            contentPadding      = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── AVATAR CARD ───────────────────────────────────────────────
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier            = Modifier
                            .fillMaxWidth()
                            .padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier
                                    .size(100.dp)
                                    .clip(CircleShape)
                                    .border(3.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    .clickable { photoLauncher.launch("image/*") },
                                contentAlignment = Alignment.Center
                            ) {
                                if (uiState.profilePhotoUrl != null) {
                                    AsyncImage(model = uiState.profilePhotoUrl,
                                        contentDescription = stringResource(R.string.profile_photo),
                                        modifier = Modifier
                                            .fillMaxSize()
                                            .clip(CircleShape),
                                        contentScale = ContentScale.Crop)
                                } else {
                                    Box(modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Person, null, Modifier.size(50.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                }
                                if (uiState.isUploadingPhoto) {
                                    Box(modifier = Modifier
                                        .fillMaxSize()
                                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                                        contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator(Modifier.size(24.dp))
                                    }
                                }
                            }
                            Surface(modifier = Modifier
                                .size(32.dp)
                                .offset(3.dp, 3.dp),
                                shape = CircleShape, color = MaterialTheme.colorScheme.primary) {
                                IconButton(onClick = { photoLauncher.launch("image/*") },
                                    modifier = Modifier.padding(4.dp)) {
                                    Icon(Icons.Default.CameraAlt, null, Modifier.size(16.dp),
                                        tint = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                        Text(uiState.fullName ?: uiState.user?.name ?: "User",
                            style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(uiState.user?.email ?: "", style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
                        val phoneNumber = uiState.phoneNumber
                        if (!phoneNumber.isNullOrBlank()) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.Phone, null, Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(phoneNumber, style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        val location = uiState.location
                        if (!location.isNullOrBlank()) {
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                                Icon(Icons.Default.LocationOn, null, Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.onSurfaceVariant)
                                Text(location, style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant)
                            }
                        }
                        Surface(color = MaterialTheme.colorScheme.tertiaryContainer,
                            shape = MaterialTheme.shapes.small) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Person, null, Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.onTertiaryContainer)
                                Text(stringResource(R.string.job_seeker), style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onTertiaryContainer,
                                    fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // ── STATS ─────────────────────────────────────────────────────
            if (uiState.rating > 0f || uiState.completedJobs > 0) {
                item {
                    ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .padding(20.dp),
                            horizontalArrangement = Arrangement.SpaceEvenly) {
                            ProfileStat(Icons.Default.Work, uiState.completedJobs.toString(), "Completed")
                            VerticalDivider(Modifier.height(48.dp))
                            ProfileStat(Icons.Default.Star,
                                String.format(java.util.Locale.getDefault(), "%.1f", uiState.rating), "Rating")
                            VerticalDivider(Modifier.height(48.dp))
                            ProfileStat(Icons.Default.RateReview, uiState.totalReviews.toString(), "Reviews")
                        }
                    }
                }
            }

            // ── BIO ───────────────────────────────────────────────────────
            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.about_me), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { showBioEdit = !showBioEdit; bioText = uiState.bio ?: "" }) {
                        Icon(Icons.Default.Edit, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.edit))
                    }
                }
            }
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        if (showBioEdit) {
                            OutlinedTextField(value = bioText, onValueChange = { bioText = it },
                                modifier = Modifier.fillMaxWidth(), minLines = 3,
                                placeholder = { Text(stringResource(R.string.write_a_short_bio_about_yourself)) })
                            Spacer(Modifier.height(8.dp))
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                OutlinedButton(onClick = { showBioEdit = false },
                                    modifier = Modifier.weight(1f)) { Text(stringResource(R.string.cancel)) }
                                Button(onClick = { viewModel.updateBio(bioText); showBioEdit = false },
                                    modifier = Modifier.weight(1f)) { Text(stringResource(R.string.save)) }
                            }
                        } else {
                            val bioDisplay = uiState.bio
                            Text(
                                text  = if (bioDisplay.isNullOrBlank()) stringResource(R.string.no_bio)
                                else bioDisplay,
                                style = MaterialTheme.typography.bodyMedium,
                                color = if (bioDisplay.isNullOrBlank()) MaterialTheme.colorScheme.onSurfaceVariant
                                else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            // ── SKILLS ────────────────────────────────────────────────────
            item {
                Row(modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically) {
                    Text(stringResource(R.string.skills), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    TextButton(onClick = { showAddSkill = !showAddSkill }) {
                        Icon(Icons.Default.Add, null, Modifier.size(16.dp))
                        Spacer(Modifier.width(4.dp))
                        Text(stringResource(R.string.add_skill))
                    }
                }
            }
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(12.dp)) {
                        if (showAddSkill) {
                            Row(modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                OutlinedTextField(value = skillInput, onValueChange = { skillInput = it },
                                    label = { Text(stringResource(R.string.skill)) }, singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    placeholder = { Text(stringResource(R.string.e_g_carpentry_driving)) })
                                IconButton(onClick = {
                                    val s = skillInput.trim()
                                    if (s.isNotBlank() && !localSkills.contains(s)) {
                                        localSkills = localSkills + s
                                        viewModel.updateSkills(localSkills)
                                    }
                                    skillInput = ""; showAddSkill = false
                                }, enabled = skillInput.isNotBlank()) {
                                    Icon(Icons.Default.Check, null, tint = MaterialTheme.colorScheme.primary)
                                }
                            }
                        }
                        if (localSkills.isEmpty()) {
                            Text(stringResource(R.string.no_skills_added_yet),
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant)
                        } else {
                            FlowRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalArrangement = Arrangement.spacedBy(6.dp)) {
                                localSkills.forEach { skill ->
                                    InputChip(selected = false, onClick = {}, label = {
                                        Text(skill, style = MaterialTheme.typography.labelMedium)
                                    }, leadingIcon = {
                                        Icon(Icons.Default.CheckCircle, null, Modifier.size(14.dp),
                                            tint = MaterialTheme.colorScheme.primary)
                                    }, trailingIcon = {
                                        IconButton(onClick = {
                                            localSkills = localSkills - skill
                                            viewModel.updateSkills(localSkills)
                                        }, modifier = Modifier.size(18.dp)) {
                                            Icon(Icons.Default.Close, null, Modifier.size(12.dp))
                                        }
                                    })
                                }
                            }
                        }
                    }
                }
            }

            // ── SETTINGS ─────────────────────────────────────────────────
            item {
                Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
            }
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        ProfileSettingsItem(Icons.Default.Edit, stringResource(R.string.edit_personal_info)) {
                            activeSheet = EmployeeSheet.EditInfo
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileSettingsItem(Icons.Default.Notifications, stringResource(R.string.notifications)) {
                            activeSheet = EmployeeSheet.Notifications
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileSettingsItem(Icons.Default.Language, stringResource(R.string.app_language),
                            subtitle = uiState.selectedLanguage) {
                            activeSheet = EmployeeSheet.Language
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileSettingsItem(Icons.Default.Security, stringResource(R.string.security)) {
                            activeSheet = EmployeeSheet.Security
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileSettingsItem(Icons.AutoMirrored.Filled.Help, stringResource(R.string.help_support)) {
                            activeSheet = EmployeeSheet.Help
                        }
                    }
                }
            }

            // ── LOGOUT ────────────────────────────────────────────────────
            item {
                Button(onClick = { viewModel.logout(); onLogout() },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(50.dp),
                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.error)) {
                    Icon(Icons.AutoMirrored.Filled.Logout, null, Modifier.padding(end = 8.dp))
                    Text(stringResource(R.string.logout))
                }
            }

            item { Spacer(Modifier.height(8.dp)) }
        }
    }
}

// ── SHEET ENUM ───────────────────────────────────────────────────────────────

private enum class EmployeeSheet { EditInfo, Notifications, Language, Security, Help }

// ── SHARED COMPOSABLES ───────────────────────────────────────────────────────

@Composable
private fun ProfileStat(
    icon  : androidx.compose.ui.graphics.vector.ImageVector,
    value : String,
    label : String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(4.dp)) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
        Text(value, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun ProfileSettingsItem(
    icon     : androidx.compose.ui.graphics.vector.ImageVector,
    label    : String,
    subtitle : String? = null,
    onClick  : () -> Unit
) {
    ListItem(
        modifier        = Modifier.clickable(onClick = onClick),
        headlineContent = { Text(label) },
        supportingContent = subtitle?.let { { Text(it, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant) } },
        leadingContent  = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = { Icon(Icons.Default.ChevronRight, null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant) }
    )
}

@Composable
private fun NotificationToggleRow(
    label           : String,
    sub             : String,
    checked         : Boolean,
    onCheckedChange : (Boolean) -> Unit
) {
    ListItem(
        headlineContent   = { Text(label) },
        supportingContent = { Text(sub, style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant) },
        trailingContent   = { Switch(checked = checked, onCheckedChange = onCheckedChange) }
    )
}

@Composable
private fun HelpItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    ListItem(
        modifier        = Modifier.clickable { },
        headlineContent = { Text(label) },
        leadingContent  = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = { Icon(Icons.Default.ChevronRight, null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant) }
    )
}