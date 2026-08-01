package com.mydgnbot.ui.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.mydgnbot.ui.theme.DarkBg
import com.mydgnbot.ui.theme.Emerald
import com.mydgnbot.ui.theme.TextMuted
import com.mydgnbot.ui.viewmodel.SettingsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingsScreen(
    onBackClick: () -> Unit,
    viewModel: SettingsViewModel
) {
    val settingsState by viewModel.settings.collectAsState()

    var apiUser by remember(settingsState) { mutableStateOf(settingsState["api_user"] ?: "") }
    var secretKey by remember(settingsState) { mutableStateOf(settingsState["secret_key"] ?: "") }
    var eaEmail by remember(settingsState) { mutableStateOf(settingsState["ea_email"] ?: "") }
    var platform by remember(settingsState) { mutableStateOf(settingsState["platform"] ?: "Console") }
    var playerMethod by remember(settingsState) { mutableStateOf(settingsState["player_type"] ?: "2") }
    var minPrice by remember(settingsState) { mutableStateOf(settingsState["minimum_price"] ?: "1000") }
    var maxPrice by remember(settingsState) { mutableStateOf(settingsState["maximum_price"] ?: "300000") }
    var searchInterval by remember(settingsState) { mutableStateOf(settingsState["poll_interval"] ?: "10") }

    var isSecretVisible by remember { mutableStateOf(false) }

    Scaffold(
        containerColor = DarkBg,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Settings",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                            tint = Color.White
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = DarkBg)
            )
        },
        bottomBar = {
            Surface(
                color = DarkBg,
                tonalElevation = 8.dp,
                modifier = Modifier
                    .fillMaxWidth()
                    .border(BorderStroke(1.dp, Color(0xFF19221C)))
            ) {
                Box(modifier = Modifier.padding(16.dp)) {
                    Button(
                        onClick = {
                            viewModel.saveSettings(
                                apiUser = apiUser,
                                secretKey = secretKey,
                                eaEmail = eaEmail,
                                platform = platform,
                                minimumPrice = minPrice,
                                maximumPrice = maxPrice,
                                playerType = playerMethod,
                                pollInterval = searchInterval
                            )
                            onBackClick()
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(containerColor = Emerald)
                    ) {
                        Text(
                            text = "Save Configuration",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            // 1. API CONFIGURATION CARD
            SettingsCard(title = "API CONFIGURATION") {
                CustomTextField(
                    value = apiUser,
                    onValueChange = { apiUser = it },
                    label = "API Username",
                    icon = Icons.Default.Person
                )

                CustomTextField(
                    value = secretKey,
                    onValueChange = { secretKey = it },
                    label = "Secret Key",
                    icon = Icons.Default.Lock,
                    isPassword = true,
                    isPasswordVisible = isSecretVisible,
                    onVisibilityToggle = { isSecretVisible = !isSecretVisible }
                )

                CustomTextField(
                    value = eaEmail,
                    onValueChange = { eaEmail = it },
                    label = "EA Account Email",
                    icon = Icons.Default.Email,
                    keyboardType = KeyboardType.Email
                )
            }

            // 2. BOT TARGET PLATFORM & STRATEGY CARD
            SettingsCard(title = "BOT CONFIGURATION") {
                Text(
                    text = "Platform",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )
                SegmentedControl(
                    items = listOf("Console", "PC"),
                    selectedIndex = if (platform.equals("PC", ignoreCase = true)) 1 else 0,
                    onItemSelection = { index ->
                        platform = if (index == 1) "PC" else "Console"
                    }
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Player Strategy Method",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = TextMuted
                )
                SegmentedControl(
                    items = listOf("Safe", "Quick Sell"),
                    selectedIndex = if (playerMethod == "1") 0 else 1,
                    onItemSelection = { index ->
                        playerMethod = if (index == 0) "1" else "2"
                    }
                )
            }

            // 3. MARKET SCANNER PARAMETERS CARD
            SettingsCard(title = "SEARCH FILTERS & INTERVALS") {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = minPrice,
                            onValueChange = { minPrice = it },
                            label = "Min Buy Price",
                            keyboardType = KeyboardType.Number
                        )
                    }
                    Box(modifier = Modifier.weight(1f)) {
                        CustomTextField(
                            value = maxPrice,
                            onValueChange = { maxPrice = it },
                            label = "Max Buy Price",
                            keyboardType = KeyboardType.Number
                        )
                    }
                }

                CustomTextField(
                    value = searchInterval,
                    onValueChange = { searchInterval = it },
                    label = "Search Interval (seconds)",
                    keyboardType = KeyboardType.Number
                )

                // Presets without 5s option
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Presets:", fontSize = 11.sp, color = TextMuted)
                    listOf("10", "15", "30").forEach { sec ->
                        val isSelected = searchInterval == sec
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(if (isSelected) Emerald else Color(0xFF141A16))
                                .border(
                                    1.dp,
                                    if (isSelected) Emerald else Color(0xFF27302A),
                                    RoundedCornerShape(8.dp)
                                )
                                .clickable { searchInterval = sec }
                                .padding(horizontal = 10.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "${sec}s",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) Color.Black else Color.White
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}

@Composable
private fun SettingsCard(
    title: String,
    content: @Composable ColumnScope.() -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .border(1.dp, Color(0xFF19221C), RoundedCornerShape(16.dp)),
        color = Color(0xFF09120D)
    ) {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            Text(
                text = title,
                fontSize = 11.sp,
                fontWeight = FontWeight.Black,
                color = Emerald,
                letterSpacing = 1.sp
            )
            content()
        }
    }
}

@Composable
private fun CustomTextField(
    value: String,
    onValueChange: (String) -> Unit,
    label: String,
    icon: ImageVector? = null,
    isPassword: Boolean = false,
    isPasswordVisible: Boolean = false,
    onVisibilityToggle: (() -> Unit)? = null,
    keyboardType: KeyboardType = KeyboardType.Text
) {
    OutlinedTextField(
        value = value,
        onValueChange = onValueChange,
        label = { Text(label, fontSize = 12.sp) },
        singleLine = true,
        leadingIcon = icon?.let {
            { Icon(it, contentDescription = null, tint = Emerald, modifier = Modifier.size(18.dp)) }
        },
        trailingIcon = if (isPassword && onVisibilityToggle != null) {
            {
                Text(
                    text = if (isPasswordVisible) "HIDE" else "SHOW",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Bold,
                    color = Emerald,
                    modifier = Modifier
                        .clip(RoundedCornerShape(4.dp))
                        .clickable { onVisibilityToggle() }
                        .padding(horizontal = 8.dp, vertical = 4.dp)
                )
            }
        } else null,
        visualTransformation = if (isPassword && !isPasswordVisible) PasswordVisualTransformation() else VisualTransformation.None,
        keyboardOptions = KeyboardOptions(keyboardType = keyboardType, imeAction = ImeAction.Next),
        colors = OutlinedTextFieldDefaults.colors(
            focusedContainerColor = Color(0xFF0B120E),
            unfocusedContainerColor = Color(0xFF0B120E),
            focusedBorderColor = Emerald,
            unfocusedBorderColor = Color(0xFF1E2822),
            focusedLabelColor = Emerald,
            unfocusedLabelColor = TextMuted,
            focusedTextColor = Color.White,
            unfocusedTextColor = Color.White
        ),
        shape = RoundedCornerShape(12.dp),
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun SegmentedControl(
    items: List<String>,
    selectedIndex: Int,
    onItemSelection: (Int) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(44.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF040A07))
            .border(1.dp, Color(0xFF1E2822), RoundedCornerShape(12.dp))
            .padding(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        items.forEachIndexed { index, item ->
            val isSelected = selectedIndex == index
            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(8.dp))
                    .background(if (isSelected) Emerald else Color.Transparent)
                    .clickable { onItemSelection(index) },
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    color = if (isSelected) Color.Black else Color.White
                )
            }
        }
    }
}
