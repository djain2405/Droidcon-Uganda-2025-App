# Store Submission Checklist

## ✅ Pre-Submission Requirements Completed

### 📱 Android (Google Play Store)

#### App Configuration
- [x] Version code set to 1 (build.gradle.kts:72)
- [x] Version name set to "1.0" (build.gradle.kts:73)
- [x] Application ID: com.droidcon.uganda
- [x] Target SDK: Latest (check libs.versions.toml)
- [x] Min SDK: Check libs.versions.toml
- [x] Internet permission added to AndroidManifest.xml
- [x] App label: "DroidCon Uganda"

#### Build Configuration
- [x] ProGuard/R8 enabled for release builds
- [x] Resource shrinking enabled
- [x] ProGuard rules created (proguard-rules.pro)
- [x] Signing config structure added (needs keystore details)

#### Required Assets
- [ ] **ACTION REQUIRED:** App icon needed
  - Create ic_launcher.png for each density (mdpi, hdpi, xhdpi, xxhdpi, xxxhdpi)
  - Create ic_launcher_round.png for each density
  - Recommended: Use Android Studio's Image Asset tool
  - Current: Using default icons (will be rejected)

#### Store Listing
- [x] Privacy policy created (PRIVACY_POLICY.md)
- [x] App description written (STORE_LISTING.md)
- [x] Keywords defined
- [x] Category selected: Events

#### Before Building Release APK
1. **Create Keystore:**
   ```bash
   keytool -genkey -v -keystore droidcon-uganda-release.jks \
     -alias droidcon-release -keyalg RSA -keysize 2048 -validity 10000
   ```

2. **Update build.gradle.kts signing config:**
   ```kotlin
   signingConfigs {
       create("release") {
           storeFile = file("../droidcon-uganda-release.jks")
           storePassword = System.getenv("KEYSTORE_PASSWORD")
           keyAlias = "droidcon-release"
           keyPassword = System.getenv("KEY_PASSWORD")
       }
   }
   buildTypes {
       getByName("release") {
           signingConfig = signingConfigs.getByName("release")
           // ... rest of config
       }
   }
   ```

3. **Build Release APK/AAB:**
   ```bash
   # For AAB (recommended for Play Store)
   ./gradlew :composeApp:bundleRelease

   # Output: composeApp/build/outputs/bundle/release/composeApp-release.aab

   # For APK (testing)
   ./gradlew :composeApp:assembleRelease

   # Output: composeApp/build/outputs/apk/release/composeApp-release.apk
   ```

4. **Test Release Build:**
   ```bash
   # Install release APK on device
   ./gradlew :composeApp:installRelease

   # Test thoroughly:
   - All screens load correctly
   - Favorites work
   - No crashes
   - Timezone conversion works
   - Navigation works
   ```

#### Screenshots Required (Google Play)
- [ ] Phone screenshots (minimum 2, maximum 8):
  - 16:9 or 9:16 aspect ratio
  - Minimum dimension: 320px
  - Maximum dimension: 3840px

  **Suggested screenshots:**
  1. Agenda screen with sessions list
  2. Session detail dialog
  3. Speakers list
  4. Speaker detail view
  5. My Agenda / Favorites screen
  6. Day selector view

- [ ] Tablet screenshots (optional but recommended):
  - 16:9 or 9:16 aspect ratio
  - Same scenes as phone

- [ ] Feature graphic (required):
  - 1024 x 500 px
  - JPG or 24-bit PNG (no alpha)

#### Play Store Console Steps
1. Create app in Play Console
2. Upload app icon (512x512 PNG, 32-bit with alpha)
3. Add screenshots
4. Add feature graphic
5. Fill app description from STORE_LISTING.md
6. Set category: Events
7. Set content rating (complete questionnaire)
8. Add privacy policy URL (host PRIVACY_POLICY.md online first)
9. Upload AAB file
10. Set pricing (Free)
11. Select countries
12. Submit for review

---

### 🍎 iOS (App Store)

#### App Configuration
- [x] Bundle version: 1 (Info.plist:20)
- [x] Version string: 1.0 (Info.plist:18)
- [x] Bundle ID: Set in Xcode project (currently uses $(PRODUCT_BUNDLE_IDENTIFIER))
- [x] Display name: "DroidCon Uganda"
- [x] Supported orientations configured

#### Required Assets
- [ ] **ACTION REQUIRED:** App Icons in Assets.xcassets
  - Create Assets.xcassets/AppIcon.appiconset
  - Add all required sizes:
    - iPhone: 60x60@2x, 60x60@3x, 40x40@2x, 40x40@3x, 29x29@2x, 29x29@3x
    - iPhone (iOS 14+): 20x20@2x, 20x20@3x
    - iPad: 76x76@2x, 40x40@1x, 40x40@2x, 29x29@1x, 29x29@2x, 20x20@1x, 20x20@2x
    - App Store: 1024x1024@1x
  - Use Xcode's asset catalog for proper setup

