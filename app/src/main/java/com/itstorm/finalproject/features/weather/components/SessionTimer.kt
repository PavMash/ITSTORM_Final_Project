package com.itstorm.finalproject.features.weather.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.shared.components.SecondaryText
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun SessionTimer(
    modifier: Modifier = Modifier,
    startTime: String,
    endTime: String
) {

    Box(
        modifier = modifier.background(
            color = Black,
            shape = RoundedCornerShape(4.dp))
            .border(
                color = Grey34,
                shape = RoundedCornerShape(4.dp),
                width = 1.dp)
            .padding(6.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            CustomIcon(
                painter = painterResource(R.drawable.clock),
                description = stringResource(R.string.time_text),
                tint = White
            )

            Spacer(modifier = Modifier.width(4.dp))

            SecondaryText(
                text = "${stringResource(R.string.session_timer_text)} $startTime до $endTime",
                color = GreyE5
            )
        }
    }

}