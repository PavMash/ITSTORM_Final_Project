package com.itstorm.finalproject.shared.components

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.Grey6F
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun OnlineStausText(
    modifier: Modifier = Modifier,
    isOnline: Boolean
) {
    Text(
        text = if (isOnline)
            stringResource(R.string.online_status)
        else stringResource(R.string.offline_status),
        fontFamily = robotoFlexFontFamily,
        fontSize = 11.sp,
        fontWeight = FontWeight.W500,
        color = if (isOnline) White else Grey6F
    )
}