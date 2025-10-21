package com.itstorm.finalproject.root.flow.admin

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelUI
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardUI
import com.itstorm.finalproject.root.flow.admin.AdminFlowComponent.Child

@Composable
fun AdminFlowUI(component: AdminFlowComponent) {
    Children(stack = component.stack,){ child ->
        when(val instance = child.instance){
            is Child.UserPanel -> AdminUserPanelUI(instance.component)
            is Child.SessionDashboard -> SessionDashboardUI(instance.component)
        }
    }
}