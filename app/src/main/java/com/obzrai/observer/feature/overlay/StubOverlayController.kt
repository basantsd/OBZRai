package com.obzrai.observer.feature.overlay

import android.content.Intent
import android.util.Log
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

/** Unit-test double. Production binds [DefaultOverlayController]. */
@Singleton
class StubOverlayController @Inject constructor() : OverlayController {
    private val _isShowing = MutableStateFlow(false)
    override val isShowing: StateFlow<Boolean> = _isShowing.asStateFlow()

    private var canDraw: Boolean = true

    fun setCanDrawForTest(value: Boolean) {
        canDraw = value
    }

    override fun canDrawOverlays(): Boolean = canDraw

    override fun overlaySettingsIntent(): Intent = Intent()

    override fun isVisible(): Boolean = _isShowing.value

    override fun show() {
        _isShowing.value = true
        Log.i(TAG, "Overlay stub show")
    }

    override fun hide() {
        _isShowing.value = false
        Log.i(TAG, "Overlay stub hide")
    }

    companion object {
        private const val TAG = "OverlayStub"
    }
}
