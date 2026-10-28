package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.usecase.LoginUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.AppExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase
) : ViewModel(), LoginContract.ViewModel {
    private val _states =
        MutableStateFlow<LoginContract.State>(value = LoginContract.State.Idle)
    override val states: StateFlow<LoginContract.State>
        get() = _states.asStateFlow()

    private val _events = MutableSharedFlow<LoginContract.Event>()
    override val events: SharedFlow<LoginContract.Event>
        get() = _events.asSharedFlow()

    private var email: String = ""
    private var password: String = ""

    override fun invokeActions(action: LoginContract.Action) {
        when (action) {
            is LoginContract.Action.UpdateEmail -> email = action.email
            is LoginContract.Action.UpdatePassword -> password = action.password
            LoginContract.Action.SubmitLogin -> login()
            LoginContract.Action.ClickedOnRegisterText -> {
                viewModelScope.launch {
                    _events.emit(LoginContract.Event.NavigateToRegistration)
                }
            }
        }
    }

    private fun login() {
        viewModelScope.launch {
            _states.value = LoginContract.State.Loading
            loginUseCase(email, password).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        when (result.exception) {
                            is AppExceptions.ClientException -> {
                                _events.emit(
                                    LoginContract.Event.ShowToast(result.exception.message)
                                )
                                _states.value =
                                    LoginContract.State.Error(result.exception.message)
                            }

                            AppExceptions.NoDataException -> {
                                _states.value = LoginContract.State.Error(null)
                            }

                            is AppExceptions.ServerException -> {
                                _events.emit(
                                    LoginContract.Event.ShowToast(result.exception.message)
                                )
                                _states.value =
                                    LoginContract.State.Error(result.exception.message)
                            }

                            is AppExceptions.UnknownException -> {
                                _states.value =
                                    LoginContract.State.Error(result.exception.message)
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        val authResult = result.data
                        if (authResult != null) {
                            _states.value = LoginContract.State.Success(authResult)
                            _events.emit(LoginContract.Event.NavigateToHome)
                        } else {
                            _states.value = LoginContract.State.Error(null)
                        }
                    }
                }
            }
        }
    }
}
