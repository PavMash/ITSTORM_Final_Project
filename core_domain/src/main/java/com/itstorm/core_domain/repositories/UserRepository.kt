package com.itstorm.core_domain.repositories

import com.itstorm.core_domain.models.user.User

interface UserRepository {
    suspend fun getUserByLogin(login: String): User?
}