package com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.dataSource

import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.dataSource.api.CategoriesService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.api.safeAPICall
import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.mapper.toDomain
import com.route.ecommerce.c19_mobile_track.GMonThu.data.utils.mapResource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRemoteDataSourceImpl @Inject constructor(
    private val categoriesService: CategoriesService
) : CategoriesRemoteDataSource {
    override suspend fun fetchCategories(): Flow<ApiResult<List<CategoryEntity>>> = safeAPICall {
        categoriesService.fetchCategories()
    }.mapResource { response ->
        response?.data?.map { it.toDomain() } ?: listOf()
    }
}
