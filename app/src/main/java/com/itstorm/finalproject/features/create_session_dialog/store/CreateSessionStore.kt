package com.itstorm.finalproject.features.create_session_dialog.store

import com.arkivanov.mvikotlin.core.store.Store
import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Intent
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.State
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Label

interface CreateSessionStore: Store<Intent, State, Label> {

    sealed interface Intent {
        data class ChangeDate(val date: String): Intent
        data class ChangeStartTime(val time: String): Intent
        data class ChangeDuration(val hours: String): Intent
        data class ChangeUserStatus(val id: Long): Intent
        data class ChooseStation(val id: Long): Intent
        data class ChooseTariff(val id: Long): Intent
        data object ClickStationDropDown: Intent
        data object ClickTariffDropDown: Intent
        data object DismissStationDropDown: Intent
        data object DismissTariffDropDown: Intent
        data object CreateSession: Intent
        data object CloseDialog: Intent
    }

    data class State(
        val date: String = "",
        val time: String = "",
        val duration: String = "",
        val errors: List<SessionValidation> =
            listOf(SessionValidation.Initial,
                SessionValidation.Initial,
                SessionValidation.Initial,),

        val includedUser: UserDomain? = null,
        val availableUsers: List<UserDomain> = emptyList(),

        val station: StationWithSessionsDomain? = null,
        val availableStations: List<StationWithSessionsDomain> = emptyList(),
        val allStations: List<StationWithSessionsDomain> = emptyList(),
        val stationExpanded: Boolean = false,

        val tariff: TariffDomain? = null,
        val allTariffs: List<TariffDomain> = emptyList(),
        val tariffExpanded: Boolean = false
    )

    sealed interface Label {
        data object CreateSession: Label
        data object CloseDialog: Label
    }
}