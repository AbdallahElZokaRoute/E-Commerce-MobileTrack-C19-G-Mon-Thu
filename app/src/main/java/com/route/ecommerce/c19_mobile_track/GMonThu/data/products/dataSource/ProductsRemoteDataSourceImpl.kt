package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.dataSource

import com.route.ecommerce.c19_mobile_track.GMonThu.data.api.safeAPICall
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.dataSource.api.ProductsService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.mapper.toDomain
import com.route.ecommerce.c19_mobile_track.GMonThu.data.utils.mapResource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.ProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.entitiy.SingleProductEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductsRemoteDataSourceImpl @Inject constructor(
    private val service: ProductsService
) : ProductsRemoteDataSource {
    override suspend fun fetchProductsList(categoryId: String?): Flow<ApiResult<List<ProductEntity>>> =
        safeAPICall {
            service.fetchProducts(categoryId)
        }.mapResource {
            it?.data?.map { it.toDomain() } ?: listOf()
        }

    override suspend fun fetchProductByProductId(productId: String?): Flow<ApiResult<SingleProductEntity?>> =
        safeAPICall {
            service.fetchProductById(productId)
        }.mapResource {
            it?.singleProductDataItem?.toDomain()
        }

}