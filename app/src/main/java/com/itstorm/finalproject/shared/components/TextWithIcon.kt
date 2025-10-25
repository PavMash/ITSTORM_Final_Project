package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun TextWithIcon(
    text: String,
    iconDescription: String,
    iconRes: Painter
) {
    Row(
        modifier = Modifier.wrapContentSize()
            .padding(6.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = iconRes,
            contentDescription = iconDescription,
            tint = White
        )

        Spacer(modifier = Modifier.width(7.25.dp))

        Text(
            text = text,
            fontFamily = robotoFlexFontFamily,
            fontWeight = FontWeight.W400,
            fontSize = 14.sp,
            color = GreyE5
        )
    }
}