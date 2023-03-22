package com.module.plugin

import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.JavaVersion
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies

class BasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("====== base 库依赖 -> start ======")
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
                compileOptions {
                    sourceCompatibility = JavaVersion.VERSION_1_8
                    targetCompatibility = JavaVersion.VERSION_1_8
                }
                buildFeatures {
                    viewBinding = true
                }
            }
            dependencies {
                baseProject()
            }
        }
        println("====== base 库依赖 -> end ======")
    }
}