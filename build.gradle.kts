plugins {
    alias(libs.plugins.androidApplication).apply(false)
    alias(libs.plugins.androidLibrary).apply(false)
    alias(libs.plugins.kotlinAndroid).apply(false)
    alias(libs.plugins.kotlinMultiplatform).apply(false)
    alias(libs.plugins.jetbrainsCompose).apply(false)
    alias(libs.plugins.compose.compiler).apply(false)
    id("org.jetbrains.kotlin.jvm").version("1.7.20").apply(false)
}

buildscript {
    repositories {
        mavenCentral()
        google()
        gradlePluginPortal()
        mavenLocal()
    }

    dependencies {
        classpath(libs.resources.generator)
        classpath(libs.buildkonfig.gradle.plugin)
    }
}