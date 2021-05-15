package com.example.storyapp.api.interceptors

import okhttp3.Interceptor
import okhttp3.Response

object HeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        request =
            request.newBuilder()
                .build()

        return chain.proceed(request)
    }

}