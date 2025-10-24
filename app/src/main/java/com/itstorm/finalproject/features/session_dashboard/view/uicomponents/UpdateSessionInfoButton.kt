package com.itstorm.finalproject.features.session_dashboard.view.uicomponents

import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.Grey67
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun UpdateSessionInfoButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    enabled: Boolean
) {
    IconButton(
        modifier = modifier,
        onClick = onClick,
        enabled = enabled
    ) {
        CustomIcon(
            painter = painterResource(R.drawable.edit),
            description = stringResource(R.string.update_session_button),
            tint = if (enabled) White else Grey67
        )
    }
}