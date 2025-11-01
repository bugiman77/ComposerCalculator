import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

fun generateVersionCode(): Int {
    val simpleDateFormat = SimpleDateFormat("yyMMddHH", Locale.US)
    return simpleDateFormat.format(Date()).toInt()
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

fun generateVersionName(baseVersion: String): String {
    return "$baseVersion.${generateVersionCode()}"
}

// Базовая версия вашего приложения (меняйте ее вручную при крупных релизах)
val appVersionNameBase = "1.4"

android {
    namespace = "com.example.composercalculator"
    compileSdk = 36


    defaultConfig {
        applicationId = "com.example.composercalculator"
        minSdk = 29
        targetSdk = 34
        versionCode = 1
//        versionName = "1.0"

        versionCode = generateVersionCode()
        versionName = generateVersionName(appVersionNameBase)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildFeatures {
        buildConfig = true
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
    buildFeatures {
        compose = true
    }
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.compose.ui)
    implementation(libs.androidx.compose.ui.graphics)
    implementation(libs.androidx.compose.ui.tooling.preview)
    implementation(libs.androidx.compose.material3)
    implementation(libs.mediation.test.suite)
    implementation(libs.androidx.compose.adaptive)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    implementation("com.google.android.gms:play-services-ads:24.7.0")

    implementation("androidx.navigation:navigation-compose:2.7.7")

    implementation("androidx.core:core-splashscreen:1.0.1")

    // DataStore Preferences
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    // ViewModel для Compose
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.0")

    implementation("androidx.compose.material:material:1.6.8")
//    implementation("androidx.compose.material3:material3-adaptive:1.0.0-alpha11")

    // Kotlin (build.gradle.kts)
    val adaptive_version = "1.2.0" // As of October 2025, 1.2.0 is a stable version [7, 12]

    implementation("androidx.compose.material3.adaptive:adaptive:$adaptive_version")
    implementation("androidx.compose.material3.adaptive:adaptive-layout:$adaptive_version")
    implementation("androidx.compose.material3.adaptive:adaptive-navigation:$adaptive_version")


}