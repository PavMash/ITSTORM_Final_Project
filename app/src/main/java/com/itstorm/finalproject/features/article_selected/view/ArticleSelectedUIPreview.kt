package com.itstorm.finalproject.features.article_selected.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.itstorm.core_domain.models.news.NewsCategory
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.features.article_selected.store.ArticleSelectedStore
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Article Selected Preview"
)
@Composable
fun ArticleSelectedUIPreview() {
    FinalProjectTheme {
        val fakeArticle = UINews(
            id = 1,
            title = "Тайные улочки Барселоны",
            content = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor",
            previewImagePath = "file:///android_asset/news_images/spanish_view.png",
            category = NewsCategory.Culture,
            type = "Статья",
            timeSinceCreation = "5 минут назад",
            timeToRead = "15 мин",
            isRead = false,
            isFavorite = false
        )

        val fakeComponent = object : ArticleSelectedComponent {
            override val model = MutableStateFlow(
                ArticleSelectedStore.State(
                    article = fakeArticle
                )
            )

            override fun onClickClose() {}
            override fun onToggleFavorite() {}
            override fun onToggleRead() {}
        }

        ArticleSelectedUI(
            component = fakeComponent,
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Black.copy(alpha = 0.8f))
        )
    }
}
