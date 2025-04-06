plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("kotlin-kapt")
    alias(libs.plugins.hilt)
}

android {
    namespace = "com.example.mathmastery_beta"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.mathmastery_beta"
        minSdk = 26
        targetSdk = 35
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }

    kotlinOptions {
        jvmTarget = "11"
    }

    kapt {
        correctErrorTypes = true
    }

    buildFeatures {
        compose = true
    }
}

dependencies {
    // --- Core Android ---
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.androidx.core.ktx)

    // --- Jetpack Compose ---
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.activity.compose)

    // --- Lifecycle & Datastore ---
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.androidx.datastore.preferences)

    // --- Hilt / Dependency Injection ---
    implementation(libs.hilt.android)
    implementation(libs.hilt.navigation.compose)
    kapt(libs.hilt.compiler)

    // --- RecyclerView & ViewPager2 ---
    implementation("androidx.recyclerview:recyclerview:1.2.1")
    implementation("androidx.viewpager2:viewpager2:1.0.0")

    // --- Other Libraries ---
    implementation("com.google.code.gson:gson:2.12.1")
    implementation("commons-io:commons-io:2.18.0")
    implementation("com.github.bumptech.glide:glide:4.16.0")

    // --- Project Modules ---
    implementation(project(":game2048"))

    // --- Debugging Tools ---
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // --- Testing ---
    testImplementation(libs.junit)
    testImplementation("com.google.dagger:hilt-android-testing:2.56.1")
    testAnnotationProcessor("com.google.dagger:hilt-compiler:2.56.1")

    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
    androidTestImplementation("com.google.dagger:hilt-android-testing:2.56.1")
    androidTestAnnotationProcessor("com.google.dagger:hilt-compiler:2.56.1")
}


