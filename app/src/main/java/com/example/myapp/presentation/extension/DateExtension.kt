package com.example.myapp.presentation.extension

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun Long.toPostDateText(): String {
    val sdf = SimpleDateFormat("d MMMM 'at' h:mm a", Locale.ENGLISH)
    return sdf.format(Date(this))
}
