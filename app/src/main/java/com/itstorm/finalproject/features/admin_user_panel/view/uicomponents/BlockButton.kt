package com.itstorm.finalproject.features.admin_user_panel.view.uicomponents

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.Red76
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.Grey1A

@Composable
fun BlockButton(
    modifier: Modifier = Modifier,
    isBlocked: Boolean,
    onBlockClick: () -> Unit
) {
    Box(
        modifier = Modifier.size(32.dp)
            .background(
                shape = RoundedCornerShape(4.dp),
                color = if (isBlocked) GreyE5 else Red76
            ).clickable(
                onClick = onBlockClick
            ),
        contentAlignment = Alignment.Center
    ) {
        Icon(
            painter = if (isBlocked)
                painterResource(R.drawable.unblock)
            else painterResource(R.drawable.block),
            contentDescription = if (isBlocked)
                stringResource(R.string.unblock_icon)
            else stringResource(R.string.block_icon),
            tint = Grey1A
        )
    }
}