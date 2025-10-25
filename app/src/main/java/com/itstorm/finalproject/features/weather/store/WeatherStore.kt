package com.itstorm.finalproject.features.weather.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.weather.EstimationInput
import com.itstorm.core_domain.models.weather.EstimationState
import com.itstorm.core_domain.models.weather.ValidationResults
import com.itstorm.core_domain.models.weather.WeatherEstimation
import com.itstorm.finalproject.features.weather.store.WeatherStore.Intent
import com.itstorm.finalproject.features.weather.store.WeatherStore.State
import com.itstorm.finalproject.features.weather.store.WeatherStore.Label

interface WeatherStore : Store<Intent, State, Label>{
    
    sealed interface Intent {
        data class EstimateCityWeather(val city : String, val temperature : Int) : Intent
        data object RetryEstimation : Intent
        data class ValidateCityName(val city: String): Intent
        data class ValidateTemperature(val temperature: String): Intent
        data object ClickNews: Intent
        data object ClickFavorites: Intent
    }

    data class State (
        val estimations : List<WeatherEstimation> = emptyList(),
        val estimationState: EstimationState =
            EstimationState.InProgress(
                EstimationInput(),
                ValidationResults()
            )
    )

    sealed interface Label {
        data object ClickNews: Label
        data object ClickFavorites: Label
    }
}