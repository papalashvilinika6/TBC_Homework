package com.example.myapplication.category

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCategoryBinding
import com.example.myapplication.R

class CategoryAdapter(
    private val onCategoryClick: (String) -> Unit
) : ListAdapter<Category, CategoryAdapter.ViewHolder>(DiffCallback()) {

    private var selectedPos = 0

    inner class ViewHolder(val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Category, isSelected: Boolean) {
            binding.textCategory.text = item.title
            binding.textCategory.setTextColor(Color.WHITE)
            binding.iconImage.setImageResource(item.image)
            binding.layoutId.setBackgroundResource(
                if (isSelected) R.drawable.selected_button_shape else R.drawable.button_shape
            )
            binding.root.setOnClickListener {
                selectedPos = adapterPosition
                notifyDataSetChanged()
                onCategoryClick(item.title)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position), position == selectedPos)
    }

    class DiffCallback : DiffUtil.ItemCallback<Category>() {
        override fun areItemsTheSame(oldItem: Category, newItem: Category) = oldItem.title == newItem.title
        override fun areContentsTheSame(oldItem: Category, newItem: Category) = oldItem == newItem
    }
}