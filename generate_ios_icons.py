#!/usr/bin/env python3

"""
iOS App Icon Generator for DroidCon Uganda
Generates all required iOS icon sizes from base icon
"""

import os
import subprocess
import sys
from pathlib import Path

# Icon sizes for iOS
IOS_SIZES = {
    'icon-20@2x.png': 40,
    'icon-20@3x.png': 60,
    'icon-29@2x.png': 58,
    'icon-29@3x.png': 87,
    'icon-40@2x.png': 80,
    'icon-40@3x.png': 120,
    'icon-60@2x.png': 120,
    'icon-60@3x.png': 180,
    'icon-20.png': 20,
    'icon-20@2x-ipad.png': 40,
    'icon-29.png': 29,
    'icon-29@2x-ipad.png': 58,
    'icon-40.png': 40,
    'icon-40@2x-ipad.png': 80,
    'icon-76.png': 76,
    'icon-76@2x.png': 152,
    'icon-83.5@2x.png': 167,
    'icon-1024.png': 1024,
}

def print_banner():
    print("🎨 DroidCon Uganda - iOS Icon Generator")
    print("=" * 50)
    print()

def check_pillow():
    """Check if Pillow is installed"""
    try:
        from PIL import Image
        return True
    except ImportError:
        return False

def install_pillow():
    """Install Pillow using pip"""
    print("📦 Installing Pillow (Python imaging library)...")
    try:
        subprocess.check_call([sys.executable, '-m', 'pip', 'install', 'Pillow'])
        print("✅ Pillow installed successfully!")
        return True
    except:
        print("❌ Failed to install Pillow")
        return False

def convert_svg_to_png(svg_path, png_path, size=1024):
    """Convert SVG to PNG using cairosvg or rsvg"""
    try:
        import cairosvg
        cairosvg.svg2png(url=str(svg_path), write_to=str(png_path),
                        output_width=size, output_height=size)
        return True
    except ImportError:
        pass

    # Try rsvg-convert command
    try:
        subprocess.check_call([
            'rsvg-convert',
            '-w', str(size),
            '-h', str(size),
            str(svg_path),
            '-o', str(png_path)
        ])
        return True
    except (subprocess.CalledProcessError, FileNotFoundError):
        pass

    return False

def resize_image(input_path, output_path, size):
    """Resize image using Pillow"""
    from PIL import Image

    img = Image.open(input_path)
    img = img.resize((size, size), Image.Resampling.LANCZOS)
    img.save(output_path, 'PNG')

def generate_icons(base_image_path, output_dir):
    """Generate all iOS icon sizes"""
    from PIL import Image

    print(f"🖼️  Generating iOS icons from: {base_image_path.name}")
    print()

    generated = []
    for filename, size in IOS_SIZES.items():
        output_path = output_dir / filename
        resize_image(base_image_path, output_path, size)
        generated.append(f"  ✅ {filename} ({size}x{size})")

    print("\n".join(generated))
    print()
    print(f"✅ Generated {len(generated)} icon sizes!")

def main():
    print_banner()

    # Setup paths
    script_dir = Path(__file__).parent
    icon_dir = script_dir / 'iosApp' / 'iosApp' / 'Assets.xcassets' / 'AppIcon.appiconset'
    svg_path = icon_dir / 'base-icon.svg'
    base_png_path = icon_dir / 'base-icon-1024.png'

    # Check if Pillow is installed
    if not check_pillow():
        print("❌ Pillow not found!")
        print()
        if input("Install Pillow? (y/n): ").lower() == 'y':
            if not install_pillow():
                print("\n⚠️  Manual installation:")
                print("  python3 -m pip install Pillow")
                sys.exit(1)
            # Import after installation
            from PIL import Image
        else:
            print("\nPlease install manually:")
            print("  python3 -m pip install Pillow")
            sys.exit(1)

    print("✅ Pillow is installed")
    print()

    # Convert SVG to PNG if needed
    if not base_png_path.exists():
        print("📐 Converting SVG to base PNG (1024x1024)...")
        if convert_svg_to_png(svg_path, base_png_path):
            print("✅ SVG converted to PNG")
        else:
            print("⚠️  Could not auto-convert SVG")
            print()
            print("Please create base-icon-1024.png manually:")
            print(f"  1. Open {svg_path} in browser")
            print("  2. Take screenshot or use online converter")
            print("  3. Save as: {base_png_path}")
            print("  4. Run this script again")
            print()
            print("Alternative: Use online converter:")
            print("  https://cloudconvert.com/svg-to-png")
            sys.exit(1)
    else:
        print(f"✅ Base PNG found: {base_png_path.name}")

    print()

    # Generate all iOS sizes
    generate_icons(base_png_path, icon_dir)

    print()
    print("🚀 Next steps:")
    print("  1. open iosApp/iosApp.xcodeproj")
    print("  2. Check Assets.xcassets → AppIcon")
    print("  3. Build and run (Cmd+R)")
    print()
    print("🎉 Your DroidCon Uganda iOS icons are ready!")

if __name__ == '__main__':
    main()
