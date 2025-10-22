package com.itstorm.finalproject.features.admin_user_panel.view.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.shared.components.SectionTitle
import com.itstorm.finalproject.shared.ui.theme.GreyE5

@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    onCreateUser: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SectionTitle(
            text = stringResource(R.string.users_section_title))

        IconButton(
            onClick = onCreateUser
        ) {
            CustomIcon(
                painter = painterResource(R.drawable.add_circle_outline),
                description = stringResource(R.string.user_create_button_description)
            )
        }
    }
}