package com.itstorm.finalproject.shared.utils

import java.time.LocalTime
import java.time.format.DateTimeFormatter

fun getTimeString(start: LocalTime, end: LocalTime): String {
    val formatter = DateTimeFormatter.ofPattern("HH:mm")
    val formattedStart = start.format(formatter)
    val formattedEnd = end.format(formatter)

    return "$formattedStart -$formattedEnd"
}