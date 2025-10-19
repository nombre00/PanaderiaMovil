plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.panaderia"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.example.cursojetpack_1"
        minSdk = 26
        targetSdk = 36
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
    buildFeatures {
        compose = true
    }
}

dependencies {

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.entity.extraction)
    implementation(libs.androidx.animation.core)
    //implementation(libs.material3)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    // Mis dependencias.
    implementation(libs.androidx.core.splashscreen) // Para splashScreen
    implementation(libs.androidx.lifecycle.viewmodel.compose) // Para viewModel()
    implementation(libs.androidx.lifecycle.runtime.ktx) // Para LiveData o StateFlow
    implementation(libs.kotlinx.coroutines.android) // Para coroutines
    implementation(libs.androidx.navigation.compose)  // Para navegar entre paginas
    implementation(libs.coil.compose)  // Para manejar urls

    implementation(libs.room.runtime) // Room Runtime
    implementation(libs.room.ktx) // Room KTX para soporte de Kotlin y corutinas
    implementation(libs.androidx.datastore.preferences)
    implementation(libs.gson) // Para serialización a JSON

    implementation(libs.androidx.lifecycle.viewmodel.compose.v287)
    implementation(libs.androidx.lifecycle.runtime.compose)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.androidx.datastore.preferences.v111)

    implementation(libs.androidx.animation)
    implementation(libs.ui)
    implementation(libs.androidx.foundation)

}