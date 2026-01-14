package com.example.myapp.presentation.extension

import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide
import com.example.myapp.R


fun AppCompatImageView.loadImage(url: String?) {
    Glide.with(context)
        .load(url)
        .placeholder(R.drawable.ic_launcher_background)
        .into(this)
}