import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import java.io.FileInputStream
import java.io.InputStreamReader
import java.util.Properties

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.skie)
    alias(libs.plugins.androidLibrary)
    id("dev.icerock.mobile.multiplatform-resources")
    id("com.codingfeline.buildkonfig")
}

val cmr_server = getStringValueFromLocalProperties(name = "crm.server")
val cmr_port = getStringValueFromLocalProperties(name = "crm.port")

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

            //Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)

            //Shared res
            api(libs.moko.resources)

            //Date time
            implementation(libs.kotlinx.datetime)
        }
        commonTest.dependencies {
            implementation(libs.kotlin.test)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
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

buildkonfig {
    packageName = "com.vodimobile.shared.buildkonfig"
    objectName = "SharedBuildkonfig"

    defaultConfigs {
        buildConfigField(STRING, "crm_server", cmr_server, nullable = false, const = true)
        buildConfigField(STRING, "crm_port", cmr_port, nullable = false, const = true)
    }
}

fun getStringValueFromLocalProperties(name: String): String {
    val result = project.getLocalProperty().getProperty(name).toString()
    return result
}

fun Project.getLocalProperty(file: String = "local.properties"): Properties {
    val properties = Properties()
    val localProperties = File(file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else error("File from not found")

    return properties
}