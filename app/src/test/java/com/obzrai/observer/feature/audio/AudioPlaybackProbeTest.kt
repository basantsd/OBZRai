package com.obzrai.observer.feature.audio

import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [34])
class AudioPlaybackProbeTest {
    @Test
    fun expectedThirdPartyHonestyLog_mentionsInstagramAndYoutube() {
        val probe = AudioPlaybackProbe(RuntimeEnvironment.getApplication())
        val lines = probe.expectedThirdPartyHonestyLog()
        assertTrue(lines.any { it.contains("Instagram", ignoreCase = true) })
        assertTrue(lines.any { it.contains("YouTube", ignoreCase = true) })
        assertTrue(lines.any { it.contains("not a V1 no-go", ignoreCase = true) })
    }
}
