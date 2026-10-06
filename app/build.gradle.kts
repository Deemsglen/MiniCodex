plugins {
    id(" com.android.application\)
 kotlin(\android\)
}

android {
 namespace = \com.minicodex\
 compileSdk = 34

 defaultConfig {
 applicationId = \com.minicodex\
 minSdk = 26
 targetSdk = 34
 versionCode = 1
 versionName = \1.0\
 }

 buildTypes {
 getByName(\debug\) {
 isMinifyEnabled = false
 }
 getByName(\release\) {
 isMinifyEnabled = true
 proguardFiles(getDefaultProguardFile(\proguard-android-optimize.txt\), \proguard-rules.pro\)
 }
 }

 buildFeatures {
 compose = true
 }

 composeOptions {
 kotlinCompilerExtensionVersion = \1.5.1\
 }
}

dependencies {
 implementation(project(\:core:ai\))
 implementation(project(\:core:security\))
 implementation(project(\:core:ui\))
 implementation(project(\:core:util\))
 implementation(project(\:feature:agent\))
 implementation(project(\:feature:browser\))
 implementation(project(\:feature:build\))
 implementation(project(\:feature:editor\))
 implementation(project(\:feature:terminal\))

 implementation(\androidx.core:core-ktx:1.12.0\)
 implementation(\androidx.lifecycle:lifecycle-runtime-ktx:2.6.2\)
 implementation(\androidx.activity:activity-compose:1.8.0\)
 implementation(platform(\androidx.compose:compose-bom:2023.08.00\))
 implementation(\androidx.compose.ui:ui\)
 implementation(\androidx.compose.material3:material3\)
 implementation(\androidx.navigation:navigation-compose:2.7.4\)
}