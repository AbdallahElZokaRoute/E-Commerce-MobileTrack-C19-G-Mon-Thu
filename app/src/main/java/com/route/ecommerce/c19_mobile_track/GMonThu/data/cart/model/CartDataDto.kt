package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartDataDto(
    @SerialName("_id")
    val id: String? = null,

    @SerialName("cartOwner")
    val cartOwner: String? = null,

    @SerialName("products")
    val products: List<CartProductDto>? = null,

    @SerialName("createdAt")
    val createdAt: String? = null,

    @SerialName("updatedAt")
    val updatedAt: String? = null,

    @SerialName("__v")
    val v: Int? = null,

    @SerialName("totalCartPrice")
    val totalCartPrice: Int? = null
)
