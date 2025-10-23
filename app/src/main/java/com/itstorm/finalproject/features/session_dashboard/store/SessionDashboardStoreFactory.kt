package com.itstorm.finalproject.features.session_dashboard.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.session.SessionDomain
import com.itstorm.core_domain.repositories.SessionRepository
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Intent
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.State
import com.itstorm.finalproject.features.session_dashboard.store.SessionDashboardStore.Label
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SessionDashboardStoreFactory(
    private val storeFactory: StoreFactory,
    private val sessionRepo: SessionRepository
) {
    fun create(): SessionDashboardStore = object:
        SessionDashboardStore, Store<Intent, State, Label> by storeFactory.create(
        name = "SessionStore",
        initialState = State(),
        bootstrapper = SimpleBootstrapper(Action.LoadSessions),
        executorFactory = { ExecutorImpl(sessionRepo) },
        reducer = TODO()
    ){}

    private sealed interface Action {
        data object LoadSessions: Action
    }

    private sealed interface Msg {
        data class SessionsLoaded(val sessions: List<SessionDomain>): Msg
    }

    private class ExecutorImpl(
        private val sessionRepo: SessionRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.CreateSession ->
                    publish(Label.CreateSession)
                is Intent.UpdateSessionInfo ->
                    publish(Label.UpdateSessionInfo)
                is Intent.UserClick ->
                    publish(Label.UsersClick)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.LoadSessions ->
                    loadSessions()
            }

        private fun loadSessions() {
            scope.launch(Dispatchers.IO) {
                sessionRepo.getAllSessions().collect { sessions ->
                    dispatch(Msg.SessionsLoaded(sessions))
                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.SessionsLoaded ->
                    copy(sessions = msg.sessions)
            }
    }
}