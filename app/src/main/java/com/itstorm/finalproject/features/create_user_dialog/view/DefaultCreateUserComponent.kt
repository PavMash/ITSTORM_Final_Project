package com.itstorm.finalproject.features.create_user_dialog.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.State
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Intent
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Label
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCreateUserComponent(
    componentContext: ComponentContext,
    onClose: () -> Unit,
    onSubmit: (String, String, String) -> Unit
): CreateUserComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore {
        CreateUserDialogStoreFactory(
            storeFactory = DefaultStoreFactory()).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.Close -> onClose()
                    is Label.Submit ->
                        onSubmit(it.login, it.password, it.phoneNumber)
                }
            }
        }
    }

    override val model: StateFlow<State> = store.stateFlow

    override fun onLoginChange(login: String) {
        store.accept(Intent.ChangeLogin(login))
    }

    override fun onPasswordChange(password: String) {
        store.accept(Intent.ValidatePassword(password))
    }

    override fun onPhoneNumberChange(phNum: String) {
        store.accept(Intent.ValidatePhoneNumber(phNum))
    }

    override fun onClose() {
        store.accept(Intent.Close)
    }

    override fun onSubmit(login: String, password: String, phNum: String) {
        store.accept(Intent.Submit(login, password, phNum))
    }
}