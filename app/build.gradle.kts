import com.android.build.gradle.internal.cxx.configure.gradleLocalProperties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("dagger.hilt.android.plugin")
    id("io.realm.kotlin")
}

android {

    android.buildFeatures.buildConfig = true

    namespace = "com.alexafit.fitjournal"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.alexafit.fitjournal"
        minSdk = 29
        targetSdk = 34
        versionCode = 2
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            val ADS_ID_VALUE: String = gradleLocalProperties(rootDir, providers).getProperty("ADS_ID_PROD")
            buildConfigField("String", "ADS_ID", ADS_ID_VALUE)

            isMinifyEnabled = true
            proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
        }
        debug {
            val ADS_ID_VALUE: String = gradleLocalProperties(rootDir, providers).getProperty("ADS_ID")
            buildConfigField("String", "ADS_ID", ADS_ID_VALUE)
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.14"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.13.1")
    implementation("androidx.appcompat:appcompat:1.7.0")

    /* Coroutines */
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.8.0")

    /* DaggerHilt */
    implementation("com.google.dagger:hilt-android:2.49")
    implementation("androidx.hilt:hilt-navigation-compose:1.2.0")
    ksp("com.google.dagger:hilt-compiler:2.48")

    /* DataStore */
    implementation("androidx.datastore:datastore-preferences:1.1.1")

    /* Room db */
    implementation("androidx.room:room-runtime:2.6.1")
    ksp("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")

    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.8.2")
    implementation("androidx.navigation:navigation-compose:2.7.7")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.8.2")
    implementation("androidx.activity:activity-compose:1.9.0")
    implementation("androidx.compose.runtime:runtime:1.6.8")
    implementation("androidx.compose.compiler:compiler:1.5.14")
    implementation("androidx.compose.ui:ui:1.6.8")
    implementation("androidx.compose.ui:ui-graphics:1.6.8")
    implementation("androidx.compose.ui:ui-tooling-preview:1.6.8")
    implementation("androidx.window:window:1.3.0")

    /* Material */
    implementation("androidx.compose.animation:animation:1.6.8")
    implementation("androidx.compose.foundation:foundation:1.6.8")
    implementation("androidx.compose.material3:material3:1.2.1")
    implementation("androidx.compose.material3:material3-window-size-class:1.2.1")
    implementation("androidx.compose.material3:material3-adaptive-navigation-suite:1.0.0-alpha07")

    /* Lottie Animation */
    implementation("com.airbnb.android:lottie-compose:6.1.0")

    /* Cloudy Blur library */
    implementation("com.github.skydoves:cloudy:0.1.2")

    /* Realm Db */
    implementation("io.realm.kotlin:library-base:1.11.0")

    // YCharts for graphing data
    implementation("co.yml:ycharts:2.1.0")

    // Google Ads (AdMob)
    implementation("com.google.android.gms:play-services-ads:23.1.0")
}
