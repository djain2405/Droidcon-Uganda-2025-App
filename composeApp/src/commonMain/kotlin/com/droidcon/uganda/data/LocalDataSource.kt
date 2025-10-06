package com.droidcon.uganda.data

import com.droidcon.uganda.utils.TimeZoneUtils

object LocalDataSource {

    // Conference dates in EAT timezone
    private const val DAY_1 = "2025-11-10"  // Monday
    private const val DAY_2 = "2025-08-16"  // Saturday

    val speakers = listOf(
        Speaker(
            id = "1",
            name = "Ahmed Nabil",
            title = "Senior Android Engineer",
            company = "Jumia Uganda",
            bio = "Sarah is a passionate Android developer with 8 years of experience building scalable mobile applications. She's a Google Developer Expert and loves mentoring upcoming developers.",
            imageUrl = "👩🏿‍💻",
            twitter = "@sarahokello"
        ),
        Speaker(
            id = "2",
            name = "Anselmo Alexandre",
            title = "Mobile Architect",
            company = "SafeBoda",
            bio = "David specializes in Kotlin Multiplatform and has been instrumental in building cross-platform solutions. He's an advocate for clean architecture and testing.",
            imageUrl = "👨🏿‍💻",
            linkedin = "davidmukasa"
        ),
        Speaker(
            id = "3",
            name = "Jamiu Okanlawon",
            title = "UX Engineer",
            company = "MTN Uganda",
            bio = "Grace bridges the gap between design and development, creating beautiful and accessible user experiences for millions of users across Africa.",
            imageUrl = "👩🏿‍🎨",
            twitter = "@gracenamugga"
        ),
        Speaker(
            id = "4",
            name = "Sam Aricha",
            title = "Cloud Solutions Architect",
            company = "Andela",
            bio = "John helps teams build robust backend systems that power mobile applications. He's an expert in Firebase, AWS, and microservices architecture.",
            imageUrl = "👨🏿‍🔬",
            linkedin = "johnssemwogerere"
        ),
        Speaker(
            id = "5",
            name = "Georges Byona",
            title = "Kotlin Evangelist",
            company = "JetBrains",
            bio = "Rebecca travels the world teaching developers about Kotlin's latest features. She's particularly interested in Kotlin Multiplatform and Compose.",
            imageUrl = "👩🏿‍🏫",
            twitter = "@rebeccanakitto"
        ),
        Speaker(
            id = "6",
            name = "Joshua Musyoki",
            title = "Android Lead",
            company = "Flutterwave",
            bio = "Alex leads the Android team at Flutterwave, building payment solutions used across Africa. He's passionate about performance optimization and security.",
            imageUrl = "👨🏿‍💼",
            twitter = "@alexkateregga"
        )
    )

