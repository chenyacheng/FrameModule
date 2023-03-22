package com.module.plugin

import org.gradle.kotlin.dsl.DependencyHandlerScope

fun DependencyHandlerScope.networkProject() {
    "api"(Google.gson)
    // Retrofit2.0依赖
    "api"(Squareup.retrofit)
    // 结果转为实体类所需依赖。使用到的相关类：GsonConverterFactory
    "api"(Squareup.converterGson)
    // 结果转为基本类型所需依赖，一般为String。使用到的相关类：ScalarsConverterFactory
    "api"(Squareup.converterScalars)
    // OKHttp3.0依赖
    "api"(Squareup.okhttp)
    // OKHttp3.0日志拦截器依赖。使用到的相关类：HttpLoggingInterceptor
    "api"(Squareup.loggingInterceptor)
}

fun DependencyHandlerScope.baseProject() {
    "api"(AndroidX.appcompat)
    // Java language implementation
    "api"(AndroidX.core)
    // Kotlin
    "api"(AndroidX.coreKtx)
    "api"(AndroidX.lifecycle_viewmodel)
    "api"(AndroidX.lifecycle_livedata)
    "api"(Google.gson)
}