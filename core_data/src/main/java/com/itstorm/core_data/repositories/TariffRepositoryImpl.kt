package com.itstorm.core_data.repositories

import com.itstorm.core_data.db.dao.TariffDao
import com.itstorm.core_data.db.entities.TariffEntity
import com.itstorm.core_data.db.mappers.toDomain
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.tariff.TariffType
import com.itstorm.core_domain.repositories.TariffRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class TariffRepositoryImpl(
    private val tariffDao: TariffDao
): TariffRepository {
    override suspend fun addTariff(tariff: TariffDomain): Long =
        tariffDao.insertTariff(tariff.toEntity())

    override suspend fun getTariffById(id: Long): TariffDomain? =
        tariffDao.getTariffById(id)?.toDomain()

    override fun getAllTariffs(): Flow<List<TariffDomain>> =
        tariffDao.getAllTariffs().map { it.map(TariffEntity::toDomain) }

    override suspend fun deleteTariffById(id: Long) {
        tariffDao.deleteTariffById(id)
    }

    override suspend fun clearAllTariffs() {
        tariffDao.clearAllTariffs()
    }

    override suspend fun preloadIfEmpty() {
        if (tariffDao.getAllTariffs().first().isEmpty()) {
            tariffDao.insertAllTariffs(initialTariffs.map { it.toEntity() })
        }
    }

    val initialTariffs = listOf(
        TariffDomain(
            title = "Pause",
            pricePerHourCents = 50,
            minBillableMinutes = 1,
            roundingStepMinutes = 5,
            type = TariffType.System
        ),

        TariffDomain(
            title = "Student",
            pricePerHourCents = 100,
            minBillableMinutes = 2,
            roundingStepMinutes = 15,
            type = TariffType.Commercial,
        ),

        TariffDomain(
            title = "Regular",
            pricePerHourCents = 200,
            minBillableMinutes = 5,
            roundingStepMinutes = 10,
            type = TariffType.Commercial
        )
    )
}