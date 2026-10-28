package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartResponse(
    @SerialName("status")
    val status: String? = null,

    @SerialName("message")
    val message: String? = null,

    @SerialName("numOfCartItems")
    val numOfCartItems: Int? = null,

    @SerialName("cartId")
    val cartId: String? = null,

    @SerialName("data")
    val data: CartDataDto? = null
)
