# DroidCon Uganda App - Deployment Guide v1.1.0

This guide will walk you through the process of building and deploying version 1.1.0 to both Google Play Store and Apple App Store.

---

## 📋 Pre-Release Checklist

- [x] Version numbers updated (Android: 2/1.1.0, iOS: 2/1.1.0)
- [x] Release notes prepared
- [x] ProGuard rules verified
- [ ] Signing credentials configured in `local.properties`
- [ ] Code reviewed and tested
- [ ] All features working as expected
- [ ] No hardcoded credentials in codebase

---

## 🤖 Android - Google Play Store Deployment

### Step 1: Verify Signing Configuration

Ensure your `local.properties` file (in project root) contains:

```properties
signing.storeFile=../droidcon-uganda-release.jks
signing.storePassword=YOUR_STORE_PASSWORD
signing.keyAlias=droidcon-uganda
signing.keyPassword=YOUR_KEY_PASSWORD
```

⚠️ **Important:** Make sure the keystore file exists at the specified path!

### Step 2: Build Release AAB (Android App Bundle)

Run the following command from the project root:

```bash
cd /Users/divyajain/DroidConUganda-KMP
./gradlew :composeApp:bundleRelease
```

The signed AAB will be generated at:
```
composeApp/build/outputs/bundle/release/composeApp-release.aab
```

### Step 3: Test Release Build (Optional but Recommended)

Before uploading, you can test the release build:

```bash
# Build release APK for testing
./gradlew :composeApp:assembleRelease

# The APK will be at:
# composeApp/build/outputs/apk/release/composeApp-release.apk
```

Install and test on a physical device:
```bash
adb install composeApp/build/outputs/apk/release/composeApp-release.apk
```

### Step 4: Upload to Google Play Console

