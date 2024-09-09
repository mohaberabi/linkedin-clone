plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)
    alias(libs.plugins.safe.args)

}

android {
    namespace = libs.versions.projectDomain.get() + ".user_media"
    compileSdk = 34

}
dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.remoteAnayltics)

}