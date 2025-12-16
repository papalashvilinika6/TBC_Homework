package com.example.myapplication.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.databinding.ItemFeedStoriesBinding
import com.example.myapplication.databinding.ItemMyDriverBinding
import com.example.myapplication.databinding.ItemPostBinding
import com.example.myapplication.domain.model.Driver
import com.example.myapplication.domain.model.Story
import com.example.myapplication.presentation.ui.feed.FeedItem

class FeedAdapter() : ListAdapter<FeedItem, RecyclerView.ViewHolder>(FeedDiff) {

    companion object {
        private const val VIEW_TYPE_STORIES = 0
        private const val VIEW_TYPE_MY_DRIVER = 1
        private const val VIEW_TYPE_POST = 2
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is FeedItem.StoriesRow -> VIEW_TYPE_STORIES
            is FeedItem.PostRow    -> VIEW_TYPE_POST
            is FeedItem.MyDriverRow -> VIEW_TYPE_MY_DRIVER
        }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_STORIES -> StoriesViewHolder(
                ItemFeedStoriesBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_MY_DRIVER -> MyDriverViewHolder( // 🔥 REQUIRED
                ItemMyDriverBinding.inflate(inflater, parent, false)
            )

            VIEW_TYPE_POST -> PostViewHolder(
                ItemPostBinding.inflate(inflater, parent, false)
            )

            else -> error("Unknown viewType: $viewType")
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FeedItem.StoriesRow -> (holder as StoriesViewHolder).bind(item.stories)
            is FeedItem.PostRow    -> {
                (holder as PostViewHolder).bind(item.post)
            }
            is FeedItem.MyDriverRow -> // 🔥 REQUIRED
                (holder as MyDriverViewHolder).bind(item.driver)
        }
    }

    class StoriesViewHolder(
        private val binding: ItemFeedStoriesBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        private val storiesAdapter = StoryAdapter()

        fun bind(stories: List<Story>) = with(binding) {
            if (rvStories.adapter == null) {
                rvStories.layoutManager = LinearLayoutManager(
                    root.context, LinearLayoutManager.HORIZONTAL, false
                )
                rvStories.adapter = storiesAdapter
            }
            storiesAdapter.submitList(stories)
        }
    }

    class MyDriverViewHolder(
        private val binding: ItemMyDriverBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Driver) {
            binding.ivDriver.load(item.favDriverImage) { // naxe es tavidan
                crossfade(true)
            }
        }
    }

    object FeedDiff : DiffUtil.ItemCallback<FeedItem>() {

        override fun areItemsTheSame(old: FeedItem, new: FeedItem): Boolean {
            return when {
                old is FeedItem.StoriesRow && new is FeedItem.StoriesRow -> true

                old is FeedItem.MyDriverRow && new is FeedItem.MyDriverRow ->
                    old.driver.id == new.driver.id // 🔥 IMPORTANT

                old is FeedItem.PostRow && new is FeedItem.PostRow ->
                    old.post.id == new.post.id

                else -> false
            }
        }

        override fun areContentsTheSame(old: FeedItem, new: FeedItem): Boolean {
            return old == new
        }
    }

}