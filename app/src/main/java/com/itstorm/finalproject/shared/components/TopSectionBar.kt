package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.itstorm.finalproject.R

@Composable
fun TopSectionBar(
    modifier: Modifier = Modifier,
    sectionTitle: String,
    onClickPlus: () -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        SectionTitle(text = sectionTitle)

        IconButton(
            onClick = onClickPlus
        ) {
            CustomIcon(
                painter = painterResource(R.drawable.add_circle_outline),
                description = stringResource(R.string.user_create_button_description)
            )
        }
    }
}