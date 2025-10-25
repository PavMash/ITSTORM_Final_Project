package com.itstorm.finalproject.features.article_selected.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultArticleSelectedComponent(
    private val storeFactory: StoreFactory,
    private val repository: NewsRepository,
    private val article: UINews,
    componentContext: ComponentContext,
    onCloseClicked: () -> Unit
): ArticleSelectedComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        ArticleSelectedStoreFactory(
            storeFactory, repository,
            article
        ).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is ArticleSelectedStore.Label.ClickClose -> {
                        onCloseClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<ArticleSelectedStore.State> = store.stateFlow

    override fun onToggleFavorite() {
        store.accept(ArticleSelectedStore.Intent.ToggleFavorite)
    }

    override fun onToggleRead() {
        store.accept(ArticleSelectedStore.Intent.ToggleRead)
    }

    override fun onClickClose() {
        store.accept(ArticleSelectedStore.Intent.ClickClose)
    }
}