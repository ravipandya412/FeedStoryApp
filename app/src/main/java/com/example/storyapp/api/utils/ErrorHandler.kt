package com.example.storyapp.api.utils

import com.google.gson.Gson
import okhttp3.ResponseBody
import org.json.JSONObject

object ErrorHandler {
    private val gSon = Gson()
    fun getDetailError(errorBody: ResponseBody?): ErrorResponse? {
        return errorBody?.let {
            val jsonObject = JSONObject(errorBody.string())
            gSon.fromJson(jsonObject.toString(), ErrorResponse::class.java)
        }
    }
}