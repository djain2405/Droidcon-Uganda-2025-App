package com.droidcon.uganda.data

import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

class ConferenceRepository(
    private val baseUrl: String = "https://api.droidcon.uganda.com"
) {
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
     * Fetch all sessions from the API
     * Endpoint: GET /sessions
     */
    suspend fun getSessions(): Result<List<Session>> {
        return try {
            val sessions: List<Session> = client.get("$baseUrl/sessions").body()
            Result.success(sessions)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    /**
     * Fetch all speakers from the API
     * Endpoint: GET /speakers
     */
    suspend fun getSpeakers(): Result<List<Speaker>> {
        return try {
            val speakers: List<Speaker> = client.get("$baseUrl/speakers").body()
            Result.success(speakers)
        } catch (e: Exception) {
            Result.failure(e)
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