1. Go to [Google Play Console](https://play.google.com/console)
2. Select "DroidCon Uganda" app
3. Navigate to **Production** → **Create new release**
4. Upload the AAB file: `composeApp-release.aab`
5. Add release notes from `STORE_RELEASE_NOTES.md` (Google Play section)
6. Review and rollout to production

### Play Store Release Notes (Copy this):

```
🎉 What's New in v1.1.0:

✨ NEW: Branded splash screen with DroidCon colors
🔍 NEW: Search sessions, speakers & topics instantly
⏰ NEW: Real-time session status (LIVE NOW, STARTING SOON, etc.)
🔄 NEW: Pull-to-refresh to get latest updates

🐛 Bug fixes:
- Fixed refresh spinner issue
- Improved performance & battery life
- Eliminated excessive background processing

Enjoy a smoother conference experience! 🚀
```

---

## 🍎 iOS - Apple App Store Deployment

### Step 1: Open Project in Xcode

```bash
cd /Users/divyajain/DroidConUganda-KMP
open iosApp/iosApp.xcodeproj
```

### Step 2: Verify Version Numbers in Xcode

1. Select the project in the navigator
2. Select the "iosApp" target
3. Go to the **General** tab
4. Verify:
   - **Version:** 1.1.0
   - **Build:** 2

These should already be updated in Info.plist, but verify in Xcode as well.

### Step 3: Select Release Scheme

1. In Xcode, click the scheme selector (next to the play button)
2. Click **Edit Scheme...**
3. Select **Run** → **Info** tab
4. Set **Build Configuration** to **Release**

### Step 4: Archive the App

1. Select **Product** → **Archive** from the menu
2. Wait for the archive process to complete
3. The Organizer window will open automatically

### Step 5: Distribute to App Store

1. In the Organizer, select the archive you just created
2. Click **Distribute App**
3. Select **App Store Connect**
4. Follow the wizard:
   - Upload
   - Include bitcode for iOS content: Yes
   - Upload symbols: Yes
   - Automatically manage signing: Yes (or use manual if configured)
5. Click **Upload**

### Step 6: Submit for Review in App Store Connect

1. Go to [App Store Connect](https://appstoreconnect.apple.com)
2. Select "DroidCon Uganda" app
3. Click the **+** button to create a new version (1.1.0)
4. Fill in the version information:
   - **What's New:** Copy from `STORE_RELEASE_NOTES.md` (App Store section)
5. Select the build you just uploaded
6. Submit for review

### App Store Release Notes (Copy this):

```
What's New in Version 1.1.0

This update brings major improvements to help you navigate DroidCon Uganda 2025 with ease!

✨ NEW FEATURES

• Branded Splash Screen
  Beautiful new launch screen featuring DroidCon Uganda's signature orange and green gradient with the Android robot icon.

• Smart Session Search
  Quickly find what you're looking for! Search across session titles, speaker names, tracks, and descriptions. Real-time results show exactly how many sessions match your query.

• Real-Time Session Status
  Never miss a session with live status badges:
  🔴 LIVE NOW - Session currently in progress
  🟠 STARTING SOON - Starting within 15 minutes
  🟢 UPCOMING - Future sessions
  ⚫ ENDED - Past sessions

  Status updates automatically every minute with timezone-aware calculations.

• Pull-to-Refresh
  Swipe down to instantly get the latest conference schedule updates.

🐛 BUG FIXES

• Fixed issue where the refresh spinner would continue spinning after data loaded
• Eliminated excessive background processing that caused performance issues
• Optimized timer implementation for better battery life
• Improved app responsiveness and reduced CPU usage

🎨 IMPROVEMENTS

• Enhanced visual design with prominent status badges
• Ended sessions now display with reduced opacity for better clarity
• Search results counter for better context
• Smoother animations throughout the app
• Improved session card layout and readability

🔒 SECURITY

• Enhanced credential management with improved security measures

Make the most of DroidCon Uganda 2025! 🚀

Questions or feedback? Visit us at the conference booth!
```

---

## 🧪 Testing Checklist

Before releasing, test these key features:

### Splash Screen
- [ ] Splash screen displays on app launch
- [ ] Gradient colors are correct (orange to green)
- [ ] Android robot icon is visible
- [ ] Smooth transition to main app

### Search Functionality
- [ ] Search bar is visible at top of Agenda screen
- [ ] Searching by session title works
- [ ] Searching by speaker name works
- [ ] Searching by track name works
- [ ] Search results counter is accurate
- [ ] Clear button clears search
- [ ] Empty state displays when no results

### Session Status Badges
- [ ] Status badges display on all sessions
- [ ] Colors are correct (red/orange/green/gray)
- [ ] Status updates automatically
- [ ] Ended sessions have reduced opacity

### Pull-to-Refresh
- [ ] Pull down gesture triggers refresh
- [ ] Spinner displays during refresh
- [ ] Spinner stops after data loads
- [ ] Data updates correctly

### General
- [ ] App launches without crashes
- [ ] Navigation works smoothly
- [ ] Favorites persist after app restart
- [ ] No performance issues or lag
- [ ] Battery drain is normal

---

## 📦 Build Commands Reference

### Android

```bash
# Clean build
./gradlew clean

# Build debug APK
./gradlew :composeApp:assembleDebug

# Build release APK
./gradlew :composeApp:assembleRelease

# Build release AAB (for Play Store)
./gradlew :composeApp:bundleRelease

# Install debug on device
./gradlew :composeApp:installDebug

# Run Android tests
./gradlew :composeApp:testDebugUnitTest
```

### iOS

```bash
# Clean build folders
rm -rf iosApp/build
rm -rf composeApp/build

# Build debug framework
./gradlew :composeApp:linkDebugFrameworkIosSimulatorArm64

# Build release framework
./gradlew :composeApp:linkReleaseFrameworkIosArm64
```

---

## 🚨 Troubleshooting

### Android Build Issues

**Issue:** "Keystore file not found"
- **Solution:** Verify the path in `local.properties` and ensure the .jks file exists

**Issue:** "Task :composeApp:bundleRelease FAILED"
- **Solution:** Run `./gradlew clean` and try again

**Issue:** ProGuard warnings/errors
- **Solution:** Check `proguard-rules.pro` and add necessary keep rules

### iOS Build Issues

**Issue:** "No signing certificate found"
- **Solution:** Configure signing in Xcode under Signing & Capabilities

**Issue:** "Framework not found ComposeApp"
- **Solution:** Run `./gradlew :composeApp:linkReleaseFrameworkIosArm64` first

**Issue:** Archive validation fails
- **Solution:** Ensure all frameworks are properly embedded and signed

---

## 📊 Post-Release Monitoring

After releasing to production:

1. **Monitor Crash Reports**
   - Google Play Console → Quality → Crashes & ANRs
   - App Store Connect → TestFlight → Crashes

2. **Check User Reviews**
   - Respond to user feedback within 24-48 hours
   - Address critical issues in hotfix if needed

3. **Monitor Performance**
   - App startup time
   - Screen load times
   - Battery usage
   - Network requests

4. **Analytics** (if configured)
   - User engagement metrics
   - Feature usage statistics
   - Session search queries

---

## 🔐 Security Reminders

- ✅ **Never** commit signing keys to git
- ✅ **Never** commit `local.properties` to git
- ✅ Keystore file should be stored securely
- ✅ Use different keys for debug and release
- ✅ Back up your keystore file in a secure location
- ✅ Document keystore passwords in secure password manager

---

## 📝 Version History

| Version | Date       | Build | Notes                          |
|---------|------------|-------|--------------------------------|
| 1.1.0   | 2025-10-11 | 2     | Search, status badges, splash  |
| 1.0     | -          | 1     | Initial release                |

---

## 🆘 Support

If you encounter issues during deployment:

1. Check this guide first
2. Review build logs for specific errors
3. Consult official documentation:
   - [Android App Bundle](https://developer.android.com/guide/app-bundle)
   - [Xcode Archive](https://developer.apple.com/documentation/xcode/distributing-your-app-for-beta-testing-and-releases)

---

**Good luck with the release! 🚀**

Built with ❤️ for DroidCon Uganda 2025
