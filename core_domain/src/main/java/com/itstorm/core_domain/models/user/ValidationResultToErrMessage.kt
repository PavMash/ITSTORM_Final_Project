package com.example.itstorm.features.authentication.domain

import com.itstorm.core_domain.models.user.UserValidationResult

fun UserValidationResult.toErrMessage(): String =
    when(this) {
        UserValidationResult.WrongLogin ->
            "Неверный логин"
        UserValidationResult.WrongPassword ->
            "Неверный пароль"
        UserValidationResult.NonCyrillicLogin ->
            "Логин пользователя должен быть на кириллице"
        UserValidationResult.NoLatinLettersInPw ->
            "Пароль должен содержать хотя бы одну латинскую букву"
        UserValidationResult.NoDigitsInPw ->
            "Пароль должен содержать хотя бы одну цифру"
        UserValidationResult.ShortPw ->
            "Пароль должен содержать не менее 6 символов"
        UserValidationResult.Valid ->
            ""
    }