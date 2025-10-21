package com.itstorm.finalproject.root

import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.value.Value
import com.itstorm.finalproject.features.authentication.view.AuthenticationComponent
import com.itstorm.finalproject.root.flow.admin.AdminFlowComponent
import com.itstorm.finalproject.root.flow.user.UserFlowComponent
import com.itstorm.finalproject.root.splash.SplashComponent
import kotlinx.serialization.Serializable

interface RootComponent {
    val stack: Value<ChildStack<*, Child>>

    @Serializable
    sealed interface Config {
        @Serializable
        data object Splash: Config
        @Serializable
        data object Authentication: Config
        @Serializable
        data object AdminFlow: Config
        @Serializable
        data object UserFlow: Config
    }

    sealed interface Child {
        class Splash(val component: SplashComponent): Child
        class Authentication(val component: AuthenticationComponent): Child
        class AdminFlow(val component: AdminFlowComponent): Child
        class UserFlow(val component: UserFlowComponent): Child
    }
}