package com.itstorm.finalproject.features.weather.view

import com.itstorm.finalproject.features.weather.store.WeatherStore
import kotlinx.coroutines.flow.StateFlow

interface WeatherComponent {
    val model: StateFlow<WeatherStore.State>

    fun onEstimate(city: String, temperature: Int)

    fun onRetryEstimation()

    fun onCityValidate(city: String)

    fun onTemperatureValidate(temperature: String)

    fun onNewsClicked()

    fun onFavoritesClicked()

    fun getStartTime(): String

    fun getEndTime(): String
}