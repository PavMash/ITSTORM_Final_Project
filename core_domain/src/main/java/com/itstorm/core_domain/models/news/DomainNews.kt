package com.itstorm.core_domain.models.news

import java.time.Instant

data class DomainNews (
    val id: Long = 0,
    val title: String,
    val content: String,
    val previewImagePath: String?,
    val type: NewsType,
    val category: NewsCategory,
    val timeToRead: Int,
    val createdAt: Instant,
    val isRead: Boolean,
    val isFavorite: Boolean
)