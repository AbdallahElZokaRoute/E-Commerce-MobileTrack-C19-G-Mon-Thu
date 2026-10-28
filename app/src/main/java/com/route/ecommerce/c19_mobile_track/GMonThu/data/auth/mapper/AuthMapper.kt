package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.mapper

import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.AuthResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.AuthUserDto
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthUserEntity

fun AuthUserDto.toDomain(): AuthUserEntity =
    AuthUserEntity(
        name = name,
        email = email,
        role = role
    )

fun AuthResponse.toDomain(): AuthResultEntity =
    AuthResultEntity(
        message = message,
        user = user?.toDomain(),
        token = token
    )
