package com.example.myapplication.helper

import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar

object Helper {
    fun showSnackbar(view: View, message: String) {
        Snackbar.make(view, message, Snackbar.LENGTH_SHORT).show()
    }

    fun emailIsValid(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}