package com.penny.app

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings as AndroidSettings
import androidx.activity.ComponentActivity
import androidx.activity.compose.BackHandler
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.SizeTransform
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.spring
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.IntOffset
import kotlin.math.absoluteValue
import kotlin.math.roundToInt
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.scaleIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.BasicText
import androidx.compose.foundation.text.TextAutoSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ArrowBack
import androidx.compose.material.icons.automirrored.outlined.ArrowForward
import androidx.compose.material.icons.automirrored.outlined.ReceiptLong
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.ChevronRight
import androidx.compose.material.icons.outlined.DarkMode
import androidx.compose.material.icons.outlined.DeleteOutline
import androidx.compose.material.icons.outlined.History
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material.icons.outlined.LightMode
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MusicNote
import androidx.compose.material.icons.outlined.NorthEast
import androidx.compose.material.icons.outlined.NotificationsNone
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material.icons.outlined.PhoneAndroid
import androidx.compose.material.icons.outlined.PrivacyTip
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Sms
import androidx.compose.material.icons.outlined.SouthWest
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material.icons.outlined.Block
import androidx.compose.material.icons.outlined.Category
import androidx.compose.material.icons.outlined.Fingerprint
import androidx.compose.material.icons.outlined.Memory
import androidx.compose.material.icons.outlined.Security
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material.icons.outlined.Translate
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.BottomSheetDefaults
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SelectableDates
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.core.app.NotificationManagerCompat
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import androidx.core.view.WindowCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.penny.app.data.Direction
import com.penny.app.data.PennyThemeMode
import com.penny.app.data.TransactionEntity
import com.penny.app.data.TransactionKind
import com.penny.app.notifications.ExpenseNotifications
import com.penny.app.ui.theme.AuroraCyan
import com.penny.app.ui.theme.AuroraMagenta
import com.penny.app.ui.theme.AuroraViolet
import com.penny.app.ui.theme.DarkMoneyGreen
import com.penny.app.ui.theme.MoneyGreen
import com.penny.app.ui.theme.PennyTheme
import com.penny.app.data.PennyLanguage
import com.penny.app.ui.LocalPennyStrings
import com.penny.app.ui.stringsFor
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import java.util.Locale

class MainActivity : FragmentActivity() {
    private val viewModel: PennyViewModel by viewModels()
    private val requestedTransaction = MutableStateFlow<String?>(null)
    private val permissionLauncher = registerForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions(),
    ) { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        requestedTransaction.value = intent.getStringExtra(ExpenseNotifications.EXTRA_TRANSACTION_ID)
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            PennyTheme(state.settings.themeMode) {
                PennySystemBars(state.settings.themeMode)
                PennyApp(
                    state = state,
                    viewModel = viewModel,
                    requestedTransaction = requestedTransaction,
                    requestPermissions = {
                        val permissions = buildList {
                            add(Manifest.permission.RECEIVE_SMS)
                            if (Build.VERSION.SDK_INT >= 33) add(Manifest.permission.POST_NOTIFICATIONS)
                        }
                        permissionLauncher.launch(permissions.toTypedArray())
                    },
                )
            }
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        requestedTransaction.value = intent.getStringExtra(ExpenseNotifications.EXTRA_TRANSACTION_ID)
    }
}

private enum class PennyTab { HOME, HISTORY, SETTINGS }
private enum class HistoryFilter { TODAY, YESTERDAY, WEEK, CHOOSE }
private enum class SettingsPage { MAIN, PRIVACY, ABOUT }
private enum class BalanceFocus { OUT, IN }

@Composable
private fun PennySystemBars(themeMode: PennyThemeMode) {
    val view = LocalView.current
    val systemDark = androidx.compose.foundation.isSystemInDarkTheme()
    val dark = themeMode == PennyThemeMode.DARK || (themeMode == PennyThemeMode.SYSTEM && systemDark)
    DisposableEffect(view, dark) {
        val window = (view.context as ComponentActivity).window
        WindowCompat.getInsetsController(window, view).apply {
            isAppearanceLightStatusBars = !dark
            isAppearanceLightNavigationBars = !dark
        }
        onDispose { }
    }
}

@Composable
private fun PennyApp(
    state: PennyUiState,
    viewModel: PennyViewModel,
    requestedTransaction: MutableStateFlow<String?>,
    requestPermissions: () -> Unit,
) {
    val deepLinkId by requestedTransaction.collectAsStateWithLifecycle()
    var tab by rememberSaveable { mutableStateOf(PennyTab.HOME) }
    var activeTransactionId by rememberSaveable { mutableStateOf<String?>(null) }
    val snackbar = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val context = LocalContext.current

    val onDeleteTransaction: (TransactionEntity) -> Unit = { transaction ->
        (context as? FragmentActivity)?.let { activity ->
            requestBiometricDelete(activity, transaction) {
                viewModel.deleteTransaction(transaction.id) {
                    if (activeTransactionId == transaction.id) {
                        activeTransactionId = null
                    }
                    scope.launch {
                        snackbar.showSnackbar("Transaction deleted")
                    }
                }
            }
        }
    }
    LaunchedEffect(deepLinkId, state.transactions, state.settings.onboardingComplete) {
        if (deepLinkId != null) {
            if (state.transactions.any { it.id == deepLinkId }) {
                activeTransactionId = deepLinkId
                requestedTransaction.value = null
            } else if (state.settings.onboardingComplete) {
                snackbar.showSnackbar("This transaction is no longer available")
                requestedTransaction.value = null
            }
        }
    }
    LaunchedEffect(Unit) {
        viewModel.foregroundDebitEvents.collect { id ->
            if (activeTransactionId == null) activeTransactionId = id
        }
    }

    if (!state.settings.onboardingComplete) {
        Onboarding(
            onEnable = { viewModel.completeOnboarding(); requestPermissions() },
            onSkip = viewModel::completeOnboarding,
        )
        return
    }

    val strings = stringsFor(state.settings.language)
    CompositionLocalProvider(LocalPennyStrings provides strings) {
        PennyBackdrop {
            Scaffold(
                containerColor = Color.Transparent,
                contentWindowInsets = WindowInsets(0, 0, 0, 0),
                snackbarHost = { GlassSnackbarHost(snackbar) },
                bottomBar = { PennyBottomBar(tab) { tab = it } },
            ) { padding ->
                AnimatedContent(
                    targetState = tab,
                    transitionSpec = {
                        (fadeIn() + slideInHorizontally { it / 10 }).togetherWith(
                            fadeOut() + slideOutHorizontally { -it / 14 },
                        ).using(SizeTransform(clip = false))
                    },
                    label = "tab transition",
                ) { destination ->
                    when (destination) {
                        PennyTab.HOME -> HomeScreen(
                            transactions = state.transactions,
                            onTransaction = { activeTransactionId = it },
                            onDeleteTransaction = onDeleteTransaction,
                            onPending = {
                                activeTransactionId = state.transactions
                                .filter { it.direction == Direction.DEBIT && it.title.isNullOrBlank() }
                                .minByOrNull { it.occurredAt }?.id
                            },
                            onEnablePermissions = requestPermissions,
                            modifier = Modifier.padding(padding),
                        )
                        PennyTab.HISTORY -> HistoryScreen(
                            transactions = state.transactions,
                            onTransaction = { activeTransactionId = it },
                            onDeleteTransaction = onDeleteTransaction,
                            modifier = Modifier.padding(padding),
                        )
                        PennyTab.SETTINGS -> SettingsScreen(
                            themeMode = state.settings.themeMode,
                            language = state.settings.language,
                            notificationsEnabled = state.settings.notificationsEnabled,
                            notificationSoundEnabled = state.settings.notificationSoundEnabled,
                            onThemeMode = viewModel::themeMode,
                            onLanguage = viewModel::language,
                            onNotifications = viewModel::notifications,
                            onNotificationSound = viewModel::notificationSound,
                            onRequestPermissions = requestPermissions,
                            onTestNotification = {
                                ExpenseNotifications.postPreview(context, state.settings.notificationSoundEnabled)
                                scope.launch { snackbar.showSnackbar(strings.testNotification) }
                            },
                            onDelete = { viewModel.deleteAll { activeTransactionId = null } },
                            onSimulateDebit = { viewModel.simulate(Direction.DEBIT) },
                            onSimulateCredit = { viewModel.simulate(Direction.CREDIT) },
                            onSeed = { viewModel.seedRealisticStoreData() },
                            modifier = Modifier.padding(padding),
                        )
                    }
                }
            }
        }

        val active = state.transactions.firstOrNull { it.id == activeTransactionId }
        if (active != null) {
            TransactionSheet(
                transaction = active,
                savedDraftId = state.settings.draftTransactionId,
                savedTitle = state.settings.draftTitle,
                savedDescription = state.settings.draftDescription,
                onDraft = viewModel::saveDraft,
                onDismiss = { activeTransactionId = null },
                onSave = { title, description ->
                    viewModel.saveExpense(active.id, title, description) {
                        activeTransactionId = state.transactions
                            .filter { it.id != active.id && it.direction == Direction.DEBIT && it.title.isNullOrBlank() }
                            .minByOrNull { it.occurredAt }?.id
                        scope.launch { snackbar.showSnackbar(strings.expenseSaved) }
                    }
                },
                onTransfer = { viewModel.markTransfer(active.id, it) },
            )
        }
    }
}

