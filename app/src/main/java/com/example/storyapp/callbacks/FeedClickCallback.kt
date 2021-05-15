package com.example.storyapp.callbacks

import com.example.storyapp.api.model.Feed

interface FeedClickCallback {
    fun openFeedDetail(feedObject: Feed?)
}