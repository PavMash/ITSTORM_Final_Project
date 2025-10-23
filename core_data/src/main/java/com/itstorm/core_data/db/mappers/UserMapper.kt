package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.UserEntity
import com.itstorm.core_data.db.entities.UserWithSessions
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserWithSessionsDomain

fun UserWithSessions.toDomain(): UserWithSessionsDomain =
    UserWithSessionsDomain(
        id = user.id,
        name = user.name,
        phoneNumber = user.phoneNumber,
        password = user.password,
        isBlocked = user.isBlocked,
        isOnline = user.isOnline,
        role = user.role,
        sessions = sessions.map { it.toDomain() }
    )

fun UserEntity.toDomain(): UserDomain =
    UserDomain(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        isBlocked = isBlocked,
        isOnline = isOnline,
        role = role
    )

fun UserDomain.toEntity(): UserEntity =
    UserEntity(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        isBlocked = isBlocked,
        isOnline = isOnline,
        role = role,
    )