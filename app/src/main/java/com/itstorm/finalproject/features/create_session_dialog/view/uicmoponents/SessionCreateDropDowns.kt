package com.itstorm.finalproject.features.create_session_dialog.view.uicmoponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.station.toText
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.tariff.toText
import com.itstorm.finalproject.R

@Composable
fun SessionCreateDropDowns(
    modifier: Modifier = Modifier,
    stations: List<StationWithSessionsDomain>,
    tariffs: List<TariffDomain>,
    selectedStation: StationWithSessionsDomain?,
    selectedTariff: TariffDomain?,
    stationEnabled: Boolean,
    tariffEnabled: Boolean,
    stationExpanded: Boolean,
    tariffExpanded: Boolean,
    stationOnDismiss: () -> Unit,
    tariffOnDismiss: () -> Unit,
    stationOnClick: () -> Unit,
    tariffOnClick: () -> Unit,
    stationOnSelected: (Long) -> Unit,
    tariffOnSelected: (Long) -> Unit
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        CustomDropDownMenu(
            modifier = Modifier.wrapContentWidth().padding(horizontal = 12.dp),
            text = stringResource(R.string.station_dropdown_label),
            enabled = stationEnabled,
            items = stations,
            expanded = stationExpanded,
            onDismiss = stationOnDismiss,
            onClick = stationOnClick,
            onItemSelect = {station -> stationOnSelected(station.id) },
            selectedItem = selectedStation,
            itemText = { station -> station.toText() }
        )

        CustomDropDownMenu(
            modifier = Modifier.wrapContentWidth().padding(horizontal = 12.dp),
            text = stringResource(R.string.tariff_dropdown_label),
            enabled = tariffEnabled,
            items = tariffs,
            expanded = tariffExpanded,
            onDismiss = tariffOnDismiss,
            onClick = tariffOnClick,
            onItemSelect = { tariff -> tariffOnSelected(tariff.id) },
            selectedItem = selectedTariff,
            itemText = { tariff -> tariff.toText() },
        )
    }
}