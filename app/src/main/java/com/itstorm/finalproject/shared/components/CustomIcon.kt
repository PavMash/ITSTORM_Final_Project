package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import com.itstorm.finalproject.shared.ui.theme.GreyE5

@Composable
fun CustomIcon(
    modifier: Modifier = Modifier,
    painter: Painter,
    description: String
) {
    Icon(
        modifier = modifier,
        painter = painter,
        contentDescription = description,
        tint = GreyE5
    )
}