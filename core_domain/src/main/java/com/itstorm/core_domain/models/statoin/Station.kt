package com.itstorm.core_domain.models.statoin

import com.itstorm.core_domain.models.sesssion.Session

class Station(
    val code: Long,
    val sessions: List<Session>
)