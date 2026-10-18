package com.obzrai.observer.feature.share

import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.lifecycleScope
import com.obzrai.observer.core.model.SourceUrlOrigin
import com.obzrai.observer.feature.library.ItemSaver
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * S1 approved interim + FR-015 Sharesheet import.
 * Accepts ACTION_SEND image or text/plain (URL or note).
 */
@AndroidEntryPoint
class ShareImportActivity : ComponentActivity() {
    @Inject
    lateinit var itemSaver: ItemSaver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = intent
        lifecycleScope.launch {
            val outcome =
                withContext(Dispatchers.IO) {
                    handleShare(intent)
                }
            val msg =
                when {
                    outcome == null -> "Nothing to import"
                    outcome.skipped -> "Skipped: ${outcome.reason}"
                    outcome.item?.captureBlocked == true ->
                        "Saved as capture_blocked (no screenshot) — idea/time kept"
                    else -> "Saved to OBZRAi library"
                }
            Toast.makeText(this@ShareImportActivity, msg, Toast.LENGTH_SHORT).show()
            finish()
        }
    }

    private suspend fun handleShare(intent: Intent?): ItemSaver.SaveOutcome? {
        if (intent?.action != Intent.ACTION_SEND) return null
        val type = intent.type.orEmpty()
        return when {
            type.startsWith("image/") -> {
                val uri = intent.parcelableUri(Intent.EXTRA_STREAM) ?: return null
                val bitmap =
                    contentResolver.openInputStream(uri)?.use { BitmapFactory.decodeStream(it) }
                itemSaver.save(
                    ItemSaver.SaveRequest(
                        bitmap = bitmap,
                        titleHint = intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty(),
                        sourceApp = intent.getStringExtra(Intent.EXTRA_SUBJECT),
                        sourceUrlOrigin = SourceUrlOrigin.SHARE_SHEET,
                        manualOverride = true,
                    ),
                )
            }
            type.startsWith("text/") -> {
                val text = intent.getStringExtra(Intent.EXTRA_TEXT).orEmpty()
                val url = text.lineSequence().map { it.trim() }.firstOrNull { it.startsWith("http") }
                itemSaver.save(
                    ItemSaver.SaveRequest(
                        bitmap = null,
                        titleHint = text.take(80),
                        sourceUrl = url,
                        sourceUrlOrigin =
                            if (url != null) SourceUrlOrigin.SHARE_SHEET else SourceUrlOrigin.NONE,
                        manualOverride = true,
                        userNotes = text,
                    ),
                )
            }
            else -> null
        }
    }
}

@Suppress("DEPRECATION")
private fun Intent.parcelableUri(key: String): Uri? =
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.TIRAMISU) {
        getParcelableExtra(key, Uri::class.java)
    } else {
        getParcelableExtra(key)
    }
