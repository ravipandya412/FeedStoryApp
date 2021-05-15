package com.example.storyapp.api.model

import com.google.gson.annotations.SerializedName

data class FeedsAPIResponse(
    var kind: String,
    @SerializedName("data") var feedsData: FeedsData
)

class FeedsData(
    @SerializedName("children") var childrenList: List<Feed>
)

class Feed(
    @SerializedName("data") var feedData: FeedData
)

class FeedData(
    var title:String,
    @SerializedName("ups") var upvotes:Int,
    @SerializedName("selftext") var description:String,
    @SerializedName("num_comments") var numbOfComments:Int,
    @SerializedName("thumbnail") var imageUrl:String
)







