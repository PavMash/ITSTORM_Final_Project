package com.itstorm.core_domain.models.user

import com.itstorm.core_domain.models.sesssion.Session

data class User(
    val id: Long,
    val name: String,
    val phoneNumber: String,
    val login: String,
    val password: String,
    val isBlocked: Boolean,
    val sessions: List<Session>
)