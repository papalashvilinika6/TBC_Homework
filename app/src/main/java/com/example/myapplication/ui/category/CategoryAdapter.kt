package com.example.myapplication.ui.category

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemCategoryBinding

class CategoryAdapter(
) : ListAdapter<CategoryItem, CategoryAdapter.CategoryViewHolder>(CategoryDiffCallback()) {


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
            
            val ballCount = if (depth == 0) 0 else minOf(depth, maxBalls)
            
            binding.dotsContainer.removeAllViews()
            if (ballCount > 0) {
                val dotSizeDp = 8
                val dotMarginDp = 4
                val density = itemView.context.resources.displayMetrics.density
                val dotSize = (dotSizeDp * density).toInt()
                val dotMargin = (dotMarginDp * density).toInt()
                
                repeat(ballCount) {
                    val imageView = ImageView(itemView.context).apply {
                        setImageDrawable(
                            ContextCompat.getDrawable(
                                itemView.context,
                                R.drawable.orange_circle
                            )
                        )
                        layoutParams = ViewGroup.MarginLayoutParams(dotSize, dotSize).apply {
                            marginEnd = dotMargin
                        }
                        scaleType = ImageView.ScaleType.FIT_CENTER
                    }
                    binding.dotsContainer.addView(imageView)
                }
            }
            
            binding.categoryName.text = item.category.name
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

