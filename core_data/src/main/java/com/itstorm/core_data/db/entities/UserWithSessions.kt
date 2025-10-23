package com.itstorm.core_data.db.entities

import androidx.room.Embedded
import androidx.room.Junction
import androidx.room.Relation

data class UserWithSessions(
    @Embedded val user: UserEntity,

    @Relation(
        entity = SessionEntity::class,
        parentColumn = "id",
        entityColumn = "id",
        associateBy = Junction(
            value = UserSessionCrossRef::class,
            parentColumn = "userId",
            entityColumn = "sessionId"
        )
    )
    val sessions: List<SessionEntity>
)