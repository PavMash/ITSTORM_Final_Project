package com.itstorm.finalproject.features.authentication.view.uicomponents

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.session.toErrorMessage
import com.itstorm.core_domain.models.user.toErrMessage
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.shared.ui.theme.Red00
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily
import com.itstorm.finalproject.shared.utils.determineErrorMessage

@Composable
fun ErrorMessage(
    modifier: Modifier = Modifier,
    usrValidRes: UserValidationResult? = null,
    sessValidRes: SessionValidation? = null
) {
    Text(
        modifier = modifier,
        text = determineErrorMessage(usrValidRes, sessValidRes),
        fontFamily = robotoFlexFontFamily,
        fontSize = 12.sp,
        fontWeight = FontWeight.W500,
        color = Red00
    )
}