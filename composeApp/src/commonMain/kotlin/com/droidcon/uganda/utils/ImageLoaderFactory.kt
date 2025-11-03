package com.droidcon.uganda.utils

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.disk.DiskCache
import coil3.memory.MemoryCache
import coil3.request.CachePolicy
import coil3.request.crossfade
import coil3.util.DebugLogger
import okio.FileSystem

/**
 * Factory for creating ImageLoader instances for Coil3
 * Handles configuration for both Android and iOS platforms
 */
object ImageLoaderFactory {

    fun create(context: PlatformContext): ImageLoader {
        return ImageLoader.Builder(context)
            .memoryCache {
                MemoryCache.Builder()
                    .maxSizePercent(context, 0.25)
                    .build()
            }
            .diskCache {
                newDiskCache()
            }
            // Enable crossfade animation
            .crossfade(true)
            // Enable debug logging
            .logger(DebugLogger())
            // Configure cache policies
            .memoryCachePolicy(CachePolicy.ENABLED)
            .diskCachePolicy(CachePolicy.ENABLED)
            .networkCachePolicy(CachePolicy.ENABLED)
            .build()
    }

    private fun newDiskCache(): DiskCache {
        return DiskCache.Builder()
            .directory(FileSystem.SYSTEM_TEMPORARY_DIRECTORY / "image_cache")
            .maxSizeBytes(512L * 1024 * 1024) // 512MB
            .build()
    }
}
