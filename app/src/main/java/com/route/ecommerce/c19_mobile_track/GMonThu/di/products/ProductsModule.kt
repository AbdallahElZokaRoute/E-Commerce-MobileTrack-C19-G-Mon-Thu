package com.route.ecommerce.c19_mobile_track.GMonThu.di.products

import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.dataSource.ProductsRemoteDataSourceImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.dataSource.api.ProductsService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.products.repository.ProductsRepositoryImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.products.repository.ProductsRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ProductsModule {
    @Provides
    @Singleton
    fun provideProductsRepository(remoteDataSource: ProductsRemoteDataSource): ProductsRepository {
        return ProductsRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideProductsRemoteDataSource(service: ProductsService): ProductsRemoteDataSource {
        return ProductsRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideProductsService(retrofit: Retrofit): ProductsService {
        return retrofit.create(ProductsService::class.java)
    }
}
