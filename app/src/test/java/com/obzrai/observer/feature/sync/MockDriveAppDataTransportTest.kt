package com.obzrai.observer.feature.sync

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertArrayEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class MockDriveAppDataTransportTest {
    @Test
    fun roundTrip_preservesCiphertext() =
        runBlocking {
            val transport = MockDriveAppDataTransport()
            val payload = "ciphertext-bytes".toByteArray()
            assertTrue(transport.pushCiphertext(payload).isSuccess)
            val pulled = transport.pullCiphertext().getOrNull()
            assertArrayEquals(payload, pulled)
        }

    @Test
    fun webDavStub_failsFastFollow() =
        runBlocking {
            val stub = WebDavTransportStub()
            assertTrue(stub.pushCiphertext(byteArrayOf(1)).isFailure)
        }
}
