package com.itstorm.core_domain.models.user

enum class UserValidationResult {
    WrongLogin,
    WrongPassword,
    NonCyrillicLogin,
    NoLatinLettersInPw,
    NoDigitsInPw,
    ShortPw,
    Valid
}