package com.example.myapplication.presentation.ui.feed.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemFeedStoriesBinding
import com.example.myapplication.databinding.ItemPostBinding
import com.example.myapplication.domain.model.Story
import com.example.myapplication.presentation.ui.feed.FeedItem

class FeedAdapter() : ListAdapter<FeedItem, RecyclerView.ViewHolder>(FeedDiff) {

    companion object {
        private const val VIEW_TYPE_STORIES = 0
        private const val VIEW_TYPE_POST = 1
    }

    override fun getItemViewType(position: Int): Int =
        when (getItem(position)) {
            is FeedItem.StoriesRow -> VIEW_TYPE_STORIES
            is FeedItem.PostRow    -> VIEW_TYPE_POST
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_TYPE_STORIES -> {
                val binding = ItemFeedStoriesBinding.inflate(inflater, parent, false)
                StoriesViewHolder(binding)
            }
            else -> {
                val binding = ItemPostBinding.inflate(inflater, parent, false)
                PostViewHolder(binding)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = getItem(position)) {
            is FeedItem.StoriesRow -> (holder as StoriesViewHolder).bind(item.stories)
            is FeedItem.PostRow    -> {
                (holder as PostViewHolder).bind(item.post)
            }
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

    object FeedDiff : DiffUtil.ItemCallback<FeedItem>() {
        override fun areItemsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            when {
                oldItem is FeedItem.StoriesRow && newItem is FeedItem.StoriesRow -> true
                oldItem is FeedItem.PostRow && newItem is FeedItem.PostRow ->
                    oldItem.post.id == newItem.post.id
                else -> false
            }

        override fun areContentsTheSame(oldItem: FeedItem, newItem: FeedItem): Boolean =
            oldItem == newItem
    }
}