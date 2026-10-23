package com.penny.app.ui

import androidx.compose.runtime.staticCompositionLocalOf
import com.penny.app.data.PennyLanguage

interface PennyStrings {
    // Navigation & Global
    val appName: String
    val home: String
    val history: String
    val settings: String
    val today: String
    val yesterday: String
    val last7Days: String
    val dateRange: String
    val selectDateRange: String
    val applyRange: String
    val cancel: String
    val enable: String
    val disable: String
    val manage: String
    val delete: String
    val later: String
    val saveExpense: String
    val saving: String
    val transfer: String
    val pending: String
    val addTitle: String
    val moneyReceived: String
    val transactionDeleted: String
    val expenseSaved: String
    val movementsCount: (Int) -> String

    // Balance Card
    val todaysFlow: String
    val smsLive: String
    val spentToday: String
    val moneyInToday: String
    val inLabel: String
    val outLabel: String

    // Movement details sheet
    val newExpense: String
    val transactionDetails: String
    val whatWasThisFor: String
    val titlePlaceholder: String
    val foodChip: String
    val travelChip: String
    val shoppingChip: String
    val otherChip: String
    val addNote: String
    val descriptionLabel: String
    val transferBetweenAccounts: String

    // Settings
    val settingsTitle: String
    val settingsSubtitle: String
    val appearance: String
    val theme: String
    val themeSubtitle: String
    val systemTheme: String
    val lightTheme: String
    val darkTheme: String
    val languageSection: String
    val languageSubtitle: String
    val notificationsSection: String
    val expenseReminders: String
    val expenseRemindersSub: String
    val blockedByAndroid: String
    val pennyChime: String
    val pennyChimeSub: String
    val testNotification: String
    val androidSettings: String
    val accessSection: String
    val smsAccess: String
    val smsAccessOn: String
    val smsAccessOff: String
    val paymentListener: String
    val paymentListenerOn: String
    val paymentListenerOff: String
    val privacy: String
    val privacySub: String
    val about: String
    val aboutSub: String
    val deleteAllData: String
    val deleteConfirmTitle: String
    val deleteConfirmBody: String

    // Privacy Module (Enhanced & Comprehensive)
    val privacyTitle: String
    val localPhoneTitle: String
    val localPhoneDesc: String
    val noBankLoginTitle: String
    val noBankLoginDesc: String
    val futureAlertsTitle: String
    val futureAlertsDesc: String
    val paymentAppTitle: String
    val paymentAppDesc: String
    val biometricProtectionTitle: String
    val biometricProtectionDesc: String
    val permissionsTitle: String
    val smsPermTitle: String
    val smsPermDesc: String
    val notifPermTitle: String
    val notifPermDesc: String
    val paymentNotifTitle: String
    val paymentNotifDesc: String
    val privacyFooter: String

    // About Module (Enhanced & Comprehensive)
    val aboutTitle: String
    val aboutTagline: String
    val aboutMission: String
    val capabilitiesTitle: String
    val cap1Title: String
    val cap1Desc: String
    val cap2Title: String
    val cap2Desc: String
    val cap3Title: String
    val cap3Desc: String
    val cap4Title: String
    val cap4Desc: String
    val limitsTitle: String
    val limit1Title: String
    val limit1Desc: String
    val limit2Title: String
    val limit2Desc: String
    val techTitle: String
    val techDesc: String
}

object EnglishStrings : PennyStrings {
    override val appName = "Penny"
    override val home = "Home"
    override val history = "History"
    override val settings = "Settings"
    override val today = "Today"
    override val yesterday = "Yesterday"
    override val last7Days = "Last 7 days"
    override val dateRange = "Date range"
    override val selectDateRange = "Select date range"
    override val applyRange = "Apply range"
    override val cancel = "Cancel"
    override val enable = "Enable"
    override val disable = "Disable"
    override val manage = "Manage"
    override val delete = "Delete"
    override val later = "Later"
    override val saveExpense = "Save expense"
    override val saving = "Saving…"
    override val transfer = "Transfer"
    override val pending = "Pending"
    override val addTitle = "Add a title"
    override val moneyReceived = "Money received"
    override val transactionDeleted = "Transaction deleted"
    override val expenseSaved = "Expense saved"
    override val movementsCount: (Int) -> String = { count -> if (count == 1) "1 movement" else "$count movements" }

