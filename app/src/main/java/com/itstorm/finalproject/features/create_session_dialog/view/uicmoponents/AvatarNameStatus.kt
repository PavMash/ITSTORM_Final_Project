package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.shared.components.OnlineStausText
import com.itstorm.finalproject.shared.components.SubtitleText
import com.itstorm.finalproject.shared.ui.theme.Blue2C
import com.itstorm.finalproject.shared.ui.theme.White
import com.itstorm.finalproject.shared.utils.randomColorFromPool

@Composable
fun AvatarNameStatus(
    modifier: Modifier = Modifier,
    id: Long,
    name: String,
    isOnline: Boolean
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier.size(40.dp)
                .background(
                    shape = CircleShape,
                    color = randomColorFromPool(id.toInt())
                ),
            contentAlignment = Alignment.Center
        ) {
            SubtitleText(
                text = name.first().toString().uppercase(),
                color = Blue2C
            )
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            SubtitleText(
                text = name,
                color = White
            )

            OnlineStausText(isOnline = isOnline)
        }
    }
}