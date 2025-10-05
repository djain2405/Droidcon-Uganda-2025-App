# How to Update the Agenda with Real DroidCon Uganda Data

Since the DroidCon Uganda website (https://www.uganda.droidcon.com/agenda) uses dynamic JavaScript content, we need to manually extract the data.

## Option 1: Copy Session Data

1. Visit https://www.uganda.droidcon.com/agenda
2. Copy the session information (titles, times, speakers, descriptions)
3. Paste it here and I'll update the `LocalDataSource.kt` file

## Option 2: Update Manually

Edit the file: `composeApp/src/commonMain/kotlin/com/droidcon/uganda/data/LocalDataSource.kt`

### Data Structure:

```kotlin
// Conference date in EAT timezone
private const val CONFERENCE_DATE = "2025-08-15"  // Update with actual date

val speakers = listOf(
    Speaker(
        id = "unique-id",
        name = "Speaker Name",
        title = "Job Title",
        company = "Company Name",
        bio = "Bio text...",
        imageUrl = "emoji or URL",  // Use emoji like "👨🏿‍💻" or actual image URL
        twitter = "@handle",  // Optional
        linkedin = "username"  // Optional
    ),
    // Add more speakers...
)

val sessions = listOf(
    Session(
        id = "session-id",
        title = "Session Title",
        description = "Session description...",
        startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "09:00"),  // HH:mm format
        endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "09:45"),
        speaker = speakers[0],  // Reference to speaker
        track = Track.ANDROID,  // ANDROID, KOTLIN, DESIGN, CLOUD, or KEYNOTE
        room = "Room Name",
        level = SessionLevel.INTERMEDIATE  // BEGINNER, INTERMEDIATE, or ADVANCED
    ),
    // Add more sessions...
)
```

## Current Tracks Available:

- `Track.ANDROID` - Android (green)
- `Track.KOTLIN` - Kotlin (blue)
- `Track.DESIGN` - Design & UX (orange)
- `Track.CLOUD` - Cloud & Backend (purple)
- `Track.KEYNOTE` - Keynote (red)

## Session Levels:

- `SessionLevel.BEGINNER`
- `SessionLevel.INTERMEDIATE`
- `SessionLevel.ADVANCED`

## Example Update:

```kotlin
Session(
    id = "real-session-1",
    title = "Building Production-Ready Android Apps",
    description = "Learn best practices...",
    startTime = TimeZoneUtils.createConferenceDateTime("2025-08-15", "10:30"),
    endTime = TimeZoneUtils.createConferenceDateTime("2025-08-15", "11:15"),
    speaker = speakers.first { it.name == "Speaker Name" },
    track = Track.ANDROID,
    room = "Main Hall",
    level = SessionLevel.INTERMEDIATE
)
```

All times will be automatically converted to the user's local timezone!
