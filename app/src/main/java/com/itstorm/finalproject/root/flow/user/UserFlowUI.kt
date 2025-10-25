package com.itstorm.finalproject.root.flow.user

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itstorm.finalproject.features.favorites.view.NewsFavoritesUI
import com.itstorm.finalproject.features.news.view.NewsUI
import com.itstorm.finalproject.features.session_enter_restricted.view.SessionEnterRestrictedUI
import com.itstorm.finalproject.features.weather.view.WeatherUI

@Composable
fun UserFlowUI(component: UserFlowComponent) {
    Children(stack = component.stack,) { child ->
        when (val instance = child.instance) {
            is UserFlowComponent.Child.Weather -> WeatherUI(instance.component)
            is UserFlowComponent.Child.News -> NewsUI(instance.component)
            is UserFlowComponent.Child.Favorites -> NewsFavoritesUI(instance.component)
            is UserFlowComponent.Child.SessionEnterRestricted ->
                SessionEnterRestrictedUI(instance.component)
        }
    }
}