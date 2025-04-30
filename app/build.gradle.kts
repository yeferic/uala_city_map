import java.util.Properties

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.dagger.hilt)
    alias(libs.plugins.kotlin.ksp)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.jacoco)
    alias(libs.plugins.google.services)
}

val keyPropertiesFile = rootProject.file("key.properties")
val keyProperties = Properties()

if (keyPropertiesFile.exists()) {
    keyProperties.load(keyPropertiesFile.inputStream())
} else {
    throw GradleException(
        "key.properties not found.",
    )
}

val baseUrl: String =
    keyProperties.getProperty("BASE_URL")
        ?: throw GradleException(
            "BASE_URL is not set in key.properties. " +
                "This value is required for configuring the app environment.",
        )

val localDBName: String =
    keyProperties.getProperty("LOCAL_DB_NAME")
        ?: throw GradleException(
            "LOCAL_DB_NAME is not set in key.properties. " +
                "This value is required for create local db.",
        )

android {
    namespace = "com.yeferic.ualacity"
    compileSdk = 35

    buildFeatures {
        buildConfig = true
        compose = true
    }

    defaultConfig {
        applicationId = "com.yeferic.ualacity"
        minSdk = 24
        targetSdk = 35
        versionCode = System.getenv("VERSION_CODE")?.toInt() ?: 1
        versionName = System.getenv("VERSION_NAME") ?: "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        buildConfigField("String", "BASE_URL", "\"$baseUrl\"")

        buildConfigField("String", "LOCAL_DB_NAME", "\"$localDBName\"")
    }

    signingConfigs {
        create("release") {
            val keystorePassword: String? = System.getenv("KEYSTORE_PASSWORD")
            val keyAlias: String? = System.getenv("KEY_ALIAS")
            val keyPassword: String? = System.getenv("KEY_PASSWORD")

            if (keystorePassword != null && keyAlias != null && keyPassword != null) {
                storeFile = file("$rootDir/keystore.jks")
                storePassword = keystorePassword
                this.keyAlias = keyAlias
                this.keyPassword = keyPassword
            }
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            signingConfig = signingConfigs.getByName("release")
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
        debug {
            enableUnitTestCoverage = true
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }

    composeOptions {
        kotlinCompilerExtensionVersion = libs.versions.kotlinCompiler.get()
    }

    ksp {
        arg("room.schemaLocation", "$projectDir/schemas")
    }

    tasks.withType<Test>().configureEach {
        useJUnitPlatform()
    }

    lint {
        error += "VisibleForTests"
    }
}

tasks.withType(Test::class) {
    configure<JacocoTaskExtension> {
        isIncludeNoLocationClasses = true
        excludes = listOf("jdk.internal.*")
    }
}

dependencies {

    // Design System
    implementation(project(":designSystem"))

    // Compose
    implementation(platform(libs.compose.bom))
    implementation(libs.activity.compose)
    implementation(libs.navigation.compose)
    implementation(libs.compose.live.data)
    implementation(libs.ui)
    implementation(libs.ui.graphics)
    implementation(libs.ui.tooling)
    implementation(libs.ui.tooling.preview)
    implementation(libs.material3)
    implementation(libs.compose.constraintlayout)

    // Material
    implementation(libs.material)

    // Core
    implementation(libs.core.ktx)

    // Lifecycle
    implementation(libs.lifecycle.runtime.ktx)

    // Kotlin
    implementation(libs.kotlin.reflect)

    // Retrofit
    implementation(libs.retrofit)

    // Gson
    implementation(libs.gson)

    // OkHttp
    implementation(libs.okhttp)
    implementation(libs.okhttp.interceptor)

    // Activity
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.activity)

    // Dagger Hilt
    implementation(libs.hilt)
    ksp(libs.hilt.compiler)
    implementation(libs.hilt.compose)

    // Room
    implementation(libs.room.runtime)
    ksp(libs.room.compiler)
    implementation(libs.room.ktx)

    // Google
    implementation(libs.google.maps)
    implementation(libs.maps.compose)

    // Coroutine Test
    testImplementation(libs.coroutine.test)

    // Unit Test & Instrumentation
    testImplementation(libs.junit)
    testImplementation(libs.core.testing)
    testImplementation(libs.junit.api)
    testRuntimeOnly(libs.junit.engine)
    androidTestImplementation(libs.androidx.test.ext.junit)
    androidTestImplementation(libs.espresso.core)
    debugImplementation(libs.ui.test.manifest)

    // Mockk
    testImplementation(libs.bundles.mockk)
}
