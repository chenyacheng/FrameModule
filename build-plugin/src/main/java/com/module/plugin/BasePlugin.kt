package com.module.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BasePlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("====== base 库依赖 -> start ======")
        println("====== base 库依赖 -> end ======")
    }
}