package com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy

data class AuthResultEntity(
    val message: String? = null,
    val user: AuthUserEntity? = null,
    val token: String? = null
)
