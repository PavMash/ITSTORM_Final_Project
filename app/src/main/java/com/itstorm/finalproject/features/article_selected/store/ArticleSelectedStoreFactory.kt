package com.itstorm.finalproject.features.article_selected.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.core_domain.models.news.toUI
import com.itstorm.core_domain.repositories.NewsRepository
import kotlinx.coroutines.launch

class ArticleSelectedStoreFactory(
    private val storeFactory: StoreFactory,
    private val newsRepo: NewsRepository,
    private val article: UINews
) {

    fun create(): ArticleSelectedStore =
        object: ArticleSelectedStore, Store<ArticleSelectedStore.Intent,
                ArticleSelectedStore.State, ArticleSelectedStore.Label> by storeFactory.create(
            name = "NewsStore",
            initialState = ArticleSelectedStore.State(article = article),
            executorFactory = { NewsExecutor(newsRepo) },
            bootstrapper = SimpleBootstrapper(Action.ArticleLoaded),
            reducer = ArticleSelectedReducer
        ) {}

    private sealed interface Msg {
        data class ArticleLoaded(val article: UINews): Msg
    }

    private sealed interface Action{
        data object ArticleLoaded: Action
    }

    private class NewsExecutor(
        private val newsRepo: NewsRepository
    ): CoroutineExecutor<ArticleSelectedStore.Intent,
            Action, ArticleSelectedStore.State, Msg, ArticleSelectedStore.Label>() {

        override fun executeIntent(intent: ArticleSelectedStore.Intent) =
            when (intent) {
                is ArticleSelectedStore.Intent.ToggleRead ->
                    changeReadStatus()

                is ArticleSelectedStore.Intent.ToggleFavorite ->
                    changeFavoriteStatus()

                is ArticleSelectedStore.Intent.ClickClose ->
                    publish(ArticleSelectedStore.Label.ClickClose)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.ArticleLoaded -> displayArticle()
            }

        private fun changeReadStatus() {
            val article = state().article
            scope.launch {
                newsRepo.updateReadStatus(article.id, !article.isRead)
            }
        }

        private fun changeFavoriteStatus() {
            val article = state().article
            scope.launch {
                newsRepo.updateFavoriteStatus(article.id, !article.isFavorite)
            }
        }

        private fun displayArticle() {
            val articleId = state().article.id
            scope.launch {
                newsRepo.getNewsFlowById(articleId).collect { article ->
                    dispatch(
                        Msg.ArticleLoaded(
                            article = article.toUI()
                        )
                    )
                }
            }
        }
    }

    private object ArticleSelectedReducer: Reducer<ArticleSelectedStore.State, Msg> {
        override fun ArticleSelectedStore.State.reduce(msg: Msg): ArticleSelectedStore.State =
            when(msg) {
                is Msg.ArticleLoaded ->
                    copy(article = msg.article)
            }
    }
}