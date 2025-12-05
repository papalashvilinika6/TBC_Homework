package com.example.myapplication.presentation.ui.cards

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.databinding.ItemCardBinding

class CardPagerAdapter : RecyclerView.Adapter<CardPagerAdapter.CardViewHolder>() {

    private val items = mutableListOf<CardUi>()

    fun submitList(list: List<CardUi>) {
        items.clear()
        items.addAll(list)
        notifyDataSetChanged()
    }

    inner class CardViewHolder(val binding: ItemCardBinding)
        : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val binding = ItemCardBinding.inflate(
            LayoutInflater.from(parent.context), parent, false
        )
        return CardViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        val item = items[position]

        holder.binding.apply {
            imgBackground.load(item.image)
            tvTitle.text = item.title
            tvCity.text = item.location
            ratingBar.rating = item.stars.toFloat()
        }
    }

    override fun getItemCount() = items.size
}
