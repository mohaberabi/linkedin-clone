plugins {
    alias(libs.plugins.linkedinclone.android.library)


}

android {
    namespace = "com.mohaberabi.linkedinclone.core.remote_logging"

}

dependencies {
    api(libs.firebase.crash)
}