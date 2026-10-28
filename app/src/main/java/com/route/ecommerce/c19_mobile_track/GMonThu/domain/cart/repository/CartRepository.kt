package com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.ClearCartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface CartRepository {
    suspend fun addProduct(productId: String): Flow<ApiResult<CartEntity?>>
    suspend fun getCart(): Flow<ApiResult<CartEntity?>>
    suspend fun updateProductCount(productId: String, count: String): Flow<ApiResult<CartEntity?>>
    suspend fun removeProduct(productId: String): Flow<ApiResult<CartEntity?>>
    suspend fun clearCart(): Flow<ApiResult<ClearCartEntity?>>
}

interface CartRemoteDataSource {
    suspend fun addProduct(productId: String): Flow<ApiResult<CartEntity?>>
    suspend fun getCart(): Flow<ApiResult<CartEntity?>>
    suspend fun updateProductCount(productId: String, count: String): Flow<ApiResult<CartEntity?>>
    suspend fun removeProduct(productId: String): Flow<ApiResult<CartEntity?>>
    suspend fun clearCart(): Flow<ApiResult<ClearCartEntity?>>
}
