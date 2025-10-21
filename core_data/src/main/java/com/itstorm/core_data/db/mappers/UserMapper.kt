package com.itstorm.core_data.db.mappers

import com.itstorm.core_data.db.entities.UserEntity
import com.itstorm.core_domain.models.user.User

fun UserEntity.toUser(): User =
    User(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        isBlocked = isBlocked,
        isOnline = isOnline,
        role = role,
    )

fun User.toEntity(): UserEntity =
    UserEntity(
        id = if (id == 0L) 0L else id,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        isBlocked = isBlocked,
        isOnline = isOnline,
        role = role,
    )