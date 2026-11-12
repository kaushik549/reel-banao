package com.reelbanao.app.theme

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

val BackgroundDark = Color(0xFF0F1015)
val SurfaceDark = Color(0xFF171922)
val CardDark = Color(0xFF212430)
val CardStroke = Color(0xFF2E3344)
val DividerDark = Color(0xFF282C3D)

val PrimaryPurple = Color(0xFF7052FF)
val PrimaryPurpleLight = Color(0xFF9078FF)
val AccentCyan = Color(0xFF00E5FF)
val AccentPink = Color(0xFFFF2A6D)
val AccentGreen = Color(0xFF00D26A)
val AccentAmber = Color(0xFFFFB800)

val TextWhite = Color(0xFFFFFFFF)
val TextGray = Color(0xFFA1A4B2)
val TextMuted = Color(0xFF6B6E7E)

val CapCutGradient = Brush.linearGradient(
    colors = listOf(PrimaryPurple, AccentPink)
)

val NeonCyanGradient = Brush.linearGradient(
    colors = listOf(AccentCyan, PrimaryPurple)
)

val ExportGreenGradient = Brush.linearGradient(
    colors = listOf(AccentGreen, Color(0xFF00B0FF))
)

val DarkCardGradient = Brush.verticalGradient(
    colors = listOf(Color(0xFF25293A), Color(0xFF1B1D28))
)

