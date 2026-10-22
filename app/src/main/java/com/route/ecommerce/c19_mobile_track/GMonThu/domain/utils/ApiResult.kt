package com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils

sealed interface ApiResult<T> {
    data class Success<T>(val data: T? = null) : ApiResult<T>
    data class Error<T>(val exception: AppExceptions) : ApiResult<T>

}
