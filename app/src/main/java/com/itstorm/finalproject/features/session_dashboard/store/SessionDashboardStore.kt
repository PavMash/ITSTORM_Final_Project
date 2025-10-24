package com.itstorm.finalproject.features.session_dashboard.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Intent
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.State
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Label

interface SessionDashboardStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data object UserClick: Intent
        data object CreateSession: Intent
        data class UpdateSessionInfo(val id: Long): Intent
    }

    data class State(
        val sessions: List<SessionWithUserDomain> = emptyList()
    )

    sealed interface Label {
        data object UsersClick: Label
        data object CreateSession: Label
        data class UpdateSessionInfo(val id: Long): Label
    }
}