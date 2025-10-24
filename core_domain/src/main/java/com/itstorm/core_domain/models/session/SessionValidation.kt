package com.itstorm.core_domain.models.session

enum class SessionValidation {
    PastDate,
    PastTime,
    FractionMinutes,
    WrongDateFormat,
    WrongTimeFormat,
    WrongDurationFormat,
    Valid,
    Initial
}