    val sessions = listOf(
        // DAY 1 - Friday, August 15
        Session(
            id = "s1",
            title = "Registration & Check-In",
            description = "Attendee check-in, badge collection, networking over breakfast. Sponsor booths open for early networking.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "07:30"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "09:00"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s2",
            title = "Welcome Address",
            description = "Keynote Introductory",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "09:20"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "09:55"),
            speaker = null,
            track = Track.KEYNOTE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s3",
            title = "Dynamic UI with Compose and Firebase",
            description = "Know how to provide a dynamic UI and app features using firebase and Jetpack Compose",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "10:15"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "10:55"),
            speaker = speakers[0],
            track = Track.ANDROID,
            room = "Auditorium",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s4",
            title = "Break",
            description = "Break",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "10:55"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "11:10"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s5",
            title = "Kotlin Rich Errors: Transforming Errors into Actionable project insights.",
            description = "During this talk, we will:\n" +
                    "- Understand Error Types: Distinguishing between different types of errors in Kotlin;\n" +
                    "- Create Rich Error Models: Designing error classes that carry context, such as error codes, messages, and potential resolutions;\n" +
                    "- Transform Errors into Insights: Techniques to analyse errors for trends, common issues, and user feedback to enhance development processes.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "11:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "11:55"),
            speaker = speakers[1],
            track = Track.KOTLIN,
            room = "Room B",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s6",
            title = "Dart on the Server: Unlocking Full-Stack Potential",
            description = "Looking for a modern, efficient language for your next API project? This session spotlights Dart as a powerful yet underutilized server-side language that delivers exceptional performance and developer experience for API development.\n" +
                    "\n" +
                    "Discover how Dart's built-in async/await, strong typing, and robust standard library make it uniquely suited for creating reliable, maintainable APIs. We'll explore practical implementations using server frameworks like Shelf, demonstrate effective patterns for structuring API endpoints, and showcase how Dart's inherent strengths address common API development challenges.\n" +
                    "\n" +
                    "You'll learn how Dart enables rapid development cycles while maintaining type safety, see real-world examples of REST implementations, and understand deployment strategies for production environments. Whether you're a backend engineer evaluating new technologies or an API architect seeking productivity gains, this talk provides valuable insights into how Dart can enhance your server-side development toolkit.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "11:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "11:55"),
            speaker = speakers[2],
            track = Track.KOTLIN,
            room = "Visual Spaces",
            level = SessionLevel.ADVANCED
        ),
        Session(
            id = "s7",
            title = "Breaking the HTTP Habit: Kotlin's Journey to AI-First Servers",
            description = "HTTP served us well, but AI demands better. Follow Kotlin's evolution from HTTP servers to MCP-powered AI infrastructure that communicates the way artificial intelligence actually thinks.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:05"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:50"),
            speaker = speakers[3],
            track = Track.DESIGN,
            room = "Auditorium",
            level = SessionLevel.INTERMEDIATE
        ),
        Session(
            id = "s8",
            title = "Build an interpreter companion on your Smartwatch with FlutterFire and Gemini",
            description = "A demo session during which we will discover all Gemini API's capabilities, from text capability to Live potential!\n" +
                    "\n" +
                    "A session designed for cross-platform developers, particularly Flutter developers, which will cover the following points:\n" +
                    "1. Flutter's cross-platform capability and performance\n" +
                    "2. Seamless and fast integration of Gemini with Flutter\n" +
                    "3. Exploration of powerful Flutter packages with Firebase and on WearOS, such as firebase_ai, wear_plus, wear_connectivity, etc.\n" +
                    "4. Harness all the latest power of Gemini with features like Live Streaming and more.\n" +
                    "\n" +
                    "Without limiting the points above, this is just a glimpse of what will be covered during the session; however, more discoveries will be made during this comprehensive session, which covers recent, current, and requested features for Flutter developers.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:05"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:50"),
            speaker = speakers[0],
            track = Track.KEYNOTE,
            room = "Visual Spaces",
            level = SessionLevel.BEGINNER
        ),
        Session(
            id = "s9",
            title = "Lunch Break Buffet lunch & networking Sponsor & community showcase",
            description = "",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "1:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:00"),
            speaker = null,
            track = Track.KEYNOTE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s10",
            title = "CI/CD the Right Way: Accelerating Android Development with Smart Infrastructure",
            description = "With growth of our Android teams, complexities of collaboration, testing, and releasing at scale arise. In this talk we will dive into how to design and implement CI/CD pipelines and infrastructure tailored for Android engineering, from first commit to production rollout.\n" +
                    "We’ll explore practical strategies to speed up build times, enforce quality gates, manage test reliability, and reduce friction in release workflows. We’ll learn how to integrate tools like GitHub Actions, Bitbucket, Bitrise, Firebase App Distribution, and Gradle caching for faster feedback loops, as well as infrastructure patterns to support modular codebases, feature toggles, and canary releases. We will also learn how to integrate and automate Jira-Bitbucket/GitHub workflows.\n" +
                    "Whether you're working in a startup or an enterprise Android team, you'll walk away with actionable ideas to boost your development velocity while keeping confidence high. Let’s break down what it really takes to build CI/CD pipelines that scale with your team and product.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:45"),
            speaker = speakers[5],
            track = Track.CLOUD,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s11",
            title = "Build Spectacular TV Apps with Flutter",
            description = "Flutter for Mobile is released, Flutter for Web is released, Flutter for macOS, Linux, and Windows is in also released and it's really cool that Flutter officially supports six platforms. But what if I want to run my Flutter app on the TV? Unfortunately, nobody has heard of the official Flutter for TV yet. Actually, a Flutter app can be launched on TV (not AndroidTV only, but AppleTV, Tizen, and others as well). In my talk, I'm going to tell you how to do this, what difficulties and issues you will face, and how to resolve them.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:45"),
            speaker = null,
            track = Track.CLOUD,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s12",
            title = "Break",
            description = "Break",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:45"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:00"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s13",
            title = "Myths That May Be Holding You Back from Pursuing Engineering Leadership",
            description = "When exploring leadership career paths, there might be some myths holding engineers back, some of which are completely baseless. My talk will cover such myths,\n" +
                    "\n" +
                    "Myth number 1: I need to increase my visibility (the worst advice one can give to someone to explore the leadership career path is ‘increase your visibility’) I will explain with examples how it’s an absolute myth\n" +
                    "Myth number 2: Only extroverts can succeed as managers (I will explain what are main areas of success for a manager and how being extrovert isn’t one of them)\n" +
                    "Myth number 3: I’m expected to never say no (I will explain how saying No to requests from outside the time can help a great deal in success of a manager and how saying yes all the time can be dangerous)\n" +
                    "Myth number 4: I’ll spend most of my time in meetings (I will explain how this role can influence a lot of changes on ways working in / out of a team including the number of meetings)\n" +
                    "[Bonus] Self Doubt: I’ll be viewed as technically less competent (I will challenge this notion with examples)\n" +
                    "\n" +
                    "Key takeaways and learning points\n" +
                    "Deeper understanding on what actually is a job of an engineering manager, what are the key skills required for the role, what are they key areas that will make the person successful\n" +
                    "\n" +
                    "Experience level with the topic\n" +
                    "Expert, I am head of engineering in my current role and hence I have several years of experience delivering features and leading teams.\n" +
                    "In addition to my experience, I believe that my perspective as a woman can contribute meaningfully to this topic. I will do this talk in the Droidcon Berlin this year as well.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:45"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s14",
            title = "Supercharging Android Apps with On-Device AI: Gemini Nano & MediaPipe LLM Inference",
            description = "The mobile AI revolution is increasingly moving on-device, driven by demands for privacy, low latency, and offline capability. In this session, I’ll demonstrate how to leverage cutting-edge on-device AI tools—including Gemini Nano, Google Edge AI SDK, and MediaPipe LLM Inference APIs—to build intelligent Android apps entirely in Kotlin and Jetpack Compose.\n" +
                    "\n" +
                    "Key Takeaways:\n" +
                    "\n" +
                    "- On-Device AI Landscape: Understand the shift from cloud to on-device AI, the privacy benefits, and real-world use cases for features like smart reply, summarization, image analysis, and more.\n" +
                    "- Getting Started with Gemini Nano: Walk through integrating Google’s Gemini Nano generative model into a modern Android app, highlighting both ML Kit GenAI APIs and the experimental AI Edge SDK for custom scenarios.\n" +
                    "- Beyond Gemini: MediaPipe, LiteRT, and Custom Models: Explore the MediaPipe ecosystem for LLM (large language model) inference on-device, and how to bring your own models using LiteRT/TensorFlow Lite for specialized tasks.\n" +
                    "- Jetpack Compose + Kotlin: LLM-Driven UI Generation: Discover how reactive UI development with Compose and Kotlin Coroutines enables real-time, AI-powered experiences. We’ll demonstrate our internal LLM-based UI generation framework, which builds dynamic, schema-driven UIs on top of Compose—enabling adaptable interfaces generated entirely from structured prompts.\n" +
                    "- Production Considerations: Address model size, device compatibility, privacy, and performance optimization—lessons learned from deploying AI features at scale in consumer and enterprise Android apps.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:45"),
            speaker = null,
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s15",
            title = "Break",
            description = "Break",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "3:45"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:00"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s16",
            title = "Converged IAM in the Cloud Era: Unlocking Security, Compliance, and Agility for Modern Enterprises",
            description = "As enterprises embrace digital transformation, identity has become both the backbone of innovation and a primary target for cyber threats. Traditional siloed Identity and Access Management (IAM) systems often create inefficiencies, compliance headaches, and heightened risks challenges that are only magnified in cloud-first environments.\n" +
                    "\n" +
                    "This session introduces Converged IAM a unified approach that brings together Access Management, Identity Governance and Administration, and Privileged Access Management into a single, cloud-native platform. Through real-world case studies, including healthcare implementations, we’ll explore how convergence strengthens security, streamlines compliance, and boosts operational efficiency.\n" +
                    "\n" +
                    "Attendees will learn how AI-driven automation, consistent policy enforcement, and simplified integrations are enabling enterprises to move faster while reducing risk. Beyond security and compliance, we’ll highlight the measurable business value of converged IAM from lowering operational costs to accelerating digital transformation ROI.\n" +
                    "\n" +
                    "Finally, the session will share proven best practices and a practical roadmap for successful implementation. Leaders will walk away with clear strategies for positioning identity management as a strategic driver of resilience, regulatory readiness, and sustainable growth in today’s cloud era.\n" +
                    "\n",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:45"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s17",
            title = "A Deep Dive into WineSom, an Agentic Flutter App",
            description = "The term “agentic app” is everywhere these days but what does it mean for us as app developers? In this talk I look at what makes a Flutter application not only AI enabled but agentic through the example of a quirky wine themed chat app.\n" +
                    "I will demonstrate how WineSom, an agentic Flutter app, leverages the firebase_ai interface to enable dynamic, context-aware multi-turn chat conversations with an LLM of choice. True agentic behaviour is reached through the strategic integration of function calls, which empower the AI model to autonomously identify the need for, and orchestrate actions such as the retrieval of real-time proprietary data.\n" +
                    "The resulting WineSom app succeeds at engaging the user in a meaningful conversation about wine, while contributing factual information about available stock that is not in the public domain.\n" +
                    "\n",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:45"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s18",
            title = "How to make better agents ?",
            description = "In this talk I would like share about all my learning which I got while building my own agent at itsloki.com.\n" +
                    "The talk will mainly focus on pattern around how to solve the most common problem that arise when dealing with complex agentic systems. For example how to minimize hallucinations when dealing with multiple tools , common patterns/tips when dealing with sub agentic and mulitple agentic flows.\n" +
                    "\n" +
                    "This talk should give a very good idea to the audience about desinging agents",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:45"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "5:30"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s19",
            title = "AI-Augmented Master Data Governance in Healthcare: Smarter Stewardship with SAP S/4HANA",
            description = "Healthcare enterprises operate in one of the most data-intensive and regulated industries, where the accuracy of product, patient, and supplier data directly impacts compliance, patient safety, and speed to market. Yet, master data is often riddled with duplicates, inconsistencies, and regulatory gaps.\n" +
                    "\n" +
                    "This session demonstrates how AI-powered Master Data Governance (MDG), integrated with SAP S/4HANA, can transform enterprise data stewardship. We will explore how machine learning and natural language processing can automatically validate master data quality, detect duplicates, and proactively surface inconsistencies in critical product datasets. By embedding predictive data scoring and intelligent anomaly detection, the framework reduces manual intervention, accelerates new product introductions, and ensures adherence to FDA and EMA requirements.\n" +
                    "\n" +
                    "Drawing on real-world healthcare enterprise deployments, we highlight measurable outcomes—reduced remediation efforts, improved compliance, and faster time-to-market for regulated products. Attendees will leave with actionable strategies for:\n" +
                    "\n" +
                    "Embedding AI into MDG workflows for proactive governance.\n" +
                    "\n" +
                    "Prioritizing high-risk anomalies with intelligent recommendations.\n" +
                    "\n" +
                    "Designing enterprise-grade data stewardship systems that are not just compliant, but business-ready and innovation-ready.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "4:45"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "5:30"),
            speaker = null,
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s20",
            title = "Closing Notes & Day Wrap-Up\n",
            description = "Announcements for Day 2 / Hackathon Networking continues at booths",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "5:30"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "6:30"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        // DAY 2 - Saturday, August 16
