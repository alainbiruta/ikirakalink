package com.microcareer.match.presentation.profile

import android.content.ClipData
import androidx.compose.ui.res.stringResource
import android.content.ClipboardManager
import android.content.Context
import android.net.Uri
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
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
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.google.firebase.auth.FirebaseAuth
import com.microcareer.match.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    viewModel: ProfileViewModel,
    uiState: ProfileUiState,
    onBack: () -> Unit,
    onLogout: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    onNotificationsChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier
) {
    BackHandler(onBack = onBack)
    val firebaseAuth = FirebaseAuth.getInstance()

    LaunchedEffect(firebaseAuth.currentUser) {
        if (firebaseAuth.currentUser == null) {
            onLogout()
        }
    }

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Profile",
                        style = MaterialTheme.typography.titleLarge
                    )
                },
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
    ) { padding ->
        when {
            uiState.isLoading -> LoadingContent(padding)
            uiState.error != null -> ErrorContent(
                error = uiState.error,
                padding = padding,
                onDismiss = { viewModel.clearError() }
            )
            else -> ProfileContent(
                uiState = uiState,
                padding = padding,
                viewModel = viewModel,
                onLogout = { viewModel.logout() },
                onLanguageSelected = onLanguageSelected,
                onNotificationsChanged = onNotificationsChanged
            )
        }
    }
}

@Composable
private fun LoadingContent(padding: PaddingValues) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorContent(
    error: String,
    padding: PaddingValues,
    onDismiss: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.errorContainer
            )
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    Icons.Default.Error,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                    modifier = Modifier.size(48.dp)
                )
                Spacer(Modifier.height(8.dp))
                Text(
                    text = error,
                    color = MaterialTheme.colorScheme.onErrorContainer,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(Modifier.height(16.dp))
                Button(onClick = onDismiss) {
                    Text(stringResource(R.string.dismiss))
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileContent(
    uiState: ProfileUiState,
    padding: PaddingValues,
    viewModel: ProfileViewModel,
    onLogout: () -> Unit,
    onLanguageSelected: (String) -> Unit,
    onNotificationsChanged: (Boolean) -> Unit
) {
    // ✅ ENHANCED: Local language support including Kinyarwanda
    val languages = remember {
        listOf(
            "English" to "🇬🇧 English",
            "Kinyarwanda" to "🇷🇼 Kinyarwanda (Ikinyarwanda)",
            "Swahili" to "🇰🇪 Swahili (Kiswahili)",
            "French" to "🇫🇷 Français",
            "Arabic" to "🇸🇦 العربية",
            "Spanish" to "🇪🇸 Español"
        )
    }

    var expanded by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding),
        contentPadding = PaddingValues(20.dp),
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        // ═══════════════════════════════════════════════════════
        // USER HEADER WITH PHOTO
        // ═══════════════════════════════════════════════════════
        item {
            UserHeader(
                uiState = uiState,
                viewModel = viewModel
            )
        }

        // ═══════════════════════════════════════════════════════
        // STATS CARD (if available)
        // ═══════════════════════════════════════════════════════
        if (uiState.rating > 0f || uiState.completedJobs > 0) {
            item {
                StatsCard(uiState)
            }
        }

        // ═══════════════════════════════════════════════════════
        // SKILLS SECTION
        // ═══════════════════════════════════════════════════════
        item {
            SectionTitle("Skills")
        }

        item {
            SkillsSection(skills = uiState.skills)
        }

        // ═══════════════════════════════════════════════════════
        // SETTINGS SECTION
        // ═══════════════════════════════════════════════════════
        item {
            SectionTitle("Settings")
        }

        item {
            LanguageSelector(
                selectedLanguage = uiState.selectedLanguage,
                languages = languages,
                expanded = expanded,
                onExpandedChange = { expanded = !expanded },
                onLanguageSelected = {
                    expanded = false
                    onLanguageSelected(it)
                }
            )
        }

        item {
            NotificationToggle(
                checked = uiState.notificationsEnabled,
                onCheckedChange = onNotificationsChanged
            )
        }

        // ═══════════════════════════════════════════════════════
        // ACCOUNT SECTION
        // ═══════════════════════════════════════════════════════
        item {
            SectionTitle("Account")
        }

        // DEBUG TOOLS (Only in DEBUG builds)
        if (com.microcareer.match.BuildConfig.DEBUG) {
            item {
                val debugViewModel: com.microcareer.match.presentation.debug.DebugViewModel =
                    androidx.hilt.navigation.compose.hiltViewModel()

                com.microcareer.match.presentation.debug.DebugDatabaseMenu(
                    viewModel = debugViewModel
                )
            }

            item {
                FirebaseTokenButton()
            }
        }

        item {
            LogoutButton(onLogout)
        }
    }
}

