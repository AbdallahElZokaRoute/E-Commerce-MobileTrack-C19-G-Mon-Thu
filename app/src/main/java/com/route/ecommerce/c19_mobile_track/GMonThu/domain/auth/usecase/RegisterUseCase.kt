package com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.usecase

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRepository
import javax.inject.Inject

class RegisterUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ) = repository.register(name, email, password, rePassword, phone)
}
