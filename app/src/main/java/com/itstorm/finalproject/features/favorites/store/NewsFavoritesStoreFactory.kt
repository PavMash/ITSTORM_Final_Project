package com.itstorm.finalproject.features.favorites.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.core_domain.models.news.toUI
import com.itstorm.core_domain.repositories.NewsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class NewsFavoritesStoreFactory(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository
) {

    fun create(): NewsFavoritesStore =
        object: NewsFavoritesStore,
            Store<NewsFavoritesStore.Intent, NewsFavoritesStore.State,
                    NewsFavoritesStore.Label> by storeFactory.create(
                name = "NewsFavoritesStore",
                initialState = NewsFavoritesStore.State(),
                bootstrapper = SimpleBootstrapper(Action.NewsLoaded),
                executorFactory = { NewsFavoritesExecutor(repository) },
                reducer = NewsFavoritesReducer
            ){}

    private sealed interface Msg {
        data class NewsLoaded(val news: List<UINews>): Msg
        data class FavoritesFiltered(val filter: NewsCategory?): Msg
        data class MarkAsToRemove(val id: Long): Msg
        data class UnmarkAsToRemove(val id: Long): Msg

    }

    private sealed interface Action{
        data object NewsLoaded: Action
    }

    private class NewsFavoritesExecutor(
        private val newsRepo: NewsRepository
    ): CoroutineExecutor<NewsFavoritesStore.Intent, Action,
            NewsFavoritesStore.State, Msg, NewsFavoritesStore.Label>() {

        override fun executeIntent(intent: NewsFavoritesStore.Intent) =
            when (intent) {
                is NewsFavoritesStore.Intent.ToggleNewsFavorite ->
                    removeFromFavorites(intent.id)

                is NewsFavoritesStore.Intent.ClickFilter ->
                    filterNews(intent.filter)

                is NewsFavoritesStore.Intent.ClickWeather ->
                    publish(NewsFavoritesStore.Label.ClickWeather)

                is NewsFavoritesStore.Intent.ClickNews ->
                    publish(NewsFavoritesStore.Label.ClickNews)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.NewsLoaded -> displayNews()
            }

        private fun filterNews(category: NewsCategory?) {
            dispatch(Msg.FavoritesFiltered(category))
        }
        private fun removeFromFavorites(newsId: Long) {
            val state = state()
            val articleToRemove = state.favorites.find {newsId == it.id}
            articleToRemove?.let {
                dispatch(Msg.MarkAsToRemove(articleToRemove.id))
                scope.launch {
                    delay(1000L)
                    dispatch(Msg.UnmarkAsToRemove(articleToRemove.id))
                    newsRepo.updateFavoriteStatus(newsId, !articleToRemove.isFavorite)
                }
            }
        }

        private fun displayNews() {
            scope.launch {
                newsRepo.getFavoriteNews().collect { newsList ->
                    dispatch(Msg.NewsLoaded(newsList.map { it.toUI() }))
                }
            }
        }
    }

    private object NewsFavoritesReducer: Reducer<NewsFavoritesStore.State, Msg> {
        override fun NewsFavoritesStore.State.reduce(msg: Msg): NewsFavoritesStore.State =
            when(msg) {
                is Msg.NewsLoaded ->
                    copy(
                        favorites = msg.news,
                        appliedFilter = appliedFilter,
                        filtered = filterNews(msg.news, appliedFilter),
                        toRemove = toRemove
                    )
                is Msg.MarkAsToRemove ->
                    copy(
                        favorites = favorites,
                        appliedFilter = appliedFilter,
                        filtered = filtered,
                        toRemove = toRemove + msg.id
                    )
                is Msg.UnmarkAsToRemove ->
                    copy(
                        favorites = favorites,
                        appliedFilter = appliedFilter,
                        filtered = filtered,
                        toRemove = if (toRemove.contains(msg.id))
                            toRemove - msg.id
                        else toRemove
                    )
                is Msg.FavoritesFiltered ->
                    copy(
                        favorites = favorites,
                        appliedFilter = msg.filter,
                        filtered = filterNews(favorites, msg.filter),
                        toRemove = toRemove
                    )
            }

        private fun filterNews(news: List<UINews>, filter: NewsCategory?): List<UINews>  =
            if (filter != null)
                news.filter { it.category == filter }
            else
                news
        }
}