plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
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

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.15"
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
}
