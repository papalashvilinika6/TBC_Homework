package com.example.myapplication.order

import android.os.Parcelable
import androidx.annotation.DrawableRes
import com.example.myapplication.R
import kotlinx.parcelize.Parcelize

@Parcelize
data class Order(
    val id: String,
    val title: String,
    val price: String,
    val color: String,
    val quantity: Int,
    val status: OrderStatus,
    @DrawableRes val imageRes: Int = R.drawable.ic_launcher_background,
    val reviewed: Boolean = false
) : Parcelable


enum class OrderStatus {
    ACTIVE,
    COMPLETED
}