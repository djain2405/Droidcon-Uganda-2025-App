# DroidCon Uganda 2025 Conference App 🇺🇬

A beautiful Kotlin Multiplatform conference app built for DroidCon Uganda! Features a fun UI, smooth animations, and full iOS + Android support.

## Features ✨

- **📅 Agenda Tab**: Browse all conference sessions with beautiful cards showing time, speaker, track, and difficulty level
- **👥 Speakers Tab**: Explore speaker profiles with their bio, company, and social links
- **⭐ My Agenda Tab**: Build your personal schedule by favoriting sessions you want to attend
- **ℹ️ About DroidCon**: Learn about the conference
- **🎨 Beautiful UI**: Material Design 3 with DroidCon brand colors, smooth animations, and intuitive navigation
- **📱 Truly Multiplatform**: Built with Kotlin Multiplatform - runs on Android, iOS, and Desktop!

## UI Highlights 🎨

- **Material Design 3** with custom DroidCon Uganda theming
- **Smooth animations** on card interactions with spring physics
- **Track-based color coding** for easy session identification (Android, Kotlin, Design, Cloud, Keynote)
- **Intuitive bottom navigation** between tabs
- **Detailed dialogs** for sessions and speakers with rich information
- **Empty state** guidance for favorites
- **Responsive layouts** that adapt to all screen sizes

## Tech Stack 🛠️

- **Kotlin Multiplatform** - Share 100% of business logic across platforms
- **Compose Multiplatform** - Modern declarative UI framework
- **Material 3** - Latest Material Design components
- **Kotlin Coroutines & Flow** - Reactive state management
- **ViewModel** - Clean MVVM architecture

## Sample Data 📊

The app includes a rich local dataset featuring:
- 12 conference sessions across 5 tracks (Android, Kotlin, Design, Cloud, Keynote)
- 6 expert speakers from Uganda's tech ecosystem (Jumia, SafeBoda, MTN, Andela, JetBrains, Flutterwave)
- Sessions covering beginner to advanced levels
- Realistic conference schedule (9:00 AM - 4:45 PM EAT)
- **Automatic timezone conversion**: Times stored in East Africa Time (EAT, UTC+3) and displayed in your local timezone
- Smart timezone detection with visual indicators

## Running the App 🚀

### Android
```bash
cd DroidConUganda-KMP
./gradlew :composeApp:installDebug
```

Or open in Android Studio and run the app target.

### iOS (requires macOS with Xcode)
1. Open `iosApp/iosApp.xcodeproj` in Xcode:
   ```bash
   open iosApp/iosApp.xcodeproj
   ```
2. Select the "iosApp" scheme and choose a simulator (e.g., iPhone 15)
3. Click the Run button (▶️) or press Cmd+R
4. The Kotlin framework will be built automatically, then the iOS app will launch

**Note**: The first build may take a few minutes as it compiles the shared Kotlin code into an iOS framework.

### Desktop (JVM)
```bash
./gradlew :composeApp:run
```

## Project Structure 📁

```
DroidConUganda-KMP/
├── composeApp/                    # Shared Kotlin Multiplatform module
│   └── src/
│       ├── commonMain/            # Shared code for all platforms
│       │   └── kotlin/com/droidcon/uganda/
│       │       ├── App.kt         # Main app with navigation
│       │       ├── data/
│       │       │   ├── Models.kt          # Data models
│       │       │   └── LocalDataSource.kt # Sample conference data
│       │       └── ui/
│       │           ├── ConferenceViewModel.kt # State & favorites
│       │           ├── theme/Theme.kt         # Material 3 theming
│       │           └── screens/
│       │               ├── AgendaScreen.kt    # Sessions list
│       │               ├── SpeakersScreen.kt  # Speakers list
│       │               └── FavoritesScreen.kt # Saved sessions
│       ├── androidMain/           # Android-specific code
│       │   ├── AndroidManifest.xml
│       │   └── kotlin/com/droidcon/uganda/MainActivity.kt
│       └── iosMain/               # iOS-specific code
│           └── kotlin/com/droidcon/uganda/MainViewController.kt
└── iosApp/                        # iOS application wrapper
    └── iosApp/
        ├── iOSApp.swift
        └── ContentView.swift
```

## Architecture 🏗️

The app follows Clean Architecture principles with MVVM pattern:

- **Data Layer**: Models and LocalDataSource provide conference data
- **UI Layer**: Composable screens with ViewModel for state management
- **Shared Logic**: 100% of business logic shared across platforms
- **Platform-Specific**: Only UI entry points differ per platform

## Future Enhancements 🚀

- [ ] Connect to real backend API
- [ ] Add session reminders/notifications
- [ ] Implement session search and filtering by track/level
- [ ] Add venue/room maps
- [ ] Enable offline support with local caching
- [ ] Social features (share schedule, attendee chat)
- [ ] QR code ticket integration
- [ ] Speaker Q&A functionality

## Contributing 🤝

This app was built as a demo for DroidCon Uganda. Feel free to fork, improve, and adapt it for your own conferences!

## License 📄

Open source - use it for your own conferences!

---

Built with ❤️ using Kotlin Multiplatform & Compose for DroidCon Uganda 2025
