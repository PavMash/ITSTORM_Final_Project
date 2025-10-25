package com.itstorm.finalproject.features.article_selected.view

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.R
import com.itstorm.finalproject.root.helper_enums.UserHostingScreen
import com.itstorm.finalproject.shared.components.NewsCard
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun ArticleSelectedUI(
    component: ArticleSelectedComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Box(modifier = modifier) {

        Column(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(
                top = 56.dp, bottom = 10.dp,
                start = 7.dp, end = 7.dp),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(40.dp))

            CloseButton(
                modifier = Modifier.align(Alignment.End),
                onClose = component::onClickClose
            )

            Spacer(modifier = Modifier.height(12.dp))

            NewsCard(
                modifier = Modifier.fillMaxWidth(),
                content = state.article,
                hostingScreen = UserHostingScreen.SelectedArticle,
                onFavoriteClick = component::onToggleFavorite,
                onReadClick = component::onToggleRead,
            )
        }
    }
}

@Composable
private fun CloseButton(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Box(
        modifier = modifier.background(
            color = White,
            shape = RoundedCornerShape(6.dp))
            .wrapContentSize()
            .padding(6.dp)
            .clickable(
                onClick = onClose
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = stringResource(R.string.close_icon_description)
        )
    }
}