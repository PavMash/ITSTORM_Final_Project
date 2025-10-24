package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.itstorm.core_domain.models.user.UserDomain

@Composable
fun SessionCreateUserEntry(
    modifier: Modifier = Modifier,
    user: UserDomain,
    isIncluded: Boolean,
    onUserSelected: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        AvatarNameStatus(
            id = user.id,
            name = user.name,
            isOnline = user.isOnline
        )

        StatusCheckBox(
            isChecked = isIncluded,
            onClick = onUserSelected,

        )
    }
}