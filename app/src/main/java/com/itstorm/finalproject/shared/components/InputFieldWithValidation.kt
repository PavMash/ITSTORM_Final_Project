package com.itstorm.finalproject.shared.components

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.Red0C
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.utils.determineValidity

@Composable
fun InputFieldWithValidation(
    modifier: Modifier = Modifier,
    value: String,
    usrValidRes: UserValidationResult? = null,
    sessValidRes: SessionValidation? = null,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color = Black,
    trailingIcon: @Composable (() -> Unit)? = null
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { TextFieldLabel(label = label) },
        onValueChange = onValueChange,
        isError = determineValidity(usrValidRes, sessValidRes),
        colors = TextFieldDefaults.colors(
            focusedTextColor = White,
            unfocusedTextColor = White,
            errorTextColor = White,
            focusedContainerColor = backgroundColor,
            unfocusedContainerColor = backgroundColor,
            errorContainerColor = backgroundColor,
            unfocusedIndicatorColor = Grey34,
            focusedIndicatorColor = Grey34,
            errorIndicatorColor = Red0C,
        ),
        trailingIcon = {
            trailingIcon?.let {
                trailingIcon()
            }
        }
    )
}