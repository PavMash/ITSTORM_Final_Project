package com.itstorm.finalproject.shared.utils

import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.session.toErrorMessage
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.core_domain.models.user.toErrMessage

fun determineErrorMessage(
    usrValidRes: UserValidationResult?,
    sessValidRes: SessionValidation?): String {
    var errorMessage = ""

    usrValidRes?.let {
        errorMessage = usrValidRes.toErrMessage()
    }

    sessValidRes?.let {
        errorMessage = sessValidRes.toErrorMessage()
    }

    return errorMessage
}