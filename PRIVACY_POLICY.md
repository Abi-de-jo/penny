# Privacy Policy for Penny – Money Tracker

**Effective Date:** October 23, 2026  
**Application:** Penny – Money Tracker  
**Developer:** Abisheik (`Abi-de-jo`)  
**Contact:** `abisheikabisheik102@gmail.com`  

---

### Executive Summary
Penny is a 100% local-first, offline financial ledger. **Penny does not request the Android `INTERNET` permission.** It is technically impossible for your financial data, SMS alerts, transaction notes, or personal information to be transmitted outside your mobile device.

---

### 1. Data Storage & Security
- **Local SQLite Database:** All transaction records, titles, descriptions, and settings are stored locally on your device in `/data/data/com.penny.app/databases/penny.db`.
- **Zero Cloud Servers:** Penny does not operate any remote servers, databases, or user registration accounts.
- **No Internet Access:** Penny does not request `android.permission.INTERNET`. The operating system prevents the app from sending any bytes to external networks.

---

### 2. Permissions & Use Cases
1. **SMS Access (`RECEIVE_SMS`)**:
   - Used strictly on-device to capture incoming transaction debit/credit receipts from financial institutions.
   - Ignores all personal chats, non-financial messages, and OTPs. Penny never reads your past SMS inbox.
2. **Payment App Listener (`BIND_NOTIFICATION_LISTENER_SERVICE`)**:
   - Catches transaction alerts from Google Pay, PhonePe, Paytm, and bank apps on-device when banks do not deliver SMS.
   - Personal messaging notifications (WhatsApp, Telegram, Signal, etc.) are strictly filtered out and never read.
3. **Biometric Authentication (`BiometricPrompt`)**:
   - Deleting any transaction requires device confirmation (Fingerprint, Face unlock, or Lock Screen PIN) to prevent unauthorized deletion.

---

### 3. Zero Bank Credentials
Penny will never request:
- Bank net-banking passwords or usernames
- Debit/credit card PIN, CVV, or expiry dates
- UPI MPIN or payment passwords
- One-Time Passwords (OTPs)

---

### 4. Third-Party Trackers & Advertising
Penny contains **zero advertising SDKs, zero analytics trackers, and zero telemetry**. Data is never sold, shared, or monetized.

---

### 5. Data Deletion
- **Individual Deletion:** Swipe right on any movement and authenticate with your phone PIN or fingerprint to remove it.
- **Complete Wipe:** Go to *Settings > Delete all Penny data* to instantly wipe all records and preferences.
- **Uninstall:** Uninstalling the app permanently deletes the local SQLite database.

---

### 6. Contact
- **Email:** `abisheikabisheik102@gmail.com`
- **Developer GitHub:** `https://github.com/Abi-de-jo`
