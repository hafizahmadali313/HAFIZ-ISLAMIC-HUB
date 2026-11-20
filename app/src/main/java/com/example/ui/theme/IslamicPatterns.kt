package com.example.ui.theme

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.math.cos
import kotlin.math.sin

/**
 * Renders subtle geometric Islamic 8-pointed star (Rub el Hizb) lattice watermark across backgrounds.
 */
@Composable
fun IslamicGeometricBackdrop(
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.primary.copy(alpha = 0.045f)
) {
    Canvas(modifier = modifier.fillMaxSize()) {
        val spacing = 96.dp.toPx()
        val radius = 26.dp.toPx()
        val cols = (size.width / spacing).toInt() + 2
        val rows = (size.height / spacing).toInt() + 2

        for (r in 0..rows) {
            for (c in 0..cols) {
                val cx = c * spacing + if (r % 2 == 1) spacing / 2f else 0f
                val cy = r * spacing
                val half = radius * 0.7f
                // First rotated square
                drawRect(
                    color = tint,
                    topLeft = Offset(cx - half, cy - half),
                    size = androidx.compose.ui.geometry.Size(half * 2, half * 2),
                    style = Stroke(width = 1.2.dp.toPx())
                )
                // Second 45-degree rotated square -> 8-pointed Rub el Hizb star
                rotate(45f, pivot = Offset(cx, cy)) {
                    drawRect(
                        color = tint,
                        topLeft = Offset(cx - half, cy - half),
                        size = androidx.compose.ui.geometry.Size(half * 2, half * 2),
                        style = Stroke(width = 1.2.dp.toPx())
                    )
                }
                drawCircle(
                    color = tint,
                    radius = 4.dp.toPx(),
                    center = Offset(cx, cy),
                    style = Stroke(width = 1.dp.toPx())
                )
            }
        }
    }
}

/**
 * 8-pointed Islamic Rub el Hizb emblem displaying a Surah or Ayah number.
 */
@Composable
fun RubElHizbBadge(
    label: String,
    modifier: Modifier = Modifier,
    size: Dp = 40.dp,
    strokeColor: Color = MaterialTheme.colorScheme.secondary,
    fillColor: Color = MaterialTheme.colorScheme.secondaryContainer.copy(alpha = 0.45f),
    textColor: Color = MaterialTheme.colorScheme.primary
) {
    Box(
        modifier = modifier.size(size),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val side = this.size.minDimension * 0.64f
            val cx = this.size.width / 2f
            val cy = this.size.height / 2f
            val half = side / 2f

            drawRect(
                color = fillColor,
                topLeft = Offset(cx - half, cy - half),
                size = androidx.compose.ui.geometry.Size(side, side)
            )
            drawRect(
                color = strokeColor,
                topLeft = Offset(cx - half, cy - half),
                size = androidx.compose.ui.geometry.Size(side, side),
                style = Stroke(width = 1.5.dp.toPx())
            )
            rotate(45f, pivot = Offset(cx, cy)) {
                drawRect(
                    color = fillColor,
                    topLeft = Offset(cx - half, cy - half),
                    size = androidx.compose.ui.geometry.Size(side, side)
                )
                drawRect(
                    color = strokeColor,
                    topLeft = Offset(cx - half, cy - half),
                    size = androidx.compose.ui.geometry.Size(side, side),
                    style = Stroke(width = 1.5.dp.toPx())
                )
            }
        }
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall.copy(
                fontWeight = FontWeight.Bold,
                fontSize = if (label.length > 2) 10.sp else 12.sp
            ),
            color = textColor
        )
    }
}

/**
 * Delicate crescent & geometric ornament divider.
 */
@Composable
fun IslamicOrnamentDivider(
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.secondary.copy(alpha = 0.6f)
) {
    Canvas(modifier = modifier) {
        val midY = size.height / 2f
        val midX = size.width / 2f
        val diamondRadius = 6.dp.toPx()

        drawLine(
            color = color.copy(alpha = 0.35f),
            start = Offset(0f, midY),
            end = Offset(midX - 16.dp.toPx(), midY),
            strokeWidth = 1.dp.toPx()
        )
        drawLine(
            color = color.copy(alpha = 0.35f),
            start = Offset(midX + 16.dp.toPx(), midY),
            end = Offset(size.width, midY),
            strokeWidth = 1.dp.toPx()
        )
        val path = Path().apply {
            moveTo(midX, midY - diamondRadius)
            lineTo(midX + diamondRadius, midY)
            lineTo(midX, midY + diamondRadius)
            lineTo(midX - diamondRadius, midY)
            close()
        }
        drawPath(path = path, color = color, style = Stroke(width = 1.4.dp.toPx()))
        for (i in listOf(-1, 1)) {
            val dotX = midX + i * 11.dp.toPx()
            drawCircle(color = color, radius = 2.dp.toPx(), center = Offset(dotX, midY))
        }
        // Subtle inner 8-ray glint
        val glintR = 2.5.dp.toPx()
        for (angleDeg in 0 until 360 step 45) {
            val rad = Math.toRadians(angleDeg.toDouble())
            drawLine(
                color = color,
                start = Offset(midX, midY),
                end = Offset(
                    midX + (glintR * cos(rad)).toFloat(),
                    midY + (glintR * sin(rad)).toFloat()
                ),
                strokeWidth = 0.8.dp.toPx()
            )
        }
    }
}
