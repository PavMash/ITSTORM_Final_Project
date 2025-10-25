package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.GreyD7
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun ArticleText(
    modifier: Modifier = Modifier,
    text: String,
    maxLines: Int = Int.MAX_VALUE
) {
    Text(
        modifier = modifier,
        text = text,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        fontSize = 12.sp,
        color = GreyD7,
        maxLines = maxLines,
        overflow = TextOverflow.Ellipsis,
        lineHeight = 24.sp
    )
}