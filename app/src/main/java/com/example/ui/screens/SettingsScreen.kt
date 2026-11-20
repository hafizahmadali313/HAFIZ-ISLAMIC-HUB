package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.PhotoCamera
import androidx.compose.material.icons.filled.Policy
import androidx.compose.material.icons.filled.RestartAlt
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Slider
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import coil.compose.AsyncImage
import com.example.BuildConfig
import com.example.R
import com.example.data.model.AppPreferences
import com.example.data.model.DeveloperProfile
import com.example.data.model.LanguageDisplayMode
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.IslamicGeometricBackdrop
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.theme.ThemeMode
import com.example.ui.viewmodel.MainViewModel
import java.io.File

@Composable
fun SettingsScreen(
    preferences: AppPreferences,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var showEditDeveloperDialog by remember { mutableStateOf(false) }
    var showPrivacyDialog by remember { mutableStateOf(false) }
    var showAboutDialog by remember { mutableStateOf(false) }

    // Zero-permission Android Photo Picker (Google Play Policy compliant)
    val photoPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.PickVisualMedia()
    ) { uri ->
        if (uri != null) {
            mainViewModel.updateDeveloperPhoto(uri)
        }
    }

    val notificationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            mainViewModel.updateNotifications(preferences.notifications.copy(masterEnabled = true))
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        IslamicGeometricBackdrop()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter),
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                Column {
                    Text(
                        text = "Settings & Developer Profile",
                        style = MaterialTheme.typography.headlineMedium.copy(fontFamily = PlayfairFontFamily),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Customize theme, reading sizes, Azan notifications & builder profile",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // DEVELOPER PROFILE CARD (Editable + Changeable Photo)
            item {
                val dev = preferences.developerProfile
                val photoFile = dev.photoPath?.let { File(it) }?.takeIf { it.exists() }

                Card(
                    modifier = Modifier
                        .fillMaxWidth()
                        .testTag("developer_profile_card"),
                    shape = RoundedCornerShape(22.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                    border = androidx.compose.foundation.BorderStroke(
                        1.5.dp,
                        MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(18.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.SpaceBetween,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Text(
                                text = "DEVELOPER PROFILE",
                                style = MaterialTheme.typography.labelMedium.copy(
                                    fontWeight = FontWeight.Bold,
                                    letterSpacing = 1.sp
                                ),
                                color = MaterialTheme.colorScheme.secondary
                            )
                            Icon(
                                Icons.Default.Verified,
                                contentDescription = "Verified App Builder",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            if (photoFile != null) {
                                AsyncImage(
                                    model = photoFile,
                                    contentDescription = "Developer photograph",
                                    modifier = Modifier
                                        .size(82.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            } else {
                                Image(
                                    painter = painterResource(id = R.drawable.img_developer_placeholder),
                                    contentDescription = "Default developer emblem",
                                    modifier = Modifier
                                        .size(82.dp)
                                        .clip(CircleShape)
                                        .border(2.dp, MaterialTheme.colorScheme.secondary, CircleShape),
                                    contentScale = ContentScale.Crop
                                )
                            }

                            Spacer(modifier = Modifier.width(14.dp))

                            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(2.dp)) {
                                Text(
                                    text = "App Builder: ${dev.name}",
                                    style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                    color = MaterialTheme.colorScheme.primary
                                )
                                Text(
                                    text = dev.title,
                                    style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.SemiBold),
                                    color = MaterialTheme.colorScheme.onSurface
                                )
                                Text(
                                    text = dev.email,
                                    style = MaterialTheme.typography.bodySmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                            }
                        }

                        Text(
                            text = dev.bio,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = {
                                    photoPickerLauncher.launch(
                                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                                    )
                                },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("change_dev_photo_button")
                            ) {
                                Icon(Icons.Default.PhotoCamera, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Change Photo", maxLines = 1)
                            }

                            OutlinedButton(
                                onClick = { showEditDeveloperDialog = true },
                                modifier = Modifier
                                    .weight(1f)
                                    .testTag("edit_dev_profile_button")
                            ) {
                                Icon(Icons.Default.Edit, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Edit Profile", maxLines = 1)
                            }
                        }

                        if (photoFile != null) {
                            TextButton(
                                onClick = { mainViewModel.clearDeveloperPhoto() },
                                modifier = Modifier.align(Alignment.End)
                            ) {
                                Icon(Icons.Default.RestartAlt, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(4.dp))
                                Text("Reset to Default Emblem")
                            }
                        }
                    }
                }
            }

            // APPEARANCE & LANGUAGE
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        Text("Theme & Language", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        Text("App Theme", style = MaterialTheme.typography.labelLarge)
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            ThemeMode.entries.forEach { mode ->
                                FilterChip(
                                    selected = preferences.themeMode == mode,
                                    onClick = { mainViewModel.setThemeMode(mode) },
                                    label = { Text(mode.name.lowercase().replaceFirstChar { it.uppercase() }) },
                                    modifier = Modifier.testTag("theme_chip_${mode.name}")
                                )
                            }
                        }

                        HorizontalDivider()

                        Text("Translation Language Display", style = MaterialTheme.typography.labelLarge)
                        LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            items(LanguageDisplayMode.entries) { mode ->
                                FilterChip(
                                    selected = preferences.languageMode == mode,
                                    onClick = { mainViewModel.setLanguageMode(mode) },
                                    label = { Text(mode.label) }
                                )
                            }
                        }
                    }
                }
            }

            // TEXT SIZE SETTINGS
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Text Size Settings", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        Text("Arabic Size: ${preferences.arabicFontSizeSp.toInt()} sp", style = MaterialTheme.typography.labelLarge)
                        Slider(
                            value = preferences.arabicFontSizeSp,
                            onValueChange = { mainViewModel.setFontSizes(it, preferences.urduFontSizeSp) },
                            valueRange = 18f..42f
                        )
                        Text(
                            text = "بِسْمِ ٱللَّهِ ٱلرَّحْمَٰنِ ٱلرَّحِيمِ",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = AmiriFontFamily,
                                fontSize = preferences.arabicFontSizeSp.sp
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )

                        HorizontalDivider()

                        Text("Urdu Translation Size: ${preferences.urduFontSizeSp.toInt()} sp", style = MaterialTheme.typography.labelLarge)
                        Slider(
                            value = preferences.urduFontSizeSp,
                            onValueChange = { mainViewModel.setFontSizes(preferences.arabicFontSizeSp, it) },
                            valueRange = 13f..26f
                        )
                        Text(
                            text = "شروع اللہ کے نام سے جو بڑا مہربان نہایت رحم والا ہے",
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontFamily = AmiriFontFamily,
                                fontSize = preferences.urduFontSizeSp.sp
                            )
                        )
                    }
                }
            }

            // AZAN & NOTIFICATION SETTINGS
            item {
                val notif = preferences.notifications
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Azan & Prayer Notifications", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))

                        SettingToggleRow(
                            label = "Enable Prayer & Islamic Alerts",
                            checked = notif.masterEnabled,
                            onCheckedChange = { enabled ->
                                if (enabled && Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val granted = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (!granted) {
                                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        return@SettingToggleRow
                                    }
                                }
                                mainViewModel.updateNotifications(notif.copy(masterEnabled = enabled))
                            }
                        )

                        if (notif.masterEnabled) {
                            HorizontalDivider()
                            SettingToggleRow("Fajr Azan Alert", notif.fajrEnabled) {
                                mainViewModel.updateNotifications(notif.copy(fajrEnabled = it))
                            }
                            SettingToggleRow("Dhuhr Azan Alert", notif.dhuhrEnabled) {
                                mainViewModel.updateNotifications(notif.copy(dhuhrEnabled = it))
                            }
                            SettingToggleRow("Asr Azan Alert", notif.asrEnabled) {
                                mainViewModel.updateNotifications(notif.copy(asrEnabled = it))
                            }
                            SettingToggleRow("Maghrib Azan Alert", notif.maghribEnabled) {
                                mainViewModel.updateNotifications(notif.copy(maghribEnabled = it))
                            }
                            SettingToggleRow("Isha Azan Alert", notif.ishaEnabled) {
                                mainViewModel.updateNotifications(notif.copy(ishaEnabled = it))
                            }
                            SettingToggleRow("Daily Islamic Reminder (8:30 AM)", notif.dailyReminderEnabled) {
                                mainViewModel.updateNotifications(notif.copy(dailyReminderEnabled = it))
                            }
                            SettingToggleRow("Vibrate Only (Silent Alert)", notif.vibrateOnly) {
                                mainViewModel.updateNotifications(notif.copy(vibrateOnly = it))
                            }

                            Text("Reminder Timing", style = MaterialTheme.typography.labelLarge)
                            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                                listOf(0 to "At Azan", 10 to "10 min before", 15 to "15 min before").forEach { (min, label) ->
                                    FilterChip(
                                        selected = notif.reminderMinutesBefore == min,
                                        onClick = { mainViewModel.updateNotifications(notif.copy(reminderMinutesBefore = min)) },
                                        label = { Text(label) }
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(4.dp))
                        OutlinedButton(
                            onClick = {
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                                    val granted = ContextCompat.checkSelfPermission(
                                        context,
                                        Manifest.permission.POST_NOTIFICATIONS
                                    ) == PackageManager.PERMISSION_GRANTED
                                    if (!granted) {
                                        notificationPermissionLauncher.launch(Manifest.permission.POST_NOTIFICATIONS)
                                        return@OutlinedButton
                                    }
                                }
                                mainViewModel.sendTestPrayerNotification()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .testTag("test_notification_button")
                        ) {
                            Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text("Send Test Prayer Notification")
                        }
                    }
                }
            }

            // ABOUT APP & PRIVACY POLICY
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("App Information", style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold))
                        Text(
                            text = "${stringResource(R.string.app_brand_title)} • Version ${BuildConfig.VERSION_NAME}",
                            style = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.SemiBold),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Developed & Managed By: ${preferences.developerProfile.name}",
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Text(
                            text = "Ad-free Islamic companion featuring 114 Quran Surahs with Fateh Muhammad Jalandhry Urdu translation (AlQuran.cloud), Astronomical Salah timings, Qibla Compass, Masnoon Duas, 99 Names of Allah, Tasbeeh, and Islamic Quiz.",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(10.dp)
                        ) {
                            OutlinedButton(
                                onClick = { showAboutDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Info, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("About App")
                            }
                            OutlinedButton(
                                onClick = { showPrivacyDialog = true },
                                modifier = Modifier.weight(1f)
                            ) {
                                Icon(Icons.Default.Policy, contentDescription = null, modifier = Modifier.size(16.dp))
                                Spacer(modifier = Modifier.width(6.dp))
                                Text("Privacy Policy")
                            }
                        }
                    }
                }
            }

            item { Spacer(modifier = Modifier.height(12.dp)) }
        }
    }

    if (showEditDeveloperDialog) {
        EditDeveloperProfileDialog(
            current = preferences.developerProfile,
            onDismiss = { showEditDeveloperDialog = false },
            onSave = { updated ->
                mainViewModel.updateDeveloperProfile(updated)
                showEditDeveloperDialog = false
            }
        )
    }

    if (showAboutDialog) {
        AlertDialog(
            onDismissRequest = { showAboutDialog = false },
            title = { Text(stringResource(R.string.app_brand_title)) },
            text = {
                Text(
                    "Version ${BuildConfig.VERSION_NAME}\n\n" +
                        "Developed & Managed By: ${preferences.developerProfile.name}\n\n" +
                        "Reliable Data Sources:\n" +
                        "• Holy Quran (Uthmani Arabic & Urdu by Maulana Fateh Muhammad Jalandhry) via AlQuran.cloud & offline bundled Surahs.\n" +
                        "• Prayer Timings via pure astronomical solar geometry.\n" +
                        "• Duas & Hadith references verified from Sahih al-Bukhari, Sahih Muslim, Abu Dawud, Tirmidhi, and Hisn al-Muslim."
                )
            },
            confirmButton = {
                TextButton(onClick = { showAboutDialog = false }) { Text("Close") }
            }
        )
    }

    if (showPrivacyDialog) {
        AlertDialog(
            onDismissRequest = { showPrivacyDialog = false },
            title = { Text("Privacy Policy") },
            text = {
                Text(
                    "HAFIZ ISLAMIC HUB respects your privacy completely:\n\n" +
                        "1. Zero Advertisements & Zero Tracking: We do not show ads or sell user data.\n" +
                        "2. Local Storage Only: Your bookmarks, Tasbeeh counts, quiz scores, and developer profile photo stay strictly on your device.\n" +
                        "3. Location Access: Coarse location is used solely on-device to calculate Salah timings and Qibla bearing.\n" +
                        "4. Photo Picker: Selecting a profile photo uses Android's zero-permission Photo Picker without broad storage access."
                )
            },
            confirmButton = {
                TextButton(onClick = { showPrivacyDialog = false }) { Text("Understood") }
            }
        )
    }
}

@Composable
private fun SettingToggleRow(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(text = label, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.weight(1f))
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}

@Composable
private fun EditDeveloperProfileDialog(
    current: DeveloperProfile,
    onDismiss: () -> Unit,
    onSave: (DeveloperProfile) -> Unit
) {
    var name by remember { mutableStateOf(current.name) }
    var title by remember { mutableStateOf(current.title) }
    var bio by remember { mutableStateOf(current.bio) }
    var email by remember { mutableStateOf(current.email) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Edit Developer Profile") },
        text = {
            Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("App Builder Name") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = title,
                    onValueChange = { title = it },
                    label = { Text("Role / Title") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    label = { Text("Contact Email") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth()
                )
                OutlinedTextField(
                    value = bio,
                    onValueChange = { bio = it },
                    label = { Text("Developer Bio") },
                    minLines = 2,
                    maxLines = 4,
                    modifier = Modifier.fillMaxWidth()
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        current.copy(
                            name = name.trim().ifBlank { "HAFIZ AHMAD ALI" },
                            title = title.trim(),
                            bio = bio.trim(),
                            email = email.trim()
                        )
                    )
                }
            ) {
                Text("Save Profile")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text("Cancel") }
        }
    )
}
