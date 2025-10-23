package com.example.myapplication.product

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemProductBinding

class ProductAdapter : ListAdapter<Product, ProductAdapter.ViewHolder>(DiffCallback()) {

    inner class ViewHolder(val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Product) {
            binding.txTitleId.text = item.title
            binding.tvPriceId.text = item.price.toString()
            binding.ivImageId.setImageResource(item.image)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Product, newItem: Product) = oldItem == newItem
    }
}



