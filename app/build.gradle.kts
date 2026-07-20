plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.compose")
}

android {
    namespace = "com.mydgnbot"

    compileSdk = 35

    defaultConfig {
        applicationId = "com.mydgnbot"

        minSdk = 26
        targetSdk = 35

        versionCode = 1
        versionName = "0.1"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    kotlinOptions {
        jvmTarget = "17"
    }

    buildFeatures {
        compose = true
    }

    buildTypes {

        release {

            isMinifyEnabled = false

        }

    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.15.0")

    implementation(
        "androidx.activity:activity-compose:1.9.3"
    )

    implementation(
        "androidx.compose.material3:material3:1.3.1"
    )

    implementation(
        "androidx.compose.ui:ui:1.7.5"
    )

    implementation(
        "androidx.compose.ui:ui-tooling-preview:1.7.5"
    )

    debugImplementation(
        "androidx.compose.ui:ui-tooling:1.7.5"
    )

    implementation(
        "androidx.navigation:navigation-compose:2.8.4"
    )

    implementation(
        "androidx.datastore:datastore-preferences:1.1.1"
    )
}
