package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.dataSource.api

import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.AuthResponse
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.LoginRequest
import com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.model.RegisterRequest
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("auth/signin")
    suspend fun signIn(@Body request: LoginRequest): Response<AuthResponse>

    @POST("auth/signup")
    suspend fun signUp(@Body request: RegisterRequest): Response<AuthResponse>
}
