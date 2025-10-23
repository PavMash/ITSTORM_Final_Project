package com.itstorm.finalproject.features.authentication.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Intent
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.AuthState
import com.itstorm.finalproject.features.authentication.store.AuthenticationStore.Label
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.core_domain.repositories.UserRepository
import kotlinx.coroutines.launch

class AuthenticationStoreFactory(
    private val storeFactory: StoreFactory,
    private val userRepo: UserRepository
) {

    fun create(): AuthenticationStore =
        object:  AuthenticationStore, Store<Intent, AuthState, Label> by storeFactory.create(
            name = "WeatherStore",
            initialState = AuthState(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = { AuthenticationExecutor(userRepo) },
            reducer = AuthenticationReducer
        ) {}

    private sealed interface Msg {
        data class LoginChanged(val newLogin: String): Msg
        data class LoginValidated(val newLogin: String,
                                  val validationRes: UserValidationResult): Msg
        data class PasswordValidated(val newPassword: String,
                                     val validationRes: UserValidationResult): Msg
        data class PasswordVisibilityChanged(val isVisible: Boolean): Msg
    }

    private class AuthenticationExecutor(
        private val userRepo: UserRepository
    ): CoroutineExecutor<Intent, Unit, AuthState, Msg, Label>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.SubmitLoginCredentials ->
                    submitCredentials()
                is Intent.ChangeLogin ->
                    dispatch(Msg.LoginChanged(intent.login))
                is Intent.ValidatePassword ->
                    validatePassword(intent.password)
                is Intent.ChangePasswordVisibility ->
                    dispatch(Msg.PasswordVisibilityChanged(intent.isVisible))
            }

        private fun submitCredentials() {
            val state = state()
            scope.launch {
                val user = userRepo.getUserByLogin(state.login)

                if (user == null) {
                    dispatch(Msg.LoginValidated(newLogin = state.login,
                        validationRes = UserValidationResult.WrongLogin))
                }
                user?.let {
                    if (user.password != state.password) {
                        dispatch(Msg.PasswordValidated(newPassword = state.password,
                            validationRes = UserValidationResult.WrongPassword))
                    } else {
                        userRepo.updateOnlineStatus(user.id)
                        publish(Label.EnterApp(user))
                    }
                }
            }
        }

        private fun validatePassword(password: String) {
            val latinRegex = Regex("^(?=.*[A-Za-z]).+$")
            val digitRegex = Regex("^(?=.*\\d).+$")

            when {
                (!latinRegex.matches(password)) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validationRes = UserValidationResult.NoLatinLettersInPw
                        )
                    )

                (!digitRegex.matches(password)) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validationRes = UserValidationResult.NoDigitsInPw
                        )
                    )

                (password.length < 6) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validationRes = UserValidationResult.ShortPw
                        )
                    )

                else -> dispatch(
                    Msg.PasswordValidated(
                        newPassword = password,
                        validationRes = UserValidationResult.Valid
                    )
                )
            }
        }
    }

    private object AuthenticationReducer: Reducer<AuthState, Msg> {
        override fun AuthState.reduce(msg: Msg): AuthState =
            when (msg) {
                is Msg.LoginChanged ->
                    copy(
                        login = msg.newLogin,
                        password = password,
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = UserValidationResult.Valid,
                        isPasswordVisible = isPasswordVisible,
                    )
                is Msg.LoginValidated ->
                    copy(
                        login = msg.newLogin,
                        password = password,
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = msg.validationRes,
                        isPasswordVisible = isPasswordVisible
                    )

                is Msg.PasswordValidated ->
                    copy(
                        login = login,
                        password = msg.newPassword,
                        passwordErrMessage = msg.validationRes,
                        loginErrMessage = loginErrMessage,
                        isPasswordVisible = isPasswordVisible
                    )

                is Msg.PasswordVisibilityChanged -> {
                    copy(
                        login = login,
                        password = password,
                        passwordErrMessage = passwordErrMessage,
                        loginErrMessage = loginErrMessage,
                        isPasswordVisible = msg.isVisible
                    )
                }

            }
    }
}