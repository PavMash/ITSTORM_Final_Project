package com.itstorm.finalproject.features.weather.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.itstorm.core_domain.models.weather.EstimationState
import com.itstorm.core_domain.models.weather.WeatherEstimation
import com.itstorm.finalproject.features.weather.store.WeatherStore
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
)
@Composable
fun WeatherUIPreview() {
    FinalProjectTheme {
        val fakeComponent = object : WeatherComponent {
            override val model = MutableStateFlow(
                WeatherStore.State(
                    estimationState = EstimationState.Success(
                        estimation = WeatherEstimation(
                            city = "Ульяновск",
                            temperature = 25,
                            verdict = "тепло"
                        )
                    ),
                    estimations = listOf(
                        WeatherEstimation("Москва", 30, "жарко"),
                        WeatherEstimation("Париж", 18, "тепло"),
                        WeatherEstimation("Томск", 20, "тепло")
                    )
                )
            )

            override fun onEstimate(city: String, temperature: Int) {}
            override fun onRetryEstimation() {}
            override fun onNewsClicked() {}
            override fun onFavoritesClicked() {}
            override fun onCityValidate(city: String) {}
            override fun onTemperatureValidate(temperature: String) {}
        }

        WeatherUI(component = fakeComponent)
    }
}
