package com.itstorm.finalproject.sharedui.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.sharedui.ui.theme.Grey67
import com.itstorm.finalproject.sharedui.ui.theme.robotoFlexFontFamily

@Composable
fun TextFieldLabel(label: String) {
    Text(
        text = label,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 16.sp,
        lineHeight = 24.sp,
        color = Grey67
    )
}