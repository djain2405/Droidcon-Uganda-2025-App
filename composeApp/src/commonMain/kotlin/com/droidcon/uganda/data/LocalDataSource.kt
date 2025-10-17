package com.droidcon.uganda.data

import com.droidcon.uganda.utils.TimeZoneUtils

object LocalDataSource {

    // Conference dates in EAT timezone
    private const val DAY_1 = "2025-11-10"  // Monday
    private const val DAY_2 = "2025-11-11"  // Tuesday

    val speakers = listOf(
        Speaker(
            id = "0",
            name = "Ahmed Nabil",
            title = "Senior Software Tech Lead",
            company = "Vodafone",
            bio = "Founder and Leader Of Kotlin Egypt, first and largest Kotlin community in Egypt,\n" +
                    "helped 100K+ know about Kotlin, Android, and general programming. Created \"Learn Programming\" initiative helped 500+ people from Egypt, Gulf, UK and more, Joined 1M Arab coders as a mentor,\n" +
                    "currently: Senior Software Tech Lead at Vodafone _VOIS, Previously, worked at HungerStation, Gen. C., and more.\u2028\u2028\n" +
                    "Social Links:\u2028https://linktr.ee/AhmedNMahran",
            imageUrl = "speaker_ahmed_nabil",
            linkedin = "https://linkedIn.com/company/KotlinEgypt"
        ),
        Speaker(
            id = "1",
            name = "Akshay Chordiya",
            title = "Android Developer",
            company = "Tinder | Co-Author of Kotlin Blueprints",
            bio = "Akshay Chordiya is Google Developer Expert for Android & Kotlin | Android Developer @ Tinder ❤\uFE0F and Kotlin Enthusiast.\n" +
                    "He is an active community speaker who is mostly found talking about Android and Kotlin. He is an avid blogger and author of \"Kotlin Blueprints\" book and instructor at Caster.IO",
            imageUrl = "speaker_akshay_chordiya",
        ),
        Speaker(
            id = "2",
            name = "Anjan kumar Kaleru",
            title = "Staff IAM Engineer",
            company = "Sony Interactive Entertainment",
            bio = "Anjan Kumar K is a Staff IAM Engineer and Product Lead at Sony Interactive Entertainment (PlayStation) with over 10 years of specialized experience in identity and access management. Currently leading global PlayStation identity initiatives for 100+ applications, Anjan manages a team of 7 direct reports while driving strategic IAM roadmap development and implementation.\n" +
                    "As a Certified Identity Governance Expert and results-driven leader, Anjan has successfully managed multi-million dollar IAM projects, including \$5M+ identity governance initiatives that delivered measurable ROI. His expertise spans the complete IAM ecosystem, with deep technical proficiency in Saviynt, Okta, SailPoint IdentityNow, Active Roles, and cloud platforms.\n" +
                    "Key achievements include:\n" +
                    "Reduced UAR cycle time by 50% and ticket SLAs by 80% through process optimization\n" +
                    "Decreased manual provisioning by 75% across enterprise environments\n" +
                    "Created 180+ audit-ready reports, saving 2000+ hours annually\n" +
                    "Achieved 'High Impact' rating at PlayStation for three consecutive years (top 10% in department)\n" +
                    "Reduced user onboarding time by 60% through automated solutions\n" +
                    "Anjan holds a Master's in Information Security and Artificial Intelligence from Ferris State University and maintains current certifications in Identity Governance and Product Management. His combination of technical expertise, strategic vision, and proven track record in scaling IAM operations makes him a recognized thought leader in cloud identity governance and enterprise security frameworks.",
            imageUrl = "speaker_anjan_kumar_kaleru",
        ),
        Speaker(
            id = "3",
            name = "Anselmo Alexandre",
            title = "Android",
            company = "SnappMobile",
            bio = "Olá \uD83D\uDC4B\uD83C\uDFFF,\n" +
                    "My name is Anselmo Alexandre\n" +
                    "I am a Sr. Android Engineer at Snapp Mobile Germany.\n" +
                    "Everyday making people's life easier using tech.\n" +
                    "\n",
            imageUrl = "speaker_anselmo_alexandre",
        ),
        Speaker(
            id = "4",
            name = "Arun Kambhammettu",
            title = "Software Engineering Manager",
            company = "AWS",
            bio = "Arun Kambhammettu is a seasoned software engineering leader with extensive experience in architecting, designing, and deploying scalable, high-performing applications across web, mobile, and cloud platforms. With a strong background in full-stack development, distributed systems, AdTech, and data-driven solutions, he has successfully led engineering teams to deliver enterprise-grade applications and cutting-edge products across diverse industries, including hospitality, travel, automotive, and streaming media.\n" +
                    "Currently, Arun serves as a Software Engineering Manager at Amazon (AWS Connect), leading a cross-functional team of software and ML engineers to build advanced customer data solutions. He drives initiatives in customer profiling, data mapping, and ML-based segmentation for prominent clients, including United Airlines, Nova Scotia, and Anthology, delivering AI-powered insights and unified 360° customer views.\n" +
                    "Previously, as a Senior Engineering Manager at Warner Bros. Discovery, Arun oversaw five teams spanning Web, Mobile, AdTech, SEO, and Operations for MotorTrend.com, a platform reaching over 130 million monthly users. His leadership enabled the development of highly scalable digital products, AI-driven personalization, and subscription models that significantly increased user engagement and revenue. Arun has also held pivotal roles at Truckstop.com, AccuRadio, and other organizations, where he spearheaded cloud migrations, introduced machine learning for fraud detection, enhanced DevOps and CICD pipelines, and architected next-gen applications for iOS, Android, and web platforms.\n" +
                    "A hands-on technologist, Arun is proficient in C#, Python, Node.js, React, Next.js, and major cloud services (AWS, GCP, Azure). He holds a Master's degree in Management Information Systems from Utah State University and is a certified Microsoft .NET and Azure professional. His passion lies in leveraging AI, data, and cloud-native architectures to build products that are secure, scalable, and impactful.",
            imageUrl = "speaker_arun_kambhammettu",
        ),
        Speaker(
            id = "5",
            name = "Caroline Nicole Letaru",
            title = "Career Coach & Talent Strategist",
            company = "Kazini Consults | Empowering Tech Professionals to Level Up",
            bio = "Caroline Nicole Letaru is a seasoned Career Coach and Talent Strategist at Kazini Consults, with over 6+ years of experience empowering tech professionals across 12 countries. She specializes in helping developers and tech talents elevate their careers through skill development, strategic career planning, and effective talent sourcing. With a strong background in technical recruiting and HR management, Nicole has successfully guided hundreds of professionals to build core skills, navigate career transitions, and achieve measurable career growth in the dynamic tech industry.",
            imageUrl = "speaker_caroline_nicole_letaru",
        ),
        Speaker(
            id = "6",
            name = "Dinoy Raj",
            title = "Product Engineer",
            company = "Strollby | Android developer | Jetpack compose | Kotlin | Flutter",
            bio = "Crafting mobile apps with compose & kotlin",
            imageUrl = "speaker_dinoy_raj",
        ),
        Speaker(
            id = "7",
            name = "Georges Byona",
            title = "Mobile Engineer",
            company = "FlutterFire | AI Enthusiast | Community Lead",
            bio = "Crafting mobile apps with compose & kotlin",
            imageUrl = "speaker_georges_byona",
        ),
        Speaker(
            id = "8",
            name = "Indu Priya Uppala",
            title = "",
            company = "Infosys McCamish",
            bio = "Indu Priya Uppala is a highly accomplished Project and Product Manager with over 16 years of experience delivering transformative technology solutions across the insurance, telecommunications, and logistics industries. Currently pursuing an Executive MBA at Georgia Tech, she brings a unique combination of strategic leadership and deep technical expertise to drive large-scale digital transformation initiatives. Indu has spearheaded end-to-end programs for major insurers, including the successful migration of millions of active and inactive policies, the implementation of Salesforce-driven agent portals, microservices-based architectures, and secure API ecosystems that enhance policy lifecycle management. Her experience includes leading cross-functional teams of 20–25 members across onshore and offshore models, orchestrating data governance, UAT, and large-scale system integrations to optimize performance and ensure regulatory compliance. Recognized for her ability to combine innovation with operational excellence, she has delivered measurable outcomes such as cutting claims processing times by more than 70%, streamlining agent workflows, and improving overall system reliability for Fortune 500 clients like John Hancock, TIAA CREF, and MetLife. With expertise spanning Java development, cloud platforms like AWS and Azure, and advanced DevOps practices, Indu excels at transforming complex legacy ecosystems into agile, scalable digital environments. Passionate about mentoring teams and fostering collaboration, she is committed to helping organizations achieve sustainable growth, operational efficiency, and a competitive edge through technology-driven solutions.\n" +
                    "\n",
            imageUrl = "speaker_indu_priya_uppala",
        ),
        Speaker(
            id = "9",
            name = "Jamiu Okanlawon",
            title = "Developer Advocate",
            company = "Invertase | Organizer of the FlutterBytes Conference",
            bio = "Jamiu Okanlawon is a Developer Advocate at Invertase and a Google Developer Expert (GDE) in Flutter. He brings 7 years of software engineering experience, with over 5 years dedicated to building and deploying Flutter applications.\n" +
                    "\n" +
                    "He is the founder of FlutterBytes, a thriving Africa-based community that supports Flutter developers through mentorship, hands-on learning, and collaboration. Jamiu also leads the FlutterBytes Conference, the largest Flutter-focused event in Africa.\n" +
                    "\n" +
                    "Passionate about empowering developers, Jamiu blends technical expertise with community leadership to drive growth, learning, and impact across the Flutter ecosystem.\n" +
                    "\n",
            imageUrl = "speaker_jamiu_okanlawon",
        ),
        Speaker(
            id = "10",
            name = "Jayesh Kumar Pandey",
            title = "Senior DFT Methodology Engineer",
            company = "ABV-Indian Institute of Information Technology, Gwalior, MP",
            bio = "Jayesh Kumar Pandey is a Senior DFT Methodology Engineer with over 18 years of experience in semiconductor design, Design-for-Test (DFT), In-System Test (IST), and system-level validation for advanced System-on-Chip (SoC) solutions. He currently leads innovation efforts at a leading semiconductor company in Santa Clara.\n" +
                    "An IETE Fellow, SCRS Fellow, and IEEE Senior Member, Jayesh has pioneered industry-first advancements in scalable, safety-compliant IST frameworks and DFT architectures significantly enhancing chip reliability and reducing time-to-market. His work has resulted in a U.S. patent on in-system test execution and several peer-reviewed publications.\n" +
                    "He actively contributes to the research community as a technical program committee (TPC) member and reviewer for top-tier conferences such as ITC, DFTS, and AIC. Jayesh is also a frequent invited speaker on topics related to reliable AI and robotics SoCs.",
            imageUrl = "speaker_jayesh_kumar_pandey",
        ),
        Speaker(
            id = "11",
            name = "Joshua Musyoki",
            title = "Android Engineer",
            company = "Transsion Kenya",
            bio = "Joshua Musyoki is an Android Engineer at Transsion Kenya, where he crafts clean and scalable mobile solutions used by real users. He builds not just for developers, but for real users — with a strong focus on performance, usability, and impact at scale. Passionate about developer tools, open-source, and clean architecture, Joshua is currently exploring how to simplify Android development by building reusable libraries and improving developer experience (DX). A firm believer in continuous learning, he’s always evolving — whether it’s through mentoring, contributing to community projects, or staying on the edge of Android’s ever-growing ecosystem. For Joshua, it’s all about building for builders — and making tools that matter.",
            imageUrl = "speaker_joshua_musyoki",
        ),
        Speaker(
            id = "12",
            name = "Joydip Basu",
            title = "",
            company = "West bengal university of Technology",
            bio = "Joydip Basu is a seasoned IT leader with over 19 years of expertise in SAP enterprise solutions, specializing in global program execution and digital transformation within the healthcare and life sciences sectors. He is widely recognized for leading cross-functional teams across geographies and delivering large-scale ERP implementations, including SAP S/4HANA, ECC Suite on HANA, APO, and GTS, seamlessly integrated with cloud platforms such as Salesforce, AWS, and MuleSoft.\n" +
                    "Throughout his career, Joydip has architected and executed end-to-end ERP transformations that drive operational excellence, regulatory compliance, and process optimization for global enterprises. His work consistently meets stringent regulatory standards such as FDA and HIPAA, ensuring IT system readiness in highly regulated, mission-critical environments.\n" +
                    "Joydip’s portfolio includes leadership roles in strategic initiatives at industry leaders including Gilead Sciences, Jabil Healthcare, IBM, and Tata Steel. He holds a Master’s degree in Business Administration and is SAP-certified in Supply Chain Management. With a unique combination of strategic insight and technical depth, Joydip is committed to delivering innovative, high-impact solutions that enable organizations to scale, modernize, and thrive in dynamic markets.\n",
            imageUrl = "speaker_joydip_basu",
        ),
        Speaker(
            id = "13",
            name = "Kenneth Mathari",
            title = "Mobile Developer | Kotlin",
            company = "",
            bio = "I'm a passionate Mobile Developer with a strong focus on Kotlin and Kotlin Multiplatform. I’ve spent the last few years building high-quality Android apps and cross-platform solutions that prioritize clean architecture, great user experiences, and scalable codebases. I’m also a strong advocate for open source, and I enjoy exploring how community-driven libraries can accelerate mobile development and bring developers together.\n" +
                    "\n",
            imageUrl = "speaker_kenneth_mathari",
        ),
        Speaker(
            id = "14",
            name = "Markus Wendland",
            title = "Software Craftsman",
            company = "iits consulting",
            bio = "Markus is a seasoned software developer with 30 years of experience, starting as a curious script kiddie and growing into a tech expert. Having witnessed the rapid evolution of the industry, he now shares not only technical insights but also a crucial perspective on mental health in software development. Drawing from his own experiences navigating the highs and lows of a demanding career, Markus delivers honest, thought-provoking talks that encourage open conversations about well-being in tech. His mission is to help developers build not just better software, but also healthier, more sustainable careers.",
            imageUrl = "speaker_markus_wendland",
        ),
        Speaker(
            id = "15",
            name = "Mrinal Jain",
            title = "Software Craftsman",
            company = "iits consulting",
            bio = "Markus is a seasoned software developer with 30 years of experience, starting as a curious script kiddie and growing into a tech expert. Having witnessed the rapid evolution of the industry, he now shares not only technical insights but also a crucial perspective on mental health in software development. Drawing from his own experiences navigating the highs and lows of a demanding career, Markus delivers honest, thought-provoking talks that encourage open conversations about well-being in tech. His mission is to help developers build not just better software, but also healthier, more sustainable careers.",
            imageUrl = "speaker_mrinal_jain",
        ),
        Speaker(
            id = "16",
            name = "Mugisa Brian (Cephas)",
            title = "Innovative Software Engineer",
            company = "",
            bio = "Hi, my name is Mugisa Brian, but you can call me Cephas. I'm a software engineer with experience in cross-platform app development, and I'm also passionate about graphics and UX design. I love using my skills to create experiences that make people's lives better. Am glad you checked me out here!",
            imageUrl = "speaker_mugisa_brian",
        ),
        Speaker(
            id = "17",
            name = "Pallavi Desai",
            title = "",
            company = "Capital One",
            bio = "Pallavi Desai is a seasoned technology leader with over 15 years of experience delivering scalable solutions in cloud computing, real-time data processing, and cybersecurity on AWS and big data platforms. She specializes in bot protection, and fraud prevention using anomaly modeling, automation, and observability.\n" +
                    "At Capital One, Pallavi has led cross-functional teams in designing and deploying advanced bot detection systems, embedding security automation into CI/CD pipelines, and leveraging AWS Lambda and Kafka-based log analytics to strengthen organizational resilience. Her work focuses on building secure, cloud-native, and intelligent platforms that enhance incident response, ensure regulatory compliance, and drive innovation.\n" +
                    "Pallavi holds a Master of Science in Information Systems from Central Michigan University and a Bachelor of Technology in Electronics and Communications Engineering from JNTU, India. She is an AWS Certified Solutions Architect (Professional and Associate) and a named inventor on a U.S. patent for an automated streaming data platform.",
            imageUrl = "speaker_pallavi_desai",
        ),
        Speaker(
            id = "18",
            name = "Paul Mayero",
            title = "Open-source believer",
            company = "",
            bio = "Paul is an experienced developer with over 5 years of professional experience in software development. He is highly skilled in building scalable and secure web applications using various technologies.\n" +
                    "\n" +
                    "Paul is an active member of the open-source community and has contributed to various open-source projects such as F-Droid and Open stack. He is passionate about helping other developers learn and grow, and he enjoys mentoring junior developers and sharing his knowledge and expertise with others.",
            imageUrl = "speaker_paul_mayero",
        ),
        Speaker(
            id = "19",
            name = "Priyank Shankar",
            title = "Android Dev",
            company = "Mercari",
            bio = "My Socials -\n" +
                    "GitHub - https://github.com/shankarpriyank\n" +
                    "LinkedIn - https://www.linkedin.com/in/shankarpriyank\n" +
                    "Twitter - https://twitter.com/priyank312002",
            imageUrl = "speaker_priyank_shankar",
        ),
        Speaker(
            id = "20",
            name = "Pulkit Midha",
            title = "Senior Mobile Developer",
            company = "Couchbase",
            bio = "Pulkit Midha is a mobile developer and technical evangelist passionate about offline-first architecture, real-time sync, and intelligent client-side experiences. He has worked on cross-platform apps, AR/VR products, and AI-integrated systems. A GSoC alumnus and frequent hackathon winner, Pulkit is focused on building resilient mobile solutions and growing open developer communities.\n" +
                    "\n",
            imageUrl = "speaker_pulkit_midha",
        ),
        Speaker(
            id = "21",
            name = "Roy Wanyoike",
            title = "Software engineer",
            company = "",
            bio = "Software Developer",
            imageUrl = "speaker_roy_wanyoike",
        ),
        Speaker(
            id = "22",
            name = "Saheed Adewumi",
            title = "GDE & Co-Founder",
            company = "QTSolution",
            bio = "Saheed Adewumi is a Google Developer Expert (GDE) and a leading Community Developer Advocate, specializing in full-stack architecture and multi-cloud engineering. I am deeply committed to building software the right way, leveraging principles like Domain-Driven Design (DDD) and Clean Architecture to ensure mobile and cloud-native solutions are scalable, maintainable, and aligned with core business logic.\n" +
                    "\n" +
                    "As Co-founder of QTSolution Services, I operate at the intersection of innovation and infrastructure. My core focus is architecting resilient, multi-cloud native systems and software development.\n" +
                    "\n" +
                    "Whether I'm hands-on building with AI, leading advanced technical workshops, or teaching best practices for clean code and architecture, my mission is to translate bold ideas into sustainable technological impact.",
            imageUrl = "speaker_saheed_adewumi",
        ),
        Speaker(
            id = "23",
            name = "Sam Aricha",
            title = "Mobile Engineer",
            company = "Intersoft Eagles Systems",
            bio = "An Engineer who loves to debug and learn.",
            imageUrl = "speaker_sam_aricha",
        ),
        Speaker(
            id = "24",
            name = "Sylvia Dieckmann",
            title = "Mobile App Developer, GDE Flutter/Dart",
            company = "",
            bio = "Sylvia is a Google Developer Expert for Flutter/Dart. Her preferred professional role includes some IT management but also enough hands-on development work to keep her dev skills sharp. Her main interest lies in exploring new technologies in the space of mobile apps and web technologies. She is currently playing with Flutter, Firebase, and some ML for Android but might change focus as new technologies pop up. \n" +
                    "Sylvia divides her time between South Africa and Germany. She is a Women Techmaker (WTM) Ambassador and an active member of the GDG Cape Town chapter. She is a frequent speaker at GDG events and aims to support the developer community through mentoring and events.",
            imageUrl = "speaker_sylvia_dieckmann",
        ),
        Speaker(
            id = "25",
            name = "Zoe Farooq",
            title = "Engineering Lead",
            company = "DeliveryHero",
            bio = "Hi, I am Zoe. A musician by ❤\uFE0F, working as an Engineering Manager at DeliveryHero. Living in Berlin, 'trying' to learn German.",
            imageUrl = "speaker_zoe_farooq",
        ),




    )