// ══════════════════════════════════════════════════════════
// ✅ ENHANCED: USER HEADER WITH PHOTO UPLOAD
// ══════════════════════════════════════════════════════════

@Composable
private fun UserHeader(
    uiState: ProfileUiState,
    viewModel: ProfileViewModel
) {
    val context = LocalContext.current

    // ✅ Photo picker launcher
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        uri?.let { viewModel.uploadProfilePhoto(it) }
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(6.dp),
        shape = MaterialTheme.shapes.large
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // ═══════════════════════════════════════════════════════
            // PROFILE PHOTO
            // ═══════════════════════════════════════════════════════

            Box(
                contentAlignment = Alignment.BottomEnd
            ) {
                // Photo container
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(
                            width = 3.dp,
                            color = MaterialTheme.colorScheme.primary,
                            shape = CircleShape
                        )
                        .clickable { photoPickerLauncher.launch("image/*") },
                    contentAlignment = Alignment.Center
                ) {
                    if (uiState.profilePhotoUrl != null) {
                        // Show uploaded photo
                        AsyncImage(
                            model = uiState.profilePhotoUrl,
                            contentDescription = stringResource(R.string.profile_photo),
                            modifier = Modifier
                                .fillMaxSize()
                                .clip(CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    } else {
                        // Show default icon
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.primaryContainer),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Filled.Person,
                                contentDescription = null,
                                modifier = Modifier.size(50.dp),
                                tint = MaterialTheme.colorScheme.onPrimaryContainer
                            )
                        }
                    }

                    // Loading overlay
                    if (uiState.isUploadingPhoto) {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator(modifier = Modifier.size(24.dp))
                        }
                    }
                }

                // Camera icon button
                Surface(
                    modifier = Modifier
                        .size(32.dp)
                        .offset(x = 4.dp, y = 4.dp),
                    shape = CircleShape,
                    color = MaterialTheme.colorScheme.primary,
                    shadowElevation = 4.dp
                ) {
                    IconButton(
                        onClick = { photoPickerLauncher.launch("image/*") },
                        modifier = Modifier.padding(4.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.CameraAlt,
                            contentDescription = stringResource(R.string.change_photo),
                            modifier = Modifier.size(16.dp),
                            tint = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // ═══════════════════════════════════════════════════════
            // ✅ FULL NAME (separate from email)
            // ═══════════════════════════════════════════════════════

            Text(
                text = uiState.fullName ?: uiState.user?.name ?: "User",
                style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Email
            Text(
                text = uiState.user?.email ?: "",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            // Phone number (if available)
            if (!uiState.phoneNumber.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.Phone,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = uiState.phoneNumber,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Location (if available)
            if (!uiState.location.isNullOrBlank()) {
                Spacer(modifier = Modifier.height(4.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.LocationOn,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(Modifier.width(4.dp))
                    Text(
                        text = uiState.location,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }
        }
    }
}

// ══════════════════════════════════════════════════════════
// ✅ NEW: STATS CARD
// ══════════════════════════════════════════════════════════

@Composable
private fun StatsCard(uiState: ProfileUiState) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            // Completed Jobs
            StatItem(
                icon = Icons.Default.Work,
                value = uiState.completedJobs.toString(),
                label = stringResource(R.string.jobs)
            )

            // Divider
            VerticalDivider(
                modifier = Modifier.height(48.dp)
            )

            // Rating
            StatItem(
                icon = Icons.Default.Star,
                value = String.format("%.1f", uiState.rating),
                label = stringResource(R.string.rating)
            )

            // Divider
            VerticalDivider(
                modifier = Modifier.height(48.dp)
            )

            // Reviews
            StatItem(
                icon = Icons.Default.RateReview,
                value = uiState.totalReviews.toString(),
                label = stringResource(R.string.reviews)
            )
        }
    }
}

@Composable
private fun StatItem(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    value: String,
    label: String
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )
        Spacer(Modifier.height(4.dp))
        Text(
            text = value,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

// ══════════════════════════════════════════════════════════
// ✅ ENHANCED: LANGUAGE SELECTOR WITH FLAGS
// ══════════════════════════════════════════════════════════

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LanguageSelector(
    selectedLanguage: String,
    languages: List<Pair<String, String>>,
    expanded: Boolean,
    onExpandedChange: () -> Unit,
    onLanguageSelected: (String) -> Unit
) {
    ExposedDropdownMenuBox(
        expanded = expanded,
        onExpandedChange = { onExpandedChange() }
    ) {
        OutlinedTextField(
            value = languages.find { it.first == selectedLanguage }?.second ?: selectedLanguage,
            onValueChange = {},
            readOnly = true,
            label = { Text(stringResource(R.string.app_language)) },
            leadingIcon = {
                Icon(Icons.Filled.Language, contentDescription = null)
            },
            trailingIcon = {
                ExposedDropdownMenuDefaults.TrailingIcon(expanded)
            },
            modifier = Modifier
                .menuAnchor()
                .fillMaxWidth()
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { onExpandedChange() }
        ) {
            languages.forEach { (code, display) ->
                DropdownMenuItem(
                    text = { Text(display) },
                    onClick = { onLanguageSelected(code) },
                    leadingIcon = if (code == selectedLanguage) {
                        { Icon(Icons.Default.Check, contentDescription = null) }
                    } else null
                )
            }
        }
    }
}

// ══════════════════════════════════════════════════════════
// NOTIFICATION TOGGLE
// ══════════════════════════════════════════════════════════

@Composable
private fun NotificationToggle(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 14.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(Icons.Filled.Notifications, contentDescription = null)
                Spacer(modifier = Modifier.width(12.dp))
                Text(stringResource(R.string.push_notifications))
            }

            Switch(
                checked = checked,
                onCheckedChange = onCheckedChange
            )
        }
    }
}

// ══════════════════════════════════════════════════════════
// FIREBASE TOKEN BUTTON (DEBUG)
// ══════════════════════════════════════════════════════════

@Composable
private fun FirebaseTokenButton() {
    val context = LocalContext.current
    val firebaseAuth = FirebaseAuth.getInstance()

    Card(
        modifier = Modifier.fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer
        )
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Icon(
                    Icons.Default.Key,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onTertiaryContainer
                )
                Text(
                    stringResource(R.string.backend_testing),
                    style = MaterialTheme.typography.titleSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onTertiaryContainer
                )
            }

            val firebaseTokenLabel = stringResource(R.string.firebase_token)

            Button(
                onClick = {
                    firebaseAuth.currentUser?.getIdToken(false)
                        ?.addOnSuccessListener { result ->
                            val token = result.token ?: ""
                            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                            val clip = ClipData.newPlainText(firebaseTokenLabel, token)
                            clipboard.setPrimaryClip(clip)

                            android.util.Log.d("FIREBASE_TOKEN", "Token: $token")

                            Toast.makeText(
                                context,
                                "✅ Token copied!",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(Icons.Default.ContentCopy, null, Modifier.size(18.dp))
                Spacer(Modifier.width(8.dp))
                Text(stringResource(R.string.copy_firebase_token))
            }
        }
    }
}

// ══════════════════════════════════════════════════════════
// LOGOUT BUTTON
// ══════════════════════════════════════════════════════════

@Composable
private fun LogoutButton(onLogout: () -> Unit) {
    Button(
        onClick = onLogout,
        modifier = Modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.error
        )
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.Logout,
            contentDescription = null
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(stringResource(R.string.logout))
    }
}

// ══════════════════════════════════════════════════════════
// SECTION TITLE
// ══════════════════════════════════════════════════════════

@Composable
private fun SectionTitle(text: String) {
    Text(
        text = text,
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold
    )
}

// ══════════════════════════════════════════════════════════
// SKILLS SECTION
// ══════════════════════════════════════════════════════════

@Composable
private fun SkillsSection(skills: List<String>) {
    if (skills.isEmpty()) {
        Text(
            text = "No skills added yet",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        return
    }

    Card(
        modifier = Modifier.fillMaxWidth(),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            skills.forEach { skill ->
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        Icons.Default.CheckCircle,
                        contentDescription = null,
                        modifier = Modifier.size(16.dp),
                        tint = MaterialTheme.colorScheme.primary
                    )
                    Spacer(Modifier.width(8.dp))
                    Text(
                        text = skill,
                        style = MaterialTheme.typography.bodyMedium
                    )
                }
            }
        }
    }
}