    override val todaysFlow = "TODAY'S FLOW"
    override val smsLive = "SMS LIVE"
    override val spentToday = "Spent today"
    override val moneyInToday = "Money in today"
    override val inLabel = "In"
    override val outLabel = "Out"

    override val newExpense = "New expense"
    override val transactionDetails = "Transaction details"
    override val whatWasThisFor = "What was this for?"
    override val titlePlaceholder = "Food, petrol, groceries…"
    override val foodChip = "Food"
    override val travelChip = "Travel"
    override val shoppingChip = "Shopping"
    override val otherChip = "Other"
    override val addNote = "Add a note"
    override val descriptionLabel = "Description (optional)"
    override val transferBetweenAccounts = "Transfer between my accounts"

    override val settingsTitle = "Settings"
    override val settingsSubtitle = "Make Penny feel right on this phone."
    override val appearance = "Appearance"
    override val theme = "Theme"
    override val themeSubtitle = "Follow the phone or choose a fixed appearance"
    override val systemTheme = "System"
    override val lightTheme = "Light"
    override val darkTheme = "Dark"
    override val languageSection = "Language"
    override val languageSubtitle = "Choose your preferred app language"
    override val notificationsSection = "Notifications"
    override val expenseReminders = "Expense reminders"
    override val expenseRemindersSub = "Prompt me to name new debits"
    override val blockedByAndroid = "Blocked by Android settings"
    override val pennyChime = "Penny chime"
    override val pennyChimeSub = "Play Penny’s two-note reminder"
    override val testNotification = "Test notification"
    override val androidSettings = "Android settings"
    override val accessSection = "Access"
    override val smsAccess = "SMS access"
    override val smsAccessOn = "On · new bank alerts can be captured"
    override val smsAccessOff = "Off · Penny cannot capture alerts"
    override val paymentListener = "Payment app listener"
    override val paymentListenerOn = "On · captures Google Pay, PhonePe, and Paytm alerts"
    override val paymentListenerOff = "Off · enables tracking when banks do not send SMS"
    override val privacy = "Privacy"
    override val privacySub = "Local storage, permissions, and data safety"
    override val about = "About"
    override val aboutSub = "Version, capabilities, and limits"
    override val deleteAllData = "Delete all Penny data"
    override val deleteConfirmTitle = "Delete all Penny data?"
    override val deleteConfirmBody = "This removes every transaction, title, draft, and setting from this phone. This cannot be undone."

    override val privacyTitle = "Privacy & Data Safety"
    override val localPhoneTitle = "100% Stored on this phone"
    override val localPhoneDesc = "Transactions, titles, notes, and settings stay in Penny’s local SQLite database. Penny does not declare the INTERNET permission, making external data leaks technically impossible."
    override val noBankLoginTitle = "Zero bank credentials"
    override val noBankLoginDesc = "Penny never asks for bank passwords, debit card PINs, OTPs, or UPI MPINs. It only processes transaction receipts already delivered to your device."
    override val futureAlertsTitle = "Future alerts only"
    override val futureAlertsDesc = "Penny starts tracking only after you grant permission. It never scans, uploads, or touches your historical SMS inbox."
    override val paymentAppTitle = "Private payment app listener"
    override val paymentAppDesc = "When enabled in Android Settings, Penny captures alerts from Google Pay, PhonePe, and Paytm on-device. Personal messaging notifications (WhatsApp, Telegram, SMS contacts) are strictly ignored."
    override val biometricProtectionTitle = "Biometric deletion lock"
    override val biometricProtectionDesc = "Deleting any movement requires your phone’s Fingerprint, Face unlock, or Screen Lock PIN to prevent accidental or unauthorized data loss."
    override val permissionsTitle = "Permissions"
    override val smsPermTitle = "SMS"
    override val smsPermDesc = "Used exclusively to recognize new debit and credit alerts from supported banks."
    override val notifPermTitle = "Notifications"
    override val notifPermDesc = "Used to remind you to name a new expense when a debit alert arrives."
    override val paymentNotifTitle = "Payment Notifications"
    override val paymentNotifDesc = "Allows on-device capture from UPI apps when banks do not deliver carrier SMS."
    override val privacyFooter = "Penny does not request internet, contacts, location, SMS sending, accessibility, overlay, or bank credentials."

