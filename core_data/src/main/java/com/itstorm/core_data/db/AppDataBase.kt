package com.itstorm.core_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itstorm.core_data.db.converters.Converters
import com.itstorm.core_data.db.dao.SessionDao
import com.itstorm.core_data.db.dao.StationDao
import com.itstorm.core_data.db.dao.TariffDao
import com.itstorm.core_data.db.dao.UserDao
import com.itstorm.core_data.db.entities.SessionEntity
import com.itstorm.core_data.db.entities.StationEntity
import com.itstorm.core_data.db.entities.TariffEntity
import com.itstorm.core_data.db.entities.UserEntity

@Database(entities = [UserEntity::class,
    SessionEntity::class,
    TariffEntity::class,
    StationEntity::class],
    version = 8)
@TypeConverters(Converters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao

    abstract fun sessionDao(): SessionDao

    abstract fun tariffDao(): TariffDao

    abstract fun stationDao(): StationDao
}