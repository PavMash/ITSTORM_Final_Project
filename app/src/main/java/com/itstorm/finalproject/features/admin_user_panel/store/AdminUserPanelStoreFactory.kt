package com.itstorm.finalproject.features.admin_user_panel.store

import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.arkivanov.mvikotlin.core.store.Reducer
import com.itstorm.core_domain.models.user.SearchFilter
import com.itstorm.core_domain.models.user.User
import com.itstorm.core_domain.repositories.UserRepository
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Intent
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.State
import com.itstorm.finalproject.features.admin_user_panel.store.AdminUserPanelStore.Label
import kotlinx.coroutines.launch

class AdminUserPanelStoreFactory(
    private val storeFactory: StoreFactory,
    private val userRepo: UserRepository,
) {

    fun create(): AdminUserPanelStore =
        object: AdminUserPanelStore, Store<Intent, State, Label> by storeFactory.create(
            name = "AdminUserPanelStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Action.UsersLoaded),
            executorFactory = { ExecutorImpl(userRepo) },
            reducer = ReducerImpl
        ){}

    private sealed interface Action {
        data object UsersLoaded: Action
    }

    private sealed interface Msg {
        data class UsersLoaded(val users: List<User>): Msg
        data class UsersFilteredByPartialPhoneNumber(val phNum: String): Msg
        data class UsersFilteredByPartialName(val name: String): Msg
    }

    private class ExecutorImpl(
        private val userRepo: UserRepository
    ): CoroutineExecutor<Intent, Action, State, Msg, Label>() {

        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.SearchForUser ->
                    searchForUser(intent.searchParameter)
                is Intent.ChangeUserBlockedStatus ->
                    changeUserBlockedStatus(intent.id)
                is Intent.CreateUser ->
                    publish(Label.CreateUser)
                is Intent.ClickSessions ->
                    publish(Label.ClickSessions)
            }

        override fun executeAction(action: Action) =
            when(action) {
                is Action.UsersLoaded ->
                    displayUsers()
            }

        private fun searchForUser(searchParam: String) {
            val phoneRegex = Regex("""\+\d{1,11}""")

            if (phoneRegex.matches(searchParam)) {
                dispatch(Msg.UsersFilteredByPartialPhoneNumber(phNum = searchParam))
            } else {
                dispatch(Msg.UsersFilteredByPartialName(name = searchParam))
            }
        }

        private fun changeUserBlockedStatus(id: Long) {
            scope.launch {
                userRepo.updateBlockedStatus(id)
            }
        }

        private fun displayUsers() {
            scope.launch {
                userRepo.getAllUsers().collect { userList ->
                    dispatch(Msg.UsersLoaded(userList))
                }
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when(msg) {
                is Msg.UsersLoaded ->
                    copy(
                        users = msg.users,
                        filtered = msg.users.filterUsers(
                            type = filterType,
                            filter = appliedFilter)
                    )
                is Msg.UsersFilteredByPartialName ->
                    copy(
                        users = users,
                        filtered = users.filterUsers(
                            type = SearchFilter.Name,
                            filter = msg.name)
                    )
                is Msg.UsersFilteredByPartialPhoneNumber ->
                    copy(
                        users = users,
                        filtered = users.filterUsers(
                            type = SearchFilter.Phone,
                            filter = msg.phNum)
                    )
            }

        private fun List<User>.filterUsers(type: SearchFilter?, filter: String): List<User> =
            when(type) {
                SearchFilter.Name ->
                    this.filter { it.name.contains(filter, ignoreCase = true) }
                SearchFilter.Phone ->
                    this.filter { it.phoneNumber.contains(filter) }
                else ->
                    this
            }
    }
}