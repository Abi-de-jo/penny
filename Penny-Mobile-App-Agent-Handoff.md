# Penny — Android App Prototype and Agent Implementation Handoff

## 1. Instructions to the implementation agent

Build a working native Android prototype of **Penny**, a minimal personal money tracker for India. Use Kotlin, Jetpack Compose, and Google's Android SDK emulator. Complete the implementation, run it in the emulator, exercise its main flows, and report actual results.

The user should not have to download and manually install a new APK after each change. Configure a repeatable command that builds, updates the existing emulator installation, and opens the app. Reuse the same emulator and app data between iterations.

This document is the implementation brief. It is not evidence that the app has already been built or tested.

### Product in one sentence

Capture bank transaction SMS automatically; show credits and debits; ask for a short title only when money is spent, with an optional description.

### Priorities

1. Clean, minimal white-and-purple mobile interface.
2. Working SMS-to-transaction pipeline, including emulator SMS tests.
3. A quick expense naming experience.
4. Accurate daily totals and useful history.
5. Automatic emulator deployment for development.

Do not turn this into a banking dashboard. No payments, loans, investments, charts, subscriptions, advertisements, AI chat, account registration, or cloud backend in this prototype. No GPay login or merchant payment SDK is necessary. The data source is transaction SMS, regardless of whether payment happened through GPay, another UPI app, or a card.

## 2. Core behavior and platform constraint

The desired experience is: spend money → debit SMS arrives → amount is recorded → user names the expense.

Android restricts opening activities from the background. Implement this supported behavior:

| App state | New debit behavior |
| --- | --- |
| Foreground and idle | Save transaction, update totals, open the expense sheet. |
| Foreground while editing another expense | Queue the new item. Never replace the current form or lose typed text. |
| Background or screen locked | Save transaction and post a notification. A tap opens the expense sheet for that specific transaction. |
| Notifications denied | Save transaction; show it in the pending queue when the app is opened. |
| Credit arrives | Save it and update money-in totals. Do not open a spending form. |

Do not force-launch over another app, use accessibility automation, request overlay access, or use full-screen intents to imitate unrestricted auto-opening. The notification should read: **“₹250 spent. What was it for?”** with secondary text such as **“HDFC ••4521 · Tap to add a title.”** Heads-up presentation depends on channel settings and device behavior; do not guarantee it.

## 3. Visual direction

### Design tokens

| Token | Value | Usage |
| --- | --- | --- |
| Background | `#FFFFFF` | Main screens |
| Primary | `#7950E8` | Main buttons, active navigation, focus |
| Primary pressed | `#6540CC` | Pressed controls |
| Soft purple | `#F4EFFF` | Summary and selected chip surfaces |
| Main text | `#202024` | Headings and amounts |
| Secondary text | `#706B7C` | Supporting text; check contrast on actual surfaces |
| Border | `#ECE9F1` | Dividers and input borders |
| Credit text | `#23764B` | Optional restrained positive accent |
| Error | `#B3261E` | Validation |

Use the Android system font, or bundle Inter if already available. Do not rely on a network font. Main amount: 36–40sp; page title: 26–28sp; section title: 18sp; body: 15–16sp; supporting text: 12–14sp. Respect font scaling.

Use 20–24dp horizontal page padding, 16–20dp card padding, 16–20dp card corners, and 12–14dp input/button corners. Main buttons should be at least 52dp high. Use thin borders and almost no shadows. Use an 8dp spacing rhythm, 48dp minimum touch targets, and generous whitespace.

Use consistent outline icons: home, clock/history, settings, arrow, close, and a small receipt. Selected icons may use a filled version. No emoji as core interface icons, heavy gradients, illustrations, glass effects, large decorative charts, or dense nested cards.

### Bottom navigation

Use three evenly spaced items: **Home · History · Settings**. Instagram is a reference for placement and simplicity only. Keep labels visible. Selected item is purple; other items are muted. White background, subtle top divider, and correct system navigation insets. There is no center add button.

## 4. Home screen specification

Order from top to bottom:

