package com.example.spoonacularchatbot.core.data.remote.rxerrorhandling

import com.squareup.moshi.Moshi
import retrofit2.Response
import java.io.IOException

object NetworkException {
    fun httpError(response: Response<Any>?): SpoonacularException {
        var message: String? = null
        var responseBody: String? = null
        var statusCode = 0
        var errorCode = 0
        response?.let { statusCode = it.code() }
        response?.let {
            responseBody = it.errorBody()?.string()
            try {
                val apiError = Moshi.Builder().build().adapter(ApiError::class.java)
                    .fromJson(responseBody ?: "")

                apiError?.let { it ->
                    message = it.message
                    responseBody = it.data
                    it.code?.let { it -> errorCode = it }
                }
            } catch (exception: Exception) {
            }
        }

        var kind = SpoonacularException.Kind.HTTP
        when (statusCode) {
            500, 502 -> kind =
                SpoonacularException.Kind.SERVER_DOWN
            408 -> kind =
                SpoonacularException.Kind.TIME_OUT
            401 -> kind =
                SpoonacularException.Kind.UNAUTHORIZED
            304 -> kind =
                SpoonacularException.Kind.NOT_MODIFIED
        }

        return SpoonacularException(
            kind,
            message?.let { message }
                ?: kotlin.run { "" })
            .setErrorCode(errorCode)
            .setStatusCode(statusCode)
            .setData(responseBody)
    }

    fun networkError(exception: IOException): SpoonacularException {
        return SpoonacularException(
            SpoonacularException.Kind.NETWORK,
            exception
        )
    }

    fun unexpectedError(exception: Throwable): SpoonacularException {
        return SpoonacularException(
            SpoonacularException.Kind.UNEXPECTED,
            exception
        )
    }


}