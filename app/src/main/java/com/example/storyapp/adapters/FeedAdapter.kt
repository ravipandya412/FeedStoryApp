package com.example.storyapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.callbacks.FeedClickCallback
import com.example.storyapp.api.model.Feed
import com.example.storyapp.databinding.ChildFeedItemBinding
import com.example.storyapp.viewholders.FeedItemViewHolder

class FeedAdapter(
    private var feedFeedStory: List<Feed>,
    private val feedClickCallBack: FeedClickCallback,
) : RecyclerView.Adapter<FeedItemViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = FeedItemViewHolder(
        ChildFeedItemBinding.inflate((LayoutInflater.from(parent.context)), parent, false),
        feedClickCallBack
    )

    override fun onBindViewHolder(holder: FeedItemViewHolder, position: Int) {
        holder.bindFeedStoryData(feedFeedStory[position])
    }

    override fun getItemCount(): Int {
        return feedFeedStory.size
    }
}