1. Header: **Penny** on the left and today's date below it.
2. Large soft-purple summary card with **“Spent today”** and a large amount.
3. Two compact values within that card or directly below it: **“Money in”** and **“Money out”** for today.
4. Pending expense card when at least one debit needs a title.
5. **“Today”** transaction list, newest first.
6. Fixed bottom navigation.

### Summary semantics

- Money in: recorded credits for the displayed local calendar day, excluding identified own-account transfers.
- Money out: recorded debits for that day, excluding identified own-account transfers.
- Spent today: recorded purchase debits minus explicitly matched purchase refunds/reversals on that day. Show refund adjustments when applicable. An unmatched credit must not automatically cancel spending. Support a negative net-spending amount rather than silently clamping it.
- Untitled debits still count. Naming is annotation, not approval of a transaction.
- Cash movements cannot be inferred beyond their alerts. Do not claim complete financial coverage.
- Use **“Based on captured SMS”** as subtle supporting text.
- Do not label credits minus debits as a bank balance. A reliable account balance is out of scope.

### Pending card

Example: **“2 expenses need a title”**, then **“Add titles →”**. Tapping opens the oldest unnamed debit. After saving it, offer the next pending item without creating duplicate transactions. Dismissing leaves remaining items pending.

### Transaction rows

| State | Primary label | Secondary label | Amount |
| --- | --- | --- | --- |
| Named debit | Food | HDFC ••4521 · 1:30 PM | −₹250 |
| Unnamed debit | Add a title | HDFC ••4521 · 1:30 PM | −₹250 |
| Credit | Money received, or extracted sender | HDFC ••4521 · 9:00 AM | +₹5,000 |
| Own transfer | Transfer | Account and time | Direction and amount; transfer badge |
| Matched refund | Refund · Food | Account and time | +₹250 |

Use neutral debit text and restrained positive credit text, plus explicit signs. Never depend only on color. Tapping opens transaction details. Show a small pending indicator on unnamed debits.

### Empty and permission states

- No transactions: **“Your day starts here.”** / **“Transactions will appear when a bank SMS arrives.”**
- SMS denied: a compact **“SMS access is off”** card with **“Enable”**. Navigation and existing history still work.
- Notification denied: **“Expense reminders are off”** with a settings action; avoid repeatedly prompting.
- Midnight: recompute Today using the device's current time zone. Default emulator time zone should be Asia/Kolkata.

## 5. Expense sheet and transaction details

Use a modal bottom sheet with rounded top corners, a subtle drag handle, and enough height for the keyboard. Expand to a full-height form on small screens or large text settings if needed.

### New debit content

1. Small label: **“New expense”**.
2. Large amount: **₹250**.
3. Muted metadata: **HDFC ••4521 · Today, 1:30 PM**.
4. Heading: **“What was this for?”**
5. One required title input; placeholder: **“Food, petrol, groceries…”**.
6. Optional suggestion chips: **Food · Travel · Shopping · Other**. Tapping sets the editable title; it does not save automatically.
7. **“Add a note”** expands an optional multiline description, labeled **“Description (optional)”**.
8. Full-width purple **“Save expense”** button.
9. Small **“Later”** action.

### Validation and behavior

- Trim title; require 1–60 characters. Description: 0–300 characters.
- Disable save for a blank/whitespace title. Show an inline error if submitted through another action.
- Save updates the existing transaction. Amount, direction, account, and SMS timestamp are read-only.
- Guard against double taps. Disable save while persisting.
- Successful save dismisses the sheet, clears the pending state, and shows **“Expense saved”**.
- Later, swipe dismissal, or Back preserves the unnamed transaction. Preserve an in-progress draft locally across rotation and accidental dismissal; discard it after save or explicit clearing.
- Queue incoming debits while a form is active. Never stack sheets.
- Notification deep links identify a database transaction ID. Opening one must not insert another row.
- If it was already named, open its existing detail form. If missing/deleted, show a friendly message and return Home.

### Existing transaction details

Allow editing debit title/note and marking **“Transfer between my accounts”** with an undo action. Credits show read-only extracted details and may be marked as transfers. Permit explicit refund linking to a prior purchase through a small details action; do not guess every credit is a refund. Keep these secondary controls out of the initial naming form.

