package com.itstorm.core_data.repositories

import com.itstorm.core_data.db.dao.StationDao
import com.itstorm.core_data.db.entities.StationEntity
import com.itstorm.core_data.db.entities.StationWithSessions
import com.itstorm.core_data.db.mappers.toDomain
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_domain.models.station.StationDomain
import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.repositories.StationRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class StationRepositoryImpl(
    private val stationDao: StationDao
): StationRepository {

    override suspend fun addStation(code: String): Long =
        stationDao.insertStation(StationEntity(code = code))

    override suspend fun getStationById(id: Long): StationWithSessionsDomain? =
        stationDao.getStationById(id)?.toDomain()

    override fun getAllStations(): Flow<List<StationWithSessionsDomain>> =
        stationDao.getAllStations().map { it.map(StationWithSessions::toDomain) }

    override suspend fun deleteStationById(id: Long) {
        stationDao.deleteStationById(id)
    }

    override suspend fun clearAllStations() {
        stationDao.clearAllStations()
    }

    override suspend fun preloadIfEmpty() {
        if (!stationDao.getAllStations().first().isEmpty()) {
            stationDao.insertAllStations(initialStations.map { it.toEntity() })
        }
    }

    val initialStations = listOf(
        StationDomain(
            code = "PC-01"
        ),

        StationDomain(
            code = "PC-02"
        ),

        StationDomain(
            code = "PC-03"
        ),

        StationDomain(
            code = "PC-04"
        )
    )
}