package com.reelbanao.app.models

data class TemplateSlot(
    val slotIndex: Int,
    val durationMs: Long,
    val label: String = "Clip ${slotIndex + 1}"
)

data class Template(
    val id: String,
    val title: String,
    val categoryId: String,
    val categoryName: String,
    val coverUrl: String,
    val previewVideoUrl: String? = null,
    val slots: List<TemplateSlot> = emptyList(),
    val totalDurationMs: Long = 15000L,
    val usesCount: Long = 12500L,
    val likesCount: Long = 3400L,
    val isTrending: Boolean = false,
    val isLatest: Boolean = false,
    val isPopular: Boolean = false,
    val isFavorite: Boolean = false,
    val aspectRatio: String = "9:16",
    val musicName: String = "Trending Beats - Original Sound",
    val authorName: String = "ReelBanao Studio",
    val tags: List<String> = emptyList()
)