    override val aboutTitle = "About Penny"
    override val aboutTagline = "A private, luminous money tracker."
    override val aboutMission = "Penny turns everyday bank and UPI alerts into a clean, readable financial ledger without manual data entry or cloud accounts."
    override val capabilitiesTitle = "What Penny Can Do"
    override val cap1Title = "Automatic transaction capture"
    override val cap1Desc = "Recognizes debit and credit alerts from major Indian banks and payment apps."
    override val cap2Title = "Daily cash flow & history"
    override val cap2Desc = "Aggregates daily spending and provides flexible date-range timelines."
    override val cap3Title = "Expense categorization"
    override val cap3Desc = "Quick chips and custom notes let you name spending while it is fresh."
    override val cap4Title = "Biometric security"
    override val cap4Desc = "Swipe-to-delete protected by your phone's hardware biometric prompt."
    override val limitsTitle = "Current Limitations"
    override val limit1Title = "No payment execution"
    override val limit1Desc = "Penny records money movement; it cannot initiate transfers, pay bills, or access bank accounts."
    override val limit2Title = "Alert format dependency"
    override val limit2Desc = "Accuracy depends on the format delivered by bank SMS and payment app push notifications."
    override val techTitle = "Architecture"
    override val techDesc = "Built natively with Jetpack Compose, Kotlin Coroutines, and local Room SQLite storage. Zero third-party analytics or tracking SDKs."
}

object TamilStrings : PennyStrings {
    override val appName = "பென்னி"
    override val home = "முகப்பு"
    override val history = "வரலாறு"
    override val settings = "அமைப்புகள்"
    override val today = "இன்று"
    override val yesterday = "நேற்று"
    override val last7Days = "கடந்த 7 நாட்கள்"
    override val dateRange = "தேதி வரம்பு"
    override val selectDateRange = "தேதி வரம்பைத் தேர்ந்தெடுக்கவும்"
    override val applyRange = "வரம்பைப் பயன்படுத்து"
    override val cancel = "ரத்து செய்"
    override val enable = "இயக்கு"
    override val disable = "முடக்கு"
    override val manage = "நிர்வகி"
    override val delete = "நீக்கு"
    override val later = "பிறகு"
    override val saveExpense = "செலவைச் சேமி"
    override val saving = "சேமிக்கிறது…"
    override val transfer = "பரிமாற்றம்"
    override val pending = "நிலுவை"
    override val addTitle = "தலைப்பு சேர்க்க"
    override val moneyReceived = "பணம் வரவு வைக்கப்பட்டது"
    override val transactionDeleted = "பரிவர்த்தனை நீக்கப்பட்டது"
    override val expenseSaved = "செலவு சேமிக்கப்பட்டது"
    override val movementsCount: (Int) -> String = { count -> if (count == 1) "1 பரிவர்த்தனை" else "$count பரிவர்த்தனைகள்" }

    override val todaysFlow = "இன்றைய வரவு-செலவு"
    override val smsLive = "SMS நேரலை"
    override val spentToday = "இன்றைய செலவு"
    override val moneyInToday = "இன்று வந்த பணம்"
    override val inLabel = "உள்வரவு"
    override val outLabel = "வெளிசெலவு"

