import java.util.Properties

plugins {
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.jetbrainsKotlinAndroid)
    alias(libs.plugins.kotlinSerialization)
}

android {
    namespace = "fi.project.petcare"
    compileSdk = 34

    defaultConfig {
        applicationId = "fi.project.petcare"
        minSdk = 33
        targetSdk = 34
        versionCode = 1
        versionName = "0.1.0"

        val keystoreFile = project.rootProject.file("local.properties")
        val properties = Properties()
        properties.load(keystoreFile.inputStream())

        val apiKey = properties.getProperty("api.key") ?: ""
        val apiUrl = properties.getProperty("api.url") ?: ""
        val serverClientId = properties.getProperty("server.client.id") ?: ""

        buildConfigField(type = "String", name = "SUPABASE_KEY", value = apiKey)
        buildConfigField(type = "String", name = "SUPABASE_URL", value = apiUrl)
        buildConfigField(type = "String", name = "SERVER_CLIENT_ID", value = serverClientId)

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
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
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
        buildConfig = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.11"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
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
    implementation(libs.androidx.material.icons.extended)
    implementation(libs.androidx.lifecycle.viewmodel.compose)
    //implementation(libs.androidx.appcompat)
    //implementation(libs.material)
    //implementation(libs.skiko.android)
    implementation(libs.androidx.navigation.compose)
    // Lets-Plot Kotlin API
    implementation(libs.lets.plot.kotlin.kernel)
    // Lets-Plot Multiplatform
    implementation(libs.lets.plot.common)
    // Lets-Plot Skia Frontend
    implementation(libs.lets.plot.compose)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    // Credentials manager and sign in with google
    implementation(libs.androidx.credentials)
    implementation(libs.androidx.credentials.play.services.auth)
    implementation(libs.googleid)
    // Supabase libraries
    implementation(platform(libs.bom))
    implementation(libs.compose.auth.ui)
    implementation(libs.compose.auth)
    implementation(libs.gotrue.kt)
    implementation(libs.ktor.client.android)
}
