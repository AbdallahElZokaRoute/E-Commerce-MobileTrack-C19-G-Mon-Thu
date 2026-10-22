package com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.usecase

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRepository
import javax.inject.Inject

class GetCategoriesUseCase @Inject constructor(
    private val repository: CategoriesRepository
) {
    suspend operator fun invoke() = repository.getCategories()
}
