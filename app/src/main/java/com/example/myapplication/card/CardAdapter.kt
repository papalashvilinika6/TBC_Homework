package com.example.myapplication.card

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.CardItemBinding

class CardAdapter(
    private val onLongClick: (Card) -> Unit
) : ListAdapter<Card, CardAdapter.CardViewHolder>(DiffCallback()) {

    inner class CardViewHolder(val binding: CardItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(card: Card) = with(binding) {
            tvCardNumberId.text = card.cardNumber
            tvCardHolderNameId.text = card.name
            tvValidThruId.text = card.valid
            val bg = if (card.type == Type.MASTERCARD) R.drawable.mc_card else R.drawable.visa_card
            mainItemCardId.setBackgroundResource(bg)
            mainItemCardId.setOnLongClickListener {
                onLongClick(card)
                true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Card>() {
        override fun areItemsTheSame(oldItem: Card, newItem: Card) = oldItem.id == newItem.id
        override fun areContentsTheSame(oldItem: Card, newItem: Card) = oldItem == newItem
    }
}
