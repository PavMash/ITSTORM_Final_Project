package com.itstorm.finalproject.root

import android.content.Context
import androidx.room.Room
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.router.stack.StackNavigation
import com.arkivanov.decompose.router.stack.childStack
import com.arkivanov.decompose.value.Value
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.pushToFront
import com.arkivanov.decompose.router.stack.replaceCurrent
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_data.db.AppDataBase
import com.itstorm.core_data.repositories.UserRepositoryImpl
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserRole
import com.itstorm.finalproject.features.authentication.view.DefaultAuthenticationComponent
import com.itstorm.finalproject.root.RootComponent.Config
import com.itstorm.finalproject.root.RootComponent.Child
import com.itstorm.finalproject.root.flow.admin.DefaultAdminFlowComponent
import com.itstorm.finalproject.root.flow.user.DefaultUserFlowComponent
import com.itstorm.finalproject.root.splash.DefaultSplashComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DefaultRootComponent(
    appContext: Context,
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext  {

    private val db = Room.databaseBuilder(
        appContext,
        AppDataBase::class.java,
        "database"
    ).fallbackToDestructiveMigration(false).build()
    private val userRepository = UserRepositoryImpl(db.userDao())
    private val sessionRepository =

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
                preloadAppResources = ::preloadResources,
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
                componentContext = childContext,
                userRepo = userRepository
            )
        )
    }

    private fun onSplashFinished() {
        navigation.replaceCurrent(Config.Authentication)
    }

    private fun preloadResources() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.clearAllUsers()
            userRepository.preloadIfEmpty()
        }
    }

    private fun onEnterApp(user: UserDomain) {
        if(user.role == UserRole.User || user.role == UserRole.Guest) {
            navigation.pushToFront(Config.UserFlow)
        } else {
            navigation.pushToFront(Config.AdminFlow)
        }
    }
}