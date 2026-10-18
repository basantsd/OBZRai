package com.obzrai.observer.core.prefs

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.floatPreferencesKey
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "obzrai_prefs")

@Singleton
class ObserverPreferences @Inject constructor(
    @ApplicationContext private val context: Context,
) {
    val onboardingComplete: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.ONBOARDING] ?: false }

    val overlayX: Flow<Int?> =
        context.dataStore.data.map { it[Keys.OVERLAY_X] }

    val overlayY: Flow<Int?> =
        context.dataStore.data.map { it[Keys.OVERLAY_Y] }

    val matchSensitivity: Flow<String> =
        context.dataStore.data.map { it[Keys.MATCH_SENSITIVITY] ?: "BALANCED" }

    val micOptIn: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.MIC_OPT_IN] ?: false }

    val lastExportPath: Flow<String?> =
        context.dataStore.data.map { it[Keys.LAST_EXPORT] }

    val syncOptIn: Flow<Boolean> =
        context.dataStore.data.map { it[Keys.SYNC_OPT_IN] ?: false }

    val lastThinSyncAt: Flow<Long?> =
        context.dataStore.data.map { it[Keys.LAST_THIN_SYNC] }

    suspend fun setOnboardingComplete(done: Boolean) {
        context.dataStore.edit { it[Keys.ONBOARDING] = done }
    }

    suspend fun setLastExportPath(path: String) {
        context.dataStore.edit { it[Keys.LAST_EXPORT] = path }
    }

    suspend fun setSyncOptIn(value: Boolean) {
        context.dataStore.edit { it[Keys.SYNC_OPT_IN] = value }
    }

    suspend fun setLastThinSyncAt(epochMs: Long) {
        context.dataStore.edit { it[Keys.LAST_THIN_SYNC] = epochMs }
    }

    suspend fun setOverlayPosition(x: Int, y: Int) {
        context.dataStore.edit {
            it[Keys.OVERLAY_X] = x
            it[Keys.OVERLAY_Y] = y
        }
    }

    suspend fun setMatchSensitivity(value: String) {
        context.dataStore.edit { it[Keys.MATCH_SENSITIVITY] = value }
    }

    suspend fun setMicOptIn(value: Boolean) {
        context.dataStore.edit { it[Keys.MIC_OPT_IN] = value }
    }

    private object Keys {
        val ONBOARDING = booleanPreferencesKey("onboarding_complete")
        val OVERLAY_X = intPreferencesKey("overlay_x")
        val OVERLAY_Y = intPreferencesKey("overlay_y")
        val MATCH_SENSITIVITY = stringPreferencesKey("match_sensitivity")
        val MIC_OPT_IN = booleanPreferencesKey("mic_opt_in")
        val LAST_EXPORT = stringPreferencesKey("last_export_path")
        val SYNC_OPT_IN = booleanPreferencesKey("sync_opt_in")
        val LAST_THIN_SYNC = longPreferencesKey("last_thin_sync_at")
        // reserved for future density scale
        @Suppress("unused")
        val UNUSED = floatPreferencesKey("unused")
    }
}
