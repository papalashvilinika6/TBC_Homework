pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()   // REQUIRED for protobuf plugin
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "My Application"
include(":app")
