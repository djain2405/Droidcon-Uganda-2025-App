#!/usr/bin/env python3
"""
Fix iOS app icons by removing alpha channel.
iOS app icons must be RGB (no transparency).
"""
import os
from PIL import Image

icons_dir = "iosApp/iosApp/Assets.xcassets/AppIcon.appiconset"

# Get all PNG files
png_files = [f for f in os.listdir(icons_dir) if f.endswith('.png')]

print(f"Found {len(png_files)} PNG files")

for filename in png_files:
    filepath = os.path.join(icons_dir, filename)

    try:
        # Open the image
        img = Image.open(filepath)

        # Check if it has alpha channel
        if img.mode in ('RGBA', 'LA') or (img.mode == 'P' and 'transparency' in img.info):
            print(f"Converting {filename} - removing alpha channel...")

            # Create a new RGB image with white background
            rgb_img = Image.new('RGB', img.size, (255, 255, 255))

            # Paste the image onto white background
            if img.mode == 'P':
                img = img.convert('RGBA')

            rgb_img.paste(img, mask=img.split()[-1] if img.mode == 'RGBA' else None)

            # Save without alpha
            rgb_img.save(filepath, 'PNG', optimize=True)
            print(f"  ✓ {filename} converted successfully")
        else:
            print(f"  → {filename} already RGB, skipping")

    except Exception as e:
        print(f"  ✗ Error processing {filename}: {e}")

print("\nAll icons processed!")
