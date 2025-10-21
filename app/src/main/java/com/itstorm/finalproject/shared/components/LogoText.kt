package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun LogoText() {
    Text(
        text = stringResource(R.string.app_title),
        fontSize = 28.sp,
        fontFamily = robotoFlexFontFamily,
        fontWeight = FontWeight.W500,
        color = GreyE5
    )
}