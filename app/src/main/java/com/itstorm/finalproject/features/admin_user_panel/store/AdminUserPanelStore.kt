package com.itstorm.finalproject.features.admin_user_panel.store

import androidx.compose.ui.graphics.Color
import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.user.SearchFilter
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserWithSessionsDomain
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Intent
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.State
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Label

interface AdminUserPanelStore: Store<Intent, State, Label> {
    sealed interface Intent {
        data object CreateUser: Intent
        data class ChangeUserBlockedStatus(val id: Long): Intent
        data class SearchForUser(val searchParameter: String): Intent
        data object ClickSessions: Intent
        data class AddUser(val login: String,
                           val password: String, val phoneNumber: String): Intent
    }

    data class State(
        val users: List<UserWithSessionsDomain> = emptyList(),
        val filtered: List<UserWithSessionsDomain> = emptyList(),
        val filterType: SearchFilter? = null,
        val appliedFilter: String = ""
    )

    sealed interface Label {
        data object CreateUser: Label
        data object ClickSessions: Label
    }
}