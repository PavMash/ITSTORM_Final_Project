package com.itstorm.finalproject.features.authentication.view.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.core_domain.models.user.toErrMessage
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.shared.ui.theme.Red00
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    validRes: UserValidationResult
) {
    Text(
        modifier = modifier,
        text = validRes.toErrMessage(),
        fontFamily = robotoFlexFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = Red00
    )
}