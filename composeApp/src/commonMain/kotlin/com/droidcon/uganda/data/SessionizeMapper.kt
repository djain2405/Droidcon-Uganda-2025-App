package com.droidcon.uganda.data

import com.droidcon.uganda.utils.TimeZoneUtils
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

/**
 * Maps Sessionize API responses to app models
 * Uses GridSmart endpoint for sessions and Speakers endpoint for speaker details
 */
object SessionizeMapper {

    /**
     * Maps GridSmart response (organized by date/room) to app Session models
     * @param gridResponse The GridSmart API response
     * @param speakersMap Map of speaker IDs to full speaker details
     */
    fun mapGridResponse(gridResponse: SessionizeGridResponse, speakersMap: Map<String, SessionizeSpeaker>): List<Session> {
        val sessions = mutableListOf<Session>()

        gridResponse.forEach { day ->
            day.rooms.forEach { room ->
                room.sessions.forEach { gridSession ->
                    // Map session, including service sessions
                    val session = mapGridSession(gridSession, speakersMap)
                    if (session != null) {
                        sessions.add(session)
                    }
                }
            }
        }

        return sessions
    }

    /**
     * Maps a list of Sessionize speakers to app Speaker models
     */
    fun mapSpeakers(sessionizeSpeakers: List<SessionizeSpeaker>): List<Speaker> {
        return sessionizeSpeakers.map { mapSpeaker(it) }
    }

    /**
     * Maps a GridSmart session to app Session model
     */
    private fun mapGridSession(
        gridSession: SessionizeGridSession,
        speakersMap: Map<String, SessionizeSpeaker>
    ): Session? {
        // Parse start and end times
        val startTime = parseInstantToLocalDateTime(gridSession.startsAt)
        val endTime = parseInstantToLocalDateTime(gridSession.endsAt)

        // Get the first speaker (if any)
        val speaker = gridSession.speakers.firstOrNull()?.let { sessionSpeaker ->
            speakersMap[sessionSpeaker.id]?.let { mapSpeaker(it) }
        }

        // Determine track from categories
        val track = determineTrackFromCategories(gridSession.categories)

        // Determine session level from categories
        val level = determineLevelFromCategories(gridSession.categories)

        return Session(
            id = gridSession.id,
            title = gridSession.title,
            description = gridSession.description ?: "",
            startTime = startTime,
            endTime = endTime,
            speaker = speaker,
            track = track,
            room = gridSession.room,
            level = level
        )
    }

    /**
     * Maps the complete Sessionize response to app models (OLD FORMAT - kept for backward compatibility)
     */
    fun mapResponse(response: SessionizeResponse): Pair<List<Session>, List<Speaker>> {
        // Create lookup maps for efficient access
        val speakersMap = response.speakers.associateBy { it.id }
        val roomsMap = response.rooms.associateBy { it.id }
        val categoriesMap = buildCategoryMap(response.categories)

        // Map speakers first
        val speakers = response.speakers.map { mapSpeaker(it) }

        // Map sessions with references to speakers
        val sessions = response.sessions.mapNotNull { sessionizeSession ->
            mapSession(sessionizeSession, speakersMap, roomsMap, categoriesMap)
        }

        return Pair(sessions, speakers)
    }

