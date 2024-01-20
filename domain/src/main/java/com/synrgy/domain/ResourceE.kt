package com.synrgy.domain

import com.synrgy.domain.response.error.ErrorResponse
import com.synrgy.domain.response.error.ExceptionResponse

sealed class Resource<T>(
    val data: T?,
    val exceptionRes: ExceptionResponse?,
    val errorRes: ErrorResponse?
) {
    class Success<T>(data: T) : Resource<T>(
        data,
        null,
        null
    )
    class ExceptionRes<T>(exceptionResponse: ExceptionResponse?) : Resource<T>(
        null,
        exceptionResponse,
        null
    )
    class ErrorRes<T>(errorResponse: ErrorResponse?) : Resource<T>(
        null,
        null,
        errorResponse
    )
}