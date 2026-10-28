package com.route.ecommerce.c19_mobile_track.GMonThu.data.auth.interceptor

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.auth.repository.AuthLocalDataSource
import kotlinx.coroutines.runBlocking
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthInterceptor @Inject constructor(
    private val localDataSource: AuthLocalDataSource
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = runBlocking { localDataSource.getToken() }
        val requestBuilder = chain.request().newBuilder()
        if (!token.isNullOrBlank()) {
            requestBuilder.header("token", token)
        }
        return chain.proceed(requestBuilder.build())
    }
}
