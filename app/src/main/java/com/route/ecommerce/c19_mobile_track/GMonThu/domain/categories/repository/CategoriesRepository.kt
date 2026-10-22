package com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {
    suspend fun getCategories(): Flow<ApiResult<List<CategoryEntity>>>
}

interface CategoriesRemoteDataSource {
    suspend fun fetchCategories(): Flow<ApiResult<List<CategoryEntity>>>
}
