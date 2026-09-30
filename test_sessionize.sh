#!/bin/bash
# Test Sessionize API endpoints for the 2026 event.
# GridSmart may be empty until the schedule is announced. Accepted talks come from All.

EVENT_ID="bin6i3xe"

echo "======================================"
echo "Testing Sessionize API Integration"
echo "======================================"
echo ""

echo "1️⃣  Testing GridSmart endpoint (schedule)..."
echo "   URL: https://sessionize.com/api/v2/$EVENT_ID/view/GridSmart"
echo ""

GRID_RESPONSE=$(curl -s "https://sessionize.com/api/v2/$EVENT_ID/view/GridSmart")

if echo "$GRID_RESPONSE" | grep -q "document.write"; then
    echo "   ❌ ERROR: GridSmart endpoint is not configured!"
    exit 1
fi

GRID_STATS=$(printf '%s' "$GRID_RESPONSE" | python3 -c "
import json, sys
data = json.load(sys.stdin)
if not isinstance(data, list):
    raise SystemExit('not a list')
sessions = sum(len(room.get('sessions', [])) for day in data for room in day.get('rooms', []))
print(f'{len(data)} {sessions}')
" 2>/dev/null) || {
    echo "   ⚠️  WARNING: Unexpected response format from GridSmart"
    exit 1
}

GRID_DAYS=$(echo "$GRID_STATS" | awk '{print $1}')
GRID_SESSIONS=$(echo "$GRID_STATS" | awk '{print $2}')
if [ "$GRID_SESSIONS" = "0" ]; then
    echo "   ✅ GridSmart is empty. Schedule is not announced yet."
else
    echo "   ✅ GridSmart endpoint working!"
    echo "   Days: $GRID_DAYS, Total sessions: $GRID_SESSIONS"
fi

echo ""
echo "2️⃣  Testing All endpoint (accepted talks)..."
echo "   URL: https://sessionize.com/api/v2/$EVENT_ID/view/All"
echo ""

ALL_RESPONSE=$(curl -s "https://sessionize.com/api/v2/$EVENT_ID/view/All")
ALL_COUNT=$(printf '%s' "$ALL_RESPONSE" | python3 -c "
import json, sys
data = json.load(sys.stdin)
sessions = data.get('sessions', [])
titled = [s for s in sessions if s.get('title')]
print(len(titled))
" 2>/dev/null) || {
    echo "   ❌ ERROR: Unexpected response format from All"
    exit 1
}

if [ "$ALL_COUNT" -gt 0 ]; then
    echo "   ✅ All endpoint working!"
    echo "   Sessions with titles: $ALL_COUNT"
else
    echo "   ❌ ERROR: All endpoint returned no session titles"
    exit 1
fi

echo ""
echo "3️⃣  Testing Speakers endpoint..."
echo "   URL: https://sessionize.com/api/v2/$EVENT_ID/view/Speakers"
echo ""

SPEAKERS_RESPONSE=$(curl -s "https://sessionize.com/api/v2/$EVENT_ID/view/Speakers")
SPEAKER_COUNT=$(printf '%s' "$SPEAKERS_RESPONSE" | python3 -c "
import json, sys
data = json.load(sys.stdin)
named = [s for s in data if s.get('fullName')]
print(len(named))
" 2>/dev/null) || {
    echo "   ❌ ERROR: Unexpected response format from Speakers"
    exit 1
}

if [ "$SPEAKER_COUNT" -gt 0 ]; then
    echo "   ✅ Speakers endpoint working!"
    echo "   Total speakers: $SPEAKER_COUNT"
else
    echo "   ❌ ERROR: Speakers endpoint returned no names"
    exit 1
fi

echo ""
echo "======================================"
echo "✅ All Sessionize endpoints working!"
echo "======================================"
echo ""
echo "Your app will now:"
echo "  • Use GridSmart when the schedule is announced"
echo "  • Otherwise list accepted talks from All as unscheduled"
echo "  • Get speaker details with bios and photos"
echo ""
