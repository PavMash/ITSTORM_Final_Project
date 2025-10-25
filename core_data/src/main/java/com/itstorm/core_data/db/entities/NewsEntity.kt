package com.itstorm.core_data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.models.news.NewsType
import java.time.Instant

@Entity(tableName = "news")
data class NewsEntity (
    @PrimaryKey val id: Long = 0,
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