@Composable
private fun PennyBackdrop(content: @Composable () -> Unit) {
    val scheme = MaterialTheme.colorScheme
    val dark = scheme.background.red < .1f
    val topGlow = if (dark) AuroraViolet.copy(alpha = .24f) else AuroraViolet.copy(alpha = .12f)
    val sideGlow = if (dark) AuroraMagenta.copy(alpha = .13f) else AuroraCyan.copy(alpha = .10f)
    CompositionLocalProvider(LocalContentColor provides scheme.onBackground) {
        Box(
            Modifier
                .fillMaxSize()
                .background(
                    Brush.verticalGradient(
                        listOf(
                            if (dark) Color(0xFF140B25) else Color(0xFFF4EFFF),
                            scheme.background,
                            scheme.background,
                        ),
                    ),
                )
                .drawBehind {
                    drawCircle(topGlow, size.width * .7f, Offset(size.width * .12f, size.height * .05f))
                    drawCircle(sideGlow, size.width * .58f, Offset(size.width * .98f, size.height * .30f))
                },
        ) { content() }
    }
}

@Composable
private fun Onboarding(onEnable: () -> Unit, onSkip: () -> Unit) {
    PennyBackdrop {
        Column(
            Modifier.fillMaxSize().systemBarsPadding().padding(horizontal = 24.dp, vertical = 28.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            Column {
                BrandOrb()
                Spacer(Modifier.height(36.dp))
                Text("Know where your money goes.", style = MaterialTheme.typography.displayMedium)
                Spacer(Modifier.height(16.dp))
                Text(
                    "Penny turns new bank transaction SMS into a private money timeline. When you spend, add the one detail your bank cannot know: what it was for.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(26.dp))
                GlassPanel {
                    PermissionBenefit(Icons.Outlined.Sms, "SMS access", "Capture new debit and credit alerts")
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .55f))
                    PermissionBenefit(Icons.Outlined.NotificationsNone, "Expense reminders", "Name a debit while it is still fresh")
                }
                Row(Modifier.padding(top = 18.dp), verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Lock, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(18.dp))
                    Spacer(Modifier.width(10.dp))
                    Text("Your transaction data stays on this device.", style = MaterialTheme.typography.bodySmall)
                }
            }
            Column {
                Button(
                    onClick = onEnable,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp),
                    shape = RoundedCornerShape(18.dp),
                ) { Text("Enable and continue") }
                TextButton(onClick = onSkip, modifier = Modifier.fillMaxWidth()) { Text("Continue without access") }
            }
        }
    }
}

@Composable
private fun BrandOrb(modifier: Modifier = Modifier) {
    Image(
        painter = painterResource(R.drawable.app_logo),
        contentDescription = "Penny",
        modifier = modifier
            .size(72.dp)
            .shadow(18.dp, RoundedCornerShape(22.dp), ambientColor = AuroraViolet.copy(alpha = .35f), spotColor = AuroraMagenta.copy(alpha = .3f))
            .clip(RoundedCornerShape(22.dp)),
    )
}

