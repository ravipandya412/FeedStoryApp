package com.example.storyapp.api.utils

import com.google.gson.annotations.SerializedName

data class ErrorResponse(
    @SerializedName("jsonapi") val jsonApi: JsonApi,
    val errors: Error
) {
    fun getErrorMessage(): String {
        return errors.message
    }

    override fun toString(): String {
        return "Error : $errors"
    }

}

data class JsonApi(
    val version: String
)

data class Error(
    val source: Source,
    val code: String, @SerializedName("http_code")
    val httpCode: Int,
    var message: String,
    val details: Any?
)

data class Source(
    val method: String,
    @SerializedName("http_request") val httpRequest: String
)