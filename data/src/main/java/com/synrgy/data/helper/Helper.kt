package com.synrgy.data.helper

import com.google.gson.Gson
import com.synrgy.domain.Resource
import com.synrgy.domain.response.error.ErrorResponse
import com.synrgy.domain.response.error.ExceptionResponse
import retrofit2.Response

object Helper {
    fun <T> getResult(response: Response<T>, result: T?): Resource<T> {
        return if (response.isSuccessful && result != null) {
            Resource.Success(result)
        } else if (response.code() == 400 || response.code() == 415) {
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

    fun <T> getErrorResponse(response: String, errorClass: Class<T>): T {
        return Gson().fromJson(response, errorClass)
    }

    fun containsSpecialCharacter(sentence: String): Boolean {
        val specialCharacters = "!\"#\$%&'()*+,-./:;<=>?@[\\]^_`{|}~"
        for (ch in sentence) {
            if (specialCharacters.contains(ch)) {
                return true
            }
        }
        return false
    }

    fun containsAlphanumeric(sentence: String): Boolean {
        var containsLetter = false
        var containsNumber = false

        for (ch in sentence) {
            if (ch.isLetter()) {
                containsLetter = true
            }

            if (ch.isDigit()) {
                containsNumber = true
            }

            if (containsLetter && containsNumber) {
                break
            }
        }

        return containsLetter && containsNumber
    }

    fun containsUppercaseLetter(sentence: String): Boolean {
        for (ch in sentence) {
            if (ch.isUpperCase()) {
                return true
            }
        }
        return false
    }

    fun checkPasswordLength(password: String): Boolean {
        return password.length >= 8
    }
}