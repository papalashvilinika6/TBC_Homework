package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (
    val id: String,
    val firstName: String,
    val lastName: String,
    val birthday: String,
    val address: String,
    val email: String
) : Parcelable {
        companion object{
            const val KEY = "user_key"
        }
    }