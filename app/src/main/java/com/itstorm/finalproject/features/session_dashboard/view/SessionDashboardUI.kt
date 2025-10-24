package com.itstorm.finalproject.features.session_dashboard.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.arkivanov.decompose.extensions.compose.subscribeAsState
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.finalproject.shared.components.TopSectionBar
import com.itstorm.finalproject.features.create_session_dialog.view.CreateSessionDialogUI
import com.itstorm.finalproject.features.session_dashboard.view.SessionDashboardComponent.Child
import com.itstorm.finalproject.features.update_session_dialog.view.UpdateSessionDialogUI
import com.itstorm.finalproject.root.helper_enums.AdminHostingScreen
import com.itstorm.finalproject.shared.components.AdminBottomNavBar
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.session_dashboard.view.uicomponents.SessionListEntry
import com.itstorm.finalproject.shared.components.CustomHorizontalDivider
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun SessionDashboardUI(component: SessionDashboardComponent) {
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
                            hostingScreen = AdminHostingScreen.Session,
                            onUsersClick = component::clickUsers,
                            onSessionsClick = {}
                        )
                    }
                }
            ) { innerPadding ->
                SessionDashboardScreen(component, innerPadding)
            }
        }
    }

    slot.child?.instance?.let { child ->
        when(child) {
            is Child.CreateNewSession ->
                CreateSessionDialogUI(
                    component = child.component,
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Black.copy(alpha = 0.8f))
                )
            is Child.UpdateSessionInfo ->
                UpdateSessionDialogUI(
                    component = child.component
                )
        }
    }
}

@Composable
private fun SessionDashboardScreen(
    component: SessionDashboardComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        TopSectionBar(
            modifier = Modifier.fillMaxWidth()
                .padding(
                    start = 13.dp, end = 26.dp,
                    top = 12.dp, bottom = 12.dp
                ),
            sectionTitle = stringResource(R.string.sessions_page_navigation_text),
            onClickPlus = component::clickCreateSession
        )

        CustomHorizontalDivider(modifier = Modifier.fillMaxWidth())

        SessionList(
            sessions = state.sessions,
            onUpdateClick = component::clickUpdateSession
        )
    }
}

@Composable
private fun SessionList(
    modifier: Modifier = Modifier,
    sessions: List<SessionWithUserDomain>,
    onUpdateClick: (Long) -> Unit
) {
    LazyColumn(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(sessions) { item ->
            val zoneId = ZoneId.systemDefault()
            val startLocalDate = LocalDateTime
                .ofInstant(item.start, zoneId).toLocalDate()

            val indexOfCurSession = sessions.indexOf(item)
            val nextSession = if (indexOfCurSession < sessions.size-1)
                sessions[indexOfCurSession+1] else item
            val nextSessionLocalDate = LocalDateTime
                .ofInstant(nextSession.start, zoneId).toLocalDate()

            SessionListEntry(
                modifier = Modifier.fillMaxWidth(),
                session = item,
                onUpdateClick = { onUpdateClick(item.id) }
            )

            if (startLocalDate == nextSessionLocalDate) {
                CustomHorizontalDivider(
                    modifier = Modifier.fillMaxWidth()
                        .padding(vertical = 12.dp)
                )
            }
        }
    }
}