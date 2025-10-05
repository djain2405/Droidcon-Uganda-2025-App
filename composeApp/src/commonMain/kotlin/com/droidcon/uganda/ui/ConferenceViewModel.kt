package com.droidcon.uganda.ui

import androidx.lifecycle.ViewModel
import com.droidcon.uganda.data.LocalDataSource
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.Speaker
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class ConferenceViewModel : ViewModel() {

    private val _favoriteSessionIds = MutableStateFlow<Set<String>>(emptySet())
    val favoriteSessionIds: StateFlow<Set<String>> = _favoriteSessionIds.asStateFlow()

    val sessions: List<Session> = LocalDataSource.sessions
    val speakers: List<Speaker> = LocalDataSource.speakers

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
}
