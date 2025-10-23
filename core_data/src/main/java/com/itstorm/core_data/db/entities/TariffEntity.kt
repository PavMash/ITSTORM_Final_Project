package com.itstorm.core_data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.itstorm.core_domain.models.tariff.TariffType

@Entity(tableName = "tariffs")
data class TariffEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val title: String,
    val pricePerHourCents: Int,
    val minBillableMinutes: Int,
    val roundingStepMinutes: Int,
    val type: TariffType
)