@Composable
private fun PermissionBenefit(icon: ImageVector, title: String, detail: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape, modifier = Modifier.size(42.dp)) {
            Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(10.dp))
        }
        Column(Modifier.padding(start = 14.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun HomeScreen(
    transactions: List<TransactionEntity>,
    onTransaction: (String) -> Unit,
    onDeleteTransaction: (TransactionEntity) -> Unit,
    onPending: () -> Unit,
    onEnablePermissions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = LocalPennyStrings.current
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    var permissionRefresh by remember { mutableIntStateOf(0) }
    DisposableEffect(lifecycleOwner) {
        val observer = LifecycleEventObserver { _, event -> if (event == Lifecycle.Event.ON_RESUME) permissionRefresh++ }
        lifecycleOwner.lifecycle.addObserver(observer)
        onDispose { lifecycleOwner.lifecycle.removeObserver(observer) }
    }
    @Suppress("UNUSED_VARIABLE") val refresh = permissionRefresh
    val smsGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
    val notificationGranted = Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now(zone)
    val todayItems = transactions.filter { Instant.ofEpochMilli(it.occurredAt).atZone(zone).toLocalDate() == today }
    val (moneyIn, moneyOut) = todayItems.includedTotals()
    val refunds = todayItems.filter { it.kind == TransactionKind.REFUND || it.kind == TransactionKind.REVERSAL }.sumOf { it.amountMinor }
    val spent = moneyOut - refunds
    val pending = transactions.count { it.direction == Direction.DEBIT && it.title.isNullOrBlank() }

    LazyColumn(
        modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(20.dp, 18.dp, 20.dp, 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        item {
            Column {
                Text(strings.appName, style = MaterialTheme.typography.headlineLarge)
                Text(today.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")), style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item { AuroraBalanceCard(spent, moneyIn, moneyOut) }
        if (!smsGranted) item { PermissionCard("SMS access is off", "New bank alerts cannot be captured.", "Enable", onEnablePermissions) }
        if (!notificationGranted) item { PermissionCard("Expense reminders are off", "Debits are still saved when SMS access is on.", "Enable", onEnablePermissions) }
        if (pending > 0) item {
            AnimatedVisibility(visible = true, enter = fadeIn() + scaleIn(initialScale = .97f)) {
                PendingExpenses(pending, onPending)
            }
        }
        item {
            Row(Modifier.padding(top = 8.dp), verticalAlignment = Alignment.CenterVertically) {
                Text(strings.today, style = MaterialTheme.typography.titleLarge, modifier = Modifier.weight(1f))
                Text(strings.movementsCount(todayItems.size), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        if (todayItems.isEmpty()) item { EmptyState("Your day starts here", "Transactions appear when a bank SMS arrives.") }
        else item {
            GlassPanel(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)) {
                todayItems.forEachIndexed { index, transaction ->
                    TransactionRow(transaction, { onTransaction(transaction.id) }, { onDeleteTransaction(transaction) })
                    if (index != todayItems.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .45f))
                }
            }
        }
    }
}

@Composable
private fun AuroraBalanceCard(spent: Long, moneyIn: Long, moneyOut: Long) {
    val strings = LocalPennyStrings.current
    var focus by rememberSaveable { mutableStateOf(BalanceFocus.OUT) }
    val scale by animateFloatAsState(
        targetValue = if (focus == BalanceFocus.OUT) 1f else 1.012f,
        animationSpec = spring(dampingRatio = .78f, stiffness = 220f),
        label = "balance lobe spring",
    )
    val focusedLabel = when (focus) {
        BalanceFocus.OUT -> strings.spentToday
        BalanceFocus.IN -> strings.moneyInToday
    }
    val focusedAmount = when (focus) {
        BalanceFocus.OUT -> spent
        BalanceFocus.IN -> moneyIn
    }
    Box(
        Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .scale(scale)
            .shadow(24.dp, RoundedCornerShape(32.dp), ambientColor = AuroraViolet.copy(alpha = .35f), spotColor = AuroraMagenta.copy(alpha = .25f))
            .clip(RoundedCornerShape(topStart = 38.dp, topEnd = 24.dp, bottomEnd = 42.dp, bottomStart = 24.dp))
            .background(Brush.linearGradient(listOf(Color(0xFF3B18B5), Color(0xFF6132C4), Color(0xFFA12B7C))))
            .drawBehind {
                drawCircle(Color.White.copy(alpha = .08f), size.width * .64f, Offset(size.width * .93f, size.height * .06f))
                drawCircle(AuroraCyan.copy(alpha = .13f), size.width * .44f, Offset(size.width * .16f, size.height * 1.02f))
            }
            .padding(22.dp),
    ) {
        Column(Modifier.fillMaxWidth()) {
            Column(Modifier.fillMaxWidth()) {
                Row(
                    Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        strings.todaysFlow,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = Color.White.copy(alpha = .85f),
                    )
                    Surface(
                        color = Color.White.copy(alpha = .16f),
                        shape = CircleShape,
                        border = BorderStroke(0.5.dp, Color.White.copy(alpha = .22f)),
                    ) {
                        Row(
                            modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp),
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(6.dp)
                                    .background(Color(0xFF70E3BC), CircleShape),
                            )
                            Spacer(Modifier.width(6.dp))
                            Text(
                                strings.smsLive,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.SemiBold,
                                letterSpacing = 0.5.sp,
                                color = Color.White,
                            )
                        }
                    }
                }
                Spacer(Modifier.height(14.dp))
                AnimatedContent(
                    targetState = focusedLabel,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "balance label",
                ) { label ->
                    Text(
                        label,
                        style = MaterialTheme.typography.labelLarge,
                        fontWeight = FontWeight.Medium,
                        color = Color.White.copy(alpha = .92f),
                    )
                }
                Spacer(Modifier.height(4.dp))
                AnimatedContent(
                    targetState = focusedAmount,
                    transitionSpec = { fadeIn() togetherWith fadeOut() },
                    label = "balance amount",
                ) { amount ->
                    BasicText(
                        text = formatMoney(amount),
                        modifier = Modifier.fillMaxWidth().heightIn(min = 48.dp, max = 56.dp),
                        maxLines = 1,
                        autoSize = TextAutoSize.StepBased(minFontSize = 24.sp, maxFontSize = 46.sp, stepSize = 1.sp),
                        style = MaterialTheme.typography.displayMedium.copy(
                            color = if (focus == BalanceFocus.IN) Color(0xFF8CF6CC) else Color.White,
                            fontWeight = FontWeight.Bold,
                        ),
                    )
                }
            }
            Spacer(Modifier.height(18.dp))
            BalanceSelector(focus, moneyIn, moneyOut) { focus = it }
        }
    }
}

@Composable
private fun BalanceSelector(
    focus: BalanceFocus,
    moneyIn: Long,
    moneyOut: Long,
    onFocus: (BalanceFocus) -> Unit,
) {
    val strings = LocalPennyStrings.current
    val choices = listOf(
        Triple(BalanceFocus.IN, strings.inLabel, moneyIn),
        Triple(BalanceFocus.OUT, strings.outLabel, moneyOut),
    )
    BoxWithConstraints(
        Modifier
            .fillMaxWidth()
            .height(72.dp)
            .clip(RoundedCornerShape(20.dp))
            .background(Color.Black.copy(alpha = .18f))
            .border(0.5.dp, Color.White.copy(alpha = .15f), RoundedCornerShape(20.dp))
            .padding(4.dp),
    ) {
        val selectedIndex = choices.indexOfFirst { it.first == focus }
        val segmentWidth = maxWidth / choices.size
        val position by animateDpAsState(
            targetValue = segmentWidth * selectedIndex,
            animationSpec = spring(dampingRatio = .78f, stiffness = 280f),
            label = "traveling balance highlight",
        )
        val highlightWidth by animateDpAsState(
            targetValue = if (selectedIndex == choices.lastIndex) maxWidth - (segmentWidth * (choices.size - 1)) else segmentWidth,
            animationSpec = spring(dampingRatio = .78f, stiffness = 280f),
            label = "balance highlight width",
        )
        Surface(
            modifier = Modifier
                .offset(x = position)
                .width(highlightWidth)
                .fillMaxHeight(),
            color = Color.White.copy(alpha = .22f),
            shape = RoundedCornerShape(16.dp),
            border = BorderStroke(0.5.dp, Color.White.copy(alpha = .28f)),
        ) { }
        Row(Modifier.fillMaxSize().selectableGroup()) {
            choices.forEach { (value, label, amount) ->
                val isSelected = focus == value
                Column(
                    Modifier
                        .weight(1f)
                        .fillMaxHeight()
                        .selectable(
                            selected = isSelected,
                            onClick = { onFocus(value) },
                            role = Role.RadioButton,
                        )
                        .semantics { contentDescription = "$label ${formatMoney(amount)}" }
                        .padding(horizontal = 8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                        color = if (isSelected) Color.White else Color.White.copy(alpha = .72f),
                        maxLines = 1,
                    )
                    Spacer(Modifier.height(3.dp))
                    Text(
                        text = formatLobeAmount(amount),
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 15.sp,
                            letterSpacing = (-0.2).sp,
                        ),
                        maxLines = 1,
                        color = when {
                            value == BalanceFocus.IN -> Color(0xFF8CF6CC)
                            isSelected -> Color.White
                            else -> Color.White.copy(alpha = .88f)
                        },
                        textAlign = TextAlign.Center,
                    )
                }
            }
        }
    }
}
@Composable
private fun PermissionCard(title: String, detail: String, action: String, onAction: () -> Unit) {
    GlassPanel(contentPadding = PaddingValues(start = 16.dp, top = 12.dp, bottom = 12.dp, end = 8.dp)) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Surface(color = MaterialTheme.colorScheme.secondaryContainer, shape = CircleShape, modifier = Modifier.size(38.dp)) {
                Icon(Icons.Outlined.Tune, null, tint = MaterialTheme.colorScheme.secondary, modifier = Modifier.padding(9.dp))
            }
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(title, style = MaterialTheme.typography.titleMedium)
                Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TextButton(onClick = onAction) { Text(action) }
        }
    }
}

@Composable
private fun PendingExpenses(pending: Int, onClick: () -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        shape = RoundedCornerShape(22.dp),
        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = .82f),
    ) {
        Row(Modifier.padding(18.dp), verticalAlignment = Alignment.CenterVertically) {
            Surface(color = MaterialTheme.colorScheme.primary, shape = CircleShape, modifier = Modifier.size(44.dp)) {
                Icon(Icons.AutoMirrored.Outlined.ReceiptLong, null, tint = MaterialTheme.colorScheme.onPrimary, modifier = Modifier.padding(11.dp))
            }
            Column(Modifier.weight(1f).padding(horizontal = 14.dp)) {
                Text(if (pending == 1) "1 expense needs a title" else "$pending expenses need titles", style = MaterialTheme.typography.titleMedium)
                Text("Add context now", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.primary)
            }
            Icon(Icons.AutoMirrored.Outlined.ArrowForward, "Open pending expenses", tint = MaterialTheme.colorScheme.primary)
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun HistoryScreen(
    transactions: List<TransactionEntity>,
    onTransaction: (String) -> Unit,
    onDeleteTransaction: (TransactionEntity) -> Unit,
    modifier: Modifier = Modifier,
) {
    val strings = LocalPennyStrings.current
    var filter by rememberSaveable { mutableStateOf(HistoryFilter.WEEK) }
    var rangeFromEpochDay by rememberSaveable { mutableStateOf<Long?>(null) }
    var rangeToEpochDay by rememberSaveable { mutableStateOf<Long?>(null) }
    var showDatePicker by remember { mutableStateOf(false) }
    val zone = ZoneId.systemDefault()
    val today = LocalDate.now(zone)
    val rangeFrom = rangeFromEpochDay?.let(LocalDate::ofEpochDay) ?: today.minusDays(6)
    val rangeTo = rangeToEpochDay?.let(LocalDate::ofEpochDay) ?: today
    val from = when (filter) {
        HistoryFilter.TODAY -> today
        HistoryFilter.YESTERDAY -> today.minusDays(1)
        HistoryFilter.WEEK -> today.minusDays(6)
        HistoryFilter.CHOOSE -> rangeFrom
    }
    val to = when (filter) {
        HistoryFilter.TODAY -> today
        HistoryFilter.YESTERDAY -> today.minusDays(1)
        HistoryFilter.WEEK -> today
        HistoryFilter.CHOOSE -> rangeTo
    }
    val filtered = transactions.filter { Instant.ofEpochMilli(it.occurredAt).atZone(zone).toLocalDate() in from..to }
    val (moneyIn, moneyOut) = filtered.includedTotals()

    LazyColumn(
        modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(20.dp, 18.dp, 20.dp, 32.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        item {
            Text(strings.history, style = MaterialTheme.typography.headlineLarge)
            Text(strings.settingsSubtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                HistoryFilter.entries.forEach { value ->
                    item(value) {
                        FilterChip(
                            selected = filter == value,
                            onClick = { if (value == HistoryFilter.CHOOSE) showDatePicker = true else filter = value },
                            label = {
                                Text(
                                    when (value) {
                                        HistoryFilter.TODAY -> strings.today
                                        HistoryFilter.YESTERDAY -> strings.yesterday
                                        HistoryFilter.WEEK -> strings.last7Days
                                        HistoryFilter.CHOOSE -> {
                                            if (filter == HistoryFilter.CHOOSE && rangeFromEpochDay != null && rangeToEpochDay != null) {
                                                if (rangeFrom == rangeTo) rangeFrom.format(DateTimeFormatter.ofPattern("d MMM"))
                                                else "${rangeFrom.format(DateTimeFormatter.ofPattern("d MMM"))} – ${rangeTo.format(DateTimeFormatter.ofPattern("d MMM"))}"
                                            } else strings.dateRange
                                        }
                                    }
                                )
                            },
                        )
                    }
                }
            }
        }
        item {
            GlassPanel {
                Text(
                    if (from == to) from.format(DateTimeFormatter.ofPattern("d MMMM uuuu")) else "${from.format(DateTimeFormatter.ofPattern("d MMM"))} – ${to.format(DateTimeFormatter.ofPattern("d MMM uuuu"))}",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
                Spacer(Modifier.height(14.dp))
                Row {
                    HistoryTotal(strings.inLabel, moneyIn, true, Modifier.weight(1f))
                    HistoryTotal(strings.outLabel, moneyOut, false, Modifier.weight(1f))
                }
            }
        }
        if (filtered.isEmpty()) item { EmptyState("Nothing in this period", "Try another date range.") }
        else {
            val groups = filtered.groupBy { Instant.ofEpochMilli(it.occurredAt).atZone(zone).toLocalDate() }.toSortedMap(reverseOrder())
            groups.forEach { (date, values) ->
                item { Text(if (date == today) "Today" else date.format(DateTimeFormatter.ofPattern("EEEE, d MMMM")), style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(top = 10.dp)) }
                item {
                    GlassPanel(contentPadding = PaddingValues(horizontal = 16.dp, vertical = 4.dp)) {
                        values.forEachIndexed { index, transaction ->
                            TransactionRow(transaction, { onTransaction(transaction.id) }, { onDeleteTransaction(transaction) })
                            if (index != values.lastIndex) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .45f))
                        }
                    }
                }
            }
        }
    }
    if (showDatePicker) {
        val rangePickerState = rememberDateRangePickerState(
            initialSelectedStartDateMillis = rangeFrom.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
            initialSelectedEndDateMillis = rangeTo.atStartOfDay(ZoneOffset.UTC).toInstant().toEpochMilli(),
            selectableDates = object : SelectableDates {
                override fun isSelectableDate(utcTimeMillis: Long): Boolean =
                    Instant.ofEpochMilli(utcTimeMillis).atZone(ZoneOffset.UTC).toLocalDate() <= today
            },
        )
        DatePickerDialog(
            onDismissRequest = { showDatePicker = false },
            confirmButton = {
                TextButton(
                    onClick = {
                        val startMillis = rangePickerState.selectedStartDateMillis
                        val endMillis = rangePickerState.selectedEndDateMillis ?: startMillis
                        if (startMillis != null) {
                            rangeFromEpochDay = Instant.ofEpochMilli(startMillis).atZone(ZoneOffset.UTC).toLocalDate().toEpochDay()
                            rangeToEpochDay = Instant.ofEpochMilli(endMillis ?: startMillis).atZone(ZoneOffset.UTC).toLocalDate().toEpochDay()
                            filter = HistoryFilter.CHOOSE
                        }
                        showDatePicker = false
                    },
                    enabled = rangePickerState.selectedStartDateMillis != null,
                ) { Text(strings.applyRange) }
            },
            dismissButton = {
                TextButton(onClick = { showDatePicker = false }) { Text(strings.cancel) }
            },
        ) {
            DateRangePicker(
                state = rangePickerState,
                modifier = Modifier.weight(1f).padding(top = 8.dp),
                title = {
                    Text(
                        strings.selectDateRange,
                        style = MaterialTheme.typography.labelLarge,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 12.dp),
                    )
                },
                headline = {
                    val start = rangePickerState.selectedStartDateMillis?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                    }
                    val end = rangePickerState.selectedEndDateMillis?.let {
                        Instant.ofEpochMilli(it).atZone(ZoneOffset.UTC).toLocalDate()
                    }
                    val text = when {
                        start != null && end != null ->
                            "${start.format(DateTimeFormatter.ofPattern("d MMM"))} – ${end.format(DateTimeFormatter.ofPattern("d MMM yyyy"))}"
                        start != null ->
                            "${start.format(DateTimeFormatter.ofPattern("d MMM yyyy"))} – End date"
                        else -> "Start date – End date"
                    }
                    Text(
                        text,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.padding(horizontal = 24.dp, vertical = 4.dp),
                    )
                },
            )
        }
    }
}

