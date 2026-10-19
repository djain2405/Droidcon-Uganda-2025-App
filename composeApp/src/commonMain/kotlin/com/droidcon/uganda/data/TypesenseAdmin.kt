package com.droidcon.uganda.data

/**
 * Admin key is present on debug builds only. Android release and iOS release return an empty string.
 */
internal expect fun typesenseAdminApiKey(): String
