package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.Grey67
import com.itstorm.finalproject.shared.ui.theme.GreyE5

@Composable
fun<T> CustomDropDownMenu(
    modifier: Modifier = Modifier,
    text: String,
    selectedItem: T?,
    itemText: (T) -> String,

    enabled: Boolean,
    items: List<T>,
    expanded: Boolean,
    onDismiss: () -> Unit,
    onClick: () -> Unit,
    onItemSelect: (T) -> Unit
) {
    val selectedOptionText = selectedItem?.let(itemText) ?: text

    Box(modifier = modifier) {

        Button(
            onClick = onClick,
            enabled = enabled,
            shape = RoundedCornerShape(
                topStart = 8.dp,
                topEnd = 8.dp,
                bottomStart = if (expanded) 0.dp else 8.dp,
                bottomEnd = if (expanded) 0.dp else 8.dp
            ),
            colors = ButtonDefaults.buttonColors(
                containerColor = GreyE5,
                disabledContainerColor = Grey67
            ),
            contentPadding = PaddingValues(horizontal = 12.dp, vertical = 8.dp)
        ) {
            Text(
                text = selectedOptionText,
                color = if (enabled) Black else Grey34,
                fontSize = 14.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }


        DropdownMenu(
            expanded = expanded,
            onDismissRequest = onDismiss,
            modifier = Modifier
                .background(GreyE5, shape = RoundedCornerShape(bottomStart = 8.dp, bottomEnd = 8.dp)),
            containerColor = GreyE5
        ) {
            items.forEach { item ->
                DropdownMenuItem(
                    text = {
                        Text(
                            text = itemText(item),
                            modifier = Modifier.padding(vertical = 4.dp)
                        )
                    },
                    onClick = {
                        onItemSelect(item)
                        onDismiss()
                    }
                )
            }
        }
    }
}