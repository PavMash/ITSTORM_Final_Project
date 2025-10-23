package com.itstorm.core_data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "stations",
    indices = [
        Index(value = ["code"], unique = true)
    ])
data class StationEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val code: String
)