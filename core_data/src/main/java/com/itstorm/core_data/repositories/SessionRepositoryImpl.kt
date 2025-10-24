package com.itstorm.core_data.repositories

import android.util.Log
import androidx.room.Transaction
import com.itstorm.core_data.db.dao.SessionDao
import com.itstorm.core_data.db.entities.SessionWithRelations
import com.itstorm.core_data.db.mappers.toDomain
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_domain.models.session.SessionDomain
import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.core_domain.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepositoryImpl(
    private val sessionDao: SessionDao
): SessionRepository {

    override fun getAllSessions(): Flow<List<SessionWithUserDomain>> =
        sessionDao.getAllSessions().map { it.map(SessionWithRelations::toDomain) }

    override suspend fun getSessionById(id: Long): SessionWithUserDomain? =
        sessionDao.getSessionById(id)?.toDomain()

    override suspend fun updateSessionStatus(id: Long, status: SessionStatus) {
        val session = sessionDao.getSessionById(id)
        session?.let {
            sessionDao.updateSession(session.session.copy(sessionStatus = status))
        }
    }

    override suspend fun updateStation(id: Long, stationCode: Long) {
        val session = sessionDao.getSessionById(id)
        session?.let {
            sessionDao.updateSession(session.session.copy(stationId = stationCode))
        }
    }

    @Transaction
    override suspend fun createSession(session: SessionWithUserDomain): Long {
        Log.d("sessionsDebug", "in repo")
        val sessionToInsert = SessionDomain(
            id = session.id,
            stationId = session.station.id,
            start = session.start,
            end = session.end,
            mainTariffId = session.mainTariff.id,
            currentTariffId = session.currentTariff.id,
            userId = session.user.id,
            status = session.status,
            sum = session.sum
        )
        Log.d("sessionsDebug", "created domain")
        Log.d("sessionsDebug", "mainTariffId=${session.mainTariff.id}, currentTariffId=${session.currentTariff.id}, stationId=${session.station.id}")
        Log.d("sessionsDebug", "Users: ${session.user.id}")

        val insertResult = sessionDao.insertSession(sessionToInsert.toEntity())

        return insertResult
    }

    override suspend fun deleteSessionById(id: Long) {
        sessionDao.deleteSessionById(id)
    }

    override suspend fun clearAllSessions() {
        sessionDao.clearAllSessions()
    }


}