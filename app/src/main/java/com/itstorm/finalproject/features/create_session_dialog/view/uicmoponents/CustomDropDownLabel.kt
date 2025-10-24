package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun DropDownLabel(
    modifier: Modifier = Modifier,
    text: String,
    icon: Painter,
    iconDescription: String,
    enabled: Boolean
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Icon(
            modifier = Modifier.size(15.dp),
            painter = icon,
            tint = Grey34,
            contentDescription = iconDescription
        )

        Text(
            text = text,
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight.W500,
            fontSize = 12.sp,
            color = Grey34
        )
    }
}