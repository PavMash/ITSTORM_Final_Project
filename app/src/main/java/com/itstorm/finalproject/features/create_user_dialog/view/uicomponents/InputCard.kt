package com.itstorm.finalproject.features.create_user_dialog.view.uicomponents

import android.graphics.Paint
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.shared.components.SectionTitle
import com.itstorm.finalproject.shared.ui.theme.Grey1A
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.authentication.view.uicomponents.ErrorMessage
import com.itstorm.finalproject.shared.components.CustomHorizontalDivider
import com.itstorm.finalproject.shared.components.InputFieldWithValidation
import com.itstorm.finalproject.shared.components.MainButton

@Composable
fun InputCard(
    modifier: Modifier = Modifier,
    login: String,
    password: String,
    phoneNumber: String,
    passwordValidRes: UserValidationResult,
    phoneNumberValidRes: UserValidationResult,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
    onPhoneNumberChange: (String) -> Unit,
    onSubmit: (String, String, String) -> Unit
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey1A,
            disabledContainerColor = Grey1A
        )
    ) {

        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(start = 12.dp, end = 12.dp,
                    top = 12.dp, bottom = 20.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {

            SectionTitle(
                text = stringResource(R.string.user_creating_dialog_title)
            )

            CustomHorizontalDivider(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = login,
                validRes = UserValidationResult.Valid,
                onValueChange = onLoginChange,
                label = stringResource(R.string.login_textfield_label),
                backgroundColor = Grey1A
            )

            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = phoneNumber,
                validRes = phoneNumberValidRes,
                onValueChange = onPhoneNumberChange,
                label = stringResource(R.string.phone_number_textfield_label),
                backgroundColor = Grey1A
            )

            if (phoneNumberValidRes != UserValidationResult.Valid
                && phoneNumberValidRes != UserValidationResult.Initial) {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Start)
                        .padding(start = 12.dp),
                    validRes = phoneNumberValidRes)
            }

            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                validRes = passwordValidRes,
                onValueChange = onPasswordChange,
                label = stringResource(R.string.password_textfield_label),
                backgroundColor = Grey1A
            )

            if (passwordValidRes != UserValidationResult.Valid
                && passwordValidRes != UserValidationResult.Initial) {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Start)
                        .padding(start = 12.dp),
                    validRes = passwordValidRes)
            }

            Spacer(modifier = Modifier.height(12.dp))

            MainButton(
                enabled = (phoneNumberValidRes == UserValidationResult.Valid)
                        && (passwordValidRes == UserValidationResult.Valid),
                onClick = { onSubmit(login, password, phoneNumber) },
                label = stringResource(R.string.user_creation_button_text),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}