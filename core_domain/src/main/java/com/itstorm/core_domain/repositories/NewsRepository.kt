package com.itstorm.core_domain.repositories

import android.content.Context
import com.itstorm.core_domain.models.news.DomainNews
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getAllNews(): Flow<List<DomainNews>>

    fun getFavoriteNews(): Flow<List<DomainNews>>

    fun getNewsFlowById(id: Long): Flow<DomainNews>

    suspend fun getNewsById(id: Long): DomainNews?

    suspend fun preloadNewsIfEmpty(context: Context)

    suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean)

    suspend fun updateReadStatus(id: Long, isRead: Boolean)
}