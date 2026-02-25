import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.time.LocalTime

private fun generateVersionCode(): String {
    val versionAppFromDateCreate = SimpleDateFormat("yy.MM.dd.HH", Locale.US)
    return versionAppFromDateCreate.format(Date())
}

private fun generateVersionName(): String {
    val currentTime = LocalTime.now()
    val minutes = currentTime.minute
    val seconds = currentTime.second
    val sumMinutesAndSeconds = minutes + seconds
    return generateVersionCode() + ".$sumMinutesAndSeconds"
}

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    id("com.google.devtools.ksp")
    id("com.chaquo.python")

    alias(libs.plugins.hilt.android)
    kotlin("kapt")

}

android {
    namespace = "com.bugiman.composercalculator"
    compileSdk = 36


    defaultConfig {
        applicationId = "com.example.composercalculator"
        minSdk = 29
        targetSdk = 34
        versionCode = 1

        versionName = generateVersionName()

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            abiFilters.addAll(
                listOf(
                    "armeabi-v7a",
                    "arm64-v8a",
                    "x86",
                    "x86_64"
                )
            )
        }
    }

    buildFeatures {
        buildConfig = true
        compose = true
        viewBinding = true
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
    dependenciesInfo {
        includeInApk = true
        includeInBundle = true
    }
//    buildToolsVersion = "30.0.3"
    ndkVersion = "25.1.8937393"

}

chaquopy {
    defaultConfig {
        pip {
            install("simpleeval")
            install("requests")
            install("sympy")
            install("numpy")
            install("matplotlib")
        }
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
    implementation("androidx.compose.material:material-icons-extended")
//    implementation(libs.mediation.test.suite)
    implementation(libs.androidx.compose.adaptive)
    implementation(libs.androidx.compose.foundation.layout)
    implementation(libs.androidx.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.compose.ui.test.junit4)
    debugImplementation(libs.androidx.compose.ui.tooling)
    debugImplementation(libs.androidx.compose.ui.test.manifest)

    implementation(libs.androidx.lifecycle.viewmodel.compose)
    implementation(libs.google.play.services.ads)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.core.splashscreen)
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.androidx.compose.material)

    implementation(libs.androidx.compose.material3.adaptive)
    implementation(libs.androidx.compose.material3.adaptive.layout)
    implementation(libs.androidx.compose.material3.adaptive.navigation)

    implementation(libs.androidx.compose.foundation)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.html.jvm)

    implementation(libs.androidx.room.runtime)
    implementation(libs.androidx.room.ktx)
    ksp(libs.androidx.room.compiler)

    implementation(libs.hilt.android)
    ksp(libs.hilt.compiler)
    implementation(libs.androidx.hilt.navigation.compose)

    implementation(libs.google.accompanist.drawablepainter)
    implementation(libs.lottie.compose)

    implementation(project(":domain"))
    implementation(project(":data"))

    val voyagerVersion = "1.1.0-beta03" // Текущая стабильная версия
    implementation("cafe.adriel.voyager:voyager-navigator:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-transitions:$voyagerVersion")
    implementation("cafe.adriel.voyager:voyager-hilt:$voyagerVersion")

    implementation("io.github.alexzhirkevich:cupertino-decompose:0.1.0-alpha04")
    implementation("io.github.alexzhirkevich:cupertino:0.1.0-alpha04")

}