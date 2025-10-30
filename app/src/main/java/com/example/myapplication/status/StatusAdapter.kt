package com.example.myapplication.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemStatusBinding

class StatusAdapter() : ListAdapter<Status, StatusAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Status) {
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemStatusBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Status>() {
        override fun areItemsTheSame(oldItem: Status, newItem: Status) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Status, newItem: Status) = oldItem == newItem
    }
}