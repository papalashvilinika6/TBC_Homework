package com.example.myapplication.presentation.screen.race.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.databinding.ItemRace2026Binding
import com.example.myapplication.presentation.model.RaceUiModel

class RaceViewHolder2026(
    private val binding: ItemRace2026Binding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RaceUiModel) = with(binding) {
        tvRound.text = "Round ${item.roundNum}"
        tvCountry.text = item.country
        tvCircuit.text = item.circuitName
        tvDate.text = item.date

        imageId.load(item.countryImage) {
            crossfade(true)
            transformations(RoundedCornersTransformation(50f))
        }
    }
}