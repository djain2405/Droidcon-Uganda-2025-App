package com.droidcon.uganda

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import coil3.SingletonImageLoader
import com.droidcon.uganda.utils.ImageLoaderFactory

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Install splash screen before setContent
        installSplashScreen()

        // Initialize AppContext for DataStore
        AppContext.init(this)

        SingletonImageLoader.setSafe { context ->
            ImageLoaderFactory.create(context)
        }

        setContent {
            App()
        }
    }
}

@Preview
@Composable
fun AppAndroidPreview() {
    App()
}
