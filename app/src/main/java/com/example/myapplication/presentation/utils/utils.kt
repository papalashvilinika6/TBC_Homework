package com.example.myapplication.presentation.utils

import android.view.View
import com.google.android.material.snackbar.Snackbar

fun showSnackbar(rootView: View, message: String) {
    Snackbar.make(rootView, message, Snackbar.LENGTH_SHORT).show()
}