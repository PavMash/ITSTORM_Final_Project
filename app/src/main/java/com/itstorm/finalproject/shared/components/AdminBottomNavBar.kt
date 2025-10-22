package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.root.helper_enums.AdminHostingScreen
import com.itstorm.finalproject.R

@Composable
fun AdminBottomNavBar(
    hostingScreen: AdminHostingScreen,
    onUsersClick: () -> Unit,
    onSessionsClick: () -> Unit
) {
    Row(modifier = Modifier.fillMaxWidth()
        .height(IntrinsicSize.Min)
        .padding(horizontal = 6.dp),
        horizontalArrangement = Arrangement.spacedBy(
            6.dp,
            Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically
    ) {
        AdminBottomNavButton(
            painter = painterResource(R.drawable.people),
            label = stringResource(R.string.users_page_navigation_text),
            enabled = (hostingScreen != AdminHostingScreen.Users),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onUsersClick() }

        AdminBottomNavButton(
            painter = painterResource(R.drawable.clock),
            label = stringResource(R.string.sessions_page_navigation_text),
            enabled = (hostingScreen != AdminHostingScreen.Session),
            modifier = Modifier.weight(1f).fillMaxHeight()
        ) { onSessionsClick() }
    }
}