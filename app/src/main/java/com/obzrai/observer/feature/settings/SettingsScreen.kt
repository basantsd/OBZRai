package com.obzrai.observer.feature.settings

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.obzrai.observer.R

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
) {
    val micOptIn by viewModel.micOptIn.collectAsStateWithLifecycle()
    val syncOptIn by viewModel.syncOptIn.collectAsStateWithLifecycle()
    val status by viewModel.statusMessage.collectAsStateWithLifecycle()
    var passphrase by remember { mutableStateOf("") }

    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        Text(stringResource(R.string.settings), style = MaterialTheme.typography.titleLarge)
        Text(stringResource(R.string.trust_center), style = MaterialTheme.typography.titleMedium)
        Text(
            text =
                "Local-first. No account. Sync off. Capture only in an authorized session " +
                    "or via Sharesheet. No FLAG_SECURE bypass. Continuity: encrypted export (FR-097). " +
                    "Counsel/DPDP review PENDING before any account or relay (Q36).",
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f),
        )
        Text(
            text = stringResource(R.string.continuity_local_only),
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary,
        )

        RowSwitch(
            label = "Microphone opt-in for PTT idea notes (FR-040)",
            checked = micOptIn,
            onCheckedChange = viewModel::setMicOptIn,
        )
        RowSwitch(
            label = "Opt-in thin sync (off by default · mock Drive appData)",
            checked = syncOptIn,
            onCheckedChange = viewModel::setSyncOptIn,
        )
        OutlinedButton(
            onClick = viewModel::syncNow,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text("Thin sync now (ciphertext mock)")
        }

        OutlinedTextField(
            value = passphrase,
            onValueChange = { passphrase = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Export / import passphrase (≥8)") },
            singleLine = true,
        )
        Button(
            onClick = { viewModel.exportLibrary(passphrase) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.export_library))
        }
        OutlinedButton(
            onClick = { viewModel.importLibrary(passphrase) },
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.import_library))
        }
        OutlinedButton(
            onClick = viewModel::deleteAll,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Text(stringResource(R.string.delete_all))
        }
        if (status.isNotBlank()) {
            Text(text = status, style = MaterialTheme.typography.bodySmall)
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text =
                "Sideload overlay (Android 15+): App info → Allow restricted settings → " +
                    "Display over other apps → OBZRAi (FR-057).",
            style = MaterialTheme.typography.bodySmall,
        )
    }
}

@Composable
private fun RowSwitch(
    label: String,
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
) {
    Column {
        Text(label, style = MaterialTheme.typography.bodyMedium)
        Switch(checked = checked, onCheckedChange = onCheckedChange)
    }
}
