package com.example.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

enum class ThemeMode {
    SYSTEM,
    LIGHT,
    DARK
}

private val LightColorScheme = lightColorScheme(
    primary = EmeraldDeep,
    onPrimary = Color.White,
    primaryContainer = EmeraldSoftContainer,
    onPrimaryContainer = EmeraldOnContainer,
    secondary = GoldBurnished,
    onSecondary = Color.White,
    secondaryContainer = GoldSoftContainer,
    onSecondaryContainer = GoldOnContainer,
    tertiary = EmeraldVibrant,
    onTertiary = Color.White,
    tertiaryContainer = EmeraldSoftContainer,
    onTertiaryContainer = EmeraldOnContainer,
    background = IvoryBackground,
    onBackground = InkForest,
    surface = IvorySurface,
    onSurface = InkForest,
    surfaceVariant = IvorySurfaceVariant,
    onSurfaceVariant = InkMutedSage,
    outline = OutlineWarm,
    error = ErrorCrimson,
    errorContainer = ErrorContainerLight
)

private val DarkColorScheme = darkColorScheme(
    primary = NightEmeraldPrimary,
    onPrimary = NightEmeraldOnPrimary,
    primaryContainer = NightEmeraldContainer,
    onPrimaryContainer = Color(0xFFC2F5DC),
    secondary = NightGoldPrimary,
    onSecondary = Color(0xFF2B1F03),
    secondaryContainer = NightGoldContainer,
    onSecondaryContainer = Color(0xFFF9E4AE),
    tertiary = EmeraldVibrant,
    onTertiary = Color.White,
    background = NightEmeraldBg,
    onBackground = NightIvoryText,
    surface = NightEmeraldSurface,
    onSurface = NightIvoryText,
    surfaceVariant = NightEmeraldSurfaceHigh,
    onSurfaceVariant = NightMutedText,
    outline = NightOutline,
    error = Color(0xFFF2B8B5),
    errorContainer = Color(0xFF8C1D18)
)

@Composable
fun HafizIslamicHubTheme(
    themeMode: ThemeMode = ThemeMode.SYSTEM,
    content: @Composable () -> Unit
) {
    val darkTheme = when (themeMode) {
        ThemeMode.SYSTEM -> isSystemInDarkTheme()
        ThemeMode.LIGHT -> false
        ThemeMode.DARK -> true
    }

    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            if (window != null) {
                WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = !darkTheme
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
