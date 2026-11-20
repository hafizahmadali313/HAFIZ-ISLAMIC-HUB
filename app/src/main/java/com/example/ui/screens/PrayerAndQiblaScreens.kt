package com.example.ui.screens

import android.Manifest
import android.content.pm.PackageManager
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.WbSunny
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.R
import com.example.data.model.AppPreferences
import com.example.data.model.AsrMadhhab
import com.example.data.model.CityLocation
import com.example.data.model.PrayerCalcMethod
import com.example.data.model.PrayerSchedule
import com.example.domain.PrayerTimeCalculator
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.GoldBurnished
import com.example.ui.theme.IslamicGeometricBackdrop
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.viewmodel.PrayersQiblaViewModel
import java.util.Locale
import kotlin.math.cos
import kotlin.math.sin

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PrayersAndQiblaScreen(
    initialTab: Int,
    preferences: AppPreferences,
    prayerSchedule: PrayerSchedule,
    viewModel: PrayersQiblaViewModel,
    onOpenNotificationSettings: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedTab by remember(initialTab) { mutableIntStateOf(initialTab) }
    var showCitySheet by remember { mutableStateOf(false) }

    val context = LocalContext.current
    val isDetecting by viewModel.isDetectingLocation.collectAsStateWithLifecycle()
    val locationStatus by viewModel.locationStatus.collectAsStateWithLifecycle()

    val locationPermissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            viewModel.detectDeviceLocation()
        } else {
            showCitySheet = true
        }
    }

    val requestLocation: () -> Unit = {
        val granted = ContextCompat.checkSelfPermission(
            context,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
        if (granted) {
            viewModel.detectDeviceLocation()
        } else {
            locationPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
        }
    }

    Box(modifier = modifier.fillMaxSize()) {
        IslamicGeometricBackdrop()

        Column(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter)
        ) {
            Column(modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp)) {
                Text(
                    text = if (selectedTab == 0) "Namaz & Prayer Times" else "Qibla Compass",
                    style = MaterialTheme.typography.headlineMedium.copy(fontFamily = PlayfairFontFamily),
                    color = MaterialTheme.colorScheme.primary
                )
                Text(
                    text = "${prayerSchedule.cityName}, ${prayerSchedule.country} • ${prayerSchedule.hijriDate}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            TabRow(selectedTabIndex = selectedTab) {
                Tab(
                    selected = selectedTab == 0,
                    onClick = { selectedTab = 0 },
                    text = { Text("Prayer Times") },
                    modifier = Modifier.testTag("tab_prayer_times")
                )
                Tab(
                    selected = selectedTab == 1,
                    onClick = { selectedTab = 1 },
                    text = { Text("Qibla Compass") },
                    modifier = Modifier.testTag("tab_qibla_compass")
                )
            }

            if (selectedTab == 0) {
                PrayerTimesTabContent(
                    preferences = preferences,
                    prayerSchedule = prayerSchedule,
                    isDetecting = isDetecting,
                    locationStatus = locationStatus,
                    onDetectLocation = requestLocation,
                    onOpenCityPicker = { showCitySheet = true },
                    onSelectMethod = { viewModel.setPrayerCalcMethod(it) },
                    onSelectMadhhab = { viewModel.setAsrMadhhab(it) },
                    onOpenQibla = { selectedTab = 1 },
                    onOpenNotificationSettings = onOpenNotificationSettings
                )
            } else {
                QiblaTabContent(
                    preferences = preferences,
                    viewModel = viewModel,
                    onOpenCityPicker = { showCitySheet = true }
                )
            }
        }
    }

    if (showCitySheet) {
        CityPickerBottomSheet(
            viewModel = viewModel,
            selectedCity = preferences.selectedCity,
            onDismiss = { showCitySheet = false }
        )
    }
}

