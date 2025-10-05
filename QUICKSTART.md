# Quick Start Guide 🚀

## Running on Android ✅

The Android build is already tested and working!

```bash
cd DroidConUganda-KMP

# Install on connected device or emulator
./gradlew :composeApp:installDebug

# Or just build the APK
./gradlew :composeApp:assembleDebug
```

The APK will be at: `composeApp/build/outputs/apk/debug/composeApp-debug.apk`

## Running on iOS 📱

### Prerequisites
- macOS with Xcode 14+ installed
- iOS Simulator or physical iOS device

### Steps

1. **Open the Xcode project:**
   ```bash
   cd DroidConUganda-KMP
   open iosApp/iosApp.xcodeproj
   ```

2. **In Xcode:**
   - Select the **iosApp** scheme at the top
   - Choose a simulator (e.g., "iPhone 15")
   - Click the **Run** button (▶️) or press **Cmd+R**

3. **First Build:**
   - Xcode will automatically run the Gradle task to build the Kotlin framework
   - This may take 2-5 minutes the first time
   - Subsequent builds will be much faster

4. **App Launch:**
   - Once the framework is built, the iOS app will install and launch on the simulator
   - You'll see the same beautiful DroidCon Uganda app running natively on iOS!

### Troubleshooting iOS

**If you see "ComposeApp framework not found":**
- Clean build: Product → Clean Build Folder (Cmd+Shift+K)
- Try building again

**If the Gradle script fails:**
- Make sure you're in the project root directory
- Verify Gradle wrapper is executable: `chmod +x gradlew`
- Try running manually: `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`

## Project Features

Once the app runs, you can:

1. **Browse Agenda** - See 12 conference sessions with details (times in EAT timezone)
2. **View Speakers** - Explore 6 speaker profiles
3. **Save Favorites** - Tap ⭐ to add sessions to "My Agenda"
4. **Learn About DroidCon** - Tap ℹ️ in the top bar (includes timezone info)
5. **Try Dark Mode** - Switch your device/simulator to dark mode

**Timezone Support**:
- Session times are stored in **East Africa Time (EAT, UTC+3)** - Uganda's timezone
- The app **automatically converts** times to your device's local timezone
- Example: If you're in PST (UTC-8), a session at 9:00 AM EAT shows as 10:00 PM (previous day)
- The About dialog shows your current timezone and conversion status

## Development

- **Shared Code**: `composeApp/src/commonMain/` - 100% shared across platforms
- **Android**: `composeApp/src/androidMain/` - Android-specific entry point
- **iOS**: `composeApp/src/iosMain/` - iOS-specific entry point
- **iOS App**: `iosApp/` - Xcode project wrapper

## Next Steps

- Customize the data in `data/LocalDataSource.kt`
- Update theme colors in `ui/theme/Theme.kt`
- Add more screens or features
- Connect to a real backend API

Enjoy building with Kotlin Multiplatform! 🎉
