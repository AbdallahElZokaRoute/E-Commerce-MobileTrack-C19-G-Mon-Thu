package com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    suspend fun login(email: String, password: String): Flow<ApiResult<AuthResultEntity>>
    suspend fun register(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<ApiResult<AuthResultEntity>>

    suspend fun getToken(): String?
}

interface AuthRemoteDataSource {
    suspend fun signIn(email: String, password: String): Flow<ApiResult<AuthResultEntity>>
    suspend fun signUp(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<ApiResult<AuthResultEntity>>
}

interface AuthLocalDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}
