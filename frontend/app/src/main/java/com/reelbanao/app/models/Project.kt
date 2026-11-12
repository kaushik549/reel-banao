package com.reelbanao.app.models

data class Project(
    val id: String,
    val title: String,
    val coverUri: String? = null,
    val durationMs: Long = 0L,
    val aspectRatio: String = "9:16",
    val resolution: String = "1080p",
    val fps: Int = 30,
    val updatedAt: Long = System.currentTimeMillis(),
    val clips: List<VideoClip> = emptyList(),
    val audioTracks: List<AudioTrack> = emptyList(),
    val textLayers: List<TextLayer> = emptyList()
) {
    val totalDurationFormatted: String
        get() {
            val totalSec = durationMs / 1000
            val min = totalSec / 60
            val sec = totalSec % 60
            return String.format("%02d:%02d", min, sec)
        }
}

