package com.itstorm.core_data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

class StationWithSessions(
    @Embedded val station: StationEntity,

    @Relation(
        parentColumn = "code",
        entityColumn = "stationId"
    )
    val sessions: List<SessionEntity>
)