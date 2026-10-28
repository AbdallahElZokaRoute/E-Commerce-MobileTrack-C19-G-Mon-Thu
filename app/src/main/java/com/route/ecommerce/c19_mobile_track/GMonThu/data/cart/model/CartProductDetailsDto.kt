package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CartProductDetailsDto(
    @SerialName("_id")
    val id: String? = null,

    @SerialName("title")
    val title: String? = null,

    @SerialName("quantity")
    val quantity: Int? = null,

    @SerialName("imageCover")
    val imageCover: String? = null,

    @SerialName("id")
    val productId: String? = null
)
