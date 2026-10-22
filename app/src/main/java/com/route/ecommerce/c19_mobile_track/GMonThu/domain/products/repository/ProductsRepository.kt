package com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.SingleProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface ProductsRepository {
    suspend fun getProductsList(categoryId: String?): Flow<ApiResult<List<ProductEntity>>>
    suspend fun getProductByProductId(productId: String?): Flow<ApiResult<SingleProductEntity?>>
}

interface ProductsRemoteDataSource {
    suspend fun fetchProductsList(categoryId: String?): Flow<ApiResult<List<ProductEntity>>>
    suspend fun fetchProductByProductId(productId: String?): Flow<ApiResult<SingleProductEntity?>>
}
