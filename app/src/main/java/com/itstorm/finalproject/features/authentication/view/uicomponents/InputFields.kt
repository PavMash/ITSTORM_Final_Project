package com.itstorm.finalproject.features.authentication.view.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.TextFieldLabel
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.Red0C
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun InputFields(
    modifier: Modifier = Modifier,
    login: String,
    password: String,
    loginErr: UserValidationResult,
    passwordErr: UserValidationResult,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = login,
            label = { TextFieldLabel(
                stringResource(R.string.login_textfield_label)) },
            onValueChange = onLoginChange,
            isError = (loginErr != UserValidationResult.Valid
                    && loginErr != UserValidationResult.Initial),
            colors = TextFieldDefaults.colors(
                focusedTextColor = White,
                unfocusedTextColor = White,
                errorTextColor = White,
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C,
            )
        )

        if (loginErr != UserValidationResult.Valid
            && loginErr != UserValidationResult.Initial) {
            ErrorMessage(
                modifier = Modifier.align(Alignment.Start)
                    .padding(start = 12.dp),
                usrValidRes = loginErr)
        }

        TextField(
            modifier = Modifier.fillMaxWidth(),
            value = password,
            label = { TextFieldLabel(
                    stringResource(R.string.password_textfield_label)) },
            onValueChange = onPasswordChange,
            isError = (passwordErr != UserValidationResult.Valid
                    && passwordErr != UserValidationResult.Initial),
            visualTransformation = if (isPasswordVisible)
                VisualTransformation.None
            else PasswordVisualTransformation('\u002A'),
            trailingIcon = {
                PasswordVisibilityButton(
                    isPasswordVisible = isPasswordVisible,
                    onPasswordVisibilityChange = onPasswordVisibilityChange
                )
            },
            colors = TextFieldDefaults.colors(
                focusedTextColor = White,
                unfocusedTextColor = White,
                errorTextColor = White,
                focusedContainerColor = Black,
                unfocusedContainerColor = Black,
                errorContainerColor = Black,
                unfocusedIndicatorColor = Grey34,
                focusedIndicatorColor = Grey34,
                errorIndicatorColor = Red0C
            ),
        )

        if (passwordErr != UserValidationResult.Valid
            && passwordErr != UserValidationResult.Initial) {
            ErrorMessage(
                modifier = Modifier.align(Alignment.Start)
                    .padding(start = 12.dp),
                usrValidRes = passwordErr)
        }
    }
}