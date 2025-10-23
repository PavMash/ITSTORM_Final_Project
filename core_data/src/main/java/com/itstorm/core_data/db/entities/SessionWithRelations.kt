package com.itstorm.core_data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
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
        entityColumn = "code"
    )
    val station: StationEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserSessionCrossRef::class,
            parentColumn = "sessionId",
            entityColumn = "userId"
        )
    )
    val users: List<UserEntity>
)