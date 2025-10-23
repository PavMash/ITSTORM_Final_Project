package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.itstorm.core_data.db.entities.SessionEntity
import com.itstorm.core_data.db.entities.SessionWithRelations
import kotlinx.coroutines.flow.Flow

@Dao
interface SessionDao {
    @Insert(onConflict = IGNORE)
    suspend fun insertSession(session: SessionEntity): Long

    @Insert(onConflict = IGNORE)
    suspend fun insertAllSessions(sessions: List<SessionEntity>)

    @Transaction
    @Query("SELECT * FROM sessions WHERE id = :id")
    suspend fun getSessionById(id: Long): SessionWithRelations?

    @Transaction
    @Query("SELECT * FROM sessions")
    fun getAllSessions(): Flow<List<SessionWithRelations>>

    @Update
    suspend fun updateSession(session: SessionEntity)

    @Query("DELETE FROM sessions WHERE id = :id")
    suspend fun deleteSessionById(id: Long)

    @Query("DELETE FROM sessions")
    suspend fun clearAllSessions()
}