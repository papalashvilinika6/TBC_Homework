plugins {
    id("com.android.application") version "8.9.1" apply false
    id("org.jetbrains.kotlin.android") version "2.0.0" apply false
    id("com.google.dagger.hilt.android") version "2.51.1" apply false
    id("com.google.devtools.ksp") version "2.0.0-1.0.22" apply false
}

buildscript {
    repositories {
        google()
        mavenCentral()
    }
    dependencies {
        classpath ("com.android.tools.build:gradle:8.2.0")
        classpath ("com.google.gms:google-services:4.4.1")
    }
}

//// This block is required by the Firebase Gradle plugin.  Without it the
//// google-services.json file will not be processed.
//apply plugin: ("com.google.gms.google-services")