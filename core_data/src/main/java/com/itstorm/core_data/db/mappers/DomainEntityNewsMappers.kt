package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.NewsEntity
import com.itstorm.core_domain.models.news.DomainNews

fun NewsEntity.toDomain(): DomainNews = DomainNews(
    id = id,
    title = title,
    content = content,
    previewImagePath = previewImagePath,
    type = type,
    category = category,
    timeToRead = timeToRead,
    createdAt = createdAt,
    isRead = isRead,
    isFavorite = isFavorite
)

fun DomainNews.toEntity(): NewsEntity = NewsEntity(
    id = id,
    title = title,
    content = content,
    previewImagePath = previewImagePath,
    type = type,
    category = category,
    timeToRead = timeToRead,
    createdAt = createdAt,
    isRead = isRead,
    isFavorite = isFavorite
)