@Composable
private fun PrayerTimesTabContent(
    preferences: AppPreferences,
    prayerSchedule: PrayerSchedule,
    isDetecting: Boolean,
    locationStatus: String?,
    onDetectLocation: () -> Unit,
    onOpenCityPicker: () -> Unit,
    onSelectMethod: (PrayerCalcMethod) -> Unit,
    onSelectMadhhab: (AsrMadhhab) -> Unit,
    onOpenQibla: () -> Unit,
    onOpenNotificationSettings: () -> Unit
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        // Next prayer highlight card
        item {
            Surface(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                color = MaterialTheme.colorScheme.primary
            ) {
                Column(
                    modifier = Modifier.padding(20.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "NEXT PRAYER",
                                style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                                color = MaterialTheme.colorScheme.secondaryContainer
                            )
                            Text(
                                text = "${prayerSchedule.nextPrayerName} • ${prayerSchedule.nextPrayerTime}",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Surface(
                            color = MaterialTheme.colorScheme.secondary,
                            shape = RoundedCornerShape(14.dp)
                        ) {
                            Text(
                                text = PrayerTimeCalculator.formatCountdown(prayerSchedule.nextPrayerMillisRemaining),
                                style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondary,
                                modifier = Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
                            )
                        }
                    }

                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(
                            onClick = onDetectLocation,
                            enabled = !isDetecting,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.16f),
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("detect_gps_button")
                        ) {
                            if (isDetecting) {
                                CircularProgressIndicator(
                                    modifier = Modifier.size(16.dp),
                                    strokeWidth = 2.dp,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            } else {
                                Icon(Icons.Default.MyLocation, contentDescription = null, modifier = Modifier.size(16.dp))
                            }
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Auto GPS", maxLines = 1)
                        }

                        Button(
                            onClick = onOpenCityPicker,
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                                contentColor = MaterialTheme.colorScheme.onSecondaryContainer
                            ),
                            modifier = Modifier
                                .weight(1f)
                                .testTag("select_city_button")
                        ) {
                            Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("Select City", maxLines = 1)
                        }
                    }
                }
            }
        }

        if (locationStatus != null) {
            item {
                Surface(
                    shape = RoundedCornerShape(12.dp),
                    color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.7f),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text(
                        text = locationStatus,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSecondaryContainer,
                        modifier = Modifier.padding(12.dp)
                    )
                }
            }
        }

        // Daily Prayer Schedule List
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(20.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(
                        text = "Daily Salah Schedule",
                        style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = prayerSchedule.gregorianDate,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    prayerSchedule.prayers.forEachIndexed { idx, entry ->
                        val isNext = !entry.isSunrise && entry.name == prayerSchedule.nextPrayerName
                        Surface(
                            shape = RoundedCornerShape(14.dp),
                            color = if (isNext) {
                                MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.7f)
                            } else {
                                Color.Transparent
                            },
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(horizontal = 12.dp, vertical = 12.dp),
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween
                            ) {
                                Row(verticalAlignment = Alignment.CenterVertically) {
                                    Icon(
                                        imageVector = if (entry.isSunrise) Icons.Default.WbSunny else Icons.Default.NotificationsActive,
                                        contentDescription = null,
                                        tint = if (isNext) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
                                        modifier = Modifier.size(20.dp)
                                    )
                                    Spacer(modifier = Modifier.width(12.dp))
                                    Column {
                                        Text(
                                            text = entry.name,
                                            style = MaterialTheme.typography.titleSmall.copy(
                                                fontWeight = if (isNext) FontWeight.Bold else FontWeight.SemiBold
                                            ),
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                        Text(
                                            text = entry.arabicName,
                                            style = MaterialTheme.typography.bodySmall.copy(fontFamily = AmiriFontFamily),
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                                Text(
                                    text = entry.formattedTime,
                                    style = MaterialTheme.typography.titleMedium.copy(
                                        fontWeight = if (isNext) FontWeight.Bold else FontWeight.Medium
                                    ),
                                    color = if (isNext) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurface
                                )
                            }
                        }
                        if (idx < prayerSchedule.prayers.lastIndex) {
                            HorizontalDivider(color = MaterialTheme.colorScheme.outline.copy(alpha = 0.2f))
                        }
                    }
                }
            }
        }

        // Quick Actions Row
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                OutlinedButton(
                    onClick = onOpenQibla,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.Explore, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Qibla Direction")
                }
                OutlinedButton(
                    onClick = onOpenNotificationSettings,
                    modifier = Modifier.weight(1f)
                ) {
                    Icon(Icons.Default.NotificationsActive, contentDescription = null, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Azan Alerts")
                }
            }
        }

        // Calculation Settings
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
                    Text(
                        text = "Calculation Method",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(PrayerCalcMethod.entries) { method ->
                            FilterChip(
                                selected = preferences.calcMethod == method,
                                onClick = { onSelectMethod(method) },
                                label = { Text(method.displayName) }
                            )
                        }
                    }

                    Text(
                        text = "Asr Juristic Method",
                        style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                    )
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(AsrMadhhab.entries) { madhhab ->
                            FilterChip(
                                selected = preferences.asrMadhhab == madhhab,
                                onClick = { onSelectMadhhab(madhhab) },
                                label = { Text(madhhab.displayName) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun QiblaTabContent(
    preferences: AppPreferences,
    viewModel: PrayersQiblaViewModel,
    onOpenCityPicker: () -> Unit
) {
    val compassState by viewModel.compassState.collectAsStateWithLifecycle()
    val qiblaBearing = remember(preferences.selectedCity) {
        PrayerTimeCalculator.calculateQiblaBearing(
            preferences.selectedCity.latitude,
            preferences.selectedCity.longitude
        )
    }

    LaunchedEffect(qiblaBearing) {
        viewModel.updateActiveQiblaBearing(qiblaBearing)
    }

    DisposableEffect(Unit) {
        viewModel.startCompass()
        onDispose { viewModel.stopCompass() }
    }

    val animatedAzimuth by animateFloatAsState(
        targetValue = compassState.azimuthDegrees,
        animationSpec = tween(durationMillis = 120),
        label = "azimuth"
    )

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(22.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(14.dp)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.img_kaaba_emblem),
                            contentDescription = "Holy Kaaba Emblem",
                            modifier = Modifier
                                .size(48.dp)
                                .clip(CircleShape)
                                .border(1.5.dp, GoldBurnished, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Column {
                            Text(
                                text = "Qibla Bearing from ${preferences.selectedCity.name}",
                                style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold)
                            )
                            Text(
                                text = "${String.format(Locale.US, "%.1f", qiblaBearing)}° ${PrayerTimeCalculator.getCompassCardinalLabel(qiblaBearing)} toward Makkah",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    // Alignment status pill
                    Surface(
                        shape = RoundedCornerShape(50),
                        color = if (compassState.isAlignedWithQibla) {
                            MaterialTheme.colorScheme.primary
                        } else {
                            MaterialTheme.colorScheme.secondaryContainer
                        }
                    ) {
                        Text(
                            text = when {
                                !compassState.hasCompassSensor -> "Compass sensor unavailable — Face ${String.format(Locale.US, "%.0f", qiblaBearing)}° ${PrayerTimeCalculator.getCompassCardinalLabel(qiblaBearing)}"
                                compassState.isAlignedWithQibla -> "Aligned with Qibla (Holy Kaaba)"
                                else -> "Rotate phone until gold Kaaba pointer aligns upright"
                            },
                            style = MaterialTheme.typography.labelLarge,
                            color = if (compassState.isAlignedWithQibla) {
                                MaterialTheme.colorScheme.onPrimary
                            } else {
                                MaterialTheme.colorScheme.onSecondaryContainer
                            },
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
                        )
                    }

                    // Interactive Qibla Compass Canvas
                    Box(
                        modifier = Modifier
                            .size(270.dp)
                            .padding(8.dp)
                            .testTag("qibla_compass_dial"),
                        contentAlignment = Alignment.Center
                    ) {
                        val primaryColor = MaterialTheme.colorScheme.primary
                        val goldColor = GoldBurnished
                        val surfaceColor = MaterialTheme.colorScheme.surfaceVariant
                        val onSurface = MaterialTheme.colorScheme.onSurface

                        Canvas(modifier = Modifier.fillMaxSize()) {
                            val cx = size.width / 2f
                            val cy = size.height / 2f
                            val radius = size.minDimension / 2f - 8.dp.toPx()

                            // Outer golden bezel
                            drawCircle(
                                color = if (compassState.isAlignedWithQibla) primaryColor else goldColor,
                                radius = radius,
                                center = Offset(cx, cy),
                                style = Stroke(width = 4.dp.toPx())
                            )
                            drawCircle(
                                color = surfaceColor.copy(alpha = 0.55f),
                                radius = radius - 6.dp.toPx(),
                                center = Offset(cx, cy)
                            )

                            // Rotate dial opposite to phone azimuth so North points to true North
                            rotate(degrees = -animatedAzimuth, pivot = Offset(cx, cy)) {
                                // Degree ticks
                                for (deg in 0 until 360 step 15) {
                                    val rad = Math.toRadians(deg.toDouble() - 90.0)
                                    val innerR = if (deg % 45 == 0) radius - 22.dp.toPx() else radius - 14.dp.toPx()
                                    val outerR = radius - 8.dp.toPx()
                                    drawLine(
                                        color = if (deg == 0) Color(0xFFC62828) else onSurface.copy(alpha = 0.45f),
                                        start = Offset(cx + (innerR * cos(rad)).toFloat(), cy + (innerR * sin(rad)).toFloat()),
                                        end = Offset(cx + (outerR * cos(rad)).toFloat(), cy + (outerR * sin(rad)).toFloat()),
                                        strokeWidth = if (deg % 90 == 0) 2.5.dp.toPx() else 1.2.dp.toPx()
                                    )
                                }

                                // Qibla Needle pointing at qiblaBearing relative to North
                                rotate(degrees = qiblaBearing.toFloat(), pivot = Offset(cx, cy)) {
                                    val needlePath = Path().apply {
                                        moveTo(cx, cy - radius + 24.dp.toPx())
                                        lineTo(cx - 12.dp.toPx(), cy)
                                        lineTo(cx, cy + 18.dp.toPx())
                                        lineTo(cx + 12.dp.toPx(), cy)
                                        close()
                                    }
                                    drawPath(path = needlePath, color = primaryColor)

                                    // Gold Kaaba tip marker
                                    drawCircle(
                                        color = goldColor,
                                        radius = 10.dp.toPx(),
                                        center = Offset(cx, cy - radius + 24.dp.toPx())
                                    )
                                }
                            }

                            // Fixed top device heading marker
                            drawLine(
                                color = goldColor,
                                start = Offset(cx, 0f),
                                end = Offset(cx, 16.dp.toPx()),
                                strokeWidth = 3.dp.toPx()
                            )
                        }

                        // Center Kaaba Medallion
                        Image(
                            painter = painterResource(id = R.drawable.img_kaaba_emblem),
                            contentDescription = "Kaaba center",
                            modifier = Modifier
                                .size(54.dp)
                                .clip(CircleShape)
                                .border(2.dp, GoldBurnished, CircleShape),
                            contentScale = ContentScale.Crop
                        )
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceEvenly
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Heading", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                "${animatedAzimuth.toInt()}° ${PrayerTimeCalculator.getCompassCardinalLabel(animatedAzimuth.toDouble())}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold)
                            )
                        }
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text("Qibla Angle", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                            Text(
                                "${qiblaBearing.toInt()}° ${PrayerTimeCalculator.getCompassCardinalLabel(qiblaBearing)}",
                                style = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Text(
                        text = "Sensor Status: ${compassState.sensorAccuracyLabel}",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    OutlinedButton(onClick = onOpenCityPicker) {
                        Icon(Icons.Default.LocationOn, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Change Location (${preferences.selectedCity.name})")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CityPickerBottomSheet(
    viewModel: PrayersQiblaViewModel,
    selectedCity: CityLocation,
    onDismiss: () -> Unit
) {
    val search by viewModel.citySearchQuery.collectAsStateWithLifecycle()
    val filtered = remember(search) {
        val q = search.trim().lowercase()
        if (q.isEmpty()) viewModel.allCities
        else viewModel.allCities.filter {
            it.name.lowercase().contains(q) || it.country.lowercase().contains(q)
        }
    }

    ModalBottomSheet(onDismissRequest = onDismiss) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                text = "Select City for Prayer Times & Qibla",
                style = MaterialTheme.typography.titleLarge
            )
            OutlinedTextField(
                value = search,
                onValueChange = { viewModel.updateCitySearch(it) },
                placeholder = { Text("Search city or country...") },
                leadingIcon = { Icon(Icons.Default.Search, contentDescription = null) },
                singleLine = true,
                shape = RoundedCornerShape(14.dp),
                modifier = Modifier.fillMaxWidth()
            )
            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(340.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(filtered, key = { "${it.name}_${it.country}" }) { city ->
                    val isSelected = city.name == selectedCity.name && city.country == selectedCity.country
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.6f)
                                else Color.Transparent
                            )
                            .clickable {
                                viewModel.selectCity(city)
                                onDismiss()
                            }
                            .padding(horizontal = 14.dp, vertical = 12.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(city.name, style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                            Text(city.country, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                        }
                        if (isSelected) {
                            Icon(Icons.Default.Check, contentDescription = "Selected", tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
        }
    }
}
