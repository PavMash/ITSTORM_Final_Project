package com.itstorm.finalproject.features.authentication.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserWithSessionsDomain
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Intent
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.AuthState
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Label
import com.itstorm.finalproject.features.authentication.store.AuthenticationStoreFactory
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultAuthenticationComponent(
    private val storeFactory: StoreFactory,
    private val userRepo: UserRepository,
    componentContext: ComponentContext,
    private val onEnterApp: (UserWithSessionsDomain) -> Unit
): AuthenticationComponent, ComponentContext by componentContext {

    val store = instanceKeeper.getStore {
        AuthenticationStoreFactory(
            storeFactory,
            userRepo
        ).create()
    }

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.EnterApp -> {
                        onEnterApp(it.user)
                    }
                }
            }
        }
    }

    override val model: StateFlow<AuthState> = store.stateFlow

    override fun onCredentialsSubmit() {
        store.accept(Intent.SubmitLoginCredentials)
    }

    override fun onPasswordValidate(password: String) {
        store.accept(Intent.ValidatePassword(password))
    }

    override fun onLoginChange(login: String) {
        store.accept(Intent.ChangeLogin(login))
    }

    override fun onPasswordVisibilityChange(isVisible: Boolean) {
        store.accept(Intent.ChangePasswordVisibility(isVisible))
    }
}