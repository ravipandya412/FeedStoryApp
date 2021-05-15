package com.example.storyapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.storyapp.R
import com.example.storyapp.adapters.FeedAdapter
import com.example.storyapp.api.model.Feed
import com.example.storyapp.api.model.FeedsAPIResponse
import com.example.storyapp.api.utils.ErrorResponse
import com.example.storyapp.api.utils.Status
import com.example.storyapp.api.viewmodel.FeedViewModel
import com.example.storyapp.callbacks.FeedClickCallback
import com.example.storyapp.databinding.ActivityHomeBinding
import com.example.storyapp.extras.Keys
import com.example.storyapp.fragments.FeedDetailFragment


class HomeActivity : AppCompatActivity(), FeedClickCallback {

    private lateinit var binding: ActivityHomeBinding
    private var feedStoryList: List<Feed> = ArrayList()
    private val feedViewModel: FeedViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        viewInflation()
        setUpViewModel()
    }

    private fun viewInflation() {
        //Swipe to Refresh
        binding.swipeToRefresh.setOnRefreshListener {
            setUpViewModel()
            binding.swipeToRefresh.isRefreshing = false
        }
    }

    private fun setUpViewModel() {
        //Make an Api call to fetch Feed
        feedViewModel.getFeedAsync()
        //Observe Response
        feedViewModel.feedLiveEvent.observe(this, {
            it.let { resource ->
                if (resource != null) {
                    when (resource.status) {
                        Status.SUCCESS -> {
                            if (resource.data is FeedsAPIResponse) {
                                feedStoryList = resource.data.feedsData.childrenList
                                loadChildStoryList(feedStoryList)
                            }
                        }
                        Status.ERROR -> {
                            if (it != null) {
                                if (it.data != null && it.data is ErrorResponse) {
                                    val errorResponse = it.data as ErrorResponse?
                                    Toast.makeText(
                                        this,
                                        errorResponse!!.getErrorMessage(),
                                        Toast.LENGTH_LONG
                                    ).show()
                                } else {
                                    if (it.message == getString(R.string.no_network_available)) {
                                        Toast.makeText(
                                            this,
                                            getString(R.string.no_network_available),
                                            Toast.LENGTH_SHORT
                                        ).show()

                                    }
                                }
                            }
                        }
                        Status.LOADING -> {
                        }
                    }
                }
            }
        })
    }

    private fun loadChildStoryList(feedStoryList: List<Feed>) {
        //Bind List of Feed in Adapter
        if (feedStoryList.isNotEmpty()) {
            binding.rvChildFeed.layoutManager = GridLayoutManager(this, 1)
            binding.rvChildFeed.adapter = FeedAdapter(feedStoryList, this)
        }
    }

    //Navigate to Detail Screen.
    override fun openFeedDetail(feedObject: Feed?) {
        val fragment = FeedDetailFragment()
        fragment.feedObject = feedObject
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.home_screen_frame_layout, fragment)
        transaction.commit()
        transaction.addToBackStack(Keys.FEED_DETAILS_FRAGMENT)
    }

}