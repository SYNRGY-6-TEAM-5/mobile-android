package com.synrgy.data.helper

import com.google.gson.Gson
import com.synrgy.domain.Resource
import com.synrgy.domain.response.ErrorResponse
import com.synrgy.domain.response.ExceptionResponse
import retrofit2.Response

object Helper {
    fun <T> getResult(response: Response<T>, result: T?): Resource<T> {
        return if (response.isSuccessful && result != null) {
            Resource.Success(result)
        } else if (response.code() == 400) {
            Resource.ErrorRes(getErrorResponse(
                response.errorBody()!!.string(),
                ErrorResponse::class.java
            ))
        } else {
            Resource.ExceptionRes(getErrorResponse(
                response.errorBody()!!.string(),
                ExceptionResponse::class.java
            ))
        }
    }

    private fun <T> getErrorResponse(response: String, errorClass: Class<T>): T {
        return Gson().fromJson(response, errorClass)
    }
}