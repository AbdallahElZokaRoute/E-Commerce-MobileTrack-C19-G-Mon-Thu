package com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.entity.CategoryEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRepository
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class CategoriesRepositoryImpl @Inject constructor(
    private val remoteDataSource: CategoriesRemoteDataSource
) : CategoriesRepository {
    override suspend fun getCategories(): Flow<ApiResult<List<CategoryEntity>>> {
        return remoteDataSource.fetchCategories()
    }

}
