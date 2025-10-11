# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.

# Keep Kotlin Metadata
-keep class kotlin.Metadata { *; }

# Keep serialization classes
-keepattributes *Annotation*, InnerClasses
-dontnote kotlinx.serialization.AnnotationsKt
-keepclassmembers class kotlinx.serialization.json.** {
    *** Companion;
}
-keepclasseswithmembers class kotlinx.serialization.json.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep data classes for serialization
-keep,includedescriptorclasses class com.droidcon.uganda.data.**$$serializer { *; }
-keepclassmembers class com.droidcon.uganda.data.** {
    *** Companion;
}
-keepclasseswithmembers class com.droidcon.uganda.data.** {
    kotlinx.serialization.KSerializer serializer(...);
}

# Keep ViewModel classes
-keep class * extends androidx.lifecycle.ViewModel {
    <init>();
}
-keep class * extends androidx.lifecycle.AndroidViewModel {
    <init>(android.app.Application);
}

# Keep Compose runtime
-keep class androidx.compose.runtime.** { *; }
-keep class androidx.compose.ui.** { *; }

# Keep Ktor client
-keep class io.ktor.** { *; }
-dontwarn io.ktor.**

# Keep SLF4J logging
-dontwarn org.slf4j.**
-keep class org.slf4j.** { *; }

# DataStore
-keep class androidx.datastore.preferences.** { *; }

# DateTime
-keep class kotlinx.datetime.** { *; }

# General
-keepattributes Signature
-keepattributes Exceptions
-keepattributes SourceFile,LineNumberTable
