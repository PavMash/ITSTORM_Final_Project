package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.SessionEntity
import com.itstorm.core_data.db.entities.SessionWithRelations
import com.itstorm.core_domain.models.session.SessionDomain
import com.itstorm.core_domain.models.session.SessionWithUserDomain

fun SessionEntity.toDomain(): SessionDomain =
    SessionDomain(
        id = id,
        stationId = stationId,
        start = start,
        end = end,
        mainTariffId = mainTariffId,
        currentTariffId = currentTariffId,
        userId = userId,
        status = sessionStatus,
        sum = sum
    )

fun SessionWithRelations.toDomain(): SessionWithUserDomain =
    SessionWithUserDomain(
        id = session.id,
        user = user.toDomain(),
        station = station.toDomain(),
        start = session.start,
        end = session.end,
        mainTariff = mainTariff.toDomain(),
        currentTariff = currentTariff.toDomain(),
        status = session.sessionStatus,
        sum = session.sum
    )

fun SessionDomain.toEntity(): SessionEntity =
    SessionEntity(
        id = id,
        start = start,
        end = end,
        sessionStatus = status,
        sum = sum,
        mainTariffId = mainTariffId,
        currentTariffId = currentTariffId,
        stationId = stationId,
        userId = userId
    )