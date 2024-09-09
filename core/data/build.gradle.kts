plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.linkedinclone.android.hilt)
    alias(libs.plugins.kotlin.serilization)
}

android {
    namespace = libs.versions.projectDomain.get() + ".core.data"

}

dependencies {
    implementation(projects.core.remoteAnayltics)

    implementation(projects.core.domain)
    implementation(libs.prefs.datastore)
    implementation(libs.kotlinx.serilization)
    api(libs.firebase.firestore)
    api(libs.firebase.auth)
    api(libs.firebase.storage)
}