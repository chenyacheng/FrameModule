package com.module.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class NetworkPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("====== 网络依赖 -> start ======")
        println("====== 网络依赖 -> end ======")
    }
}