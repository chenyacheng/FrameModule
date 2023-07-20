import com.module.plugin.BuildConfig
import com.module.plugin.networkProject

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.module.plugin.network")
}

android {
    namespace = "com.module.network"
    compileSdk = BuildConfig.compileSdk
    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
    }
    buildFeatures {
        buildConfig = false
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
}

dependencies {
    networkProject()
}