import com.android.ddmlib.Log
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
    kotlin("plugin.serialization") version "1.9.23"
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

            //Ktor
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.encoding)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.client.auth)
            implementation(libs.ktor.client.cio)
            implementation(libs.kotlinx.serialization.json)

            //Shared res
            api(libs.moko.resources)

            //Date time
            implementation(libs.kotlinx.datetime)

            //Supabase
            implementation(project.dependencies.platform(libs.bom))
            implementation(libs.postgrest.kt)
            implementation(libs.gotrue.kt)
            implementation(libs.realtime.kt)

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

fun getStringValueFromLocalProperties(name: String): String {
    val value = project.getLocalProperty().getProperty(name)?.toString()
    if (value == null) {
        throw GradleException("Property $name not found in local.properties")
    }
    return value
}

fun Project.getLocalProperty(file: String = "local.properties"): Properties {
    val properties = Properties()
    val localProperties = File(rootDir, file)
    if (localProperties.isFile) {
        InputStreamReader(FileInputStream(localProperties), Charsets.UTF_8).use { reader ->
            properties.load(reader)
        }
    } else {
        throw GradleException("File '$file' not found at path: ${localProperties.absolutePath}")
    }
    return properties
}


buildkonfig {
    packageName = "com.vodimobile.shared.buildkonfig"
    objectName = "SharedBuildkonfig"

    val cmr_server = getStringValueFromLocalProperties(name = "crm.server")
    val cmr_port = getStringValueFromLocalProperties(name = "crm.port")
    val cmr_login = getStringValueFromLocalProperties(name = "crm.login")
    val crm_password_hash = getStringValueFromLocalProperties(name = "crm.password.hash")
    val crm_test_access_token = getStringValueFromLocalProperties(name = "crm.test.access.token")
    val crm_test_refresh_token = getStringValueFromLocalProperties(name = "crm.test.refresh.token")

    val supabase_url = getStringValueFromLocalProperties(name = "supabase.url")
    val supabase_public = getStringValueFromLocalProperties(name = "supabase.public")
    val supabase_secret = getStringValueFromLocalProperties(name = "supabase.secret")

    defaultConfigs {
        buildConfigField(STRING, "crm_server", cmr_server, nullable = false, const = true)
        buildConfigField(STRING, "crm_port", cmr_port, nullable = false, const = true)
        buildConfigField(STRING, "crm_login", cmr_login, nullable = false, const = true)
        buildConfigField(STRING, "crm_password_hash", crm_password_hash, nullable = false, const = true)
        buildConfigField(STRING, "crm_test_access_token", crm_test_access_token, nullable = false, const = true)
        buildConfigField(STRING, "crm_test_refresh_token", crm_test_refresh_token, nullable = false, const = true)

        buildConfigField(STRING, "supabase_url", supabase_url, nullable = false, const = true)
        buildConfigField(STRING, "supabase_public", supabase_public, nullable = false, const = true)
        buildConfigField(STRING, "supabase_secret", supabase_secret, nullable = false, const = true)
    }
}
