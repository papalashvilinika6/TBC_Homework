import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlinAndroid)
    alias(libs.plugins.kotlinParcelize)
    alias(libs.plugins.navigationSafeArgs)
    alias(libs.plugins.kotlinSerialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.hilt)
}


android {
    namespace = "com.example.myapplication"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.myapplication"
        minSdk = 24
        targetSdk = 36
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables.useSupportLibrary = true


        val mapsKey: String = run {
            val props = Properties()
            val f = rootProject.file("local.properties")
            if (f.exists()) f.inputStream().use { props.load(it) }
            (props.getProperty("MAPS_API_KEY") ?: System.getenv("MAPS_API_KEY") ?: "")
        }.trim()

        if (mapsKey.isEmpty() || mapsKey == "REPLACE_ME") {
            throw GradleException("Missing MAPS_API_KEY. Add it to local.properties or env.")
        }
        manifestPlaceholders["MAPS_API_KEY"] = mapsKey
    }

    buildFeatures {
        viewBinding = true
        buildConfig = true
    }

    buildTypes {
        debug {
            isMinifyEnabled = false

            buildConfigField("String", "BASE_URL", "\"https://mocki.io/\"")
            buildConfigField("String", "API_VERSION", "\"v1/\"")

            buildConfigField("String", "PLACE_ENDPOINT", "\"v1/d7c6d734-6080-4045-a196-7da16339b6d7\"")
            buildConfigField("String", "POSTS_ENDPOINT", "\"1e3f40b1-19a5-4986-ad60-fdc80c27234b\"")
        }

        release {
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )

            buildConfigField("String", "BASE_URL", "\"https://mocki.io/\"")
            buildConfigField("String", "API_VERSION", "\"v1/\"")

            buildConfigField("String", "PLACE_ENDPOINT", "\"v1/d7c6d734-6080-4045-a196-7da16339b6d7\"")
            buildConfigField("String", "POSTS_ENDPOINT", "\"1e3f40b1-19a5-4986-ad60-fdc80c27234b\"")
        }
    }




    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }



}

dependencies {
    implementation(libs.core.ktx)
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation("androidx.activity:activity-ktx:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.2.1")
    implementation("androidx.navigation:navigation-fragment-ktx:2.9.6")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.9.4")
    implementation("androidx.viewpager2:viewpager2:1.1.0")
    implementation("androidx.recyclerview:recyclerview:1.4.0")
    testImplementation("androidx.room:room-compiler-processing-testing:2.8.3")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.3.0")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.7.0")
    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.11.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.7.0")
    implementation("com.jakewharton.retrofit:retrofit2-kotlinx-serialization-converter:1.0.0")
    implementation("androidx.fragment:fragment-ktx:1.6.1")
    implementation("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.paging:paging-runtime-ktx:3.3.2")
    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.navigationFragment)
    implementation(libs.navigationUi)
    implementation("androidx.room:room-runtime:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("io.coil-kt:coil:2.7.0")
    implementation("com.google.android.flexbox:flexbox:3.0.0")
    implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.6.0")
    implementation("com.google.android.gms:play-services-maps:19.0.0")
    implementation("com.google.android.gms:play-services-location:21.3.0")
    implementation("com.google.maps.android:android-maps-utils:3.8.2")


}



