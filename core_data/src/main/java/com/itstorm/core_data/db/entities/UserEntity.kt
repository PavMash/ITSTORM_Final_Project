package com.itstorm.core_data.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.itstorm.core_domain.models.user.UserRole

@Entity(tableName = "users",
    indices = [
        Index(value = ["phoneNumber"], unique = true),
        Index(value = ["name"], unique = true)
    ])
data class UserEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val password: String,
    val isBlocked: Boolean,
    val isOnline: Boolean,
    val role: UserRole
)