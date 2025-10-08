#!/bin/bash

# iOS App Icon Generator for DroidCon Uganda
# This script generates all required iOS icon sizes from the base SVG

set -e

SCRIPT_DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"
ICON_DIR="$SCRIPT_DIR/iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"
BASE_ICON="$ICON_DIR/base-icon.svg"

echo "🎨 DroidCon Uganda - iOS Icon Generator"
echo "========================================"

# Check if ImageMagick is installed
if ! command -v convert &> /dev/null; then
    echo "❌ ImageMagick not found!"
    echo ""
    echo "Please install ImageMagick:"
    echo "  brew install imagemagick"
    echo ""
    echo "Or use the online method:"
    echo "1. Open base-icon.svg in browser"
    echo "2. Take screenshot at 1024x1024"
    echo "3. Visit https://appicon.co"
    echo "4. Upload PNG and generate iOS icons"
    echo "5. Copy generated files to: $ICON_DIR"
    exit 1
fi

# Check if librsvg is installed (for SVG support)
if ! command -v rsvg-convert &> /dev/null; then
    echo "⚠️  librsvg not found (needed for SVG conversion)"
    echo ""
    echo "Installing librsvg..."
    brew install librsvg || {
        echo "❌ Failed to install librsvg"
        echo ""
        echo "Alternative: Convert SVG to PNG manually:"
        echo "1. Open $BASE_ICON in browser"
        echo "2. Take screenshot at 1024x1024"
        echo "3. Save as base-icon-1024.png in $ICON_DIR"
        echo "4. Run this script again"
        exit 1
    }
fi

echo "✅ Tools found: ImageMagick & librsvg"
echo ""

# Check if base SVG exists
if [ ! -f "$BASE_ICON" ]; then
    echo "❌ Base icon not found: $BASE_ICON"
    exit 1
fi

cd "$ICON_DIR"

echo "📐 Converting SVG to base PNG (1024x1024)..."
rsvg-convert -w 1024 -h 1024 "$BASE_ICON" -o base-icon-1024.png

echo "🖼️  Generating iOS icon sizes..."

# iPhone Notification
convert base-icon-1024.png -resize 40x40 icon-20@2x.png
convert base-icon-1024.png -resize 60x60 icon-20@3x.png

# iPhone Settings
convert base-icon-1024.png -resize 58x58 icon-29@2x.png
convert base-icon-1024.png -resize 87x87 icon-29@3x.png

# iPhone Spotlight
convert base-icon-1024.png -resize 80x80 icon-40@2x.png
convert base-icon-1024.png -resize 120x120 icon-40@3x.png

# iPhone App
convert base-icon-1024.png -resize 120x120 icon-60@2x.png
convert base-icon-1024.png -resize 180x180 icon-60@3x.png

# iPad Notification
convert base-icon-1024.png -resize 20x20 icon-20.png
convert base-icon-1024.png -resize 40x40 icon-20@2x-ipad.png

# iPad Settings
convert base-icon-1024.png -resize 29x29 icon-29.png
convert base-icon-1024.png -resize 58x58 icon-29@2x-ipad.png

# iPad Spotlight
convert base-icon-1024.png -resize 40x40 icon-40.png
convert base-icon-1024.png -resize 80x80 icon-40@2x-ipad.png

# iPad App
convert base-icon-1024.png -resize 76x76 icon-76.png
convert base-icon-1024.png -resize 152x152 icon-76@2x.png

# iPad Pro
convert base-icon-1024.png -resize 167x167 icon-83.5@2x.png

# App Store
convert base-icon-1024.png -resize 1024x1024 icon-1024.png

echo ""
echo "✅ Icon generation complete!"
echo ""
echo "📱 Generated files:"
ls -lh *.png | awk '{print "   " $9 " (" $5 ")"}'

echo ""
echo "🚀 Next steps:"
echo "1. Open Xcode: open iosApp/iosApp.xcodeproj"
echo "2. Check Assets.xcassets → AppIcon"
echo "3. Build and run the app"
echo ""
echo "🎉 Your iOS icons are ready!"
