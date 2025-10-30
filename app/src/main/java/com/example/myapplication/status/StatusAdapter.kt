package com.example.myapplication.status

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemStatusBinding

class StatusAdapter(
    private val onStatusClick: (Status) -> Unit
) : ListAdapter<Status, StatusAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(private val binding: ItemStatusBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(status: Status) {
            binding.tvStatusId.text = status.title

            val context = binding.root.context
            if (status.isSelected) {
                binding.tvStatusId.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.etext_selected
                )
            }else {
                binding.tvStatusId.background = ContextCompat.getDrawable(
                    context,
                    R.drawable.etext_shape
                )
            }

            binding.apply {
                root.setOnClickListener { onStatusClick(status) }
                tvStatusId.setOnClickListener { onStatusClick(status) }
            }
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