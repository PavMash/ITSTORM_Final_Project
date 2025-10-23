package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Update
import com.itstorm.core_data.db.entities.TariffEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface TariffDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertTariff(tariff: TariffEntity): Long

    @Insert(onConflict = IGNORE)
    suspend fun insertAllTariffs(tariffs: List<TariffEntity>)

    @Query("SELECT * FROM tariffs WHERE id = :id")
    suspend fun getTariffById(id: Long): TariffEntity?

    @Query("SELECT * FROM tariffs")
    fun getAllTariffs(): Flow<List<TariffEntity>>

    @Update
    suspend fun updateTariff(tariff: TariffEntity)

    @Query("DELETE FROM tariffs WHERE id = :id")
    suspend fun deleteTariffById(id: Long)

    @Query("DELETE FROM tariffs")
    suspend fun clearAllTariffs()
}