package com.example.myapplication.ui.chat

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemChatBinding
import com.bumptech.glide.Glide
import com.example.myapplication.data.network.ChatConversationDto

class ChatAdapter :
    ListAdapter<ChatConversationDto, ChatAdapter.ChatViewHolder>(DiffCallback) {

    object DiffCallback : DiffUtil.ItemCallback<ChatConversationDto>() {
        override fun areItemsTheSame(oldItem: ChatConversationDto, newItem: ChatConversationDto): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: ChatConversationDto, newItem: ChatConversationDto): Boolean =
            oldItem == newItem
    }

    inner class ChatViewHolder(
        private val binding: ItemChatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ChatConversationDto) = with(binding) {
            tvName.text = item.owner
            tvMessage.text = if (item.isTyping) "Typing..." else item.lastMessage
            tvTime.text = item.lastActive


            if (item.unreadMessages > 0) {
                badgeUnread.text = item.unreadMessages.toString()
                badgeUnread.visibility = View.VISIBLE
            } else {
                badgeUnread.visibility = View.GONE
            }


            if (!item.image.isNullOrBlank()) {
                Glide.with(imgAvatar)
                    .load(item.image)
                    .placeholder(R.drawable.circle)
                    .into(imgAvatar)
            } else {
                imgAvatar.setImageResource(R.drawable.user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemChatBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }
}
