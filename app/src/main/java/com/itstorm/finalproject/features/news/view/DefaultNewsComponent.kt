package com.itstorm.finalproject.features.news.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.finalproject.features.article_selected.view.DefaultArticleSelectedComponent
import com.itstorm.finalproject.features.news.store.NewsStore
import com.itstorm.finalproject.features.news.store.NewsStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultNewsComponent(
    private val storeFactory: StoreFactory,
    private val newsRepo: NewsRepository,
    componentContext: ComponentContext,
    onWeatherClicked: () -> Unit,
    onFavoritesClicked: () -> Unit
): NewsComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        NewsStoreFactory(storeFactory, newsRepo).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is NewsStore.Label.ClickWeather -> {
                        onWeatherClicked()
                    }
                    is NewsStore.Label.ClickFavorites -> {
                        onFavoritesClicked()
                    }
                    is NewsStore.Label.SelectNewsArticle -> {}
                }
            }
        }
    }

    override val model: StateFlow<NewsStore.State> = store.stateFlow

    val navigation = SlotNavigation<NewsComponent.NewsConfig>()
    override val slot: Value<ChildSlot<*, NewsComponent.Child>> =
        childSlot(
            source = navigation,
            serializer = NewsComponent.NewsConfig.serializer(),
            handleBackButton = true,
            childFactory = ::createChildSlot
        )

    private fun createChildSlot(
        config: NewsComponent.NewsConfig,
        childContext: ComponentContext
    ): NewsComponent.Child = when (config) {
        is NewsComponent.NewsConfig.ArticleSelected ->
            NewsComponent.Child.ArticleSelected(
                component = DefaultArticleSelectedComponent(
                    storeFactory = DefaultStoreFactory(),
                    repository = newsRepo,
                    article = config.article,
                    componentContext = childContext,
                    onCloseClicked = ::onSelectedArticleClose
                )
            )
    }

    private fun onSelectedArticleClose() {
        navigation.dismiss()
    }

    override fun onToggleFavorite(newsId: Long) {
        store.accept(NewsStore.Intent.ToggleNewsFavorite(newsId))
    }

    override fun onToggleRead(newsId: Long) {
        store.accept(NewsStore.Intent.ToggleNewsRead(newsId))
    }

    override fun onArticleSelect(newsId: Long) {
        val article = store.state.news.find { it.id == newsId }
        article?.let {
            navigation.activate(NewsComponent.NewsConfig.ArticleSelected(article))
        }
    }

    override fun onClickWeather() {
        store.accept(NewsStore.Intent.ClickWeather)
    }

    override fun onClickFavorites() {
        store.accept(NewsStore.Intent.ClickFavorites)
    }


}