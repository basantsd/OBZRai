package com.obzrai.observer.feature.capture

/**
 * Placeholder for MediaProjectionManager / ImageReader pipeline.
 * No AccessibilityService. Fresh OS consent required per session (Android 14+).
 */
interface MediaProjectionGateway {
    fun hasValidConsent(): Boolean

    fun startSessionStub()

    fun stopSessionStub(reason: String)
}
