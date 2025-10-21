package com.itstorm.core_data.db

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.itstorm.core_data.db.converters.UserConverters
import com.itstorm.core_data.db.dao.UserDao
import com.itstorm.core_data.db.entities.UserEntity

@Database(entities = [UserEntity::class], version = 1)
@TypeConverters(UserConverters::class)
abstract class AppDataBase: RoomDatabase() {
    abstract fun userDao(): UserDao
}