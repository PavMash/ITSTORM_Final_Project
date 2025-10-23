package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserWithSessionsDomain
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserByLogin(login: String): UserWithSessionsDomain?

    fun getAllUsers(): Flow<List<UserWithSessionsDomain>>

    suspend fun updateBlockedStatus(id: Long)

    suspend fun updateOnlineStatus(id: Long)

    suspend fun addUser(user: UserDomain): Long

    suspend fun clearAllUsers()

    suspend fun preloadIfEmpty()
}