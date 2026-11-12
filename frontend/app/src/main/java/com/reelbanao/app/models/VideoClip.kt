package com.reelbanao.app.models

data class VideoClip(
    val id: String,
    val uriString: String,
    val name: String,
    val totalDurationMs: Long,
    val trimStartMs: Long = 0L,
    val trimEndMs: Long = totalDurationMs,
    val speed: Float = 1.0f,
    val volume: Float = 1.0f,
    val rotationDegrees: Int = 0,
    val isMuted: Boolean = false,
    val filterId: String? = null,
    val transition: String = "None"
) {
    val durationMs: Long
        get() = ((trimEndMs - trimStartMs) / speed).toLong().coerceAtLeast(100L)
}

