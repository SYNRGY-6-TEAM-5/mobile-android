plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("kotlin-kapt")
    id("com.google.dagger.hilt.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.firebase-perf")
    id("com.google.firebase.crashlytics")
    id("androidx.navigation.safeargs.kotlin")
}

android {
    namespace = "com.synrgy.aeroswift"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.synrgy.aeroswift"
        minSdk = 21
        targetSdk = 34
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
            signingConfig = signingConfigs.getByName("debug")
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
        viewBinding = true
    }
}

dependencies {
    implementation(project(":di"))

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.google.android.gms:play-services-maps:18.2.0")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")

    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.7.0")
    implementation("androidx.activity:activity-ktx:1.8.2")

    implementation("com.google.android.gms:play-services-auth:20.7.0")

    implementation("androidx.datastore:datastore-preferences:1.0.0")
    implementation("io.coil-kt:coil:2.5.0")

    implementation("com.google.dagger:hilt-android:2.48.1")
    kapt("com.google.dagger:hilt-android-compiler:2.48.1")

    implementation("androidx.work:work-runtime-ktx:2.9.0")

    implementation("com.github.Spikeysanju:MotionToast:1.4")

    implementation(platform("com.google.firebase:firebase-bom:32.7.0"))
    implementation("com.google.firebase:firebase-analytics")
    implementation("com.google.firebase:firebase-perf")
    implementation("com.google.firebase:firebase-crashlytics-ktx")

    implementation("androidx.navigation:navigation-fragment-ktx:2.7.6")
    implementation("androidx.navigation:navigation-ui-ktx:2.7.6")

    implementation("com.github.angads25:toggle:1.1.0")

    implementation("com.github.bumptech.glide:glide:4.16.0")

    implementation("com.github.esafirm:android-image-picker:3.0.0")

    implementation("com.ericktijerou.koleton:koleton:1.0.0-beta01")

    implementation("androidx.room:room-runtime:2.6.1")
    kapt("androidx.room:room-compiler:2.6.1")
    implementation("androidx.room:room-ktx:2.6.1")
}

// Allow references to generated code
kapt {
    correctErrorTypes = true
}