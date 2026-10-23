package com.penny.app.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.shape.RoundedCornerShape
import com.penny.app.data.PennyThemeMode

val AuroraViolet = Color(0xFF7547FF)
val AuroraMagenta = Color(0xFFE54BBE)
val AuroraCyan = Color(0xFF34D9EA)
val MoneyGreen = Color(0xFF0A8F68)
val DarkMoneyGreen = Color(0xFF70E3BC)

private val LightColors = lightColorScheme(
    primary = Color(0xFF6540D7),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFEDE6FF),
    onPrimaryContainer = Color(0xFF241054),
    secondary = Color(0xFF9A347C),
    onSecondary = Color.White,
    secondaryContainer = Color(0xFFFFD8EF),
    onSecondaryContainer = Color(0xFF3D002E),
    tertiary = Color(0xFF006875),
    onTertiary = Color.White,
    tertiaryContainer = Color(0xFF9CF0FF),
    onTertiaryContainer = Color(0xFF001F24),
    background = Color(0xFFF8F6FF),
    onBackground = Color(0xFF1C1822),
    surface = Color(0xFFFCF9FF),
    onSurface = Color(0xFF1C1822),
    surfaceVariant = Color(0xFFEAE5F0),
    onSurfaceVariant = Color(0xFF645E6C),
    outline = Color(0xFF837B8B),
    outlineVariant = Color(0xFFD0C8D7),
    error = Color(0xFFBA1A1A),
    onError = Color.White,
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFCDBDFF),
    onPrimary = Color(0xFF35127F),
    primaryContainer = Color(0xFF4D28A5),
    onPrimaryContainer = Color(0xFFE8DEFF),
    secondary = Color(0xFFFFACE0),
    onSecondary = Color(0xFF5F104C),
    secondaryContainer = Color(0xFF7B2864),
    onSecondaryContainer = Color(0xFFFFD8EF),
    tertiary = Color(0xFF4ED8EA),
    onTertiary = Color(0xFF00363E),
    tertiaryContainer = Color(0xFF004E59),
    onTertiaryContainer = Color(0xFF9CF0FF),
    background = Color(0xFF0D0914),
    onBackground = Color(0xFFEAE2EF),
    surface = Color(0xFF14101C),
    onSurface = Color(0xFFEAE2EF),
    surfaceVariant = Color(0xFF332D3B),
    onSurfaceVariant = Color(0xFFCEC5D2),
    outline = Color(0xFF978E9C),
    outlineVariant = Color(0xFF49414F),
    error = Color(0xFFFFB4AB),
    onError = Color(0xFF690005),
)

private val PennyTypography = Typography(
    displayLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 52.sp, lineHeight = 56.sp, letterSpacing = (-1.2).sp),
    displayMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 40.sp, lineHeight = 44.sp, letterSpacing = (-0.8).sp),
    headlineLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Bold, fontSize = 32.sp, lineHeight = 38.sp, letterSpacing = (-0.5).sp),
    headlineMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 26.sp, lineHeight = 32.sp, letterSpacing = (-0.3).sp),
    titleLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 22.sp, lineHeight = 28.sp),
    titleMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 16.sp, lineHeight = 22.sp),
    bodyLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 16.sp, lineHeight = 24.sp),
    bodyMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 14.sp, lineHeight = 20.sp),
    bodySmall = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Normal, fontSize = 12.sp, lineHeight = 17.sp),
    labelLarge = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.SemiBold, fontSize = 14.sp, lineHeight = 20.sp),
    labelMedium = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 12.sp, lineHeight = 16.sp),
    labelSmall = TextStyle(fontFamily = FontFamily.SansSerif, fontWeight = FontWeight.Medium, fontSize = 11.sp, lineHeight = 14.sp),
)

private val PennyShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(24.dp),
    extraLarge = RoundedCornerShape(32.dp),
)

@Composable
fun PennyTheme(themeMode: PennyThemeMode, content: @Composable () -> Unit) {
    val dark = when (themeMode) {
        PennyThemeMode.SYSTEM -> isSystemInDarkTheme()
        PennyThemeMode.LIGHT -> false
        PennyThemeMode.DARK -> true
    }
    MaterialTheme(
        colorScheme = if (dark) DarkColors else LightColors,
        typography = PennyTypography,
        shapes = PennyShapes,
        content = content,
    )
}
