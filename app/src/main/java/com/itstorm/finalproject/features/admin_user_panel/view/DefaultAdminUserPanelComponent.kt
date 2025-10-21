package com.itstorm.finalproject.features.admin_user_panel.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Label
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Intent
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStoreFactory
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelComponent.Config
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelComponent.Child
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.activate
import com.itstorm.finalproject.features.create_user_dialog.view.DefaultCreateUserComponent
import kotlinx.coroutines.launch

class DefaultAdminUserPanelComponent(
    private val userRepo: UserRepository,
    componentContext: ComponentContext,
    onSessionsClick: () -> Unit
): AdminUserPanelComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        AdminUserPanelStoreFactory(
            storeFactory = DefaultStoreFactory(),
            userRepo = userRepo).create()
    }

    override val model = store.stateFlow

    private val scope = coroutineScope()

    init {
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.ClickSessions -> {
                        onSessionsClick()
                    }
                    is Label.CreateUser -> {
                        onCreateUserClick()
                    }
                }
            }
        }
    }

    val navigation = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, Child>> =
        childSlot(
            source = navigation,
            serializer = Config.serializer(),
            handleBackButton = true,
            childFactory = ::createChildSlot
        )

    override fun searchForUser(searchParameter: String) {
        store.accept(Intent.SearchForUser(searchParameter = searchParameter))
    }

    override fun createUser() {
        store.accept(Intent.CreateUser)
    }

    override fun clickSessions() {
        store.accept(Intent.ClickSessions)
    }

    override fun changeUserBlockedStatus(id: Long) {
        store.accept(Intent.ChangeUserBlockedStatus(id = id))
    }

    private fun createChildSlot(
        config: Config,
        childContext: ComponentContext
    ): Child = when(config) {
        is Config.CreateUser ->
            Child.CreateUser(
                component = DefaultCreateUserComponent(
                    componentContext = childContext
                )
            )
    }

    private fun onCreateUserClick() {
        navigation.activate(Config.CreateUser)
    }
}