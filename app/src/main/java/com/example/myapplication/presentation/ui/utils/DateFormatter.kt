package com.example.myapplication.presentation.ui.utils

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

fun Long.toFeedDateString(): String {
    val instant = Instant.fromEpochMilliseconds(this)
    val local = instant.toLocalDateTime(TimeZone.currentSystemDefault())

    val month = local.month.name.lowercase().replaceFirstChar { it.titlecase() }.take(3)

    val hour = local.hour.toString().padStart(2, '0')
    val minute = local.minute.toString().padStart(2, '0')

    return "$month ${local.dayOfMonth} at $hour:$minute"
}

