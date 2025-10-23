package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import com.itstorm.core_data.db.entities.StationEntity
import com.itstorm.core_data.db.entities.StationWithSessions
import kotlinx.coroutines.flow.Flow

@Dao
interface StationDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertStation(station: StationEntity): Long

    @Insert(onConflict = IGNORE)
    suspend fun insertAllStations(stations: List<StationEntity>)

    @Transaction
    @Query("SELECT * FROM stations WHERE id = :id")
    suspend fun getStationById(id: Long): StationWithSessions?

    @Transaction
    @Query("SELECT * FROM stations")
    fun getAllStations(): Flow<List<StationWithSessions>>

    @Query("DELETE FROM stations WHERE id = :id")
    fun deleteStationById(id: Long)

    @Query("DELETE FROM stations")
    fun clearAllStations()
}