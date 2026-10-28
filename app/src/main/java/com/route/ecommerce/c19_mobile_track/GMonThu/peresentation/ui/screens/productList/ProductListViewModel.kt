package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase.AddProductToCartUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.usecase.GetProductsUseCase
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
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    private val addProductToCartUseCase: AddProductToCartUseCase
) : ViewModel(), ProductListContract.ViewModel {
    private val _states =
        MutableStateFlow<ProductListContract.State>(value = ProductListContract.State.Idle)
    override val states: StateFlow<ProductListContract.State>
        get() = _states.asStateFlow()
    private val _events = MutableSharedFlow<ProductListContract.Event>()
    override val events: SharedFlow<ProductListContract.Event>
        get() = _events.asSharedFlow()

    override fun invokeActions(action: ProductListContract.Action) {
        when (action) {
            is ProductListContract.Action.AddedProductToCart -> {
                addProductToCart(action.product?.id)
            }

            is ProductListContract.Action.AddedProductToWishlist -> {}
            ProductListContract.Action.ClickedOnCartIcon -> {
                viewModelScope.launch {
                    _events.emit(ProductListContract.Event.NavigateToCart)
                }
            }

            is ProductListContract.Action.ClickedOnProduct -> {}
            is ProductListContract.Action.LoadProducts -> {
                getProducts(action.categoryId)
            }
        }
    }

    private fun addProductToCart(productId: String?) {
        if (productId.isNullOrBlank()) return
        viewModelScope.launch {
            addProductToCartUseCase(productId).collect { result ->
                when (result) {
                    is ApiResult.Error -> {
                        when (result.exception) {
                            is AppExceptions.ClientException -> {
                                _events.emit(
                                    ProductListContract.Event.ShowToast(result.exception.message)
                                )
                            }

                            AppExceptions.NoDataException -> {}
                            is AppExceptions.ServerException -> {
                                _events.emit(
                                    ProductListContract.Event.ShowToast(result.exception.message)
                                )
                            }

                            is AppExceptions.UnknownException -> {}
                        }
                    }

                    is ApiResult.Success -> {
                        _events.emit(
                            ProductListContract.Event.ShowToast(result.data?.message)
                        )
                    }
                }
            }
        }
    }

    private fun getProducts(categoryId: String?) {
        viewModelScope.launch {
            _states.value = ProductListContract.State.Loading
            getProductsUseCase(categoryId).collect {
                when (it) {
                    is ApiResult.Error -> {
                        when (it.exception) {
                            is AppExceptions.ClientException -> {
                                _events.emit(ProductListContract.Event.ShowToast(it.exception.message))
                            }


                            AppExceptions.NoDataException -> {}
                            is AppExceptions.ServerException -> _events.emit(
                                ProductListContract.Event.ShowToast(
                                    it.exception.message
                                )
                            )

                            is AppExceptions.UnknownException -> {}
                        }
                    }

                    is ApiResult.Success -> {
                        _states.value = ProductListContract.State.Success(it.data ?: listOf())
                    }
                }
            }

        }
    }
}
