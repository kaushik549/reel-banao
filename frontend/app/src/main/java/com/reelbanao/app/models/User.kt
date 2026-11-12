package com.reelbanao.app.models

data class User(
    val id: String,
    val name: String,
    val username: String,
    val email: String,
    val avatarUrl: String? = null,
    val isVip: Boolean = false,
    val totalProjects: Int = 12,
    val totalExports: Int = 38,
    val storageUsedMb: Long = 420L,
    val maxStorageMb: Long = 2048L
)