- [ ] Launch Screen
  - Current: Using default empty launch screen (Info.plist:30)
  - Optional: Create custom launch screen storyboard

#### Xcode Configuration
1. **Open project in Xcode:**
   ```bash
   open iosApp/iosApp.xcodeproj
   ```

2. **Configure in Xcode:**
   - Select project in navigator
   - Under "General":
     - Set Display Name: "DroidCon Uganda"
     - Set Bundle Identifier: com.droidcon.uganda (or your team ID prefix)
     - Select Team (requires Apple Developer account)
     - Set version: 1.0
     - Set build: 1
   - Under "Signing & Capabilities":
     - Enable "Automatically manage signing"
     - Select your team
   - Under "App Icons and Launch Images":
     - Set app icon to AppIcon

3. **Build Archive:**
   - Select "Any iOS Device" as target
   - Product → Archive
   - Wait for build to complete
   - Organizer window will open

4. **Distribute to App Store:**
   - Click "Distribute App"
   - Select "App Store Connect"
   - Select "Upload"
   - Follow prompts

#### Screenshots Required (App Store)
- [ ] 6.7" Display (iPhone 14 Pro Max or later):
  - 1290 x 2796 pixels
  - Minimum 1 screenshot required

- [ ] 5.5" Display (iPhone 8 Plus):
  - 1242 x 2208 pixels
  - Required if supporting older devices

- [ ] iPad Pro (12.9-inch) (optional):
  - 2048 x 2732 pixels

**Suggested screenshots (same as Android):**
1. Agenda screen
2. Session detail
3. Speakers list
4. Speaker detail
5. My Agenda
6. Day selector

#### App Store Connect Steps
1. Create app in App Store Connect
2. Fill app information:
   - Name: DroidCon Uganda 2025
   - Subtitle: Conference Schedule & Speakers
   - Privacy Policy URL (host PRIVACY_POLICY.md first)
   - Category: Productivity or Reference
   - Content Rights: Own or have rights
3. Add app description from STORE_LISTING.md
4. Add keywords (max 100 characters)
5. Upload screenshots
6. Add app icon (1024x1024)
7. Upload build from Xcode
8. Set pricing (Free)
9. Select availability (countries)
10. Submit for review

---

## 🔑 Critical Action Items

### MUST DO BEFORE SUBMISSION:

1. **Create App Icons** (CRITICAL - both platforms)
   - Design a 1024x1024 base icon
   - Use online tools or Android Studio/Xcode to generate all sizes
   - Icons must not use default placeholders

2. **Create Keystore** (Android)
   - Generate release keystore
   - Store securely (backup!)
   - NEVER commit to git

3. **Set up Apple Developer Account** (iOS)
   - $99/year required
   - Enable signing in Xcode

4. **Host Privacy Policy Online**
   - Upload PRIVACY_POLICY.md to website
   - Get public URL
   - Add URL to store listings

5. **Take Screenshots**
   - Run app on device/simulator
   - Capture all suggested screens
   - Use clean data (check spelling!)

6. **Test Release Builds Thoroughly**
   - Test on real devices
   - Test all features
   - Check performance
   - Verify no crashes

---

## 📋 Build Commands Summary

### Android
```bash
# Create keystore (one-time)
keytool -genkey -v -keystore droidcon-uganda-release.jks \
  -alias droidcon-release -keyalg RSA -keysize 2048 -validity 10000

# Build release AAB (for Play Store)
export KEYSTORE_PASSWORD=your_password
export KEY_PASSWORD=your_key_password
./gradlew :composeApp:bundleRelease

# Output: composeApp/build/outputs/bundle/release/composeApp-release.aab

# Test release build
./gradlew :composeApp:installRelease
```

### iOS
```bash
# Open Xcode
open iosApp/iosApp.xcodeproj

# In Xcode:
# 1. Select "Any iOS Device"
# 2. Product → Archive
# 3. Distribute → App Store Connect
```

---

## 📝 Helpful Resources

- [Android Publishing Guide](https://developer.android.com/studio/publish)
- [iOS App Distribution Guide](https://developer.apple.com/documentation/xcode/distributing-your-app-for-beta-testing-and-releases)
- [Google Play Console](https://play.google.com/console)
- [App Store Connect](https://appstoreconnect.apple.com)
- [Material Design Icons](https://fonts.google.com/icons) (for app icon inspiration)

---

## ⚠️ Important Notes

1. **Keystore Security**: Never lose your Android keystore! Back it up securely. You cannot update your app without it.

2. **Version Management**: Increment version code/build number for each release.

3. **Testing**: Always test release builds before submission. They behave differently than debug builds.

4. **Review Times**:
   - Google Play: Usually 1-3 days
   - App Store: Usually 1-2 days (can be longer for first submission)

5. **Privacy Policy**: Must be hosted online and accessible. Both stores require this for apps that store any data.

6. **App Icons**: Cannot use default system icons. Must be unique branded icons.

---

**Current Status: ✅ App Code Ready | ⚠️ Assets & Signing Needed**

The app code is production-ready. Complete the action items above to submit to stores.
