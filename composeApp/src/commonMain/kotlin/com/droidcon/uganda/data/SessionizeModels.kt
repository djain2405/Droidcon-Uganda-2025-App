package com.droidcon.uganda.data

import kotlinx.serialization.Serializable

/**
 * Data models matching the Sessionize API response structure
 *
 * GridSmart Endpoint: https://sessionize.com/api/v2/{eventId}/view/GridSmart
 * Speakers Endpoint: https://sessionize.com/api/v2/{eventId}/view/Speakers
 */

// GridSmart API response - organized by date and room
typealias SessionizeGridResponse = List<SessionizeDay>

@Serializable
data class SessionizeDay(
    val date: String,  // e.g., "2025-11-10T00:00:00"
    val isDefault: Boolean = true,
    val rooms: List<SessionizeRoomWithSessions>
)

@Serializable
data class SessionizeRoomWithSessions(
    val id: Int,
    val name: String,
    val sessions: List<SessionizeGridSession>
)

@Serializable
data class SessionizeGridSession(
    val id: String,
    val title: String,
    val description: String? = null,
    val startsAt: String,  // e.g., "2025-11-10T09:00:00"
    val endsAt: String,
    val isServiceSession: Boolean = false,
    val isPlenumSession: Boolean = false,
    val speakers: List<SessionizeSessionSpeaker> = emptyList(),
    val categories: List<SessionizeSessionCategory> = emptyList(),
    val roomId: Int,
    val room: String,
    val liveUrl: String? = null,
    val recordingUrl: String? = null,
    val status: String? = null,
    val isInformed: Boolean = false,
    val isConfirmed: Boolean = false
)

@Serializable
data class SessionizeSessionSpeaker(
    val id: String,
    val name: String
)

@Serializable
data class SessionizeSessionCategory(
    val id: Int,
    val name: String,
    val categoryItems: List<SessionizeCategoryItem>,
    val sort: Int = 0  // Optional in some responses
)

// Old /view/All format (kept for reference, not used anymore)
@Serializable
data class SessionizeResponse(
    val sessions: List<SessionizeSession>,
    val speakers: List<SessionizeSpeaker>,
    val questions: List<SessionizeQuestion> = emptyList(),
    val categories: List<SessionizeCategory> = emptyList(),
    val rooms: List<SessionizeRoom> = emptyList()
)

@Serializable
data class SessionizeSession(
    val id: String,
    val title: String,
    val description: String? = null,
    val startsAt: String? = null,  // ISO 8601 format: "2023-09-16T09:00:00Z"
    val endsAt: String? = null,
    val isServiceSession: Boolean = false,
    val isPlenumSession: Boolean = false,
    val speakers: List<String> = emptyList(),  // Speaker IDs
    val categoryItems: List<Int> = emptyList(),
    val questionAnswers: List<SessionizeQuestionAnswer> = emptyList(),
    val roomId: Int? = null,
    val liveUrl: String? = null,
    val recordingUrl: String? = null,
    val status: String? = null,
    val isInformed: Boolean = false,
    val isConfirmed: Boolean = false
)

@Serializable
data class SessionizeSpeaker(
    val id: String,
    val firstName: String,
    val lastName: String,
    val fullName: String,
    val bio: String? = null,
    val tagLine: String? = null,
    val profilePicture: String? = null,
    val isTopSpeaker: Boolean = false,
    val links: List<SessionizeLink> = emptyList(),
    val sessions: List<SessionizeSpeakerSession> = emptyList(),  // Speakers endpoint format
    val categoryItems: List<Int> = emptyList(),
    val questionAnswers: List<SessionizeQuestionAnswer> = emptyList()
)

@Serializable
data class SessionizeSpeakerSession(
    val id: Int,  // Note: Session ID is Int in Speakers endpoint
    val name: String
)

@Serializable
data class SessionizeLink(
    val title: String,
    val url: String,
    val linkType: String  // e.g., "Twitter", "LinkedIn", "Blog"
)

@Serializable
data class SessionizeQuestion(
    val id: Int,
    val question: String,
    val questionType: String,
    val sort: Int
)

@Serializable
data class SessionizeQuestionAnswer(
    val questionId: Int,
    val answerValue: String? = null
)

@Serializable
data class SessionizeCategory(
    val id: Int,
    val title: String,
    val items: List<SessionizeCategoryItem> = emptyList(),
    val sort: Int = 0,
    val type: String = "session"  // e.g., "session"
)

@Serializable
data class SessionizeCategoryItem(
    val id: Int,
    val name: String,
    val sort: Int = 0  // Optional in GridSmart endpoint
)

@Serializable
data class SessionizeRoom(
    val id: Int,
    val name: String,
    val sort: Int
)
