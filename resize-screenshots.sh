#!/bin/bash
# Resize iPhone screenshots to App Store required dimensions
# From: 1320 x 2868 (iPhone 17 Pro Max)
# To: 1290 x 2796 (Required for App Store 6.7" display)

SOURCE_DIR="$HOME/Desktop"
OUTPUT_DIR="$HOME/Desktop/app-store-screenshots"

# Create output directory
mkdir -p "$OUTPUT_DIR"

echo "📱 Resizing iPhone screenshots for App Store submission"
echo "======================================================"
echo ""
echo "Source: $SOURCE_DIR"
echo "Output: $OUTPUT_DIR"
echo ""

# Counter
count=1

# Find and resize all iPhone 17 Pro Max screenshots from today
for file in "$SOURCE_DIR"/Simulator\ Screenshot\ -\ iPhone\ 17\ Pro\ Max\ -\ 2025-10-08*.png; do
    if [ -f "$file" ]; then
        # Create a simple numbered filename
        output_file="$OUTPUT_DIR/screenshot-$count.png"

        echo "Processing: $(basename "$file")"
        echo "  Original size: 1320 x 2868"
        echo "  Resizing to: 1290 x 2796"

        # Resize using sips
        sips -z 2796 1290 "$file" --out "$output_file" > /dev/null 2>&1

        if [ $? -eq 0 ]; then
            echo "  ✓ Saved as: screenshot-$count.png"
            ((count++))
        else
            echo "  ✗ Failed to resize"
        fi
        echo ""
    fi
done

echo "======================================================"
echo "✓ Done! Resized $((count - 1)) screenshots"
echo ""
echo "Screenshots saved to:"
echo "  $OUTPUT_DIR"
echo ""
echo "You can now upload these to App Store Connect!"
