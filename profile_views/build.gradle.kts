plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.jetbrains.kotlin.android)

}

android {
    namespace = libs.versions.projectDomain.get() + ".profile_views"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androdix.pull.refresh)

}