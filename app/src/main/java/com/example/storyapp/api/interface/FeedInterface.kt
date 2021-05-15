package com.example.storyapp.api.`interface`

import com.example.storyapp.api.model.FeedsAPIResponse
import com.example.storyapp.extras.Endpoints
import kotlinx.coroutines.Deferred
import retrofit2.Response
import retrofit2.http.GET

interface FeedInterface {
    @GET(Endpoints.GET_FEED)
    fun getFeedAsync(): Deferred<Response<FeedsAPIResponse>>
}