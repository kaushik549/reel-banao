package com.reelbanao.app.repositories

import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.AudioTrack
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class AudioRepository {

    private val _tracks = MutableStateFlow(DummyData.audioTracks)
    val tracks: Flow<List<AudioTrack>> = _tracks.asStateFlow()

    val categories = listOf("All", "Trending", "Beat Sync", "BGM", "Vlog", "Romantic")

    fun searchTracks(query: String, category: String): Flow<List<AudioTrack>> {
        return _tracks.map { list ->
            list.filter { track ->
                val matchesQuery = query.isBlank() ||
                        track.title.contains(query, ignoreCase = true) ||
                        track.artist.contains(query, ignoreCase = true)
                val matchesCat = category == "All" || track.category.equals(category, ignoreCase = true)
                matchesQuery && matchesCat
            }
        }
    }

    fun toggleFavorite(trackId: String) {
        _tracks.value = _tracks.value.map {
            if (it.id == trackId) it.copy(isFavorite = !it.isFavorite) else it
        }
    }
}

