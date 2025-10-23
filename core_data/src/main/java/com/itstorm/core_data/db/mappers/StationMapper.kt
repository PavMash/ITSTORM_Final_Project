package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.StationEntity
import com.itstorm.core_data.db.entities.StationWithSessions
import com.itstorm.core_domain.models.station.StationDomain
import com.itstorm.core_domain.models.station.StationWithSessionsDomain

fun StationWithSessions.toDomain(): StationWithSessionsDomain =
    StationWithSessionsDomain(
        id = station.id,
        code = station.code,
        sessions = sessions.map { it.toDomain() },
    )

fun StationEntity.toDomain(): StationDomain =
    StationDomain(
        id = id,
        code = code
    )

fun StationDomain.toEntity(): StationEntity =
    StationEntity(
        id = id,
        code = code
    )