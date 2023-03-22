package com.module.plugin

import org.gradle.api.Plugin
import org.gradle.api.Project

class BuildPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        println("构建依赖")
    }
}