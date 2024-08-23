plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = libs.versions.projectDomain.get() + "core.firestore"

}


dependencies {
    api(libs.firebase.firestore)
    api(libs.firebase.auth)
    implementation(projects.core.domain)
    implementation(projects.core.data)
}