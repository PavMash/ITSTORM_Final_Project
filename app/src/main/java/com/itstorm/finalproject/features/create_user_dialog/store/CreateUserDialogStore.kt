package com.itstorm.finalproject.features.create_user_dialog.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Intent
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.State
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Label

interface CreateUserDialogStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeLogin(val login: String): Intent
        data class ValidatePhoneNumber(val phNum: String): Intent
        data class ValidatePassword(val password: String): Intent
        data object Close: Intent
        data class Submit(val login: String,
                          val password: String,
                          val phoneNumber: String): Intent
    }

    data class State(
        val login: String = "",
        val password: String = "",
        val phoneNumber: String = "",
        val phoneErrMessage: UserValidationResult = UserValidationResult.Initial,
        val passwordErrMessage: UserValidationResult = UserValidationResult.Initial,
    )

    sealed interface Label {
        data object Close: Label
        data class Submit(val login: String,
                          val password: String,
                          val phoneNumber: String): Label
    }
}