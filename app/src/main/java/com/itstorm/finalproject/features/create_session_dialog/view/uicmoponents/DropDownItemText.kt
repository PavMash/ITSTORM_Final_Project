package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun DropDownItemText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W400,
        fontSize = 12.sp,
        color = White
    )
}