@Composable
private fun HistoryTotal(label: String, amount: Long, credit: Boolean, modifier: Modifier) {
    Column(modifier) {
        Text(label, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(formatMoney(amount), style = MaterialTheme.typography.titleLarge, color = if (credit) creditColor() else MaterialTheme.colorScheme.onSurface)
    }
}

@Composable
private fun TransactionRow(
    transaction: TransactionEntity,
    onClick: () -> Unit,
    onDelete: () -> Unit,
) {
    val isCredit = transaction.direction == Direction.CREDIT
    val label = when {
        transaction.kind == TransactionKind.REFUND -> "Refund · ${transaction.title ?: transaction.merchantOrSender ?: "Purchase"}"
        transaction.kind == TransactionKind.OWN_TRANSFER -> "Transfer"
        isCredit -> transaction.merchantOrSender ?: "Money received"
        !transaction.title.isNullOrBlank() -> transaction.title
        else -> "Add a title"
    }

    val offsetX = remember(transaction.id) { Animatable(0f) }
    val scope = rememberCoroutineScope()
    val revealWidth = with(LocalDensity.current) { 84.dp.toPx() }
    val currentWidth = with(LocalDensity.current) { offsetX.value.coerceAtLeast(0f).toDp() }
    val progress = (offsetX.value / revealWidth).coerceIn(0f, 1f)

    val curveRadius = with(LocalDensity.current) {
        (16.dp + 12.dp * progress).coerceAtMost(28.dp)
    }
    val deleteLobeShape = RoundedCornerShape(
        topStart = 16.dp,
        bottomStart = 16.dp,
        topEnd = curveRadius,
        bottomEnd = curveRadius
    )
    val rowShape = RoundedCornerShape(16.dp)

    Box(
        Modifier
            .fillMaxWidth()
            .clip(rowShape)
    ) {
        if (offsetX.value > 1f) {
            Box(
                modifier = Modifier
                    .matchParentSize()
                    .padding(vertical = 4.dp),
                contentAlignment = Alignment.CenterStart
            ) {
                Box(
                    modifier = Modifier
                        .width(currentWidth)
                        .fillMaxHeight()
                        .shadow(
                            elevation = 14.dp,
                            shape = deleteLobeShape,
                            ambientColor = Color(0xFFC01E5D).copy(alpha = 0.45f),
                            spotColor = AuroraViolet.copy(alpha = 0.35f)
                        )
                        .clip(deleteLobeShape)
                        .background(
                            Brush.horizontalGradient(
                                listOf(
                                    Color(0xFF6F0C35),
                                    Color(0xFFA81552),
                                    Color(0xFFC92065)
                                )
                            )
                        )
                        .border(
                            1.dp,
                            Brush.horizontalGradient(
                                listOf(
                                    Color.White.copy(alpha = 0.15f),
                                    AuroraMagenta.copy(alpha = 0.50f),
                                    Color.White.copy(alpha = 0.40f)
                                )
                            ),
                            deleteLobeShape
                        )
                        .clickable {
                            scope.launch { offsetX.animateTo(0f, spring(dampingRatio = 0.82f, stiffness = 320f)) }
                            onDelete()
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .size(42.dp)
                            .scale(0.6f + 0.4f * progress)
                            .alpha((progress * 1.5f).coerceIn(0f, 1f))
                            .shadow(6.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.4f))
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.18f))
                            .border(0.5.dp, Color.White.copy(alpha = 0.55f), CircleShape),
                        contentAlignment = Alignment.Center,
                    ) {
                        Icon(
                            Icons.Outlined.DeleteOutline,
                            contentDescription = "Delete",
                            tint = Color.White,
                            modifier = Modifier.size(22.dp),
                        )
                    }
                }
            }
        }

        Row(
            Modifier
                .fillMaxWidth()
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .clip(rowShape)
                .background(MaterialTheme.colorScheme.surface.copy(alpha = if (offsetX.value > 0f) 0.98f else 0f))
                .then(
                    if (offsetX.value > 1f) {
                        Modifier
                            .shadow(16.dp, rowShape, ambientColor = Color.Black.copy(alpha = 0.5f), spotColor = AuroraViolet.copy(alpha = 0.25f))
                            .border(0.5.dp, Color.White.copy(alpha = 0.18f), rowShape)
                    } else Modifier
                )
                .clickable {
                    if (offsetX.value > 10f) {
                        scope.launch { offsetX.animateTo(0f, spring(dampingRatio = 0.82f, stiffness = 320f)) }
                    } else {
                        onClick()
                    }
                }
                .pointerInput(transaction.id) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX.value > revealWidth * 0.5f) {
                                scope.launch {
                                    offsetX.animateTo(revealWidth, spring(dampingRatio = 0.82f, stiffness = 320f))
                                }
                            } else {
                                scope.launch {
                                    offsetX.animateTo(0f, spring(dampingRatio = 0.82f, stiffness = 320f))
                                }
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            val newOffset = (offsetX.value + dragAmount).coerceIn(0f, revealWidth * 1.25f)
                            if (newOffset > 0f || offsetX.value > 0f) {
                                change.consume()
                                scope.launch { offsetX.snapTo(newOffset) }
                            }
                        }
                    )
                }
                .padding(vertical = 14.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Surface(
                color = if (isCredit) creditColor().copy(alpha = .12f) else MaterialTheme.colorScheme.primaryContainer,
                shape = RoundedCornerShape(15.dp),
                modifier = Modifier.size(46.dp),
            ) {
                Icon(if (isCredit) Icons.Outlined.SouthWest else Icons.Outlined.NorthEast, null, tint = if (isCredit) creditColor() else MaterialTheme.colorScheme.primary, modifier = Modifier.padding(11.dp))
            }
            Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
                Text(label, style = MaterialTheme.typography.titleMedium, color = if (!isCredit && transaction.title.isNullOrBlank()) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface, maxLines = 1, overflow = TextOverflow.Ellipsis)
                val account = listOfNotNull(transaction.bank, transaction.accountLast4?.let { "••$it" }).joinToString(" ").ifBlank { "Bank alert" }
                val time = Instant.ofEpochMilli(transaction.occurredAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("h:mm a"))
                Text("$account · $time", style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text((if (isCredit) "+" else "−") + formatMoney(transaction.amountMinor), style = MaterialTheme.typography.titleMedium, color = if (isCredit) creditColor() else MaterialTheme.colorScheme.onSurface)
                when {
                    transaction.kind == TransactionKind.OWN_TRANSFER -> Text("Transfer", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    !isCredit && transaction.title.isNullOrBlank() -> Text("Pending", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
    }
}

private fun requestBiometricDelete(
    activity: FragmentActivity,
    transaction: TransactionEntity,
    onConfirmed: () -> Unit,
) {
    val authenticators = BiometricManager.Authenticators.BIOMETRIC_STRONG or BiometricManager.Authenticators.DEVICE_CREDENTIAL
    val biometricManager = BiometricManager.from(activity)

    if (biometricManager.canAuthenticate(authenticators) == BiometricManager.BIOMETRIC_SUCCESS) {
        val executor = ContextCompat.getMainExecutor(activity)
        val prompt = BiometricPrompt(
            activity,
            executor,
            object : BiometricPrompt.AuthenticationCallback() {
                override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                    super.onAuthenticationSucceeded(result)
                    onConfirmed()
                }

                override fun onAuthenticationError(errorCode: Int, errString: CharSequence) {
                    super.onAuthenticationError(errorCode, errString)
                }

                override fun onAuthenticationFailed() {
                    super.onAuthenticationFailed()
                }
            }
        )

        val promptInfo = BiometricPrompt.PromptInfo.Builder()
            .setTitle("Confirm deletion")
            .setSubtitle("Use your fingerprint or phone PIN to delete this transaction")
            .setAllowedAuthenticators(authenticators)
            .build()

        prompt.authenticate(promptInfo)
    } else {
        onConfirmed()
    }
}

@Composable
private fun EmptyState(title: String, detail: String) {
    GlassPanel {
        Column(Modifier.fillMaxWidth().padding(vertical = 26.dp), horizontalAlignment = Alignment.CenterHorizontally) {
            Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = CircleShape, modifier = Modifier.size(56.dp)) {
                Icon(Icons.AutoMirrored.Outlined.ReceiptLong, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(15.dp))
            }
            Spacer(Modifier.height(14.dp))
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SettingsScreen(
    themeMode: PennyThemeMode,
    language: PennyLanguage,
    notificationsEnabled: Boolean,
    notificationSoundEnabled: Boolean,
    onThemeMode: (PennyThemeMode) -> Unit,
    onLanguage: (PennyLanguage) -> Unit,
    onNotifications: (Boolean) -> Unit,
    onNotificationSound: (Boolean) -> Unit,
    onRequestPermissions: () -> Unit,
    onTestNotification: () -> Unit,
    onDelete: () -> Unit,
    onSimulateDebit: () -> Unit,
    onSimulateCredit: () -> Unit,
    onSeed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var page by rememberSaveable { mutableStateOf(SettingsPage.MAIN) }
    BackHandler(page != SettingsPage.MAIN) { page = SettingsPage.MAIN }
    AnimatedContent(
        targetState = page,
        transitionSpec = {
            val forward = targetState != SettingsPage.MAIN
            (fadeIn() + slideInHorizontally { if (forward) it / 3 else -it / 3 }).togetherWith(
                fadeOut() + slideOutHorizontally { if (forward) -it / 4 else it / 4 },
            )
        },
        label = "settings detail",
        modifier = modifier,
    ) { destination ->
        when (destination) {
            SettingsPage.MAIN -> SettingsMain(
                themeMode, language, notificationsEnabled, notificationSoundEnabled, onThemeMode,
                onLanguage, onNotifications, onNotificationSound, onRequestPermissions, onTestNotification,
                onPrivacy = { page = SettingsPage.PRIVACY },
                onAbout = { page = SettingsPage.ABOUT },
                onDelete, onSimulateDebit, onSimulateCredit, onSeed,
            )
            SettingsPage.PRIVACY -> PrivacyScreen { page = SettingsPage.MAIN }
            SettingsPage.ABOUT -> AboutScreen { page = SettingsPage.MAIN }
        }
    }
}

@Composable
private fun SettingsMain(
    themeMode: PennyThemeMode,
    language: PennyLanguage,
    notificationsEnabled: Boolean,
    notificationSoundEnabled: Boolean,
    onThemeMode: (PennyThemeMode) -> Unit,
    onLanguage: (PennyLanguage) -> Unit,
    onNotifications: (Boolean) -> Unit,
    onNotificationSound: (Boolean) -> Unit,
    onRequestPermissions: () -> Unit,
    onTestNotification: () -> Unit,
    onPrivacy: () -> Unit,
    onAbout: () -> Unit,
    onDelete: () -> Unit,
    onSimulateDebit: () -> Unit,
    onSimulateCredit: () -> Unit,
    onSeed: () -> Unit,
) {
    val strings = LocalPennyStrings.current
    val context = LocalContext.current
    val smsGranted = ContextCompat.checkSelfPermission(context, Manifest.permission.RECEIVE_SMS) == PackageManager.PERMISSION_GRANTED
    val notificationGranted = Build.VERSION.SDK_INT < 33 || ContextCompat.checkSelfPermission(context, Manifest.permission.POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED
    var confirmDelete by remember { mutableStateOf(false) }
    LazyColumn(
        Modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(20.dp, 18.dp, 20.dp, 32.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item {
            Text(strings.settingsTitle, style = MaterialTheme.typography.headlineLarge)
            Text(strings.settingsSubtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        item {
            SettingsSection(strings.languageSection) {
                SettingsLead(Icons.Outlined.Translate, strings.languageSection, strings.languageSubtitle)
                Row(Modifier.fillMaxWidth().selectableGroup(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    LanguageChoice(PennyLanguage.ENGLISH, "English", language, onLanguage, Modifier.weight(1f))
                    LanguageChoice(PennyLanguage.TAMIL, "தமிழ்", language, onLanguage, Modifier.weight(1f))
                    LanguageChoice(PennyLanguage.HINDI, "हिन्दी", language, onLanguage, Modifier.weight(1f))
                }
            }
        }
        item {
            SettingsSection(strings.appearance) {
                SettingsLead(Icons.Outlined.Palette, strings.theme, strings.themeSubtitle)
                Row(Modifier.fillMaxWidth().selectableGroup(), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    ThemeChoice(PennyThemeMode.SYSTEM, strings.systemTheme, Icons.Outlined.PhoneAndroid, themeMode, onThemeMode, Modifier.weight(1f))
                    ThemeChoice(PennyThemeMode.LIGHT, strings.lightTheme, Icons.Outlined.LightMode, themeMode, onThemeMode, Modifier.weight(1f))
                    ThemeChoice(PennyThemeMode.DARK, strings.darkTheme, Icons.Outlined.DarkMode, themeMode, onThemeMode, Modifier.weight(1f))
                }
            }
        }
        item {
            SettingsSection(strings.notificationsSection) {
                SettingsSwitch(Icons.Outlined.NotificationsNone, strings.expenseReminders, if (!notificationGranted) strings.blockedByAndroid else strings.expenseRemindersSub, notificationsEnabled && notificationGranted) {
                    if (!notificationGranted) onRequestPermissions() else onNotifications(it)
                }
                SettingsDivider()
                SettingsSwitch(Icons.Outlined.MusicNote, strings.pennyChime, strings.pennyChimeSub, notificationSoundEnabled, onNotificationSound)
                Spacer(Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    OutlinedButton(onClick = onTestNotification, enabled = notificationGranted && notificationsEnabled, modifier = Modifier.weight(1f)) { Text(strings.testNotification) }
                    TextButton(onClick = {
                        context.startActivity(Intent(AndroidSettings.ACTION_APP_NOTIFICATION_SETTINGS).putExtra(AndroidSettings.EXTRA_APP_PACKAGE, context.packageName))
                    }) { Text(strings.androidSettings) }
                }
            }
        }
        item {
            val notificationAccessGranted = NotificationManagerCompat.getEnabledListenerPackages(context).contains(context.packageName)
            SettingsSection(strings.accessSection) {
                SettingsStatus(
                    icon = Icons.Outlined.Sms,
                    title = strings.smsAccess,
                    detail = if (smsGranted) strings.smsAccessOn else strings.smsAccessOff,
                    action = if (smsGranted) strings.settingsTitle else strings.enable,
                    onAction = {
                        if (!smsGranted) onRequestPermissions()
                        else context.startActivity(Intent(AndroidSettings.ACTION_APPLICATION_DETAILS_SETTINGS).apply { data = Uri.fromParts("package", context.packageName, null) })
                    }
                )
                SettingsDivider()
                SettingsStatus(
                    icon = Icons.Outlined.AccountBalanceWallet,
                    title = strings.paymentListener,
                    detail = if (notificationAccessGranted) strings.paymentListenerOn else strings.paymentListenerOff,
                    action = if (notificationAccessGranted) strings.disable else strings.enable,
                    onAction = {
                        context.startActivity(Intent(AndroidSettings.ACTION_NOTIFICATION_LISTENER_SETTINGS))
                    }
                )
            }
        }
        item {
            SettingsSection(strings.appName) {
                SettingsLink(Icons.Outlined.PrivacyTip, strings.privacy, strings.privacySub, onPrivacy)
                SettingsDivider()
                SettingsLink(Icons.Outlined.Info, strings.about, strings.aboutSub, onAbout)
            }
        }
        if (BuildConfig.DEBUG) item {
            SettingsSection("Developer tools") {
                OutlinedButton(onClick = onSeed, modifier = Modifier.fillMaxWidth()) { Text("Seed realistic store data") }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onSimulateDebit, modifier = Modifier.fillMaxWidth()) { Text("Simulate ₹45,280 debit") }
                Spacer(Modifier.height(8.dp))
                OutlinedButton(onClick = onSimulateCredit, modifier = Modifier.fillMaxWidth()) { Text("Simulate ₹1,25,000 credit") }
            }
        }
        item {
            TextButton(
                onClick = { confirmDelete = true },
                colors = ButtonDefaults.textButtonColors(contentColor = MaterialTheme.colorScheme.error),
                modifier = Modifier.fillMaxWidth(),
            ) {
                Icon(Icons.Outlined.DeleteOutline, null)
                Spacer(Modifier.width(8.dp))
                Text(strings.deleteAllData)
            }
        }
    }
    if (confirmDelete) AlertDialog(
        onDismissRequest = { confirmDelete = false },
        icon = { Icon(Icons.Outlined.DeleteOutline, null) },
        title = { Text(strings.deleteConfirmTitle) },
        text = { Text(strings.deleteConfirmBody) },
        confirmButton = { TextButton(onClick = { onDelete(); confirmDelete = false }) { Text(strings.delete, color = MaterialTheme.colorScheme.error) } },
        dismissButton = { TextButton(onClick = { confirmDelete = false }) { Text(strings.cancel) } },
    )
}

@Composable
private fun ThemeChoice(mode: PennyThemeMode, label: String, icon: ImageVector, selected: PennyThemeMode, onSelect: (PennyThemeMode) -> Unit, modifier: Modifier) {
    val active = mode == selected
    Surface(
        modifier = modifier.heightIn(min = 76.dp).selectable(
            selected = active,
            onClick = { onSelect(mode) },
            role = Role.RadioButton,
        ),
        shape = RoundedCornerShape(18.dp),
        color = if (active) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .55f),
    ) {
        Column(Modifier.padding(12.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(6.dp)) {
            Icon(icon, null, tint = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
            Text(label, style = MaterialTheme.typography.labelMedium, color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun LanguageChoice(
    lang: PennyLanguage,
    label: String,
    selected: PennyLanguage,
    onSelect: (PennyLanguage) -> Unit,
    modifier: Modifier = Modifier,
) {
    val active = lang == selected
    Surface(
        modifier = modifier
            .heightIn(min = 60.dp)
            .selectable(
                selected = active,
                onClick = { onSelect(lang) },
                role = Role.RadioButton,
            ),
        shape = RoundedCornerShape(16.dp),
        color = if (active) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surfaceVariant.copy(alpha = .55f),
        border = if (active) BorderStroke(1.dp, MaterialTheme.colorScheme.primary.copy(alpha = 0.35f)) else null,
    ) {
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp, vertical = 12.dp)) {
            Text(
                label,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = if (active) FontWeight.Bold else FontWeight.Medium,
                color = if (active) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
    }
}

@Composable
private fun SettingsSection(title: String, content: @Composable ColumnScope.() -> Unit) {
    Column {
        Text(title, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.padding(start = 4.dp, bottom = 8.dp))
        GlassPanel(contentPadding = PaddingValues(16.dp), content = content)
    }
}

@Composable
private fun SettingsLead(icon: ImageVector, title: String, detail: String) {
    Row(Modifier.padding(bottom = 14.dp), verticalAlignment = Alignment.CenterVertically) {
        SettingsIcon(icon)
        Column(Modifier.padding(start = 12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun SettingsStatus(icon: ImageVector, title: String, detail: String, action: String?, onAction: () -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = 60.dp)
            .clickable(onClick = onAction),
        verticalAlignment = Alignment.CenterVertically
    ) {
        SettingsIcon(icon)
        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        if (action != null) {
            Button(
                onClick = onAction,
                colors = if (action == "Disable" || action == "Settings") {
                    ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.primary)
                } else {
                    ButtonDefaults.buttonColors()
                },
                contentPadding = PaddingValues(horizontal = 14.dp, vertical = 6.dp),
                shape = RoundedCornerShape(12.dp),
            ) {
                Text(action, style = MaterialTheme.typography.labelMedium, fontWeight = FontWeight.SemiBold)
            }
        }
    }
}

@Composable
private fun SettingsSwitch(icon: ImageVector, title: String, detail: String, checked: Boolean, onChecked: (Boolean) -> Unit) {
    Row(Modifier.fillMaxWidth().heightIn(min = 62.dp), verticalAlignment = Alignment.CenterVertically) {
        SettingsIcon(icon)
        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Switch(checked = checked, onCheckedChange = onChecked)
    }
}

@Composable
private fun SettingsLink(icon: ImageVector, title: String, detail: String, onClick: () -> Unit) {
    Row(Modifier.fillMaxWidth().heightIn(min = 66.dp).clickable(onClick = onClick), verticalAlignment = Alignment.CenterVertically) {
        SettingsIcon(icon)
        Column(Modifier.weight(1f).padding(horizontal = 12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        Icon(Icons.Outlined.ChevronRight, "Open $title", tint = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

@Composable
private fun SettingsIcon(icon: ImageVector) {
    Surface(color = MaterialTheme.colorScheme.primaryContainer, shape = RoundedCornerShape(13.dp), modifier = Modifier.size(40.dp)) {
        Icon(icon, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(9.dp))
    }
}

@Composable private fun SettingsDivider() = HorizontalDivider(Modifier.padding(vertical = 8.dp), color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = .5f))

@Composable
private fun PrivacyScreen(onBack: () -> Unit) {
    val strings = LocalPennyStrings.current
    DetailScaffold(strings.privacyTitle, onBack) {
        item {
            GlassPanel {
                DetailPoint(Icons.Outlined.PhoneAndroid, strings.localPhoneTitle, strings.localPhoneDesc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Lock, strings.noBankLoginTitle, strings.noBankLoginDesc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Sms, strings.futureAlertsTitle, strings.futureAlertsDesc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.AccountBalanceWallet, strings.paymentAppTitle, strings.paymentAppDesc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Fingerprint, strings.biometricProtectionTitle, strings.biometricProtectionDesc)
            }
        }
        item {
            Text(strings.permissionsTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(10.dp))
            GlassPanel {
                Text(strings.smsPermTitle, style = MaterialTheme.typography.titleMedium)
                Text(strings.smsPermDesc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                SettingsDivider()
                Text(strings.paymentNotifTitle, style = MaterialTheme.typography.titleMedium)
                Text(strings.paymentNotifDesc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                SettingsDivider()
                Text(strings.notifPermTitle, style = MaterialTheme.typography.titleMedium)
                Text(strings.notifPermDesc, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        item {
            GlassPanel {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Outlined.Security, null, tint = MaterialTheme.colorScheme.primary, modifier = Modifier.size(24.dp))
                    Spacer(Modifier.width(12.dp))
                    Text(strings.privacyFooter, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun AboutScreen(onBack: () -> Unit) {
    val strings = LocalPennyStrings.current
    DetailScaffold(strings.aboutTitle, onBack) {
        item {
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    BrandOrb()
                    Spacer(Modifier.height(14.dp))
                    Text(strings.appName, style = MaterialTheme.typography.headlineLarge)
                    Text("Version ${BuildConfig.VERSION_NAME}", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                    Spacer(Modifier.height(6.dp))
                    Text(strings.aboutTagline, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
                }
            }
        }
        item {
            GlassPanel {
                Text(strings.aboutMission, style = MaterialTheme.typography.bodyLarge)
            }
        }
        item {
            Text(strings.capabilitiesTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(10.dp))
            GlassPanel {
                DetailPoint(Icons.AutoMirrored.Outlined.ReceiptLong, strings.cap1Title, strings.cap1Desc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Timeline, strings.cap2Title, strings.cap2Desc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Category, strings.cap3Title, strings.cap3Desc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Fingerprint, strings.cap4Title, strings.cap4Desc)
            }
        }
        item {
            Text(strings.limitsTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(10.dp))
            GlassPanel {
                DetailPoint(Icons.Outlined.Block, strings.limit1Title, strings.limit1Desc)
                SettingsDivider()
                DetailPoint(Icons.Outlined.Info, strings.limit2Title, strings.limit2Desc)
            }
        }
        item {
            Text(strings.techTitle, style = MaterialTheme.typography.titleLarge)
            Spacer(Modifier.height(10.dp))
            GlassPanel {
                DetailPoint(Icons.Outlined.Memory, strings.techTitle, strings.techDesc)
            }
        }
    }
}

@Composable
private fun DetailPoint(icon: ImageVector, title: String, detail: String) {
    Row(Modifier.fillMaxWidth().padding(vertical = 8.dp), verticalAlignment = Alignment.Top) {
        SettingsIcon(icon)
        Column(Modifier.padding(start = 12.dp)) {
            Text(title, style = MaterialTheme.typography.titleMedium)
            Text(detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
    }
}

@Composable
private fun DetailScaffold(title: String, onBack: () -> Unit, content: androidx.compose.foundation.lazy.LazyListScope.() -> Unit) {
    LazyColumn(
        Modifier.fillMaxSize().statusBarsPadding(),
        contentPadding = PaddingValues(20.dp, 12.dp, 20.dp, 32.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        item {
            Row(verticalAlignment = Alignment.CenterVertically) {
                IconButton(onClick = onBack) { Icon(Icons.AutoMirrored.Outlined.ArrowBack, "Back") }
                Text(title, style = MaterialTheme.typography.headlineMedium, modifier = Modifier.padding(start = 6.dp))
            }
        }
        content()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun TransactionSheet(
    transaction: TransactionEntity,
    savedDraftId: String?,
    savedTitle: String,
    savedDescription: String,
    onDraft: (String, String, String) -> Unit,
    onDismiss: () -> Unit,
    onSave: (String, String) -> Unit,
    onTransfer: (Boolean) -> Unit,
) {
    val isDebit = transaction.direction == Direction.DEBIT
    var title by remember(transaction.id) { mutableStateOf(if (savedDraftId == transaction.id) savedTitle else transaction.title.orEmpty()) }
    var description by remember(transaction.id) { mutableStateOf(if (savedDraftId == transaction.id) savedDescription else transaction.description.orEmpty()) }
    var noteExpanded by remember(transaction.id) { mutableStateOf(description.isNotEmpty()) }
    var saving by remember(transaction.id) { mutableStateOf(false) }
    LaunchedEffect(title, description) { if (isDebit) onDraft(transaction.id, title, description) }
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = true)
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = MaterialTheme.colorScheme.surface.copy(alpha = .98f),
        contentColor = MaterialTheme.colorScheme.onSurface,
        dragHandle = { BottomSheetDefaults.DragHandle() },
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .verticalScroll(rememberScrollState())
                .imePadding()
                .padding(horizontal = 22.dp)
                .padding(bottom = 24.dp)
        ) {
            val strings = LocalPennyStrings.current
            Text(if (transaction.title.isNullOrBlank()) strings.newExpense else strings.transactionDetails, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
            Text(formatMoney(transaction.amountMinor), style = MaterialTheme.typography.displayMedium)
            val account = listOfNotNull(transaction.bank, transaction.accountLast4?.let { "••$it" }).joinToString(" ").ifBlank { "Bank alert" }
            val date = Instant.ofEpochMilli(transaction.occurredAt).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("d MMM, h:mm a"))
            Text("$account · $date", style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Spacer(Modifier.height(26.dp))
            if (isDebit) {
                Text(strings.whatWasThisFor, style = MaterialTheme.typography.titleLarge)
                Spacer(Modifier.height(12.dp))
                OutlinedTextField(
                    value = title,
                    onValueChange = { if (it.length <= 60) title = it },
                    modifier = Modifier.fillMaxWidth(),
                    placeholder = { Text(strings.titlePlaceholder) },
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(onDone = { if (title.isNotBlank()) { saving = true; onSave(title, description) } }),
                    supportingText = { Text("${title.length}/60") },
                )
                LazyRow(Modifier.fillMaxWidth().padding(vertical = 10.dp), horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(listOf(strings.foodChip, strings.travelChip, strings.shoppingChip, strings.otherChip)) { chip ->
                        SuggestionChip(onClick = { title = chip }, label = { Text(chip) })
                    }
                }
                if (!noteExpanded) TextButton(onClick = { noteExpanded = true }) { Text(strings.addNote) }
                else OutlinedTextField(
                    value = description,
                    onValueChange = { if (it.length <= 300) description = it },
                    label = { Text(strings.descriptionLabel) },
                    modifier = Modifier.fillMaxWidth().heightIn(min = 100.dp),
                    supportingText = { Text("${description.length}/300") },
                )
                Spacer(Modifier.height(12.dp))
                Button(
                    onClick = { saving = true; onSave(title, description) },
                    enabled = title.isNotBlank() && !saving,
                    modifier = Modifier.fillMaxWidth().heightIn(min = 54.dp),
                    shape = RoundedCornerShape(18.dp),
                ) { Text(if (saving) strings.saving else strings.saveExpense) }
                TextButton(onClick = onDismiss, modifier = Modifier.fillMaxWidth()) { Text(strings.later) }
            } else {
                Text(transaction.merchantOrSender ?: strings.moneyReceived, style = MaterialTheme.typography.titleLarge)
            }
            if (!transaction.title.isNullOrBlank() || !isDebit) {
                HorizontalDivider(Modifier.padding(vertical = 16.dp), color = MaterialTheme.colorScheme.outlineVariant)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Checkbox(checked = transaction.kind == TransactionKind.OWN_TRANSFER, onCheckedChange = onTransfer)
                    Text(strings.transferBetweenAccounts)
                }
            }
        }
    }
}

@Composable
private fun PennyBottomBar(selected: PennyTab, onSelected: (PennyTab) -> Unit) {
    Box(Modifier.fillMaxWidth().windowInsetsPadding(WindowInsets.navigationBars).padding(horizontal = 16.dp, vertical = 8.dp)) {
        Surface(
            modifier = Modifier.fillMaxWidth().shadow(20.dp, RoundedCornerShape(28.dp)),
            shape = RoundedCornerShape(28.dp),
            color = MaterialTheme.colorScheme.surface.copy(alpha = .92f),
            tonalElevation = 3.dp,
        ) {
            NavigationBar(containerColor = Color.Transparent, tonalElevation = 0.dp, windowInsets = WindowInsets(0, 0, 0, 0)) {
                val strings = LocalPennyStrings.current
                listOf(
                    Triple(PennyTab.HOME, strings.home, Icons.Outlined.Home),
                    Triple(PennyTab.HISTORY, strings.history, Icons.Outlined.History),
                    Triple(PennyTab.SETTINGS, strings.settings, Icons.Outlined.Settings),
                ).forEach { (tab, label, icon) ->
                    NavigationBarItem(
                        selected = selected == tab,
                        onClick = { onSelected(tab) },
                        icon = { Icon(if (selected == tab && tab == PennyTab.HOME) Icons.Filled.Home else icon, label) },
                        label = { Text(label) },
                        colors = NavigationBarItemDefaults.colors(
                            indicatorColor = MaterialTheme.colorScheme.primaryContainer,
                            selectedIconColor = MaterialTheme.colorScheme.primary,
                            selectedTextColor = MaterialTheme.colorScheme.primary,
                            unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                        ),
                    )
                }
            }
        }
    }
}

@Composable
private fun GlassSnackbarHost(hostState: SnackbarHostState) {
    androidx.compose.material3.SnackbarHost(hostState) { data ->
        var launched by remember(data) { mutableStateOf(false) }
        LaunchedEffect(data) {
            launched = true
        }
        val animatedScale by animateFloatAsState(
            targetValue = if (launched) 1f else 0.88f,
            animationSpec = spring(dampingRatio = 0.68f, stiffness = 420f),
            label = "toast_scale"
        )
        val animatedOffset by animateDpAsState(
            targetValue = if (launched) 0.dp else 24.dp,
            animationSpec = spring(dampingRatio = 0.68f, stiffness = 420f),
            label = "toast_offset"
        )
        val animatedAlpha by animateFloatAsState(
            targetValue = if (launched) 1f else 0f,
            animationSpec = spring(dampingRatio = 0.85f, stiffness = 420f),
            label = "toast_alpha"
        )

        val offsetX = remember(data) { Animatable(0f) }
        val scope = rememberCoroutineScope()
        val swipeAlpha = (1f - (offsetX.value.absoluteValue / 500f)).coerceIn(0f, 1f)

        val isDark = MaterialTheme.colorScheme.background.red < .1f
        val shape = RoundedCornerShape(24.dp)
        val glassBg = if (isDark) {
            Brush.linearGradient(listOf(Color(0xFF221A35).copy(alpha = 0.91f), Color(0xFF130E1D).copy(alpha = 0.89f)))
        } else {
            Brush.linearGradient(listOf(Color(0xFFFFFFFF).copy(alpha = 0.93f), Color(0xFFF5EEFC).copy(alpha = 0.89f)))
        }
        val glassBorder = if (isDark) {
            Brush.linearGradient(listOf(Color.White.copy(alpha = 0.12f), AuroraViolet.copy(alpha = 0.18f), Color.White.copy(alpha = 0.04f)))
        } else {
            Brush.linearGradient(listOf(Color.White.copy(alpha = 0.70f), AuroraViolet.copy(alpha = 0.12f), Color.White.copy(alpha = 0.35f)))
        }
        val ambientShadow = if (isDark) Color.Black.copy(alpha = 0.50f) else Color(0xFF6540D7).copy(alpha = 0.14f)
        val spotShadow = if (isDark) AuroraViolet.copy(alpha = 0.28f) else Color(0xFF1C1822).copy(alpha = 0.10f)

        Surface(
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 12.dp)
                .offset { IntOffset(offsetX.value.roundToInt(), 0) }
                .offset(y = animatedOffset)
                .scale(animatedScale)
                .alpha(animatedAlpha * swipeAlpha)
                .shadow(elevation = 16.dp, shape = shape, ambientColor = ambientShadow, spotColor = spotShadow)
                .pointerInput(data) {
                    detectHorizontalDragGestures(
                        onDragEnd = {
                            if (offsetX.value.absoluteValue > 180f) {
                                val targetX = if (offsetX.value > 0) 1400f else -1400f
                                scope.launch {
                                    offsetX.animateTo(targetX, animationSpec = tween(160))
                                    data.dismiss()
                                }
                            } else {
                                scope.launch {
                                    offsetX.animateTo(0f, animationSpec = spring(dampingRatio = 0.72f, stiffness = 450f))
                                }
                            }
                        },
                        onHorizontalDrag = { change, dragAmount ->
                            change.consume()
                            scope.launch {
                                offsetX.snapTo(offsetX.value + dragAmount)
                            }
                        }
                    )
                },
            color = Color.Transparent,
            shape = shape,
        ) {
            Row(
                Modifier
                    .clip(shape)
                    .background(glassBg)
                    .border(0.5.dp, glassBorder, shape)
                    .padding(horizontal = 16.dp, vertical = 13.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    painter = painterResource(R.drawable.app_logo),
                    contentDescription = "Penny",
                    modifier = Modifier
                        .size(36.dp)
                        .clip(RoundedCornerShape(10.dp)),
                )
                Column(Modifier.weight(1f).padding(start = 14.dp)) {
                    Text(
                        "PENNY",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        letterSpacing = 1.2.sp,
                        color = if (isDark) AuroraCyan.copy(alpha = 0.95f) else MaterialTheme.colorScheme.primary
                    )
                    Text(
                        data.visuals.message,
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = if (isDark) Color(0xFFF3EDF7) else Color(0xFF1C1822)
                    )
                }
                data.visuals.actionLabel?.let { label ->
                    TextButton(onClick = { data.performAction() }, contentPadding = PaddingValues(horizontal = 8.dp)) {
                        Text(
                            label,
                            color = if (isDark) AuroraCyan else MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.labelLarge,
                            fontWeight = FontWeight.Bold
                         )
                    }
                }
            }
        }
    }
}

@Composable
private fun GlassPanel(contentPadding: PaddingValues = PaddingValues(18.dp), content: @Composable ColumnScope.() -> Unit) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(24.dp),
        color = MaterialTheme.colorScheme.surface.copy(alpha = .82f),
        tonalElevation = 2.dp,
    ) {
        Column(Modifier.padding(contentPadding), content = content)
    }
}

@Composable
private fun creditColor(): Color = if (MaterialTheme.colorScheme.background.red < .1f) DarkMoneyGreen else MoneyGreen

private fun formatMoney(amountMinor: Long): String {
    val value = amountMinor / 100.0
    return NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
        minimumFractionDigits = if (amountMinor % 100 == 0L) 0 else 2
        maximumFractionDigits = 2
    }.format(value)
}

private fun formatLobeAmount(amountMinor: Long): String {
    val rupees = amountMinor / 100.0
    val wholeRupees = rupees.toLong()
    return if (wholeRupees >= 1_000_000) {
        val lakhs = rupees / 100_000.0
        String.format(Locale.forLanguageTag("en-IN"), "₹%.1fL", lakhs).replace(".0L", "L")
    } else {
        NumberFormat.getCurrencyInstance(Locale.forLanguageTag("en-IN")).apply {
            minimumFractionDigits = 0
            maximumFractionDigits = 0
        }.format(wholeRupees)
    }
}