Credit card repayment pairing is beyond the basic parser unless confidently recognized and linked. Do not market total coverage of card repayment double counting until implemented and tested; unidentified debits remain visible and can be corrected.

## 6. History screen

Header: **History**. Horizontal filters: **Today · Yesterday · Last 7 days · Choose date**. Last 7 days includes today plus the six preceding local calendar days.

Display the selected date/range, money-in and money-out totals, then transactions grouped by date and sorted newest first. Use the same row component as Home. Date picker cannot select future dates. Tapping a row opens the same details sheet. Empty filter state: **“No transactions for this period.”** Preserve the chosen filter when switching tabs.

## 7. Settings and onboarding

Settings contains only:

- **SMS access**: actual permission status and an enable/settings action.
- **Expense notifications**: in-app preference plus actual Android permission/channel status. Explain when system settings override the preference.
- **Privacy**: **“Your transaction data stays on this device.”** Only say this when no remote data collection exists.
- **Delete all data**: confirmation that clears transactions, drafts, and stored settings as specified; cancel existing notifications too.
- **About**: version and short app description.
- Debug builds only: **Developer tools** with clearly labeled simulated transactions.

Onboarding should be one compact page: **“Know where your money goes.”** Explain transaction SMS and optional notifications, then request permissions in context. Allow **“Continue without access.”** Do not request contacts, location, call logs, accessibility, overlays, SMS sending, or bank credentials.

For the first prototype, receive future SMS only. Request `RECEIVE_SMS` for the incoming pipeline and `POST_NOTIFICATIONS` on supported Android versions. Add `READ_SMS` only if implementing a separately consented historical import; do not add it preemptively. A supported emulator image may require restricted-permission allowlisting during debug installation. Verify grants on the selected AVD rather than assuming a permission prompt proves access. Never require the app to become the default SMS handler just to bypass distribution policy.

Google Play restricts SMS permissions. Money-management apps may qualify for an exception, but publication needs the required declaration and review. Emulator operation does not demonstrate Play approval.

## 8. Native architecture

Use Kotlin, Compose/Material 3 with custom theme tokens, ViewModel + StateFlow, Room, DataStore preferences, and coroutines. Prefer a small single-module app with clear packages. No backend or internet permission is necessary for this scope.

Suggested structure:

```text
app/src/main/java/<package>/
  MainActivity.kt
  data/                 # Room entities, DAO, repository, settings
  sms/                  # BroadcastReceiver, PDU assembly, parser, normalizer
  notifications/        # Channel, notification factory, deep-link handling
  ui/theme/
  ui/home/
  ui/history/
  ui/settings/
  ui/transaction/
app/src/debug/           # Debug-only injection/demo implementation
app/src/test/            # Parser and total/deduplication tests
app/src/androidTest/     # Main user flow tests
scripts/                # Emulator, run, SMS fixtures, screenshot helpers
```

Use `Telephony.Sms.Intents.getMessagesFromIntent()` to assemble multipart SMS before parsing. Use the documented receiver declaration for the chosen target SDK. Validate incoming action and data, keep receiver work short with bounded `goAsync()` handling, and commit Room data off the main thread. Persistence must complete before posting its notification. Do not rely on an always-running foreground service or a UI process being alive.

Observe Room flows so totals and lists update automatically. Use lifecycle-aware state collection. Handle notification launch through both initial intent and `onNewIntent()` without duplicating a sheet.

Choose compatible stable Kotlin, AGP, Gradle, Compose, JDK, and SDK versions available in the environment. Record exact versions in the project README. Do not mix arbitrary latest versions or silently downgrade security/target settings to bypass behavior.

## 9. Transaction model and correctness

| Field | Meaning |
| --- | --- |
| id | Stable locally generated ID |
| amountMinor | Positive integer amount in paise; never floating-point money |
| currency | INR for prototype |
| direction | DEBIT or CREDIT |
| bank/accountKey/accountLast4 | Masked account identity; nullable when unavailable |
| merchantOrSender | Optional extracted counterparty |
| occurredAt / receivedAt | Instant values; keep both when SMS gives a transaction date |
| timeSource | Parsed SMS time or receive-time fallback |
| reference | Optional UPI/bank reference |
| title / description | User annotation, independent from extracted merchant |
| kind | PURCHASE, CREDIT, OWN_TRANSFER, REFUND, REVERSAL, or UNKNOWN |
| relatedTransactionId | Optional linked refund/reversal target |
| source | SMS or DEBUG_SIMULATION |
| fingerprint | Ingestion identity for exact redelivery protection |
| createdAt / updatedAt | Audit timestamps |

