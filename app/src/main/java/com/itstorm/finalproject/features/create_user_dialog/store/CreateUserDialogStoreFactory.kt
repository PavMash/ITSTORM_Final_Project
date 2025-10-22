package com.itstorm.finalproject.features.create_user_dialog.store

import com.arkivanov.mvikotlin.core.store.Reducer
import com.arkivanov.mvikotlin.core.store.SimpleBootstrapper
import com.arkivanov.mvikotlin.core.store.Store
import com.arkivanov.mvikotlin.core.store.StoreFactory
import com.arkivanov.mvikotlin.extensions.coroutines.CoroutineExecutor
import com.itstorm.core_domain.models.user.UserValidationResult
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Intent
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.State
import com.itstorm.finalproject.features.create_user_dialog.store.CreateUserDialogStore.Label

class CreateUserDialogStoreFactory(
    private val storeFactory: StoreFactory
) {

    fun create(): CreateUserDialogStore =
        object: CreateUserDialogStore, Store<Intent, State, Label> by storeFactory.create(
            name = "CreateUserDialogStore",
            initialState = State(),
            bootstrapper = SimpleBootstrapper(Unit),
            executorFactory = ::ExecutorImpl,
            reducer = ReducerImpl
        ){}

    private sealed interface Msg {
        data class LoginChanged(val newLogin: String) : Msg
        data class PasswordValidated(val newPassword: String,
                                     val validRes: UserValidationResult): Msg

        data class PhoneNumberValidated(val newPhNum: String,
                                        val validRes: UserValidationResult): Msg
    }
    private class ExecutorImpl: CoroutineExecutor<Intent, Unit, State, Msg, Label>() {
        override fun executeIntent(intent: Intent) =
            when(intent) {
                is Intent.ChangeLogin ->
                    dispatch(Msg.LoginChanged(newLogin = intent.login))
                is Intent.ValidatePassword ->
                    validatePassword(password = intent.password)
                is Intent.ValidatePhoneNumber ->
                    validatePhoneNumber(phNum = intent.phNum)
                is Intent.Submit ->
                    publish(Label.Submit(intent.login,
                        intent.password, intent.phoneNumber))
                is Intent.Close ->
                    publish(Label.Close)
            }

        private fun validatePassword(password: String) {
            val latinRegex = Regex("^(?=.*[A-Za-z]).+$")
            val digitRegex = Regex("^(?=.*\\d).+$")

            when {
                (!latinRegex.matches(password)) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validRes = UserValidationResult.NoLatinLettersInPw
                        )
                    )

                (!digitRegex.matches(password)) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validRes = UserValidationResult.NoDigitsInPw
                        )
                    )

                (password.length < 6) ->
                    dispatch(
                        Msg.PasswordValidated(
                            newPassword = password,
                            validRes = UserValidationResult.ShortPw
                        )
                    )

                else -> dispatch(
                    Msg.PasswordValidated(
                        newPassword = password,
                        validRes = UserValidationResult.Valid
                    )
                )
            }
        }

        private fun validatePhoneNumber(phNum: String) {
            val phoneRegex = Regex("^\\+[0-9]+\$")

            if (phoneRegex.matches(phNum)) {
                dispatch(Msg.PhoneNumberValidated(newPhNum = phNum,
                    validRes = UserValidationResult.Valid))
            } else {
                dispatch(Msg.PhoneNumberValidated(newPhNum = phNum,
                    validRes = UserValidationResult.InvalidPhoneNumber))
            }
        }
    }

    private object ReducerImpl: Reducer<State, Msg> {
        override fun State.reduce(msg: Msg): State =
            when (msg) {
                is Msg.LoginChanged ->
                    copy(
                        login = msg.newLogin,
                        password = password,
                        phoneNumber = phoneNumber,
                        passwordErrMessage = passwordErrMessage,
                        phoneErrMessage = phoneErrMessage
                    )

                is Msg.PasswordValidated ->
                    copy(
                        login = login,
                        password = msg.newPassword,
                        phoneNumber = phoneNumber,
                        passwordErrMessage = msg.validRes,
                        phoneErrMessage = phoneErrMessage,
                    )

                is Msg.PhoneNumberValidated ->
                    copy(
                        login = login,
                        password = password,
                        phoneNumber = msg.newPhNum,
                        passwordErrMessage = passwordErrMessage,
                        phoneErrMessage = msg.validRes,
                    )
            }
    }
}