package com.itstorm.finalproject.features.authentication.view

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import kotlinx.coroutines.flow.MutableStateFlow

@Preview(
    showBackground = true,
    showSystemUi = true,
    name = "Authentication Screen Preview"
)
@Composable
fun AuthenticationUIPreview() {
    FinalProjectTheme {
        val fakeComponent = object : AuthenticationComponent {
            override val model = MutableStateFlow(
                AuthenticationStore.AuthState(
                    login = "Логин_Юзера",
                    password = "pass1234",
                    loginErrMessage = UserValidationResult.Valid,
                    passwordErrMessage = UserValidationResult.Valid,
                    isPasswordVisible = false
                )
            )

            override fun onLoginChange(login: String) {}
            override fun onPasswordValidate(password: String) {}
            override fun onCredentialsSubmit() {}
            override fun onPasswordVisibilityChange(isVisible: Boolean) {}
        }

        AuthenticationUI(component = fakeComponent)
    }
}
