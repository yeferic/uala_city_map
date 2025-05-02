plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.yeferic.desingsystem"
    compileSdk = 35

    defaultConfig {
        minSdk = 24
        testApplicationId = "com.yeferic.desingsystem"
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        testFunctionalTest = true
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    buildFeatures {
        compose = true
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

dependencies {
    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.activity.compose)

    // Material
    implementation(libs.material)

    // Rive
    implementation(libs.bundles.rive)

    // Core
    implementation(libs.core.ktx)

    // Lottie
    implementation(libs.lottie)

    // Google
    implementation(libs.google.maps)
    implementation(libs.maps.compose)

    // Test
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.compose.test.junit4)
    androidTestImplementation(libs.espresso.core)
    debugApi(libs.compose.test.manifest)
}
