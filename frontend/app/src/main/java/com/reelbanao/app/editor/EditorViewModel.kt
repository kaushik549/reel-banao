package com.reelbanao.app.editor

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableLongStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.reelbanao.app.models.AudioTrack
import com.reelbanao.app.models.Project
import com.reelbanao.app.models.TextLayer
import com.reelbanao.app.models.VideoClip
import com.reelbanao.app.utils.AspectRatio
import java.util.UUID

enum class EditorTool {
    NONE,
    TRIM,
    AUDIO,
    TEXT,
    FILTER,
    SPEED,
    RATIO,
    SUBTITLE,
    TRANSITION
}

class EditorViewModel : ViewModel() {

    var activeProject by mutableStateOf<Project?>(null)
        private set

    val clips = mutableStateListOf<VideoClip>()
    val audioTracks = mutableStateListOf<AudioTrack>()
    val textLayers = mutableStateListOf<TextLayer>()

    var currentPositionMs by mutableLongStateOf(0L)
    var isPlaying by mutableStateOf(false)
    var selectedClipIndex by mutableIntStateOf(0)
    var activeTool by mutableStateOf(EditorTool.NONE)
    var currentAspectRatio by mutableStateOf(AspectRatio.REELS_9_16)
    var currentSpeed by mutableFloatStateOf(1.0f)
    var currentVolume by mutableFloatStateOf(1.0f)
    var selectedFilterName by mutableStateOf("Normal")

    val totalDurationMs: Long
        get() = clips.sumOf { it.durationMs }.coerceAtLeast(1000L)

    val currentClip: VideoClip?
        get() = clips.getOrNull(selectedClipIndex)

    fun loadProject(project: Project) {
        activeProject = project
        clips.clear()
        clips.addAll(project.clips)
        if (clips.isEmpty()) {
            // Default starter clip for quick demo testing
            clips.add(
                VideoClip(
                    id = "sample_clip_01",
                    uriString = "android.resource://sample/sample1",
                    name = "Opening Scene.mp4",
                    totalDurationMs = 5000L,
                    trimStartMs = 0L,
                    trimEndMs = 5000L
                )
            )
            clips.add(
                VideoClip(
                    id = "sample_clip_02",
                    uriString = "android.resource://sample/sample2",
                    name = "Action Beat.mp4",
                    totalDurationMs = 4000L,
                    trimStartMs = 0L,
                    trimEndMs = 4000L
                )
            )
        }
        audioTracks.clear()
        audioTracks.addAll(project.audioTracks)
        textLayers.clear()
        textLayers.addAll(project.textLayers)
        currentPositionMs = 0L
        selectedClipIndex = 0
    }

    fun togglePlayPause() {
        isPlaying = !isPlaying
    }

    fun seekTo(positionMs: Long) {
        currentPositionMs = positionMs.coerceIn(0L, totalDurationMs)
    }

    fun addClip(name: String, durationMs: Long = 4000L) {
        val newClip = VideoClip(
            id = "clip_" + UUID.randomUUID().toString().take(6),
            uriString = "content://media/sample_${System.currentTimeMillis()}",
            name = name,
            totalDurationMs = durationMs,
            trimStartMs = 0L,
            trimEndMs = durationMs
        )
        clips.add(newClip)
    }

    fun splitCurrentClip() {
        val clip = currentClip ?: return
        val clipLocalTime = (currentPositionMs - getClipStartTime(selectedClipIndex)).coerceIn(500L, clip.durationMs - 500L)

        val firstPart = clip.copy(
            id = "clip_" + UUID.randomUUID().toString().take(6),
            name = "${clip.name} (Part 1)",
            trimEndMs = clip.trimStartMs + clipLocalTime
        )
        val secondPart = clip.copy(
            id = "clip_" + UUID.randomUUID().toString().take(6),
            name = "${clip.name} (Part 2)",
            trimStartMs = clip.trimStartMs + clipLocalTime
        )

        clips[selectedClipIndex] = firstPart
        clips.add(selectedClipIndex + 1, secondPart)
    }

    fun deleteCurrentClip() {
        if (clips.size > 1 && selectedClipIndex in clips.indices) {
            clips.removeAt(selectedClipIndex)
            selectedClipIndex = selectedClipIndex.coerceAtMost(clips.lastIndex)
        }
    }

    fun duplicateCurrentClip() {
        val clip = currentClip ?: return
        val duplicate = clip.copy(
            id = "clip_" + UUID.randomUUID().toString().take(6),
            name = "${clip.name} (Copy)"
        )
        clips.add(selectedClipIndex + 1, duplicate)
    }

    fun updateTrim(startMs: Long, endMs: Long) {
        val clip = currentClip ?: return
        if (endMs > startMs + 500L) {
            clips[selectedClipIndex] = clip.copy(trimStartMs = startMs, trimEndMs = endMs)
        }
    }

    fun setSpeed(speed: Float) {
        val clip = currentClip ?: return
        currentSpeed = speed
        clips[selectedClipIndex] = clip.copy(speed = speed)
    }

    fun setVolume(volume: Float) {
        val clip = currentClip ?: return
        currentVolume = volume
        clips[selectedClipIndex] = clip.copy(volume = volume, isMuted = volume == 0f)
    }

    fun addTextLayer(text: String) {
        textLayers.add(
            TextLayer(
                id = "txt_" + UUID.randomUUID().toString().take(6),
                text = text,
                startMs = currentPositionMs,
                endMs = (currentPositionMs + 3000L).coerceAtMost(totalDurationMs)
            )
        )
    }

    fun addAudioTrack(track: AudioTrack) {
        audioTracks.add(track)
    }

    fun applyFilter(filterName: String) {
        selectedFilterName = filterName
    }

    private fun getClipStartTime(index: Int): Long {
        var start = 0L
        for (i in 0 until index) {
            if (i in clips.indices) {
                start += clips[i].durationMs
            }
        }
        return start
    }
}

