package com.itstorm.finalproject.root

import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.itstorm.finalproject.features.authentication.view.AuthenticationUI
import com.itstorm.finalproject.root.RootComponent.Child
import com.itstorm.finalproject.root.flow.admin.AdminFlowUI
import com.itstorm.finalproject.root.flow.user.UserFlowUI
import com.itstorm.finalproject.root.splash.SplashUI

@Composable
fun RootUI(rootComponent: RootComponent) {
    Children(stack = rootComponent.stack,){ child ->
        when(val instance = child.instance){
            is Child.Authentication -> AuthenticationUI(instance.component)
            is Child.UserFlow -> UserFlowUI(instance.component)
            is Child.AdminFlow -> AdminFlowUI(instance.component)
            is Child.Splash -> SplashUI(instance.component)
        }
    }
}