    val sessions = listOf(
        // DAY 1 - Monday, November 10, 2025
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
            track = Track.NONE,
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
            speaker = speakers[3],
            track = Track.NONE,
            room = "Room B",
            level = null
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
            speaker = speakers[9],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s7",
            title = "Breaking the HTTP Habit: Kotlin's Journey to AI-First Servers",
            description = "HTTP served us well, but AI demands better. Follow Kotlin's evolution from HTTP servers to MCP-powered AI infrastructure that communicates the way artificial intelligence actually thinks.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:05"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "12:50"),
            speaker = speakers[23],
            track = Track.NONE,
            room = "Auditorium",
            level = null
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
            speaker = speakers[7],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s9",
            title = "Lunch Break Buffet lunch & networking Sponsor & community showcase",
            description = "",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "1:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:00"),
            speaker = null,
            track = Track.NONE,
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
            speaker = speakers[11],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s11",
            title = "Build Spectacular TV Apps with Flutter",
            description = "Flutter for Mobile is released, Flutter for Web is released, Flutter for macOS, Linux, and Windows is in also released and it's really cool that Flutter officially supports six platforms. But what if I want to run my Flutter app on the TV? Unfortunately, nobody has heard of the official Flutter for TV yet. Actually, a Flutter app can be launched on TV (not AndroidTV only, but AppleTV, Tizen, and others as well). In my talk, I'm going to tell you how to do this, what difficulties and issues you will face, and how to resolve them.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_1, "2:45"),
            speaker = speakers[15],
            track = Track.NONE,
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
            speaker = speakers[25],
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
            speaker = speakers[6],
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
            speaker = speakers[2],
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
            speaker = speakers[24],
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
            speaker = speakers[19],
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
            speaker = speakers[12],
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
        // DAY 2 - Tuesday, November 11, 2025
        Session(
            id = "s21",
            title = "Registration & Breakfast Attendee check-in, badge collection",
            description = "Networking over breakfast Sponsor bo",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "07:35"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "08:50"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s22",
            title = "Don‘t let the AI hype break your mentality",
            description = "The topic of “AI“ seems to be everywhere these days, especially for software developers. From claiming to be the problem solver for day to day tasks up to apocalyptic scenarios of replacing all jobs, the stories being told vary wildly.\n" +
                    "\n" +
                    "Either you get caught by it and your thoughts just spin around how to be an early adopter, create a new service out of it, and see lots and lots of possibilities (often by ignoring real world needs and losing a rational base on decision making),\n" +
                    "\n" +
                    "Or you get anxious about what AI could mean to your current role and your future career prospects. The media draws a picture of the world in which developers are forced to use AI to create new software, becoming a prompt operator and ultimately losing their job to the same AI.\n" +
                    "\n" +
                    "But this fear of new overt-hyped technology taking away our jobs is not new. In the past many hypes appeared, some of them just vanished, some changed the way we live, communicate, consume media or travel. They can change the world, and so can we.\n" +
                    "\n" +
                    "In my talk I want to walk through some generic approaching to handling a hype, how hypes affects our daily work, and what impact hypes may have on our mental stability.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "08:50"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:35"),
            speaker = speakers[14],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s23",
            title = "From Web Servers to AI Servants: The Kotlin MCP Revolution",
            description = "Your servers have been serving humans for decades. Time to teach them to serve AI assistants instead. Explore how Kotlin and MCP create servers that make AI genuinely useful.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:35"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:10"),
            speaker = speakers[23],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s24",
            title = "Add Extra Functionality to Supercharge your Flutter Apps with Dart Extensions",
            description = "Dart extensions are one of the most powerful yet overlooked features in Flutter development. In this talk, we’ll unlock how you can use them to supercharge your Flutter apps—writing cleaner, more maintainable, and more expressive code without modifying existing classes or third-party libraries.\n" +
                    "\n" +
                    "Whether you’re extending core types like String or DateTime, enhancing third-party packages, or injecting new life into Flutter widgets, this advanced session will show how Dart extensions can make your codebase smarter, DRYer, and easier to scale.\n" +
                    "\n" +
                    "What You’ll Learn:\n" +
                    "• Custom Power-Ups for Core Types: Add intuitive methods to String, DateTime, List, and others to clean up boilerplate and make your logic more expressive\n" +
                    "• No-Fork Enhancements: Extend third-party packages and Flutter widgets with your own logic—without modifying their source code\n" +
                    "• Advanced Patterns: Dive into generic, conditional, and scoped extensions for modular and scalable architecture\n" +
                    "• When & Why: Learn where Dart extensions outperform mixins, helpers, and even inheritance patterns in terms of performance, readability, and maintainability\n" +
                    "• Real-World Use Cases: Explore production examples from apps I’ve built—featuring UI extensions, state helpers, and utility methods for cleaner, more maintainable code\n" +
                    "• Live Demo – Build a Mini Cart App: Watch how all these techniques come together in a live-coded mini shopping cart app, demonstrating how to apply extensions to widgets, logic, and third-party libraries in real time\n" +
                    "\n" +
                    "Speaker Bio\n" +
                    "I’m a passionate Flutter developer, UX thinker, and community speaker with over 5 years of experience building scalable cross-platform apps across fintech, education, and retail. I had the privilege of speaking at the previous Fluttercon Africa, where I shared advanced techniques in Flutter development — and I’m excited to return with even more insights.\n" +
                    "\n" +
                    "Beyond Fluttercon, I’ve delivered talks at numerous developer events across Africa on topics like clean architecture, Dart extensions, UI optimization, and real-world app development. You can explore some of my past sessions on my community profile.\n" +
                    "\n" +
                    "I love breaking down complex Flutter and Dart concepts into practical, actionable techniques that developers can use right away in their own projects. Whether it’s through the apps I’ve built like Calcut and Eduuka, or through my community talks, I’m always excited to share and learn together.\n" +
                    "\n" +
                    "Keywords: Dart Extensions, Flutter Architecture, Code Enhancement, Maintainable Code, Third-Party Packages, Advanced Flutter",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "09:35"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:10"),
            speaker = speakers[16],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s25",
            title = "Architecting the API Economy: Building Scalable Digital Partnerships with Mesh Architectures and Com",
            description = "APIs have become the backbone of digital transformation, powering ecosystems that connect businesses, developers, and customers. As organizations move beyond traditional gateway-centric models, new architectural patterns are reshaping how APIs scale, integrate, and create value.\n" +
                    "\n" +
                    "This session dives into API mesh architectures and domain-driven interfaces, showing how they unlock resilience, accelerate partner onboarding, and enable revenue-generating digital partnerships. We will explore GraphQL federation and hybrid event-driven designs that reduce complexity, cut network overhead, and improve developer productivity while maintaining flexibility across distributed systems.\n" +
                    "\n" +
                    "Attendees will also learn how to apply runtime governance with service mesh technologies including traffic shaping, circuit breaking, and eBPF-based observability to strengthen reliability at scale. Finally, we’ll highlight how composite API strategies that blend REST, GraphQL, and event-driven paradigms open new monetization paths and ecosystem opportunities.\n",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:50"),
            speaker = speakers[4],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s26",
            title = "Distribute your own apps with F-Droid",
            description = "This workshop aims to teach developers how they can use the F-Droid toolkit to distribute their own apps. The focus will be on fdroidserver - the python cli tool - to set up their own repository and distribute it. Another tool will be Repomaker, the online Django app that makes F-Droid compatible repositories all from a web interface.\n" +
                    "\n" +
                    "The workshop will show users how to create their own F-Droid repository and download it onto their devices using any F-Droid client.\n" +
                    "\n",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:50"),
            speaker = speakers[4],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s27",
            title = "Smarter Android: Building Scalable AI-Driven Apps for Startups and Local Businesses",
            description = "As mobile usage grows across Africa, so does the demand for smarter, scalable Android apps that do more with less. In this session, we’ll explore how startups and indie developers can integrate lightweight yet powerful AI features — from smart recommendations to chat interfaces — using tools like Firebase ML, Gemini APIs, and on-device ML solutions. We’ll walk through how to design architecture that supports growth, even with limited budgets or infrastructure, while keeping performance and user experience at the core. Whether you’re building a delivery app, finance tool, or service marketplace, this talk will equip you with practical tools, design tips, and a scalable mindset to take your Android app to the next level — with AI as your co-pilot.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:50"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:30"),
            speaker = speakers[22],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s28",
            title = "Test, Code, Repeat: Mastering AI-Assisted Development",
            description = "\"Test, Code, Repeat: Master AI-Assisted Development\" is a hands-on workshop that redefines coding by treating AI as your collaborative partner. Discover how iterative workflows—like the ping-pong pairing technique—blend human creativity with AI efficiency to accelerate problem-solving, improve code quality, and spark innovation.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "10:50"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:30"),
            speaker = speakers[21],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s29",
            title = "Automated Security at Scale: Real-Time Threat Detection for Resilient Mobile-First Organizations",
            description = "In today’s digital landscape, cyberattacks are not only growing in frequency but also in sophistication making real-time security automation a necessity rather than an option. This session demonstrates how organizations can transform their security posture by deploying automated, serverless monitoring frameworks that detect and respond to threats within minutes.\n" +
                    "\n" +
                    "Using AWS Lambda and managed streaming services, we’ll explore how application logs and bot traffic can be continuously analyzed to identify malicious behavior early. Attendees will see how policy-driven workflows can automatically quarantine suspicious sessions, update firewall rules, and trigger compliance checks significantly reducing the need for manual intervention in low- to medium-severity incidents.\n" +
                    "\n" +
                    "Beyond immediate protection, automation also reduces fraud-related losses, accelerates regulatory compliance through built-in auditing, and fosters customer trust. By embedding security controls into developer workflows and democratizing threat visibility across teams, organizations can achieve both operational efficiency and organizational resilience.\n" +
                    "\n" +
                    "With practical examples and actionable takeaways, this talk equips developers, architects, and security professionals with scalable frameworks to modernize security operations and safeguard not only their organizations but also the broader digital ecosystem that powers mobile-first commerce.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:25"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:05"),
            speaker = speakers[17],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s30",
            title = "From Post-Silicon to Predictive: Smarter In-System Testing for Reliable, Future-Proof SoCs",
            description = "As modern System-on-Chips (SoCs) power everything from AI applications to autonomous vehicles and cloud-scale infrastructure, ensuring reliability throughout their lifecycle has become mission-critical. Traditional test methods, limited to manufacturing or early bring-up, miss real-world issues that arise under dynamic workloads and environmental stress.\n" +
                    "\n" +
                    "This session introduces In-System Test (IST) a runtime approach that executes structural tests directly in deployed silicon without disrupting normal operation. Attendees will learn how advanced techniques such as ATPG and MBIST can be seamlessly integrated into live systems, and how a decoupled test access framework enables flexible test delivery and chip health monitoring.\n" +
                    "\n" +
                    "Looking forward, the talk explores the emerging frontier of Predictive IST where real-time test data and failure analytics transform IST from a reactive fault-detection mechanism into a proactive reliability engine. By forecasting silicon degradation and predicting failures before they occur, Predictive IST unlocks new possibilities in predictive maintenance for safety-critical and high-availability systems.\n" +
                    "\n" +
                    "Key takeaways:\n" +
                    "\n" +
                    "Architectural principles for runtime IST deployment in SoCs\n" +
                    "\n" +
                    "Practical challenges and solutions for field integration\n" +
                    "\n" +
                    "Using IST data for predictive reliability and lifecycle modeling\n" +
                    "\n" +
                    "Strategies for optimizing test scheduling, coverage, and performance\n" +
                    "\n" +
                    "This session is tailored for SoC architects, DFT engineers, and reliability professionals seeking to extend silicon lifespan, reduce downtime, and build smarter, more resilient systems.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "11:25"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:05"),
            speaker = speakers[10],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s31",
            title = "Go with the Flow",
            description = "Kotlin Flow is an implementation of Reactive Stream specification made on top of coroutines for Kotlin.\n" +
                    "\n" +
                    "In this talk, we will look at Kotlin Flow, it's internals, how to use it with on Android or any other platforms, and share how you can migrate to Flow\n" +
                    "\n" +
                    "Agenda\n" +
                    "- Quick intro to Flow\n" +
                    "- Internals of Kotlin Flow\n" +
                    "- Using Flow on Android and other platforms\n" +
                    "- Why and how to migrate\n" +
                    "- Working with existing reactive streams ❤ Flow in single codebase\n" +
                    "- Effectively using Flows with Compose\n" +
                    "- Magic of multi-platform\n" +
                    "- Tests\n" +
                    "- Conclusion\n" +
                    "\n" +
                    "You’ll walk away with a clear idea of what Kotlin Flow and how it works internally and h",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:55"),
            speaker = speakers[1],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s32",
            title = "Offline-First, AI-Ready: Architecting Mobile Apps That Learn and Sync Smartly",
            description = "Modern mobile apps must do more than just work. They must work smartly, even when offline. (Think of common applications like Twitter or Instagram.) This talk will walk through building an AI-enhanced, offline-first architecture on Android using Jetpack and custom vector stores. You’ll learn how to integrate on-device LLMs, enable smooth bi-directional sync with backend stores, and adopt scalable syncing patterns used in real-world apps. We’ll also cover sync conflict resolution, data serialization formats, and an efficient approach to user-local data modeling. All without sacrificing performance.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "12:55"),
            speaker = speakers[20],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s33",
            title = "Lunch Break",
            description = "Buffet lunch & networking Sponsor & community showcase",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "01:00"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "02:00"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s34",
            title = "Level Up Your Android Career: Core Skills and Strategies for Growth",
            description = "In a rapidly evolving tech world, technical skills alone aren’t enough to thrive as an Android developer. This session focuses on the core career skills, mindset shifts, and practical strategies Android developers need to level up professionally and navigate their career growth effectively. Drawing from my extensive experience coaching tech professionals across multiple countries, I’ll guide attendees on how to build essential soft skills, develop a growth mindset, and leverage continuous learning to unlock new opportunities in Android development.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "02:40"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:10"),
            speaker = speakers[5],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s35",
            title = "MindCraft: Building Resilience One Line at a Time",
            description = "As software developers, we often share a passion for gaming and coding, but we are not immune to the mental health challenges that affect many. In this talk, I will explore the intersection of mental health and personal growth through the lens of programming and role-playing games (RPGs). Our heros mental health journey mirrors the process of developing and refining a class in code, while navigating challenges similar to those faced by an RPG character. The process involves identifying weaknesses in the \"code\" of the mind, addressing negative thought patterns, and adapting strategies to overcome emotional obstacles such as anxiety, depression, and self-doubt, much like refactoring code or leveling up in an RPG.\n" +
                    "\n" +
                    "Just as in coding, progress in mental health is not linear. Challenges require adjustments and growth, often through trial and error. Each new obstacle demands changes in one's approach, whether in the code or in mindset. Much like an RPG character depends on strong allies to succeed, supporters, true friends and family act as essential \"party members\" in the game of life, offering support, perspective, and encouragement when challenges seem overwhelming.\n" +
                    "\n" +
                    "The session aims to raise awareness about mental health and encourage openness in talking about struggles. Attendees will be encouraged to ask for help whenever they need it. With the support of those around us, we can navigate the game of life with more love, laughter, and understanding.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "02:40"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:10"),
            speaker = speakers[14],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s36",
            title = "Peer-to-Peer Sync on Android: Building Offline-First Apps That Talk to Each Other",
            description = "In a world obsessed with cloud connectivity, what if your app could sync data directly with nearby devices, without hitting the cloud? This session introduces peer-to-peer (P2P) sync patterns for Android that enable apps to share data over LAN, Wi-Fi Direct, or BLE. We’ll walk through building resilient, offline-first apps where data flows between devices securely and automatically, perfect for field deployments, disaster zones, or privacy-focused use cases.\n" +
                    "Whether you’re building a logistics tool, health records app, or multiplayer experience. This talk will show you how to build apps that sync serverlessly!\n" +
                    "Towards the end, I will also showcase a very futuristic idea, where we will be using mesh topology to text offline just like WhatsApp.\n" +
                    "\n",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:40"),
            speaker = speakers[20],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s37",
            title = "The Hidden Cost of Copy-Paste: Why Your App Should Depend on People, Not Just Code",
            description = "In today’s fast-paced mobile development, it’s easy to copy-paste code or drop in open source libraries without a second thought but every dependency comes with people, context, and consequences. This talk explores the often-overlooked human side of open source, highlighting the importance of understanding the communities behind the code. Through real-world stories and practical insights, we’ll learn how to evaluate project health, engage with maintainers, and contribute meaningfully, even as consumers. By shifting our mindset from passive use to active participation, we can build more secure, resilient, and collaborative mobile apps.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:10"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:40"),
            speaker = speakers[13],
            track = Track.NONE,
            room = "Visual Spaces",
            level = null
        ),
        Session(
            id = "s38",
            title = "Reinventing Insurance with Microservices: API-First Strategies for Faster, Smarter Policy Management",
            description = "The insurance industry is undergoing a radical shift from legacy systems to modern, API-driven platforms that prioritize speed, scalability, and customer experience. With the global insurtech market projected to reach USD 146.43 billion by 2030, insurers embracing microservices and API-first strategies are setting new benchmarks in digital transformation.\n" +
                    "\n" +
                    "By breaking down monolithic platforms into 35–45 microservices, leading carriers have accelerated testing cycles by up to 80% and cut new product launch timelines from 18 months to just a few months. Secure API ecosystems now process nearly two billion monthly transactions across 120–180 active APIs—slashing data errors by 94% and driving system uptime to 99.98%.\n" +
                    "\n" +
                    "These modernization efforts are not just technical wins—they transform operations. Automated workflows and real-time data validation have reduced claims processing from 10+ days to under 3, while digital triage routes the majority of claims without human intervention. Massive migrations, such as Allianz’s 28 million policies moved in just 14 months, prove that large-scale change can be achieved without service disruption.\n" +
                    "\n" +
                    "This session will equip developers, architects, and tech leaders with actionable strategies for:\n" +
                    "\n" +
                    "Designing phased digital transformation roadmaps\n" +
                    "\n" +
                    "Building secure, scalable microservices and API-first ecosystems\n" +
                    "\n" +
                    "Executing high-volume data migrations with minimal risk\n" +
                    "\n" +
                    "Digital transformation in insurance is no longer optional—it’s the foundation for faster underwriting, improved customer experiences, and long-term market leadership.",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:35"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "04:15"),
            speaker = speakers[8],
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s39",
            title = "Open Roundtables",
            description = "",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "03:40"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "04:20"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s40",
            title = "Break",
            description = "",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "04:15"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "04:30"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
        Session(
            id = "s41",
            title = "Open Roundtables",
            description = "",
            startTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "04:30"),
            endTime = TimeZoneUtils.createConferenceDateTime(DAY_2, "05:10"),
            speaker = null,
            track = Track.NONE,
            room = "Auditorium",
            level = null
        ),
    )
}
