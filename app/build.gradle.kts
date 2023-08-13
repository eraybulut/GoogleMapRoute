plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.eraybulut.mapexample"
    compileSdk = 33

    viewBinding {
        enable = true
    }

    defaultConfig {
        applicationId = "com.eraybulut.mapexample"
        minSdk = 24
        targetSdk = 33
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")


    //SmartLocation
    implementation ("io.nlopez.smartlocation:library:3.3.3")

    //Retrofit
    implementation ("com.squareup.retrofit2:retrofit:2.9.0")
    implementation ("com.squareup.retrofit2:converter-gson:2.9.0")

    //Okhttp
    implementation ("com.squareup.okhttp3:logging-interceptor:4.7.2")

    //Google Places
    implementation ("com.google.android.libraries.places:places:3.2.0")

    //Google Maps
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.maps.android:android-maps-utils:2.2.3")
    implementation ("com.google.maps.android:maps-ktx:3.1.0")
    implementation ("com.google.android.gms:play-services-maps:18.1.0")
    implementation ("com.google.android.gms:play-services-location:21.0.1")
    implementation ("com.google.maps.android:maps-utils-ktx:3.1.0")

    //Test
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}