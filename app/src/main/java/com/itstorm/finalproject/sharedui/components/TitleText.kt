package com.itstorm.finalproject.sharedui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.sharedui.ui.theme.White
import com.itstorm.finalproject.sharedui.ui.theme.robotoFlexFontFamily

@Composable
fun TitleText(
    modifier: Modifier = Modifier,
    color: Color = White,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontSize = 28.sp,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        color = color
    )
}