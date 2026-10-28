package com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.usecase

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRepository
import javax.inject.Inject

class LoginUseCase @Inject constructor(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(email: String, password: String) =
        repository.login(email, password)
}
