package com.route.ecommerce.c19_mobile_track.GMonThu.di.cart

import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.dataSource.CartRemoteDataSourceImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.dataSource.api.CartService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.cart.repository.CartRepositoryImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.cart.repository.CartRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object CartModule {
    @Provides
    @Singleton
    fun provideCartRepository(remoteDataSource: CartRemoteDataSource): CartRepository {
        return CartRepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideCartRemoteDataSource(service: CartService): CartRemoteDataSource {
        return CartRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideCartService(retrofit: Retrofit): CartService {
        return retrofit.create(CartService::class.java)
    }
}
