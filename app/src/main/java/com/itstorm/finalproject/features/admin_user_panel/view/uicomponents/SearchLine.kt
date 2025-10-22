package com.itstorm.finalproject.features.admin_user_panel.view.uicomponents

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.shared.components.TextFieldLabel
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun SearchLine(
    modifier: Modifier = Modifier,
    value: String,
    label: String,
    onValueChange: (String) -> Unit
) {
    OutlinedTextField(
        modifier = modifier,
        value = value,
        onValueChange = onValueChange,
        label = { TextFieldLabel(label) },
        trailingIcon = {
            CustomIcon(
                modifier = Modifier.size(35.dp),
                painter = painterResource(R.drawable.search_icon),
                description = stringResource(R.string.search_icon_description)
            ) },
        colors = TextFieldDefaults.colors(
            focusedTextColor = White,
            unfocusedTextColor = White,
            focusedContainerColor = Black,
            unfocusedContainerColor = Black,
            focusedIndicatorColor = Grey34,
            unfocusedIndicatorColor = Grey34
        ),
        shape = RoundedCornerShape(6.dp),
    )
}