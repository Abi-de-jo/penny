#!/usr/bin/env bash
set -euo pipefail

SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/.local/android-sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
EMULATOR="$SDK_ROOT/emulator/emulator"
AVD="${PENNY_AVD:-Penny_Dev}"
AVD_HOME="${ANDROID_AVD_HOME:-$HOME/.config/.android/avd}"

[[ -x "$ADB" && -x "$EMULATOR" ]] || { echo "Android emulator tools are not installed under $SDK_ROOT" >&2; exit 2; }
if [[ -e /dev/kvm && ! -r /dev/kvm ]]; then echo "/dev/kvm is not accessible" >&2; exit 3; fi

serial="${ANDROID_SERIAL:-}"
if [[ -z "$serial" ]]; then serial=$("$ADB" devices | awk '$1 ~ /^emulator-/ && $2 == "device" {print $1; exit}'); fi
if [[ -z "$serial" ]]; then
  emulator_args=(-avd "$AVD" -no-boot-anim)
  [[ -n "${DISPLAY:-}" ]] || emulator_args+=(-no-window)
  [[ -e /dev/kvm ]] || emulator_args+=(-accel off -gpu swiftshader_indirect)
  ANDROID_AVD_HOME="$AVD_HOME" "$EMULATOR" "${emulator_args[@]}" >/tmp/penny-emulator.log 2>&1 &
  for _ in $(seq 1 120); do
    serial=$("$ADB" devices | awk '$1 ~ /^emulator-/ {print $1; exit}')
    [[ -n "$serial" ]] && break
    sleep 1
  done
fi
[[ -n "$serial" ]] || { echo "Emulator did not appear within 120 seconds; see /tmp/penny-emulator.log" >&2; exit 4; }
"$ADB" -s "$serial" wait-for-device
for _ in $(seq 1 180); do
  [[ $("$ADB" -s "$serial" shell getprop sys.boot_completed 2>/dev/null | tr -d '\r') == 1 ]] && { echo "$serial"; exit 0; }
  sleep 1
done
echo "Emulator $serial did not finish booting within 180 seconds" >&2
exit 5
