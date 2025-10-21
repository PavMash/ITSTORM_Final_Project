package com.itstorm.finalproject.root.flow.admin

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelComponent
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent
import kotlinx.serialization.Serializable

interface AdminFlowComponent {
    val stack: Value<ChildStack<*, Child>>

    @Serializable
    sealed interface AdminConfig {
        @Serializable
        data object UserPanel: AdminConfig
        @Serializable
        data object SessionDashboard: AdminConfig
    }

    sealed interface Child {
        class UserPanel(val component: AdminUserPanelComponent): Child
        class SessionDashboard(val component: SessionDashboardComponent): Child
    }
}