package com.example.storyapp.fragments;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import com.example.storyapp.R;
import com.example.storyapp.api.model.Feed;
import com.example.storyapp.databinding.FragmentFeedDetailBinding;
import com.example.storyapp.extras.Keys;
import com.example.storyapp.extras.Utilities;
import com.squareup.picasso.Picasso;
import org.jetbrains.annotations.NotNull;

public class FeedDetailFragment extends Fragment {

    FragmentFeedDetailBinding binding;
    Feed feedObject;
    String upVoteString, commentString;

    public Feed getFeedObject() {
        return feedObject;
    }

    public void setFeedObject(Feed feed) {
        this.feedObject = feed;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentFeedDetailBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NotNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewInflation(view);
    }

    private void viewInflation(View view) {
        binding.toolbar.setNavigationOnClickListener(view12 -> {
            FragmentManager fm = requireActivity().getSupportFragmentManager();
            fm.popBackStack(Keys.FEED_DETAILS_FRAGMENT, FragmentManager.POP_BACK_STACK_INCLUSIVE);
        });

        //Prevent click event of HomeActivity
        view.setOnTouchListener((view1, motionEvent) -> true);
        loadFeedDetails(getFeedObject());
    }

    private void loadFeedDetails(Feed feed) {
        //title
        binding.tvFeedDetailTitle.setText(feed.getFeedData().getTitle());
        upVoteString = Utilities.INSTANCE.getNumbersInK(feed.getFeedData().getUpvotes());
        commentString = Utilities.INSTANCE.getNumbersInK(feed.getFeedData().getNumbOfComments()) + " " +
                getString(R.string.comments);
        //No of UpVote
        binding.tvFeedDetailUpVotesNum.setText(upVoteString);
        //No of Comments
        binding.tvFeedDetailUpCommentsNum.setText(commentString);
        //Description
        binding.tvFeedDesc.setText(feed.getFeedData().getDescription());

        //FeedImage
        if (!TextUtils.isEmpty(feed.getFeedData().getImageUrl()) && feed.getFeedData().getImageUrl().contains("http")) {
            binding.ivDetailFeed.setVisibility(View.VISIBLE);
            Picasso.get().load(feed.getFeedData().getImageUrl()).into(binding.ivDetailFeed);
        } else {
            binding.ivDetailFeed.setVisibility(View.GONE);
        }
    }
}
