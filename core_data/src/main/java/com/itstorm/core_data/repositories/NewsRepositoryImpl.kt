package com.itstorm.core_data.repositories

import android.content.Context
import android.util.Log
import com.itstorm.core_data.db.dao.NewsDao
import com.itstorm.core_data.db.entities.NewsEntity
import com.itstorm.core_data.db.mappers.toDomain
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_domain.models.news.DomainNews
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.models.news.NewsType
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.core_domain.usecases.AssetFileReader
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import java.time.Instant

class NewsRepositoryImpl(
    private val newsDao: NewsDao
): NewsRepository {

    override fun getAllNews(): Flow<List<DomainNews>> =
        newsDao.getAll().map { it.map(NewsEntity::toDomain) }

    override fun getFavoriteNews(): Flow<List<DomainNews>> =
        newsDao.getFavorites().map { it.map(NewsEntity::toDomain) }

    override fun getNewsFlowById(id: Long): Flow<DomainNews> =
        newsDao.getNewsFlowById(id).map { it.toDomain() }

    override suspend fun getNewsById(id: Long): DomainNews? =
        newsDao.getNewsById(id)?.toDomain()

    override suspend fun preloadNewsIfEmpty(context: Context) {
        if (newsDao.getAll().first().isEmpty()) {
            val initialNews = initialNews(context)
            Log.d("timeCheck_loaded", "${initialNews.last().createdAt}")
            newsDao.insertAll(initialNews.map(DomainNews::toEntity))
        }
    }

    override suspend fun updateFavoriteStatus(id: Long, isFavorite: Boolean) {
        val entity = newsDao.getNewsById(id)
        entity?.let {
            newsDao.update(entity.copy(isFavorite = isFavorite))
        }
    }

    override suspend fun updateReadStatus(id: Long, isRead: Boolean) {
        val entity = newsDao.getNewsById(id)
        entity?.let {
            newsDao.update(entity.copy(isRead = isRead))
        }
    }

    private fun initialNews(context: Context): List<DomainNews> =
        listOf(
            DomainNews(
                id = 1,
                title = "Тайные улочки Барселоны",
                content = AssetFileReader(context)
                    .readAssetFile("news_texts/lorem_ipsum.txt"),
                previewImagePath = "file:///android_asset/news_images/spanish_view.png",
                type = NewsType.Article,
                category = NewsCategory.Culture,
                timeToRead = 10,
                createdAt = Instant.now(),
                isRead = false,
                isFavorite = false,
            ),

            DomainNews(
                id = 2,
                title = "Как проходит рабочий день PM из маленькой инди-компании?",
                content = AssetFileReader(context)
                    .readAssetFile("news_texts/lorem_ipsum.txt"),
                previewImagePath = "file:///android_asset/news_images/technological_company.png",
                type = NewsType.Essay,
                category = NewsCategory.Technology,
                timeToRead = 45,
                createdAt = Instant.now(),
                isRead = false,
                isFavorite = false,
            ),

            DomainNews(
                id = 3,
                title = "Как проходит рабочий день PM из маленькой инди-компании?",
                content = AssetFileReader(context)
                    .readAssetFile("news_texts/lorem_ipsum.txt"),
                previewImagePath = "file:///android_asset/news_images/chill_frog.png",
                type = NewsType.Blog,
                category = NewsCategory.Technology,
                timeToRead = 30,
                createdAt = Instant.now(),
                isRead = false,
                isFavorite = false,
            ),
        )
}