    /**
     * Maps a single Sessionize session to app Session model
     */
    private fun mapSession(
        sessionizeSession: SessionizeSession,
        speakersMap: Map<String, SessionizeSpeaker>,
        roomsMap: Map<Int, SessionizeRoom>,
        categoriesMap: Map<Int, CategoryInfo>
    ): Session? {
        // Parse start and end times (required for sessions)
        val startTime = sessionizeSession.startsAt?.let { parseInstantToLocalDateTime(it) } ?: return null
        val endTime = sessionizeSession.endsAt?.let { parseInstantToLocalDateTime(it) } ?: return null

        // Get the first speaker (if multiple, we'll use the first one)
        val speaker = sessionizeSession.speakers.firstOrNull()?.let { speakerId ->
            speakersMap[speakerId]?.let { mapSpeaker(it) }
        }

        // Determine track from categories
        val track = determineTrack(sessionizeSession.categoryItems, categoriesMap)

        // Determine session level from categories
        val level = determineLevel(sessionizeSession.categoryItems, categoriesMap)

        // Get room name
        val room = sessionizeSession.roomId?.let { roomsMap[it]?.name } ?: "TBA"

        return Session(
            id = sessionizeSession.id,
            title = sessionizeSession.title,
            description = sessionizeSession.description ?: "",
            startTime = startTime,
            endTime = endTime,
            speaker = speaker,
            track = track,
            room = room,
            level = level
        )
    }

    /**
     * Maps a Sessionize speaker to app Speaker model
     */
    private fun mapSpeaker(sessionizeSpeaker: SessionizeSpeaker): Speaker {
        // Extract social links
        val twitter = sessionizeSpeaker.links.firstOrNull {
            it.linkType.equals("Twitter", ignoreCase = true) || it.linkType.equals("X", ignoreCase = true)
        }?.url

        val linkedin = sessionizeSpeaker.links.firstOrNull {
            it.linkType.equals("LinkedIn", ignoreCase = true)
        }?.url

        return Speaker(
            id = sessionizeSpeaker.id,
            name = sessionizeSpeaker.fullName,
            title = sessionizeSpeaker.tagLine ?: "",
            company = extractCompanyFromTagline(sessionizeSpeaker.tagLine),
            bio = sessionizeSpeaker.bio ?: "",
            imageUrl = sessionizeSpeaker.profilePicture ?: "",
            twitter = twitter,
            linkedin = linkedin
        )
    }

    /**
     * Parses ISO 8601 timestamp to LocalDateTime in conference timezone
     * Sessionize returns timestamps without timezone indicators (e.g., "2025-11-10T10:05:00")
     * These timestamps are assumed to be in the conference timezone (EAT)
     */
    private fun parseInstantToLocalDateTime(isoString: String): LocalDateTime {
        return try {
            // If the string has timezone indicator (Z or +00:00), parse as Instant
            if (isoString.contains('Z') || isoString.matches(Regex(".*[+-]\\d{2}:\\d{2}$"))) {
                val instant = Instant.parse(isoString)
                instant.toLocalDateTime(TimeZoneUtils.CONFERENCE_TIMEZONE)
            } else {
                // Parse as LocalDateTime directly (already in conference timezone)
                LocalDateTime.parse(isoString)
            }
        } catch (e: Exception) {
            println("Failed to parse timestamp: $isoString - ${e.message}")
            throw e
        }
    }

    /**
     * Builds a map of category item IDs to category information
     */
    private fun buildCategoryMap(categories: List<SessionizeCategory>): Map<Int, CategoryInfo> {
        val map = mutableMapOf<Int, CategoryInfo>()
        categories.forEach { category ->
            category.items.forEach { item ->
                map[item.id] = CategoryInfo(
                    categoryTitle = category.title,
                    itemName = item.name
                )
            }
        }
        return map
    }

    /**
     * Determines track from GridSmart session categories
     */
    private fun determineTrackFromCategories(categories: List<SessionizeSessionCategory>): Track {
        categories.forEach { category ->
            val categoryName = category.name.lowercase()

            // Check if category is about tracks/topics
            if (categoryName.contains("track") || categoryName.contains("topic") || categoryName.contains("category")) {
                category.categoryItems.forEach { item ->
                    val itemName = item.name.lowercase()
                    return when {
                        itemName.contains("android") -> Track.ANDROID
                        itemName.contains("kotlin") -> Track.KOTLIN
                        itemName.contains("design") || itemName.contains("ux") || itemName.contains("ui") -> Track.DESIGN
                        itemName.contains("cloud") || itemName.contains("backend") || itemName.contains("server") -> Track.CLOUD
                        itemName.contains("keynote") -> Track.KEYNOTE
                        else -> Track.NONE
                    }
                }
            }
        }
        return Track.NONE
    }

