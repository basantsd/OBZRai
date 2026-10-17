package com.obzrai.observer.feature.export

import android.content.Context
import com.obzrai.observer.core.storage.LibraryRepository
import dagger.hilt.android.qualifiers.ApplicationContext
import org.json.JSONArray
import org.json.JSONObject
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.security.SecureRandom
import java.util.zip.ZipEntry
import java.util.zip.ZipInputStream
import java.util.zip.ZipOutputStream
import javax.crypto.Cipher
import javax.crypto.SecretKeyFactory
import javax.crypto.spec.GCMParameterSpec
import javax.crypto.spec.PBEKeySpec
import javax.crypto.spec.SecretKeySpec
import javax.inject.Inject
import javax.inject.Singleton

/**
 * FR-097 — encrypted export/import (Phase 1 continuity).
 * Format: PBKDF2-HMAC-SHA256 → AES-256-GCM wrapping a ZIP of manifest.json + media files.
 */
@Singleton
class EncryptedArchive @Inject constructor(
    @ApplicationContext private val context: Context,
    private val libraryRepository: LibraryRepository,
) {
    data class Result(
        val ok: Boolean,
        val path: String?,
        val itemCount: Int,
        val message: String,
    )

    suspend fun export(passphrase: CharArray, outFile: File = defaultExportFile()): Result {
        if (passphrase.size < 8) {
            return Result(false, null, 0, "Passphrase must be at least 8 characters")
        }
        val items = libraryRepository.getAllItems()
        val media = libraryRepository.getAllMedia()
        val manifest =
            JSONObject().apply {
                put("format", FORMAT)
                put("version", 1)
                put("exportedAtEpochMs", System.currentTimeMillis())
                put(
                    "items",
                    JSONArray().also { arr ->
                        items.forEach { item ->
                            arr.put(
                                JSONObject().apply {
                                    put("id", item.id)
                                    put("createdAtEpochMs", item.createdAtEpochMs)
                                    put("title", item.title)
                                    putOpt("sourceApp", item.sourceApp)
                                    putOpt("sourceUrl", item.sourceUrl)
                                    put("sourceUrlOrigin", item.sourceUrlOrigin.name)
                                    put("topicIdsCsv", item.topicIdsCsv)
                                    put("tagsCsv", item.tagsCsv)
                                    put("captureState", item.captureState)
                                    put("captureBlocked", item.captureBlocked)
                                    put("ocrText", item.ocrText)
                                    put("voiceIdeaText", item.voiceIdeaText)
                                    put("userNotes", item.userNotes)
                                    put("deviceLabel", item.deviceLabel)
                                    put("continuityStatus", item.continuityStatus)
                                },
                            )
                        }
                    },
                )
                put(
                    "media",
                    JSONArray().also { arr ->
                        media.forEach { m ->
                            arr.put(
                                JSONObject().apply {
                                    put("id", m.id)
                                    put("savedItemId", m.savedItemId)
                                    put("type", m.type)
                                    put("fileName", File(m.localUri).name)
                                    put("width", m.width)
                                    put("height", m.height)
                                    put("sha256", m.sha256)
                                    put("bytes", m.bytes)
                                    put("capturedAtEpochMs", m.capturedAtEpochMs)
                                },
                            )
                        }
                    },
                )
            }

        val zipBytes = ByteArrayOutputStream()
        ZipOutputStream(zipBytes).use { zip ->
            zip.putNextEntry(ZipEntry("manifest.json"))
            zip.write(manifest.toString().toByteArray(Charsets.UTF_8))
            zip.closeEntry()
            media.forEach { m ->
                val src = File(m.localUri)
                if (!src.exists()) return@forEach
                zip.putNextEntry(ZipEntry("media/${src.name}"))
                FileInputStream(src).use { it.copyTo(zip) }
                zip.closeEntry()
            }
        }

        val salt = ByteArray(16).also { SecureRandom().nextBytes(it) }
        val iv = ByteArray(12).also { SecureRandom().nextBytes(it) }
        val key = deriveKey(passphrase, salt)
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.ENCRYPT_MODE, key, GCMParameterSpec(128, iv))
        val ciphertext = cipher.doFinal(zipBytes.toByteArray())

        outFile.parentFile?.mkdirs()
        FileOutputStream(outFile).use { out ->
            out.write(MAGIC)
            out.write(1) // version
            out.write(salt)
            out.write(iv)
            out.write(ciphertext)
        }
        passphrase.fill('\u0000')
        return Result(true, outFile.absolutePath, items.size, "Exported ${items.size} items")
    }

    suspend fun importArchive(passphrase: CharArray, inFile: File): Result {
        if (!inFile.exists()) return Result(false, null, 0, "File not found")
        val bytes = inFile.readBytes()
        if (bytes.size < MAGIC.size + 1 + 16 + 12 + 16) {
            return Result(false, null, 0, "File too small")
        }
        var offset = 0
        val magic = bytes.copyOfRange(0, MAGIC.size)
        if (!magic.contentEquals(MAGIC)) return Result(false, null, 0, "Bad magic")
        offset = MAGIC.size
        val version = bytes[offset].toInt() and 0xff
        offset++
        if (version != 1) return Result(false, null, 0, "Unsupported version $version")
        val salt = bytes.copyOfRange(offset, offset + 16)
        offset += 16
        val iv = bytes.copyOfRange(offset, offset + 12)
        offset += 12
        val ciphertext = bytes.copyOfRange(offset, bytes.size)
        val key = deriveKey(passphrase, salt)
        passphrase.fill('\u0000')
        val cipher = Cipher.getInstance("AES/GCM/NoPadding")
        cipher.init(Cipher.DECRYPT_MODE, key, GCMParameterSpec(128, iv))
        val zipBytes =
            try {
                cipher.doFinal(ciphertext)
            } catch (t: Throwable) {
                return Result(false, null, 0, "Decrypt failed (wrong passphrase?)")
            }

        var imported = 0
        ZipInputStream(zipBytes.inputStream()).use { zip ->
            val mediaDir = File(context.filesDir, "library/frames").also { it.mkdirs() }
            var entry = zip.nextEntry
            var manifest: JSONObject? = null
            val stagedMedia = mutableMapOf<String, File>()
            while (entry != null) {
                if (entry.name == "manifest.json") {
                    manifest = JSONObject(zip.readBytes().toString(Charsets.UTF_8))
                } else if (entry.name.startsWith("media/")) {
                    val name = entry.name.removePrefix("media/")
                    val dest = File(mediaDir, "import_${System.currentTimeMillis()}_$name")
                    FileOutputStream(dest).use { zip.copyTo(it) }
                    stagedMedia[name] = dest
                }
                zip.closeEntry()
                entry = zip.nextEntry
            }
            val man = manifest ?: return Result(false, null, 0, "Missing manifest")
            val items = man.getJSONArray("items")
            val mediaArr = man.optJSONArray("media") ?: JSONArray()
            for (i in 0 until items.length()) {
                val o = items.getJSONObject(i)
                libraryRepository.upsertItem(
                    com.obzrai.observer.core.storage.entity.SavedItemEntity(
                        id = o.getString("id"),
                        createdAtEpochMs = o.getLong("createdAtEpochMs"),
                        title = o.getString("title"),
                        sourceApp = o.nullableString("sourceApp"),
                        sourceUrl = o.nullableString("sourceUrl"),
                        sourceUrlOrigin =
                            runCatching {
                                com.obzrai.observer.core.model.SourceUrlOrigin.valueOf(
                                    o.optString("sourceUrlOrigin", "NONE"),
                                )
                            }.getOrDefault(com.obzrai.observer.core.model.SourceUrlOrigin.NONE),
                        topicIdsCsv = o.optString("topicIdsCsv", ""),
                        tagsCsv = o.optString("tagsCsv", ""),
                        captureState = o.optString("captureState", "saved"),
                        captureBlocked = o.optBoolean("captureBlocked", false),
                        ocrText = o.optString("ocrText", ""),
                        voiceIdeaText = o.optString("voiceIdeaText", ""),
                        userNotes = o.optString("userNotes", ""),
                        deviceLabel = o.optString("deviceLabel", "Android"),
                        continuityStatus = o.optString("continuityStatus", "local_only"),
                    ),
                )
                imported++
            }
            for (i in 0 until mediaArr.length()) {
                val m = mediaArr.getJSONObject(i)
                val fileName = m.getString("fileName")
                val file = stagedMedia[fileName] ?: continue
                libraryRepository.upsertMedia(
                    com.obzrai.observer.core.storage.entity.MediaAssetEntity(
                        id = m.getString("id"),
                        savedItemId = m.getString("savedItemId"),
                        type = m.optString("type", "screenshot_original"),
                        localUri = file.absolutePath,
                        width = if (m.has("width") && !m.isNull("width")) m.getInt("width") else null,
                        height = if (m.has("height") && !m.isNull("height")) m.getInt("height") else null,
                        sha256 = m.nullableString("sha256"),
                        bytes = if (m.has("bytes") && !m.isNull("bytes")) m.getLong("bytes") else null,
                        capturedAtEpochMs = m.optLong("capturedAtEpochMs", System.currentTimeMillis()),
                    ),
                )
            }
        }
        return Result(true, inFile.absolutePath, imported, "Imported $imported items")
    }

    private fun defaultExportFile(): File =
        File(context.getExternalFilesDir(null) ?: context.filesDir, "obzrai-export.obzrai")

    private fun deriveKey(passphrase: CharArray, salt: ByteArray): SecretKeySpec {
        val factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256")
        val spec = PBEKeySpec(passphrase, salt, 120_000, 256)
        val keyBytes = factory.generateSecret(spec).encoded
        return SecretKeySpec(keyBytes, "AES")
    }

    companion object {
        const val FORMAT = "obzrai-encrypted-archive"
        private val MAGIC = byteArrayOf('O'.code.toByte(), 'B'.code.toByte(), 'Z'.code.toByte(), 'R'.code.toByte())
    }
}

private fun JSONObject.nullableString(key: String): String? =
    if (!has(key) || isNull(key)) null else optString(key).ifBlank { null }
