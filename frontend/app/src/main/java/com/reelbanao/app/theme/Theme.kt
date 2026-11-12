package com.reelbanao.app.theme

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

@Composable
fun ReelBanaoTheme(
    themeMode: AppThemeMode = ThemeManager.currentTheme,
    content: @Composable () -> Unit
) {
    val colorScheme = if (themeMode.isDark) {
        darkColorScheme(
            primary = themeMode.primaryColor,
            onPrimary = TextWhite,
            secondary = themeMode.accentColor,
            onSecondary = themeMode.backgroundColor,
            tertiary = AccentPink,
            background = themeMode.backgroundColor,
            onBackground = TextWhite,
            surface = themeMode.surfaceColor,
            onSurface = TextWhite,
            surfaceVariant = themeMode.cardColor,
            onSurfaceVariant = TextGray,
            outline = CardStroke
        )
    } else {
        lightColorScheme(
            primary = themeMode.primaryColor,
            onPrimary = TextWhite,
            secondary = themeMode.accentColor,
            onSecondary = TextWhite,
            tertiary = AccentPink,
            background = themeMode.backgroundColor,
            onBackground = Color(0xFF14151B),
            surface = themeMode.surfaceColor,
            onSurface = Color(0xFF14151B),
            surfaceVariant = themeMode.cardColor,
            onSurfaceVariant = Color(0xFF5A5D6E),
            outline = Color(0xFFD6D8E2)
        )
    }

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as? Activity)?.window
            window?.let {
                it.statusBarColor = themeMode.backgroundColor.toArgb()
                it.navigationBarColor = themeMode.backgroundColor.toArgb()
                WindowCompat.getInsetsController(it, view).apply {
                    isAppearanceLightStatusBars = !themeMode.isDark
                    isAppearanceLightNavigationBars = !themeMode.isDark
                }
            }
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