    override val newExpense = "புதிய செலவு"
    override val transactionDetails = "பரிவர்த்தனை விவரங்கள்"
    override val whatWasThisFor = "இது எதற்காக செலவிடப்பட்டது?"
    override val titlePlaceholder = "உணவு, பெட்ரோல், மளிகை…"
    override val foodChip = "உணவு"
    override val travelChip = "பயணம்"
    override val shoppingChip = "ஷாப்பிங்"
    override val otherChip = "மற்றவை"
    override val addNote = "குறிப்பு சேர்"
    override val descriptionLabel = "விளக்கம் (விருப்பத்தேர்வு)"
    override val transferBetweenAccounts = "எனது கணக்குகளுக்கு இடையிலான பரிமாற்றம்"

    override val settingsTitle = "அமைப்புகள்"
    override val settingsSubtitle = "பென்னி செயலியை உங்கள் விருப்பத்திற்கேற்ப மாற்றவும்."
    override val appearance = "தோற்றம்"
    override val theme = "வண்ண முறை"
    override val themeSubtitle = "போன் அமைப்பைப் பின்பற்றவும் அல்லது நிலையான ஒன்றைத் தேர்ந்தெடுக்கவும்"
    override val systemTheme = "சிஸ்டம்"
    override val lightTheme = "வெளிச்சம்"
    override val darkTheme = "இருள்"
    override val languageSection = "மொழி"
    override val languageSubtitle = "உங்கள் விருப்ப மொழியைத் தேர்ந்தெடுக்கவும்"
    override val notificationsSection = "அறிவிப்புகள்"
    override val expenseReminders = "செலவு நினைவூட்டல்கள்"
    override val expenseRemindersSub = "புதிய செலவுகளுக்குப் பெயரிட நினைவூட்டு"
    override val blockedByAndroid = "Android அமைப்புகளில் தடுக்கப்பட்டுள்ளது"
    override val pennyChime = "பென்னி மணி ஒலி"
    override val pennyChimeSub = "பென்னியின் பிரத்யேக நினைவூட்டல் ஒலியை ஒலிக்கவும்"
    override val testNotification = "சோதனை அறிவிப்பு"
    override val androidSettings = "Android அமைப்புகள்"
    override val accessSection = "அனுமதிகள்"
    override val smsAccess = "SMS அனுமதி"
    override val smsAccessOn = "இயங்குகிறது · வங்கி எச்சரிக்கைகள் பெறப்படும்"
    override val smsAccessOff = "முடங்கியுள்ளது · எச்சரிக்கைகளைப் பெற முடியாது"
    override val paymentListener = "பணம் செலுத்தும் செயலிகள் கேட்பான்"
    override val paymentListenerOn = "இயங்குகிறது · Google Pay, PhonePe, Paytm பதிவாகும்"
    override val paymentListenerOff = "முடங்கியுள்ளது · SMS வராதபோதும் கண்காணிக்க உதவும்"
    override val privacy = "தனியுரிமை"
    override val privacySub = "உள்ளூர் சேமிப்பு, அனுமதிகள் மற்றும் தரவு பாதுகாப்பு"
    override val about = "செயலி பற்றி"
    override val aboutSub = "பதிப்பு, செயல்பாடுகள் மற்றும் வரம்புகள்"
    override val deleteAllData = "அனைத்து பென்னி தரவுகளையும் அழி"
    override val deleteConfirmTitle = "அனைத்து தரவுகளையும் அழிக்கவா?"
    override val deleteConfirmBody = "இது உங்கள் போனில் உள்ள அனைத்து பரிவர்த்தனைகள், தலைப்புகள், குறிப்புகள் மற்றும் அமைப்புகளை நிரந்தரமாக அழித்துவிடும். இதை மீட்டெடுக்க முடியாது."

