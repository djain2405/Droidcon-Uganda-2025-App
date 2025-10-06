package com.droidcon.uganda.data

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class Speaker(
    val id: String,
    val name: String,
    val title: String,
    val company: String,
    val bio: String,
    val imageUrl: String,
    val twitter: String? = null,
    val linkedin: String? = null
)

@Serializable
data class Session(
    val id: String,
    val title: String,
    val description: String,
    val startTime: LocalDateTime,  // Now uses LocalDateTime
    val endTime: LocalDateTime,    // Now uses LocalDateTime
    val speaker: Speaker? = null,  // Optional - some sessions may not have a specific speaker
    val track: Track,
    val room: String,
    val level: SessionLevel? = null  // Optional - some sessions may not have a difficulty level
)

@Serializable
enum class Track(val displayName: String, val color: Long?) {
    ANDROID("Android", 0xFF4CAF50),
    KOTLIN("Kotlin", 0xFF2196F3),
    DESIGN("Design & UX", 0xFFFF9800),
    CLOUD("Cloud & Backend", 0xFF9C27B0),
    KEYNOTE("Keynote", 0xFFF44336),
    NONE("", color = null)
}

@Serializable
enum class SessionLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}
