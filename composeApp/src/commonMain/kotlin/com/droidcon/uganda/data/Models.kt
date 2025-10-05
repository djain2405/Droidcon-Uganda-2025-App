package com.droidcon.uganda.data

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

data class Session(
    val id: String,
    val title: String,
    val description: String,
    val startTime: String,
    val endTime: String,
    val speaker: Speaker,
    val track: Track,
    val room: String,
    val level: SessionLevel
)

enum class Track(val displayName: String, val color: Long) {
    ANDROID("Android", 0xFF4CAF50),
    KOTLIN("Kotlin", 0xFF2196F3),
    DESIGN("Design & UX", 0xFFFF9800),
    CLOUD("Cloud & Backend", 0xFF9C27B0),
    KEYNOTE("Keynote", 0xFFF44336)
}

enum class SessionLevel {
    BEGINNER,
    INTERMEDIATE,
    ADVANCED
}
