package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthLocalDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRepository
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val remoteDataSource: AuthRemoteDataSource,
    private val localDataSource: AuthLocalDataSource
) : AuthRepository {
    override suspend fun login(
        email: String,
        password: String
    ): Flow<ApiResult<AuthResultEntity>> {
        return remoteDataSource.signIn(email, password).onEach { result ->
            if (result is ApiResult.Success) {
                result.data?.token?.takeIf { it.isNotBlank() }?.let { token ->
                    localDataSource.saveToken(token)
                }
            }
        }
    }

    override suspend fun register(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<ApiResult<AuthResultEntity>> {
        return remoteDataSource.signUp(name, email, password, rePassword, phone).onEach { result ->
            if (result is ApiResult.Success) {
                result.data?.token?.takeIf { it.isNotBlank() }?.let { token ->
                    localDataSource.saveToken(token)
                }
            }
        }
    }

    override suspend fun getToken(): String? {
        return localDataSource.getToken()
    }
}
