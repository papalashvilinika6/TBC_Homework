package com.example.myapplication.presentation.ui.favdriver

import android.graphics.drawable.GradientDrawable
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.R
import com.example.myapplication.databinding.ItemDriverBinding

class DriversAdapter(
    private val onFavoriteClick: (Int) -> Unit
) : ListAdapter<DriverUiModel, DriversAdapter.DriverViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DriverViewHolder {
        val binding = ItemDriverBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return DriverViewHolder(binding)
    }

    override fun onBindViewHolder(holder: DriverViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class DriverViewHolder(
        private val binding: ItemDriverBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: DriverUiModel) = with(binding) {

            // name
            tvFirstName.text = item.fullName.substringBefore(" ")
            tvLastName.text = item.fullName.substringAfter(" ")

            // background color
            (root.background as? GradientDrawable)
                ?.setColor(item.backgroundColor)

            // image
            imgDriver.load(item.photoUrl) {
                crossfade(true)
//                placeholder(R.drawable.ic_user)
                error(R.drawable.ic_user)
            }

            // favorite icon
            btnFavorite.setImageResource(
                if (item.isFavorite)
                    R.drawable.ic_star_filled
                else
                    R.drawable.ic_star
            )

            btnFavorite.setOnClickListener {
                onFavoriteClick(item.id)
            }
        }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<DriverUiModel>() {
            override fun areItemsTheSame(
                oldItem: DriverUiModel,
                newItem: DriverUiModel
            ): Boolean = oldItem.id == newItem.id

            override fun areContentsTheSame(
                oldItem: DriverUiModel,
                newItem: DriverUiModel
            ): Boolean = oldItem == newItem
        }
    }
}
