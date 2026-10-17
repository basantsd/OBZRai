package com.obzrai.observer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.obzrai.observer.core.ui.theme.AIObserverTheme
import com.obzrai.observer.feature.capture.MediaProjectionGateway
import com.obzrai.observer.feature.home.HomeViewModel
import com.obzrai.observer.feature.navigation.ObserverApp
import com.obzrai.observer.feature.overlay.OverlayController
import com.obzrai.observer.feature.session.ObservationSessionController
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @Inject
    lateinit var mediaProjectionGateway: MediaProjectionGateway

    @Inject
    lateinit var overlayController: OverlayController

    @Inject
    lateinit var sessionController: ObservationSessionController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val homeViewModel: HomeViewModel = hiltViewModel()
            val needsConsent by homeViewModel.needsProjectionConsent.collectAsStateWithLifecycle()
            val needsOverlay by homeViewModel.needsOverlayPermission.collectAsStateWithLifecycle()

            val projectionLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult(),
                ) { result ->
                    val ok =
                        mediaProjectionGateway.onConsentResult(result.resultCode, result.data)
                    if (ok) {
                        sessionController.onProjectionConsentGranted()
                    } else {
                        sessionController.onProjectionConsentDenied()
                    }
                }

            val overlaySettingsLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.StartActivityForResult(),
                ) {
                    // Re-check after returning from Settings.
                    if (overlayController.canDrawOverlays()) {
                        homeViewModel.clearOverlayPermissionNeed()
                        homeViewModel.onToggleObserve()
                    }
                }

            val audioPermissionLauncher =
                rememberLauncherForActivityResult(
                    ActivityResultContracts.RequestPermission(),
                ) { /* S4 probe uses permission when granted */ }

            LaunchedEffect(Unit) {
                if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.RECORD_AUDIO)
                    != PackageManager.PERMISSION_GRANTED
                ) {
                    audioPermissionLauncher.launch(Manifest.permission.RECORD_AUDIO)
                }
            }

            LaunchedEffect(needsConsent) {
                if (needsConsent) {
                    val intent = mediaProjectionGateway.createScreenCaptureIntent(this@MainActivity)
                    projectionLauncher.launch(intent)
                }
            }

            LaunchedEffect(needsOverlay) {
                if (needsOverlay) {
                    overlaySettingsLauncher.launch(overlayController.overlaySettingsIntent())
                }
            }

            AIObserverTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ObserverApp(homeViewModel = homeViewModel)
                }
            }
        }
    }
}
