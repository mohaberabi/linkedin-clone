plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.linkedinclone.android.hilt)

}

android {
    namespace = libs.versions.projectDomain.get() + ".remote_logging"

}

dependencies {
    api(libs.firebase.crash)
}