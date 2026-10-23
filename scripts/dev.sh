#!/usr/bin/env bash
set -euo pipefail

SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/.local/android-sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
SERIAL="${ANDROID_SERIAL:-}"
PACKAGE="${PENNY_PACKAGE:-com.penny.app}"

[[ -x "$ADB" ]] || { echo "adb not found at $ADB" >&2; exit 2; }
if [[ -z "$SERIAL" ]]; then
  mapfile -t emulators < <("$ADB" devices | awk '$1 ~ /^emulator-/ && $2 == "device" {print $1}')
  [[ ${#emulators[@]} -eq 1 ]] || { echo "Set ANDROID_SERIAL; expected exactly one running emulator." >&2; exit 3; }
  SERIAL="${emulators[0]}"
fi
[[ "$SERIAL" == emulator-* ]] || { echo "Refusing to select a physical device: $SERIAL" >&2; exit 4; }

ANDROID_SDK_ROOT="$SDK_ROOT" ./gradlew :app:assembleDebug
"$ADB" -s "$SERIAL" install -r -t app/build/outputs/apk/debug/app-debug.apk
"$ADB" -s "$SERIAL" shell am start -n "$PACKAGE/.MainActivity"
echo "Penny updated and opened on $SERIAL (app data preserved)."
