package com.example.myapplication.presentation.ui.common

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun View.showSnack(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}
