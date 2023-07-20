plugins {
    `kotlin-dsl`
}

// 消除警告 -> 'compileJava' task (current target is 11) 
// and 'compileKotlin' task (current target is 1.8) jvm target
// compatibility should be set to the same Java version.
afterEvaluate {
    tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile> {
        kotlinOptions {
            jvmTarget = "11"
        }
    }
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