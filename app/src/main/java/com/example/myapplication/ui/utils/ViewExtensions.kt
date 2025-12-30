package com.example.myapplication.ui.utils

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatImageView
import coil.load
import coil.transform.CircleCropTransformation
import com.example.myapplication.R
import com.google.android.material.snackbar.Snackbar

fun ImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_launcher_background,
    errorRes: Int = R.drawable.ic_launcher_background,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}

fun AppCompatImageView.loadImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_launcher_background,
    errorRes: Int = R.drawable.ic_launcher_background,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
    }
}

fun ImageView.loadCircleImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_launcher_background,
    errorRes: Int = R.drawable.ic_launcher_background,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
        transformations(CircleCropTransformation())
    }
}

fun AppCompatImageView.loadCircleImage(
    url: String?,
    placeholderRes: Int = R.drawable.ic_launcher_background,
    errorRes: Int = R.drawable.ic_launcher_background,
    enableCrossfade: Boolean = true,
) {
    this.load(url) {
        placeholder(placeholderRes)
        error(errorRes)
        crossfade(enableCrossfade)
        transformations(CircleCropTransformation())
    }
}

fun View.hideKeyboard() {
    val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
    imm.hideSoftInputFromWindow(windowToken, 0)
}

fun View.showSnackBar(message: String, duration: Int = Snackbar.LENGTH_SHORT) {
    Snackbar.make(this, message, duration).show()
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.INVISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun View.setVisible(visible: Boolean) {
    if (visible) show() else gone()
}


