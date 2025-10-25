package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.Grey1A
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun ChipCard(
    text: String
) {
    Box(
        modifier = Modifier
            .wrapContentSize()
            .background(
                color = Grey1A,
                shape = RoundedCornerShape(4.dp))
            .border(
                color = Grey34,
                width = 1.dp,
                shape = RoundedCornerShape(4.dp))
            .padding(vertical = 6.dp,
                horizontal = 10.dp)
    ) {
        Text(
            text = text,
            fontFamily = robotoFlexFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.W400,
            color = GreyE5
        )
    }
}