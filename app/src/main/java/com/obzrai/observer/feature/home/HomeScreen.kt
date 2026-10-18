package com.obzrai.observer.feature.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.obzrai.observer.R
import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.core.ui.ObserverRing

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 20.dp, vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            ObserverRing(size = 40.dp, showGlyph = true)
            Column {
                Text(
                    text = stringResource(R.string.app_name),
                    style = MaterialTheme.typography.titleLarge,
                )
                Text(
                    text = stringResource(R.string.product_promise),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f),
                )
            }
        }

        Text(
            text = "Observe Mode",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = sessionLabel(state.sessionState),
            style = MaterialTheme.typography.headlineMedium,
            color = MaterialTheme.colorScheme.primary,
        )
        Text(
            text = "Phase 0 spikes: MediaProjection (S1), overlay (S2), clean frame (S3), audio (S4). " +
                "No AccessibilityService. Fresh OS consent each session.",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        )

        if (state.sessionState == SessionState.ACTIVE || state.sessionState == SessionState.STARTING) {
            OutlinedButton(
                onClick = viewModel::onToggleObserve,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.observe_stop))
            }
        } else {
            Button(
                onClick = viewModel::onToggleObserve,
                modifier = Modifier.fillMaxWidth(),
            ) {
                Text(stringResource(R.string.observe_start))
            }
        }

        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            Counter("Saved", state.savedCount)
            Counter("Today", state.todayCount)
            Counter("Topics", state.topicCount)
        }

        Text(
            text = "Phase 0 spike panel",
            style = MaterialTheme.typography.titleMedium,
        )
        Text(
            text = "S1 frames: ${state.frameCount}" +
                (state.lastFrameInfo?.let {
                    " · last ${it.width}×${it.height}" +
                        (it.path?.let { p -> " · $p" } ?: "")
                } ?: ""),
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "S1 stop reason: ${state.lastStopReason ?: "—"}",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "S2 overlay: ${if (state.overlayShowing) "showing" else "hidden"} · ${state.densityReport}",
            style = MaterialTheme.typography.bodySmall,
        )
        Text(
            text = "S2 visual match vs approved PNG: PENDING (asset missing)",
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.tertiary,
        )
        OutlinedButton(
            onClick = viewModel::runS3CleanFrame,
            modifier = Modifier.fillMaxWidth(),
            enabled = state.sessionState == SessionState.ACTIVE,
        ) {
            Text("Run S3 clean-frame capture")
        }
        state.s3Result?.let {
            Text(text = "S3: $it", style = MaterialTheme.typography.bodySmall)
        }
        OutlinedButton(
            onClick = viewModel::runS4AudioProbes,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Run S4 audio honesty probe")
        }
        state.s4Results.forEach { line ->
            Text(text = line, style = MaterialTheme.typography.bodySmall)
        }

        Text(
            text = "Recent saves",
            style = MaterialTheme.typography.titleMedium,
        )
        if (state.recentItems.isEmpty()) {
            Text(
                text = "Nothing saved yet — shell library is empty.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
            )
        } else {
            state.recentItems.forEach { item ->
                Text(
                    text = item.title.ifBlank { item.id },
                    style = MaterialTheme.typography.bodyLarge,
                )
            }
        }
    }
}

@Composable
private fun Counter(label: String, value: Int) {
    Column(horizontalAlignment = Alignment.Start) {
        Text(text = value.toString(), style = MaterialTheme.typography.titleLarge)
        Text(
            text = label,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
        )
    }
}

@Composable
private fun sessionLabel(state: SessionState): String =
    when (state) {
        SessionState.ACTIVE -> stringResource(R.string.observe_active)
        SessionState.PAUSED -> stringResource(R.string.observe_paused)
        SessionState.NEEDS_PERMISSION -> stringResource(R.string.observe_needs_permission)
        SessionState.STARTING -> "Starting…"
        SessionState.STOPPING -> "Stopping…"
        SessionState.STOPPED -> stringResource(R.string.observe_stopped)
    }
