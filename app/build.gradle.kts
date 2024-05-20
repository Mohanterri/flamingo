plugins {
    alias(libs.plugins.androidApplication)
}

android {
    namespace = "com.aves.flamingodb"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.aves.flamingodb"
        minSdk = 25
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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    buildFeatures {
        viewBinding = true
    }
}

dependencies {

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-gson:2.9.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.9.0")
    implementation("com.squareup.okhttp3:okhttp:4.9.0")
    implementation("com.squareup.okhttp3:okhttp-urlconnection:4.4.1")

    implementation("com.google.code.gson:gson:2.10.1")

    implementation("javax.annotation:javax.annotation-api:1.3.2")
    implementation("io.grpc:grpc-okhttp:1.20.0")
    implementation("io.grpc:grpc-android:1.20.0")
    implementation("io.grpc:grpc-protobuf-lite:1.20.0")
    implementation("io.grpc:grpc-stub:1.20.0")
    implementation("io.grpc:grpc-netty-shaded:1.20.0")
    implementation("com.google.android.gms:play-services-tasks:18.1.0")

    implementation("com.google.firebase:firebase-messaging:23.0.0")

    implementation("com.google.auto.value:auto-value-annotations:1.6")
    implementation("androidx.concurrent:concurrent-futures:1.1.0")
    annotationProcessor("com.google.auto.value:auto-value:1.6")


    implementation(libs.navigation.fragment)
    implementation(libs.navigation.ui)


    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    implementation(libs.wear.ongoing)
    implementation(libs.firebase.firestore)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}