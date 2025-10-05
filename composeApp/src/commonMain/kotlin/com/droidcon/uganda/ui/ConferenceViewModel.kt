package com.droidcon.uganda.ui

import androidx.lifecycle.ViewModel
import com.droidcon.uganda.data.LocalDataSource
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.Speaker
import com.droidcon.uganda.utils.TimeZoneUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConferenceViewModel : ViewModel() {

    private val _favoriteSessionIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteSessionIds: StateFlow<Set<String>> = _favoriteSessionIds.asStateFlow()

    private val _selectedDay = MutableStateFlow<String?>(null)
    val selectedDay: StateFlow<String?> = _selectedDay.asStateFlow()

    val sessions: List<Session> = LocalDataSource.sessions
    val speakers: List<Speaker> = LocalDataSource.speakers

    // Get all unique conference days
    val conferenceDays: List<String> by lazy {
        sessions
            .map { TimeZoneUtils.getDateKey(it.startTime) }
            .distinct()
            .sorted()
    }

    fun toggleFavorite(sessionId: String) {
        _favoriteSessionIds.update { currentFavorites ->
            if (sessionId in currentFavorites) {
                currentFavorites - sessionId
            } else {
                currentFavorites + sessionId
            }
        }
    }

    fun isFavorite(sessionId: String): Boolean {
        return sessionId in _favoriteSessionIds.value
    }

    fun selectDay(day: String?) {
        _selectedDay.value = day
    }

    // Get sessions filtered by selected day (null = all days)
    fun getFilteredSessions(): List<Session> {
        val day = _selectedDay.value
        return if (day == null) {
            sessions
        } else {
            sessions.filter { TimeZoneUtils.getDateKey(it.startTime) == day }
        }
    }
}
