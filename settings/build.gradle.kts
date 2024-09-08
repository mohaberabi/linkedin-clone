plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = libs.versions.projectDomain.get() + ".settings"

}
dependencies {
    implementation(projects.core.currentUser)
    implementation(projects.core.domain)
}