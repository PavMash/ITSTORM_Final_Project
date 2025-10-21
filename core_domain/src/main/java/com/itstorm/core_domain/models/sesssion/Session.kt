package com.itstorm.core_domain.models.sesssion

import com.itstorm.core_domain.models.user.User
import java.time.Instant

data class Session(
    val users: List<User>,
    val start: Instant,
    val end: Instant
)