package com.route.ecommerce.c19_mobile_track.GMonThu.data.api.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ErrorModel(
    @SerialName("message")
    val errorMessage: String? = null
)
