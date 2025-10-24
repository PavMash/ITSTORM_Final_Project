package com.itstorm.finalproject.features.session_dashboard.view.uicomponents

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.finalproject.shared.utils.getDateString
import com.itstorm.finalproject.shared.utils.getTimeString
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun SessionInfoBar(
    modifier: Modifier = Modifier,
    session: SessionWithUserDomain,
    onUpdateClick: () -> Unit
){
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {

        val zoneId = ZoneId.systemDefault()
        val startInLocalDateTime = LocalDateTime.ofInstant(session.start, zoneId)
        val sessionDate = startInLocalDateTime.toLocalDate()
        val startTime = startInLocalDateTime.toLocalTime()

        val endTime = LocalDateTime
            .ofInstant(session.end, zoneId).toLocalTime()

        val currentDate = LocalDate.now()

        SessionDateTimeText(
            text =
                "${getDateString(sessionDate, currentDate)}, ${getTimeString(startTime, endTime)}"
        )

        UpdateSessionInfoButton(
            modifier = Modifier.padding(4.dp),
            onClick = onUpdateClick,
            enabled = (session.status != SessionStatus.Finished)
                    && (session.status != SessionStatus.Cancelled)
        )
    }
}