    override val privacyTitle = "தனியுரிமை மற்றும் தரவு பாதுகாப்பு"
    override val localPhoneTitle = "100% இந்த போனிலேயே சேமிக்கப்படுகிறது"
    override val localPhoneDesc = "பரிவர்த்தனைகள், தலைப்புகள், குறிப்புகள் அனைத்தும் பென்னியின் உள்ளூர் SQLite தரவுத்தளத்தில் மட்டுமே இருக்கும். பென்னி இணைய அனுமதியை (INTERNET) கோருவதில்லை, எனவே தரவு எந்த சர்வருக்கும் செல்ல வாய்ப்பில்லை."
    override val noBankLoginTitle = "வங்கி உள்நுழைவு தேவையில்லை"
    override val noBankLoginDesc = "பென்னி உங்கள் வங்கி கடவுச்சொல், ஏடிஎம் கார்டு பின், OTP அல்லது UPI பின் எதையும் கேட்பதில்லை. உங்கள் போனுக்கு வரும் வரவு-செலவு எச்சரிக்கைகளை மட்டுமே படிக்கிறது."
    override val futureAlertsTitle = "எதிர்கால எச்சரிக்கைகள் மட்டுமே"
    override val futureAlertsDesc = "அனுமதி அளித்த பிறகு வரும் புதிய பரிவர்த்தனைகள் மட்டுமே பதியப்படும். உங்கள் பழைய SMS இன்பாக்ஸை பென்னி பார்க்கவோ படிக்கவோ செய்யாது."
    override val paymentAppTitle = "பாதுகாப்பான கட்டணச் செயலி கண்காணிப்பு"
    override val paymentAppDesc = "இயக்கப்பட்டதும், Google Pay, PhonePe, Paytm செயலிகளின் கட்டண எச்சரிக்கைகளை மட்டுமே போனில் படிக்கும். தனிப்பட்ட வாட்ஸ்அப், டெலிகிராம், எஸ்எம்எஸ் போன்ற தனிநபர் உரையாடல்கள் படிக்கப்படாது."
    override val biometricProtectionTitle = "கைரேகை / PIN பாதுகாப்பு"
    override val biometricProtectionDesc = "பரிவர்த்தனையை நீக்க உங்கள் போனின் கைரேகை, முகம் அல்லது லாக் ஸ்கிரீன் PIN தேவைப்படும். தவறுதலாக நீக்கப்படுவதை இது தடுக்கும்."
    override val permissionsTitle = "அனுமதிகள்"
    override val smsPermTitle = "SMS"
    override val smsPermDesc = "வங்கி வரவு, செலவு எச்சரிக்கைகளைத் தானாகப் பதிவு செய்ய மட்டுமே பயன்படுகிறது."
    override val notifPermTitle = "அறிவிப்புகள்"
    override val notifPermDesc = "புதிய செலவுகளுக்கு விரைவாகப் பெயரிட உங்களுக்கு நினைவூட்டப் பயன்படுகிறது."
    override val paymentNotifTitle = "கட்டண அறிவிப்புகள்"
    override val paymentNotifDesc = "வங்கிகள் SMS அனுப்பாதபோது UPI செயலிகளின் அறிவிப்புகளிலிருந்து செலவுகளைப் பதிய உதவும்."
    override val privacyFooter = "பென்னி இணையம், தொடர்புகள், இருப்பிடம், எஸ்எம்எஸ் அனுப்புதல் அல்லது வங்கி உள்நுழைவு எதையும் கோருவதில்லை."

