package com.example.spoonacularchatbot.core.data.remote.rxerrorhandling

import com.squareup.moshi.Json

data class ApiError(
    @Json(name = KEY_STATUS) val status: String?,
    @Json(name = KEY_CODE) val code: Int?,
    @Json(name = KEY_MESSAGE) val message: String?,
    @Json(name = KEY_DATA) val data: String?) {
    companion object {
        private const val KEY_CODE = "code"
        private const val KEY_STATUS = "status"
        private const val KEY_ALTERNATIVE_STATUS = "ReviewActionStatus"
        private const val KEY_ALTERNATIVE_CODE = "Code"
        private const val KEY_MESSAGE = "message"
        private const val KEY_ALTERNATIVE_MESSAGE = "Message"
        private const val KEY_DATA = "data"
        private const val KEY_ALTERNATIVE_DATA = "Data"
    }
}