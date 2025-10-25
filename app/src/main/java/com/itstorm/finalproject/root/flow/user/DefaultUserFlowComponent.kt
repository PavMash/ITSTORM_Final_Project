package com.itstorm.finalproject.root.flow.user

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_domain.repositories.NewsRepository
import com.itstorm.finalproject.features.favorites.view.DefaultNewsFavoritesComponent
import com.itstorm.finalproject.features.news.view.DefaultNewsComponent
import com.itstorm.finalproject.features.session_enter_restricted.view.DefaultSessionEnterRestrictedComponent
import com.itstorm.finalproject.features.weather.view.DefaultWeatherComponent

class DefaultUserFlowComponent(
    componentContext: ComponentContext,
    private val newsRepo: NewsRepository,
    private val hasAccess: Boolean,
    private val startTime: String,
    private val endTime: String,
): UserFlowComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<UserFlowComponent.UserConfig>()

    override val stack = childStack(
        source = navigation,
        serializer = UserFlowComponent.UserConfig.serializer(),
        childFactory = ::createChild,
        handleBackButton = true,
        initialConfiguration = if (hasAccess) UserFlowComponent.UserConfig.Weather
        else UserFlowComponent.UserConfig.SessionEnterRestricted
    )

    private fun createChild(
        config: UserFlowComponent.UserConfig,
        childContext: ComponentContext
    ) = when(config) {
        is UserFlowComponent.UserConfig.Weather -> UserFlowComponent.Child.Weather(
            component = DefaultWeatherComponent(
                storeFactory = DefaultStoreFactory(),
                startTime = startTime,
                endTime = endTime,
                componentContext = childContext,
                onNewsClicked = ::onNewsClicked,
                onFavoritesClicked = ::onFavoritesClicked
            )
        )
        is UserFlowComponent.UserConfig.News -> UserFlowComponent.Child.News(
            component = DefaultNewsComponent(
                storeFactory = DefaultStoreFactory(),
                newsRepo = newsRepo,
                componentContext = childContext,
                onWeatherClicked = ::onWeatherClicked,
                onFavoritesClicked = ::onFavoritesClicked
            )
        )
        is UserFlowComponent.UserConfig.Favorites -> UserFlowComponent.Child.Favorites(
            component = DefaultNewsFavoritesComponent(
                storeFactory = DefaultStoreFactory(),
                newsRepo,
                componentContext = childContext,
                onWeatherClicked = ::onWeatherClicked,
                onNewsClicked = ::onNewsClicked
            )
        )
        is UserFlowComponent.UserConfig.SessionEnterRestricted ->
            UserFlowComponent.Child.SessionEnterRestricted(
                component = DefaultSessionEnterRestrictedComponent(childContext)
            )

    }

    private fun onNewsClicked() {
        navigation.pushToFront(UserFlowComponent.UserConfig.News)
    }

    private fun onFavoritesClicked() {
        navigation.pushToFront(UserFlowComponent.UserConfig.Favorites)
    }

    private fun onWeatherClicked() {
        navigation.pushToFront(UserFlowComponent.UserConfig.Weather)
    }
}