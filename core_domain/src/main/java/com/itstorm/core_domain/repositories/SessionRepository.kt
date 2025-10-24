package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    fun getAllSessions(): Flow<List<SessionWithUserDomain>>

    suspend fun getSessionById(id: Long): SessionWithUserDomain?

    suspend fun updateSessionStatus(id: Long, status: SessionStatus)

    suspend fun updateStation(id: Long, stationCode: Long)

    suspend fun createSession(session: SessionWithUserDomain): Long

    suspend fun deleteSessionById(id: Long)

    suspend fun clearAllSessions()
}