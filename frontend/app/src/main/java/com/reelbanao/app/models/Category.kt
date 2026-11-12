package com.reelbanao.app.models

data class Category(
    val id: String,
    val name: String,
    val hindiName: String,
    val iconName: String,
    val templateCount: Int,
    val isFeatured: Boolean = false
)

