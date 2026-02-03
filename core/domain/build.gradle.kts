plugins {
    alias(libs.plugins.kotlin.jvm)
}

java {
    sourceCompatibility = JavaVersion.VERSION_11
    targetCompatibility = JavaVersion.VERSION_11
}

tasks.withType<org.jetbrains.kotlin.gradle.tasks.KotlinCompile>().configureEach {
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Pure Kotlin module - no Android dependencies
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.1")
    // For @Inject annotation
    implementation("javax.inject:javax.inject:1")
}
