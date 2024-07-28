plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrainsCompose).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
}

buildscript {
    repositories {
        mavenCentral()
    }
    dependencies {
        classpath(libs.buildkonfig.gradle.plugin)
    }
}