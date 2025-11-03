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
    // TODO: Replace "YOUR_EVENT_ID" with your actual Sessionize event ID
    // You can find this in your Sessionize dashboard under API/Embed
    // Example: "jl4ktls0" - this should be the alphanumeric ID, not the event slug
    private val sessionizeEventId: String = "re6do43h",
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

            // Fetch GridSmart data (complete schedule)
            val gridUrl = "$sessionizeBaseUrl/$sessionizeEventId/view/GridSmart"
            println("🌐 Fetching schedule from Sessionize GridSmart: $gridUrl")

            val gridResponse: SessionizeGridResponse = client.get(gridUrl).body()

            // Fetch speaker details
            val speakersUrl = "$sessionizeBaseUrl/$sessionizeEventId/view/Speakers"
            println("🌐 Fetching speakers from Sessionize: $speakersUrl")

            val speakers: List<SessionizeSpeaker> = client.get(speakersUrl).body()
            val speakersMap = speakers.associateBy { it.id }

            // Map the data
            val sessions = SessionizeMapper.mapGridResponse(gridResponse, speakersMap)

            println("✅ Successfully fetched ${sessions.size} sessions from Sessionize API (GridSmart)")
            println("   Including service sessions (breaks, registration, etc.)")
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
