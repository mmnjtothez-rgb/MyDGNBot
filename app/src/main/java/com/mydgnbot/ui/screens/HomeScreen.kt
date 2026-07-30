@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel,
    onSettingsClick: () -> Unit,
    onHistoryClick: () -> Unit,
    onPlayerFound: () -> Unit
) {
    val player by viewModel.player.collectAsState()
    val isRunning by viewModel.isRunning.collectAsState()
    val isOnline by viewModel.isOnline.collectAsState()
    val settings by viewModel.settings.collectAsState()
    val botStatus by viewModel.botStatus.collectAsState()
    val waitSeconds by viewModel.waitSeconds.collectAsState()
    val statusText by viewModel.statusText.collectAsState()

    LaunchedEffect(player) {
        if (player != null) {
            onPlayerFound()
        }
    }

    val platform = settings["platform"] ?: "Console"
    val playerTypeRaw = settings["player_type"] ?: "2"
    val pollIntervalRaw = settings["poll_interval"] ?: "10"

    val playerMethodEnum = PlayerMethod.fromValue(
        playerTypeRaw.toIntOrNull() ?: 2
    )

    val methodChip = when (playerMethodEnum) {
        PlayerMethod.SAFE -> "Safe"
        PlayerMethod.QUICKSELL -> "Quicksell"
    }

    val intervalChip = pollIntervalRaw
        .toIntOrNull()
        ?.let { "${it}s" }
        ?: "0s"

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "MyDGN Bot",
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                actions = {
                    IconButton(onClick = onSettingsClick) {
                        Icon(
                            imageVector = Icons.Filled.Settings,
                            contentDescription = "Settings"
                        )
                    }
                }
            )
        }
    ) { padding ->
        val scrollState = rememberScrollState()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 12.dp)
                .verticalScroll(scrollState),
            verticalArrangement = Arrangement.Top
        ) {
            StatusChipsRow(
                connected = isOnline,
                platform = platform,
                method = methodChip,
                interval = intervalChip,
                onSettingsClick = onSettingsClick
            )

            Spacer(modifier = Modifier.height(10.dp))

            RadarScannerCard(
                isRunning = isRunning,
                playerFound = player != null,
                connected = isOnline,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            BotStatusCard(
                status = botStatus,
                statusText = statusText,
                waitSeconds = waitSeconds,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(12.dp))

            val actionState = when {
                isRunning -> BotActionState.SEARCHING
                player != null -> BotActionState.PLAYER_FOUND
                else -> BotActionState.IDLE
            }

            ActionButtons(
                state = actionState,
                onStartClick = { viewModel.startBot() },
                onStopClick = { viewModel.stopBot() },
                onBoughtClick = { viewModel.markBought() },
                onCancelClick = { viewModel.cancelPlayer() },
                onHistoryClick = {
                    viewModel.requestHistory()
                    onHistoryClick()
                }
            )

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}