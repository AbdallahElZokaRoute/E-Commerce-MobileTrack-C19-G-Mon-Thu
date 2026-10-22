package com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.dataSource.api

import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.model.CategoriesResponse
import retrofit2.Response
import retrofit2.http.GET

interface CategoriesService {
    @GET("categories")
    suspend fun fetchCategories(): Response<CategoriesResponse>
}
