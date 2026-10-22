package com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.usecase

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRepository
import javax.inject.Inject

class GetProductByIdUseCase @Inject constructor(
    private val repository: ProductsRepository
) {
    suspend operator fun invoke(productId: String?) = repository.getProductByProductId(productId)
}