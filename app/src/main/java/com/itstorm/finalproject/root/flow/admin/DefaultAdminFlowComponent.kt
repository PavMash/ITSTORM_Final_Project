package com.itstorm.finalproject.root.flow.admin

import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.value.Value
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.core_domain.repositories.StationRepository
import com.itstorm.core_domain.repositories.TariffRepository
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.admin_user_panel.view.DefaultAdminUserPanelComponent
import com.itstorm.finalproject.features.session_dashboard.view.DefaultSessionDashboardComponent
import com.itstorm.finalproject.root.flow.admin.AdminFlowComponent.AdminConfig
import com.itstorm.finalproject.root.flow.admin.AdminFlowComponent.Child

class DefaultAdminFlowComponent(
    private val userRepo: UserRepository,
    private val sessionRepo: SessionRepository,
    private val stationRepo: StationRepository,
    private val tariffRepo: TariffRepository,
    componentContext: ComponentContext
): AdminFlowComponent, ComponentContext by componentContext {

    private val navigation = StackNavigation<AdminConfig>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = AdminConfig.serializer(),
        childFactory = ::createChild,
        handleBackButton = true,
        initialConfiguration = AdminConfig.UserPanel
    )

    private fun createChild(
        config: AdminConfig,
        childContext: ComponentContext
    ) = when(config) {
        is AdminConfig.UserPanel ->
            Child.UserPanel(
                component = DefaultAdminUserPanelComponent(
                    componentContext = childContext,
                    userRepo = userRepo,
                    onSessionsClick = ::onSessionClick
                )
            )
        is AdminConfig.SessionDashboard ->
            Child.SessionDashboard(
                component = DefaultSessionDashboardComponent(
                    componentContext = childContext,
                    sessionRepo = sessionRepo,
                    proceedToUsers = ::onUsersClick,
                    userRepo = userRepo,
                    stationRepo = stationRepo,
                    tariffRepo = tariffRepo
                )
            )
    }

    private fun onSessionClick() {
        navigation.pushToFront(AdminConfig.SessionDashboard)
    }

    private fun onUsersClick() {
        navigation.pushToFront(AdminConfig.UserPanel)
    }
}