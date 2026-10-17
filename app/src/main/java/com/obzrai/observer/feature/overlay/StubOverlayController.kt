package com.obzrai.observer.feature.overlay

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StubOverlayController @Inject constructor() : OverlayController {
    override fun showStub() {
        Log.i(TAG, "Overlay stub show (Compose ring placeholder; approved PNG missing)")
    }

    override fun hideStub() {
        Log.i(TAG, "Overlay stub hide")
    }

    companion object {
        private const val TAG = "OverlayStub"
    }
}
