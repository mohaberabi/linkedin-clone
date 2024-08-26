plugins {
    alias(libs.plugins.linkedinclone.android.feature)

    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".posts"

}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(libs.androdix.pull.refresh)
    implementation(projects.core.remoteLogging)
}