### Parser

- Use deterministic format-specific rules. Prototype supports the documented synthetic fixtures and a small declared set of real bank formats; do not claim universal bank support.
- Identify financial context and a positive transaction amount. Extract the transaction amount rather than available balance, OTP, outstanding bill amount, or credit limit.
- Recognize INR, Rs., Rs, and the rupee symbol, commas, and decimal paise.
- Reject OTPs, promotions, balance-only messages, payment requests, unpaid bills, declined transactions, and failed attempts without a confirmed debit.
- Keep ambiguous messages out of totals; report aggregate debug diagnostics without retaining unrelated messages.
- Real numeric senders are possible; sender allowlisting alone is insufficient. Use sender plus body rules and explicit parser adapters. Debug fixtures are not proof of bank authenticity.
- Retain normalized transaction fields; avoid retaining full SMS bodies. Never log OTPs or full personal financial messages. Use synthetic fixtures in test logs.

### Deduplication

Apply unique constraints/atomic upsert so concurrent or redelivered events cannot insert the same source event twice. Prefer reference plus account identity plus direction where available. For events lacking a reference, use normalized content and original SMS metadata to reject exact redelivery. Do not merge unrelated same-amount purchases just because their timestamps are close. Refund and debit directions are separate events even when references match.

Scope this version to SMS input. Notification-listener and statement imports are future work; do not add them now. If introduced later, reconcile their evidence against an existing transaction instead of counting each source separately.

## 10. Google Android SDK emulator workflow — required

### Goal

One-time SDK/AVD setup, followed by one development command per iteration. The emulator still installs an updated debug build when required; automation removes the user's manual APK transfer and installation steps. Compose previews or Android Studio Apply Changes can speed up some edits but do not replace all rebuilds or device testing.

### Environment checks before implementation

Inspect host OS, architecture, installed Android SDK, available AVDs, JDK, Gradle wrapper, disk space, and emulator acceleration. Reuse working installations and an existing suitable AVD. On Linux, verify KVM accessibility; on Apple Silicon choose an ARM64 image; on x86_64 choose an appropriate x86_64 image. Google APIs images are suitable; Play Store services are not required for Penny. Use a phone image with telephony/SMS support.

If virtualization, packages, or downloads are unavailable, report the exact blocker and complete all feasible source and local checks. Do not pretend a web preview or fixture-only screen validates the native SMS path. Do not ask the user to manually reinstall APKs as the default workaround.

### Tooling compatibility

Use Google's official Android SDK tools: platform-tools/ADB, SDK command-line tools, a compatible emulator/system image, and the Gradle wrapper. Current documentation also describes the newer `android emulator` CLI and deprecates direct emulator-tool management. Detect the installed toolchain and use its documented interface. The legacy SDK commands below are a compatibility example, not a requirement to install deprecated tooling.

### One-time setup example (Linux/macOS Bash)

Replace API, ABI, build-tools, SDK path, and device profile with values verified on the host. Avoid running placeholder values literally. Use the project's actual JDK requirement. Review SDK licenses through the normal SDK mechanism.

```bash
export ANDROID_HOME="/absolute/path/to/android-sdk"
export PATH="$ANDROID_HOME/platform-tools:$ANDROID_HOME/emulator:$ANDROID_HOME/cmdline-tools/latest/bin:$PATH"

sdkmanager --list
avdmanager list device

# Example only: select a stable installed/available image and record the choice.
sdkmanager "platform-tools" "emulator" "platforms;android-35" "system-images;android-35;google_apis;x86_64"
avdmanager create avd --name Penny_Dev --package "system-images;android-35;google_apis;x86_64"

emulator -list-avds
emulator -avd Penny_Dev -no-boot-anim
```

