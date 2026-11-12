package com.reelbanao.app.repositories

import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.Category
import com.reelbanao.app.models.Template
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map

class TemplateRepository {

    // Scalable in-memory repository with pagination simulation for 5000+ templates
    private val _templates = MutableStateFlow(DummyData.templates)
    val templates: Flow<List<Template>> = _templates.asStateFlow()

    fun getAllCategories(): List<Category> = DummyData.categories

    fun getCategoryById(categoryId: String): Category? {
        return DummyData.categories.find { it.id == categoryId }
    }

    fun getTemplateById(id: String): Template? {
        return _templates.value.find { it.id == id }
    }

    fun searchAndFilter(
        query: String = "",
        categoryId: String? = null,
        filterTab: String = "All" // "All", "Trending", "Latest", "Popular", "Favorites"
    ): Flow<List<Template>> {
        return _templates.map { list ->
            list.filter { template ->
                val matchesQuery = query.isBlank() ||
                        template.title.contains(query, ignoreCase = true) ||
                        template.categoryName.contains(query, ignoreCase = true) ||
                        template.tags.any { it.contains(query, ignoreCase = true) }

                val matchesCategory = categoryId == null || categoryId == "all" || template.categoryId == categoryId

                val matchesTab = when (filterTab) {
                    "Trending" -> template.isTrending
                    "Latest" -> template.isLatest
                    "Popular" -> template.isPopular
                    "Favorites" -> template.isFavorite
                    else -> true
                }

                matchesQuery && matchesCategory && matchesTab
            }
        }
    }

    fun toggleFavorite(templateId: String) {
        val updated = _templates.value.map {
            if (it.id == templateId) it.copy(isFavorite = !it.isFavorite) else it
        }
        _templates.value = updated
    }
}

