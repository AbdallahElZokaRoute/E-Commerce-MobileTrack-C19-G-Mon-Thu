package com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.usecase.GetCategoriesUseCase
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.AppExceptions
import com.route.ecommerce.c19_mobile_track.GMonThu.peresentation.tutorial.product.Product
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getCategoriesUseCase: GetCategoriesUseCase
) : ViewModel() {
    val productsState = MutableStateFlow<List<Product>>(listOf())
    val categoriesState = MutableStateFlow<List<CategoryEntity>>(listOf())

    val errorSharedFlow = MutableSharedFlow<String>()
    fun getCategories() {
        viewModelScope.launch {
            val response = getCategoriesUseCase()
            response.collect {
                when (it) {
                    is ApiResult.Error -> {
                        when (it.exception) {
                            is AppExceptions.ClientException -> {
                                errorSharedFlow.emit(it.exception.errorMessage ?: "")
                            }

                            AppExceptions.NoDataException -> {}
                            is AppExceptions.ServerException -> {
                                errorSharedFlow.emit(it.exception.errorMessage ?: "")
                            }

                            is AppExceptions.UnknownException -> {}
                        }
                    }

                    is ApiResult.Success -> {
                        val categories = it.data
                        categoriesState.value = categories ?: listOf()
                    }
                }
            }
        }
    }

    fun getProductsFromServer() {
        viewModelScope.launch {
            try {
                delay(3_000)
                productsState.value = listOf(Product(id = 2))
                throw Exception("No Internet Connection ")
            } catch (e: Exception) {
                errorSharedFlow.emit(" Exception : ${e.message}")
            }
        }
    }
    // StateFlow <->   .getValue and setValue
    // SharedFlow <->   One-time Events
    // Flow       <->   Between Layers
    //               safeApiCall
    //             flow {  }

}


