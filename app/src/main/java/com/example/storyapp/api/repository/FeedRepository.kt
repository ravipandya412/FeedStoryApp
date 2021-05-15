package com.example.storyapp.api.repository

import com.example.storyapp.api.utils.ApiFactory

object FeedRepository {
    fun getFeedAsync() = ApiFactory.feedService.getFeedAsync()
}