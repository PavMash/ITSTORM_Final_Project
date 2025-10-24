package com.itstorm.core_data.db.entities

import androidx.room.Embedded
import androidx.room.Relation


data class SessionWithRelations(
    @Embedded val session: SessionEntity,

    @Relation(
        parentColumn = "mainTariffId",
        entityColumn = "id"
    )
    val mainTariff: TariffEntity,

    @Relation(
        parentColumn = "currentTariffId",
        entityColumn = "id"
    )
    val currentTariff: TariffEntity,

    @Relation(
        parentColumn = "stationId",
        entityColumn = "id"
    )
    val station: StationEntity,

    @Relation(
        parentColumn = "userId",
        entityColumn = "id"
    )
    val user: UserEntity
)