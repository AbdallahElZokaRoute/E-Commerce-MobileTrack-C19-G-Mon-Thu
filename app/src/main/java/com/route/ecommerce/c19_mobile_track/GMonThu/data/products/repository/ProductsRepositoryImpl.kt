package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.SingleProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRepository
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource
) : ProductsRepository {
    override suspend fun getProductsList(categoryId: String?): Flow<ApiResult<List<ProductEntity>>> {
        return remoteDataSource.fetchProductsList(categoryId)
    }

    override suspend fun getProductByProductId(productId: String?): Flow<ApiResult<SingleProductEntity?>> {
        return remoteDataSource.fetchProductByProductId(productId)
    }

}