package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.R

@Composable
fun CloseButton(
    modifier: Modifier = Modifier,
    onClose: () -> Unit
) {
    Box(
        modifier = modifier.background(
            color = White,
            shape = RoundedCornerShape(6.dp))
            .wrapContentSize()
            .padding(6.dp)
            .clickable(
                onClick = onClose
            )
    ) {
        Icon(
            painter = painterResource(R.drawable.close),
            contentDescription = stringResource(R.string.close_icon_description)
        )
    }
}