plugins {
    alias(libs.plugins.linkedinclone.android.library)
    alias(libs.plugins.linkedinclone.android.hilt)
}

android {
    namespace = "com.mohaberabi.linedinclone.core.remote_anayltics"

}

dependencies {
    api(libs.firebase.anayltics)
}