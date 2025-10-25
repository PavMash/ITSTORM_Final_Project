package com.itstorm.core_domain.usecases

import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter

class SessionTimeCalculator(private val zoneId: ZoneId) {

    fun startTimeToInstant(date: LocalDate, time: LocalTime): Instant {
        val startLocal = date.atTime(time)
        val startInstant = startLocal.atZone(zoneId).toInstant()
        return startInstant
    }

    fun calculateEndTime(start: Instant, durationInHours: Double): Instant {
        val hoursAsSeconds = (durationInHours * 3600).toLong()
        val endInstant = start.plusSeconds(hoursAsSeconds)
        return endInstant
    }

    fun intervalsIntersect(start1: Instant, end1: Instant,
                           start2: Instant, end2: Instant): Boolean =
        (start1 < end2) && (end1 > start2)

    fun dateToLocalDate(date: String): LocalDate {
        val currentYear = LocalDate.now().year
        val formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy")
        val localDate = LocalDate.parse("$date.$currentYear", formatter)
        return localDate
    }

    fun timeToLocal(time: String): LocalTime {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localTime = LocalTime.parse(time, formatter)
        return localTime
    }

    fun instantToTimeString(time: Instant): String {
        val formatter = DateTimeFormatter.ofPattern("HH:mm")
        val localTime = LocalDateTime.ofInstant(time, zoneId)
            .toLocalTime()
        return localTime.format(formatter)
    }
}