plugins {
    alias(libs.plugins.linkedinclone.android.library)

    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.kotlin.serilization)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.data"

}

dependencies {
    implementation(projects.core.domain)
    implementation(libs.prefs.datastore)
    implementation(libs.kotlinx.serilization)
}