Keep the emulator running in a dedicated session. Use the corresponding documented create/start/list commands instead if the newer Android CLI is installed. Do not create a new AVD on every build, wipe data, or routinely use `gradlew clean`.

### Daily build/update/run example

Discover the emulator serial from `adb devices -l`; select explicitly when multiple devices exist. `emulator-5554` below is an example. Use a stable application ID and debug signing identity across updates.

```bash
export ANDROID_SERIAL="emulator-5554"
adb -s "$ANDROID_SERIAL" wait-for-device
adb -s "$ANDROID_SERIAL" shell getprop sys.boot_completed

# In the actual script: poll boot completion with a bounded timeout first.
./gradlew :app:assembleDebug

# Replace this package ID if the project uses another one.
# -r updates the installed app while preserving data; -t allows test packages.
adb -s "$ANDROID_SERIAL" install -r -t app/build/outputs/apk/debug/app-debug.apk
adb -s "$ANDROID_SERIAL" shell am start -n com.penny.app/.MainActivity
```

`./gradlew :app:installDebug` is another supported build-and-install path when device selection is configured correctly. Do not run both installation approaches unnecessarily. Do not uninstall to fix a signature conflict without explaining that it deletes local data; first reuse the original debug key or a separate debug application ID.

### Scripts the agent must deliver

| Script | Responsibility |
| --- | --- |
| `scripts/run-emulator.sh` | Reuse or start the selected AVD; validate acceleration; wait with timeout for boot; print chosen serial. |
| `scripts/dev.sh` | Check prerequisites, select emulator explicitly, build, update-install, and launch; stop on failures. |
| `scripts/send-test-sms.sh` | Send named synthetic scenarios using `adb emu sms send`; record that injection is a test action. |
| `scripts/capture-screen.sh` | Capture a PNG from the chosen emulator to a requested output path. |

Provide PowerShell equivalents if the host is Windows. Scripts must quote paths, return meaningful nonzero exit codes, and avoid silently choosing an arbitrary connected physical phone. Accept serial, AVD, and package as configuration. Preserve app data unless an explicit reset option is supplied.

### Simulated SMS through the actual emulator

Open the app once, complete onboarding, and grant the required runtime permissions. For isolated emulator-only automation, permission grants and any restricted SMS allowlisting may be set with documented ADB install/package options supported by that image. Still test the user-facing denied/granted flows separately. Never apply these debug shortcuts to a user's physical phone automatically.

```bash
adb -s "$ANDROID_SERIAL" emu sms send 9000000001 "PENNYTEST: INR 250.00 debited from A/c XX4521 at CAFE on 22-Oct-2026 13:30. Ref 626001001. Avl Bal INR 10000.00."

adb -s "$ANDROID_SERIAL" emu sms send 9000000001 "PENNYTEST: INR 5000.00 credited to A/c XX4521 on 22-Oct-2026 14:00. Ref 626001002. Avl Bal INR 15000.00."
```

`PENNYTEST` fixtures must be accepted only by a debug parser adapter, absent from release builds. Keep the normal receiver → parser → repository → UI/notification pipeline intact. The numeric sender is intentional for emulator telephony compatibility. Separately test real-format parser fixtures in unit tests.

For date-dependent tests, generate fixture dates from the emulator's current local date. Fixed examples above belong to 22 October 2026; do not expect them to appear in Today on another date. Set the emulator time zone to Asia/Kolkata through supported device settings and verify displayed dates.

The developer tools screen may inject the same fixtures directly into the pipeline for quick UI checks, but **at least one debit and credit test must arrive through emulator SMS delivery**. Label simulated records and keep developer tools out of release builds. Do not preseed fake transactions in the normal user experience.

## 11. Required emulator test matrix

