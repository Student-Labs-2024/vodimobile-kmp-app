pluginManagement {
    repositories {
        google ()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "Водимобиль"
include(":androidApp")
include(":shared")
