package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource

import com.route.ecommerce.c19_mobile_track.GMonThu.data.api.safeAPICall
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource.api.AuthService
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.mapper.toDomain
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.LoginRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.RegisterRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.utils.mapResource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.entitiy.AuthResultEntity
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthRemoteDataSource
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AuthRemoteDataSourceImpl @Inject constructor(
    private val service: AuthService
) : AuthRemoteDataSource {
    override suspend fun signIn(
        email: String,
        password: String
    ): Flow<ApiResult<AuthResultEntity>> =
        safeAPICall {
            service.signIn(LoginRequest(email = email, password = password))
        }.mapResource { response ->
            response?.toDomain() ?: AuthResultEntity()
        }

    override suspend fun signUp(
        name: String,
        email: String,
        password: String,
        rePassword: String,
        phone: String
    ): Flow<ApiResult<AuthResultEntity>> =
        safeAPICall {
            service.signUp(
                RegisterRequest(
                    name = name,
                    email = email,
                    password = password,
                    rePassword = rePassword,
                    phone = phone
                )
            )
        }.mapResource { response ->
            response?.toDomain() ?: AuthResultEntity()
        }
}
