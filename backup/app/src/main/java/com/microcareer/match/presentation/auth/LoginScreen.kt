package com.microcareer.match.presentation.auth

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.PathNode
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.microcareer.match.R

@Composable
fun LoginScreen(
    viewModel: AuthViewModel = hiltViewModel(),
    onNavigateToRegister: () -> Unit,
    onLoginSuccess: () -> Unit = {}
) {
    val focusManager = LocalFocusManager.current
    val colors       = MaterialTheme.colorScheme

    var identifier      by remember { mutableStateOf("") }
    var password        by remember { mutableStateOf("") }
    var passwordVisible by remember { mutableStateOf(false) }

    val isPhone = identifier.trimStart().let {
        it.startsWith("+") || it.firstOrNull()?.isDigit() == true
    }

    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(uiState.isLoggedIn) {
        if (uiState.isLoggedIn) onLoginSuccess()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(colors.background)
            .systemBarsPadding()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 28.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Spacer(Modifier.height(48.dp))

            // ── App logo mark — the K-diamond from ic_launcher_foreground ─
            Box(
                modifier = Modifier
                    .size(72.dp)
                    .clip(RoundedCornerShape(18.dp))
                    .background(colors.primary),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painter           = rememberVectorPainter(KirakaLogoVector),
                    contentDescription = stringResource(R.string.app_name),
                    modifier           = Modifier.size(44.dp),
                    tint               = colors.onPrimary
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text          = stringResource(R.string.app_name),
                fontSize      = 28.sp,
                fontWeight    = FontWeight.Bold,
                color         = colors.onBackground,
                letterSpacing = (-0.5).sp
            )

            Spacer(Modifier.height(6.dp))

            Text(
                text       = stringResource(R.string.find_opportunities),
                fontSize   = 14.sp,
                color      = colors.onSurfaceVariant,
                textAlign  = TextAlign.Center,
                lineHeight = 20.sp
            )

            Spacer(Modifier.height(40.dp))

            // ── Identifier field ──────────────────────────────────────────
            AppleTextField(
                value         = identifier,
                onValueChange = { identifier = it },
                label         = stringResource(R.string.email_or_phone).uppercase(),
                placeholder   = stringResource(R.string.email_or_phone),
                leadingIcon   = {
                    Icon(
                        imageVector        = if (isPhone) Icons.Default.Phone else Icons.Default.Email,
                        contentDescription = null,
                        modifier           = Modifier.size(16.dp)
                    )
                },
                keyboardOptions = KeyboardOptions(
                    keyboardType = if (isPhone) KeyboardType.Phone else KeyboardType.Email,
                    imeAction    = ImeAction.Next
                ),
                keyboardActions = KeyboardActions(
                    onNext = { focusManager.moveFocus(FocusDirection.Down) }
                )
            )

            Spacer(Modifier.height(12.dp))

            // ── Password field ────────────────────────────────────────────
            AppleTextField(
                value         = password,
                onValueChange = { password = it },
                label         = stringResource(R.string.password).uppercase(),
                placeholder   = stringResource(R.string.password),
                leadingIcon   = {
                    Icon(
                        imageVector        = Icons.Default.Lock,
                        contentDescription = null,
                        modifier           = Modifier.size(16.dp)
                    )
                },
                trailingIcon = {
                    IconButton(
                        onClick  = { passwordVisible = !passwordVisible },
                        modifier = Modifier.size(36.dp)
                    ) {
                        Icon(
                            imageVector        = if (passwordVisible)
                                Icons.Default.Visibility else Icons.Default.VisibilityOff,
                            contentDescription = if (passwordVisible) "Hide password" else "Show password",
                            modifier           = Modifier.size(16.dp)
                        )
                    }
                },
                visualTransformation = if (passwordVisible)
                    VisualTransformation.None else PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Password,
                    imeAction    = ImeAction.Done
                ),
                keyboardActions = KeyboardActions(
                    onDone = {
                        focusManager.clearFocus()
                        viewModel.login(identifier.trim(), password)
                    }
                )
            )

            // ── Forgot password ───────────────────────────────────────────
            Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
                TextButton(
                    onClick        = { /* TODO */ },
                    contentPadding = PaddingValues(horizontal = 0.dp, vertical = 4.dp)
                ) {
                    Text(
                        text       = stringResource(R.string.forgot_password),
                        fontSize   = 13.sp,
                        color      = colors.primary,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            Spacer(Modifier.height(20.dp))

            // ── Primary CTA ───────────────────────────────────────────────
            Button(
                onClick  = { viewModel.login(identifier.trim(), password) },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape    = CircleShape,
                colors   = ButtonDefaults.buttonColors(
                    containerColor         = colors.primary,
                    contentColor           = colors.onPrimary,
                    disabledContainerColor = colors.primary.copy(alpha = 0.45f),
                    disabledContentColor   = colors.onPrimary.copy(alpha = 0.70f)
                ),
                enabled = !uiState.isLoading
            ) {
                AnimatedContent(targetState = uiState.isLoading, label = "login_btn") { loading ->
                    if (loading) {
                        CircularProgressIndicator(
                            modifier    = Modifier.size(20.dp),
                            color       = colors.onPrimary,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text(
                            text          = stringResource(R.string.sign_in),
                            fontSize      = 16.sp,
                            fontWeight    = FontWeight.SemiBold,
                            letterSpacing = 0.1.sp
                        )
                    }
                }
            }

            // ── Error message ─────────────────────────────────────────────
            AnimatedVisibility(
                visible = uiState.error != null,
                enter   = fadeIn() + expandVertically(),
                exit    = fadeOut() + shrinkVertically()
            ) {
                uiState.error?.let { errorMessage ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 12.dp)
                            .clip(RoundedCornerShape(12.dp))
                            .background(colors.errorContainer)
                            .padding(horizontal = 14.dp, vertical = 10.dp),
                        verticalAlignment     = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            Icons.Default.ErrorOutline,
                            contentDescription = null,
                            tint               = colors.error,
                            modifier           = Modifier.size(15.dp)
                        )
                        Text(
                            text       = errorMessage,
                            color      = colors.onErrorContainer,
                            fontSize   = 13.sp,
                            lineHeight = 18.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            // ── Divider ───────────────────────────────────────────────────
            Row(
                modifier          = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                HorizontalDivider(modifier = Modifier.weight(1f), color = colors.onSurfaceVariant, thickness = 0.5.dp)
                Text(stringResource(R.string.or), modifier = Modifier.padding(horizontal = 14.dp), fontSize = 13.sp, color = colors.onSurfaceVariant)
                HorizontalDivider(modifier = Modifier.weight(1f), color = colors.onSurfaceVariant, thickness = 0.5.dp)
            }

            Spacer(Modifier.height(16.dp))

            // ── Secondary CTA ─────────────────────────────────────────────
            OutlinedButton(
                onClick  = onNavigateToRegister,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(52.dp),
                shape    = CircleShape,
                colors   = ButtonDefaults.outlinedButtonColors(contentColor = colors.onSurface),
                border   = androidx.compose.foundation.BorderStroke(0.5.dp, colors.outline)
            ) {
                Text(
                    text       = stringResource(R.string.create_new_account),
                    fontSize   = 15.sp,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(Modifier.height(32.dp))

            Text(
                text      = stringResource(R.string.app_name) + " · Urubyiruko imbaraga z'igihugu",
                fontSize  = 14.sp,
                color     = colors.onSurfaceVariant,
                textAlign = TextAlign.Center
            )

            Spacer(Modifier.height(32.dp))
        }
    }
}

// ─── Apple-style field ────────────────────────────────────────────────────────
@Composable
private fun AppleTextField(
    value                : String,
    onValueChange        : (String) -> Unit,
    label                : String,
    placeholder          : String,
    modifier             : Modifier = Modifier,
    leadingIcon          : (@Composable () -> Unit)? = null,
    trailingIcon         : (@Composable () -> Unit)? = null,
    visualTransformation : VisualTransformation = VisualTransformation.None,
    keyboardOptions      : KeyboardOptions = KeyboardOptions.Default,
    keyboardActions      : KeyboardActions = KeyboardActions.Default
) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text          = label,
            fontSize      = 11.sp,
            fontWeight    = FontWeight.SemiBold,
            color         = colors.onSurfaceVariant,
            letterSpacing = 0.06.sp,
            modifier      = Modifier.padding(start = 4.dp, bottom = 5.dp)
        )
        OutlinedTextField(
            value                = value,
            onValueChange        = onValueChange,
            placeholder          = {
                // Placeholder sits INSIDE the field, muted
                Text(
                    text     = placeholder,
                    fontSize = 14.sp,
                    color    = colors.onSurfaceVariant.copy(alpha = 0.45f)
                )
            },
            leadingIcon          = leadingIcon,
            trailingIcon         = trailingIcon,
            singleLine           = true,
            visualTransformation = visualTransformation,
            keyboardOptions      = keyboardOptions,
            keyboardActions      = keyboardActions,
            modifier             = Modifier
                .fillMaxWidth()
                .height(52.dp),
            shape  = RoundedCornerShape(14.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor         = colors.primary,
                unfocusedBorderColor       = colors.outline,
                focusedContainerColor      = colors.surface,
                unfocusedContainerColor    = colors.surfaceVariant,
                focusedTextColor           = colors.onSurface,
                unfocusedTextColor         = colors.onSurface,
                cursorColor                = colors.primary,
                focusedLeadingIconColor    = colors.primary,
                unfocusedLeadingIconColor  = colors.onSurfaceVariant,
                focusedTrailingIconColor   = colors.onSurfaceVariant,
                unfocusedTrailingIconColor = colors.onSurfaceVariant
            ),
            textStyle = TextStyle(fontSize = 15.sp, fontWeight = FontWeight.Normal, letterSpacing = 0.sp)
        )
    }
}

// ─── K-diamond logo vector (matches ic_launcher_foreground.xml) ───────────────
//
// Paths are the centred, scaled coords from the adaptive icon we built:
//   Spine        : M46.7,28.1 → 79.9
//   Arm upper-R  : M46.7,54  → L64.5,36.2
//   Arm lower-R  : M46.7,54  → L64.5,71.8
//   Centre diamond (hollow), top, bottom, upper-R, lower-R endpoint diamonds
//
// viewportWidth/Height = 108 (same as the XML)
private val KirakaLogoVector: ImageVector by lazy {
    ImageVector.Builder(
        defaultWidth      = 108.dp,
        defaultHeight     = 108.dp,
        viewportWidth     = 108f,
        viewportHeight    = 108f
    ).apply {

        // Vertical spine
        addPath(
            pathData = listOf(
                PathNode.MoveTo(46.7f, 28.1f),
                PathNode.LineTo(46.7f, 79.9f)
            ),
            stroke        = SolidColor(androidx.compose.ui.graphics.Color.White),
            strokeLineWidth = 2.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round,
            fill          = SolidColor(androidx.compose.ui.graphics.Color.Transparent)
        )

        // Upper-right arm
        addPath(
            pathData = listOf(
                PathNode.MoveTo(46.7f, 54f),
                PathNode.LineTo(64.5f, 36.2f)
            ),
            stroke        = SolidColor(androidx.compose.ui.graphics.Color.White),
            strokeLineWidth = 2.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round,
            fill          = SolidColor(androidx.compose.ui.graphics.Color.Transparent)
        )

        // Lower-right arm
        addPath(
            pathData = listOf(
                PathNode.MoveTo(46.7f, 54f),
                PathNode.LineTo(64.5f, 71.8f)
            ),
            stroke        = SolidColor(androidx.compose.ui.graphics.Color.White),
            strokeLineWidth = 2.0f,
            strokeLineCap = androidx.compose.ui.graphics.StrokeCap.Round,
            fill          = SolidColor(androidx.compose.ui.graphics.Color.Transparent)
        )

        // Centre diamond — outer (hollow ring: white outer, bg cut-out handled by stacking)
        addPath(
            pathData = listOf(
                PathNode.MoveTo(38.6f, 54f),
                PathNode.LineTo(46.7f, 45.9f),
                PathNode.LineTo(54.8f, 54f),
                PathNode.LineTo(46.7f, 62.1f),
                PathNode.Close
            ),
            fill = SolidColor(androidx.compose.ui.graphics.Color.White)
        )

        // Centre diamond — inner cut-out (use primary color to punch the hole)
        addPath(
            pathData = listOf(
                PathNode.MoveTo(42.3f, 54f),
                PathNode.LineTo(46.7f, 49.6f),
                PathNode.LineTo(51.1f, 54f),
                PathNode.LineTo(46.7f, 58.4f),
                PathNode.Close
            ),
            // This will be tinted by the Icon tint — we use Color.Unspecified so
            // the caller's tint does NOT override this path. Instead we rely on
            // the box background bleeding through. For a simpler approach we
            // just fill with the primary color (passed as tint to the Icon composable).
            fill = SolidColor(androidx.compose.ui.graphics.Color.Transparent)
        )

        // Top diamond
        addPath(
            pathData = listOf(
                PathNode.MoveTo(41.8f, 28.9f),
                PathNode.LineTo(46.7f, 24.0f),
                PathNode.LineTo(51.6f, 28.9f),
                PathNode.LineTo(46.7f, 33.8f),
                PathNode.Close
            ),
            fill = SolidColor(androidx.compose.ui.graphics.Color.White)
        )

        // Bottom diamond
        addPath(
            pathData = listOf(
                PathNode.MoveTo(41.8f, 79.1f),
                PathNode.LineTo(46.7f, 74.3f),
                PathNode.LineTo(51.6f, 79.1f),
                PathNode.LineTo(46.7f, 84.0f),
                PathNode.Close
            ),
            fill = SolidColor(androidx.compose.ui.graphics.Color.White)
        )

        // Upper-right diamond
        addPath(
            pathData = listOf(
                PathNode.MoveTo(59.7f, 36.2f),
                PathNode.LineTo(64.5f, 31.3f),
                PathNode.LineTo(69.4f, 36.2f),
                PathNode.LineTo(64.5f, 41.0f),
                PathNode.Close
            ),
            fill = SolidColor(androidx.compose.ui.graphics.Color.White)
        )

        // Lower-right diamond
        addPath(
            pathData = listOf(
                PathNode.MoveTo(59.7f, 71.8f),
                PathNode.LineTo(64.5f, 67.0f),
                PathNode.LineTo(69.4f, 71.8f),
                PathNode.LineTo(64.5f, 76.7f),
                PathNode.Close
            ),
            fill = SolidColor(androidx.compose.ui.graphics.Color.White)
        )

    }.build()
}