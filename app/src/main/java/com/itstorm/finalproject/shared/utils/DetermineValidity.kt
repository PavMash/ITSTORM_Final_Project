package com.itstorm.finalproject.shared.utils

import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.user.UserValidationResult

// usrValidRes and sessValidRes cannot be non-null simultaneously

fun determineValidity(
    usrValidRes: UserValidationResult?,
    sessValidRes: SessionValidation?): Boolean {
    var validRes = false

    usrValidRes?.let {
        validRes = usrValidRes != UserValidationResult.Valid
                && usrValidRes != UserValidationResult.Initial
    }

    sessValidRes?.let {
        validRes = sessValidRes != SessionValidation.Valid
                && sessValidRes != SessionValidation.Initial
    }

    return validRes
}