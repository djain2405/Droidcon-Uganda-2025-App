package com.droidcon.uganda

import coil3.SingletonImageLoader
import com.droidcon.uganda.utils.ImageLoaderFactory

/**
 * Initialize Coil ImageLoader for iOS
 */
fun initializeImageLoader() {
    try {
        println("🎨 Initializing Coil ImageLoader for iOS")

        SingletonImageLoader.setSafe { context ->
            ImageLoaderFactory.create(context)
        }

        println("✅ Coil ImageLoader initialized successfully")
    } catch (e: Exception) {
        println("❌ Failed to initialize Coil ImageLoader: ${e.message}")
        e.printStackTrace()
        // Don't rethrow - let the app continue with fallback images
    }
}
