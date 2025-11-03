#!/bin/bash
# Test Sessionize API endpoints
# Tests both GridSmart (for schedule) and Speakers (for speaker details)

EVENT_ID="re6do43h"  # DroidCon Uganda 2025

echo "======================================"
echo "Testing Sessionize API Integration"
echo "======================================"
echo ""

# Test GridSmart endpoint
echo "1️⃣  Testing GridSmart endpoint (schedule)..."
echo "   URL: https://sessionize.com/api/v2/$EVENT_ID/view/GridSmart"
echo ""

GRID_RESPONSE=$(curl -s "https://sessionize.com/api/v2/$EVENT_ID/view/GridSmart")

if echo "$GRID_RESPONSE" | grep -q "document.write"; then
    echo "   ❌ ERROR: GridSmart endpoint is not configured!"
    echo "   The endpoint is returning HTML/JavaScript instead of JSON."
    exit 1
elif echo "$GRID_RESPONSE" | grep -q '"date"'; then
    echo "   ✅ GridSmart endpoint working!"

    # Parse the response with Python for better stats
    if command -v python3 &> /dev/null; then
        GRID_STATS=$(python3 -c "
import sys, json
data = json.loads('''$GRID_RESPONSE''')
days = len(data)
rooms = len(data[0]['rooms']) if data else 0
sessions = sum(len(room['sessions']) for day in data for room in day['rooms'])
print(f'   Days: {days}, Rooms: {rooms}, Total sessions: {sessions}')
" 2>/dev/null)
        echo "$GRID_STATS"
    fi
else
    echo "   ⚠️  WARNING: Unexpected response format from GridSmart"
    exit 1
fi

echo ""

# Test Speakers endpoint
echo "2️⃣  Testing Speakers endpoint..."
echo "   URL: https://sessionize.com/api/v2/$EVENT_ID/view/Speakers"
echo ""

SPEAKERS_RESPONSE=$(curl -s "https://sessionize.com/api/v2/$EVENT_ID/view/Speakers")

if echo "$SPEAKERS_RESPONSE" | grep -q "document.write"; then
    echo "   ❌ ERROR: Speakers endpoint is not configured!"
    exit 1
elif echo "$SPEAKERS_RESPONSE" | grep -q '"fullName"'; then
    echo "   ✅ Speakers endpoint working!"

    # Count speakers
    if command -v python3 &> /dev/null; then
        SPEAKER_COUNT=$(python3 -c "
import sys, json
data = json.loads('''$SPEAKERS_RESPONSE''')
print(f'   Total speakers: {len(data)}')
" 2>/dev/null)
        echo "$SPEAKER_COUNT"
    fi
else
    echo "   ⚠️  WARNING: Unexpected response format from Speakers"
    exit 1
fi

echo ""
echo "======================================"
echo "✅ All Sessionize endpoints working!"
echo "======================================"
echo ""
echo "Your app will now:"
echo "  • Fetch complete schedule from GridSmart"
echo "  • Include service sessions (breaks, registration)"
echo "  • Get full speaker details with bios and photos"
echo "  • Display organized agenda by date and room"
echo ""
