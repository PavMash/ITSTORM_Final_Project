package com.itstorm.finalproject.root

import android.content.Context
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_data.repositories.UserRepositoryImpl
import com.itstorm.core_domain.models.user.User
import com.itstorm.finalproject.features.authentication.view.DefaultAuthenticationComponent
import com.itstorm.finalproject.root.RootComponent.Config
import com.itstorm.finalproject.root.RootComponent.Child
import com.itstorm.finalproject.root.flow.admin.DefaultAdminFlowComponent
import com.itstorm.finalproject.root.flow.user.DefaultUserFlowComponent
import com.itstorm.finalproject.root.splash.DefaultSplashComponent

class DefaultRootComponent(
    private val appContext: Context,
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext  {

    private val userRepository = UserRepositoryImpl()
    private val navigation = StackNavigation<Config>()

    override val stack: Value<ChildStack<*, Child>> = childStack(
        source = navigation,
        serializer = Config.serializer(),
        childFactory = ::createChild,
        handleBackButton = true,
        initialConfiguration = Config.Splash
    )

    private fun createChild(
        config: Config,
        childContext: ComponentContext
    ) = when(config) {
        is Config.Splash -> Child.Splash(
            component = DefaultSplashComponent(
                onFinished = ::onSplashFinished,
                preloadAppResources = { preloadResources(appContext) },
                componentContext = childContext
            ))

        is Config.Authentication -> Child.Authentication(
            component = DefaultAuthenticationComponent(
                storeFactory = DefaultStoreFactory(),
                userRepo = userRepository,
                componentContext = childContext,
                onEnterApp = ::onEnterApp
            )
        )

        is Config.UserFlow -> Child.UserFlow(
            component = DefaultUserFlowComponent(
                componentContext = childContext
            )
        )

        is Config.AdminFlow -> Child.AdminFlow(
            component = DefaultAdminFlowComponent(
                componentContext = childContext
            )
        )
    }

    private fun onSplashFinished() {
        navigation.replaceCurrent(Config.Authentication)
    }

    private fun preloadResources(context: Context) {
//        CoroutineScope(Dispatchers.IO).launch {
//            repository.preloadNewsIfEmpty(context)
//        }
    }

    private fun onEnterApp(user: User) {}

}