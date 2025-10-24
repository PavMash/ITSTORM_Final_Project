package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.finalproject.R
import com.itstorm.finalproject.features.authentication.view.uicomponents.ErrorMessage
import com.itstorm.finalproject.shared.components.CustomHorizontalDivider
import com.itstorm.finalproject.shared.components.CustomIcon
import com.itstorm.finalproject.shared.components.InputFieldWithValidation
import com.itstorm.finalproject.shared.components.MainButton
import com.itstorm.finalproject.shared.components.SectionTitle
import com.itstorm.finalproject.shared.ui.theme.Grey1A
import com.itstorm.finalproject.shared.ui.theme.Grey67


@Composable
fun SessionInputCard(
    modifier: Modifier = Modifier,
    date: String,
    time: String,
    duration: String,
    errors: List<SessionValidation>,
    availableUsers: List<UserDomain>,
    includedUser: UserDomain?,
    onDateChange: (String) -> Unit,
    onTimeChange: (String) -> Unit,
    onDurationChange: (String) -> Unit,
    onUserStatusChange: (Long) -> Unit,
    onCreateSession: () -> Unit,

    availableStations: List<StationWithSessionsDomain>,
    tariffs: List<TariffDomain>,
    selectedStation: StationWithSessionsDomain?,
    selectedTariff: TariffDomain?,
    stationExpanded: Boolean,
    tariffExpanded: Boolean,
    stationOnDismiss: () -> Unit,
    tariffOnDismiss: () -> Unit,
    stationOnClick: () -> Unit,
    tariffOnClick: () -> Unit,
    onStationChoose: (Long) -> Unit,
    onTariffChoose: (Long) -> Unit
){
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(8.dp),
        colors = CardDefaults.cardColors(
            containerColor = Grey1A,
            disabledContainerColor = Grey1A
        )
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
                .padding(top = 12.dp, bottom = 20.dp,
                    start = 12.dp, end = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SectionTitle(
                text = stringResource (R.string.session_creating_title)
            )

            CustomHorizontalDivider(
                modifier = Modifier.fillMaxWidth()
                    .padding(vertical = 10.dp)
            )

            //Date
            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = date,
                sessValidRes = errors[0],
                onValueChange = onDateChange,
                label = stringResource(R.string.date_textfield_label),
                backgroundColor = Grey1A,
            ) {
                CustomIcon(
                    painter = painterResource(R.drawable.calendar),
                    description = stringResource(R.string.date_textfield_label),
                    tint = Grey67
                )
            }

            if (errors[0] != SessionValidation.Valid
                && errors[0] != SessionValidation.Initial
            ) {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Start)
                        .padding(start = 12.dp),
                    sessValidRes = errors[0]
                )
            }

            //Time
            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = time,
                sessValidRes = errors[1],
                onValueChange = onTimeChange,
                label = stringResource(R.string.time_textfield_label),
                backgroundColor = Grey1A,
            ) {
                CustomIcon(
                    painter = painterResource(R.drawable.clock),
                    description = stringResource(R.string.time_textfield_label),
                    tint = Grey67
                )
            }

            if (errors[1] != SessionValidation.Valid
                && errors[1] != SessionValidation.Initial
            ) {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Start)
                        .padding(start = 12.dp),
                    sessValidRes = errors[1]
                )
            }

            //Duration
            InputFieldWithValidation(
                modifier = Modifier.fillMaxWidth(),
                value = duration,
                sessValidRes = errors[2],
                onValueChange = onDurationChange,
                label = stringResource(R.string.duration_textfield_label),
                backgroundColor = Grey1A,
            )

            if (errors[2] != SessionValidation.Valid
                && errors[2] != SessionValidation.Initial
            ) {
                ErrorMessage(
                    modifier = Modifier.align(Alignment.Start)
                        .padding(start = 12.dp),
                    sessValidRes = errors[2]
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            val allInputsAreValid = errors.all { it == SessionValidation.Valid }

            //Station and tariff
            SessionCreateDropDowns(
                modifier = Modifier.fillMaxWidth(),
                stations = availableStations,
                tariffs = tariffs,
                stationEnabled = allInputsAreValid,
                tariffEnabled = allInputsAreValid,
                stationExpanded = stationExpanded,
                tariffExpanded = tariffExpanded,
                stationOnDismiss = stationOnDismiss,
                tariffOnDismiss = tariffOnDismiss,
                stationOnClick = stationOnClick,
                tariffOnClick = tariffOnClick,
                stationOnSelected = onStationChoose,
                tariffOnSelected = onTariffChoose,
                selectedStation = selectedStation,
                selectedTariff = selectedTariff
            )

            Spacer(modifier = Modifier.height(12.dp))

            SessionCreateUserSection(
                modifier = Modifier.fillMaxWidth(),
                users = availableUsers,
                includedUser = includedUser,
                onUserStatusChanged = onUserStatusChange
            )

            Spacer(modifier = Modifier.height(12.dp))

            val creationEnabled = allInputsAreValid
                    && (selectedStation != null)
                    && (selectedTariff != null)
                    && (includedUser != null)

            MainButton(
                enabled = creationEnabled,
                onClick = onCreateSession,
                label = stringResource(R.string.user_creation_button_text),
                modifier = Modifier.align(Alignment.End)
            )
        }
    }
}