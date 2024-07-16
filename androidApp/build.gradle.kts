plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.compose.compiler)
}

android {
    namespace = "com.vodimobile.android"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.vodimobile.android"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":shared"))
    implementation(libs.compose.ui)
    implementation(libs.compose.ui.tooling.preview)
    implementation(libs.compose.material3)
    implementation(libs.androidx.activity.compose)
    implementation(libs.compose.foundation)
    implementation(libs.androidx.foundation.android)
    debugImplementation(libs.compose.ui.tooling)

    implementation(libs.androidx.material)

    // Navigation
    implementation (libs.androidx.navigation.compose)

    //Splash screen
    implementation(libs.androidx.core.splashscreen)

    //Android UI tests
    implementation(libs.androidx.ui.test.junit4.android)
    androidTestImplementation(libs.testng)
    debugImplementation(libs.androidx.ui.test.manifest)
}