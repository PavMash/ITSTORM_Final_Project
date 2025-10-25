package com.itstorm.finalproject.root

import android.content.Context
import android.util.Log
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
import com.itstorm.core_data.repositories.NewsRepositoryImpl
import com.itstorm.core_data.repositories.SessionRepositoryImpl
import com.itstorm.core_data.repositories.StationRepositoryImpl
import com.itstorm.core_data.repositories.TariffRepositoryImpl
import com.itstorm.core_data.repositories.UserRepositoryImpl
import com.itstorm.core_domain.models.session.SessionDomain
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserRole
import com.itstorm.core_domain.models.user.UserWithSessionsDomain
import com.itstorm.core_domain.repositories.StationRepository
import com.itstorm.core_domain.usecases.SessionTimeCalculator
import com.itstorm.finalproject.features.authentication.view.DefaultAuthenticationComponent
import com.itstorm.finalproject.root.RootComponent.Config
import com.itstorm.finalproject.root.RootComponent.Child
import com.itstorm.finalproject.root.flow.admin.DefaultAdminFlowComponent
import com.itstorm.finalproject.root.flow.user.DefaultUserFlowComponent
import com.itstorm.finalproject.root.splash.DefaultSplashComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.ZoneId

class DefaultRootComponent(
    private val appContext: Context,
    componentContext: ComponentContext
): RootComponent, ComponentContext by componentContext  {

    private val db = Room.databaseBuilder(
        appContext,
        AppDataBase::class.java,
        "database"
    ).fallbackToDestructiveMigration(false).build()
    private val userRepository = UserRepositoryImpl(db.userDao())
    private val sessionRepository = SessionRepositoryImpl(
        sessionDao = db.sessionDao())
    private val stationRepository = StationRepositoryImpl(db.stationDao())
    private val tariffRepository = TariffRepositoryImpl(db.tariffDao())
    private val newsRepository = NewsRepositoryImpl(db.newsDao())

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
                componentContext = childContext,
                newsRepo = newsRepository,
                hasAccess = config.hasAccessToSession,
                startTime = config.startTime,
                endTime = config.endTime
            )
        )

        is Config.AdminFlow -> Child.AdminFlow(
            component = DefaultAdminFlowComponent(
                componentContext = childContext,
                userRepo = userRepository,
                sessionRepo = sessionRepository,
                stationRepo = stationRepository,
                tariffRepo = tariffRepository
            )
        )
    }

    private fun onSplashFinished() {
        navigation.replaceCurrent(Config.Authentication)
    }

    private fun preloadResources() {
        CoroutineScope(Dispatchers.IO).launch {
            userRepository.preloadIfEmpty()
            stationRepository.preloadIfEmpty()
            tariffRepository.preloadIfEmpty()
            newsRepository.preloadNewsIfEmpty(appContext)
        }
    }

    private fun onEnterApp(user: UserWithSessionsDomain) {
        if(user.role == UserRole.User || user.role == UserRole.Guest) {
            val accessibleSession = findAccessibleSession(user)
            val hasAccess = accessibleSession != null
            val zoneId = ZoneId.systemDefault()
            val calculator = SessionTimeCalculator(zoneId)
            var startString = ""
            var endString = ""
            accessibleSession?.let {
                startString = calculator
                    .instantToTimeString(accessibleSession.start)
                endString = calculator
                    .instantToTimeString(accessibleSession.end)
            }

            navigation.pushToFront(Config.UserFlow(
                hasAccessToSession = hasAccess,
                startTime = startString,
                endTime = endString
            ))
        } else {
            navigation.pushToFront(Config.AdminFlow)
        }
    }

    private fun findAccessibleSession(user: UserWithSessionsDomain): SessionDomain? {
        val currentTime = Instant.now()

        val session = user.sessions.find { it.start <= currentTime && currentTime < it.end }
        return session
    }
}