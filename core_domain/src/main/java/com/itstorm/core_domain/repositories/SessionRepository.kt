package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionWithUsersDomain
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getAllSessions(): Flow<List<SessionWithUsersDomain>>

    suspend fun getSessionById(id: Long): SessionWithUsersDomain?

    suspend fun updateSessionStatus(id: Long, status: SessionStatus)

    suspend fun updateStation(id: Long, stationCode: Long)

    suspend fun createSession(session: SessionWithUsersDomain): Long

    suspend fun deleteSessionById(id: Long)

    suspend fun clearAllSessions()
}