import com.module.plugin.BuildConfig
import com.module.plugin.baseProject

plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.module.plugin.base")
}

android {
    namespace = "com.module.arch"
    compileSdk = BuildConfig.compileSdk
    defaultConfig {
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
    }
    buildFeatures {
        viewBinding = true
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
    baseProject()
}