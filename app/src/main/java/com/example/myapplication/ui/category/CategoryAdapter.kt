package com.example.myapplication.ui.category

import android.graphics.Color
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemCategoryBinding

class CategoryAdapter : ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class CategoryViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CategoryItem) {
            val depth = item.category.depth
            val maxBalls = 4
            
            // Calculate number of balls to show (max 4)
            val ballCount = if (depth == 0) 0 else minOf(depth, maxBalls)
            
            // Build the display text with bullets
            val displayText = buildString {
                repeat(ballCount) {
                    append("• ")
                }
                append(item.category.name)
            }
            
            // Create SpannableString to color only the bullets green
            val spannable = SpannableString(displayText)
            if (ballCount > 0) {
                val greenColor = Color.parseColor("#4CAF50") // Green color
                val bulletLength = ballCount * 2 // Each "• " is 2 characters
                spannable.setSpan(
                    ForegroundColorSpan(greenColor),
                    0,
                    bulletLength,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
                )
            }
            
            binding.categoryName.text = spannable
        }
    }

    private class CategoryDiffCallback : DiffUtil.ItemCallback<CategoryItem>() {
        override fun areItemsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem.category.id == newItem.category.id
        }

        override fun areContentsTheSame(oldItem: CategoryItem, newItem: CategoryItem): Boolean {
            return oldItem == newItem
        }
    }
}

