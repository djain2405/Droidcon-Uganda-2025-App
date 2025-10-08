# Release Build Instructions

## Quick Start - Building for Production

### Prerequisites
- [x] Code is ready for submission
- [ ] App icons created (REQUIRED - see STORE_SUBMISSION_CHECKLIST.md)
- [ ] Android keystore generated (REQUIRED for Android)
- [ ] Apple Developer account active (REQUIRED for iOS)
- [ ] Privacy policy hosted online (REQUIRED for both stores)

---

## 🤖 Android Release Build

### Step 1: Generate Keystore (First Time Only)

```bash
cd /Users/divyajain/DroidConUganda-KMP

# Generate release keystore
keytool -genkey -v -keystore droidcon-uganda-release.jks \
  -alias droidcon-release \
  -keyalg RSA \
  -keysize 2048 \
  -validity 10000

# Follow prompts to set password and details
# IMPORTANT: Save password securely - you'll need it for every release!
```

### Step 2: Configure Signing

Edit `composeApp/build.gradle.kts` and update the signing config:

```kotlin
signingConfigs {
    create("release") {
        storeFile = file("../droidcon-uganda-release.jks")
        storePassword = System.getenv("KEYSTORE_PASSWORD") ?: "your_password"
        keyAlias = "droidcon-release"
        keyPassword = System.getenv("KEY_PASSWORD") ?: "your_key_password"
    }
}
buildTypes {
    getByName("release") {
        signingConfig = signingConfigs.getByName("release")
        isMinifyEnabled = true
        isShrinkResources = true
        proguardFiles(
            getDefaultProguardFile("proguard-android-optimize.txt"),
            "proguard-rules.pro"
        )
    }
}
```

### Step 3: Build AAB (Android App Bundle)

```bash
# Set passwords as environment variables (recommended)
export KEYSTORE_PASSWORD=your_store_password
export KEY_PASSWORD=your_key_password

# Build release AAB for Google Play
./gradlew :composeApp:bundleRelease

# Find output at:
# composeApp/build/outputs/bundle/release/composeApp-release.aab
```

### Step 4: Test Release Build

```bash
# Build and install release APK for testing
./gradlew :composeApp:assembleRelease
./gradlew :composeApp:installRelease

# Or manually install:
# adb install composeApp/build/outputs/apk/release/composeApp-release.apk
```

### Step 5: Upload to Google Play Console

