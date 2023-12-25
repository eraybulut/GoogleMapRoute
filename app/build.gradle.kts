plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    kotlin("kapt")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.eraybulut.mapexample"
    compileSdk = 34

    viewBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.eraybulut.mapexample"
        minSdk = 24
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
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
}

dependencies {
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.11.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")

    //Security Crypto
    implementation ("androidx.security:security-crypto:1.0.0")

    // Coil
    implementation("io.coil-kt:coil:2.4.0")
    
    // Dagger - Hilt
    implementation("com.google.dagger:hilt-android:2.47")
    kapt("com.google.dagger:hilt-android-compiler:2.47")

    //SmartLocation
    implementation ("io.nlopez.smartlocation:library:3.3.3")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Okhttp
    implementation ("com.squareup.okhttp3:logging-interceptor:4.11.0")

    //Google Places
    implementation ("com.google.android.libraries.places:places:3.3.0")

    //Google Maps
    implementation ("com.google.maps.android:maps-ktx:3.1.0")
    implementation ("com.google.android.gms:play-services-maps:18.2.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:android-maps-utils:2.2.3")
    implementation ("com.google.maps.android:maps-utils-ktx:3.1.0")

    implementation ("androidx.fragment:fragment-ktx:1.6.2")
    implementation ("androidx.lifecycle:lifecycle-extensions:2.2.0")

    //Swipe Refresh Layout
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}

kapt {
    correctErrorTypes = true
}