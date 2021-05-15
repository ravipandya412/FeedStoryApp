package com.example.storyapp.viewholders

import android.text.TextUtils
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.storyapp.R
import com.example.storyapp.api.model.Feed
import com.example.storyapp.callbacks.FeedClickCallback
import com.example.storyapp.databinding.ChildFeedItemBinding
import com.example.storyapp.extras.Utilities
import com.squareup.picasso.Picasso

class FeedItemViewHolder(
    private val binding: ChildFeedItemBinding,
    private val feedClickCallback: FeedClickCallback,
) :
    RecyclerView.ViewHolder(binding.root), View.OnClickListener {
    private var feed: Feed? = null

    init {
        binding.cvStory.setOnClickListener(this)
    }

    fun bindFeedStoryData(feed: Feed) {
        this.feed = feed
        //Title
        binding.tvFeedTitle.text = feed.feedData.title
        val upVoteValue = Utilities.getNumbersInK(feed.feedData.upvotes)
        val commentValue = Utilities.getNumbersInK(feed.feedData.numbOfComments) + " " +
                binding.root.context.getString(R.string.comments)
        //No of UpVote
        binding.tvFeedUpVotesNum.text = upVoteValue
        //No of Comments
        binding.tvFeedUpCommentsNum.text = commentValue

        //FeedImage
        if (!TextUtils.isEmpty(feed.feedData.imageUrl) && feed.feedData.imageUrl.contains("http")) {
            binding.ivFeed.visibility = View.VISIBLE
            Picasso.get().load(feed.feedData.imageUrl).into(binding.ivFeed)
        } else {
            binding.ivFeed.visibility = View.GONE
        }
    }

    override fun onClick(view: View) {
        if (view.id == binding.cvStory.id) {
            feedClickCallback.openFeedDetail(feed)
        }
    }
}