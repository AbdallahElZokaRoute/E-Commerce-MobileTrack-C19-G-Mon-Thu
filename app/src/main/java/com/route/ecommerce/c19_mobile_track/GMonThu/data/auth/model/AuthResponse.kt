package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class AuthResponse(
    @SerialName("message")
    val message: String? = null,

    @SerialName("user")
    val user: AuthUserDto? = null,

    @SerialName("token")
    val token: String? = null
)
