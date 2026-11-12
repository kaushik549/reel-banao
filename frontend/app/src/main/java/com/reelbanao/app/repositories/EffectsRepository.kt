package com.reelbanao.app.repositories

import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.FilterEffect
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class EffectsRepository {

    private val _effects = MutableStateFlow(DummyData.filtersAndEffects)
    val effects: Flow<List<FilterEffect>> = _effects.asStateFlow()

    val categories = listOf("All", "Glitch", "Retro", "Cinematic", "Light", "Mood")

    fun getEffectsByCategory(category: String): Flow<List<FilterEffect>> {
        return _effects.map { list ->
            if (category == "All") list else list.filter { it.category.equals(category, ignoreCase = true) }
        }
    }
}

