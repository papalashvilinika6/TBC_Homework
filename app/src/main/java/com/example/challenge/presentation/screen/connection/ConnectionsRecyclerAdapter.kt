package com.example.challenge.presentation.screen.connection

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.challenge.databinding.ItemConnectionLayoutBinding
import com.example.challenge.presentation.extension.loadImage
import com.example.challenge.presentation.screen.connection.Connection
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

class ConnectionsRecyclerAdapter :
    ListAdapter<Connection, ConnectionsRecyclerAdapter.ConnectionsViewHolder>(ConnectionsDiffUtil()) {

    @Inject
    @ApplicationContext
    lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ConnectionsViewHolder(
        ItemConnectionLayoutBinding.inflate(
            LayoutInflater.from(context),
            parent,
            false
        )
    )

    override fun onBindViewHolder(holder: ConnectionsViewHolder, position: Int) {
        holder.bind()
    }

    inner class ConnectionsViewHolder(private val binding: ItemConnectionLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        private lateinit var model: Connection

        fun bind() {
            model = currentList[adapterPosition]
            binding.apply {
                imvProfile.loadImage(model.avatar)
                tvFullName.text = model.fullName
            }
        }
    }

    class ConnectionsDiffUtil : DiffUtil.ItemCallback<Connection>() {
        override fun areItemsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Connection, newItem: Connection): Boolean {
            return oldItem == newItem
        }
    }
}