package com.itstorm.finalproject.sharedui.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.sharedui.ui.theme.Grey1A
import com.itstorm.finalproject.sharedui.ui.theme.Grey34
import com.itstorm.finalproject.sharedui.ui.theme.Grey67
import com.itstorm.finalproject.sharedui.ui.theme.GreyE5
import com.itstorm.finalproject.sharedui.ui.theme.robotoFlexFontFamily

@Composable
fun MainButton(
    enabled: Boolean,
    onClick: () -> Unit,
    label: String,
    modifier: Modifier
) {
    Button(
        modifier = modifier,
        onClick = { onClick() },
        content = {
            Text(
                text = label,
                fontFamily = robotoFlexFontFamily,
                fontWeight = FontWeight.W500,
                fontSize = 14.sp
            )
        },
        shape = RoundedCornerShape(4.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
            disabledContainerColor = Grey67,
            containerColor = GreyE5,
            disabledContentColor = Grey34,
            contentColor = Grey1A
        )
    )
}