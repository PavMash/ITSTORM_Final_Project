package com.itstorm.core_data.db.entities

import androidx.room.Embedded
import androidx.room.Relation

data class UserWithSessions(
    @Embedded val user: UserEntity,

    @Relation(
        parentColumn = "id",
        entityColumn = "userId",
    )
    val sessions: List<SessionEntity>
)