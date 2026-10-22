package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.productList

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.usecase.GetProductsUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.AppExceptions
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase
) : ViewModel() {
    private val _productsState = MutableStateFlow<List<ProductEntity>>(listOf())
    val productsState = _productsState.asStateFlow()

    private val _errorSharedFlow = MutableSharedFlow<String?>()
    val errorSharedFlow = _errorSharedFlow.asSharedFlow()

    fun getProducts(categoryId: String?) {
        viewModelScope.launch {
            getProductsUseCase(categoryId).collect {
                when (it) {
                    is ApiResult.Error -> {
                        when (it.exception) {
                            is AppExceptions.ClientException -> _errorSharedFlow.emit(it.exception.message)


                            AppExceptions.NoDataException -> {}
                            is AppExceptions.ServerException ->
                                _errorSharedFlow.emit(it.exception.message)

                            is AppExceptions.UnknownException -> {}
                        }
                    }

                    is ApiResult.Success -> {
                        _productsState.value = it.data ?: listOf()
                    }
                }
            }

        }
    }
}
