package com.example.myapp.core.common

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {
    data class Dynamic(val value: String) : UiText()
    data class Resource(
        @StringRes val resId: Int,
        val args: List<Any> = emptyList()
    ) : UiText()

    fun asString(context: Context): String {
        return when (this) {
            is Dynamic -> value
            is Resource -> context.getString(resId, *args.toTypedArray())
        }
    }
}
