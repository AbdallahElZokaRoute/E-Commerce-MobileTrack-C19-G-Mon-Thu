package com.route.ecommerce.c19_mobile_track.GMonThu.data.products.dataSource.api

import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model.ProductsListResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.model.SingleProductResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ProductsService {
    @GET("products")
    suspend fun fetchProducts(@Query("category[in]") categoryId: String?): Response<ProductsListResponse>

    @GET("products/{id}")
    suspend fun fetchProductById(@Path("id") categoryId: String?): Response<SingleProductResponse>
}
