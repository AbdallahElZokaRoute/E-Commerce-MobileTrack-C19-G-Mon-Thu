package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.cart

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class CartContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: SharedFlow<Event>
        fun invokeActions(action: Action)
    }

    sealed interface Action {
        data object LoadCart : Action
        data class UpdateQuantity(val productId: String, val count: Int) : Action
        data class RemoveItem(val productId: String) : Action
        data object ClearCart : Action
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class Error(val message: String?) : State
        data class Success(val cart: CartEntity) : State
    }

    sealed interface Event {
        data class ShowToast(val message: String?) : Event
    }
}
