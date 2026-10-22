package com.route.ecommerce.c19_mobile_track.GMonThu.di.categories

import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.dataSource.api.CategoriesService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.dataSource.CategoriesRemoteDataSourceImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.data.categories.repository.CategoriesRepositoryImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.categories.repository.CategoriesRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CategoriesModule {

    @Provides
    @Singleton
    fun provideCategoriesRepository(remoteDataSourceSource: CategoriesRemoteDataSource): CategoriesRepository {
        return CategoriesRepositoryImpl(remoteDataSourceSource)
    }

    @Provides
    @Singleton
    fun provideRemoteCategoriesDataSource(categoriesService: CategoriesService): CategoriesRemoteDataSource {
        return CategoriesRemoteDataSourceImpl(categoriesService)
    }

    @Provides
    @Singleton
    fun getCategoriesService(retrofit: Retrofit): CategoriesService {
        return retrofit.create(CategoriesService::class.java)
    }
}
