package com.itstorm.finalproject.features.session_dashboard.view

import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.Value
import com.itstorm.finalproject.features.create_session_dialog.view.CreateSessionComponent
import com.itstorm.finalproject.features.update_session_dialog.view.UpdateSessionComponent
import kotlinx.serialization.Serializable

interface SessionDashboardComponent {

    val slot: Value<ChildSlot<*, Child>>

    fun clickUsers()

    fun clickCreateSession()

    fun clickUpdateSession(sessionId: Long)

    @Serializable
    sealed interface Config {
        @Serializable
        data object CreateNewSession: Config
        @Serializable
        data class UpdateSessionInfo(val sessionId: Long): Config
    }

    sealed interface Child {
        class CreateNewSession(val component: CreateSessionComponent): Child
        class UpdateSessionInfo(val component: UpdateSessionComponent): Child
    }
}