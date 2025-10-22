package com.itstorm.core_domain.models.sesssion

import com.itstorm.core_domain.models.statoin.Station
import com.itstorm.core_domain.models.tariff.Tariff
import com.itstorm.core_domain.models.user.User
import java.time.Instant

data class Session(
    val users: List<User>,
    val station: Station,
    val start: Instant,
    val end: Instant,
    val tariff: Tariff,
    val status: SessionStatus
)