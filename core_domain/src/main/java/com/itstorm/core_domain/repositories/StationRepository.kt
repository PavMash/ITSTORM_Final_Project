package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import kotlinx.coroutines.flow.Flow

interface StationRepository {

    suspend fun addStation(code: String): Long

    suspend fun getStationById(id: Long): StationWithSessionsDomain?

    fun getAllStations(): Flow<List<StationWithSessionsDomain>>

    suspend fun deleteStationById(id: Long)

    suspend fun clearAllStations()

    suspend fun preloadIfEmpty()
}