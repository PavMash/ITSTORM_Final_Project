package com.itstorm.core_domain.models.user

enum class UserValidationResult {
    WrongLogin,
    WrongPassword,
    NoLatinLettersInPw,
    NoDigitsInPw,
    ShortPw,
    InvalidPhoneNumber,
    Valid,
    Initial
}