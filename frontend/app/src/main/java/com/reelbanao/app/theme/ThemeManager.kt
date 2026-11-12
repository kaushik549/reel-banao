package com.reelbanao.app.theme

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

enum class AppThemeMode(
    val id: String,
    val title: String,
    val hindiTitle: String,
    val primaryColor: Color,
    val accentColor: Color,
    val backgroundColor: Color,
    val surfaceColor: Color,
    val cardColor: Color,
    val isDark: Boolean = true
) {
    OLED_PURPLE(
        id = "oled_purple",
        title = "CapCut Purple (Default)",
        hindiTitle = "ओलेड डार्क पर्पल",
        primaryColor = Color(0xFF7052FF),
        accentColor = Color(0xFF00E5FF),
        backgroundColor = Color(0xFF0F1015),
        surfaceColor = Color(0xFF171922),
        cardColor = Color(0xFF212430),
        isDark = true
    ),
    CYBER_CYAN(
        id = "cyber_cyan",
        title = "Cyber Neon",
        hindiTitle = "साइबर नियॉन स्यान",
        primaryColor = Color(0xFF00E5FF),
        accentColor = Color(0xFFFF2A6D),
        backgroundColor = Color(0xFF0A0F18),
        surfaceColor = Color(0xFF111A28),
        cardColor = Color(0xFF1A2638),
        isDark = true
    ),
    SUNSET_GOLD(
        id = "sunset_gold",
        title = "Sunset Gold",
        hindiTitle = "सनसेट गोल्ड व एम्बर",
        primaryColor = Color(0xFFFFB800),
        accentColor = Color(0xFFFF5E3A),
        backgroundColor = Color(0xFF14110E),
        surfaceColor = Color(0xFF1E1A16),
        cardColor = Color(0xFF2B251F),
        isDark = true
    ),
    EMERALD_GREEN(
        id = "emerald_green",
        title = "Emerald Pro",
        hindiTitle = "एमराल्ड प्रो ग्रीन",
        primaryColor = Color(0xFF00D26A),
        accentColor = Color(0xFF00E5FF),
        backgroundColor = Color(0xFF0A140F),
        surfaceColor = Color(0xFF112019),
        cardColor = Color(0xFF1A2E24),
        isDark = true
    ),
    STUDIO_LIGHT(
        id = "studio_light",
        title = "Studio Clean Light",
        hindiTitle = "क्लीन लाइट मोड",
        primaryColor = Color(0xFF5B36FE),
        accentColor = Color(0xFFFF2A6D),
        backgroundColor = Color(0xFFF6F7FA),
        surfaceColor = Color(0xFFFFFFFF),
        cardColor = Color(0xFFEDEFF5),
        isDark = false
    );
}

object ThemeManager {
    var currentTheme by mutableStateOf(AppThemeMode.OLED_PURPLE)

    fun setTheme(theme: AppThemeMode) {
        currentTheme = theme
    }
}

