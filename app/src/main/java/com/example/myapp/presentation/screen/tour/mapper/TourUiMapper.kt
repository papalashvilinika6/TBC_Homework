package com.example.myapp.presentation.screen.tour.mapper

import com.example.myapp.domain.model.Tour
import com.example.myapp.presentation.screen.tour.model.TourUi

fun Tour.toUi() = TourUi(
    title = title,
    location = location,
    number = number,
    photo = photo,
    price = price,
    stars = stars
)