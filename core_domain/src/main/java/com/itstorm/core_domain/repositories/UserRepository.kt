package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.user.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByLogin(login: String): User?

    fun getAllUsers(): Flow<List<User>>

    suspend fun updateBlockedStatus(id: Long)

    suspend fun updateOnlineStatus(id: Long)

    suspend fun preloadIfEmpty()
}