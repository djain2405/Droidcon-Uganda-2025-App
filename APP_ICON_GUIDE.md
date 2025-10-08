# App Icon Creation Guide

## ✅ What's Been Done

I've created the icon structure for both platforms using the **DroidCon Uganda brand colors**:
- **Primary Blue**: #30BDFF (rgb(48, 189, 255))
- **Secondary Blue**: #97DEFF (rgb(151, 222, 255))
- **White**: #FFFFFF for contrast

### Android Icons Created ✅
- ✅ Adaptive icon XML resources (API 26+)
- ✅ Vector drawable icons for all densities
- ✅ Background and foreground layers
- ✅ Legacy icons for older Android versions

### iOS Icon Structure Created ✅
- ✅ Assets.xcassets/AppIcon.appiconset created
- ✅ Contents.json configured for all required sizes

## 📱 Icon Design

The icons use a simple, recognizable design:
- **Background**: DroidCon brand blue (#30BDFF)
- **Foreground**: White Android robot symbol
- **Style**: Modern, flat design that works at all sizes

## 🎨 Option 1: Use Android Studio to Generate Bitmap Icons (Recommended)

Since XML icons are already created, you can optionally generate bitmap versions:

### For Android:
1. **Open Android Studio**
2. Right-click `composeApp/src/androidMain/res`
3. Select **New → Image Asset**
4. Configure:
   - **Asset Type**: Launcher Icons (Adaptive and Legacy)
   - **Name**: ic_launcher
   - **Foreground Layer**:
     - Source: Drawable
     - Path: Select `ic_launcher_foreground.xml`
   - **Background Layer**:
     - Source: Color
     - Color: #30BDFF
   - Or use drawable: `ic_launcher_background.xml`
5. **Click Next → Finish**

This will generate proper PNG files for all densities while keeping your vector drawables.

### For iOS:
The iOS icons need actual PNG files. You have 3 options:

## 🎨 Option 2: Online Icon Generator (Fastest)

Use an online tool to generate all sizes from a single 1024x1024 image:

### Step 1: Create Base Icon (1024x1024)

Use **Figma**, **Canva**, or **Photoshop**:

**Design specs:**
- Canvas: 1024 x 1024 px
- Background: #30BDFF (DroidCon blue)
- Icon: White Android robot or "DC" monogram
- Keep design within safe zone (900x900 center)

**Simple Design Ideas:**
1. **Android Robot**: White Android mascot on blue background
2. **DC Monogram**: Large "DC" letters in white on blue
3. **Droid + Uganda**: Android + Uganda flag colors
4. **Conference Badge**: Stylized conference badge shape

### Step 2: Generate All Sizes

Use one of these tools:

**Option A: AppIcon.co** (Recommended)
1. Go to https://appicon.co
2. Upload your 1024x1024 PNG
3. Select iOS and Android
4. Download generated files
5. Replace files in:
   - Android: `composeApp/src/androidMain/res/mipmap-*/`
   - iOS: `iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/`

**Option B: MakeAppIcon**
1. Go to https://makeappicon.com
2. Upload 1024x1024 icon
3. Download iOS and Android packages
4. Extract and copy to project

**Option C: Android Asset Studio**
1. Go to https://romannurik.github.io/AndroidAssetStudio/
2. Select "Launcher icon generator"
3. Upload or design icon
4. Download and extract to project

## 🎨 Option 3: Professional Design (Best Quality)

Hire a designer on:
- **Fiverr**: $5-50 for app icons
- **99designs**: Professional contest
- **Dribbble**: Find freelance designers

**Brief for designer:**
```
App Icon for DroidCon Uganda Conference App

Brand Colors:
- Primary: #30BDFF (cyan blue)
- Secondary: #97DEFF (light blue)
- Accent: #207EA9 (dark blue)

Style: Modern, flat, tech-focused
Theme: Android conference in Uganda
Elements: Android mascot, "DC" or "DroidCon" text
Size: 1024x1024 PNG (no transparency for Android)

Deliverables:
- 1024x1024 PNG (transparent background for iOS)
- 1024x1024 PNG (solid background for Android)
- All iOS sizes (see Contents.json)
- All Android densities (mdpi to xxxhdpi)
```

## 📋 Required iOS Icon Sizes (PNG files needed)

Place these in `iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/`:

| Filename | Size | Usage |
|----------|------|-------|
| icon-20@2x.png | 40x40 | iPhone Notification |
| icon-20@3x.png | 60x60 | iPhone Notification |
| icon-29@2x.png | 58x58 | iPhone Settings |
| icon-29@3x.png | 87x87 | iPhone Settings |
| icon-40@2x.png | 80x80 | iPhone Spotlight |
| icon-40@3x.png | 120x120 | iPhone Spotlight |
| icon-60@2x.png | 120x120 | iPhone App |
| icon-60@3x.png | 180x180 | iPhone App |
| icon-20.png | 20x20 | iPad Notification |
| icon-20@2x-ipad.png | 40x40 | iPad Notification |
| icon-29.png | 29x29 | iPad Settings |
| icon-29@2x-ipad.png | 58x58 | iPad Settings |
| icon-40.png | 40x40 | iPad Spotlight |
| icon-40@2x-ipad.png | 80x80 | iPad Spotlight |
| icon-76.png | 76x76 | iPad App |
| icon-76@2x.png | 152x152 | iPad App |
| icon-83.5@2x.png | 167x167 | iPad Pro |
| icon-1024.png | 1024x1024 | App Store |

## 🤖 Quick Script: Generate iOS Icons from 1024x1024

If you have ImageMagick installed:

```bash
cd /Users/divyajain/DroidConUganda-KMP/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset

# Place your 1024x1024 icon as "base-icon.png" in this directory, then run:

convert base-icon.png -resize 40x40 icon-20@2x.png
convert base-icon.png -resize 60x60 icon-20@3x.png
convert base-icon.png -resize 58x58 icon-29@2x.png
convert base-icon.png -resize 87x87 icon-29@3x.png
convert base-icon.png -resize 80x80 icon-40@2x.png
convert base-icon.png -resize 120x120 icon-40@3x.png
convert base-icon.png -resize 120x120 icon-60@2x.png
convert base-icon.png -resize 180x180 icon-60@3x.png
convert base-icon.png -resize 20x20 icon-20.png
convert base-icon.png -resize 40x40 icon-20@2x-ipad.png
convert base-icon.png -resize 29x29 icon-29.png
convert base-icon.png -resize 58x58 icon-29@2x-ipad.png
convert base-icon.png -resize 40x40 icon-40.png
convert base-icon.png -resize 80x80 icon-40@2x-ipad.png
convert base-icon.png -resize 76x76 icon-76.png
convert base-icon.png -resize 152x152 icon-76@2x.png
convert base-icon.png -resize 167x167 icon-83.5@2x.png
convert base-icon.png -resize 1024x1024 icon-1024.png
```

Install ImageMagick (if needed):
```bash
brew install imagemagick
```

## ✅ Current Status

### Android
- ✅ XML vector icons created (work on all devices)
- ✅ Adaptive icon configured (API 26+)
- ⚠️ Optional: Can add PNG bitmaps for better quality

### iOS
- ✅ Asset catalog structure created
- ✅ Contents.json configured
- ❌ PNG files needed (use online generator or designer)

## 🎯 Next Steps

1. **Create 1024x1024 base icon** using:
   - Figma/Canva (free)
   - Photoshop
   - Or hire designer

2. **Generate all sizes** using:
   - https://appicon.co (recommended)
   - https://makeappicon.com
   - ImageMagick script above

3. **Copy generated files** to:
   - `iosApp/iosApp/Assets.xcassets/AppIcon.appiconset/`

4. **Test in Xcode**:
   ```bash
   open iosApp/iosApp.xcodeproj
   # Build and run - check icon appears
   ```

5. **Test on Android**:
   ```bash
   ./gradlew :composeApp:installDebug
   # Check home screen icon
   ```

## 🎨 Design Resources

**Inspiration:**
- https://dribbble.com/search/conference-app-icon
- https://www.behance.net/search/projects/conference%20app%20icon

**Free Design Tools:**
- Figma: https://figma.com
- Canva: https://canva.com
- Photopea: https://photopea.com (Photoshop alternative)

**Icon Generators:**
- AppIcon.co: https://appicon.co
- MakeAppIcon: https://makeappicon.com
- Android Asset Studio: https://romannurik.github.io/AndroidAssetStudio/

**Colors to Use:**
- Primary: #30BDFF
- Secondary: #97DEFF
- Dark: #207EA9
- White: #FFFFFF

---

## 📱 Current Android Icons (Already Working!)

The XML-based icons are already functional. To see them:

```bash
./gradlew :composeApp:installDebug
```

The app will show with the blue Android robot icon on your device!

## 🍎 iOS Icons (Need PNG Files)

Use any method above to generate the PNG files, then:

```bash
open iosApp/iosApp.xcodeproj
# Build and run to test
```

---

**Recommended Quick Path:**
1. Create 1024x1024 icon in Canva (5 min)
2. Upload to https://appicon.co (1 min)
3. Download and extract files (1 min)
4. Copy to iOS Assets.xcassets (1 min)
5. Build and test! 🚀

**Total time: ~10 minutes** ⏱️
