package com.obzrai.observer.feature.capture

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StubMediaProjectionGateway @Inject constructor() : MediaProjectionGateway {
    private var sessionActive: Boolean = false

    override fun hasValidConsent(): Boolean {
        // Scaffold: pretend consent exists so Home Start/Stop can exercise the state machine.
        // Real flow: MediaProjectionManager.createScreenCaptureIntent() + Activity result.
        return true
    }

    override fun startSessionStub() {
        sessionActive = true
        Log.i(TAG, "MediaProjection stub session started (no real capture)")
    }

    override fun stopSessionStub(reason: String) {
        sessionActive = false
        Log.i(TAG, "MediaProjection stub session stopped: $reason")
    }

    companion object {
        private const val TAG = "MediaProjectionStub"
    }
}
