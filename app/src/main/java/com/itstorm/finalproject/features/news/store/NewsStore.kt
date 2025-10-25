package com.itstorm.finalproject.features.news.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.features.news.store.NewsStore.Intent
import com.itstorm.finalproject.features.news.store.NewsStore.State
import com.itstorm.finalproject.features.news.store.NewsStore.Label

interface NewsStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data class ToggleNewsRead(val id: Long): Intent
        data class ToggleNewsFavorite(val id: Long): Intent
        data class SelectNewsArticle(val id: Long): Intent
        data object ClickWeather: Intent
        data object ClickFavorites: Intent
    }

    data class State(
        val news: List<UINews> = emptyList(),
        val focusedArticle: Long? = null
    )

    sealed interface Label {
        data class SelectNewsArticle(val id: Long): Label
        data object ClickWeather: Label
        data object ClickFavorites: Label
    }
}