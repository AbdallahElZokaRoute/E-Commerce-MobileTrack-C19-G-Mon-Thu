package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartProductDto(
    @SerialName("count")
    val count: Int? = null,

    @SerialName("_id")
    val id: String? = null,

    @SerialName("product")
    val product: String? = null,

    @SerialName("price")
    val price: Int? = null
)
