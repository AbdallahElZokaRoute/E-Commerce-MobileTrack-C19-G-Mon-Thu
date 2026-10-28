package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

class ProductListContract {
    interface ViewModel {
        val states: StateFlow<State>
        val events: SharedFlow<Event>
        fun invokeActions(action: Action)
    }

    sealed interface Action {
        data class LoadProducts(val categoryId: String?) : Action
        data object ClickedOnCartIcon : Action
        data class ClickedOnProduct(val product: ProductEntity?) : Action
        data class AddedProductToCart(val product: ProductEntity?) : Action
        data class AddedProductToWishlist(val product: ProductEntity?) : Action
    }

    sealed interface State {
        data object Idle : State
        data object Loading : State
        data class Error(val message: String?) : State
        data class Success(val productsList: List<ProductEntity>) : State
    }

    sealed

    interface Event {
        data class ShowToast(val message: String?) : Event
        data object NavigateToCart : Event

    }
}
