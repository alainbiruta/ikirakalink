package com.microcareer.match.presentation.auth

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.microcareer.match.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(
    viewModel: AuthViewModel,
    onNavigateToLogin: () -> Unit,
    onRegisterSuccess: () -> Unit
) {
    // ── STATE (unchanged) ─────────────────────────────────
    var fullName        by remember { mutableStateOf("") }
    var email           by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var confirmPassword by remember { mutableStateOf("") }
    var phone           by remember { mutableStateOf("") }
    var location        by remember { mutableStateOf("") }
    var companyName     by remember { mutableStateOf("") }
    var selectedRole    by remember { mutableStateOf("employee") }
    var passwordVisible by remember { mutableStateOf(false) }
    var passwordError   by remember { mutableStateOf<String?>(null) }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onRegisterSuccess()
    }

    // ── DESIGN TOKENS ─────────────────────────────────────
    val surface       = MaterialTheme.colorScheme.surface
    val fieldBg       = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.55f)
    val primaryColor  = MaterialTheme.colorScheme.primary
    val onSurface     = MaterialTheme.colorScheme.onSurface
    val onSurfaceVar  = MaterialTheme.colorScheme.onSurfaceVariant
    val errorColor    = MaterialTheme.colorScheme.error

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    Box(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)),
                        contentAlignment = Alignment.Center
                    ) {
                        IconButton(
                            onClick = onNavigateToLogin,
                            modifier = Modifier.size(36.dp)
                        ) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.size(18.dp),
                                tint = onSurface
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Transparent
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(0.dp)
        ) {

            // ── HERO HEADING ──────────────────────────────
            Spacer(Modifier.height(4.dp))
            Text(
                text       = stringResource(R.string.create_account),
                style      = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                fontSize   = 30.sp,
                color      = onSurface,
                letterSpacing = (-0.5).sp
            )
            Spacer(Modifier.height(4.dp))
            Text(
                text  = stringResource(R.string.njyewe_ndi),
                style = MaterialTheme.typography.bodyMedium,
                color = onSurfaceVar
            )
            Spacer(Modifier.height(24.dp))

            // ── ROLE SELECTOR — Apple segmented style ─────
            AppleSegmentedControl(
                selectedRole    = selectedRole,
                onRoleSelected  = { selectedRole = it },
                employeeLabel   = stringResource(R.string.job_seeker),
                employerLabel   = stringResource(R.string.employer)
            )

            Spacer(Modifier.height(28.dp))

            // ── FORM SECTION LABEL ────────────────────────
            SectionLabel(stringResource(R.string.personal_info))
            Spacer(Modifier.height(10.dp))

            // ── GROUPED FIELDS CARD ───────────────────────
            AppleFieldGroup {
                AppleField(
                    value         = fullName,
                    onValueChange = { fullName = it },
                    label         = stringResource(R.string.full_name),
                    icon          = Icons.Default.Person,
                    fieldBg       = fieldBg,
                    isLast        = false
                )
                AppleDivider()
                AppleField(
                    value           = email,
                    onValueChange   = { email = it },
                    label           = stringResource(R.string.email),
                    icon            = Icons.Default.Email,
                    fieldBg         = fieldBg,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Email),
                    isLast          = false
                )
                AppleDivider()
                AppleField(
                    value           = phone,
                    onValueChange   = { phone = it },
                    label           = stringResource(R.string.phone_number_optional),
                    icon            = Icons.Default.Phone,
                    fieldBg         = fieldBg,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                    isLast          = false
                )
                AppleDivider()
                AppleField(
                    value         = location,
                    onValueChange = { location = it },
                    label         = stringResource(R.string.location_e_g_kigali_rwanda),
                    icon          = Icons.Default.LocationOn,
                    fieldBg       = fieldBg,
                    isLast        = true
                )
            }

            // ── EMPLOYER ONLY ─────────────────────────────
            if (selectedRole == "employer") {
                Spacer(Modifier.height(20.dp))
                SectionLabel(stringResource(R.string.company1))
                Spacer(Modifier.height(10.dp))
                AppleFieldGroup {
                    AppleField(
                        value         = companyName,
                        onValueChange = { companyName = it },
                        label         = stringResource(R.string.company_name),
                        icon          = Icons.Default.Business,
                        fieldBg       = fieldBg,
                        isLast        = true
                    )
                }
            }

            // ── PASSWORD ──────────────────────────────────
            Spacer(Modifier.height(20.dp))
            SectionLabel(stringResource(R.string.security1))
            Spacer(Modifier.height(10.dp))

            AppleFieldGroup {
                ApplePasswordField(
                    value            = password,
                    onValueChange    = { password = it; passwordError = null },
                    label            = stringResource(R.string.password),
                    passwordVisible  = passwordVisible,
                    onToggleVisible  = { passwordVisible = !passwordVisible },
                    fieldBg          = fieldBg,
                    isLast           = false
                )
                AppleDivider()
                ApplePasswordField(
                    value           = confirmPassword,
                    onValueChange   = { confirmPassword = it; passwordError = null },
                    label           = stringResource(R.string.confirm_password),
                    passwordVisible = false,
                    onToggleVisible = {},
                    showToggle      = false,
                    fieldBg         = fieldBg,
                    isError         = passwordError != null,
                    isLast          = true
                )
            }

            // ── PASSWORD ERROR ────────────────────────────
            passwordError?.let { err ->
                Spacer(Modifier.height(8.dp))
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(horizontal = 4.dp)
                ) {
                    Icon(
                        Icons.Default.Error,
                        contentDescription = null,
                        tint     = errorColor,
                        modifier = Modifier.size(14.dp)
                    )
                    Spacer(Modifier.width(6.dp))
                    Text(
                        text  = err,
                        color = errorColor,
                        style = MaterialTheme.typography.bodySmall
                    )
                }
            }

            // ── API ERROR ─────────────────────────────────
            uiState.error?.let { err ->
                Spacer(Modifier.height(12.dp))
                Surface(
                    shape  = RoundedCornerShape(14.dp),
                    color  = MaterialTheme.colorScheme.errorContainer,
                    tonalElevation = 0.dp,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.padding(14.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            Icons.Default.Error,
                            contentDescription = null,
                            tint     = errorColor,
                            modifier = Modifier.size(18.dp)
                        )
                        Spacer(Modifier.width(10.dp))
                        Text(
                            text  = err,
                            color = MaterialTheme.colorScheme.onErrorContainer,
                            style = MaterialTheme.typography.bodySmall
                        )
                    }
                }
            }

            // ── SUBMIT ────────────────────────────────────
            Spacer(Modifier.height(28.dp))
            Button(
                onClick = {
                    if (password != confirmPassword) {
                        passwordError = "Passwords do not match"
                        return@Button
                    }
                    if (password.length < 8) {
                        passwordError = "Password must be at least 8 characters"
                        return@Button
                    }
                    viewModel.register(
                        email       = email.trim(),
                        password    = password,
                        fullName    = fullName.trim(),
                        role        = selectedRole,
                        phoneNumber = phone.trim().ifBlank { null },
                        location    = location.trim().ifBlank { null },
                        companyName = companyName.trim().ifBlank { null }
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                enabled = !uiState.isLoading &&
                        fullName.isNotBlank() &&
                        email.isNotBlank() &&
                        password.isNotBlank() &&
                        confirmPassword.isNotBlank(),
                shape  = RoundedCornerShape(16.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor         = primaryColor,
                    disabledContainerColor = primaryColor.copy(alpha = 0.35f)
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 0.dp)
            ) {
                if (uiState.isLoading) {
                    CircularProgressIndicator(
                        modifier = Modifier.size(22.dp),
                        color    = MaterialTheme.colorScheme.onPrimary,
                        strokeWidth = 2.dp
                    )
                } else {
                    Text(
                        text       = stringResource(R.string.create_account),
                        fontWeight = FontWeight.SemiBold,
                        fontSize   = 16.sp,
                        letterSpacing = (-0.2).sp
                    )
                }
            }

            // ── SIGN IN LINK ──────────────────────────────
            Spacer(Modifier.height(12.dp))
            TextButton(
                onClick  = onNavigateToLogin,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            ) {
                Text(
                    text  = stringResource(R.string.already_have_an_account_sign_in),
                    style = MaterialTheme.typography.bodyMedium,
                    color = primaryColor
                )
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}


// ─────────────────────────────────────────────────────────
// APPLE SEGMENTED CONTROL
// Replaces the two bordered RoleCard composables.
// Logic output (selectedRole string) is identical.
// ─────────────────────────────────────────────────────────

@Composable
private fun AppleSegmentedControl(
    selectedRole   : String,
    onRoleSelected : (String) -> Unit,
    employeeLabel  : String,
    employerLabel  : String
) {
    val trackColor  = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.7f)
    val thumbColor  = MaterialTheme.colorScheme.surface
    val activeText  = MaterialTheme.colorScheme.onSurface
    val inactiveText = MaterialTheme.colorScheme.onSurfaceVariant

    Surface(
        shape  = RoundedCornerShape(12.dp),
        color  = trackColor,
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(3.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            listOf("employee" to employeeLabel, "employer" to employerLabel).forEach { (role, label) ->
                val isSelected = selectedRole == role
                val textColor  by animateColorAsState(
                    if (isSelected) activeText else inactiveText,
                    animationSpec = tween(200), label = "seg_text_$role"
                )

                Surface(
                    onClick      = { onRoleSelected(role) },
                    modifier     = Modifier
                        .weight(1f)
                        .fillMaxHeight(),
                    shape        = RoundedCornerShape(10.dp),
                    color        = if (isSelected) thumbColor else Color.Transparent,
                    shadowElevation = if (isSelected) 1.dp else 0.dp,
                    tonalElevation  = 0.dp
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Text(
                            text       = label,
                            color      = textColor,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal,
                            fontSize   = 14.sp,
                            letterSpacing = (-0.1).sp
                        )
                    }
                }
            }
        }
    }
}


