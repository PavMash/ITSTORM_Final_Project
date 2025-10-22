package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.Grey67
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun SecondaryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Grey67
) {
    Text(
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 14.sp,
        color = color,
        modifier = modifier
    )
}