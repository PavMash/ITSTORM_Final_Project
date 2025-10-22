package com.itstorm.finalproject.features.create_user_dialog.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.features.create_user_dialog.view.uicomponents.InputCard
import com.itstorm.finalproject.shared.components.CloseButton

@Composable
fun CreateUserDialogUI(
    component: CreateUserComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(
                    top = 56.dp, bottom = 10.dp,
                    start = 7.dp, end = 7.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(40.dp))

            CloseButton(
                modifier = Modifier.align(Alignment.End),
                onClose = component::onClose
            )

            Spacer(modifier = Modifier.height(12.dp))

            InputCard(
                login = state.login,
                password = state.password,
                phoneNumber = state.phoneNumber,
                passwordValidRes = state.passwordErrMessage,
                phoneNumberValidRes = state.phoneErrMessage,
                onLoginChange = component::onLoginChange,
                onPasswordChange = component::onPasswordChange,
                onPhoneNumberChange = component::onPhoneNumberChange,
                onSubmit = component::onSubmit
            )
        }
    }
}