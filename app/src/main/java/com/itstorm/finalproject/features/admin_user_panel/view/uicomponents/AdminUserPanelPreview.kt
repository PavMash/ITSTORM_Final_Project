package com.itstorm.finalproject.features.admin_user_panel.view.uicomponents

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.arkivanov.decompose.router.slot.ChildSlot
import com.arkivanov.decompose.value.MutableValue
import com.arkivanov.decompose.value.Value
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.UserRole
import com.itstorm.core_domain.models.user.UserWithSessionsDomain
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelComponent
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Authentication Screen Preview"
)
@Composable
fun AdminUserPanelPreview() {
    val fakeComponent = object: AdminUserPanelComponent {
        val users = listOf(
            UserWithSessionsDomain(
                name = "Вася",
                phoneNumber = "+71234567890",
                password = "pass12345",
                isBlocked = false,
                isOnline = false,
                role = UserRole.User,
                sessions = emptyList()
            ),
            UserWithSessionsDomain(
                name = "Ваня",
                phoneNumber = "+91235671234",
                password = "12345pass",
                isBlocked = false,
                isOnline = false,
                role = UserRole.User,
                sessions = emptyList()
            ),
            UserWithSessionsDomain(
                name = "James",
                phoneNumber = "+13569023457",
                password = "smart_pass1",
                isBlocked = false,
                isOnline = false,
                role = UserRole.User,
                sessions = emptyList()
            )
        )

        override val model: StateFlow<AdminUserPanelStore.State> = MutableStateFlow(
            AdminUserPanelStore.State(
                users = users,
                filtered = users
            )
        )
        override val slot: Value<ChildSlot<Unit, AdminUserPanelComponent.Child>> =
            MutableValue(ChildSlot())

        override fun searchForUser(searchParameter: String) {}

        override fun createUser() {}

        override fun onClickSessions() {}

        override fun changeUserBlockedStatus(id: Long) {}

    }

    AdminUserPanelUI(fakeComponent)
}