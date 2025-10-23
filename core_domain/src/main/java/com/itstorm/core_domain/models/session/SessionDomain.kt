package com.itstorm.core_domain.models.session

import java.time.Instant

data class SessionDomain(
    val id: Long = 0L,
    val stationId: Long,
    val start: Instant,
    val end: Instant,
    val mainTariffId: Long,
    val currentTariffId: Long,
    val status: SessionStatus,
    val sum: Int
)