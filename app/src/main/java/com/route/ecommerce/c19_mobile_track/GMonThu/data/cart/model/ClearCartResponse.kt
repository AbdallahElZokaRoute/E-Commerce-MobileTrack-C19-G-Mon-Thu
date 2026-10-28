package com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ClearCartResponse(
    @SerialName("message")
    val message: String? = null
)
