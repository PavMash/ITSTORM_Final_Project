package com.itstorm.core_domain.models.station

import com.itstorm.core_domain.models.session.SessionDomain

data class StationWithSessionsDomain(
    val id: Long = 0L,
    val code: String,
    val sessions: List<SessionDomain>
)

fun StationWithSessionsDomain.toStationDomain(): StationDomain =
    StationDomain(
        id = id,
        code = code
    )

fun StationWithSessionsDomain.toText(): String = code