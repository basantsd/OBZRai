package com.obzrai.observer.feature.today

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.obzrai.observer.R
import java.text.DateFormat
import java.util.Date

@Composable
fun TodayScreen(
    viewModel: TodayViewModel = hiltViewModel(),
) {
    val items by viewModel.items.collectAsStateWithLifecycle()

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(horizontal = 20.dp, vertical = 16.dp),
    ) {
        Text(stringResource(R.string.nav_today), style = MaterialTheme.typography.titleLarge)
        Text(
            text = stringResource(R.string.continuity_local_only),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )
        Spacer(modifier = Modifier.height(12.dp))
        if (items.isEmpty()) {
            Text(
                text = stringResource(R.string.today_empty),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
            )
        } else {
            LazyColumn(verticalArrangement = Arrangement.spacedBy(12.dp)) {
                items(items, key = { it.id }) { item ->
                    Column(modifier = Modifier.fillMaxWidth()) {
                        Text(
                            text = item.title.ifBlank { "Untitled" },
                            style = MaterialTheme.typography.titleMedium,
                        )
                        Text(
                            text =
                                buildString {
                                    append(DateFormat.getTimeInstance(DateFormat.SHORT).format(Date(item.createdAtEpochMs)))
                                    append(" · ")
                                    append(item.sourceApp ?: item.deviceLabel)
                                    if (item.captureBlocked) append(" · blocked")
                                    if (item.voiceIdeaText.isNotBlank()) append(" · voice")
                                    if (item.tagsCsv.isNotBlank()) append(" · #${item.tagsCsv}")
                                },
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.65f),
                        )
                        if (item.userNotes.isNotBlank() || item.voiceIdeaText.isNotBlank()) {
                            Text(
                                text = item.voiceIdeaText.ifBlank { item.userNotes },
                                style = MaterialTheme.typography.bodySmall,
                            )
                        }
                        AssistChip(
                            onClick = {},
                            label = { Text(item.continuityStatus) },
                        )
                    }
                }
            }
        }
    }
}
