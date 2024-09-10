plugins {
    alias(libs.plugins.linkedinclone.android.feature)
    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".post_saver"

}

dependencies {

    implementation(projects.core.domain)
    implementation(projects.core.data)
    implementation(projects.core.remoteLogging)
    implementation(projects.core.remoteAnayltics)

}