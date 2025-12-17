package com.example.myapplication.presentation.utils

import android.util.Patterns
import android.view.View

object AuthValidation {

    fun validateEmail(
        rootView: View,
        email: String
    ): Boolean {
        if (email.isBlank()) {
            rootView.showSnack("Email cannot be empty")
            return false
        }

        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            rootView.showSnack("Invalid email format")
            return false
        }

        return true
    }

    fun validatePassword(
        rootView: View,
        password: String
    ): Boolean {
        if (password.isBlank()) {
            rootView.showSnack("Password cannot be empty")
            return false
        }

        if (password.length < 6) {
            rootView.showSnack("Password must be at least 6 characters")
            return false
        }

        return true
    }

    fun validateEmailAndPassword(
        rootView: View,
        email: String,
        password: String
    ): Boolean {
        return validateEmail(rootView, email) &&
                validatePassword(rootView, password)
    }
}