    /**
     * Determines level from GridSmart session categories
     */
    private fun determineLevelFromCategories(categories: List<SessionizeSessionCategory>): SessionLevel? {
        categories.forEach { category ->
            val categoryName = category.name.lowercase()

            // Check if category is about level/difficulty
            if (categoryName.contains("level") || categoryName.contains("difficulty") || categoryName.contains("audience")) {
                category.categoryItems.forEach { item ->
                    val itemName = item.name.lowercase()
                    return when {
                        itemName.contains("beginner") || itemName.contains("intro") || itemName.contains("101") -> SessionLevel.BEGINNER
                        itemName.contains("intermediate") || itemName.contains("200") -> SessionLevel.INTERMEDIATE
                        itemName.contains("advanced") || itemName.contains("expert") || itemName.contains("300") -> SessionLevel.ADVANCED
                        else -> null
                    }
                }
            }
        }
        return null
    }

    /**
     * Determines the Track from session categories (OLD FORMAT)
     * Looks for category items that match known track names
     */
    private fun determineTrack(categoryItemIds: List<Int>, categoriesMap: Map<Int, CategoryInfo>): Track {
        categoryItemIds.forEach { itemId ->
            val categoryInfo = categoriesMap[itemId] ?: return@forEach
            val categoryName = categoryInfo.categoryTitle.lowercase()
            val itemName = categoryInfo.itemName.lowercase()

            // Check if category is about tracks/topics
            if (categoryName.contains("track") || categoryName.contains("topic") || categoryName.contains("category")) {
                return when {
                    itemName.contains("android") -> Track.ANDROID
                    itemName.contains("kotlin") -> Track.KOTLIN
                    itemName.contains("design") || itemName.contains("ux") || itemName.contains("ui") -> Track.DESIGN
                    itemName.contains("cloud") || itemName.contains("backend") || itemName.contains("server") -> Track.CLOUD
                    itemName.contains("keynote") -> Track.KEYNOTE
                    else -> Track.NONE
                }
            }
        }

        // Check if it's a keynote from session title or type
        return Track.NONE
    }

    /**
     * Determines the SessionLevel from session categories
     * Looks for category items that match difficulty levels
     */
    private fun determineLevel(categoryItemIds: List<Int>, categoriesMap: Map<Int, CategoryInfo>): SessionLevel? {
        categoryItemIds.forEach { itemId ->
            val categoryInfo = categoriesMap[itemId] ?: return@forEach
            val categoryName = categoryInfo.categoryTitle.lowercase()
            val itemName = categoryInfo.itemName.lowercase()

            // Check if category is about level/difficulty
            if (categoryName.contains("level") || categoryName.contains("difficulty") || categoryName.contains("audience")) {
                return when {
                    itemName.contains("beginner") || itemName.contains("intro") || itemName.contains("101") -> SessionLevel.BEGINNER
                    itemName.contains("intermediate") || itemName.contains("200") -> SessionLevel.INTERMEDIATE
                    itemName.contains("advanced") || itemName.contains("expert") || itemName.contains("300") -> SessionLevel.ADVANCED
                    else -> null
                }
            }
        }
        return null
    }

    /**
     * Extracts company name from tagline (usually in format "Title @ Company")
     */
    private fun extractCompanyFromTagline(tagline: String?): String {
        if (tagline == null) return ""

        // Common patterns: "Title @ Company", "Title at Company", "Title | Company"
        val parts = tagline.split("@", "at", "|", ignoreCase = true)
        if (parts.size > 1) {
            return parts[1].trim()
        }

        return ""
    }

    /**
     * Helper data class for category information
     */
    private data class CategoryInfo(
        val categoryTitle: String,
        val itemName: String
    )
}
