package com.example.myapp.presentation.extension

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBar(message: String) {
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).show()
}

fun View.show() { visibility = View.VISIBLE }
fun View.gone() { visibility = View.GONE }