| Scenario | Expected result |
| --- | --- |
| Fresh install | Onboarding; no seeded financial records. |
| SMS denied | No crash; honest disabled state; existing data accessible. |
| Debit ₹250, app visible | One pending debit, money-out +₹250, expense sheet opens. |
| Save title Food, no description | One named expense; no duplicate; amount unchanged. |
| Blank title | Cannot save; no data lost. |
| Later/dismiss | Debit still counted and pending. |
| Credit ₹5,000 | Money-in +₹5,000; no spending prompt. |
| Background debit | Stored without bringing activity forward; notification targets the right record. |
| Tap notification on cold/warm app | Correct expense opens in either case. |
| Notifications denied | Debit saved and visible as pending on next open. |
| Duplicate reference / exact redelivery | One transaction and one pending item. |
| Separate equal-amount purchases | Two transactions when references/events differ. |
| Amount vs available balance | ₹250 recognized as transaction, not ₹10,000 balance. |
| OTP/promotion/decline | No transaction or spending prompt. |
| Multipart SMS | One assembled transaction. |
| Debit while editing | Current text preserved; new debit queued. |
| Own transfer marking | Removed from spending/income aggregates as defined. |
| Linked refund/reversal | Linked event retained; appropriate spending adjustment; no silent deletion. |
| Midnight / yesterday / last 7 days | Correct local-date grouping and totals. |
| Rotation / restart | Saved records and drafts persist appropriately. |
| Update installation | Existing records survive `dev.sh` rebuild/update. |
| Delete all data | Records/drafts removed, notifications cancelled, empty state restored. |

Use parser/repository unit tests for money extraction, dates, duplicate handling, direction, transfers, and totals. Use Compose/instrumented tests for naming and history. Use actual emulator interaction for SMS delivery, permissions, background notifications, keyboard handling, and notification routing. Distinguish process death from user force-stop: Android may suppress delivery to a force-stopped app until the user launches it again; do not promise capture in that state.

## 12. Implementation sequence and completion criteria

1. Inspect the environment, choose compatible versions, and establish emulator deployment.
2. Build theme, navigation, Home, History, Settings, and the expense sheet using temporary debug fixtures.
3. Add Room persistence, exact money handling, totals, drafts, and annotation updates.
4. Add the SMS receiver, parser adapters, deduplication, and permission UX.
5. Add background notifications, exact transaction routing, and pending queues.
6. Exercise the test matrix; fix observed failures; verify a data-preserving update.
7. Deliver source, repeatable scripts, test results, and a short run guide.

Done means the agent has run the native app in Google's emulator and demonstrated the supported debit/credit flows. If emulator execution is blocked, label delivery **“implemented, emulator verification blocked”** with the exact blocker. Do not equate successful compilation with tested SMS capture.

Final agent report should include the chosen tool versions, AVD/image/serial, the single development command, successful checks, remaining limitations, and locations of source/test evidence. Capture emulator screenshots for Home, expense sheet with keyboard, History, Settings, and a synthetic debit notification using synthetic data only. Do not claim Google Play readiness or real-bank coverage without separate validation.

## 13. Official references

Consult current documentation when choosing SDK versions and permission behavior. These sources support tooling and platform constraints; all layout choices in this brief are product requirements.

- Build/install with Gradle: https://developer.android.com/build/building-cmdline
- Emulator startup and current CLI guidance: https://developer.android.com/studio/run/emulator-commandline
- Emulator console / SMS injection: https://developer.android.com/studio/run/emulator-console
- Android Debug Bridge: https://developer.android.com/tools/adb
- Background activity launch restrictions: https://developer.android.com/guide/components/activities/secure-bal
- SMS permission policy and money-management exception: https://support.google.com/googleplay/android-developer/answer/10208820
- SMS intent utilities: https://developer.android.com/reference/android/provider/Telephony.Sms.Intents
- Notification permission: https://developer.android.com/develop/ui/views/notifications/notification-permission

## 14. Copyable starting instruction

> Implement Penny according to this handoff. Start by inspecting the Android SDK and emulator environment. Build a native Kotlin/Compose app, keep the interface minimal and white/purple, capture debit and credit SMS, and ask for a required title plus optional description only for debits. Use a foreground sheet or a background notification tap according to Android restrictions. Deliver a single build/update/run command so I do not manually reinstall APKs after each iteration. Test actual SMS arrival in Google's Android emulator, preserve data across app updates, and report verified results and blockers honestly. Keep the scope to Home, History, Settings, and the expense sheet.
