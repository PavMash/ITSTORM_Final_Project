package com.itstorm.core_domain.models.tariff

data class TariffDomain (
    val id: Long = 0L,
    val title: String,
    val pricePerHourCents: Int,
    val minBillableMinutes : Int,
    val roundingStepMinutes: Int,
    val type: TariffType
)