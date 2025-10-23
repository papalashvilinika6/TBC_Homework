package com.example.myapplication

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class User (var firstName: String, val lastName: String, val age: Int?, val email: String) : Parcelable