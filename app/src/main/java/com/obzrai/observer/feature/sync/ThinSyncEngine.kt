package com.obzrai.observer.feature.sync

import com.obzrai.observer.core.prefs.ObserverPreferences
import com.obzrai.observer.core.storage.LibraryRepository
import kotlinx.coroutines.flow.first
import org.json.JSONArray
import org.json.JSONObject
import java.security.SecureRandom
import javax.crypto.Cipher
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Android thin sync client (FR-103 / FR-047† / FR-106).
 * Opt-in off by default. Uses [MockDriveAppDataTransport] until real OAuth (S24 PENDING).
 */
@Singleton
class ThinSyncEngine @Inject constructor(
    private val libraryRepository: LibraryRepository,
    private val preferences: ObserverPreferences,
) {
    private val transport: ThinSyncTransport = MockDriveAppDataTransport()

    data class Status(
        val optIn: Boolean,
        val lastSyncAt: Long?,
        val continuity: String,
        val transportId: String,
        val notes: String,
    )

    data class SyncResult(
        val ok: Boolean,
        val itemCount: Int,
        val message: String,
    )

    suspend fun status(): Status {
        val optIn = preferences.syncOptIn.first()
        val last = preferences.lastThinSyncAt.first()
        return Status(
            optIn = optIn,
            lastSyncAt = last,
            continuity = if (optIn && last != null) "synced_ciphertext" else "local_only",
            transportId = transport.id,
            notes =
                "S24 real drive.appdata OAuth + brand verification PENDING. " +
                    "WebDAV = Q32 fast-follow. Counsel/DPDP PENDING (Q36).",
        )
    }

    suspend fun setOptIn(value: Boolean) {
        preferences.setSyncOptIn(value)
    }

    suspend fun syncNow(): SyncResult {
        if (!preferences.syncOptIn.first()) {
            return SyncResult(false, 0, "Sync off by default — enable opt-in first")
        }
        val items = libraryRepository.getAllItems()
        val arr = JSONArray()
        items.forEach { item ->
            arr.put(
                JSONObject().apply {
                    put("id", item.id)
                    put("createdAtEpochMs", item.createdAtEpochMs)
                    put("title", item.title)
                    put("tags", JSONArray(item.tagsCsv.split(",").filter { it.isNotBlank() }))
                    put("ocrOrPageText", item.ocrText)
                    put("transcriptText", "")
                    put("voiceIdeaText", item.voiceIdeaText)
                    put("captureBlocked", item.captureBlocked)
                    put("continuityStatus", "synced_ciphertext")
                    put("thumbnailBase64", JSONObject.NULL)
                },
            )
        }
        val payload =
            JSONObject()
                .put("schema", "obzrai-thin-v1")
                .put("exportedAt", System.currentTimeMillis())
                .put("items", arr)
                .toString()
                .toByteArray(Charsets.UTF_8)
        val packed = encrypt(payload)
        transport.pushCiphertext(packed).getOrElse {
            return SyncResult(false, 0, "Push failed: ${it.message}")
        }
        val pulled = transport.pullCiphertext().getOrNull()
        if (pulled == null || !pulled.contentEquals(packed)) {
            return SyncResult(false, 0, "Round-trip verify failed")
        }
        preferences.setLastThinSyncAt(System.currentTimeMillis())
        return SyncResult(true, items.size, "Thin ciphertext round-trip OK (${transport.id})")
    }

    private fun encrypt(plain: ByteArray): ByteArray {
        val keyBytes = ByteArray(32).also { SecureRandom().nextBytes(it) }
        val iv = ByteArray(12).also { SecureRandom().nextBytes(it) }
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, SecretKeySpec(keyBytes, "AES"), GCMParameterSpec(128, iv))
        val ct = cipher.doFinal(plain)
        val out = ByteArray(1 + 32 + 12 + ct.size)
        out[0] = 1
        System.arraycopy(keyBytes, 0, out, 1, 32)
        System.arraycopy(iv, 0, out, 33, 12)
        System.arraycopy(ct, 0, out, 45, ct.size)
        return out
    }
}
