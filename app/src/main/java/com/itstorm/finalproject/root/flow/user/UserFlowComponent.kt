package com.itstorm.finalproject.root.flow.user

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itstorm.finalproject.features.favorites.view.NewsFavoritesComponent
import com.itstorm.finalproject.features.news.view.NewsComponent
import com.itstorm.finalproject.features.session_enter_restricted.view.SessionEnterRestrictedComponent
import com.itstorm.finalproject.features.weather.view.WeatherComponent
import kotlinx.serialization.Serializable

interface UserFlowComponent {
    val stack: Value<ChildStack<*, Child>>

    @Serializable
    sealed interface UserConfig {
        @Serializable
        data object Weather: UserConfig
        @Serializable
        data object News: UserConfig
        @Serializable
        data object Favorites: UserConfig
        @Serializable
        data object SessionEnterRestricted: UserConfig
    }

    sealed interface Child {
        class Weather(val component: WeatherComponent): Child
        class News(val component: NewsComponent): Child
        class Favorites(val component: NewsFavoritesComponent): Child
        class SessionEnterRestricted(val component: SessionEnterRestrictedComponent): Child
    }
}