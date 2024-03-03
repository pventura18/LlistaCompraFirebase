plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")

    id("com.google.gms.google-services")
}

android {
    namespace = "cat.institutmontilivi.llistacomprafirebase"
    compileSdk = 34

    defaultConfig {
        applicationId = "cat.institutmontilivi.llistacomprafirebase"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }
    kotlinOptions {
        jvmTarget = "1.8"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation(platform("androidx.compose:compose-bom:2023.08.00"))
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-graphics")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.compose.material3:material3")

    //Navegació
    implementation("androidx.navigation:navigation-compose:2.7.7")
    //Biblioteca extesa d'icones
    //Lifecycle
    implementation ("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")

    //Firebase BoM (Bill of Materials)
    //És BoM qui assigna la versió correcta a cadascuna de les
    //biblioteques de firebase
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))
    //Firebase analytics
    implementation("com.google.firebase:firebase-analytics")
    //Firebase analytics
    implementation ("com.google.firebase:firebase-auth-ktx")
    //Firebase Realtime Dababase
    implementation ("com.google.firebase:firebase-database-ktx")
    //Firebase FireStore
    implementation ("com.google.firebase:firebase-firestore-ktx")
    //Firebase DataStorage
    implementation ("com.google.firebase:firebase-storage-ktx")
    //Firebase Crashlytics
    // implementation ("com.google.firebase:firebase-crashlytics-ktx")
    //Firebase Remote config
    implementation ("com.google.firebase:firebase-config-ktx")
    //Firebase Messaging
    implementation ("com.google.firebase:firebase-messaging-ktx")

    //Google play services (per a la identificació a través de Google
    implementation("com.google.android.gms:play-services-auth:21.0.0")

    //Descàrregues d'imatges d'Internet
    implementation ("io.coil-kt:coil-compose:2.5.0")

    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation(platform("androidx.compose:compose-bom:2023.08.00"))
    androidTestImplementation("androidx.compose.ui:ui-test-junit4")
    debugImplementation("androidx.compose.ui:ui-tooling")
    debugImplementation("androidx.compose.ui:ui-test-manifest")

    // Import the Firebase BoM
    implementation(platform("com.google.firebase:firebase-bom:32.7.2"))


    // TODO: Add the dependencies for Firebase products you want to use
    // When using the BoM, don't specify versions in Firebase dependencies
    implementation("com.google.firebase:firebase-analytics")


    // Add the dependencies for any other desired Firebase products
    // https://firebase.google.com/docs/android/setup#available-libraries
}