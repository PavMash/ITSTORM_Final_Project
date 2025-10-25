package com.itstorm.finalproject.features.favorites.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.models.news.UINews

interface NewsFavoritesStore: Store<NewsFavoritesStore.Intent, NewsFavoritesStore.State,
        NewsFavoritesStore.Label> {

    sealed interface Intent {
        data class ClickFilter(val filter: NewsCategory?): Intent
        data object ClickWeather: Intent
        data object ClickNews: Intent
        data class ToggleNewsFavorite(val id: Long): Intent
    }

    data class State(
        val favorites: List<UINews> = emptyList(),
        val appliedFilter: NewsCategory? = null,
        val filtered: List<UINews> = emptyList(),
        val toRemove: List<Long> = emptyList()
    )

    sealed interface Label {
        data object ClickWeather: Label
        data object ClickNews: Label
    }
}