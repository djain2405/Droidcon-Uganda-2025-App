package com.droidcon.uganda.utils

import kotlinx.datetime.*

object TimeZoneUtils {
    // East Africa Time - Uganda's timezone
    val CONFERENCE_TIMEZONE = TimeZone.of("Africa/Nairobi") // UTC+3, same as Uganda

    /**
     * Creates a LocalDateTime for a conference session in EAT timezone
     * @param date The conference date (e.g., "2025-05-15")
     * @param time The time in HH:mm format (e.g., "09:00")
     */
    fun createConferenceDateTime(date: String, time: String): LocalDateTime {
        val (hour, minute) = time.split(":").map { it.toInt() }
        val localDate = LocalDate.parse(date)
        return LocalDateTime(
            year = localDate.year,
            monthNumber = localDate.monthNumber,
            dayOfMonth = localDate.dayOfMonth,
            hour = hour,
            minute = minute
        )
    }

    /**
     * Converts a conference time (in EAT) to the user's local timezone
     */
    fun toUserLocalTime(conferenceDateTime: LocalDateTime): LocalDateTime {
        val instant = conferenceDateTime.toInstant(CONFERENCE_TIMEZONE)
        return instant.toLocalDateTime(TimeZone.currentSystemDefault())
    }

    /**
     * Formats a LocalDateTime to display time only (HH:mm)
     */
    fun formatTime(dateTime: LocalDateTime): String {
        val hour = dateTime.hour.toString().padStart(2, '0')
        val minute = dateTime.minute.toString().padStart(2, '0')
        return "$hour:$minute"
    }

    /**
     * Gets a user-friendly timezone name for display
     */
    fun getUserTimezoneName(): String {
        val userTz = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val offset = userTz.offsetAt(now)

        // Format offset as +HH:mm or -HH:mm
        val totalSeconds = offset.totalSeconds
        val hours = totalSeconds / 3600
        val minutes = (totalSeconds % 3600) / 60
        val sign = if (totalSeconds >= 0) "+" else "-"
        val offsetStr = "$sign${kotlin.math.abs(hours).toString().padStart(2, '0')}:${kotlin.math.abs(minutes).toString().padStart(2, '0')}"

        return "UTC$offsetStr"
    }

    /**
     * Check if user is in the same timezone as the conference
     */
    fun isInConferenceTimezone(): Boolean {
        val userTz = TimeZone.currentSystemDefault()
        val now = Clock.System.now()
        val conferenceOffset = CONFERENCE_TIMEZONE.offsetAt(now)
        val userOffset = userTz.offsetAt(now)
        return conferenceOffset == userOffset
    }
}
