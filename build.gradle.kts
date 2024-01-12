// Top-level build file where you can add configuration options common to all sub-projects/modules.
buildscript {
    dependencies {
        classpath("com.google.firebase:firebase-crashlytics-gradle:2.9.9")
        classpath("com.google.firebase:perf-plugin:1.4.1") {
            exclude(group = "com.google.guava", module = "guava-jdk5")
        }
    }
}

plugins {
    id("com.android.application") version "8.2.1" apply false
    id("org.jetbrains.kotlin.android") version "1.9.0" apply false
    id("com.google.dagger.hilt.android") version "2.48.1" apply false
    id("com.android.library") version "8.2.1" apply false
    id("com.google.gms.google-services") version "4.4.0" apply false
    id("com.google.firebase.firebase-perf") version "1.4.2" apply false
}