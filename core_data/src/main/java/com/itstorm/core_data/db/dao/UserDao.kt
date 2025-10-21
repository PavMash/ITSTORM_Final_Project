package com.itstorm.core_data.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy.Companion.ABORT
import androidx.room.Query
import androidx.room.Update
import com.itstorm.core_data.db.entities.UserEntity
import com.itstorm.core_domain.models.user.UserRole
import kotlinx.coroutines.flow.Flow


@Dao
interface UserDao {
    @Insert(onConflict = ABORT)
    fun insertUser(user: UserEntity): Long

    @Insert
    suspend fun insertAll(users: List<UserEntity>)

    @Query("SELECT * FROM users WHERE id = :id")
    suspend fun getUserById(id: Long): UserEntity?

    @Query("""
    SELECT * FROM users
    WHERE name LIKE '%' || :name || '%'
    ORDER BY name ASC
    """)
    suspend fun getUserByName(name: String): UserEntity?

    @Query("SELECT * FROM users WHERE role != :role ORDER BY name ASC")
    fun getAllExceptAdmin(role: UserRole = UserRole.Admin): Flow<List<UserEntity>>

    @Update
    suspend fun update(updatedNews: UserEntity)

    @Delete
    suspend fun deleteUserById(id: Long)
}