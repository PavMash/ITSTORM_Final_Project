package com.itstorm.finalproject.features.session_dashboard.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.router.slot.dismiss
import com.arkivanov.decompose.value.Value
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.core_domain.repositories.StationRepository
import com.itstorm.core_domain.repositories.TariffRepository
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent.Config
import com.itstorm.finalproject.features.create_session_dialog.view.DefaultCreateSessionComponent
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStoreFactory
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent.Child
import com.itstorm.finalproject.features.update_session_dialog.view.DefaultUpdateSessionComponent
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Label
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Intent
import kotlinx.coroutines.launch

class DefaultSessionDashboardComponent(
    componentContext: ComponentContext,
    private val sessionRepo: SessionRepository,
    private val userRepo: UserRepository,
    private val stationRepo: StationRepository,
    private val tariffRepo: TariffRepository,
    private val proceedToUsers: () -> Unit
): SessionDashboardComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        SessionDashboardStoreFactory(
            storeFactory = DefaultStoreFactory(),
            sessionRepo = sessionRepo
        ).create()
    }

    override val model = store.stateFlow

    private val scope = coroutineScope()

    init{
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.CreateSession ->
                        navigation.activate(Config.CreateNewSession)
                    is Label.UpdateSessionInfo ->
                        navigation.activate(
                            Config.UpdateSessionInfo(it.id))
                    is Label.UsersClick ->
                        proceedToUsers()
                }
            }
        }
    }

    private val navigation = SlotNavigation<Config>()
    override val slot: Value<ChildSlot<*, Child>> = childSlot(
        source = navigation,
        serializer = Config.serializer(),
        handleBackButton = true,
        childFactory = ::createChild,
    )

    private fun createChild(
        config: Config,
        childContext: ComponentContext): Child =
        when(config) {
            is Config.CreateNewSession ->
                Child.CreateNewSession(
                    component = DefaultCreateSessionComponent(
                        componentContext = childContext,
                        sessionRepo = sessionRepo,
                        userRepo = userRepo,
                        stationRepo = stationRepo,
                        tariffRepo = tariffRepo,
                        comeBackToDashboard = ::returnToDashboard))
            is Config.UpdateSessionInfo ->
                Child.UpdateSessionInfo(
                    component = DefaultUpdateSessionComponent(
                        componentContext = childContext,
                        sessionRepo = sessionRepo,
                        sessionId = config.sessionId
                    )
                )
        }

    override fun clickUsers() {
        store.accept(Intent.UserClick)
    }

    override fun clickCreateSession() {
        store.accept(Intent.CreateSession)
    }

    override fun clickUpdateSession(sessionId: Long) {
        store.accept(Intent.UpdateSessionInfo(sessionId))
    }

    private fun returnToDashboard() {
        navigation.dismiss()
    }
}