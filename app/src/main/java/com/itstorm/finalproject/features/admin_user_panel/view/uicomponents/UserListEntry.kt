package com.itstorm.finalproject.features.admin_user_panel.view.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.finalproject.shared.components.AvatarWithName
import com.itstorm.finalproject.shared.components.OnlineStausText

@Composable
fun UserListEntry(
    modifier: Modifier = Modifier,
    user: UserDomain,
    avatarColor: Color,
    onBlockClick: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AvatarWithName(
            name = user.name,
            avatarColor = avatarColor,
            isBlocked = user.isBlocked
        )

        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            OnlineStausText(isOnline = user.isOnline)

            Spacer(modifier = Modifier.width(16.dp))

            BlockButton(
                isBlocked = user.isBlocked,
                onBlockClick = onBlockClick
            )
        }

    }
}