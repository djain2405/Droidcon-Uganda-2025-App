package com.droidcon.uganda

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.SingletonImageLoader
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import okio.FileSystem

/**
 * Initialize Coil ImageLoader for iOS
 */
fun initializeImageLoader() {
    try {
        println("🎨 Initializing Coil ImageLoader for iOS")

        // Set up the singleton ImageLoader
        SingletonImageLoader.setSafe {
            ImageLoader.Builder(PlatformContext.INSTANCE)
                .memoryCache {
                    MemoryCache.Builder()
                        .maxSizePercent(PlatformContext.INSTANCE, percent = 0.25)
                        .build()
                }
                .diskCache {
                    DiskCache.Builder()
                        .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
                        .maxSizeBytes(512L * 1024 * 1024) // 512MB
                        .build()
                }
                .build()
        }

        println("✅ Coil ImageLoader initialized successfully")
    } catch (e: Exception) {
        println("❌ Failed to initialize Coil ImageLoader: ${e.message}")
        e.printStackTrace()
        // Don't rethrow - let the app continue with fallback images
    }
}