// ─────────────────────────────────────────────────────────
// SECTION LABEL
// ─────────────────────────────────────────────────────────

@Composable
private fun SectionLabel(text: String) {
    Text(
        text      = text.uppercase(),
        style     = MaterialTheme.typography.labelSmall,
        color     = MaterialTheme.colorScheme.onSurfaceVariant,
        fontWeight = FontWeight.Medium,
        letterSpacing = 0.8.sp,
        modifier  = Modifier.padding(horizontal = 4.dp)
    )
}


// ─────────────────────────────────────────────────────────
// GROUPED FIELD CARD (iOS settings-style card wrapper)
// ─────────────────────────────────────────────────────────

@Composable
private fun AppleFieldGroup(content: @Composable ColumnScope.() -> Unit) {
    Surface(
        shape  = RoundedCornerShape(14.dp),
        color  = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        tonalElevation = 0.dp,
        modifier = Modifier.fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            content  = content
        )
    }
}


// ─────────────────────────────────────────────────────────
// APPLE FIELD — borderless inset style
// ─────────────────────────────────────────────────────────

@Composable
private fun AppleField(
    value           : String,
    onValueChange   : (String) -> Unit,
    label           : String,
    icon            : androidx.compose.ui.graphics.vector.ImageVector,
    fieldBg         : Color,
    isLast          : Boolean,
    keyboardOptions : KeyboardOptions = KeyboardOptions.Default
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector        = icon,
            contentDescription = null,
            modifier           = Modifier.size(18.dp),
            tint               = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(12.dp))
        TextField(
            value           = value,
            onValueChange   = onValueChange,
            placeholder     = { Text(label, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
            singleLine      = true,
            modifier        = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            keyboardOptions = keyboardOptions,
            colors          = TextFieldDefaults.colors(
                focusedContainerColor   = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor  = Color.Transparent,
                focusedIndicatorColor   = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor        = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor      = MaterialTheme.colorScheme.onSurface
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp)
        )
    }
}


