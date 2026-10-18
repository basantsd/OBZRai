package com.obzrai.observer.feature.session

import com.obzrai.observer.core.model.SessionState
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.overlay.OverlayController
import org.junit.Assert.assertEquals
import org.junit.Test

class SessionStateMachineTest {
    @Test
    fun startAndStop_movesThroughActiveThenStopped() {
        val controller = ObservationSessionController(
            mediaProjectionGateway = object : MediaProjectionGateway {
                override fun hasValidConsent(): Boolean = true
                override fun startSessionStub() = Unit
                override fun stopSessionStub(reason: String) = Unit
            },
            overlayController = object : OverlayController {
                override fun showStub() = Unit
                override fun hideStub() = Unit
            },
        )

        assertEquals(SessionState.STOPPED, controller.state.value)
        controller.requestStart()
        assertEquals(SessionState.ACTIVE, controller.state.value)
        controller.stop("test")
        assertEquals(SessionState.STOPPED, controller.state.value)
    }

    @Test
    fun startWithoutConsent_requiresPermission() {
        val controller = ObservationSessionController(
            mediaProjectionGateway = object : MediaProjectionGateway {
                override fun hasValidConsent(): Boolean = false
                override fun startSessionStub() = Unit
                override fun stopSessionStub(reason: String) = Unit
            },
            overlayController = object : OverlayController {
                override fun showStub() = Unit
                override fun hideStub() = Unit
            },
        )

        controller.requestStart()
        assertEquals(SessionState.NEEDS_PERMISSION, controller.state.value)
    }
}
