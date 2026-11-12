package com.reelbanao.app.templates

import com.reelbanao.app.models.Project
import com.reelbanao.app.models.Template
import com.reelbanao.app.models.VideoClip
import java.util.UUID

object TemplateEngine {

    /**
     * Replaces template slots with user-selected video/photo clips
     * to instantiate a full Project ready for preview and editing.
     */
    fun instantiateProjectFromTemplate(
        template: Template,
        userSelectedMediaUris: List<String>
    ): Project {
        val clips = template.slots.mapIndexed { index, slot ->
            val uri = userSelectedMediaUris.getOrElse(index) {
                userSelectedMediaUris.firstOrNull() ?: "sample_asset_$index"
            }
            VideoClip(
                id = "clip_${UUID.randomUUID().toString().take(6)}",
                uriString = uri,
                name = slot.label,
                totalDurationMs = slot.durationMs,
                trimStartMs = 0L,
                trimEndMs = slot.durationMs,
                speed = 1.0f
            )
        }

        return Project(
            id = "proj_" + UUID.randomUUID().toString().take(8),
            title = "${template.title} (Draft)",
            coverUri = template.coverUrl,
            durationMs = template.totalDurationMs,
            aspectRatio = template.aspectRatio,
            clips = clips
        )
    }

    /**
     * Validates whether user provided enough media for this template.
     */
    fun validateSelection(template: Template, selectedCount: Int): Pair<Boolean, String> {
        val required = template.slots.size
        return if (selectedCount < required) {
            false to "कृपया कम से कम $required क्लिप्स/फ़ोटो चुनें (अभी चुने गए: $selectedCount)"
        } else {
            true to "परफेक्ट! $required क्लिप्स तैयार हैं।"
        }
    }
}

