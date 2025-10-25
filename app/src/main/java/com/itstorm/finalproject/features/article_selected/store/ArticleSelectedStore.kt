package com.itstorm.finalproject.features.article_selected.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore.Intent
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore.State
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore.Label

interface ArticleSelectedStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data object ClickClose: Intent
        data object ToggleFavorite: Intent
        data object ToggleRead: Intent
    }

    data class State(
        val article: UINews
    )

    sealed interface Label {
        data object ClickClose: Label
    }
}