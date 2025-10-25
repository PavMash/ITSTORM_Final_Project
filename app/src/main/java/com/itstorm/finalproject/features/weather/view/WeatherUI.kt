package com.itstorm.finalproject.features.weather.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.itstorm.features.weather.presentation.components.EstimationCard
import com.itstorm.core_domain.models.weather.EstimationState
import com.itstorm.core_domain.models.weather.WeatherEstimation
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.weather.components.EstimationTextField
import com.itstorm.finalproject.features.weather.components.SessionTimer
import com.itstorm.finalproject.root.helper_enums.UserHostingScreen
import com.itstorm.finalproject.shared.components.MainButton
import com.itstorm.finalproject.shared.components.SectionTitle
import com.itstorm.finalproject.shared.components.SubtitleText
import com.itstorm.finalproject.shared.components.UserBottomNavBar
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import com.itstorm.finalproject.shared.ui.theme.Grey2F
import com.itstorm.finalproject.shared.ui.theme.GreyB2
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun WeatherUI(component: WeatherComponent) {
    FinalProjectTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Black,
            bottomBar = {
                BottomAppBar(
                    modifier = Modifier.fillMaxWidth()
                        .windowInsetsPadding(
                            WindowInsets.navigationBars),
                    containerColor = Black
                ) {
                    UserBottomNavBar(
                        hostingScreen = UserHostingScreen.Weather,
                        onWeatherClick = {},
                        onNewsClick = component::onNewsClicked,
                        onFavoritesClick = component::onFavoritesClicked
                    )
                }
            }
        ) { innerPadding ->
            WeatherScreen(component, innerPadding)
        }
    }
}

@Composable
private fun WeatherScreen(component: WeatherComponent, innerPadding: PaddingValues) {
    val state by component.model.collectAsState()

    Column(modifier = Modifier.fillMaxSize()
        .padding(innerPadding))
    {
        Spacer(modifier = Modifier.height(8.dp))

        SessionTimer(startTime = component.getStartTime(),
            endTime = component.getEndTime(),
            modifier = Modifier.align(Alignment.CenterHorizontally))

        Spacer(modifier = Modifier.height(18.dp))

        SectionTitle(
            text = stringResource(R.string.weather_page_navigation_text),
            modifier = Modifier.align(Alignment.Start)
            .padding(start = 17.dp, top = 38.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        when(val state = state.estimationState) {
            is EstimationState.InProgress ->
                CityAndTemperatureInput(
                    city = state.input.city,
                    temperature = state.input.temperature,
                    isCityValid = state.validationResults.isCityValid,
                    isTemperatureValid = state.validationResults.isTemperatureValid,
                    validateCity = component::onCityValidate,
                    validateTemperature = component::onTemperatureValidate,
                    onEstimate = component::onEstimate
                )
            is EstimationState.Success ->
                LatestEstimationCard(
                    city = state.estimation.city,
                    temperature = state.estimation.temperature,
                    verdict = state.estimation.verdict,
                    onRetryEstimation = component::onRetryEstimation
                )
        }

        Spacer(modifier = Modifier.height(40.dp))

        EstimationHistorySection(estimations = state.estimations)
    }
}

@Composable
private fun CityAndTemperatureInput(
    city: String,
    temperature: String,
    isCityValid: Boolean,
    isTemperatureValid: Boolean,
    validateCity: (city: String) -> Unit,
    validateTemperature: (temperature: String) -> Unit,
    onEstimate: (city: String, temperature: Int) -> Unit
) {
    Column(modifier = Modifier.fillMaxWidth()
        .padding(horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EstimationTextField(
            value = city,
            validate = validateCity,
            label = stringResource(R.string.city_textfield_label),
            modifier = Modifier.fillMaxWidth()
        )

        EstimationTextField(
            value = temperature,
            validate = validateTemperature,
            label = stringResource(R.string.temperature_textfield_label),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        val onClick = { onEstimate(
            city, temperature.toIntOrNull() ?: 0) }
        MainButton(
            enabled = isCityValid && isTemperatureValid,
            onClick = onClick,
            label = stringResource(R.string.estimate_button_text),
            modifier = Modifier.align(Alignment.End)
        )
    }
}

@Composable
private fun LatestEstimationCard(
    city: String,
    temperature: Int,
    verdict: String,
    onRetryEstimation: () -> Unit) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(start = 7.dp, end = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        EstimationCard(
            belongsToHistorySection = false,
            city = city,
            temperature = temperature,
            verdict = verdict,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(12.dp))

        MainButton(
            enabled = true,
            onClick = onRetryEstimation,
            label = stringResource(R.string.retry_estimation_button_text),
            modifier = Modifier.align(Alignment.End)
                .padding(horizontal = 12.dp, vertical = 4.dp)
        )
    }
}

@Composable
private fun EstimationHistorySection(
    estimations: List<WeatherEstimation>
) {
    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(horizontal = 7.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        SubtitleText(
            text = stringResource(R.string.search_history_section_title),
            modifier = Modifier.align(Alignment.Start)
                .padding(start = 10.dp)
        )

        Spacer(modifier = Modifier.height(6.dp))

        if (estimations.isNotEmpty())
            EstimationHistory(estimations)
        else EstimationHistoryPlaceholder()
    }
}

@Composable
private fun EstimationHistory(estimations: List<WeatherEstimation>) {
    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {
        items(estimations) { estimation ->
            EstimationCard(
                belongsToHistorySection = true,
                city = estimation.city,
                temperature = estimation.temperature,
                verdict = estimation.verdict
            )
        }
    }
}

@Composable
private fun EstimationHistoryPlaceholder() {
    Box(
        modifier = Modifier.fillMaxWidth()
            .background(
                color = Grey2F,
                shape = RoundedCornerShape(6.dp))
            .padding(top = 8.dp, bottom = 8.dp,
                start = 11.5.dp, end = 11.5.dp),
    ) {
        Text(
            modifier = Modifier.align(Alignment.Center),
            text = stringResource(R.string.search_history_placeholder_text),
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 14.sp,
            color = GreyB2
        )
    }
}