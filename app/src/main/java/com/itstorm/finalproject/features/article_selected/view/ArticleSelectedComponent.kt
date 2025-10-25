package com.itstorm.finalproject.features.article_selected.view

import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore
import kotlinx.coroutines.flow.StateFlow

interface ArticleSelectedComponent {
    val model: StateFlow<ArticleSelectedStore.State>

    fun onToggleFavorite()

    fun onToggleRead()

    fun onClickClose()
}