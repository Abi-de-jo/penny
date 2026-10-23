#!/usr/bin/env bash
set -euo pipefail

SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/.local/android-sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
SERIAL="${ANDROID_SERIAL:-}"
OUTPUT="${1:-}"
[[ -n "$SERIAL" && "$SERIAL" == emulator-* ]] || { echo "Set ANDROID_SERIAL to the target emulator." >&2; exit 2; }
[[ -n "$OUTPUT" ]] || { echo "Usage: $0 output.png" >&2; exit 3; }
mkdir -p "$(dirname "$OUTPUT")"
"$ADB" -s "$SERIAL" exec-out screencap -p > "$OUTPUT"
echo "Captured $OUTPUT"