// ─────────────────────────────────────────────────────────
// APPLE PASSWORD FIELD
// ─────────────────────────────────────────────────────────

@Composable
private fun ApplePasswordField(
    value           : String,
    onValueChange   : (String) -> Unit,
    label           : String,
    passwordVisible : Boolean,
    onToggleVisible : () -> Unit,
    fieldBg         : Color,
    isLast          : Boolean,
    showToggle      : Boolean = true,
    isError         : Boolean = false
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(52.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector        = Icons.Default.Lock,
            contentDescription = null,
            modifier           = Modifier.size(18.dp),
            tint               = if (isError)
                MaterialTheme.colorScheme.error
            else
                MaterialTheme.colorScheme.onSurfaceVariant
        )
        Spacer(Modifier.width(12.dp))
        TextField(
            value                = value,
            onValueChange        = onValueChange,
            placeholder          = { Text(label, fontSize = 15.sp, color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f)) },
            singleLine           = true,
            modifier             = Modifier
                .weight(1f)
                .fillMaxHeight(),
            visualTransformation = if (passwordVisible)
                VisualTransformation.None else PasswordVisualTransformation(),
            keyboardOptions      = KeyboardOptions(keyboardType = KeyboardType.Password),
            isError              = isError,
            trailingIcon         = if (showToggle) ({
                IconButton(
                    onClick   = onToggleVisible,
                    modifier  = Modifier.size(36.dp)
                ) {
                    Icon(
                        imageVector        = if (passwordVisible) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                        contentDescription = null,
                        modifier           = Modifier.size(18.dp),
                        tint               = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }) else null,
            colors = TextFieldDefaults.colors(
                focusedContainerColor   = Color.Transparent,
                unfocusedContainerColor = Color.Transparent,
                disabledContainerColor  = Color.Transparent,
                focusedIndicatorColor   = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                errorIndicatorColor     = Color.Transparent,
                focusedTextColor        = MaterialTheme.colorScheme.onSurface,
                unfocusedTextColor      = MaterialTheme.colorScheme.onSurface
            ),
            textStyle = MaterialTheme.typography.bodyLarge.copy(fontSize = 15.sp)
        )
    }
}


// ─────────────────────────────────────────────────────────
// THIN DIVIDER between grouped fields
// ─────────────────────────────────────────────────────────

@Composable
private fun AppleDivider() {
    HorizontalDivider(
        modifier  = Modifier.padding(start = 30.dp),
        thickness = 0.5.dp,
        color     = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f)
    )
}