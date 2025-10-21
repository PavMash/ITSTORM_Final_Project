package com.itstorm.finalproject.features.authentication.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.components.LogoImage
import com.itstorm.finalproject.shared.components.TitleText
import com.itstorm.finalproject.shared.ui.theme.GreyE5

@Composable
fun TextWithLogo(
    modifier: Modifier = Modifier,
    text: String
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(
            space = 2.dp,
            alignment = Alignment.CenterHorizontally
        ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TitleText(
            text = text,
            color = GreyE5
        )

        LogoImage(modifier = Modifier.size(48.dp))
    }
}