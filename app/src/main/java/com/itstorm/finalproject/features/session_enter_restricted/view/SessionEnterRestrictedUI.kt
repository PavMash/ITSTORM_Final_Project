package com.itstorm.finalproject.features.session_enter_restricted.view

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import com.itstorm.finalproject.shared.components.SecondaryText
import com.itstorm.finalproject.shared.ui.theme.Black
import com.itstorm.finalproject.shared.ui.theme.FinalProjectTheme
import com.itstorm.finalproject.shared.ui.theme.Grey34
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.White

@Composable
fun SessionEnterRestrictedUI(component: SessionEnterRestrictedComponent) {
    FinalProjectTheme {
        Box(
            modifier = Modifier.fillMaxSize()
                .background(color = Black),
            contentAlignment = Alignment.Center
        ) {

            Box(
                modifier = Modifier.background(
                    color = Black,
                    shape = RoundedCornerShape(4.dp))
                    .border(
                        color = Grey34,
                        shape = RoundedCornerShape(4.dp),
                        width = 1.dp)
                    .padding(6.dp)
            ) {

                Row(
                    modifier = Modifier.fillMaxWidth(0.75f),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    CustomIcon(
                        painter = painterResource(R.drawable.sad_face),
                        description = stringResource(R.string.no_access),
                        tint = White
                    )

                    Spacer(modifier = Modifier.width(4.dp))

                    SecondaryText(
                        text = stringResource(R.string.session_enter_restricted_text),
                        color = GreyE5
                    )
                }

            }
        }
    }
}