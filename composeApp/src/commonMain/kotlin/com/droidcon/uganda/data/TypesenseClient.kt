package com.droidcon.uganda.data

import io.ktor.client.HttpClient
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.parameter
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.isSuccess
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.buildJsonObject
import kotlinx.serialization.json.put

internal class TypesenseClient(
    private val http: HttpClient
) {
    private val json = Json { ignoreUnknownKeys = true }

    suspend fun search(query: String): List<String>? {
        if (!TypesenseConfig.isConfigured) return null
        return try {
            val response = http.get("${TypesenseConfig.host}/collections/sessions/documents/search") {
                header("X-TYPESENSE-API-KEY", TypesenseConfig.searchApiKey)
                parameter("q", query)
                parameter("query_by", "title,speaker,track,description")
                parameter("per_page", "250")
            }
            if (!response.status.isSuccess()) {
                println("Typesense search failed: ${response.status}")
                return null
            }
            json.decodeFromString<TypesenseSearchResponse>(response.bodyAsText())
                .hits
                .map { it.document.id }
        } catch (e: Exception) {
            println("Typesense search error: ${e.message}")
            null
        }
    }

    suspend fun upsertSessions(sessions: List<Session>, adminApiKey: String) {
        if (!TypesenseConfig.isConfigured || adminApiKey.isBlank()) return
        try {
            if (!collectionExists(adminApiKey)) {
                createCollection(adminApiKey)
            }
            val jsonl = sessions.joinToString("\n") { session ->
                buildJsonObject {
                    put("id", session.id)
                    put("title", session.title)
                    put("speaker", session.speaker?.name ?: "")
                    put("track", session.track.displayName)
                    put("description", session.description)
                }.toString()
            }
            val response = http.post("${TypesenseConfig.host}/collections/sessions/documents/import") {
                header("X-TYPESENSE-API-KEY", adminApiKey)
                parameter("action", "upsert")
                contentType(ContentType.Text.Plain)
                setBody(jsonl)
            }
            if (!response.status.isSuccess()) {
                println("Typesense import failed: ${response.status} ${response.bodyAsText()}")
            } else {
                println("Typesense indexed ${sessions.size} sessions")
            }
        } catch (e: Exception) {
            println("Typesense index error: ${e.message}")
        }
    }

    private suspend fun collectionExists(adminApiKey: String): Boolean {
        val response = http.get("${TypesenseConfig.host}/collections/sessions") {
            header("X-TYPESENSE-API-KEY", adminApiKey)
        }
        return response.status == HttpStatusCode.OK
    }

    private suspend fun createCollection(adminApiKey: String) {
        val schema = """
            {
              "name": "sessions",
              "fields": [
                {"name": "title", "type": "string"},
                {"name": "speaker", "type": "string", "optional": true},
                {"name": "track", "type": "string", "optional": true},
                {"name": "description", "type": "string", "optional": true}
              ]
            }
        """.trimIndent()
        val response = http.post("${TypesenseConfig.host}/collections") {
            header("X-TYPESENSE-API-KEY", adminApiKey)
            contentType(ContentType.Application.Json)
            setBody(schema)
        }
        if (!response.status.isSuccess() && response.status != HttpStatusCode.Conflict) {
            println("Typesense create collection failed: ${response.status} ${response.bodyAsText()}")
        }
    }
}

@Serializable
private data class TypesenseSearchResponse(
    val hits: List<TypesenseHit> = emptyList()
)

@Serializable
private data class TypesenseHit(
    val document: TypesenseDocument
)

@Serializable
private data class TypesenseDocument(
    val id: String
)
