package com.microcareer.match.presentation.employer

import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.compose.ui.res.stringResource
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.microcareer.match.presentation.profile.ProfileEvent
import com.microcareer.match.presentation.profile.ProfileViewModel
import com.microcareer.match.utils.LocaleHelper
import kotlinx.coroutines.flow.collectLatest
import com.microcareer.match.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EmployerProfileScreen(
    viewModel : ProfileViewModel = hiltViewModel(),
    onBack    : () -> Unit,
    onLogout  : () -> Unit
) {
    val uiState           = viewModel.uiState.collectAsState().value
    val snackbarHostState = remember { SnackbarHostState() }
    val context           = LocalContext.current

    LaunchedEffect(Unit) {
        viewModel.loadSavedLanguage(context)
        viewModel.events.collectLatest { event ->
            when (event) {
                is ProfileEvent.ShowSnackbar -> snackbarHostState.showSnackbar(event.message)
            }
        }
    }

    val photoLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri -> uri?.let { viewModel.uploadProfilePhoto(it) } }

    var activeSheet by remember { mutableStateOf<EmployerSheet?>(null) }

    // ── BOTTOM SHEETS ────────────────────────────────────────────────────────
    if (activeSheet != null) {
        ModalBottomSheet(onDismissRequest = { activeSheet = null }) {
            when (activeSheet) {

                // ── EDIT PROFILE ──────────────────────────────────────────
                EmployerSheet.EditProfile -> {
                    var name     by remember { mutableStateOf(uiState.fullName ?: "") }
                    var location by remember { mutableStateOf(uiState.location ?: "") }
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp)
                            .padding(bottom = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Text(stringResource(R.string.edit_profile), style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold)
                        OutlinedTextField(
                            value         = name,
                            onValueChange = { name = it },
                            label         = { Text(stringResource(R.string.company_full_name)) },
                            modifier      = Modifier.fillMaxWidth(),
                            leadingIcon   = { Icon(Icons.Default.Business, null) },
                            singleLine    = true
                        )
                        OutlinedTextField(
                            value         = location,
                            onValueChange = { location = it },
                            label         = { Text(stringResource(R.string.location)) },
                            modifier      = Modifier.fillMaxWidth(),
                            leadingIcon   = { Icon(Icons.Default.LocationOn, null) },
                            singleLine    = true
                        )
                        Row(modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                            OutlinedButton(onClick = { activeSheet = null },
                                modifier = Modifier.weight(1f)) { Text(stringResource(R.string.cancel)) }
                            Button(
                                onClick = {
                                    viewModel.updatePersonalInfo(name.trim(), "", location.trim())
                                    activeSheet = null
                                },
                                modifier = Modifier.weight(1f)
                            ) { Text(stringResource(R.string.save)) }
                        }
                    }
                }

                // ── NOTIFICATIONS ─────────────────────────────────────────
                EmployerSheet.Notifications -> {
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
                        EmployerNotifRow(
                            label   = stringResource(R.string.push_notifications),
                            sub     = "New applicants, job alerts",
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = { viewModel.onNotificationsChanged(it) }
                        )
                        HorizontalDivider()
                        EmployerNotifRow(
                            label   = stringResource(R.string.new_applicants),
                            sub     = "When someone applies to your job",
                            checked = uiState.notificationsEnabled,
                            onCheckedChange = { viewModel.onNotificationsChanged(it) }
                        )
                        HorizontalDivider()
                        EmployerNotifRow(
                            label   = stringResource(R.string.job_status_updates),
                            sub     = "Reminders about open/closing jobs",
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
                EmployerSheet.Language -> {
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

                // ── SECURITY ──────────────────────────────────────────────
                EmployerSheet.Security -> {
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
                        Text(stringResource(R.string.security), style = MaterialTheme.typography.titleLarge,
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
                                    Icon(if (showPw) Icons.Default.VisibilityOff
                                    else Icons.Default.Visibility, null)
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
                            OutlinedButton(onClick = { activeSheet = null },
                                modifier = Modifier.weight(1f)) { Text(stringResource(R.string.cancel)) }
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
                EmployerSheet.Help -> {
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
                        EmployerHelpItem("How to post a job",            Icons.Default.Work)
                        HorizontalDivider()
                        EmployerHelpItem("Managing applicants",          Icons.Default.People)
                        HorizontalDivider()
                        EmployerHelpItem("Billing & payments",           Icons.Default.AccountBalanceWallet)
                        HorizontalDivider()
                        EmployerHelpItem("Contact support",              Icons.Default.Email)
                        Spacer(Modifier.height(8.dp))
                        Surface(
                            color = MaterialTheme.colorScheme.secondaryContainer,
                            shape = MaterialTheme.shapes.medium
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth().padding(16.dp),
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

    // ── MAIN SCAFFOLD ────────────────────────────────────────────────────────
    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.company_profile)) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer
                )
            )
        }
    ) { padding ->
        LazyColumn(
            modifier            = Modifier.fillMaxSize().padding(padding),
            contentPadding      = PaddingValues(20.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {

            // ── COMPANY HEADER ────────────────────────────────────────────
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(
                        modifier            = Modifier.fillMaxWidth().padding(24.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Box(contentAlignment = Alignment.BottomEnd) {
                            Box(
                                modifier = Modifier.size(96.dp).clip(CircleShape)
                                    .border(2.dp, MaterialTheme.colorScheme.primary, CircleShape)
                                    .clickable { photoLauncher.launch("image/*") },
                                contentAlignment = Alignment.Center
                            ) {
                                if (uiState.profilePhotoUrl != null) {
                                    AsyncImage(model = uiState.profilePhotoUrl,
                                        contentDescription = stringResource(R.string.company_logo),
                                        modifier = Modifier.fillMaxSize().clip(CircleShape),
                                        contentScale = ContentScale.Crop)
                                } else {
                                    Box(modifier = Modifier.fillMaxSize()
                                        .background(MaterialTheme.colorScheme.primaryContainer),
                                        contentAlignment = Alignment.Center) {
                                        Icon(Icons.Default.Business, null, Modifier.size(44.dp),
                                            tint = MaterialTheme.colorScheme.onPrimaryContainer)
                                    }
                                }
                                if (uiState.isUploadingPhoto) {
                                    Box(modifier = Modifier.fillMaxSize()
                                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                                        contentAlignment = Alignment.Center) {
                                        CircularProgressIndicator(Modifier.size(24.dp))
                                    }
                                }
                            }
                            Surface(modifier = Modifier.size(30.dp).offset(2.dp, 2.dp),
                                shape = CircleShape, color = MaterialTheme.colorScheme.primary) {
                                IconButton(onClick = { photoLauncher.launch("image/*") },
                                    modifier = Modifier.padding(4.dp)) {
                                    Icon(Icons.Default.CameraAlt, null, Modifier.size(14.dp),
                                        tint = MaterialTheme.colorScheme.onPrimary)
                                }
                            }
                        }
                        Text(uiState.fullName ?: uiState.user?.name ?: "Employer",
                            style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
                        Text(uiState.user?.email ?: "", style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant)
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
                        Surface(color = MaterialTheme.colorScheme.primaryContainer,
                            shape = MaterialTheme.shapes.small) {
                            Row(modifier = Modifier.padding(horizontal = 12.dp, vertical = 4.dp),
                                horizontalArrangement = Arrangement.spacedBy(4.dp),
                                verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Business, null, Modifier.size(14.dp),
                                    tint = MaterialTheme.colorScheme.primary)
                                Text(stringResource(R.string.employer), style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary,
                                    fontWeight = FontWeight.SemiBold)
                            }
                        }
                    }
                }
            }

            // ── SETTINGS ─────────────────────────────────────────────────
            item {
                Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold)
            }
            item {
                ElevatedCard(modifier = Modifier.fillMaxWidth()) {
                    Column(modifier = Modifier.padding(4.dp)) {
                        ProfileMenuItem(Icons.Default.Edit, stringResource(R.string.edit_profile)) {
                            activeSheet = EmployerSheet.EditProfile
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(Icons.Default.Notifications, stringResource(R.string.notifications)) {
                            activeSheet = EmployerSheet.Notifications
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(
                            icon    = Icons.Default.Language,
                            label   = stringResource(R.string.app_language),
                            subtitle = uiState.selectedLanguage
                        ) {
                            activeSheet = EmployerSheet.Language
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(Icons.Default.Security, stringResource(R.string.security)) {
                            activeSheet = EmployerSheet.Security
                        }
                        HorizontalDivider(Modifier.padding(horizontal = 16.dp))
                        ProfileMenuItem(Icons.AutoMirrored.Filled.Help, stringResource(R.string.help_support)) {
                            activeSheet = EmployerSheet.Help
                        }
                    }
                }
            }

            // ── LOGOUT ────────────────────────────────────────────────────
            item {
                Button(onClick = { viewModel.logout(); onLogout() },
                    modifier = Modifier.fillMaxWidth().height(50.dp),
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

private enum class EmployerSheet { EditProfile, Notifications, Language, Security, Help }

// ── PRIVATE COMPOSABLES ──────────────────────────────────────────────────────

@Composable
private fun ProfileMenuItem(
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
private fun EmployerNotifRow(
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
private fun EmployerHelpItem(label: String, icon: androidx.compose.ui.graphics.vector.ImageVector) {
    ListItem(
        modifier        = Modifier.clickable { },
        headlineContent = { Text(label) },
        leadingContent  = { Icon(icon, null, tint = MaterialTheme.colorScheme.primary) },
        trailingContent = { Icon(Icons.Default.ChevronRight, null,
            tint = MaterialTheme.colorScheme.onSurfaceVariant) }
    )
}