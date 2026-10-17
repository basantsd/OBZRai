package com.obzrai.observer.feature.onboarding

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.obzrai.observer.R
import com.obzrai.observer.core.ui.ObserverRing

/** FR-072 — ≤ 3 screens; skippable; no account wall. */
@Composable
fun OnboardingScreen(
    onFinished: () -> Unit,
) {
    var page by remember { mutableIntStateOf(0) }
    val pages =
        listOf(
            "OBZRAi watches only after you start a session (or share via Sharesheet).",
            "Everything stays on this device by default. You can stop anytime and delete all data.",
            "Save what matters — topics help auto-save; manual Save always works.",
        )

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        ObserverRing(size = 48.dp, showGlyph = true)
        Text(stringResource(R.string.app_name), style = MaterialTheme.typography.headlineMedium)
        Text(pages[page], style = MaterialTheme.typography.bodyLarge)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            OutlinedButton(onClick = onFinished) {
                Text(stringResource(R.string.onboarding_skip))
            }
            Button(
                onClick = {
                    if (page >= pages.lastIndex) onFinished() else page++
                },
            ) {
                Text(
                    if (page >= pages.lastIndex) {
                        stringResource(R.string.onboarding_done)
                    } else {
                        stringResource(R.string.onboarding_next)
                    },
                )
            }
        }
    }
}
