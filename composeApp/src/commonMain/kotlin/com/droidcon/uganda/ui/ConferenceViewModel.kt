package com.droidcon.uganda.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.uganda.data.ConferenceRepository
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.Speaker
import com.droidcon.uganda.utils.TimeZoneUtils
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.launch

sealed class UiState<out T> {
    data object Loading : UiState<Nothing>()
    data class Success<T>(val data: T) : UiState<T>()
    data class Error(val message: String) : UiState<Nothing>()
}

class ConferenceViewModel(
    private val repository: ConferenceRepository = ConferenceRepository(),
    private val favoritesDataStore: com.droidcon.uganda.data.FavoritesDataStore =
        com.droidcon.uganda.data.FavoritesDataStore(com.droidcon.uganda.data.createDataStore())
) : ViewModel() {

    // Expose favorites from DataStore
    val favoriteSessionIds: StateFlow<Set<String>> = favoritesDataStore.favoritesFlow
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptySet()
        )

    private val _selectedDay = MutableStateFlow<String?>(null)
    val selectedDay: StateFlow<String?> = _selectedDay.asStateFlow()

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing: StateFlow<Boolean> = _isRefreshing.asStateFlow()

    private val _sessionsState = MutableStateFlow<UiState<List<Session>>>(UiState.Loading)
    val sessionsState: StateFlow<UiState<List<Session>>> = _sessionsState.asStateFlow()

    private val _speakersState = MutableStateFlow<UiState<List<Speaker>>>(UiState.Loading)
    val speakersState: StateFlow<UiState<List<Speaker>>> = _speakersState.asStateFlow()

    // Public accessors for current data
    val sessions: List<Session>
        get() = (_sessionsState.value as? UiState.Success)?.data ?: emptyList()

    val speakers: List<Speaker>
        get() = (_speakersState.value as? UiState.Success)?.data ?: emptyList()

    // Get all unique conference days
    val conferenceDays: List<String>
        get() = sessions
            .map { TimeZoneUtils.getDateKey(it.startTime) }
            .distinct()
            .sorted()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            // Load sessions
            _sessionsState.value = UiState.Loading
            val sessionsResult = repository.getSessions()
            _sessionsState.value = sessionsResult.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = {
                    // Fallback to local data on error
                    UiState.Success(repository.getLocalSessions())
                }
            )

            // Load speakers
            _speakersState.value = UiState.Loading
            val speakersResult = repository.getSpeakers()
            _speakersState.value = speakersResult.fold(
                onSuccess = { UiState.Success(it) },
                onFailure = {
                    // Fallback to local data on error
                    UiState.Success(repository.getLocalSpeakers())
                }
            )
        }
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadData()
            _isRefreshing.value = false
        }
    }

    fun toggleFavorite(sessionId: String) {
        viewModelScope.launch {
            favoritesDataStore.toggleFavorite(sessionId)
        }
    }

    fun isFavorite(sessionId: String): Boolean {
        return sessionId in favoriteSessionIds.value
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

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}
