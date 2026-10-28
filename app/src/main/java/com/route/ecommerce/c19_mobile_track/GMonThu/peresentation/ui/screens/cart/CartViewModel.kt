package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase.ClearCartUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase.GetCartUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase.RemoveCartItemUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase.UpdateCartProductQuantityUseCase
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
class CartViewModel @Inject constructor(
    private val getCartUseCase: GetCartUseCase,
    private val updateCartProductQuantityUseCase: UpdateCartProductQuantityUseCase,
    private val removeCartItemUseCase: RemoveCartItemUseCase,
    private val clearCartUseCase: ClearCartUseCase
) : ViewModel(), CartContract.ViewModel {
    private val _states = MutableStateFlow<CartContract.State>(CartContract.State.Idle)
    override val states: StateFlow<CartContract.State>
        get() = _states.asStateFlow()
    private val _events = MutableSharedFlow<CartContract.Event>()
    override val events: SharedFlow<CartContract.Event>
        get() = _events.asSharedFlow()

    override fun invokeActions(action: CartContract.Action) {
        when (action) {
            CartContract.Action.LoadCart -> loadCart()
            is CartContract.Action.UpdateQuantity -> updateQuantity(action.productId, action.count)
            is CartContract.Action.RemoveItem -> removeItem(action.productId)
            CartContract.Action.ClearCart -> clearCart()
        }
    }

    private fun loadCart() {
        viewModelScope.launch {
            _states.value = CartContract.State.Loading
            getCartUseCase().collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        _states.value = CartContract.State.Error(result.exception.errorMessage)
                        showErrorToast(result.exception)
                    }

                    is ApiResult.Success -> {
                        _states.value = CartContract.State.Success(result.data ?: CartEntity())
                    }
                }
            }
        }
    }

    private fun updateQuantity(productId: String, count: Int) {
        _states.value = CartContract.State.Loading
        if (count < 1) {
            removeItem(productId)
            return
        }
        viewModelScope.launch {
            updateCartProductQuantityUseCase(productId, count.toString()).collect { result ->
                when (result) {
                    is ApiResult.Error -> showErrorToast(result.exception)
                    is ApiResult.Success -> {
                        _states.value = CartContract.State.Success(result.data ?: CartEntity())
                    }
                }
            }
        }
    }

    private fun removeItem(productId: String) {
        _states.value = CartContract.State.Loading
        viewModelScope.launch {
            removeCartItemUseCase(productId).collect { result ->
                when (result) {
                    is ApiResult.Error -> showErrorToast(result.exception)
                    is ApiResult.Success -> {
                        _states.value = CartContract.State.Success(result.data ?: CartEntity())
                    }
                }
            }
        }
    }

    private fun clearCart() {
        viewModelScope.launch {
            _states.value = CartContract.State.Loading
            clearCartUseCase().collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        _states.value = CartContract.State.Error(result.exception.errorMessage)
                        showErrorToast(result.exception)
                    }

                    is ApiResult.Success -> {
                        _states.value = CartContract.State.Success(CartEntity())
                        _events.emit(CartContract.Event.ShowToast(result.data?.message))
                    }
                }
            }
        }
    }

    private suspend fun showErrorToast(exception: AppExceptions) {
        when (exception) {
            is AppExceptions.ClientException -> {
                _events.emit(CartContract.Event.ShowToast(exception.message))
            }

            AppExceptions.NoDataException -> {}
            is AppExceptions.ServerException -> {
                _events.emit(CartContract.Event.ShowToast(exception.message))
            }

            is AppExceptions.UnknownException -> {}
        }
    }
}
