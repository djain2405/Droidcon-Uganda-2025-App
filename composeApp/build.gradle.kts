import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Properties
import java.io.FileInputStream

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.kotlinx.serialization)
}

// Load signing credentials and Typesense keys from local.properties (gitignored).
// typesense.host=https://xxx.a1.typesense.net
// typesense.searchApiKey=search-only key
// typesense.adminApiKey=admin key, compiled into Android debug and iOS debug builds only
val localProperties = Properties()
val localPropertiesFile = rootProject.file("local.properties")
if (localPropertiesFile.exists()) {
    localProperties.load(FileInputStream(localPropertiesFile))
}

fun quoteKotlinString(value: String): String = buildString {
    append('"')
    value.forEach { ch ->
        when (ch) {
            '\\' -> append("\\\\")
            '"' -> append("\\\"")
            '$' -> append("\\$")
            '\n' -> append("\\n")
            '\r' -> append("\\r")
            else -> append(ch)
        }
    }
    append('"')
}

fun quoteBuildConfigString(value: String): String {
    val escaped = value
        .replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
    return "\"$escaped\""
}

val typesenseGeneratedDir = layout.buildDirectory.dir("generated/typesense/kotlin")
val typesenseIosGeneratedDir = layout.buildDirectory.dir("generated/typesense/iosKotlin")
val generateTypesenseConfig = tasks.register("generateTypesenseConfig") {
    val host = localProperties.getProperty("typesense.host")?.trim()?.trimEnd('/') ?: ""
    val searchKey = localProperties.getProperty("typesense.searchApiKey")?.trim() ?: ""
    val adminKey = localProperties.getProperty("typesense.adminApiKey")?.trim() ?: ""
    val outputDir = typesenseGeneratedDir
    val iosOutputDir = typesenseIosGeneratedDir
    inputs.property("host", host)
    inputs.property("searchKey", searchKey)
    inputs.property("adminKey", adminKey)
    outputs.dir(outputDir)
    outputs.dir(iosOutputDir)
    doLast {
        val packageDir = outputDir.get().asFile.resolve("com/droidcon/uganda/data")
        packageDir.mkdirs()
        packageDir.resolve("TypesenseConfig.kt").writeText(
            """
            package com.droidcon.uganda.data

            internal object TypesenseConfig {
                const val host: String = ${quoteKotlinString(host)}
                const val searchApiKey: String = ${quoteKotlinString(searchKey)}

                val isConfigured: Boolean = host.isNotBlank() && searchApiKey.isNotBlank()
            }

            """.trimIndent()
        )
        val iosPackageDir = iosOutputDir.get().asFile.resolve("com/droidcon/uganda/data")
        iosPackageDir.mkdirs()
        iosPackageDir.resolve("TypesenseAdmin.ios.kt").writeText(
            """
            package com.droidcon.uganda.data

            import kotlin.experimental.ExperimentalNativeApi
            import kotlin.native.Platform

            @OptIn(ExperimentalNativeApi::class)
            internal actual fun typesenseAdminApiKey(): String =
                if (Platform.isDebugBinary) ${quoteKotlinString(adminKey)} else ""

            """.trimIndent()
        )
    }
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    val xcf = XCFramework()
    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "ComposeApp"
            isStatic = true
            xcf.add(this)
        }
    }

    sourceSets {
        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.android)
            implementation("androidx.datastore:datastore-preferences:1.1.1")
            implementation("androidx.core:core-splashscreen:1.0.1")
        }
        iosMain {
            kotlin.srcDir(typesenseIosGeneratedDir)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
        getByName("androidUnitTest").dependencies {
            implementation(kotlin("test"))
        }
        commonMain {
            kotlin.srcDir(typesenseGeneratedDir)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.kotlinx.datetime)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.datastore.preferences)
            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor)
        }
    }
}

tasks.configureEach {
    if (name != generateTypesenseConfig.name && name.contains("Kotlin", ignoreCase = true)) {
        dependsOn(generateTypesenseConfig)
    }
}

android {
    namespace = "com.droidcon.uganda"
    compileSdk = libs.versions.android.compileSdk.get().toInt()
    buildFeatures {
        buildConfig = true
    }

    defaultConfig {
        applicationId = "com.droidcon.uganda"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 7
        versionName = "1.2.0"
        buildConfigField("String", "TYPESENSE_ADMIN_API_KEY", "\"\"")
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    signingConfigs {
        create("release") {
            // Read from local.properties, which is gitignored:
            // signing.storeFile, signing.storePassword, signing.keyAlias, signing.keyPassword
            localProperties.getProperty("signing.storeFile")?.let {
                storeFile = file(it)
            }
            storePassword = localProperties.getProperty("signing.storePassword")
            keyAlias = localProperties.getProperty("signing.keyAlias")
            keyPassword = localProperties.getProperty("signing.keyPassword")
        }
    }
    buildTypes {
        getByName("debug") {
            buildConfigField(
                "String",
                "TYPESENSE_ADMIN_API_KEY",
                quoteBuildConfigString(localProperties.getProperty("typesense.adminApiKey")?.trim() ?: "")
            )
        }
        getByName("release") {
            isMinifyEnabled = true
            isShrinkResources = true
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}
