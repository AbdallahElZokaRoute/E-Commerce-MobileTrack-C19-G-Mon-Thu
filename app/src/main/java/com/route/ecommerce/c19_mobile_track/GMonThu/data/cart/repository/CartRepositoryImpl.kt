package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.ClearCartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRepository
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val remoteDataSource: CartRemoteDataSource
) : CartRepository {
    override suspend fun addProduct(productId: String): Flow<ApiResult<CartEntity?>> {
        return remoteDataSource.addProduct(productId)
    }

    override suspend fun getCart(): Flow<ApiResult<CartEntity?>> {
        return remoteDataSource.getCart()
    }

    override suspend fun updateProductCount(
        productId: String,
        count: String
    ): Flow<ApiResult<CartEntity?>> {
        return remoteDataSource.updateProductCount(productId, count)
    }

    override suspend fun removeProduct(productId: String): Flow<ApiResult<CartEntity?>> {
        return remoteDataSource.removeProduct(productId)
    }

    override suspend fun clearCart(): Flow<ApiResult<ClearCartEntity?>> {
        return remoteDataSource.clearCart()
    }
}
