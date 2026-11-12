package com.reelbanao.app.models

enum class EffectType {
    FILTER,
    VISUAL_EFFECT,
    TRANSITION
}

data class FilterEffect(
    val id: String,
    val name: String,
    val type: EffectType,
    val category: String, // "Glitch", "Retro", "Cinematic", "Light", "Mood"
    val colorHex: Long = 0xFF6C5CE7,
    val intensity: Float = 0.8f,
    val isPro: Boolean = false
)

