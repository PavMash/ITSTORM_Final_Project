package com.itstorm.core_domain.models.user

data class UserDomain(
    val id: Long = 0L,
    val name: String,
    val phoneNumber: String,
    val password: String,
    val isBlocked: Boolean,
    val isOnline: Boolean,
    val role: UserRole
)

fun UserDomain.toUserWithSessions(): UserWithSessionsDomain =
    UserWithSessionsDomain(
        id = id,
        name = name,
        phoneNumber = phoneNumber,
        password = password,
        isBlocked = isBlocked,
        isOnline = isOnline,
        role = role,
        sessions = emptyList()
    )