package com.obzrai.observer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.obzrai.observer.core.ui.theme.AIObserverTheme
import com.obzrai.observer.feature.navigation.ObserverApp
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AIObserverTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    ObserverApp()
                }
            }
        }
    }
}
