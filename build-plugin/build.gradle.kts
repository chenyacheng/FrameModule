plugins {
    `kotlin-dsl`
}

// 注册插件 id
gradlePlugin {
    plugins {
        register("buildPlugin") {
            id = "com.module.build.plugin"
            implementationClass = "com.module.plugin.BuildPlugin"
        }
        register("networkPlugin") {
            id = "com.module.plugin.network"
            implementationClass = "com.module.plugin.NetworkPlugin"
        }
        register("basePlugin") {
            id = "com.module.plugin.base"
            implementationClass = "com.module.plugin.BasePlugin"
        }
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:7.3.1")
}