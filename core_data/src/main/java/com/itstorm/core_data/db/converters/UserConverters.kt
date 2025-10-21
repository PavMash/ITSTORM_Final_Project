package com.itstorm.core_data.db.converters

import androidx.room.TypeConverter
import com.itstorm.core_domain.models.user.UserRole

class UserConverters {
    @TypeConverter
    fun roleToString(role: UserRole): String = role.name

    @TypeConverter
    fun stringToRole(role: String): UserRole = UserRole.valueOf(role)
}