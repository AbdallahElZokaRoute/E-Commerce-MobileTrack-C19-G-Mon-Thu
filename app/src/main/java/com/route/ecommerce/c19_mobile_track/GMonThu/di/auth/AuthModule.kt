package com.route.ecommerce.c19_mobile_track.GMonThu.di.auth

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource.AuthLocalDataSourceImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource.AuthRemoteDataSourceImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource.api.AuthService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.repository.AuthRepositoryImpl
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthLocalDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(
    name = "auth_preferences"
)

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideAuthDataStore(
        @ApplicationContext context: Context
    ): DataStore<Preferences> {
        return context.authDataStore
    }

    @Provides
    @Singleton
    fun provideAuthService(retrofit: Retrofit): AuthService {
        return retrofit.create(AuthService::class.java)
    }

    @Provides
    @Singleton
    fun provideAuthRemoteDataSource(service: AuthService): AuthRemoteDataSource {
        return AuthRemoteDataSourceImpl(service)
    }

    @Provides
    @Singleton
    fun provideAuthLocalDataSource(
        dataStore: DataStore<Preferences>
    ): AuthLocalDataSource {
        return AuthLocalDataSourceImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        remoteDataSource: AuthRemoteDataSource,
        localDataSource: AuthLocalDataSource
    ): AuthRepository {
        return AuthRepositoryImpl(remoteDataSource, localDataSource)
    }
}
