package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.TariffEntity
import com.itstorm.core_domain.models.tariff.TariffDomain

fun TariffEntity.toDomain(): TariffDomain =
    TariffDomain(
        id = id,
        title = title,
        pricePerHourCents = pricePerHourCents,
        minBillableMinutes = minBillableMinutes,
        roundingStepMinutes = roundingStepMinutes,
        type = type
    )

fun TariffDomain.toEntity(): TariffEntity =
    TariffEntity(
        id = id,
        title = title,
        pricePerHourCents = pricePerHourCents,
        minBillableMinutes = minBillableMinutes,
        roundingStepMinutes = roundingStepMinutes,
        type = type
    )