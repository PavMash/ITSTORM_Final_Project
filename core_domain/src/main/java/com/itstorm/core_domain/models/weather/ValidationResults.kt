package com.itstorm.core_domain.models.weather

data class ValidationResults(
    val isCityValid: Boolean = false,
    val isTemperatureValid: Boolean = false
)