package com.reelbanao.app.models

data class TextLayer(
    val id: String,
    val text: String,
    val fontName: String = "Roboto Bold",
    val fontSizeSp: Float = 22f,
    val colorHex: Long = 0xFFFFFFFF,
    val backgroundColorHex: Long? = 0x88000000,
    val startMs: Long = 0L,
    val endMs: Long = 5000L,
    val posX: Float = 0.5f, // 0 to 1 normalized
    val posY: Float = 0.5f,
    val animation: String = "Pop In" // "Fade", "Pop In", "Typewriter", "Bounce"
)