//        Session(
//            id = "s9",
//            title = "Day 2 Registration & Breakfast",
//            description = "Morning check-in for Day 2 attendees. Coffee, tea, and light breakfast available.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "08:00"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:00"),
//            speaker = null,
//            track = Track.KEYNOTE,
//            room = "Auditorium",
//            level = null
//        ),
//        Session(
//            id = "s10",
//            title = "App Security Best Practices",
//            description = "Security is crucial! Learn how to protect your app and user data. We'll cover encryption, secure storage, API security, ProGuard/R8, and common security vulnerabilities to avoid.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:30"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:15"),
//            speaker = speakers[5],
//            track = Track.ANDROID,
//            room = "Room A",
//            level = SessionLevel.ADVANCED
//        ),
//        Session(
//            id = "s11",
//            title = "Building Offline-First Apps",
//            description = "Network connectivity isn't guaranteed in many parts of Africa. Learn how to build apps that work seamlessly offline using Room, WorkManager, and sync strategies.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:30"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:15"),
//            speaker = speakers[0],
//            track = Track.ANDROID,
//            room = "Room B",
//            level = SessionLevel.INTERMEDIATE
//        ),
//        Session(
//            id = "s12",
//            title = "Performance Optimization Techniques",
//            description = "Make your app lightning fast! Learn profiling techniques, memory optimization, reducing APK size, improving startup time, and delivering smooth 60fps experiences even on low-end devices.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:30"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:15"),
//            speaker = speakers[5],
//            track = Track.ANDROID,
//            room = "Room A",
//            level = SessionLevel.ADVANCED
//        ),
//        Session(
//            id = "s13",
//            title = "Serverless Architecture for Mobile Apps",
//            description = "Build scalable backends without managing servers! Learn about serverless architectures using Cloud Functions, AWS Lambda, and how they integrate with your Android apps.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:30"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:15"),
//            speaker = speakers[3],
//            track = Track.CLOUD,
//            room = "Room B",
//            level = SessionLevel.INTERMEDIATE
//        ),
//        Session(
//            id = "s14",
//            title = "Advanced Kotlin Features for Android",
//            description = "Explore advanced Kotlin language features including inline functions, reified generics, delegates, and how to write more expressive and maintainable Android code.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "13:00"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "13:45"),
//            speaker = speakers[4],
//            track = Track.KOTLIN,
//            room = "Room A",
//            level = SessionLevel.ADVANCED
//        ),
//        Session(
//            id = "s15",
//            title = "Testing Strategies for Android Apps",
//            description = "Learn comprehensive testing approaches including unit tests, integration tests, UI tests with Espresso, and how to build confidence in your code quality.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "13:00"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "13:45"),
//            speaker = speakers[1],
//            track = Track.ANDROID,
//            room = "Room B",
//            level = SessionLevel.INTERMEDIATE
//        ),
//        Session(
//            id = "s16",
//            title = "Closing Keynote: Building the Future Together",
//            description = "Closing remarks, key takeaways from the conference, and exciting announcements about the future of the Android developer community in Uganda.",
//            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "16:00"),
//            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "17:00"),
//            speaker = speakers[0],
//            track = Track.KEYNOTE,
//            room = "Main Hall",
//            level = null
//        )
    )
}
