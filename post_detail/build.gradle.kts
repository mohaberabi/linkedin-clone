plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = "com.mohaberabi.linkedinclone.post_detail"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}