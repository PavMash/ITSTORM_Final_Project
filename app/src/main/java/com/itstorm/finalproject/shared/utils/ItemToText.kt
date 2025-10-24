package com.itstorm.finalproject.shared.utils

import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.station.toText
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.tariff.toText

fun<T> itemToText(item: T): String =
    when(item) {
        is StationWithSessionsDomain -> item.toText()
        is TariffDomain -> item.toText()
        else -> ""
    }