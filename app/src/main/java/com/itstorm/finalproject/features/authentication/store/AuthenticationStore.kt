package com.itstorm.finalproject.features.authentication.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.user.User
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Intent
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.AuthState
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Label

interface AuthenticationStore: Store<Intent, AuthState, Label> {

    sealed interface Intent {
        data class ValidateLogin(val login: String): Intent
        data class ValidatePassword(val password: String): Intent
        data object SubmitLoginCredentials: Intent
        data class ChangePasswordVisibility(val isVisible: Boolean): Intent
    }

    data class AuthState(
        val login: String = "",
        val password: String = "",
        val passwordErrMessage: UserValidationResult = UserValidationResult.Valid,
        val loginErrMessage: UserValidationResult = UserValidationResult.Valid,
        val isPasswordVisible: Boolean = false
    )

    sealed interface Label {
        data class EnterApp(val user: User): Label
    }
}