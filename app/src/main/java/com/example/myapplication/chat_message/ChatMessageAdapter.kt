package com.example.myapplication.chat_message

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemMessageBinding
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ChatAdapter : ListAdapter<ChatMessage, ChatAdapter.ChatViewHolder>(DiffCallback()) {

    inner class ChatViewHolder(val binding: ItemMessageBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(message: ChatMessage) = with(binding) {
            tvMessage.text = message.text
            tvTime.text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.date))

            setBubble(tvMessage, tvTime, message.isSentByMe)
        }

        private fun setBubble(tvMessage: TextView, tvTime: TextView, isSentByMe: Boolean) {
            val msgParams = tvMessage.layoutParams as ConstraintLayout.LayoutParams
            val timeParams = tvTime.layoutParams as ConstraintLayout.LayoutParams

            if (isSentByMe) {
                tvMessage.background = ContextCompat.getDrawable(tvMessage.context, R.drawable.bg_message_outgoing)
                tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
                msgParams.startToStart = ConstraintLayout.LayoutParams.UNSET
                msgParams.endToEnd = ConstraintLayout.LayoutParams.PARENT_ID

                timeParams.startToStart = ConstraintLayout.LayoutParams.UNSET
                timeParams.endToEnd = tvMessage.id
                tvTime.textAlignment = View.TEXT_ALIGNMENT_TEXT_END
            } else {
                tvMessage.background = ContextCompat.getDrawable(tvMessage.context, R.drawable.bg_message_incoming)
                tvMessage.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
                msgParams.startToStart = ConstraintLayout.LayoutParams.PARENT_ID
                msgParams.endToEnd = ConstraintLayout.LayoutParams.UNSET

                timeParams.startToStart = tvMessage.id
                timeParams.endToEnd = ConstraintLayout.LayoutParams.UNSET
                tvTime.textAlignment = View.TEXT_ALIGNMENT_TEXT_START
            }

            tvMessage.layoutParams = msgParams
            tvTime.layoutParams = timeParams
        }



    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val binding = ItemMessageBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ChatViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<ChatMessage>() {
        override fun areItemsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: ChatMessage, newItem: ChatMessage) = oldItem == newItem
    }
}
