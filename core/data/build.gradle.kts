plugins {
    alias(libs.plugins.linkedinclone.android.library)

    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.data"

}

dependencies {
    implementation(projects.core.domain)
}