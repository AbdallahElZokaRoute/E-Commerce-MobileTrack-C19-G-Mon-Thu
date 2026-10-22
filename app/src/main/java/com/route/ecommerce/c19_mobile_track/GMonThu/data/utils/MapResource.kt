package com.route.ecommerce.c19_mobile_track.GMonThu.data.utils

import com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map


fun <T, K> Flow<ApiResult<T>>.mapResource(mapper: (T?) -> K): Flow<ApiResult<K>> {
    return map {
        when (it) {
            is ApiResult.Error -> ApiResult.Error(it.exception)
            is ApiResult.Success -> ApiResult.Success(mapper(it.data))
        }
    }
}