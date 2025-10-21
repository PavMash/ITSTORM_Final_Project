package com.itstorm.finalproject.features.authentication.uicomponents

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.R

@Composable
fun PasswordVisibilityButton(
    modifier: Modifier = Modifier,
    isPasswordVisible: Boolean,
    onPasswordVisibilityChange: (Boolean) -> Unit
) {
    IconButton(
        modifier = modifier,
        onClick = { onPasswordVisibilityChange(!isPasswordVisible) },
    ) {
        Icon(
            modifier = Modifier.size(24.dp),
            painter = if (isPasswordVisible)
                painterResource(R.drawable.visibility_off)
            else painterResource(R.drawable.visibility_on),
            contentDescription = stringResource(R.string.password_visibility_description)
        )
    }
}