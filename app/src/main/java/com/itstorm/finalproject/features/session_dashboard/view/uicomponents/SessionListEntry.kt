package com.itstorm.finalproject.features.session_dashboard.view.uicomponents

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.core_domain.models.user.toUserWithSessions
import com.itstorm.finalproject.shared.components.UserListEntry
import com.itstorm.finalproject.shared.utils.randomColorFromPool

@Composable
fun SessionListEntry(
    modifier: Modifier = Modifier,
    session: SessionWithUserDomain,
    onUpdateClick: () -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        SessionInfoBar(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp),
            session = session,
            onUpdateClick = onUpdateClick
        )

        Log.d("sessinsUIDebug", "${session.user}")
        UserListEntry(
            modifier = Modifier.fillMaxWidth()
                .padding(12.dp),
            user = session.user.toUserWithSessions(),
            avatarColor = randomColorFromPool(session.user.id.toInt()),
            containsBlockButton = false
        )
    }

}