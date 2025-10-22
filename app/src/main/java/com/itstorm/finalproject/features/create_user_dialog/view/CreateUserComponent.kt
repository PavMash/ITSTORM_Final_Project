package com.itstorm.finalproject.features.create_user_dialog.view

import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.State
import kotlinx.coroutines.flow.StateFlow

interface CreateUserComponent {
    val model: StateFlow<State>

    fun onLoginChange(login: String)

    fun onPasswordChange(password: String)

    fun onPhoneNumberChange(phNum: String)

    fun onClose()

    fun onSubmit(login: String, password: String, phNum: String)
}