package com.itstorm.core_data.db.converters

import androidx.room.TypeConverter
import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.tariff.TariffType
import com.itstorm.core_domain.models.user.UserRole
import java.time.Instant

class Converters {
    @TypeConverter
    fun roleToString(role: UserRole): String = role.name

    @TypeConverter
    fun stringToRole(role: String): UserRole = UserRole.valueOf(role)

    @TypeConverter
    fun instantToLong(instant: Instant?): Long? = instant?.toEpochMilli()

    @TypeConverter
    fun longToInstant(timestamp: Long?): Instant? =
        timestamp?.let { Instant.ofEpochMilli(it) }

    @TypeConverter
    fun sessionStatusToString(status: SessionStatus): String = status.name

    @TypeConverter
    fun stringToSessionStatus(status: String): SessionStatus = SessionStatus.valueOf(status)

    @TypeConverter
    fun tariffTypeToString(type: TariffType): String = type.name

    @TypeConverter
    fun stringToTariffType(type: String): TariffType = TariffType.valueOf(type)

}