package com.itstorm.core_data.repositories

import com.itstorm.core_domain.models.user.User
import com.itstorm.core_domain.repositories.UserRepository

class UserRepositoryImpl(): UserRepository {
    override suspend fun getUserByLogin(login: String): User? = null //implement later
}