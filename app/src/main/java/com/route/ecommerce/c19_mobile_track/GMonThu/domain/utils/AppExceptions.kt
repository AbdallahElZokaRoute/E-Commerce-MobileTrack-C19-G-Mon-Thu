package com.route.ecommerce.c19_mobile_track.GMonThu.domain.utils

sealed class AppExceptions(val errorMessage: String?) {
    data object NoDataException : AppExceptions("No Data Found!")
    data class ServerException(val message: String?) : AppExceptions(message)
    data class ClientException(val message: String?) : AppExceptions(message)
    data class UnknownException(val message: String?) : AppExceptions(message)
}