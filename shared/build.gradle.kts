plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.skie)
    alias(libs.plugins.androidLibrary)
    id("dev.icerock.mobile.multiplatform-resources")
}

kotlin {
    androidTarget {
        compilations.all {
            kotlinOptions {
                jvmTarget = "1.8"
            }
        }
    }

    listOf(
        iosX64(),
        iosArm64(),
        iosSimulatorArm64()
    ).forEach {
        it.binaries.framework {
            baseName = "shared"
            isStatic = true

            export(libs.moko.resources)
            export(libs.moko.graphics.export)
        }
    }

    sourceSets {
        commonMain.dependencies {
            //put your multiplatform dependencies here

            //Data store
            api(libs.androidx.datastore)
            api(libs.androidx.datastore.preferences)

            //Coroutines
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.skie.annotations)

            //Koin
            api(libs.koin.core)

            //Mutex
            implementation(libs.atomicfu)

            //Koin tests
//            implementation(libs.koin.test)
//            implementation(libs.koin.test.junit4)
//            implementation(libs.koin.android.test)

            //Shared res
            api(libs.moko.resources)

            //Date time
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
    }
}

multiplatformResources {
    resourcesPackage.set("com.vodimobile.shared.resources")
    resourcesClassName.set("SharedRes")
}

android {
    namespace = "com.vodimobile"
    compileSdk = 34
    defaultConfig {
        minSdk = 24
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
}
