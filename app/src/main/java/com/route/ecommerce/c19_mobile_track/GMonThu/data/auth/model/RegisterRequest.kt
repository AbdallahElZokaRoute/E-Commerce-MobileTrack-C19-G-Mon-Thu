package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RegisterRequest(
    @SerialName("name")
    val name: String,

    @SerialName("email")
    val email: String,

    @SerialName("password")
    val password: String,

    @SerialName("rePassword")
    val rePassword: String,

    @SerialName("phone")
    val phone: String
)
