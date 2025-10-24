package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.ui.theme.GreyE5

@Composable
fun StatusCheckBox(
    modifier: Modifier = Modifier,
    isChecked: Boolean,
    onClick: () -> Unit,
    color: Color = GreyE5
) {
    IconButton(
        modifier = modifier,
        onClick = onClick
    ) {
        Icon(
            painter = if (isChecked)
                painterResource(R.drawable.checked_box) else
            painterResource(R.drawable.uncheked_box),
            contentDescription = null,
            tint = color
        )
    }
}