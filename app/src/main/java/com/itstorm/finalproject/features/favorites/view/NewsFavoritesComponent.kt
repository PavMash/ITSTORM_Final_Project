package com.itstorm.finalproject.features.favorites.view

import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.finalproject.features.favorites.store.NewsFavoritesStore
import kotlinx.coroutines.flow.StateFlow

interface NewsFavoritesComponent {
    val model: StateFlow<NewsFavoritesStore.State>

    fun onToggleFavorite(newsId: Long)

    fun onFilterNews(filter: NewsCategory?)

    fun onClickWeather()

    fun onClickNews()
}