    override val aboutTitle = "பென்னி பற்றி"
    override val aboutTagline = "தனித்துவமான, பாதுகாப்பான செலவு கண்காணிப்பான்."
    override val aboutMission = "பென்னி உங்கள் வங்கி மற்றும் UPI எச்சரிக்கைகளை அழகான, எளிதில் புரியக்கூடிய வரவு-செலவு கணக்காக மாற்றுகிறது. கைமுறை பதிவோ அல்லது கிளவுட் கணக்குகளோ தேவையில்லை."
    override val capabilitiesTitle = "பென்னியின் சிறப்பம்சங்கள்"
    override val cap1Title = "தானியங்கி பரிவர்த்தனை பதிவு"
    override val cap1Desc = "இந்திய வங்கிகள் மற்றும் UPI செயலிகளின் வரவு-செலவு எச்சரிக்கைகளைத் துல்லியமாக அடையாளம் காண்கிறது."
    override val cap2Title = "தினசரி செலவு மற்றும் வரலாறு"
    override val cap2Desc = "தினசரி செலவுகளின் கூட்டுத்தொகை மற்றும் தேதி வரம்பு வாரியான முழு வரலாற்றையும் காட்டுகிறது."
    override val cap3Title = "செலவு பிரிவுகள் மற்றும் குறிப்புகள்"
    override val cap3Desc = "செலவு செய்த உடனே எளிதாக தலைப்பு மற்றும் குறிப்புகளைச் சேர்க்கலாம்."
    override val cap4Title = "கைரேகை பாதுகாப்பு"
    override val cap4Desc = "பரிவர்த்தனையை வலதுபுறம் ஸ்வைப் செய்து கைரேகை மூலம் பாதுகாப்பாக நீக்கலாம்."
    override val limitsTitle = "தற்போதைய வரம்புகள்"
    override val limit1Title = "பணம் அனுப்ப முடியாது"
    override val limit1Desc = "பென்னி வரவு-செலவை கணக்கிட மட்டுமே செய்கிறது; இதன் மூலம் பணம் அனுப்பவோ கட்டணம் செலுத்தவோ முடியாது."
    override val limit2Title = "எச்சரிக்கை வடிவமைப்பு சார்ந்தது"
    override val limit2Desc = "வங்கி மற்றும் செயலிகள் அனுப்பும் எஸ்எம்எஸ் மற்றும் அறிவிப்புகளின் வார்த்தை அமைப்பைச் சார்ந்து அமையும்."
    override val techTitle = "தொழில்நுட்பம்"
    override val techDesc = "முழுக்க முழுக்க Jetpack Compose, Kotlin மற்றும் உள்ளூர் Room SQLite கொண்டு உருவாக்கப்பட்டது. எந்த மூன்றாம் தரப்பு கண்காணிப்பு SDK-களும் இல்லை."
}

object HindiStrings : PennyStrings {
    override val appName = "पेनी"
    override val home = "होम"
    override val history = "इतिहास"
    override val settings = "सेटिंग्स"
    override val today = "आज"
    override val yesterday = "कल"
    override val last7Days = "पिछले 7 दिन"
    override val dateRange = "तिथि सीमा"
    override val selectDateRange = "तिथि सीमा चुनें"
    override val applyRange = "सीमा लागू करें"
    override val cancel = "रद्द करें"
    override val enable = "सक्षम करें"
    override val disable = "अक्षम करें"
    override val manage = "प्रबंधित करें"
    override val delete = "हटाएं"
    override val later = "बाद में"
    override val saveExpense = "खर्च सहेजें"
    override val saving = "सहेज रहा है…"
    override val transfer = "स्थानांतरण"
    override val pending = "लंबित"
    override val addTitle = "शीर्षक जोड़ें"
    override val moneyReceived = "पैसे प्राप्त हुए"
    override val transactionDeleted = "लेन-देन हटाया गया"
    override val expenseSaved = "खर्च सहेजा गया"
    override val movementsCount: (Int) -> String = { count -> if (count == 1) "1 लेन-देन" else "$count लेन-देन" }

    override val todaysFlow = "आज का लेन-देन"
    override val smsLive = "SMS लाइव"
    override val spentToday = "आज का खर्च"
    override val moneyInToday = "आज आए पैसे"
    override val inLabel = "आवक"
    override val outLabel = "जावक"

    override val newExpense = "नया खर्च"
    override val transactionDetails = "लेन-देन का विवरण"
    override val whatWasThisFor = "यह किस लिए खर्च हुआ?"
    override val titlePlaceholder = "खाना, पेट्रोल, राशन…"
    override val foodChip = "खाना"
    override val travelChip = "यात्रा"
    override val shoppingChip = "खरीदारी"
    override val otherChip = "अन्य"
    override val addNote = "नोट जोड़ें"
    override val descriptionLabel = "विवरण (वैकल्पिक)"
    override val transferBetweenAccounts = "मेरे खातों के बीच स्थानांतरण"

