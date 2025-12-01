package com.example.myapplication.presentation.ui.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemUserBinding
import com.example.myapplication.domain.model.GetUsers

class UsersPagingAdapter :
    PagingDataAdapter<GetUsers, UsersPagingAdapter.UserViewHolder>(Diff) {

    object Diff : DiffUtil.ItemCallback<GetUsers>() {
        override fun areItemsTheSame(oldItem: GetUsers, newItem: GetUsers): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: GetUsers, newItem: GetUsers): Boolean =
            oldItem == newItem
    }

    inner class UserViewHolder(
        private val binding: ItemUserBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: GetUsers?) = with(binding) {
            tvName.text = item?.firstName
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val binding = ItemUserBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}