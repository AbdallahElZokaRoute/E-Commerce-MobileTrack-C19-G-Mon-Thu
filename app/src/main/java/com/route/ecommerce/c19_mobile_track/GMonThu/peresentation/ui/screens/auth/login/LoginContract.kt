package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.auth.login

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class LoginContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: SharedFlow<Event>
        fun invokeActions(action: Action)
    }

    sealed interface Action {
        data class UpdateEmail(val email: String) : Action
        data class UpdatePassword(val password: String) : Action
        data object SubmitLogin : Action

        data object ClickedOnRegisterText : Action
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
        data object NavigateToRegistration : Event
    }
}
