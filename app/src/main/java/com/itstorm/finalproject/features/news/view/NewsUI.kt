package com.itstorm.finalproject.features.news.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.article_selected.view.ArticleSelectedUI
import com.itstorm.finalproject.root.helper_enums.UserHostingScreen
import com.itstorm.finalproject.shared.components.NewsCard
import com.itstorm.finalproject.shared.components.SectionTitle
import com.itstorm.finalproject.shared.components.UserBottomNavBar
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme

@Composable
fun NewsUI(component: NewsComponent) {
    val slot by component.slot.subscribeAsState()

    Box {
        FinalProjectTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Black,
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth()
                            .windowInsetsPadding(
                                WindowInsets.navigationBars
                            ),
                        containerColor = Black
                    ) {
                        UserBottomNavBar(
                            hostingScreen = UserHostingScreen.News,
                            onWeatherClick = component::onClickWeather,
                            onNewsClick = {},
                            onFavoritesClick = component::onClickFavorites
                        )
                    }
                }
            ) { innerPadding ->
                NewsScreen(component, innerPadding)
            }
        }

        slot.child?.instance?.let { child ->
            when(child) {
                is NewsComponent.Child.ArticleSelected ->
                ArticleSelectedUI(
                    component = child.component,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.Black.copy(alpha = 0.8f))
                )
            }
        }
    }
}

@Composable
private fun NewsScreen(
    component: NewsComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
    ) {

        SectionTitle(
            text = stringResource(R.string.news_page_navigation_text),
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 17.dp, top = 38.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        NewsList(
            news = state.news,
            focusedArticle = state.focusedArticle,
            onArticleClick = component::onArticleSelect,
            onFavoriteClick = component::onToggleFavorite,
            onReadClick = component::onToggleRead
        )
    }
}

@Composable
private fun NewsList(
    news: List<UINews>,
    focusedArticle: Long?,
    onArticleClick: (Long) -> Unit,
    onFavoriteClick: (Long) -> Unit,
    onReadClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(horizontal = 7.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(news) { item ->
            val isFocused = focusedArticle == item.id

            NewsCard(
                modifier = Modifier.clickable(
                    onClick = { onArticleClick(item.id) }),
                isFocused = isFocused,
                hostingScreen = UserHostingScreen.News,
                content = item,
                onFavoriteClick = { onFavoriteClick(item.id) },
                onReadClick = { onReadClick(item.id) }
            )
        }
    }
}