package com.route.ecommerce.c19_mobile_track.GMonThu.api

import android.util.Log
import com.route.ecommerce.c19_mobile_track.GMonThu.api.model.ErrorModel
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.AppExceptions
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.serialization.json.Json
import retrofit2.Response


fun <T> safeAPICall(apiCall: suspend () -> Response<T>): Flow<ApiResult<T>> = flow<ApiResult<T>> {
    val response = apiCall()
    if (response.isSuccessful) {
        if (response.body() != null) {
            emit(ApiResult.Success(response.body()))
        } else {
            emit(ApiResult.Error(AppExceptions.NoDataException))
        }
    } else if (response.code() in 500..599) {// server Error
        emit(ApiResult.Error(AppExceptions.ServerException("Server is Down !")))
    } else {
        val errorModel = parseErrorSafely(response.errorBody()?.string())
        emit(ApiResult.Error(AppExceptions.ClientException(errorModel?.errorMessage)))
    }
}.catch {
    emit(ApiResult.Error(AppExceptions.UnknownException(it.message)))
}

fun parseErrorSafely(jsonString: String?): ErrorModel? {
    Log.e("API Test7", "safeApiCall: $jsonString ")
    if (jsonString.isNullOrEmpty() || jsonString.isBlank()) return null
    return runCatching {
        Json { ignoreUnknownKeys = true }.decodeFromString<ErrorModel>(jsonString)
    }.getOrNull()
}