    override val settingsTitle = "सेटिंग्स"
    override val settingsSubtitle = "पेनी को इस फोन पर अपने अनुसार बनाएं।"
    override val appearance = "दिखावट"
    override val theme = "थीम"
    override val themeSubtitle = "फोन के अनुसार रखें या अपनी पसंद से चुनें"
    override val systemTheme = "सिस्टम"
    override val lightTheme = "लाइट"
    override val darkTheme = "डार्क"
    override val languageSection = "भाषा"
    override val languageSubtitle = "अपनी पसंदीदा भाषा चुनें"
    override val notificationsSection = "सूचनाएं"
    override val expenseReminders = "खर्च की याद दिलाएं"
    override val expenseRemindersSub = "नए खर्चों को नाम देने के लिए याद दिलाएं"
    override val blockedByAndroid = "Android सेटिंग्स द्वारा अवरोधित"
    override val pennyChime = "पेनी की घंटी"
    override val pennyChimeSub = "पेनी की दो-नोट वाली याददाश्त धुन बजाएं"
    override val testNotification = "परीक्षण सूचना"
    override val androidSettings = "Android सेटिंग्स"
    override val accessSection = "पहुंच"
    override val smsAccess = "SMS पहुंच"
    override val smsAccessOn = "चालू · बैंक अलर्ट कैप्चर किए जा सकते हैं"
    override val smsAccessOff = "बंद · पेनी अलर्ट कैप्चर नहीं कर सकता"
    override val paymentListener = "पेमेंट ऐप लिसनर"
    override val paymentListenerOn = "चालू · Google Pay, PhonePe, Paytm अलर्ट रिकॉर्ड होंगे"
    override val paymentListenerOff = "बंद · बैंक द्वारा SMS न भेजने पर भी ट्रैकिंग सक्षम करता है"
    override val privacy = "गोपनीयता"
    override val privacySub = "लोकल स्टोरेज, अनुमतियां और डेटा सुरक्षा"
    override val about = "ऐप के बारे में"
    override val aboutSub = "संस्करण, क्षमताएं और सीमाएं"
    override val deleteAllData = "पेनी का सारा डेटा हटाएं"
    override val deleteConfirmTitle = "सारा डेटा हटाएं?"
    override val deleteConfirmBody = "यह आपके फोन से सभी लेन-देन, शीर्षक, नोट्स और सेटिंग्स को स्थायी रूप से हटा देगा। इसे पूर्ववत नहीं किया जा सकता।"

    override val privacyTitle = "गोपनीयता और डेटा सुरक्षा"
    override val localPhoneTitle = "100% इसी फोन पर संग्रहीत"
    override val localPhoneDesc = "लेन-देन, शीर्षक, नोट्स और सेटिंग्स केवल पेनी के लोकल SQLite डेटाबेस में रहते हैं। पेनी में कोई इंटरनेट अनुमति नहीं है, इसलिए डेटा का फोन से बाहर जाना तकनीकी रूप से असंभव है।"
    override val noBankLoginTitle = "कोई बैंक लॉगिन नहीं"
    override val noBankLoginDesc = "पेनी कभी भी आपका बैंक पासवर्ड, डेबिट कार्ड पिन, ओटीपी या यूपीआई पिन नहीं मांगता। यह केवल आपके फोन पर प्राप्त होने वाली लेन-देन रसीदों को पढ़ता है।"
    override val futureAlertsTitle = "केवल आने वाले अलर्ट"
    override val futureAlertsDesc = "पेनी अनुमति मिलने के बाद आने वाले नए अलर्ट ही रिकॉर्ड करता है। यह आपके पुराने एसएमएस इनबॉक्स को कभी नहीं पढ़ता।"
    override val paymentAppTitle = "सुरक्षित पेमेंट ऐप ट्रैकिंग"
    override val paymentAppDesc = "सक्षम होने पर, पेनी केवल Google Pay, PhonePe और Paytm जैसी वित्तीय ऐप्स के नोटिफिकेशन को ऑन-डिवाइस प्रोसेस करता है। निजी चैट (WhatsApp, Telegram आदि) कभी नहीं पढ़ी जाती हैं।"
    override val biometricProtectionTitle = "बायोमेट्रिक सुरक्षा"
    override val biometricProtectionDesc = "किसी भी लेन-देन को हटाने के लिए आपके फोन के फिंगरप्रिंट या स्क्रीन लॉक पिन की आवश्यकता होती है, जिससे आकस्मिक डेटा हानि से बचा जा सके।"
    override val permissionsTitle = "अनुमतियां"
    override val smsPermTitle = "SMS"
    override val smsPermDesc = "केवल समर्थित बैंकों से डेबिट और क्रेडिट अलर्ट पहचानने के लिए उपयोग किया जाता है।"
    override val notifPermTitle = "सूचनाएं"
    override val notifPermDesc = "नया खर्च होने पर आपको नाम जोड़ने की याद दिलाने के लिए उपयोग किया जाता है।"
    override val paymentNotifTitle = "पेमेंट नोटिफिकेशन"
    override val paymentNotifDesc = "जब बैंक एसएमएस नहीं भेजते, तब यूपीआई ऐप्स से लेन-देन रिकॉर्ड करने में मदद करता है।"
    override val privacyFooter = "पेनी इंटरनेट, कॉन्टैक्ट्स, लोकेशन, एसएमएस भेजने या बैंक क्रेडेंशियल्स की मांग नहीं करता है।"

