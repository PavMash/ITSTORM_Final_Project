package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.CustomHorizontalDivider
import com.itstorm.finalproject.shared.ui.theme.GreyE5
import com.itstorm.finalproject.shared.ui.theme.robotoFlexFontFamily

@Composable
fun SessionCreateUserSection(
    modifier: Modifier = Modifier,
    users: List<UserDomain>,
    includedUser: UserDomain?,
    onUserStatusChanged: (Long) -> Unit
) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            modifier = Modifier.align(Alignment.Start),
            text = stringResource(R.string.users_section_title).uppercase(),
            fontFamily = robotoFlexFontFamily,
            fontSize = 14.sp,
            fontWeight = FontWeight.W500,
            color = GreyE5
        )

        Spacer(modifier = Modifier.height(10.dp))

        LazyColumn(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            items(users) { item ->
                SessionCreateUserEntry(
                    modifier = Modifier.fillMaxWidth(),
                    user = item,
                    onUserSelected = { onUserStatusChanged(item.id) },
                    isIncluded = includedUser == item,
                )

                val itemInd = users.indexOf(item)
                if (itemInd < users.size - 1) {
                    CustomHorizontalDivider(
                        modifier = Modifier.fillMaxWidth()
                            .padding(4.dp)
                    )
                }
            }
        }
    }
}