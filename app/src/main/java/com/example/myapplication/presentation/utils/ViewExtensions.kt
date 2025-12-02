package com.example.myapplication.presentation.utils

import android.view.View
import android.view.animation.AnimationUtils
import com.example.myapplication.R

fun View.show() {
    this.visibility = View.VISIBLE
}

fun View.hide() {
    this.visibility = View.GONE
}

fun View.shake() {
    val shake = AnimationUtils.loadAnimation(this.context, R.anim.shake)
    this.startAnimation(shake)
}