pluginManagement {
    @Suppress("UnstableApiUsage")
    includeBuild("build-plugin")
    repositories {
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        gradlePluginPortal()
        google()
        mavenCentral()
    }
}
@Suppress("UnstableApiUsage")
dependencyResolutionManagement {
    // repositoriesMode设置构建中存储库的依赖模式
    // FAIL_ON_PROJECT_REPOS -> 默认设置,项目或插件中任何库的所有构建都可以触发报错。
    // 比如如果在项目里面没有拉下来或者构建错误则直接报错，而不是再次去使用setting.gradle下去查找根存储库的声明

    // PREFER_PROJECT -> 优先使用项目声明的存储库，而非settings.gradle
    // 即多项目下，子项目或插件配置了自己的存储库，则忽略根settings.gradle下设置的存储库，即忽略下面这个 repositories

    // PREFER_SETTINGS -> 忽略项目或者插件中直接声明的存储库，只使用settings.gradle，即只使用下面这个repositories里的
    // 具体见: https://docs.gradle.org/current/javadoc/org/gradle/api/initialization/resolve/RepositoriesMode.html
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        maven("https://maven.aliyun.com/repository/central")
        maven("https://maven.aliyun.com/repository/google")
        maven("https://maven.aliyun.com/repository/public")
        maven("https://maven.aliyun.com/repository/gradle-plugin")
        google()
        mavenCentral()
        maven("https://jitpack.io")
    }
}
rootProject.name = "FrameModule"
include(":baselib")
include(":commonlib")
include(":networklib")
include(":localrepo")
include(":wxannotaion")
include(":wxcompiler")
include(":app")
include(":home")
include(":me")
