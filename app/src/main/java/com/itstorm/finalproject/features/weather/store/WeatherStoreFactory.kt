package com.itstorm.finalproject.features.weather.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.weather.EstimationInput
import com.itstorm.core_domain.models.weather.EstimationState
import com.itstorm.core_domain.models.weather.ValidationResults
import com.itstorm.core_domain.models.weather.WeatherEstimation
import com.itstorm.core_domain.models.weather.copyWithSameCity
import com.itstorm.core_domain.models.weather.copyWithSameTemperature
import kotlinx.coroutines.launch

class WeatherStoreFactory (private val storeFactory : StoreFactory) {

    fun create(): WeatherStore =
        object:  WeatherStore, Store<WeatherStore.Intent,
                WeatherStore.State, WeatherStore.Label> by storeFactory.create(
            name = "WeatherStore",
            initialState = WeatherStore.State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::WeatherExecutor,
            reducer = WeatherReducer
        ) {}

    private sealed interface Msg {
        data class EstimationDone(val newEstimation: WeatherEstimation) : Msg
        data class CityNameValidated(val newCityName:String, val validationResult: Boolean): Msg
        data class TemperatureValidated(val newTemperature:String, val validationResult: Boolean): Msg
        data object BringBackEstimationInterface : Msg
    }

    private class WeatherExecutor: CoroutineExecutor<WeatherStore.Intent, Unit,
            WeatherStore.State, Msg, WeatherStore.Label>() {
        override fun executeIntent(intent: WeatherStore.Intent) =
            when(intent) {
                is WeatherStore.Intent.EstimateCityWeather ->
                    estimateCityWeather(
                        city = intent.city,
                        temperature = intent.temperature
                    )
                is WeatherStore.Intent.RetryEstimation ->
                    retryEstimation()
                is WeatherStore.Intent.ValidateCityName ->
                    validateCityName(intent.city)
                is WeatherStore.Intent.ValidateTemperature ->
                    validateTemperature(intent.temperature)
                is WeatherStore.Intent.ClickNews ->
                    publish(WeatherStore.Label.ClickNews)
                is WeatherStore.Intent.ClickFavorites ->
                    publish(WeatherStore.Label.ClickFavorites)
            }

        private fun estimateCityWeather(city: String, temperature: Int) {
            scope.launch {
                val verdict = when {
                    -30 <= temperature && temperature <= 13
                        -> "холодно"

                    14 <= temperature && temperature <= 25
                        -> "тепло"

                    26 <= temperature && temperature <= 35
                        -> "жарко"

                    else -> "катастрофа"
                }

                val newEstimation = WeatherEstimation(city, temperature, verdict)
                dispatch(Msg.EstimationDone(newEstimation))
            }
        }

        private fun validateCityName(city: String) {
            val nonEmptyAndBlank = city.isNotBlank() && city.isNotEmpty()
            val cyrillicPattern = Regex("\\p{IsCyrillic}")
            val containsCyrillicLetters = cyrillicPattern.containsMatchIn(city)

            val result = nonEmptyAndBlank && containsCyrillicLetters
            dispatch(Msg.CityNameValidated(city, result))
        }

        private fun validateTemperature(temperature: String) {
            val nonEmptyAndBlank = temperature.isNotBlank() && temperature.isNotEmpty()
            val numericPattern = Regex("^-?\\d+$")
            val isNumerical = numericPattern.matches(temperature)

            val result = nonEmptyAndBlank && isNumerical
            dispatch(Msg.TemperatureValidated(
                temperature,
                result))
        }

        private fun retryEstimation() {
            dispatch(Msg.BringBackEstimationInterface)
        }

    }

    private object WeatherReducer: Reducer<WeatherStore.State, Msg> {
        override fun WeatherStore.State.reduce(msg: Msg): WeatherStore.State =
            when (msg) {
                is Msg.EstimationDone ->
                    copy(
                        estimations = listOf(msg.newEstimation) + estimations,
                        estimationState = EstimationState.Success(msg.newEstimation)
                    )

                is Msg.BringBackEstimationInterface ->
                    copy(
                        estimations = estimations,
                        estimationState = EstimationState.InProgress(
                            EstimationInput(),
                            ValidationResults()
                        )
                    )

                is Msg.CityNameValidated -> {
                    if (this.estimationState is EstimationState.InProgress) {
                        val newEstimationState =
                            this.estimationState.copyWithSameTemperature(
                                city = msg.newCityName,
                                isValid = msg.validationResult)

                        copy(estimations = estimations,
                            estimationState = newEstimationState)
                    } else {
                        this
                    }
                }

                is Msg.TemperatureValidated -> {
                    if (this.estimationState is EstimationState.InProgress) {
                        val newEstimationState =
                            this.estimationState.copyWithSameCity(
                                temperature = msg.newTemperature,
                                isValid = msg.validationResult)

                        copy(estimations = estimations,
                            estimationState = newEstimationState)
                    } else {
                        this
                    }
                }

            }
    }
}