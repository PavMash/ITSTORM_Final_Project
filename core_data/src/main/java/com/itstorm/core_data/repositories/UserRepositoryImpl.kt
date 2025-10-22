package com.itstorm.core_data.repositories

import com.itstorm.core_data.db.dao.UserDao
import com.itstorm.core_data.db.entities.UserEntity
import com.itstorm.core_data.db.mappers.toEntity
import com.itstorm.core_data.db.mappers.toUser
import com.itstorm.core_domain.models.user.User
import com.itstorm.core_domain.models.user.UserRole
import com.itstorm.core_domain.repositories.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class UserRepositoryImpl(
    private val userDao: UserDao
): UserRepository {
    override suspend fun getUserByLogin(login: String): User? =
        userDao.getUserByName(login)?.toUser()

    override fun getAllUsers(): Flow<List<User>> =
        userDao.getAllExceptAdmin().map { it.map(UserEntity::toUser) }

    override suspend fun updateBlockedStatus(id: Long) {
        val user = userDao.getUserById(id)
        user?.let {
            userDao.update(
                user.copy(isBlocked = !user.isBlocked)
            )
        }
    }

    override suspend fun updateOnlineStatus(id: Long) {
        val user = userDao.getUserById(id)
        user?.let {
            userDao.update(
                user.copy(isOnline = !user.isOnline)
            )
        }
    }

    override suspend fun addUser(user: User) {
        userDao.insertUser(user.toEntity())
    }

    override suspend fun clearAllUsers() {
        userDao.clearAllUsers()
    }

    override suspend fun preloadIfEmpty() {
        if (userDao.getAllExceptAdmin().first().isEmpty()) {
            userDao.insertAll(initialUsers.map { it.toEntity() })
        }
    }

    private val initialUsers = listOf(
        User(
            name = "Вася",
            phoneNumber = "+71234567890",
            password = "pass12345",
            isBlocked = false,
            isOnline = false,
            role = UserRole.User
        ),
        User(
            name = "Ваня",
            phoneNumber = "+91235671234",
            password = "12345pass",
            isBlocked = false,
            isOnline = false,
            role = UserRole.User
        ),
        User(
            name = "James",
            phoneNumber = "+13569023457",
            password = "smart_pass1",
            isBlocked = false,
            isOnline = false,
            role = UserRole.User
        ),
        User(
            name = "admin",
            phoneNumber = "+71111111111",
            password = "admin1",
            isBlocked = false,
            isOnline = false,
            role = UserRole.Admin
        )
    )
}