package com.example.myapplication.presentation.screen.feed.adapter

import androidx.core.view.isGone
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemPostBinding
import com.example.myapplication.domain.model.Post
import com.example.myapplication.presentation.utils.toFeedDateString

class PostViewHolder(
    private val binding: ItemPostBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: Post) = with(binding) {

        tvFullName.text = item.fullName
        tvDate.text = item.createdAtEpochMillis.toFeedDateString()
        tvDescription.text = item.description

        imgAvatar.load(item.avatar) {
            crossfade(true)
            transformations(CircleCropTransformation())
        }

        tvLikes.text = itemView.context.getString(R.string.likes, item.likesCount)

        val imageViews = listOf(img1, img2, img3)
        imageViews.forEach { it.isGone = true }

        item.images.take(3).forEachIndexed { index, img ->
            imageViews[index].isVisible = true
            imageViews[index].load(img)
        }
    }
}