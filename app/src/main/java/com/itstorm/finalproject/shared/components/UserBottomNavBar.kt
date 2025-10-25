package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.R
import com.itstorm.finalproject.root.helper_enums.UserHostingScreen

@Composable
fun UserBottomNavBar(
    hostingScreen: UserHostingScreen,
    onWeatherClick: () -> Unit,
    onNewsClick: () -> Unit,
    onFavoritesClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        UserBottomNavButton(
            painter = painterResource(R.drawable.weather),
            label = stringResource(R.string.weather_page_navigation_text),
            enabled = (hostingScreen != UserHostingScreen.Weather),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onWeatherClick() }

        UserBottomNavButton(
            painter = painterResource(R.drawable.news),
            label = stringResource(R.string.news_page_navigation_text),
            enabled = (hostingScreen != UserHostingScreen.News),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onNewsClick() }

        UserBottomNavButton(
            painter = painterResource(R.drawable.heart),
            label = stringResource(R.string.favourites_page_navigation_text),
            enabled = (hostingScreen != UserHostingScreen.Favorites),
            modifier = Modifier.weight(1f).fillMaxHeight(),
        ) { onFavoritesClick() }
    }
}

