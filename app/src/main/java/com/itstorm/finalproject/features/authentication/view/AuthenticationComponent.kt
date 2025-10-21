package com.itstorm.finalproject.features.authentication.view


import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.AuthState
import kotlinx.coroutines.flow.StateFlow

interface AuthenticationComponent {

    val model: StateFlow<AuthState>

    fun onCredentialsSubmit()

    fun onPasswordValidate(password: String)

    fun onLoginValidate(login: String)

    fun onPasswordVisibilityChange(isVisible: Boolean)
}