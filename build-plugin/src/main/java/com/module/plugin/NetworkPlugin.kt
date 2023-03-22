package com.module.plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.extra

class NetworkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("====== 网络依赖 -> start ======")
        with(target) {
            plugins.run {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }
            extensions.configure<LibraryExtension> {
                compileSdk = BuildConfig.compileSdk
                defaultConfig {
                    minSdk = BuildConfig.minSdk
                    targetSdk = BuildConfig.targetSdk
                }
                buildTypes {
                    getByName("debug") {
                        buildConfigField("boolean", "SHOW_LOG", "true")
                        buildConfigField("boolean", "ENCRYPT", "${rootProject.extra["encrypt"]}")
                        buildConfigField(
                            "boolean",
                            "ORIGINAL_DATA",
                            "${rootProject.extra["original_data"]}"
                        )
                    }
                    getByName("release") {
                        buildConfigField("boolean", "SHOW_LOG", "false")
                        buildConfigField("boolean", "ENCRYPT", "${rootProject.extra["encrypt"]}")
                        buildConfigField(
                            "boolean",
                            "ORIGINAL_DATA",
                            "${rootProject.extra["original_data"]}"
                        )
                    }
                }
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
            }
            dependencies {
                networkProject()
            }
        }
        println("====== 网络依赖 -> end ======")
    }
}