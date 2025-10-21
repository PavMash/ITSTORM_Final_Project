package com.itstorm.finalproject.features.authentication.view

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.authentication.uicomponents.InputFields
import com.itstorm.finalproject.features.authentication.uicomponents.TextWithLogo
import com.itstorm.finalproject.shared.components.MainButton
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme

@Composable
fun AuthenticationUI(component: AuthenticationComponent) {
    FinalProjectTheme {
        Scaffold(
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            AuthenticationScreen(component, innerPadding)
        }
    }
}

@Composable
fun AuthenticationScreen(
    component: AuthenticationComponent,
    innerPadding: PaddingValues
) {
    val state by component.model.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
            .padding(innerPadding)
    ) {

        TextWithLogo(
            modifier = Modifier.align(Alignment.CenterHorizontally)
                .padding(top = 60.dp),
            text = stringResource(R.string.app_title)
        )

        Spacer(modifier = Modifier.height(178.dp))

        InputFields(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = 12.dp),
            login = state.login,
            password = state.password,
            loginErr = state.loginErrMessage,
            passwordErr = state.passwordErrMessage,
            isPasswordVisible = state.isPasswordVisible,
            onPasswordVisibilityChange = component::onPasswordVisibilityChange,
            onLoginChange = component::onLoginValidate,
            onPasswordChange = component::onPasswordValidate
        )

        Spacer(modifier = Modifier.height(32.dp))

        MainButton(
            modifier = Modifier.align(Alignment.End).padding(end = 12.dp),
            enabled = (state.loginErrMessage == UserValidationResult.Valid)
                    && (state.passwordErrMessage == UserValidationResult.Valid),
            onClick = { component.onCredentialsSubmit()},
            label = stringResource(R.string.enter_button_text)
        )
    }
}