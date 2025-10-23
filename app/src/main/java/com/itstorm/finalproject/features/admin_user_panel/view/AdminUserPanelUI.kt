package com.itstorm.finalproject.features.admin_user_panel.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.finalproject.features.admin_user_panel.view.uicomponents.SearchLine
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import com.itstorm.finalproject.features.admin_user_panel.view.uicomponents.TopBar
import com.itstorm.finalproject.shared.components.CustomHorizontalDivider
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.admin_user_panel.view.uicomponents.UserListEntry
import com.itstorm.finalproject.root.helper_enums.AdminHostingScreen
import com.itstorm.finalproject.shared.components.AdminBottomNavBar
import com.itstorm.finalproject.features.admin_user_panel.view.AdminUserPanelComponent.Child
import com.itstorm.finalproject.features.create_user_dialog.view.CreateUserDialogUI

@Composable
fun AdminUserPanelUI(component: AdminUserPanelComponent) {
    val slot by component.slot.subscribeAsState()

    Box {
        FinalProjectTheme {
            Scaffold(
                modifier = Modifier.fillMaxSize(),
                containerColor = Black,
                bottomBar = {
                    BottomAppBar(
                        modifier = Modifier.fillMaxWidth()
                            .windowInsetsPadding(
                                WindowInsets.navigationBars
                            ),
                        containerColor = Black
                    ) {
                        AdminBottomNavBar(
                            hostingScreen = AdminHostingScreen.Users,
                            onUsersClick = {},
                            onSessionsClick = component::onClickSessions
                        )
                    }
                }
            ) { innerPadding ->
                UserPanelScreen(component, innerPadding)
            }
        }
    }

    slot.child?.instance?.let { child ->
        when(child) {
            is Child.CreateUser ->
                CreateUserDialogUI(
                    component = child.component,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black.copy(alpha = 0.8f))
                )
        }
    }
}

@Composable
private fun UserPanelScreen(
    component: AdminUserPanelComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

   Column(
       modifier = Modifier.padding(innerPadding),
       horizontalAlignment = Alignment.CenterHorizontally
   ) {

       TopBar(
           modifier = Modifier.fillMaxWidth()
               .padding(horizontal = 13.dp, vertical = 8.dp),
           onCreateUser = component::createUser
       )

       CustomHorizontalDivider(modifier = Modifier.fillMaxWidth()
           .padding(horizontal = 7.dp))

       SearchLine(
           modifier = Modifier.fillMaxWidth().padding(horizontal = 7.dp),
           value = state.appliedFilter,
           onValueChange = component::searchForUser,
           label = stringResource(R.string.user_search_line_label)
       )

       Spacer(modifier = Modifier.height(12.dp))

       UserList(
           modifier = Modifier.fillMaxWidth()
               .padding(horizontal = 7.dp),
           users = state.filtered,
           avatarColors = state.avatarColors,
           onBlockClick = component::changeUserBlockedStatus
       )
   }
}

@Composable
private fun UserList(
    modifier: Modifier = Modifier,
    users: List<UserDomain>,
    avatarColors: List<Color>,
    onBlockClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(users) { item ->
            UserListEntry(
                modifier = Modifier.fillMaxWidth(),
                user = item,
                avatarColor = avatarColors[users.indexOf(item)],
                onBlockClick = { onBlockClick(item.id) }
            )

            CustomHorizontalDivider(
                modifier = Modifier.padding(vertical = 8.dp)
            )
        }
    }
}