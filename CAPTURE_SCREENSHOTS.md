# How to Capture App Store Screenshots

## Quick Method (Recommended)

### 1. Start the Right Simulator

For **iPhone screenshots** (required):
```bash
# iPhone 15 Pro Max (6.7" display - 1290 x 2796)
xcrun simctl boot "iPhone 15 Pro Max" 2>/dev/null || echo "Already booted"
open -a Simulator
```

For **iPad screenshots** (optional but recommended):
```bash
# iPad Pro 12.9" (2048 x 2732)
xcrun simctl boot "iPad Pro (12.9-inch) (6th generation)" 2>/dev/null || echo "Already booted"
open -a Simulator
```

### 2. Run Your App

Open Xcode and run the app on the simulator (Cmd+R)

### 3. Capture Screenshots

Navigate to each screen in the app, then press:
- **Cmd+S** in the Simulator to save screenshot, OR
- Use the command line:

```bash
# Create screenshots folder
mkdir -p screenshots/iphone screenshots/ipad

# Capture each screen (run after navigating to the screen in simulator)
xcrun simctl io booted screenshot screenshots/iphone/01-agenda.png
xcrun simctl io booted screenshot screenshots/iphone/02-session-detail.png
xcrun simctl io booted screenshot screenshots/iphone/03-speakers.png
xcrun simctl io booted screenshot screenshots/iphone/04-speaker-detail.png
xcrun simctl io booted screenshot screenshots/iphone/05-favorites.png
xcrun simctl io booted screenshot screenshots/iphone/06-day-selector.png
```

## Recommended Screenshots to Capture

Based on your app features, capture these screens:

### 1. **Agenda Screen** (Main screen)
- Shows the list of sessions for the day
- Demonstrates the clean UI and session cards
- Shows the date/day selector

### 2. **Session Detail**
- Click on a session to show the detail view
- Displays session title, speaker, time, description
- Shows the favorite/bookmark functionality

### 3. **Speakers List**
- Navigate to the speakers tab
- Shows all the speakers in a grid/list
- Demonstrates the speaker profiles

### 4. **Speaker Detail**
- Click on a speaker
- Shows speaker photo, bio, sessions
- Highlights the detailed information

### 5. **My Agenda / Favorites**
- Show the favorites/bookmarked sessions screen
- Demonstrates the personalization feature
- Shows how users can track their schedule

### 6. **Day Selector / Filter View**
- Show the day selection interface
- Or any filter/settings view if available
- Demonstrates app navigation

## Required Screenshot Sizes

### iPhone (Required)
- **6.7" Display**: 1290 x 2796 pixels (iPhone 14 Pro Max, 15 Pro Max)
  - Use: iPhone 15 Pro Max simulator
  - Screenshots will automatically be this size

### iPhone (Recommended for wider compatibility)
- **5.5" Display**: 1242 x 2208 pixels (iPhone 8 Plus)
  - Use: iPhone 8 Plus simulator
  - Or resize from 6.7" screenshots

### iPad (Optional but recommended)
- **12.9" iPad Pro**: 2048 x 2732 pixels
  - Use: iPad Pro 12.9" simulator

## Step-by-Step Process

### For iPhone 15 Pro Max (6.7" - Primary Screenshots)

1. **Boot simulator:**
   ```bash
   xcrun simctl boot "iPhone 15 Pro Max"
   open -a Simulator
   ```

2. **Run app in Xcode** (select iPhone 15 Pro Max as target)

3. **Create screenshots folder:**
   ```bash
   mkdir -p screenshots/iphone-6.7
   ```

4. **Navigate to each screen and capture:**
   - Navigate to Agenda screen
   - Press `Cmd+S` in Simulator
   - Save as: `screenshots/iphone-6.7/01-agenda.png`

   - Navigate to Session Detail
   - Press `Cmd+S`
   - Save as: `screenshots/iphone-6.7/02-session-detail.png`

   - (Repeat for all 6 screens)

### Alternative: Command Line Capture

After navigating to each screen:

```bash
# Screen 1: Agenda
xcrun simctl io booted screenshot screenshots/iphone-6.7/01-agenda.png

# Screen 2: Session Detail
xcrun simctl io booted screenshot screenshots/iphone-6.7/02-session-detail.png

# Screen 3: Speakers
xcrun simctl io booted screenshot screenshots/iphone-6.7/03-speakers.png

# Screen 4: Speaker Detail
xcrun simctl io booted screenshot screenshots/iphone-6.7/04-speaker-detail.png

# Screen 5: Favorites
xcrun simctl io booted screenshot screenshots/iphone-6.7/05-favorites.png

# Screen 6: Day Selector
xcrun simctl io booted screenshot screenshots/iphone-6.7/06-day-selector.png
```

### For iPhone 8 Plus (5.5" - Additional Screenshots)

1. **Switch simulator:**
   ```bash
   xcrun simctl shutdown booted
   xcrun simctl boot "iPhone 8 Plus"
   open -a Simulator
   ```

2. **Run app** (select iPhone 8 Plus as target in Xcode)

3. **Capture same screens:**
   ```bash
   mkdir -p screenshots/iphone-5.5

   # Repeat the same 6 screenshots
   xcrun simctl io booted screenshot screenshots/iphone-5.5/01-agenda.png
   # ... etc
   ```

### For iPad Pro 12.9"

1. **Switch to iPad simulator:**
   ```bash
   xcrun simctl shutdown booted
   xcrun simctl boot "iPad Pro (12.9-inch) (6th generation)"
   open -a Simulator
   ```

2. **Run app** (select iPad Pro as target)

3. **Capture screens:**
   ```bash
   mkdir -p screenshots/ipad-12.9

   xcrun simctl io booted screenshot screenshots/ipad-12.9/01-agenda.png
   # ... etc
   ```

## Tips for Great Screenshots

1. **Use realistic data**: Make sure the app shows actual session data
2. **Show key features**: Highlight what makes your app useful
3. **Clean UI**: Ensure no debug info or errors are visible
4. **Good timing**: Capture sessions that show interesting content
5. **Consistency**: Use the same time/day across screenshots when possible

## Uploading to App Store Connect

1. Go to App Store Connect
2. Select your app
3. Click on the version
4. Under "App Store" → "Screenshots"
5. Upload screenshots for each device size
6. You need minimum 1 screenshot per device size, maximum 10

## Screenshot File Size Limits

- Maximum file size: 500 MB per screenshot
- Format: PNG or JPEG (PNG recommended)
- Color space: sRGB or P3

---

**Quick Start:**
```bash
# 1. Boot iPhone 15 Pro Max
xcrun simctl boot "iPhone 15 Pro Max" && open -a Simulator

# 2. Run app in Xcode (Cmd+R)

# 3. Create screenshots folder
mkdir -p screenshots/iphone-6.7

# 4. Navigate to each screen and press Cmd+S in Simulator
```

Then upload the screenshots to App Store Connect!
