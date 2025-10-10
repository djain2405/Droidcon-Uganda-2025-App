# iPad Screenshot Instructions

## Steps:

1. **iPad Pro 13-inch simulator is now running**

2. **Run your app in Xcode:**
   - Select "iPad Pro 13-inch (M4)" as the target device
   - Press Cmd+R to run

3. **Capture screenshots:**
   - Navigate to each screen in the app
   - Press **Cmd+S** in Simulator to save
   - Save to Desktop

4. **After capturing, run this command to resize:**

```bash
mkdir -p ~/Desktop/app-store-screenshots-ipad

# Resize iPad screenshots from 2064x2752 to 2048x2732
for file in ~/Desktop/Simulator\ Screenshot\ -\ iPad*.png; do
    if [ -f "$file" ]; then
        filename=$(basename "$file" | sed 's/Simulator Screenshot - //' | sed 's/ - 2025.*/.png/')
        sips -z 2732 2048 "$file" --out ~/Desktop/app-store-screenshots-ipad/"$filename"
        echo "✓ Resized: $filename"
    fi
done
```

## Recommended Screenshots (same as iPhone):
1. Agenda screen
2. Session detail
3. Speakers list
4. Speaker detail
5. My Agenda/Favorites

**Tip:** The iPad version will show more content due to larger screen!
