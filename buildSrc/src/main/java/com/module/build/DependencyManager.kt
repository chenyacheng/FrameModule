package com.module.build

object Versions {
    const val gson = "2.9.0"
    const val material = "1.4.0"
    const val zxing = "3.4.1"
    const val appcompat = "1.4.1"
    const val core = "1.7.0"
    const val lifecycle_version = "2.4.0"
    const val viewpager2 = "1.0.0"
    const val constraintlayout = "2.1.0"
    const val swiperefreshlayout = "1.1.0"
    //const val splashscreen = "1.0.0-alpha01"
    const val retrofit = "2.9.0"
    const val okhttp = "4.9.3"
    const val leakcanary_android = "2.7"
    const val pictureselector = "v2.7.3-rc10"
    const val snackBar = "1.2.2"
    const val coil = "2.0.0-rc02"
    const val glide = "4.13.0"
    const val arouter = "1.5.2"
}

object Google {
    const val gson = "com.google.code.gson:gson:${Versions.gson}"
    const val material = "com.google.android.material:material:${Versions.material}"
    const val zxing = "com.google.zxing:core:${Versions.zxing}"
}

object AndroidX {
    const val appcompat = "androidx.appcompat:appcompat:${Versions.appcompat}"
    const val core = "androidx.core:core:${Versions.core}"
    const val coreKtx = "androidx.core:core-ktx:${Versions.core}"
    const val lifecycle_viewmodel = "androidx.lifecycle:lifecycle-viewmodel:${Versions.lifecycle_version}"
    const val lifecycle_livedata = "androidx.lifecycle:lifecycle-livedata:${Versions.lifecycle_version}"
    const val lifecycle_common_java8 = "androidx.lifecycle:lifecycle-common-java8:${Versions.lifecycle_version}"
    const val viewpager2 = "androidx.viewpager2:viewpager2:${Versions.viewpager2}"
    const val constraintlayout = "androidx.constraintlayout:constraintlayout:${Versions.constraintlayout}"
    const val swiperefreshlayout = "androidx.swiperefreshlayout:swiperefreshlayout:${Versions.swiperefreshlayout}"
    //const val splashscreen = "androidx.core:core-splashscreen:${Versions.splashscreen}"
}

object Squareup {
    const val retrofit = "com.squareup.retrofit2:retrofit:${Versions.retrofit}"
    const val converterGson = "com.squareup.retrofit2:converter-gson:${Versions.retrofit}"
    const val converterScalars = "com.squareup.retrofit2:converter-scalars:${Versions.retrofit}"
    const val okhttp = "com.squareup.okhttp3:okhttp:${Versions.okhttp}"
    const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Versions.okhttp}"
    const val leakcanary_android = "com.squareup.leakcanary:leakcanary-android:${Versions.leakcanary_android}"
}

object Github {
    const val pictureselector = "io.github.lucksiege:pictureselector:${Versions.pictureselector}"
    const val snackBar = "com.github.chenyacheng:SnackBar:${Versions.snackBar}"
    const val coil = "io.coil-kt:coil:${Versions.coil}"
    const val glide = "com.github.bumptech.glide:glide:${Versions.glide}"
    const val glide_compiler = "com.github.bumptech.glide:compiler:${Versions.glide}"

}

object Alibaba {
    const val arouter = "com.alibaba:arouter-api:${Versions.arouter}"
    const val arouter_compiler = "com.alibaba:arouter-compiler:${Versions.arouter}"
}