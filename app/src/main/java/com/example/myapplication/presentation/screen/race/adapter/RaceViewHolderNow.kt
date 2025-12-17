package com.example.myapplication.presentation.screen.race.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.RoundedCornersTransformation
import com.example.myapplication.databinding.ItemRaceNowBinding
import com.example.myapplication.presentation.model.RaceUiModel

class RaceViewHolderNow(
    private val binding: ItemRaceNowBinding
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