package com.itstorm.finalproject.features.favorites.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun CategoryButton(
    text: String,
    enabled: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier.wrapContentSize()
            .background(
                color = if (enabled) Black else Grey34,
                shape = RoundedCornerShape(4.dp))
            .border(
                width = 1.dp,
                color = Grey34,
                shape = RoundedCornerShape(4.dp))
            .padding(vertical = 6.dp, horizontal = 10.dp)
            .clickable(onClick = onClick),
    ) {
        ButtonText(
            text = text,
            color = if (enabled) GreyE5 else White
        )
    }
}