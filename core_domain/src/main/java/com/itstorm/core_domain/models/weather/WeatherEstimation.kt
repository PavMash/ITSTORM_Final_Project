package com.itstorm.core_domain.models.weather

data class WeatherEstimation(
    val city : String,
    val temperature : Int,
    val verdict : String
)