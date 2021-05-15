package com.example.storyapp.api.utils

import com.example.storyapp.api.`interface`.FeedInterface
import com.example.storyapp.api.utils.RetrofitFactory.httpClientWithHeader
import com.example.storyapp.api.utils.RetrofitFactory.retrofit

object ApiFactory {
    val feedService : FeedInterface = retrofit(httpClientWithHeader).create(FeedInterface::class.java)
}