package com.example.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Vibration
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.catalog.QuizCatalog
import com.example.data.model.AppPreferences
import com.example.ui.theme.AmiriFontFamily
import com.example.ui.theme.GoldBurnished
import com.example.ui.theme.IslamicGeometricBackdrop
import com.example.ui.theme.PlayfairFontFamily
import com.example.ui.viewmodel.MainViewModel

@Composable
fun TasbeehScreen(
    preferences: AppPreferences,
    mainViewModel: MainViewModel,
    modifier: Modifier = Modifier
) {
    val haptic = LocalHapticFeedback.current
    val activeDhikr = remember(preferences.tasbeehDhikrId) {
        QuizCatalog.tasbeehDhikrs.find { it.id == preferences.tasbeehDhikrId }
            ?: QuizCatalog.tasbeehDhikrs.first()
    }

    val progress = if (preferences.tasbeehTarget > 0) {
        (preferences.tasbeehCount.toFloat() / preferences.tasbeehTarget.toFloat()).coerceIn(0f, 1f)
    } else 0f
    val animatedProgress by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(durationMillis = 180),
        label = "tasbeeh_progress"
    )

    Box(modifier = modifier.fillMaxSize()) {
        IslamicGeometricBackdrop()

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .widthIn(max = 720.dp)
                .align(Alignment.TopCenter),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Digital Tasbeeh",
                        style = MaterialTheme.typography.headlineMedium.copy(fontFamily = PlayfairFontFamily),
                        color = MaterialTheme.colorScheme.primary
                    )
                    Text(
                        text = "Daily Dhikr & Remembrance Counter",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Dhikr Option Pills
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    items(QuizCatalog.tasbeehDhikrs, key = { it.id }) { dhikr ->
                        FilterChip(
                            selected = dhikr.id == activeDhikr.id,
                            onClick = { mainViewModel.selectTasbeehDhikr(dhikr.id, dhikr.defaultTarget) },
                            label = { Text(dhikr.title) },
                            modifier = Modifier.testTag("dhikr_chip_${dhikr.id}")
                        )
                    }
                }
            }

            // Active Dhikr Card
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(20.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        Text(
                            text = activeDhikr.arabic,
                            style = MaterialTheme.typography.headlineLarge.copy(
                                fontFamily = AmiriFontFamily,
                                fontSize = 34.sp
                            ),
                            color = MaterialTheme.colorScheme.primary,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = activeDhikr.urduMeaning,
                            style = MaterialTheme.typography.bodyLarge.copy(fontFamily = AmiriFontFamily, fontSize = 19.sp),
                            color = MaterialTheme.colorScheme.onSurface,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = activeDhikr.englishMeaning,
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            }

            // Circular Counter & Tap Area
            item {
                val trackColor = MaterialTheme.colorScheme.surfaceVariant
                val arcColor = MaterialTheme.colorScheme.primary

                Box(
                    modifier = Modifier
                        .size(230.dp)
                        .clip(CircleShape)
                        .clickable {
                            if (preferences.tasbeehHaptics) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                            mainViewModel.incrementTasbeeh()
                        }
                        .testTag("tasbeeh_tap_circle"),
                    contentAlignment = Alignment.Center
                ) {
                    Canvas(modifier = Modifier.fillMaxSize()) {
                        val strokeW = 14.dp.toPx()
                        drawArc(
                            color = trackColor,
                            startAngle = 0f,
                            sweepAngle = 360f,
                            useCenter = false,
                            style = Stroke(width = strokeW)
                        )
                        drawArc(
                            color = arcColor,
                            startAngle = -90f,
                            sweepAngle = 360f * animatedProgress,
                            useCenter = false,
                            style = Stroke(width = strokeW, cap = StrokeCap.Round)
                        )
                        // Subtle inner gold ring
                        drawCircle(
                            color = GoldBurnished.copy(alpha = 0.35f),
                            radius = (size.minDimension / 2f) - 22.dp.toPx(),
                            style = Stroke(width = 1.5.dp.toPx())
                        )
                    }

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "${preferences.tasbeehCount}",
                            style = MaterialTheme.typography.displayLarge.copy(
                                fontSize = 54.sp,
                                fontWeight = FontWeight.Bold
                            ),
                            color = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Target: ${preferences.tasbeehTarget}",
                            style = MaterialTheme.typography.labelLarge,
                            color = MaterialTheme.colorScheme.secondary
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = "TAP TO COUNT",
                            style = MaterialTheme.typography.labelSmall.copy(letterSpacing = 1.2.sp),
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            // Plus & Reset Buttons
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedButton(
                        onClick = { mainViewModel.resetTasbeeh() },
                        modifier = Modifier
                            .weight(1f)
                            .height(52.dp)
                            .testTag("tasbeeh_reset_button")
                    ) {
                        Icon(Icons.Default.Refresh, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Reset")
                    }

                    Button(
                        onClick = {
                            if (preferences.tasbeehHaptics) {
                                haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                            }
                            mainViewModel.incrementTasbeeh()
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primary),
                        modifier = Modifier
                            .weight(1.4f)
                            .height(52.dp)
                            .testTag("tasbeeh_plus_button")
                    ) {
                        Icon(Icons.Default.Add, contentDescription = null)
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("Count +1", fontWeight = FontWeight.Bold)
                    }
                }
            }

            // Daily & All-time Stats
            item {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.65f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Today's Dhikr", style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = "${preferences.tasbeehDailyCount}",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Surface(
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(16.dp),
                        color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.65f)
                    ) {
                        Column(
                            modifier = Modifier.padding(14.dp),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Total Lifetime", style = MaterialTheme.typography.labelMedium)
                            Text(
                                text = "${preferences.tasbeehTotalCount}",
                                style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                                color = MaterialTheme.colorScheme.onSecondaryContainer
                            )
                        }
                    }
                }
            }

            // Target & Haptic Controls
            item {
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(18.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
                ) {
                    Column(
                        modifier = Modifier.padding(14.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text("Target Count", style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.Bold))
                        Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                            listOf(33, 99, 100, 1000).forEach { target ->
                                FilterChip(
                                    selected = preferences.tasbeehTarget == target,
                                    onClick = { mainViewModel.setTasbeehTarget(target) },
                                    label = { Text("$target") }
                                )
                            }
                        }

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Icon(Icons.Default.Vibration, contentDescription = null, modifier = Modifier.size(20.dp))
                                Spacer(modifier = Modifier.width(8.dp))
                                Text("Haptic Vibration Feedback", style = MaterialTheme.typography.bodyMedium)
                            }
                            Switch(
                                checked = preferences.tasbeehHaptics,
                                onCheckedChange = { mainViewModel.setTasbeehHaptics(it) }
                            )
                        }
                    }
                }
            }
        }
    }
}
