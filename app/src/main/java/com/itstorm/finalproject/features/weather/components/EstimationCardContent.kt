package com.itstorm.finalproject.features.weather.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import com.itstorm.finalproject.R
import com.itstorm.finalproject.shared.components.ChipCard
import com.itstorm.finalproject.shared.components.TextWithIcon

@Composable
fun EstimationCardContent(
    city: String,
    temperature: Int,
    innerPadding: PaddingValues) {
    Row(
        modifier = Modifier.fillMaxWidth()
            .padding(innerPadding),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        ChipCard(text = city.uppercase())

        TextWithIcon(
            text = createTemperatureString(temperature),
            iconDescription = stringResource(R.string.temperature_icon_description_text),
            iconRes = painterResource(R.drawable.union)
        )
    }
}

private fun createTemperatureString(temperature: Int): String {
    val degreeSign = 0x00B0
    var tempStr = if (temperature > 0) "+" else ""
    tempStr += temperature.toString() + degreeSign.toChar().toString() + "C"
    return tempStr
}