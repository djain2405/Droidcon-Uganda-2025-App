package com.droidcon.uganda.data

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences

/**
 * Expect/Actual pattern for creating DataStore instance
 */
expect fun createDataStore(): DataStore<Preferences>
