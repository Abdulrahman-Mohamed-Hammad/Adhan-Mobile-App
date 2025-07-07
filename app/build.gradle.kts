import org.jetbrains.kotlin.gradle.model.ComposeCompiler


plugins {

    alias(libs.plugins.android.application)
    alias(libs.plugins.jetbrains.kotlin.android)
    id ("org.jetbrains.kotlin.plugin.serialization")
    alias(libs.plugins.compose.compiler)

}


android {
    bundle {
        abi {
            enableSplit = true  // Enables ABI splitting
        }
    }

    namespace = "com.example.prayer_time"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.prayer_time"
        minSdk = 26

        //noinspection OldTargetApi
        targetSdk = 34
        multiDexEnabled = true
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
//             isDebuggable=true
            isMinifyEnabled = true
            isShrinkResources = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
            signingConfig = signingConfigs.getByName("debug")

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

    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}
composeCompiler{
    enableStrongSkippingMode = true
    reportsDestination =layout.buildDirectory.dir("compose_compiler")

}


dependencies {

    implementation(libs.androidx.core.ktx)
  //  implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.tools.core)

    implementation(libs.play.services.location)
    implementation(libs.play.services.maps)
    implementation(libs.androidx.appcompat)
    implementation(libs.androidx.runtime.livedata)
    implementation(libs.androidx.compose.testing)
   // implementation(libs.androidx.navigation.runtime.jvmstubs)
    //   implementation(libs.androidx.navigation.compose.jvmstubs)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
    implementation(libs.retrofit)
    implementation(libs.converter.gson)


    implementation(libs.ktor.client.core)
    implementation(libs.ktor.client.android)

    implementation(libs.ktor.client.content.negotiation)
    implementation(libs.ktor.serialization.kotlinx.json)


    implementation(libs.kotlinx.serialization.json)

    implementation(libs.ktor.client.logging) // Ktor Logging


    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.android) // For Android
    implementation(libs.ktor.serialization.kotlinx.json.v237) // ✅ Add this


    implementation(libs.rxlocation)
    implementation(libs.rxjava)
    implementation(libs.rxandroid)
  //  implementation(libs.androidx.lifecycle.runtime.ktx.v262)
    implementation(libs.androidx.appcompat)
    debugImplementation(libs.ui.tooling)
    debugImplementation(libs.flipper)
    debugImplementation(libs.leakcanary.android)

implementation(libs.accompanist.permissions) // for permision


    implementation("androidx.navigation:navigation-compose:2.7.7")




}