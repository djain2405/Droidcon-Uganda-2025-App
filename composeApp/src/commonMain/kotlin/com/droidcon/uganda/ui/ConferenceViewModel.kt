package com.droidcon.uganda.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.droidcon.uganda.data.ConferenceRepository
import com.droidcon.uganda.data.Session
import com.droidcon.uganda.data.Speaker
import com.droidcon.uganda.data.Track
import com.droidcon.uganda.data.TypesenseConfig
import com.droidcon.uganda.data.matchesSessionQuery
import com.droidcon.uganda.data.typesenseAdminApiKey
import com.droidcon.uganda.utils.TimeZoneUtils
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.isActive
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

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedTrack = MutableStateFlow<Track?>(null)
    val selectedTrack: StateFlow<Track?> = _selectedTrack.asStateFlow()

    // Null means Typesense is not constraining results (empty query, not configured, or request failed).
    private val _typesenseMatchIds = MutableStateFlow<List<String>?>(null)
    val typesenseMatchIds: StateFlow<List<String>?> = _typesenseMatchIds.asStateFlow()
    private var searchJob: Job? = null

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
            .mapNotNull { session -> session.startTime?.let { TimeZoneUtils.getDateKey(it) } }
            .distinct()
            .sorted()

    init {
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch {
            loadDataSuspend()
        }
    }

    private suspend fun loadDataSuspend() {
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

        val adminKey = typesenseAdminApiKey()
        if (adminKey.isNotBlank()) {
            repository.indexSessions(sessions, adminKey)
        }
        refreshTypesenseSearch()
    }

    fun refreshData() {
        viewModelScope.launch {
            _isRefreshing.value = true
            loadDataSuspend()
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
        refreshTypesenseSearch()
    }

    fun updateSearchQuery(query: String) {
        _searchQuery.value = query
        refreshTypesenseSearch()
    }

    fun clearSearch() {
        _searchQuery.value = ""
        refreshTypesenseSearch()
    }

    fun selectTrack(track: Track?) {
        _selectedTrack.value = track
        refreshTypesenseSearch()
    }

    private fun refreshTypesenseSearch() {
        searchJob?.cancel()
        val query = _searchQuery.value.trim()
        if (query.isEmpty() || !TypesenseConfig.isConfigured) {
            _typesenseMatchIds.value = null
            return
        }
        // Drop the previous id list so a new query cannot briefly show the old hits.
        _typesenseMatchIds.value = null
        searchJob = viewModelScope.launch {
            val ids = repository.searchSessions(query)
            if (isActive && _searchQuery.value.trim() == query) {
                _typesenseMatchIds.value = ids
            }
        }
    }

    // Day and track stay on-device. A non-empty query uses Typesense order when the request succeeds.
    fun getFilteredSessions(): List<Session> {
        val day = _selectedDay.value
        val query = _searchQuery.value.trim()
        val track = _selectedTrack.value
        val remoteIds = _typesenseMatchIds.value

        var filtered = sessions

        if (query.isNotEmpty() && remoteIds != null) {
            val byId = filtered.associateBy { it.id }
            filtered = remoteIds.mapNotNull { byId[it] }
        }

        if (day != null) {
            filtered = filtered.filter { session ->
                session.startTime?.let { TimeZoneUtils.getDateKey(it) } == day
            }
        }

        if (track != null) {
            filtered = filtered.filter { it.track == track }
        }

        if (query.isNotEmpty() && remoteIds == null) {
            filtered = filtered.filter { session -> matchesSessionQuery(session, query) }
        }

        return filtered
    }

    override fun onCleared() {
        super.onCleared()
        repository.close()
    }
}
