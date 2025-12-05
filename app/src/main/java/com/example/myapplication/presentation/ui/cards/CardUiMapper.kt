package com.example.myapplication.presentation.ui.cards

import com.example.myapplication.domain.model.Card

fun Card.toUi() = CardUi(
    location = location,
    altitude = altitude,
    title = title,
    image = image,
    stars = stars
)
