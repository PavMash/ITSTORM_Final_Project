package com.itstorm.finalproject.features.admin_user_panel.view

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import kotlinx.coroutines.flow.StateFlow
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.State
import com.itstorm.finalproject.features.create_user_dialog.view.CreateUserComponent
import kotlinx.serialization.Serializable

interface AdminUserPanelComponent {
    val model: StateFlow<State>

    val slot: Value<ChildSlot<*, Child>>

    fun searchForUser(searchParameter: String)

    fun createUser()

    fun clickSessions()

    fun changeUserBlockedStatus(id: Long)

    @Serializable
    sealed interface Config {
        @Serializable
        data object CreateUser: Config
    }

    sealed interface Child {
        class CreateUser(val component: CreateUserComponent): Child
    }
}