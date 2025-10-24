package com.itstorm.core_domain.models.session

import com.itstorm.core_domain.models.station.StationDomain
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.user.UserDomain
import java.time.Instant

class SessionWithUserDomain (
    val id: Long = 0L,
    val user: UserDomain,
    val station: StationDomain,
    val start: Instant,
    val end: Instant,
    val mainTariff: TariffDomain,
    val currentTariff: TariffDomain,
    val status: SessionStatus,
    val sum: Int
)