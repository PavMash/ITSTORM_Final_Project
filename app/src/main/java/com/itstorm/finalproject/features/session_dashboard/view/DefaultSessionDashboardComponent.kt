package com.itstorm.finalproject.features.session_dashboard.view

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.router.slot.SlotNavigation
import com.arkivanov.decompose.router.slot.activate
import com.arkivanov.decompose.router.slot.childSlot
import com.arkivanov.decompose.value.Value
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent.Config
import com.itstorm.finalproject.features.create_session_dialog.view.DefaultCreateSessionComponent
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent.Child
import com.itstorm.finalproject.features.update_session_dialog.view.DefaultUpdateSessionComponent

class DefaultSessionDashboardComponent(
    componentContext: ComponentContext,
    private val sessionRepo: SessionRepository,
    private val proceedToUsers: () -> Unit
): SessionDashboardComponent, ComponentContext by componentContext {


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
                        sessionRepo = sessionRepo))
            is Config.UpdateSessionInfo ->
                Child.UpdateSessionInfo(
                    component = DefaultUpdateSessionComponent(
                        componentContext = childContext,
                        sessionRepo = sessionRepo,
                        sessionId = config.sessionId
                    )
                )
        }

    override fun clickUsers() = proceedToUsers()

    override fun clickCreateSession() {
        navigation.activate(Config.CreateNewSession)
    }

    override fun clickUpdateSession(sessionId: Long) {
        navigation.activate(Config.UpdateSessionInfo(sessionId))
    }

}