package com.obzrai.observer.feature.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.obzrai.observer.core.prefs.ObserverPreferences
import com.obzrai.observer.core.storage.LibraryRepository
import com.obzrai.observer.feature.export.EncryptedArchive
import com.obzrai.observer.feature.sync.ThinSyncEngine
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val preferences: ObserverPreferences,
    private val encryptedArchive: EncryptedArchive,
    private val libraryRepository: LibraryRepository,
    private val thinSyncEngine: ThinSyncEngine,
) : ViewModel() {
    val micOptIn: StateFlow<Boolean> =
        preferences.micOptIn.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false,
        )

    val syncOptIn: StateFlow<Boolean> =
        preferences.syncOptIn.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5_000),
            false,
        )

    private val _statusMessage = MutableStateFlow("")
    val statusMessage: StateFlow<String> = _statusMessage.asStateFlow()

    fun setMicOptIn(value: Boolean) {
        viewModelScope.launch { preferences.setMicOptIn(value) }
    }

    fun setSyncOptIn(value: Boolean) {
        viewModelScope.launch { thinSyncEngine.setOptIn(value) }
    }

    fun syncNow() {
        viewModelScope.launch {
            val result = thinSyncEngine.syncNow()
            _statusMessage.value = result.message
        }
    }

    fun exportLibrary(passphrase: String) {
        viewModelScope.launch {
            val result = encryptedArchive.export(passphrase.toCharArray())
            if (result.ok && result.path != null) {
                preferences.setLastExportPath(result.path)
            }
            _statusMessage.value = result.message + (result.path?.let { " → $it" } ?: "")
        }
    }

    fun importLibrary(passphrase: String) {
        viewModelScope.launch {
            val path = preferences.lastExportPath.first()
            if (path.isNullOrBlank() || !File(path).exists()) {
                _statusMessage.value = "Import needs a prior export on this device"
                return@launch
            }
            val result = encryptedArchive.importArchive(passphrase.toCharArray(), File(path))
            _statusMessage.value = result.message
        }
    }

    fun deleteAll() {
        viewModelScope.launch {
            libraryRepository.deleteAllItems()
            _statusMessage.value = "All local items deleted"
        }
    }
}
