package com.droidcon.uganda.data

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

/**
 * Locks the Day 1 keynote demo: a typo still finds the Compose talk,
 * and the Kotlin chip has the talks the script names.
 */
class KeynoteSearchTest {

    @Test
    fun compseTypoFindsComposeTalkOnFirstMismatch() {
        val hits = LocalDataSource.sessions
            .filter { matchesSessionQuery(it, "Compse") }
            .map { it.title }
        assertEquals(listOf("Dynamic UI with Compose and Firebase"), hits)

        val partialHits = LocalDataSource.sessions
            .filter { matchesSessionQuery(it, "Comps") }
            .map { it.title }
        assertTrue(partialHits.contains("Dynamic UI with Compose and Firebase"))
    }

    @Test
    fun dayOneTracksMatchTheRehearsalScript() {
        val sessions = LocalDataSource.sessions
        val compose = sessions.single { it.title == "Dynamic UI with Compose and Firebase" }
        val richErrors = sessions.single { it.title.startsWith("Kotlin Rich Errors") }
        val kotlinServers = sessions.single { it.title.contains("Kotlin's Journey") }
        val welcome = sessions.single { it.title == "Welcome Address" }
        val flutter = sessions.single { it.title.contains("FlutterFire") }
        val breakSession = sessions.single { it.title == "Break" && it.id == "s4" }

        assertEquals(Track.ANDROID, compose.track)
        assertEquals(Track.KOTLIN, richErrors.track)
        assertEquals(Track.KOTLIN, kotlinServers.track)
        assertEquals(Track.KEYNOTE, welcome.track)
        assertEquals(Track.NONE, flutter.track)
        assertEquals(Track.NONE, breakSession.track)

        assertTrue(matchesSessionQuery(compose, "Compse"))
        assertTrue(matchesSessionQuery(richErrors, "Kotln"))
        val kotlinTalks = sessions.filter { it.track == Track.KOTLIN }
        assertTrue(richErrors in kotlinTalks)
        assertTrue(kotlinServers in kotlinTalks)
    }
}
