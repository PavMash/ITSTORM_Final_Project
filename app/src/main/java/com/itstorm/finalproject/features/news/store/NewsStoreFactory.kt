package com.itstorm.finalproject.features.news.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.core_domain.models.news.toUI
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.finalproject.features.news.store.NewsStore.Intent
import com.itstorm.finalproject.features.news.store.NewsStore.State
import com.itstorm.finalproject.features.news.store.NewsStore.Label
import kotlinx.coroutines.launch

class NewsStoreFactory(
    private val storeFactory: StoreFactory,
    private val newsRepo: NewsRepository
) {

    fun create(): NewsStore =
        object: NewsStore, Store<Intent, State, Label> by storeFactory.create(
            name = "NewsStore",
            initialState = State(),
            executorFactory = { NewsExecutor(newsRepo) },
            bootstrapper = SimpleBootstrapper(Action.NewsLoaded),
            reducer = NewsReducer
        ) {}

    private sealed interface Msg {
        data class NewsLoaded(val news: List<UINews>): Msg
        data class ArticleFocused(val id: Long): Msg
    }

    private sealed interface Action{
        data object NewsLoaded: Action
    }

    private class NewsExecutor(
        private val newsRepo: NewsRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.ToggleNewsRead ->
                    changeReadStatus(intent.id)
                is Intent.ToggleNewsFavorite ->
                    changeFavoriteStatus(intent.id)
                is Intent.SelectNewsArticle ->
                    publish(Label.SelectNewsArticle(intent.id))
                is Intent.ClickWeather ->
                    publish(Label.ClickWeather)
                is Intent.ClickFavorites ->
                    publish(Label.ClickFavorites)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.NewsLoaded -> displayNews()
            }

        private fun changeReadStatus(newsId: Long) {
            val state = state()
            val articleToUpdate = state.news.find {newsId == it.id}
            articleToUpdate?.let {
                scope.launch {
                    newsRepo.updateReadStatus(newsId, !articleToUpdate.isRead)
                }
            }
            dispatch(Msg.ArticleFocused(newsId))
        }

        private fun changeFavoriteStatus(newsId: Long) {
            val state = state()
            val articleToUpdate = state.news.find {newsId == it.id}
            articleToUpdate?.let {
                scope.launch {
                    newsRepo.updateFavoriteStatus(newsId, !articleToUpdate.isFavorite)
                }
            }
            dispatch(Msg.ArticleFocused(newsId))
        }

        private fun displayNews() {
            scope.launch {
                newsRepo.getAllNews().collect { newsList ->
                    dispatch(Msg.NewsLoaded(
                        newsList.map{ it.toUI() }
                    ))
                }
            }
        }
    }

    private object NewsReducer: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.NewsLoaded ->
                    copy(news = msg.news,
                    focusedArticle = focusedArticle)
                is Msg.ArticleFocused ->
                    copy(news = news,
                        focusedArticle = msg.id)
            }
    }
}