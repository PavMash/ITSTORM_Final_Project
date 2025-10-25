package com.itstorm.finalproject.features.weather.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.itstorm.finalproject.features.weather.store.WeatherStore.Intent
import com.itstorm.finalproject.features.weather.store.WeatherStore.State
import com.itstorm.finalproject.features.weather.store.WeatherStore.Label
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.itstorm.finalproject.features.weather.store.WeatherStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultWeatherComponent(
    private val storeFactory: StoreFactory,
    componentContext: ComponentContext,
    private val startTime: String,
    private val endTime: String,
    onNewsClicked: () -> Unit,
    onFavoritesClicked: () -> Unit
): WeatherComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore { WeatherStoreFactory(storeFactory).create() }
    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.ClickNews -> {
                        onNewsClicked()
                    }
                    is Label.ClickFavorites -> {
                        onFavoritesClicked()
                    }
                }
            }
        }
    }

    override val model: StateFlow<State> = store.stateFlow

    override fun onEstimate(city: String, temperature: Int) {
        store.accept(
            Intent.EstimateCityWeather(city = city,
            temperature = temperature))
    }

    override fun onRetryEstimation() {
        store.accept(Intent.RetryEstimation)
    }

    override fun onCityValidate(city: String) {
        store.accept(Intent.ValidateCityName(city))
    }

    override fun onTemperatureValidate(temperature: String) {
        store.accept(Intent.ValidateTemperature(temperature))
    }

    override fun onNewsClicked() {
        store.accept(Intent.ClickNews)
    }

    override fun onFavoritesClicked() {
        store.accept(Intent.ClickFavorites)
    }

    override fun getStartTime(): String = startTime

    override fun getEndTime(): String = endTime
}