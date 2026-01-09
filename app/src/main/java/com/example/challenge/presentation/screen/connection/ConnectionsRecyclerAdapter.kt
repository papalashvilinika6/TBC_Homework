package com.example.challenge.presentation.screen.connection

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.databinding.ItemConnectionLayoutBinding
import com.example.challenge.presentation.extension.loadImage

class ConnectionsRecyclerAdapter :
    ListAdapter<Connection, ConnectionsRecyclerAdapter.ConnectionsViewHolder>(ConnectionsDiffUtil()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ConnectionsViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemConnectionLayoutBinding.inflate(inflater, parent, false)
        return ConnectionsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ConnectionsViewHolder(
        private val binding: ItemConnectionLayoutBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(model: Connection) {
            binding.imvProfile.loadImage(model.avatar)
            binding.tvFullName.text = model.fullName
        }
    }

    class ConnectionsDiffUtil : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }
    }
}