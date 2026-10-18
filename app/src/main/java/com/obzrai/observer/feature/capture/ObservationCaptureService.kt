package com.obzrai.observer.feature.capture

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.ServiceInfo
import android.os.Build
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat
import com.obzrai.observer.R
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

/**
 * Foreground service for an authorized MediaProjection session (spike S1).
 *
 * Order (Android 14+): consent Intent obtained in Activity → start this FGS →
 * [startForeground] with mediaProjection type → [getMediaProjection].
 */
@AndroidEntryPoint
class ObservationCaptureService : Service() {
    @Inject
    lateinit var mediaProjectionGateway: MediaProjectionGateway

    override fun onBind(intent: Intent?): IBinder? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            ACTION_STOP -> {
                (mediaProjectionGateway as? DefaultMediaProjectionGateway)
                    ?.stopSession("service_stop_action")
                    ?: mediaProjectionGateway.stopSession("service_stop_action")
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
                return START_NOT_STICKY
            }
            ACTION_START, null -> {
                ensureChannel()
                val notification = buildNotification()
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    startForeground(
                        NOTIFICATION_ID,
                        notification,
                        ServiceInfo.FOREGROUND_SERVICE_TYPE_MEDIA_PROJECTION,
                    )
                } else {
                    startForeground(NOTIFICATION_ID, notification)
                }

                val resultCode =
                    intent?.getIntExtra(EXTRA_RESULT_CODE, ActivityResultCodes.CANCELED)
                        ?: ActivityResultCodes.CANCELED
                val data =
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                        intent?.getParcelableExtra(EXTRA_RESULT_DATA, Intent::class.java)
                    } else {
                        @Suppress("DEPRECATION")
                        intent?.getParcelableExtra(EXTRA_RESULT_DATA)
                    }
                val gateway = mediaProjectionGateway as? DefaultMediaProjectionGateway
                if (gateway != null && data != null && resultCode == ActivityResultCodes.OK) {
                    val ok = gateway.attachProjection(applicationContext, resultCode, data)
                    if (!ok) {
                        Log.e(TAG, "attachProjection failed — stopping FGS")
                        stopForeground(STOP_FOREGROUND_REMOVE)
                        stopSelf()
                    }
                } else {
                    Log.w(TAG, "FGS started without usable projection extras (stub or missing)")
                }
                return START_STICKY
            }
            else -> return START_NOT_STICKY
        }
    }

    override fun onDestroy() {
        mediaProjectionGateway.stopSession("service_destroy")
        stopForeground(STOP_FOREGROUND_REMOVE)
        super.onDestroy()
    }

    private fun ensureChannel() {
        val manager = getSystemService(NotificationManager::class.java)
        val channel =
            NotificationChannel(
                CHANNEL_ID,
                getString(R.string.app_name),
                NotificationManager.IMPORTANCE_LOW,
            )
        manager.createNotificationChannel(channel)
    }

    private fun buildNotification(): Notification =
        NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle(getString(R.string.capture_notification_title))
            .setContentText(getString(R.string.capture_notification_text))
            .setSmallIcon(R.drawable.ic_observer_ring)
            .setOngoing(true)
            .setCategory(NotificationCompat.CATEGORY_SERVICE)
            .build()

    companion object {
        private const val TAG = "CaptureService"
        const val CHANNEL_ID = "observation_capture"
        const val NOTIFICATION_ID = 1001
        const val ACTION_START = "com.obzrai.observer.capture.START"
        const val ACTION_STOP = "com.obzrai.observer.capture.STOP"
        const val EXTRA_RESULT_CODE = "result_code"
        const val EXTRA_RESULT_DATA = "result_data"

        fun stop(context: Context) {
            context.startService(
                Intent(context, ObservationCaptureService::class.java).apply {
                    action = ACTION_STOP
                },
            )
        }
    }
}

/** Avoid importing android.app.Activity in service for clarity. */
private object ActivityResultCodes {
    const val OK = -1
    const val CANCELED = 0
}
