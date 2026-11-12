package com.reelbanao.app.repositories

import com.reelbanao.app.data.DummyData
import com.reelbanao.app.models.Project
import com.reelbanao.app.models.VideoClip
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

class ProjectRepository {

    private val _projects = MutableStateFlow(DummyData.sampleProjects)
    val projects: Flow<List<Project>> = _projects.asStateFlow()

    fun getProjectById(id: String): Project? {
        return _projects.value.find { it.id == id }
    }

    fun createNewProject(title: String = "Untitled Reel"): Project {
        val newProj = Project(
            id = "proj_" + UUID.randomUUID().toString().take(8),
            title = title,
            durationMs = 0L,
            updatedAt = System.currentTimeMillis()
        )
        _projects.value = listOf(newProj) + _projects.value
        return newProj
    }

    fun saveProject(project: Project) {
        val updated = _projects.value.map {
            if (it.id == project.id) project.copy(updatedAt = System.currentTimeMillis()) else it
        }
        _projects.value = updated
    }

    fun deleteProject(projectId: String) {
        _projects.value = _projects.value.filterNot { it.id == projectId }
    }

    fun duplicateProject(projectId: String) {
        val original = getProjectById(projectId) ?: return
        val duplicate = original.copy(
            id = "proj_" + UUID.randomUUID().toString().take(8),
            title = "${original.title} (Copy)",
            updatedAt = System.currentTimeMillis()
        )
        _projects.value = listOf(duplicate) + _projects.value
    }
}

