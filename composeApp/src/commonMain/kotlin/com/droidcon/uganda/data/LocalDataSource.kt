package com.droidcon.uganda.data

import com.droidcon.uganda.utils.TimeZoneUtils

object LocalDataSource {

    // Conference date in EAT timezone
    private const val CONFERENCE_DATE = "2025-08-15"

    val speakers = listOf(
        Speaker(
            id = "1",
            name = "Sarah Okello",
            title = "Senior Android Engineer",
            company = "Jumia Uganda",
            bio = "Sarah is a passionate Android developer with 8 years of experience building scalable mobile applications. She's a Google Developer Expert and loves mentoring upcoming developers.",
            imageUrl = "👩🏿‍💻",
            twitter = "@sarahokello"
        ),
        Speaker(
            id = "2",
            name = "David Mukasa",
            title = "Mobile Architect",
            company = "SafeBoda",
            bio = "David specializes in Kotlin Multiplatform and has been instrumental in building cross-platform solutions. He's an advocate for clean architecture and testing.",
            imageUrl = "👨🏿‍💻",
            linkedin = "davidmukasa"
        ),
        Speaker(
            id = "3",
            name = "Grace Namugga",
            title = "UX Engineer",
            company = "MTN Uganda",
            bio = "Grace bridges the gap between design and development, creating beautiful and accessible user experiences for millions of users across Africa.",
            imageUrl = "👩🏿‍🎨",
            twitter = "@gracenamugga"
        ),
        Speaker(
            id = "4",
            name = "John Ssemwogerere",
            title = "Cloud Solutions Architect",
            company = "Andela",
            bio = "John helps teams build robust backend systems that power mobile applications. He's an expert in Firebase, AWS, and microservices architecture.",
            imageUrl = "👨🏿‍🔬",
            linkedin = "johnssemwogerere"
        ),
        Speaker(
            id = "5",
            name = "Rebecca Nakitto",
            title = "Kotlin Evangelist",
            company = "JetBrains",
            bio = "Rebecca travels the world teaching developers about Kotlin's latest features. She's particularly interested in Kotlin Multiplatform and Compose.",
            imageUrl = "👩🏿‍🏫",
            twitter = "@rebeccanakitto"
        ),
        Speaker(
            id = "6",
            name = "Alex Kateregga",
            title = "Android Lead",
            company = "Flutterwave",
            bio = "Alex leads the Android team at Flutterwave, building payment solutions used across Africa. He's passionate about performance optimization and security.",
            imageUrl = "👨🏿‍💼",
            twitter = "@alexkateregga"
        )
    )

    val sessions = listOf(
        Session(
            id = "s1",
            title = "Registration & Check-In",
            description = "Attendee check-in, badge collection, networking over breakfast. Sponsor booths open for early networking.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "07:30"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "09:00"),
            speaker = null,  // No specific speaker for registration
            track = Track.KEYNOTE,
            room = "Auditorium",
            level = null  // No difficulty level for registration
        ),
        Session(
            id = "s2",
            title = "Building Scalable Apps with Jetpack Compose",
            description = "Learn how to build production-ready applications using Jetpack Compose. We'll cover best practices, state management, performance optimization, and how to structure your compose code for scalability.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "10:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "10:45"),
            speaker = speakers[0],
            track = Track.ANDROID,
            room = "Room A",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s3",
            title = "Kotlin Multiplatform: Write Once, Run Everywhere",
            description = "Discover how Kotlin Multiplatform Mobile (KMM) can help you share business logic between Android and iOS while maintaining native UIs. We'll build a real app from scratch!",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "10:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "10:45"),
            speaker = speakers[1],
            track = Track.KOTLIN,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s4",
            title = "Designing Accessible Mobile Experiences",
            description = "Accessibility isn't optional. Learn how to design and build apps that work for everyone, including users with disabilities. We'll cover Android accessibility APIs, testing tools, and inclusive design principles.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "11:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "11:45"),
            speaker = speakers[2],
            track = Track.DESIGN,
            room = "Room A",
            level = SessionLevel.BEGINNER
        ),
        Session(
            id = "s5",
            title = "Firebase: Beyond the Basics",
            description = "Firebase is more than just a database! Explore advanced Firebase features including Cloud Functions, Remote Config, A/B testing, and how to build a complete backend for your Android app.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "11:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "11:45"),
            speaker = speakers[3],
            track = Track.CLOUD,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s6",
            title = "Mastering Coroutines and Flow",
            description = "Asynchronous programming made easy! Deep dive into Kotlin Coroutines and Flow. Learn how to handle background tasks, network calls, and reactive data streams like a pro.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "13:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "13:45"),
            speaker = speakers[4],
            track = Track.KOTLIN,
            room = "Room A",
            level = SessionLevel.ADVANCED
        ),
        Session(
            id = "s7",
            title = "Material Design 3 in Action",
            description = "Explore Material Design 3 (Material You) and learn how to create dynamic, personalized UIs that adapt to user preferences. We'll implement custom themes, dynamic colors, and beautiful animations.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "13:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "13:45"),
            speaker = speakers[2],
            track = Track.DESIGN,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s8",
            title = "App Security Best Practices",
            description = "Security is crucial! Learn how to protect your app and user data. We'll cover encryption, secure storage, API security, ProGuard/R8, and common security vulnerabilities to avoid.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "14:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "14:45"),
            speaker = speakers[5],
            track = Track.ANDROID,
            room = "Room A",
            level = SessionLevel.ADVANCED
        ),
        Session(
            id = "s9",
            title = "Building Offline-First Apps",
            description = "Network connectivity isn't guaranteed in many parts of Africa. Learn how to build apps that work seamlessly offline using Room, WorkManager, and sync strategies.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "14:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "14:45"),
            speaker = speakers[0],
            track = Track.ANDROID,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s10",
            title = "Performance Optimization Techniques",
            description = "Make your app lightning fast! Learn profiling techniques, memory optimization, reducing APK size, improving startup time, and delivering smooth 60fps experiences even on low-end devices.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "15:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "15:45"),
            speaker = speakers[5],
            track = Track.ANDROID,
            room = "Room A",
            level = SessionLevel.ADVANCED
        ),
        Session(
            id = "s11",
            title = "Serverless Architecture for Mobile Apps",
            description = "Build scalable backends without managing servers! Learn about serverless architectures using Cloud Functions, AWS Lambda, and how they integrate with your Android apps.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "15:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "15:45"),
            speaker = speakers[3],
            track = Track.CLOUD,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s12",
            title = "Community Panel: The Future of Android in Uganda",
            description = "Join leading Android developers from Uganda's tech ecosystem for an open discussion about challenges, opportunities, and how we can grow our community together.",
            startTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "16:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(CONFERENCE_DATE, "16:45"),
            speaker = speakers[0],
            track = Track.KEYNOTE,
            room = "Main Hall",
            level = SessionLevel.BEGINNER
        )
    )
}
