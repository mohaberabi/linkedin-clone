plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".user_metadata"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
}