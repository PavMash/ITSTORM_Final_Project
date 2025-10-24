package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.IGNORE
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update
import com.itstorm.core_data.db.entities.UserEntity
import com.itstorm.core_data.db.entities.UserWithSessions
import com.itstorm.core_domain.models.user.UserRole
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = IGNORE)
    fun insertUser(user: UserEntity): Long

    @Insert(onConflict = IGNORE)
    suspend fun insertAll(users: List<UserEntity>)

    @Transaction
    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): UserWithSessions?

    @Transaction
    @Query("""
    SELECT * FROM users
    WHERE name LIKE '%' || :name || '%'
    ORDER BY name ASC
    """)
    suspend fun getUserByName(name: String): UserWithSessions?

    @Transaction
    @Query("SELECT * FROM users WHERE role != :role ORDER BY name ASC")
    fun getAllExceptAdmin(role: UserRole = UserRole.Admin): Flow<List<UserWithSessions>>

    @Update
    suspend fun update(updatedUser: UserEntity)

    @Query("DELETE FROM users WHERE id = :id")
    suspend fun deleteUserById(id: Long)

    @Query("DELETE FROM users")
    suspend fun clearAllUsers()
}