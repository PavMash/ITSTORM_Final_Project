package com.itstorm.finalproject.shared.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Blue2C
import com.itstorm.finalproject.shared.ui.theme.Red8100
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.utils.randomColorFromPool

@Composable
fun AvatarWithName(
    modifier: Modifier = Modifier,
    name: String,
    avatarColor: Color,
    isBlocked: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {

        Box(
            modifier = Modifier.size(40.dp)
                .background(
                    shape = CircleShape,
                    color = avatarColor
                ),
            contentAlignment = Alignment.Center
        ) {
            SubtitleText(
                text = name.first().toString().uppercase(),
                color = Blue2C
            )

            if (isBlocked)
                Box(
                    modifier = Modifier.fillMaxSize()
                        .background(
                            shape = CircleShape,
                            color = Black.copy(alpha = 0.4f)
                        )
                )
        }

        Spacer(modifier = Modifier.width(16.dp))

        SecondaryText(
            text = name,
            color = if (isBlocked) Red8100 else White,
            modifier = Modifier.fillMaxWidth(0.4f)
        )
    }
}