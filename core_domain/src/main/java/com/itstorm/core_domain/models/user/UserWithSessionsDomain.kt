package com.itstorm.core_domain.models.user

import com.itstorm.core_domain.models.session.SessionDomain

data class UserWithSessionsDomain(
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val password: String,
    val isBlocked: Boolean,
    val isOnline: Boolean,
    val role: UserRole,
    val sessions: List<SessionDomain>
)

fun UserWithSessionsDomain.toUserDomain(): UserDomain = UserDomain(
    id = id,
    name = name,
    phoneNumber = phoneNumber,
    password = password,
    isBlocked = isBlocked,
    isOnline = isOnline,
    role = role
)