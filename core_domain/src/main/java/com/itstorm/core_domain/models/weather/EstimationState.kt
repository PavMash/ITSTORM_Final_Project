package com.itstorm.core_domain.models.weather

sealed interface EstimationState {
    data class InProgress(val input: EstimationInput,
        val validationResults: ValidationResults): EstimationState
    data class Success(val estimation: WeatherEstimation): EstimationState
}

fun EstimationState.InProgress.copyWithSameTemperature(
    city: String, isValid: Boolean): EstimationState.InProgress =
    this.copy(
        input = this.input.copy(
            city = city,
            temperature = this.input.temperature
        ),
        validationResults = this.validationResults.copy(
            isCityValid = isValid,
            isTemperatureValid = this.validationResults.isTemperatureValid
        )
    )

fun EstimationState.InProgress.copyWithSameCity(
    temperature: String, isValid: Boolean): EstimationState.InProgress =
    this.copy(
        input = this.input.copy(
            city = this.input.city,
            temperature = temperature
        ),
        validationResults = this.validationResults.copy(
            isCityValid = this.validationResults.isCityValid,
            isTemperatureValid = isValid
        )
    )