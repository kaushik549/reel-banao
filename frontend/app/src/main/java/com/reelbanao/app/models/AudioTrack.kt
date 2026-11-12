package com.reelbanao.app.models

data class AudioTrack(
    val id: String,
    val title: String,
    val artist: String,
    val durationMs: Long,
    val coverUrl: String,
    val audioUrl: String,
    val category: String, // "Trending", "Beat Sync", "BGM", "Vlog", "Romantic"
    val beatPointsMs: List<Long> = emptyList(),
    val isTrending: Boolean = false,
    val isFavorite: Boolean = false,
    val usesCount: Long = 45000L
)

