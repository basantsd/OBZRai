package com.obzrai.observer.feature.sync

/**
 * FR-103 thin payload (Phase 2 / D7-B).
 * No full-res frames, voice audio blobs, or imagination originals.
 * Q33: thumbs ≤ 200 KB JPEG (assumption).
 */
data class ThinSyncItem(
    val id: String,
    val createdAtEpochMs: Long,
    val title: String,
    val tags: List<String> = emptyList(),
    val provenanceJson: String = "{}",
    val ocrOrPageText: String = "",
    val transcriptText: String = "",
    val voiceIdeaText: String = "",
    /** JPEG/PNG data-URL or empty; enforced ≤ [THUMB_MAX_BYTES]. */
    val thumbnailBase64: String? = null,
    val captureBlocked: Boolean = false,
    val continuityStatus: String = "local_only",
)

object ThinSyncLimits {
    /** Q33 assumption */
    const val THUMB_MAX_BYTES = 200 * 1024
}

/**
 * Transport adapters. Drive appData first; WebDAV fast-follow (Q32).
 */
interface ThinSyncTransport {
    val id: String

    suspend fun pushCiphertext(bytes: ByteArray): Result<Unit>

    suspend fun pullCiphertext(): Result<ByteArray?>
}

/** Mock Drive appData — no Google account created (S24 stub). */
class MockDriveAppDataTransport : ThinSyncTransport {
    override val id: String = "mock_drive_appdata"
    private var remote: ByteArray? = null

    override suspend fun pushCiphertext(bytes: ByteArray): Result<Unit> {
        remote = bytes.copyOf()
        return Result.success(Unit)
    }

    override suspend fun pullCiphertext(): Result<ByteArray?> =
        Result.success(remote?.copyOf())
}

/** WebDAV stub — Q32 fast-follow, not Phase 2 exit blocker. */
class WebDavTransportStub : ThinSyncTransport {
    override val id: String = "webdav_fast_follow"

    override suspend fun pushCiphertext(bytes: ByteArray): Result<Unit> =
        Result.failure(UnsupportedOperationException("WebDAV fast-follow not in Phase 2 exit"))

    override suspend fun pullCiphertext(): Result<ByteArray?> =
        Result.failure(UnsupportedOperationException("WebDAV fast-follow not in Phase 2 exit"))
}
