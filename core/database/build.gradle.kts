plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.linkedinclone.android.room)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.database"

}
dependencies {

    implementation(projects.core.domain)
}