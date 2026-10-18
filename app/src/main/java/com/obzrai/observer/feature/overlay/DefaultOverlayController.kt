package com.obzrai.observer.feature.overlay

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import android.util.Log
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DefaultOverlayController @Inject constructor(
    @ApplicationContext private val context: Context,
) : OverlayController {
    private val _isShowing = MutableStateFlow(false)
    override val isShowing: StateFlow<Boolean> = _isShowing.asStateFlow()

    override fun canDrawOverlays(): Boolean = Settings.canDrawOverlays(context)

    override fun overlaySettingsIntent(): Intent {
        // Android 15+ sideload: user may need restricted-settings → Allow restricted settings
        // before Display over other apps becomes grantable. Documented in Phase 0 results.
        return Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:${context.packageName}"),
        ).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun isVisible(): Boolean = _isShowing.value

    override fun show() {
        if (!canDrawOverlays()) {
            Log.w(TAG, "show() blocked — SYSTEM_ALERT_WINDOW not granted")
            return
        }
        val intent =
            Intent(context, OverlayService::class.java).apply {
                action = OverlayService.ACTION_SHOW
            }
        context.startService(intent)
        _isShowing.value = true
        Log.i(
            TAG,
            "Overlay show requested (Compose placeholder; approved PNG PENDING). " +
                "sdk=${Build.VERSION.SDK_INT} density=${context.resources.displayMetrics.density}",
        )
    }

    override fun hide() {
        val intent =
            Intent(context, OverlayService::class.java).apply {
                action = OverlayService.ACTION_HIDE
            }
        context.startService(intent)
        _isShowing.value = false
        Log.i(TAG, "Overlay hide requested")
    }

    fun markHiddenFromService() {
        _isShowing.value = false
    }

    companion object {
        private const val TAG = "OverlayS2"
    }
}
