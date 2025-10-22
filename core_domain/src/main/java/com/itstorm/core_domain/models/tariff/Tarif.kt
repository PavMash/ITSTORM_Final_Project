package com.itstorm.core_domain.models.tariff

data class Tariff (
    val title: String,
    val pricePerHourCents: Int,
    val minBillableMinutes : Int,
    val roundingStepMinutes: Int
)