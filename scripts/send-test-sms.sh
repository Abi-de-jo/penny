#!/usr/bin/env bash
set -euo pipefail

SDK_ROOT="${ANDROID_SDK_ROOT:-${ANDROID_HOME:-$HOME/.local/android-sdk}}"
ADB="$SDK_ROOT/platform-tools/adb"
SERIAL="${ANDROID_SERIAL:-}"
SCENARIO="${1:-debit}"
[[ -n "$SERIAL" && "$SERIAL" == emulator-* ]] || { echo "Set ANDROID_SERIAL to the target emulator." >&2; exit 2; }
stamp=$(TZ=Asia/Kolkata date '+%d-%b-%Y %H:%M')
ref=$(date '+%s%N')
case "$SCENARIO" in
  debit) body="PENNYTEST: INR 250.00 debited from A/c XX4521 at CAFE on $stamp. Ref $ref. Avl Bal INR 10000.00." ;;
  credit) body="PENNYTEST: INR 5000.00 credited to A/c XX4521 on $stamp. Ref $ref. Avl Bal INR 15000.00." ;;
  duplicate) body="PENNYTEST: INR 250.00 debited from A/c XX4521 at CAFE on $stamp. Ref PENNYDUP001. Avl Bal INR 10000.00." ;;
  otp) body="PENNYTEST: OTP 123456 for INR 250.00 purchase. Do not share it." ;;
  *) echo "Usage: $0 debit|credit|duplicate|otp" >&2; exit 3 ;;
esac
"$ADB" -s "$SERIAL" emu sms send 9000000001 "$body"
echo "Injected synthetic $SCENARIO SMS into $SERIAL for testing."