1. Go to [Google Play Console](https://play.google.com/console)
2. Create new app or select existing app
3. Navigate to "Release" → "Production"
4. Click "Create new release"
5. Upload `composeApp-release.aab`
6. Fill release notes
7. Review and roll out

---

## 🍎 iOS Release Build

### Step 1: Open in Xcode

```bash
cd /Users/divyajain/DroidConUganda-KMP
open iosApp/iosApp.xcodeproj
```

### Step 2: Configure Project in Xcode

1. **Select project** (iosApp) in navigator
2. **Under "General" tab:**
   - Display Name: `DroidCon Uganda`
   - Bundle Identifier: `com.droidcon.uganda` (or with your team prefix)
   - Version: `1.0`
   - Build: `1`

3. **Under "Signing & Capabilities" tab:**
   - Check "Automatically manage signing"
   - Select your Team (requires Apple Developer account)
   - Verify provisioning profile is generated

4. **Under "Build Settings":**
   - Set "iOS Deployment Target" to desired minimum (e.g., iOS 15.0)

### Step 3: Archive the App

1. **Select build target:**
   - In toolbar, select "Any iOS Device (arm64)" or a connected device
   - Do NOT select a simulator

2. **Create archive:**
   - Menu: Product → Archive
   - Wait for build to complete (first time may take 5-10 minutes)
   - Xcode Organizer will open automatically

### Step 4: Validate Archive

In Xcode Organizer:
1. Select your archive
2. Click "Validate App"
3. Follow prompts
4. Wait for validation to complete
5. Fix any errors if shown

### Step 5: Distribute to App Store

1. Click "Distribute App"
2. Select "App Store Connect"
3. Select "Upload"
4. Choose signing options:
   - Recommended: "Automatically manage signing"
5. Review app info
6. Click "Upload"
7. Wait for upload to complete

### Step 6: Submit in App Store Connect

1. Go to [App Store Connect](https://appstoreconnect.apple.com)
2. Select "My Apps" → Your app
3. Click "+" to create new version
4. Fill all required information:
   - What's New (release notes)
   - Screenshots
   - Description (from STORE_LISTING.md)
   - Keywords
   - Support URL
   - Privacy Policy URL
5. Select the uploaded build
6. Set pricing and availability
7. Click "Submit for Review"

---

## 🔍 Testing Checklist

Before submitting, test thoroughly:

### Functional Testing
- [ ] All tabs work (Agenda, Speakers, My Agenda)
- [ ] Sessions load correctly
- [ ] Speakers load correctly
- [ ] Favorite/unfavorite works
- [ ] Favorites persist after app restart
- [ ] Session details dialog works
- [ ] Speaker details dialog works
- [ ] Day selector works (if multi-day)
- [ ] Timezone conversion displays correctly
- [ ] About dialog shows correct info
- [ ] Navigation between tabs is smooth
- [ ] All animations work smoothly

### Platform-Specific
- [ ] Android: Back button works correctly
- [ ] iOS: Swipe back gestures work
- [ ] Both: Orientation changes handled gracefully
- [ ] Both: App doesn't crash on any screen

### Performance
- [ ] App launches quickly
- [ ] No lag when scrolling lists
- [ ] No memory leaks
- [ ] Battery usage is reasonable

### Edge Cases
- [ ] Empty favorites state shows correctly
- [ ] Offline mode works (if data is cached)
- [ ] Large session lists scroll smoothly
- [ ] Dialog dismiss works properly

---

## 📊 Version Management

### For Each New Release:

**Android** (composeApp/build.gradle.kts):
```kotlin
versionCode = 2  // Increment by 1
versionName = "1.1"  // Semantic versioning
```

**iOS** (Info.plist or Xcode):
- CFBundleShortVersionString: `1.1` (user-facing)
- CFBundleVersion: `2` (build number)

---

## 🚨 Common Issues & Solutions

### Android

**Issue**: Build fails with "keystore not found"
- **Solution**: Ensure keystore path in build.gradle.kts is correct
- Check: `storeFile = file("../droidcon-uganda-release.jks")`

**Issue**: "Incorrect password" error
- **Solution**: Double-check KEYSTORE_PASSWORD and KEY_PASSWORD environment variables

**Issue**: ProGuard issues / ClassNotFound
- **Solution**: Check proguard-rules.pro, add -keep rules for affected classes

### iOS

**Issue**: "No signing identity found"
- **Solution**: Ensure you're logged into Xcode with Apple Developer account
- Check: Xcode → Preferences → Accounts

**Issue**: "Archive failed to build"
- **Solution**: Clean build folder (Product → Clean Build Folder)
- Try again: Product → Archive

**Issue**: Framework not found
- **Solution**: Ensure Kotlin framework builds correctly
- Try: `./gradlew :composeApp:embedAndSignAppleFrameworkForXcode`

---

## 📦 Files Generated

### Android
- AAB: `composeApp/build/outputs/bundle/release/composeApp-release.aab` (for Play Store)
- APK: `composeApp/build/outputs/apk/release/composeApp-release.apk` (for testing)

### iOS
- IPA: Created by Xcode during distribution (handled automatically)
- Archive: Stored in Xcode Organizer

---

## 🎯 Final Checklist Before Submission

- [ ] App icons created and added (both platforms)
- [ ] Privacy policy hosted online
- [ ] Screenshots captured (minimum required)
- [ ] Store listings written (descriptions, keywords)
- [ ] Release builds tested on real devices
- [ ] No crashes or critical bugs
- [ ] Version numbers set correctly
- [ ] Keystore backed up securely (Android)
- [ ] All store metadata ready
- [ ] Support email/URL configured

---

## 📞 Need Help?

- Android Issues: Check [Android Developer Docs](https://developer.android.com/studio/publish)
- iOS Issues: Check [Apple Developer Docs](https://developer.apple.com/documentation/xcode/distributing-your-app-for-beta-testing-and-releases)
- Kotlin Multiplatform: Check [KMP Docs](https://kotlinlang.org/docs/multiplatform.html)

**Your app is ready for submission! Just complete the asset requirements and you're good to go! 🚀**
