package com.itstorm.core_domain.models.session

fun SessionValidation.toErrorMessage(): String =
    when(this) {
        SessionValidation.PastDate ->
            "Дата должна быть актуальной"
        SessionValidation.PastTime ->
            "Время должно быть актуальным"
        SessionValidation.FractionMinutes ->
            "Длительность должна быть целым числом в минутах"
        SessionValidation.WrongDateFormat ->
            "Неправильный формат даты"
        SessionValidation.WrongTimeFormat ->
            "Неправильный формат времени"
        SessionValidation.WrongDurationFormat ->
            "Неправильный формат длительности"
        SessionValidation.Initial -> ""
        SessionValidation.Valid -> ""
    }