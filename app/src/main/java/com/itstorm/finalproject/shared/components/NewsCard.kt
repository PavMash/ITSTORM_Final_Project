package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.itstorm.core_domain.models.news.UINews
import com.itstorm.finalproject.R
import com.itstorm.finalproject.root.helper_enums.UserHostingScreen
import com.itstorm.finalproject.shared.ui.theme.Grey1A
import com.itstorm.finalproject.shared.ui.theme.Grey26
import com.itstorm.finalproject.shared.utils.categoryToUI

@Composable
fun NewsCard(
    modifier: Modifier = Modifier,
    content: UINews,
    isFocused: Boolean = false,
    hostingScreen: UserHostingScreen,
    onFavoriteClick: () -> Unit,
    onReadClick: () -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (isFocused) Grey26 else Grey1A

        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp,
                    start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            NewsCardTop(content)

            CustomHorizontalDivider(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp))

            NewsCardMainSection(hostingScreen, content)

            CustomHorizontalDivider(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp))

            NewsButtonSection(
                content = content,
                onFavoriteClick = onFavoriteClick,
                onReadClick = onReadClick
            )
        }
    }
}

@Composable
private fun NewsCardTop(content: UINews) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChipCard(text = categoryToUI(content.category))

        TextWithIcon(
            text = content.timeSinceCreation,
            iconDescription = stringResource(R.string.clock_icon_description),
            iconRes = painterResource(R.drawable.clock)
        )
    }
}

@Composable
private fun NewsCardMainSection(
    hostingScreen: UserHostingScreen,
    content: UINews
) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {

        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            SecondaryText(text = content.type)

            SecondaryText(
                text = "${stringResource(R.string.time_to_read)} ${content.timeToRead}"
            )
        }

        Image(
            painter = rememberAsyncImagePainter(content.previewImagePath),
            contentDescription = null,
            modifier = Modifier.fillMaxWidth().height(120.dp),
            contentScale = ContentScale.Crop
        )

        SubtitleText(
            text = content.title,
            modifier = Modifier.align(Alignment.Start)
        )

        ArticleText(
            text = content.content,
            maxLines = if (hostingScreen == UserHostingScreen.News) 3
            else Int.MAX_VALUE
        )
    }
}

@Composable
private fun NewsButtonSection(
    content: UINews,
    onFavoriteClick: () -> Unit,
    onReadClick: () -> Unit
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        val onClickFav = onFavoriteClick
        val onClickRead = onReadClick

        NewsButton(
            isActive = content.isRead,
            activeIcon = painterResource(R.drawable.readstatus),
            unactiveIcon = painterResource(R.drawable.unreadstatus),
            activeDescription = stringResource(
                R.string.read_icon_description),
            unactiveDescription = stringResource(
                R.string.unread_icon_description),
            onClick = onClickRead
        )

        Spacer(modifier = Modifier.width(6.dp))

        NewsButton(
            isActive = content.isFavorite,
            activeIcon = painterResource(R.drawable.favorite),
            unactiveIcon = painterResource(R.drawable.notfavorite),
            activeDescription = stringResource(
                R.string.move_from_favorites_icon_description),
            unactiveDescription = stringResource(
                R.string.add_to_favorites_icon_description),
            onClick = onClickFav
        )
    }
}