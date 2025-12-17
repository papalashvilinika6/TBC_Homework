package com.example.myapplication.presentation.screen.race.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ItemRaceNowBinding
import com.example.myapplication.databinding.ItemRacePreviousBinding
import com.example.myapplication.presentation.model.RaceUiModel

class RaceAdapter :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var items: List<RaceUiModel> = emptyList()
    private var is2025: Boolean = true

    fun submit(
        races: List<RaceUiModel>,
        is2025: Boolean
    ) {
        this.items = races
        this.is2025 = is2025
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int =
        if (is2025) VIEW_2025 else VIEW_2026

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecyclerView.ViewHolder {

        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            VIEW_2025 -> {
                val binding = ItemRacePreviousBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                RaceViewHolderPrevious(binding)
            }
            else -> {
                val binding = ItemRaceNowBinding.inflate(
                    inflater,
                    parent,
                    false
                )
                RaceViewHolderNow(binding)
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int
    ) {
        val item = items[position]
        when (holder) {
            is RaceViewHolderPrevious -> holder.bind(item)
            is RaceViewHolderNow -> holder.bind(item)
        }
    }

    override fun getItemCount(): Int = items.size

    private companion object {
        const val VIEW_2025 = 2025
        const val VIEW_2026 = 2026
    }
}