package com.obzrai.observer.feature.overlay

import android.app.Service
import android.content.Intent
import android.os.IBinder

/**
 * Shell for the floating overlay window service.
 * Real WindowManager attachment + edge-snap lands after overlay spike (S2).
 */
class OverlayService : Service() {
    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        // No-op stub: does not attach a real overlay window yet.
        return START_NOT_STICKY
    }
}
