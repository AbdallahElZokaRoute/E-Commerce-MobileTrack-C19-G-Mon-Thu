package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.dataSource

import com.route.ecommerce.c19_mobile_track.GMonThu.data.api.safeAPICall
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.dataSource.api.CartService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.mapper.toDomain
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.AddToCartRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.UpdateCartCountRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.utils.mapResource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.CartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.entitiy.ClearCartEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CartRemoteDataSourceImpl @Inject constructor(
    private val service: CartService
) : CartRemoteDataSource {
    override suspend fun addProduct(productId: String): Flow<ApiResult<CartEntity?>> =
        safeAPICall {
            service.addProduct(AddToCartRequest(productId))
        }.mapResource {
            it?.toDomain()
        }

    override suspend fun getCart(): Flow<ApiResult<CartEntity?>> =
        safeAPICall {
            service.getCart()
        }.mapResource {
            it?.toDomain()
        }

    override suspend fun updateProductCount(
        productId: String,
        count: String
    ): Flow<ApiResult<CartEntity?>> =
        safeAPICall {
            service.updateProductCount(productId, UpdateCartCountRequest(count))
        }.mapResource {
            it?.toDomain()
        }

    override suspend fun removeProduct(productId: String): Flow<ApiResult<CartEntity?>> =
        safeAPICall {
            service.removeProduct(productId)
        }.mapResource {
            it?.toDomain()
        }

    override suspend fun clearCart(): Flow<ApiResult<ClearCartEntity?>> =
        safeAPICall {
            service.clearCart()
        }.mapResource {
            it?.toDomain()
        }
}
