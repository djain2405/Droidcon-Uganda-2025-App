package com.droidcon.uganda.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "droidcon_preferences")

actual fun createDataStore(): DataStore<Preferences> {
    val context = com.droidcon.uganda.AppContext.get()
    return context.dataStore
}
