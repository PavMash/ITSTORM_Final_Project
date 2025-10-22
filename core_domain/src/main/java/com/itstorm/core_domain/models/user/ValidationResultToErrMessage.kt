package com.itstorm.core_domain.models.user

fun UserValidationResult.toErrMessage(): String =
    when(this) {
        UserValidationResult.WrongLogin ->
            "Неверный логин"
        UserValidationResult.WrongPassword ->
            "Неверный пароль"
        UserValidationResult.NoLatinLettersInPw ->
            "Пароль должен содержать хотя бы одну латинскую букву"
        UserValidationResult.NoDigitsInPw ->
            "Пароль должен содержать хотя бы одну цифру"
        UserValidationResult.ShortPw ->
            "Пароль должен содержать не менее 6 символов"
        UserValidationResult.InvalidPhoneNumber ->
            "Неверный формат номера телефона"
        else -> ""
    }