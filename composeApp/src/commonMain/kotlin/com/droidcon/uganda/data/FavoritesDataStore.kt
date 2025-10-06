package com.droidcon.uganda.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringSetPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class FavoritesDataStore(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val FAVORITES_KEY = stringSetPreferencesKey("favorite_sessions")
    }

    /**
     * Get favorites as a Flow
     */
    val favoritesFlow: Flow<Set<String>> = dataStore.data
        .map { preferences ->
            preferences[FAVORITES_KEY] ?: emptySet()
        }

    /**
     * Add a session to favorites
     */
    suspend fun addFavorite(sessionId: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites + sessionId
        }
    }

    /**
     * Remove a session from favorites
     */
    suspend fun removeFavorite(sessionId: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = currentFavorites - sessionId
        }
    }

    /**
     * Toggle favorite status
     */
    suspend fun toggleFavorite(sessionId: String) {
        dataStore.edit { preferences ->
            val currentFavorites = preferences[FAVORITES_KEY] ?: emptySet()
            preferences[FAVORITES_KEY] = if (sessionId in currentFavorites) {
                currentFavorites - sessionId
            } else {
                currentFavorites + sessionId
            }
        }
    }

    /**
     * Clear all favorites
     */
    suspend fun clearFavorites() {
        dataStore.edit { preferences ->
            preferences.remove(FAVORITES_KEY)
        }
    }
}
