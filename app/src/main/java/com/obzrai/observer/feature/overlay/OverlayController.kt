package com.obzrai.observer.feature.overlay

import android.content.Intent
import kotlinx.coroutines.flow.StateFlow

/**
 * WindowManager TYPE_APPLICATION_OVERLAY ring (spike S2).
 *
 * Geometry: ~28 dp edge-clipped ring + 10–12 dp status glyph.
 * Host apps remain tappable (FLAG_NOT_FOCUSABLE | FLAG_NOT_TOUCH_MODAL).
 * Visual match to `assets/approved_overlay_reference.png` is PENDING until asset arrives.
 */
interface OverlayController {
    val isShowing: StateFlow<Boolean>

    fun canDrawOverlays(): Boolean

    /** Settings intent for Display over other apps (and restricted-settings path on 15+). */
    fun overlaySettingsIntent(): Intent

    fun isVisible(): Boolean

    fun show()

    fun hide()
}