    override val aboutTitle = "पेनी के बारे में"
    override val aboutTagline = "एक निजी और सुंदर मनी ट्रैकर।"
    override val aboutMission = "पेनी आपके रोज़मर्रा के बैंक और यूपीआई अलर्ट को बिना किसी मैन्युअल प्रविष्टि या क्लाउड खाते के एक साफ-सुथरे वित्तीय लेज़र में बदलता है।"
    override val capabilitiesTitle = "पेनी क्या कर सकता है"
    override val cap1Title = "स्वचालित लेन-देन रिकॉर्डिंग"
    override val cap1Desc = "प्रमुख भारतीय बैंकों और यूपीआई ऐप्स से डेबिट और क्रेडिट अलर्ट को तुरंत पहचानता है।"
    override val cap2Title = "दैनिक खर्च और इतिहास"
    override val cap2Desc = "दैनिक खर्चों का कुल योग और कस्टम तिथि सीमा का स्पष्ट इतिहास दिखाता है।"
    override val cap3Title = "खर्च का वर्गीकरण"
    override val cap3Desc = "त्वरित टैग और कस्टम नोट्स के साथ खर्च को तुरंत वर्गीकृत करें।"
    override val cap4Title = "बायोमेट्रिक सुरक्षा"
    override val cap4Desc = "स्वाइप करके फिंगरप्रिंट या पिन से सुरक्षित रूप से लेन-देन हटाएं।"
    override val limitsTitle = "वर्तमान सीमाएं"
    override val limit1Title = "पैसे नहीं भेज सकता"
    override val limit1Desc = "पेनी केवल लेन-देन का हिसाब रखता है; यह भुगतान नहीं कर सकता और न ही बैंक खाते से जुड़ सकता है।"
    override val limit2Title = "अलर्ट प्रारूप पर निर्भर"
    override val limit2Desc = "सटीकता बैंक द्वारा भेजे गए एसएमएस और नोटिफिकेशन के प्रारूप पर निर्भर करती है।"
    override val techTitle = "तकनीक"
    override val techDesc = "पूर्ण रूप से Jetpack Compose, Kotlin Coroutines और लोकल Room SQLite से निर्मित। कोई तृतीय-पक्ष ट्रैकर या एनालिटिक्स नहीं।"
}

fun stringsFor(language: PennyLanguage): PennyStrings = when (language) {
    PennyLanguage.ENGLISH -> EnglishStrings
    PennyLanguage.TAMIL -> TamilStrings
    PennyLanguage.HINDI -> HindiStrings
}

val LocalPennyStrings = staticCompositionLocalOf<PennyStrings> { EnglishStrings }
