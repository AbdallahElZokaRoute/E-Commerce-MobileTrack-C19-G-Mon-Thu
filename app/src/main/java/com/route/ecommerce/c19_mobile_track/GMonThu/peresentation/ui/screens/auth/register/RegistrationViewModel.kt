package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.usecase.RegisterUseCase
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
class RegistrationViewModel @Inject constructor(
    private val registerUseCase: RegisterUseCase
) : ViewModel(), RegistrationContract.ViewModel {
    private val _states =
        MutableStateFlow<RegistrationContract.State>(value = RegistrationContract.State.Idle)
    override val states: StateFlow<RegistrationContract.State>
        get() = _states.asStateFlow()

    private val _events = MutableSharedFlow<RegistrationContract.Event>()
    override val events: SharedFlow<RegistrationContract.Event>
        get() = _events.asSharedFlow()

    private var name: String = ""
    private var email: String = ""
    private var password: String = ""
    private var rePassword: String = ""
    private var phone: String = ""

    override fun invokeActions(action: RegistrationContract.Action) {
        when (action) {
            is RegistrationContract.Action.UpdateName -> name = action.name
            is RegistrationContract.Action.UpdateEmail -> email = action.email
            is RegistrationContract.Action.UpdatePassword -> password = action.password
            is RegistrationContract.Action.UpdateRePassword -> rePassword = action.rePassword
            is RegistrationContract.Action.UpdatePhone -> phone = action.phone
            RegistrationContract.Action.SubmitRegister -> register()
        }
    }

    private fun register() {
        viewModelScope.launch {
            _states.value = RegistrationContract.State.Loading
            rePassword = password
            registerUseCase(name, email, password, rePassword, phone).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        when (result.exception) {
                            is AppExceptions.ClientException -> {
                                _events.emit(
                                    RegistrationContract.Event.ShowToast(result.exception.message)
                                )
                                _states.value =
                                    RegistrationContract.State.Error(result.exception.message)
                            }

                            AppExceptions.NoDataException -> {
                                _states.value = RegistrationContract.State.Error(null)
                            }

                            is AppExceptions.ServerException -> {
                                _events.emit(
                                    RegistrationContract.Event.ShowToast(result.exception.message)
                                )
                                _states.value =
                                    RegistrationContract.State.Error(result.exception.message)
                            }

                            is AppExceptions.UnknownException -> {
                                _states.value =
                                    RegistrationContract.State.Error(result.exception.message)
                            }
                        }
                    }

                    is ApiResult.Success -> {
                        val authResult = result.data
                        if (authResult != null) {
                            _states.value = RegistrationContract.State.Success(authResult)
                            _events.emit(RegistrationContract.Event.NavigateToHome)
                        } else {
                            _states.value = RegistrationContract.State.Error(null)
                        }
                    }
                }
            }
        }
    }
}
