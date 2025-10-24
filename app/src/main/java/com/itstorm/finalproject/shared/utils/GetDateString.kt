package com.itstorm.finalproject.shared.utils

import java.time.LocalDate
import java.time.Month

fun getDateString(date: LocalDate, currentDate: LocalDate): String {
    val wordyDescription =
        when(date) {
        currentDate -> "СЕГОДНЯ, "
        currentDate.plusDays(1) -> "ЗАВТРА, "
        currentDate.minusDays(1) -> "ВЧЕРА, "
        else -> ""
    }

    val month =
        when(date.month) {
            Month.JANUARY -> "ЯНВАРЯ"
            Month.FEBRUARY -> "ФЕВРАЛЯ"
            Month.MARCH -> "МАРТА"
            Month.APRIL -> "АПРЕЛЯ"
            Month.MAY -> "МАЯ"
            Month.JUNE -> "ИЮНЯ"
            Month.JULY -> "ИЮЛЯ"
            Month.AUGUST -> "АВГУСТА"
            Month.SEPTEMBER -> "СЕНТЯБРЯ"
            Month.OCTOBER -> "ОКТЯБРЯ"
            Month.NOVEMBER -> "НОЯБРЯ"
            Month.DECEMBER -> "ДЕКАБРЯ"
        }

    val dateString = "$wordyDescription${date.dayOfMonth} $month"
    return dateString
}