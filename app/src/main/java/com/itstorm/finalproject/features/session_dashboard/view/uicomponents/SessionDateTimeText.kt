package com.itstorm.finalproject.features.session_dashboard.view.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun SessionDateTimeText(
    modifier: Modifier = Modifier,
    text: String
) {
    Text(
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W700,
        color = GreyE5
    )
}