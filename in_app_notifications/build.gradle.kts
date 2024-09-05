plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)
}

android {
    namespace = libs.versions.projectDomain.get() + ".in_app_notifications"

}


dependencies {

    implementation(projects.core.domain)
}