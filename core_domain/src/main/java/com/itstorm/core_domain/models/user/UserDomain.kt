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