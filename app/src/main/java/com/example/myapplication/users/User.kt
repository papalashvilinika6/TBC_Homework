package com.example.myapplication.users

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (var firstName: String, val lastName: String, val age: Int, val email: String) :
    Parcelable {
    companion object{
        const val USER_KEY = "user_key"
    }
}