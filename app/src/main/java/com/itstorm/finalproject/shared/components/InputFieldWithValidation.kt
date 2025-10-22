package com.itstorm.finalproject.shared.components

import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.Red0C
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun InputFieldWithValidation(
    modifier: Modifier = Modifier,
    value: String,
    validRes: UserValidationResult,
    onValueChange: (String) -> Unit,
    label: String,
    backgroundColor: Color = Black
) {
    TextField(
        modifier = modifier,
        value = value,
        label = { TextFieldLabel(label = label) },
        onValueChange = onValueChange,
        isError = (validRes != UserValidationResult.Valid
                && validRes != UserValidationResult.Initial),
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
        )
    )
}