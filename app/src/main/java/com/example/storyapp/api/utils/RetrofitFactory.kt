package com.example.storyapp.api.utils

import com.example.storyapp.api.interceptors.HeaderInterceptor
import com.example.storyapp.extras.Endpoints
import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import okhttp3.CipherSuite
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    private val builder: Retrofit.Builder = Retrofit.Builder()
        .baseUrl("https://www.reddit.com/.json/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(CoroutineCallAdapterFactory())

    val httpClientWithHeader: OkHttpClient.Builder = getHttpClientDefaultConfig()
        .addInterceptor(HeaderInterceptor)

    private fun getHttpClientDefaultConfig(): OkHttpClient.Builder {
        val httpClient: OkHttpClient.Builder = OkHttpClient.Builder()
        httpClient.connectTimeout(30, TimeUnit.SECONDS)
        httpClient.readTimeout(30, TimeUnit.SECONDS)
        httpClient.writeTimeout(30, TimeUnit.SECONDS)

        supportMethodForAndroid7(httpClient)

        return httpClient
    }

    fun retrofit(httpClient: OkHttpClient.Builder): Retrofit = builder
        .client(httpClient.build())
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private fun supportMethodForAndroid7(httpClient: OkHttpClient.Builder) {
        if (Endpoints.GET_FEED.startsWith("https")) {
            val spec: ConnectionSpec = ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .cipherSuites(
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384,
                    CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256,
                    CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA
                )
                .build()
            httpClient.connectionSpecs(listOf(spec))
        }
    }
}