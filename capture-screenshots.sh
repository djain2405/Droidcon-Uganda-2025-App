#!/bin/bash
# Script to capture App Store screenshots for iOS app

echo "📸 iOS App Store Screenshot Capture Tool"
echo "==========================================="
echo ""

# Required screenshot sizes for App Store
echo "Required screenshot sizes:"
echo "  - 6.7\" Display (iPhone 14 Pro Max): 1290 x 2796"
echo "  - 5.5\" Display (iPhone 8 Plus): 1242 x 2208"
echo "  - 12.9\" iPad Pro: 2048 x 2732"
echo ""

# Check if app is running
APP_BUNDLE="com.droidcon.uganda"

# Get booted simulator
BOOTED_SIM=$(xcrun simctl list devices | grep Booted | head -1)

if [ -z "$BOOTED_SIM" ]; then
    echo "❌ No simulator is currently running."
    echo "Please start a simulator and run the app first."
    exit 1
fi

echo "✓ Found booted simulator:"
echo "  $BOOTED_SIM"
echo ""

# Create screenshots directory
SCREENSHOTS_DIR="screenshots"
mkdir -p "$SCREENSHOTS_DIR"

echo "📂 Screenshots will be saved to: $SCREENSHOTS_DIR/"
echo ""
echo "Instructions:"
echo "============="
echo ""
echo "1. Navigate to the screen you want to capture in the simulator"
echo "2. Run this command to capture:"
echo "   xcrun simctl io booted screenshot $SCREENSHOTS_DIR/screen-1.png"
echo ""
echo "Recommended screenshots:"
echo "  1. Agenda screen with sessions list"
echo "  2. Session detail dialog"
echo "  3. Speakers list"
echo "  4. Speaker detail view"
echo "  5. My Agenda / Favorites screen"
echo "  6. Day selector view"
echo ""
echo "Quick capture commands:"
echo "----------------------"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/01-agenda.png"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/02-session-detail.png"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/03-speakers.png"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/04-speaker-detail.png"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/05-favorites.png"
echo "xcrun simctl io booted screenshot $SCREENSHOTS_DIR/06-day-selector.png"
echo ""
echo "After capturing all screenshots, you can resize them for different devices:"
echo "sips -Z 2796 $SCREENSHOTS_DIR/01-agenda.png --out $SCREENSHOTS_DIR/iphone-6.7/01-agenda.png"
echo ""
