package com.itstorm.finalproject.features.create_session_dialog.view

import kotlinx.coroutines.flow.StateFlow
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.State

interface CreateSessionComponent {

    val model: StateFlow<State>

    fun onDateChanged(date: String)

    fun onStartingTimeChanged(time: String)

    fun onDurationChanged(duration: String)

    fun onStationChosen(id: Long)

    fun onTariffChosen(id: Long)

    fun onUserStatusChange(id: Long)

    fun onCreateSession()

    fun onCloseDialog()

    fun onDismissStations()

    fun onDismissTariff()

    fun onClickStations()

    fun onClickTariffs()
}