package com.itstorm.finalproject.features.create_session_dialog.store

import android.util.Log
import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.session.SessionStatus
import com.itstorm.core_domain.models.session.SessionValidation
import com.itstorm.core_domain.models.session.SessionWithUserDomain
import com.itstorm.core_domain.models.station.StationWithSessionsDomain
import com.itstorm.core_domain.models.station.toStationDomain
import com.itstorm.core_domain.models.tariff.TariffDomain
import com.itstorm.core_domain.models.tariff.TariffType
import com.itstorm.core_domain.models.user.UserDomain
import com.itstorm.core_domain.models.user.toUserDomain
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.core_domain.repositories.StationRepository
import com.itstorm.core_domain.repositories.TariffRepository
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.core_domain.usecases.SessionTimeCalculator
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Intent
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.State
import com.itstorm.finalproject.features.create_session_dialog.store.CreateSessionStore.Label
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime
import java.time.ZoneId

class CreateSessionStoreFactory(
    private val storeFactory: StoreFactory,
    private val sessionRepo: SessionRepository,
    private val userRepo: UserRepository,
    private val tariffRepo: TariffRepository,
    private val stationRepo: StationRepository
) {

    fun create(): CreateSessionStore = object:
        CreateSessionStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CreateStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.LoadResources),
            executorFactory = { ExecutorImpl(
                userRepo = userRepo,
                sessionRepo = sessionRepo,
                stationRepo = stationRepo,
                tariffRepo = tariffRepo) },
            reducer = ReducerImpl
        ){}

    sealed interface Msg {
        data class DateChanged(val date: String, val validRes: SessionValidation): Msg
        data class StartTimeChanged(val time: String, val validRes: SessionValidation): Msg
        data class DurationChanged(val duration: String, val validRes: SessionValidation): Msg
        data class UserIncluded(val user: UserDomain): Msg
        data class UserExcluded(val user: UserDomain): Msg
        data class StationChosen(val station: StationWithSessionsDomain): Msg
        data class TariffChosen(val tariff: TariffDomain): Msg
        data object StartingTimeExpired: Msg
        data class UsersLoaded(val users: List<UserDomain>): Msg
        data class TariffsLoaded(val tariffs: List<TariffDomain>): Msg
        data class StationsLoaded(val stations: List<StationWithSessionsDomain>): Msg
        data object StationDropDownClicked: Msg
        data object StationDropDownDismissed: Msg
        data object TariffDropDownClicked: Msg
        data object TariffDropDownDismissed: Msg
    }

    sealed interface Action {
        data object LoadResources: Action
        data object LoadUsers: Action
        data object LoadTariffs: Action
        data object LoadStations: Action
    }

    private class ExecutorImpl(
        private val sessionRepo: SessionRepository,
        private val userRepo: UserRepository,
        private val stationRepo: StationRepository,
        private val tariffRepo: TariffRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.ChangeDate -> changeDate(intent.date)

                is Intent.ChangeStartTime -> changeStartTime(intent.time)

                is Intent.ChangeDuration -> changeDuration(intent.hours)

                is Intent.ChangeUserStatus -> changeUserStatus(intent.id)

                is Intent.ChooseStation -> chooseStation(intent.id)

                is Intent.ChooseTariff -> chooseTariff(intent.id)

                is Intent.CreateSession -> {
                    Log.d("sessionsDebug", "createWillStartWorking")
                    createSession()}

                is Intent.CloseDialog ->
                    publish(Label.CloseDialog)

                is Intent.ClickStationDropDown -> dispatch(Msg.StationDropDownClicked)

                is Intent.DismissStationDropDown -> dispatch(Msg.StationDropDownDismissed)

                is Intent.ClickTariffDropDown -> dispatch(Msg.TariffDropDownClicked)

                is Intent.DismissTariffDropDown -> dispatch(Msg.TariffDropDownDismissed)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.LoadUsers ->
                    loadUsers()
                is Action.LoadTariffs ->
                    loadTariffs()
                is Action.LoadStations ->
                    loadStations()
                is Action.LoadResources ->
                    loadResources()
            }

        private fun changeDate(date: String) {
            val dateRegex = Regex("""^(0[1-9]|[12][0-9]|3[01])\.(0[1-9]|1[0-2])$""")

            if (!dateRegex.matches(date)) {
                dispatch(Msg.DateChanged(date, SessionValidation.WrongDateFormat))
            } else {
                val zoneId = ZoneId.systemDefault()
                val calculator = SessionTimeCalculator(zoneId)
                val localDate = calculator.dateToLocalDate(date)

                if (localDate < LocalDate.now()) {
                    dispatch(Msg.DateChanged(date, SessionValidation.PastDate))
                } else {
                    dispatch(Msg.DateChanged(date, SessionValidation.Valid))
                }
            }
        }

        private fun changeStartTime(time: String) {
            val timeRegex = Regex("""^([01]\d|2[0-3]):[0-5]\d$""")

            if(!timeRegex.matches(time)) {
                dispatch(Msg.StartTimeChanged(time, SessionValidation.WrongTimeFormat))
            } else {
                val zoneId = ZoneId.systemDefault()
                val calculator = SessionTimeCalculator(zoneId)
                val localTime = calculator.timeToLocal(time)

                if (localTime <= LocalTime.now()) {
                    dispatch(Msg.StartTimeChanged(time, SessionValidation.PastTime))
                } else {
                    dispatch(Msg.StartTimeChanged(time, SessionValidation.Valid))
                }
            }
        }

        private fun changeDuration(hours: String) {
            val nonNegDoubleRegex = Regex("""^\d+(\.\d+)?$""")

            if (!nonNegDoubleRegex.matches(hours)) {
                dispatch(Msg.DurationChanged(hours,
                    SessionValidation.WrongDurationFormat))
            } else {
                val hoursDouble = hours.toDouble()
                val fractionPart = hoursDouble % 1

                if ((60 * fractionPart) % 1 != 0.0) {
                    dispatch(
                        Msg.DurationChanged(
                            hours,
                            SessionValidation.FractionMinutes))
                } else {
                    dispatch(Msg.DurationChanged(hours, SessionValidation.Valid))
                }
            }
        }

        private fun changeUserStatus(id: Long) {
            val user = state().availableUsers.find { it.id == id }
            user?.let {
                if (state().includedUser == user) {
                    dispatch(Msg.UserExcluded(user))
                } else {
                    dispatch(Msg.UserIncluded(user))
                }
            }
        }

        private fun chooseStation(id: Long) {
            val station = state().availableStations.find { it.id == id }
            station?.let {
                dispatch(Msg.StationChosen(station))
            }
        }

        private fun chooseTariff(id: Long) {
            val tariff = state().allTariffs.find { it.id == id }
            tariff?.let {
                dispatch(Msg.TariffChosen(tariff))
            }
        }

        private fun createSession() {
            //Log.d("sessionsDebug", "createStartedWorking")
            val state = state()
            val zoneId = ZoneId.systemDefault()
            val calculator = SessionTimeCalculator(zoneId)

            Log.d("sessionsDebug", "about to convert time to local")
            val localTime = calculator.timeToLocal(state.time)

            if (localTime <= LocalTime.now()) {
                dispatch(Msg.StartingTimeExpired)
            } else {
                Log.d("sessionsDebug", "about to convert date to local")

                val localDate = calculator.dateToLocalDate(state.date)

                Log.d("sessionsDebug", "just converted all to local")

                val start = calculator.startTimeToInstant(localDate, localTime)

                Log.d("sessionsDebug", "just got start instant")
                val end = calculator.calculateEndTime(start,
                    state.duration.toDouble())

                Log.d("sessionsDebug", "just converted all staff")

                state.includedUser?.let {
                    val session = SessionWithUserDomain(
                        user = state.includedUser,
                        station = state.station!!.toStationDomain(),
                        start = start,
                        end = end,
                        mainTariff = state.tariff!!,
                        currentTariff = state.tariff,
                        status = SessionStatus.Scheduled,
                        sum = 0
                    )

                    Log.d("sessionsDebug", "session is about to send to repo")

                    scope.launch(Dispatchers.IO) {
                        Log.d("sessionsDebug", "already in scope")
                        val sessionId = sessionRepo.createSession(session)
                        Log.d("sessionsDebug", "sessionInserted: $sessionId")
                    }
                    publish(Label.CreateSession)
                }
            }
        }

        private fun loadUsers() {
            scope.launch {
                userRepo.getAllUsers().collect { users ->
                    dispatch(Msg.UsersLoaded(users.map { it.toUserDomain() }))
                }
            }
        }

        private fun loadTariffs() {
            scope.launch {
                tariffRepo.getAllTariffs().collect { tariffs ->
                    dispatch(Msg.TariffsLoaded(tariffs))
                }
            }
        }

        private fun loadStations() {
            scope.launch {
                stationRepo.getAllStations().collect { stations ->
                    dispatch(Msg.StationsLoaded(stations))
                }
            }
        }

        private fun loadResources() {
            loadUsers()
            loadStations()
            loadTariffs()
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.StationsLoaded ->
                    copy(
                        allStations = msg.stations,
                    )
                is Msg.UsersLoaded ->
                    copy(
                        availableUsers = msg.users.filter { !it.isBlocked }
                    )
                is Msg.TariffsLoaded ->
                    copy(
                        allTariffs = msg.tariffs.filter { it.type != TariffType.System }
                    )
                is Msg.DateChanged ->
                    copy(
                        date = msg.date,
                        errors = errors.changeValidityForField(0,
                            msg.validRes)
                    )
                is Msg.StartTimeChanged ->
                    copy(
                        time = msg.time,
                        errors = errors.changeValidityForField(1, msg.validRes)
                    )
                is Msg.DurationChanged ->
                    copy(
                        duration = msg.duration,
                        errors = errors.changeValidityForField(2, msg.validRes)
                    )
                is Msg.UserIncluded ->
                    copy(
                        includedUser = msg.user
                    )
                is Msg.UserExcluded ->
                    copy(
                        includedUser = null
                    )
                is Msg.StationChosen ->
                    copy(
                        station = msg.station,
                        stationExpanded = false
                    )
                is Msg.TariffChosen ->
                    copy(
                        tariff = msg.tariff,
                        tariffExpanded = false
                    )
                is Msg.StartingTimeExpired ->
                    copy(
                        errors = errors.changeValidityForField(1,
                            SessionValidation.PastTime)
                    )
                is Msg.StationDropDownClicked ->
                    copy(
                        stationExpanded = true,
                        availableStations = allStations.findAvailable(date, time, duration)
                    )
                is Msg.StationDropDownDismissed ->
                    copy(stationExpanded = false)
                is Msg.TariffDropDownClicked ->
                    copy(tariffExpanded = true)
                is Msg.TariffDropDownDismissed ->
                    copy(tariffExpanded = false)
            }

        private fun List<StationWithSessionsDomain>
                .findAvailable(date: String, time: String, duration: String):
                List<StationWithSessionsDomain> {
            val zoneId = ZoneId.systemDefault()
            val calculator = SessionTimeCalculator(zoneId)

            val dateLocal = calculator.dateToLocalDate(date)
            val timeLocal = calculator.timeToLocal(time)
            val durationDouble = duration.toDouble()

            val start = calculator.startTimeToInstant(dateLocal, timeLocal)
            val end = calculator.calculateEndTime(start, durationDouble)

            val available = this.filter {
                it.sessions.filter { session ->
                    session.status != SessionStatus.Finished
                            && session.status != SessionStatus.Cancelled
                }.all { session -> !calculator.intervalsIntersect(
                    session.start, session.end,
                    start, end) }
            }

            return available
        }

        private fun List<SessionValidation>.
                changeValidityForField(index: Int, newValidRes: SessionValidation
        ): List<SessionValidation> {
            val newErrors = this.toMutableList()
            if (0 <= index && index < this.size) {
                newErrors[index] = newValidRes
            }
            return newErrors
        }
    }
}

