package com.obzrai.observer.core.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.obzrai.observer.core.ui.theme.AIObserverTheme
import com.obzrai.observer.core.ui.theme.ObserverRingBlue
import com.obzrai.observer.core.ui.theme.ObserverRingInterior
import com.obzrai.observer.core.ui.theme.ObserverSavedGreen

/**
 * Compose placeholder for the approved blue hollow-ring overlay mark.
 *
 * Asset gap: PRD references `assets/approved_overlay_reference.png`, which was
 * not included in the uploaded package. Replace this with the owner-approved PNG
 * once available (PRD §0 / §5.1 / §11.1). Reference size ~28 dp; glyph ~10–12 dp.
 *
 * `matches reference` remains **PENDING** until that PNG lands.
 */
@Composable
fun ObserverRing(
    modifier: Modifier = Modifier,
    size: Dp = 28.dp,
    strokeWidth: Dp = 2.5.dp,
    showGlyph: Boolean = false,
    glyphSize: Dp = 11.dp,
) {
    Canvas(modifier = modifier.size(size)) {
        val stroke = strokeWidth.toPx()
        val radius = (this.size.minDimension - stroke) / 2f
        val center = Offset(this.size.width / 2f, this.size.height / 2f)
        drawCircle(
            color = ObserverRingInterior,
            radius = radius - stroke / 2f,
            center = center,
        )
        drawCircle(
            color = ObserverRingBlue,
            radius = radius,
            center = center,
            style = Stroke(width = stroke, cap = StrokeCap.Round),
        )
        if (showGlyph) {
            // Idle status glyph: soft-green check stand-in overlapping lower edge (PRD §5.1).
            val g = glyphSize.toPx() / 2f
            val glyphCenter =
                Offset(
                    x = center.x,
                    y = center.y + radius * 0.72f,
                )
            drawCircle(
                color = ObserverSavedGreen,
                radius = g,
                center = glyphCenter,
            )
        }
    }
}

@Preview
@Composable
private fun ObserverRingPreview() {
    AIObserverTheme {
        ObserverRing(size = 56.dp, showGlyph = true, glyphSize = 14.dp)
    }
}
