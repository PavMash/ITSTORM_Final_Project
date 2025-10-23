package com.itstorm.core_data.repositories

import com.itstorm.core_data.db.dao.SessionDao
import com.itstorm.core_data.db.dao.UserSessionCrossRefDao
import com.itstorm.core_data.db.entities.SessionWithRelations
import com.itstorm.core_data.db.entities.UserSessionCrossRef
import com.itstorm.core_data.db.mappers.toDomain
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_domain.models.session.SessionDomain
import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionWithUsersDomain
import com.itstorm.core_domain.repositories.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class SessionRepositoryImpl(
    private val sessionDao: SessionDao,
    private val userSessionDao: UserSessionCrossRefDao
): SessionRepository {

    override fun getAllSessions(): Flow<List<SessionWithUsersDomain>> =
        sessionDao.getAllSessions().map { it.map(SessionWithRelations::toDomain) }

    override suspend fun getSessionById(id: Long): SessionWithUsersDomain? =
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

    override suspend fun createSession(session: SessionWithUsersDomain): Long {
        val sessionToInsert = SessionDomain(
            id = session.id,
            stationId = session.station.id,
            start = session.start,
            end = session.end,
            mainTariffId = session.mainTariff.id,
            currentTariffId = session.currentTariff.id,
            status = session.status,
            sum = session.sum
        )

        val crossRefs = mutableListOf<UserSessionCrossRef>()
        for (user in session.users) {
            crossRefs.add(UserSessionCrossRef(
                userId = user.id,
                sessionId = session.id))
        }

        val insertResult = sessionDao.insertSession(sessionToInsert.toEntity())
        if (insertResult != -1L) {
            userSessionDao.insertAllCrossRefs(crossRefs)
        }
        return insertResult
    }

    override suspend fun deleteSessionById(id: Long) {
        sessionDao.deleteSessionById(id)
    }

    override suspend fun clearAllSessions() {
        sessionDao.clearAllSessions()
    }


}