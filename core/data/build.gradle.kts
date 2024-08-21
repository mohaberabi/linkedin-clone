plugins {
    alias(libs.plugins.linkedinclone.android.library)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.data"

}

dependencies {
    implementation(projects.core.domain)
}