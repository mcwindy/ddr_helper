pluginManagement {
    repositories {
        google()
        maven {
            setUrl("https://plugins.gradle.org/m2/")
        }
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "ddr helper"
include(":app")
