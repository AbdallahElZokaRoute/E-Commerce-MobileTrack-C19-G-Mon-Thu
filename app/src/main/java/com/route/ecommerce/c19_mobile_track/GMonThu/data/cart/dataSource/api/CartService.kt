package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.dataSource.api

import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.AddToCartRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.CartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.ClearCartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.GetCartResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model.UpdateCartCountRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface CartService {
    @POST("cart")
    suspend fun addProduct(@Body request: AddToCartRequest): Response<CartResponse>

    @GET("cart")
    suspend fun getCart(): Response<GetCartResponse>

    @PUT("cart/{productId}")
    suspend fun updateProductCount(
        @Path("productId") productId: String,
        @Body request: UpdateCartCountRequest
    ): Response<GetCartResponse>

    @DELETE("cart/{productId}")
    suspend fun removeProduct(@Path("productId") productId: String): Response<GetCartResponse>

    @DELETE("cart")
    suspend fun clearCart(): Response<ClearCartResponse>
}
