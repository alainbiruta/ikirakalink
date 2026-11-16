package com.microcareer.match.presentation.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

// ─────────────────────────────────────────────────────────────────────────────
//  Color tokens  —  Apple HIG-inspired
//  Light accent : #0071E3  (Apple.com blue)
//  Dark accent  : #0A84FF  (iOS system blue)
//  Neutrals     : pure Apple gray ramp, no warm/cool tint
// ─────────────────────────────────────────────────────────────────────────────

private object Palette {
    // Grays (Apple system gray ramp)
    val Gray900 = Color(0xFF1C1C1E)   // primary text / dark bg
    val Gray800 = Color(0xFF2C2C2E)   // elevated dark surface
    val Gray700 = Color(0xFF3A3A3C)   // dark border / grouped bg
    val Gray600 = Color(0xFF48484A)   // secondary dark border
    val Gray400 = Color(0xFF8A8A8E)   // placeholder / muted text
    val Gray200 = Color(0xFFE2E5EA)   // light border
    val Gray100 = Color(0xFFF2F2F7)   // light surface / grouped bg
    val White   = Color(0xFFFFFFFF)

    // Apple Blue
    val Blue700  = Color(0xFF003F8A)  // deep press
    val Blue600  = Color(0xFF0058CC)  // dark container
    val Blue500  = Color(0xFF0071E3)  // light primary (apple.com)
    val Blue400  = Color(0xFF0A84FF)  // dark primary (iOS)
    val Blue300  = Color(0xFF34AADC)  // tonal secondary
    val Blue100  = Color(0xFFD6EAFF)  // light container

    // Semantic
    val Green     = Color(0xFF34C759)
    val GreenDark = Color(0xFF30D158)
    val GreenBg   = Color(0xFFE8F8EE)
    val GreenBgD  = Color(0xFF1A3528)

    val Red      = Color(0xFFFF3B30)
    val RedDark  = Color(0xFFFF453A)
    val RedBg    = Color(0xFFFEE8E8)
    val RedBgD   = Color(0xFF3A1515)

    val Orange     = Color(0xFFFF9F0A)
    val OrangeDark = Color(0xFFFFD60A)

    val Purple    = Color(0xFFAF52DE)
    val PurpleDark= Color(0xFFBF5AF2)
    val PurpleBg  = Color(0xFFF3E8FD)
    val PurpleBgD = Color(0xFF2D1A3E)
}

// ─────────────────────────────────────────────────────────────────────────────
//  Light scheme
// ─────────────────────────────────────────────────────────────────────────────
private val LightColorScheme = lightColorScheme(
    primary            = Palette.Blue500,
    onPrimary          = Palette.White,
    primaryContainer   = Palette.Blue100,
    onPrimaryContainer = Palette.Blue700,

    secondary            = Palette.Gray900,
    onSecondary          = Palette.White,
    secondaryContainer   = Palette.Gray100,
    onSecondaryContainer = Palette.Gray900,

    tertiary            = Palette.Purple,
    onTertiary          = Palette.White,
    tertiaryContainer   = Palette.PurpleBg,
    onTertiaryContainer = Color(0xFF6B1FA0),

    error            = Palette.Red,
    onError          = Palette.White,
    errorContainer   = Palette.RedBg,
    onErrorContainer = Color(0xFF9B1C1C),

    background   = Palette.White,
    onBackground = Palette.Gray900,

    surface          = Palette.White,
    onSurface        = Palette.Gray900,
    surfaceVariant   = Palette.Gray100,
    onSurfaceVariant = Palette.Gray400,

    outline        = Palette.Gray200,
    outlineVariant = Color(0xFFEEEFF1),

    inverseSurface   = Palette.Gray900,
    inverseOnSurface = Palette.Gray100,
    inversePrimary   = Palette.Blue400,

    scrim = Color(0x66000000)
)

// ─────────────────────────────────────────────────────────────────────────────
//  Dark scheme
// ─────────────────────────────────────────────────────────────────────────────
private val DarkColorScheme = darkColorScheme(
    primary            = Palette.Blue400,
    onPrimary          = Palette.White,
    primaryContainer   = Palette.Blue600,
    onPrimaryContainer = Palette.Blue100,

    secondary            = Palette.Gray100,
    onSecondary          = Palette.Gray900,
    secondaryContainer   = Palette.Gray700,
    onSecondaryContainer = Palette.Gray100,

    tertiary            = Palette.PurpleDark,
    onTertiary          = Palette.White,
    tertiaryContainer   = Palette.PurpleBgD,
    onTertiaryContainer = Color(0xFFDFAEF8),

    error            = Palette.RedDark,
    onError          = Palette.White,
    errorContainer   = Palette.RedBgD,
    onErrorContainer = Color(0xFFFFB3AE),

    background   = Palette.Gray900,
    onBackground = Palette.White,

    // Surface elevation steps mirror iOS layered materials
    surface          = Palette.Gray800,
    onSurface        = Color(0xFFF2F2F7),
    surfaceVariant   = Palette.Gray700,
    onSurfaceVariant = Palette.Gray400,

    outline        = Palette.Gray700,
    outlineVariant = Palette.Gray600,

    inverseSurface   = Palette.Gray100,
    inverseOnSurface = Palette.Gray900,
    inversePrimary   = Palette.Blue500,

    scrim = Color(0x99000000)
)

// ─────────────────────────────────────────────────────────────────────────────
//  Shapes  —  iOS-grade continuous rounding
//  Pill radius for small interactive elements, generous curves for cards
// ─────────────────────────────────────────────────────────────────────────────
private val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),    // chips, badges, text fields
    small      = RoundedCornerShape(12.dp),   // small buttons, input fields
    medium     = RoundedCornerShape(16.dp),   // cards, sheets, dialogs
    large      = RoundedCornerShape(20.dp),   // bottom sheets, large cards
    extraLarge = RoundedCornerShape(28.dp)    // modal sheets, hero cards
)

// ─────────────────────────────────────────────────────────────────────────────
//  Entry point
// ─────────────────────────────────────────────────────────────────────────────
@Composable
fun MicroCareerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color intentionally OFF — brand identity takes priority
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else      -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography  = Typography,
        shapes      = AppShapes,
        content     = content
    )
}