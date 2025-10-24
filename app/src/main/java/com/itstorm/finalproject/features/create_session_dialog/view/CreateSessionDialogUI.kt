package com.itstorm.finalproject.features.create_session_dialog.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents.SessionInputCard
import com.itstorm.finalproject.shared.components.CloseButton

@Composable
fun CreateSessionDialogUI(
    component: CreateSessionComponent,
    modifier: Modifier = Modifier
) {
    val state by component.model.collectAsState()

    Box(modifier = modifier) {
        Column(
            modifier = Modifier.align(Alignment.TopCenter)
                .padding(
                    top = 56.dp, bottom = 10.dp,
                    start = 7.dp, end = 7.dp
                ),
            horizontalAlignment = Alignment.CenterHorizontally,

            ) {
            Spacer(modifier = Modifier.height(40.dp))

            CloseButton(
                modifier = Modifier.align(Alignment.End),
                onClose = component::onCloseDialog
            )

            Spacer(modifier = Modifier.height(12.dp))

            SessionInputCard(
                date = state.date,
                time = state.time,
                duration = state.duration,
                errors = state.errors,
                availableUsers = state.availableUsers,
                includedUser = state.includedUser,
                onDateChange = component::onDateChanged,
                onTimeChange = component::onStartingTimeChanged,
                onDurationChange = component::onDurationChanged,
                onUserStatusChange = component::onUserStatusChange,
                onCreateSession = component::onCreateSession,
                availableStations = state.availableStations,
                tariffs = state.allTariffs,
                stationExpanded = state.stationExpanded,
                tariffExpanded = state.tariffExpanded,
                stationOnDismiss = component::onDismissStations,
                tariffOnDismiss = component::onDismissTariff,
                stationOnClick = component::onClickStations,
                tariffOnClick = component::onClickTariffs,
                onStationChoose = component::onStationChosen,
                onTariffChoose = component::onTariffChosen,
                selectedStation = state.station,
                selectedTariff = state.tariff
            )
        }
    }
}