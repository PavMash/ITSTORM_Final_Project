package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.tariff.TariffDomain
import kotlinx.coroutines.flow.Flow

interface TariffRepository {

    suspend fun addTariff(tariff: TariffDomain): Long

    suspend fun getTariffById(id: Long): TariffDomain?

    fun getAllTariffs(): Flow<List<TariffDomain>>

    suspend fun deleteTariffById(id: Long)

    suspend fun clearAllTariffs()

    suspend fun preloadIfEmpty()
}