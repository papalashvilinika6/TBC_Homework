package com.example.myapplication

import android.util.Patterns
import android.view.View
import com.google.android.material.snackbar.Snackbar

object Data {
    fun showSnackbar(bindingRoot: View, message: String) {
        Snackbar.make(bindingRoot, message, Snackbar.LENGTH_SHORT).show()
    }

    fun emailIsValid(email: String) : Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
}