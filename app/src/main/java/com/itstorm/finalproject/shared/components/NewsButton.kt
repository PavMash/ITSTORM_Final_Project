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
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun NewsButton(
    isActive: Boolean,
    activeIcon: Painter,
    unactiveIcon: Painter,
    activeDescription: String,
    unactiveDescription: String,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.background(
            color = White,
            shape = RoundedCornerShape(4.dp))
            .wrapContentSize()
            .padding(vertical = 2.dp, horizontal = 6.dp)
            .clickable(
                onClick = onClick
            )
    ) {
        Icon(
            painter = if (isActive) activeIcon else unactiveIcon,
            contentDescription = if (isActive)
                activeDescription else unactiveDescription
        )
    }
}