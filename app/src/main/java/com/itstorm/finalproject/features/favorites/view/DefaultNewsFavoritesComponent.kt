package com.itstorm.finalproject.features.favorites.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.finalproject.features.favorites.store.NewsFavoritesStore
import com.itstorm.finalproject.features.favorites.store.NewsFavoritesStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNewsFavoritesComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    onWeatherClicked: () -> Unit,
    onNewsClicked: () -> Unit,
    componentContext: ComponentContext
): NewsFavoritesComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        NewsFavoritesStoreFactory(storeFactory, repository).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is NewsFavoritesStore.Label.ClickWeather -> {
                        onWeatherClicked()
                    }
                    is NewsFavoritesStore.Label.ClickNews -> {
                        onNewsClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<NewsFavoritesStore.State> = store.stateFlow

    override fun onToggleFavorite(newsId: Long) {
        store.accept(NewsFavoritesStore.Intent.ToggleNewsFavorite(newsId))
    }

    override fun onFilterNews(filter: NewsCategory?) {
        store.accept(NewsFavoritesStore.Intent.ClickFilter(filter))
    }

    override fun onClickWeather() {
        store.accept(NewsFavoritesStore.Intent.ClickWeather)
    }

    override fun onClickNews() {
        store.accept(NewsFavoritesStore.Intent.ClickNews)
    }

}