package com.reelbanao.app.models

data class AiEditStyle(
    val id: String,
    val name: String,
    val description: String,
    val recommendedMood: String,
    val suggestedMusicGenre: String,
    val gradientColors: List<Long>,
    val tags: List<String>
)

