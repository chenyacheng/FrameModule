import com.module.plugin.Alibaba
import com.module.plugin.BuildConfig
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

val isModule: String by project

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.module.build.plugin")
}
android {
    namespace = "com.module.app"
    compileSdk = BuildConfig.compileSdk
    defaultConfig {
        applicationId = "com.module.app"
        minSdk = BuildConfig.minSdk
        targetSdk = BuildConfig.targetSdk
        versionCode = BuildConfig.versionCode
        versionName = BuildConfig.versionName
    }
    // signingConfigs 要放在 buildTypes 前面
    signingConfigs {
        getByName("debug") {
            storeFile = file("signature/debug.jks")
            storePassword = "123456"
            keyAlias = "debug"
            keyPassword = "123456"
        }
        create("keyStore") {
            storeFile = file("signature/xxx.jks")
            storePassword = "123456"
            keyAlias = "key0"
            keyPassword = "123456"
        }
    }
    // debug 和 release 是隐式提供
    buildTypes {
        getByName("debug") {
            // minifyEnabled是否启用混淆
            isMinifyEnabled = false
            // 删除无效的Resource
            isShrinkResources = false
            signingConfig = signingConfigs.getByName("debug")
        }
        getByName("release") {
            // minifyEnabled是否启用混淆
            isMinifyEnabled = false
            // 删除无效的Resource
            isShrinkResources = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("keyStore")
        }
        android.applicationVariants.all {
            outputs.all {
                if (this is com.android.build.gradle.internal.api.ApkVariantOutputImpl) {
                    this.outputFileName = "module_${releaseTime() + "_" + versionName}.apk"
                }
            }
        }
    }
    compileOptions {
        sourceCompatibility(JavaVersion.VERSION_11)
        targetCompatibility(JavaVersion.VERSION_11)
    }
    buildFeatures {
        viewBinding = true
        buildConfig = false
    }
    kotlinOptions {
        jvmTarget = JavaVersion.VERSION_11.toString()
    }
}

kapt {
    arguments {
        arg("AROUTER_MODULE_NAME", project.name)
    }
}

fun releaseTime(): String {
    val current = LocalDateTime.now()
    val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd_HHmmss")
    return current.format(formatter)
}

tasks.withType<JavaCompile> {
    options.encoding = "UTF-8"
}

dependencies {
    if (isModule.toBoolean()) {
        implementation(project(":commonlib"))
    } else {
        implementation(project(":home"))
        implementation(project(":me"))
        kapt(Alibaba.arouter_compiler)
    }
}