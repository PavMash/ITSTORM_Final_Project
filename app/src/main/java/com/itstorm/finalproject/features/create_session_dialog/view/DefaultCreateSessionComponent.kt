package com.itstorm.finalproject.features.create_session_dialog.view

import android.util.Log
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.essenty.lifecycle.coroutines.coroutineScope
import com.arkivanov.mvikotlin.core.instancekeeper.getStore
import com.arkivanov.mvikotlin.extensions.coroutines.labels
import com.arkivanov.mvikotlin.extensions.coroutines.stateFlow
import com.arkivanov.mvikotlin.main.store.DefaultStoreFactory
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.core_domain.repositories.StationRepository
import com.itstorm.core_domain.repositories.TariffRepository
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStoreFactory
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.State
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Intent
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Label
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DefaultCreateSessionComponent(
    componentContext: ComponentContext,
    sessionRepo: SessionRepository,
    userRepo: UserRepository,
    stationRepo: StationRepository,
    tariffRepo: TariffRepository,
    comeBackToDashboard: () -> Unit
): CreateSessionComponent, ComponentContext by componentContext {

    private val store = instanceKeeper.getStore {
        CreateSessionStoreFactory(
            storeFactory = DefaultStoreFactory(),
            sessionRepo = sessionRepo,
            userRepo = userRepo,
            stationRepo = stationRepo,
            tariffRepo = tariffRepo).create()
    }

    override val model: StateFlow<State> = store.stateFlow

    private val scope = coroutineScope()

    init{
        scope.launch {
            store.labels.collect {
                when(it) {
                    is Label.CreateSession ->
                        comeBackToDashboard()
                    is Label.CloseDialog ->
                        comeBackToDashboard()
                }
            }
        }
    }

    override fun onDateChanged(date: String) {
        store.accept(Intent.ChangeDate(date))
    }

    override fun onStartingTimeChanged(time: String) {
        store.accept(Intent.ChangeStartTime(time))
    }

    override fun onDurationChanged(duration: String) {
        store.accept(Intent.ChangeDuration(duration))
    }

    override fun onStationChosen(id: Long) {
        store.accept(Intent.ChooseStation(id))
    }

    override fun onTariffChosen(id: Long) {
        store.accept(Intent.ChooseTariff(id))
    }

    override fun onUserStatusChange(id: Long) {
        store.accept(Intent.ChangeUserStatus(id))
    }

    override fun onCreateSession() {
        store.accept(Intent.CreateSession)
    }

    override fun onCloseDialog() {
        store.accept(Intent.CloseDialog)
    }

    override fun onDismissStations() {
        store.accept(Intent.DismissStationDropDown)
    }

    override fun onDismissTariff() {
        store.accept(Intent.DismissTariffDropDown)
    }

    override fun onClickStations() {
        store.accept(Intent.ClickStationDropDown)
    }

    override fun onClickTariffs() {
        store.accept(Intent.ClickTariffDropDown)
    }
}