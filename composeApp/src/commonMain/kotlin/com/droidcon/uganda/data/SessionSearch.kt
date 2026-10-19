package com.droidcon.uganda.data

/**
 * On-device typo tolerance for the keynote search demo.
 * Limits match Typesense defaults: no typos under 4 characters,
 * 1 typo at 4–6 characters, 2 typos at 7 or more.
 */
internal fun matchesSessionQuery(session: Session, query: String): Boolean {
    val tokens = query.trim().lowercase().split(Regex("\\s+")).filter { it.isNotEmpty() }
    if (tokens.isEmpty()) return true
    val words = searchWords(session)
    return tokens.all { token -> words.any { word -> wordMatches(token, word) } }
}

private fun searchWords(session: Session): List<String> {
    val fields = listOfNotNull(
        session.title,
        session.speaker?.name,
        session.track.displayName.takeIf { it.isNotEmpty() }
    )
    return fields.flatMap { field ->
        field.lowercase().split(Regex("[^a-z0-9+]+")).filter { it.isNotEmpty() }
    }
}

internal fun wordMatches(token: String, word: String): Boolean {
    if (token.isEmpty()) return true
    if (word.contains(token)) return true
    val maxEdits = when {
        token.length < 4 -> 0
        token.length < 7 -> 1
        else -> 2
    }
    if (maxEdits == 0) return false
    if (kotlin.math.abs(word.length - token.length) <= maxEdits &&
        editDistanceWithin(token, word, maxEdits)
    ) {
        return true
    }
    // A typo on the first mismatched character, before the word is finished.
    val minLength = (token.length - maxEdits).coerceAtLeast(1)
    val maxLength = (token.length + maxEdits).coerceAtMost(word.length)
    for (length in minLength..maxLength) {
        if (length == word.length) continue
        if (editDistanceWithin(token, word.substring(0, length), maxEdits)) return true
    }
    return false
}

private fun editDistanceWithin(left: String, right: String, maxDistance: Int): Boolean {
    if (kotlin.math.abs(left.length - right.length) > maxDistance) return false
    var previous = IntArray(right.length + 1) { it }
    var current = IntArray(right.length + 1)
    for (i in 1..left.length) {
        current[0] = i
        var rowMin = current[0]
        for (j in 1..right.length) {
            val cost = if (left[i - 1] == right[j - 1]) 0 else 1
            current[j] = minOf(
                previous[j] + 1,
                current[j - 1] + 1,
                previous[j - 1] + cost
            )
            if (current[j] < rowMin) rowMin = current[j]
        }
        if (rowMin > maxDistance) return false
        val swap = previous
        previous = current
        current = swap
    }
    return previous[right.length] <= maxDistance
}
