package com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.usecase

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRepository
import javax.inject.Inject

class GetCartUseCase @Inject constructor(
    private val repository: CartRepository
) {
    suspend operator fun invoke() = repository.getCart()
}
