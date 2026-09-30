package com.droidcon.uganda.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

/**
 * Repository for fetching conference data from Sessionize API
 *
 * To configure your event:
 * 1. Get your Sessionize event ID from the API/Embed page in your Sessionize dashboard
 * 2. Update the sessionizeEventId parameter below
 *
 * Sessionize API Documentation: https://sessionize.com/playbook/api
 */
class ConferenceRepository(
    // 2026 Sessionize endpoint. GridSmart is empty until the schedule is announced;
    // unscheduled talks then come from /view/All.
    private val sessionizeEventId: String = "bin6i3xe",
    private val useSessionize: Boolean = true
) {
    private val sessionizeBaseUrl = "https://sessionize.com/api/v2"

    private val client = HttpClient {
        install(ContentNegotiation) {
            json(Json {
                prettyPrint = true
                isLenient = true
                ignoreUnknownKeys = true
            })
        }
    }

    /**
     * Fetch all sessions from Sessionize API using GridSmart endpoint
     * Endpoint: GET /api/v2/{eventId}/view/GridSmart
     * This endpoint provides the complete schedule organized by date and room
     */
    suspend fun getSessions(): Result<List<Session>> {
        return try {
            if (!useSessionize || sessionizeEventId == "YOUR_EVENT_ID") {
                // If Sessionize is not configured, return local data
                println("⚠️  Sessionize is not configured. Using local data.")
                println("To enable Sessionize API:")
                println("1. Create a JSON API endpoint in your Sessionize dashboard")
                println("2. Update sessionizeEventId in ConferenceRepository.kt")
                return Result.success(getLocalSessions())
            }

            val speakersUrl = "$sessionizeBaseUrl/$sessionizeEventId/view/Speakers"
            println("🌐 Fetching speakers from Sessionize: $speakersUrl")
            val speakers: List<SessionizeSpeaker> = client.get(speakersUrl).body()
            val speakersMap = speakers.associateBy { it.id }

            val gridUrl = "$sessionizeBaseUrl/$sessionizeEventId/view/GridSmart"
            println("🌐 Fetching schedule from Sessionize GridSmart: $gridUrl")
            val gridResponse: SessionizeGridResponse = client.get(gridUrl).body()
            val gridSessions = SessionizeMapper.mapGridResponse(gridResponse, speakersMap)

            val sessions = if (gridSessions.isNotEmpty()) {
                println("✅ Successfully fetched ${gridSessions.size} sessions from Sessionize GridSmart")
                gridSessions
            } else {
                val allUrl = "$sessionizeBaseUrl/$sessionizeEventId/view/All"
                println("🌐 GridSmart has no sessions. Fetching accepted talks from: $allUrl")
                val allSessions: SessionizeAllSessions = client.get(allUrl).body()
                val mapped = SessionizeMapper.mapAllSessions(allSessions.sessions, speakersMap)
                println("✅ Successfully fetched ${mapped.size} unscheduled sessions from Sessionize All")
                mapped
            }

            Result.success(sessions)
        } catch (e: Exception) {
            println("❌ Error fetching from Sessionize API: ${e.message}")
            println("Error type: ${e::class.simpleName}")
            println("Falling back to local data...")
            e.printStackTrace()
            // Fallback to local data on error
            Result.success(getLocalSessions())
        }
    }

    /**
     * Fetch all speakers from Sessionize API
     * Endpoint: GET /api/v2/{eventId}/view/Speakers
     */
    suspend fun getSpeakers(): Result<List<Speaker>> {
        return try {
            if (!useSessionize || sessionizeEventId == "YOUR_EVENT_ID") {
                // If Sessionize is not configured, return local data
                println("⚠️  Sessionize is not configured. Using local data.")
                return Result.success(getLocalSpeakers())
            }

            val url = "$sessionizeBaseUrl/$sessionizeEventId/view/Speakers"
            println("🌐 Fetching speakers from Sessionize: $url")

            val sessionizeSpeakers: List<SessionizeSpeaker> = client.get(url).body()
            val speakers = SessionizeMapper.mapSpeakers(sessionizeSpeakers)

            println("✅ Successfully fetched ${speakers.size} speakers from Sessionize API")
            Result.success(speakers)
        } catch (e: Exception) {
            println("❌ Error fetching from Sessionize API: ${e.message}")
            println("Error type: ${e::class.simpleName}")
            println("Falling back to local data...")
            e.printStackTrace()
            // Fallback to local data on error
            Result.success(getLocalSpeakers())
        }
    }

    /**
     * Fallback to local data if API is not available
     */
    fun getLocalSessions(): List<Session> {
        return LocalDataSource.sessions
    }

    fun getLocalSpeakers(): List<Speaker> {
        return LocalDataSource.speakers
    }

    fun close() {
        client.close()
    }
}
