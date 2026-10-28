package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.register

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class RegistrationContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: SharedFlow<Event>
        fun invokeActions(action: Action)
    }

    sealed interface Action {
        data class UpdateName(val name: String) : Action
        data class UpdateEmail(val email: String) : Action
        data class UpdatePassword(val password: String) : Action
        data class UpdateRePassword(val rePassword: String) : Action
        data class UpdatePhone(val phone: String) : Action
        data object SubmitRegister : Action
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class Error(val message: String?) : State
        data class Success(val authResult: AuthResultEntity) : State
    }

    sealed interface Event {
        data class ShowToast(val message: String?) : Event
        data object NavigateToHome : Event
    }
}
