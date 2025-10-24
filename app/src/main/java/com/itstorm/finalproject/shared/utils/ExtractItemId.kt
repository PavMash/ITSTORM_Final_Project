package com.itstorm.finalproject.shared.utils

import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.tariff.TariffDomain

fun<T> extractItemId(item: T): Long =
    when(item) {
        is StationWithSessionsDomain -> item.id
        is TariffDomain -> item.id
        else -> 0L
    }