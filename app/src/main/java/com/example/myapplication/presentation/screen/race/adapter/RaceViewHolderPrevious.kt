package com.example.myapplication.presentation.screen.race.adapter

import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.myapplication.databinding.ItemRacePreviousBinding
import com.example.myapplication.presentation.model.RaceUiModel
import java.util.Locale

class RaceViewHolderPrevious(
    private val binding: ItemRacePreviousBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RaceUiModel) = with(binding) {
        tvRound.text = "Round ${item.roundNum}"
        tvCountry.text = item.country
        tvCircuit.text = item.circuitName
        tvDate.text = item.date

        ImageId.load(item.countryImage) {
            crossfade(true)
        }

        val standings = item.standings.orEmpty()
        val times = item.time.orEmpty()

        tvFirst.text = standings.getOrNull(0)?.toUpperCase(Locale.ROOT) ?: "-"
        tvSecond.text = standings.getOrNull(1)?.toUpperCase(Locale.ROOT) ?: "-"
        tvThird.text = standings.getOrNull(2)?.toUpperCase(Locale.ROOT) ?: "-"

        tvTime1.text = times.getOrNull(0) ?: "-"
        tvTime2.text = times.getOrNull(1) ?: "-"
        tvTime3.text = times.getOrNull(2) ?: "-"
    }
}