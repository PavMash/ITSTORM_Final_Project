package com.itstorm.core_data.db.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.Companion.SET_NULL
import androidx.room.PrimaryKey
import com.itstorm.core_domain.models.session.SessionStatus
import java.time.Instant

@Entity(
    tableName = "sessions",
    foreignKeys = [
        ForeignKey(
            entity = TariffEntity::class,
            parentColumns = ["id"],
            childColumns = ["mainTariffId"],
            onDelete = SET_NULL),

        ForeignKey(
            entity = TariffEntity::class,
            parentColumns = ["id"],
            childColumns = ["currentTariffId"],
            onDelete = SET_NULL),

        ForeignKey(
            entity = StationEntity::class,
            parentColumns = ["code"],
            childColumns = ["stationId"],
            onDelete = SET_NULL)
    ]
)
data class SessionEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val start: Instant,
    val end: Instant,
    val sessionStatus: SessionStatus,
    val sum: Int,
    val mainTariffId: Long,
    val currentTariffId: Long,
    val stationId: Long
)