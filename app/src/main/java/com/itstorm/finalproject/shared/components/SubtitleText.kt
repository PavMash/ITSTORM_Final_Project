package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun SubtitleText(
    text :String,
    modifier: Modifier = Modifier,
    color: Color = GreyE5,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 16.sp,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        color = color
    )
}